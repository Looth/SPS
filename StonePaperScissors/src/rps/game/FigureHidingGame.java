package rps.game;

import java.rmi.RemoteException;

import rps.game.data.Figure;
import rps.game.data.FigureKind;
import rps.game.data.Move;
import rps.game.data.Player;

/**
 * This decorator is used to remove all information from get* methods that are
 * not visible for the corresponding player. Most importantly this is the
 * FigureKind of all Figure on the field that are undiscovered yet and do belong
 * to the other player.
 */
public class FigureHidingGame implements Game {

	private final Game game;

	public FigureHidingGame(Game game) throws RemoteException {
		this.game = game;
	}

	@Override
	public void setInitialAssignment(Player p, FigureKind[] assignment) throws RemoteException {
		game.setInitialAssignment(p, assignment);
	}

	@Override
	public Figure[] getField(Player p) throws RemoteException {
		
		Figure[] fieldToHide = game.getField(p);
		
		hideField(p, fieldToHide);
		
		return fieldToHide;
	}

	private void hideField(Player p, Figure[] fieldToHide) {
		for(int i = 0; i < fieldToHide.length; i++){
			if(fieldToHide[i] != null && !fieldToHide[i].belongsTo(p) && !fieldToHide[i].isDiscovered()){
				fieldToHide[i] = fieldToHide[i].cloneWithHiddenKind();
			}
		}
	}

	@Override
	public void move(Player p, int from, int to) throws RemoteException {
		game.move(p, from, to);
	}

	@Override
	public Move getLastMove(Player p) throws RemoteException {
		
		Move lastMove = game.getLastMove(p);
		
		
		Figure[] moveToHide = lastMove.getOldField();
		System.out.println(moveToHide);
		hideField(p,moveToHide);
		
		return new Move(lastMove.getFrom(), lastMove.getTo(), moveToHide);
		
		//return null;
	}

	@Override
	public void sendMessage(Player p, String message) throws RemoteException {
		game.sendMessage(p, message);
	}

	@Override
	public void setInitialChoice(Player p, FigureKind kind) throws RemoteException {
		game.setInitialChoice(p, kind);
	}

	@Override
	public void setUpdatedKindAfterDraw(Player p, FigureKind kind) throws RemoteException {
		game.setUpdatedKindAfterDraw(p, kind);
	}

	@Override
	public void surrender(Player p) throws RemoteException {
		game.surrender(p);
	}

	@Override
	public Player getOpponent(Player p) throws RemoteException {
		return game.getOpponent(p);
	}
}