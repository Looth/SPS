package rps.game;

import java.rmi.RemoteException;
import rps.game.data.Figure;
import rps.game.data.FigureKind;
import rps.game.data.Move;
import rps.game.data.Player;

public class ValidatingGame implements Game {

	private final Game game;
	private final Player thisPlayer;
	
	private static final short fieldsize = 42;

	public ValidatingGame(Game game, Player player) throws RemoteException {
		this.game = game;
		thisPlayer = player;
	}

	@Override
	public void setInitialAssignment(Player p, FigureKind[] assignment) throws RemoteException {
		checkPlayer(p);
		
		/*
		 * contains the amount of different kind of figure on the field.
		 * figurAmount:
		 * [0] amount of nulls
		 * [1] amount of rocks
		 * [2] amount of scissors
		 * [3] amount of papers
		 * [4] amount of flags
		 * [5] amount of traps
		 */
		short[] figureAmount={0, 0, 0, 0, 0, 0};
		short[] requiredFigureAmount = {28, 4, 4, 4, 1, 1};
		String[] figureKinds = {"null", "rock", "scissor", "paper", "flag", "trap"};
		
		//check if exactly 42 fields will assigned.
		if(assignment.length != fieldsize){
			throw new IllegalArgumentException(	"Assigned "+ assignment.length + " Fields!" +
												"Should assign exactly 42 fields.");
		}
			
		//count amount of figures
		for(int i=0; i < fieldsize; i++){
			if(assignment[i] == null){
				figureAmount[0] += 1;
			}else if(assignment[i] == FigureKind.ROCK){
				figureAmount[1] += 1;
			}else if(assignment[i] == FigureKind.PAPER){
				figureAmount[2] += 1;
			}else if(assignment[i] == FigureKind.SCISSORS){
				figureAmount[3] += 1;
			}else if(assignment[i] == FigureKind.FLAG){
				figureAmount[4] += 1;
			}else if(assignment[i] == FigureKind.TRAP){
				figureAmount[5] += 1;
			}else if(assignment[i] == FigureKind.HIDDEN){
				throw new IllegalArgumentException("You tried to assign a Figure as Hidden!");
			}
		}
		
		//compare the amount of figures with the required amount of figures.
		for(int i = 0; i<figureAmount.length; i++){
			if(figureAmount[i] != requiredFigureAmount[i]){
				throw new IllegalArgumentException(	"You did not assign the right amount of "+figureKinds[i]+"s. " +
													"You should place "+requiredFigureAmount[i]+". "+
													"You placed " +figureAmount[i]+"!");
			}
		}
		
		//check if assignments are on the correct fields.
		for(int i = 0; i < 28; i++){
			if(assignment[i] != null){
				throw new IllegalArgumentException("You tried to assign one field which wasn't one of your starting fields!");
			}
		}
		for(int i = 28; i<fieldsize; i++){
			if(assignment[i] == null){
				throw new IllegalArgumentException("You didn't assign one of your starter fields!");
			}
		}
		
		game.setInitialAssignment(p, assignment);
	}
	
	@Override
	public Figure[] getField(Player p) throws RemoteException {
		return game.getField(p);
	}

	@Override
	public void move(Player p, int from, int to) throws RemoteException {
		
		Figure[] currentField = game.getField(thisPlayer);
		
		checkPlayer(p);
		
		if(from < 0 || to < 0 || from >= fieldsize || to >= fieldsize || (to == from + 1 && to % 7 == 0) || (to == from - 1 && to % 7 == 6) ){
			throw new IllegalArgumentException("You cannot move out of the Borders of the field!");
		}
		
		if(!(to == from + 1 || to == from - 1 || to == from + 7 || to == from -7)){
			throw new IllegalArgumentException("You can only move to surrounding fields!");
		}
		
		if(!currentField[from].belongsTo(p)){
			throw new IllegalArgumentException("You cannot move the figures of the other player!");
		}
		
		if(currentField[from] == null){
			throw new IllegalArgumentException("Please select a field with a valid figure!");
		}
		
		if(currentField[to] != null && currentField[to].belongsTo(p)){
			throw new IllegalArgumentException("You cannot attack your own Figures!");
		}
		
		if(currentField[from].getKind() == FigureKind.FLAG || currentField[from].getKind() == FigureKind.TRAP){
			throw new IllegalArgumentException("You cannot move this Figure!");
		}
		
		game.move(p, from, to);
	}

	@Override
	public Move getLastMove(Player p) throws RemoteException {
		return game.getLastMove(p);
	}

	@Override
	public void sendMessage(Player p, String message) throws RemoteException {
		checkPlayer(p);
		game.sendMessage(p, message);
	}

	@Override
	public void setInitialChoice(Player p, FigureKind kind) throws RemoteException {
		isRockPaperScissor(kind);
		game.setInitialChoice(p, kind);
	}

	private static void isRockPaperScissor(FigureKind kind) {
		if(!(kind == FigureKind.PAPER || kind == FigureKind.SCISSORS || kind == FigureKind.PAPER)){
			throw new IllegalArgumentException(kind + " is not a valid choice!");
		}
	}
	
	@Override
	public void setUpdatedKindAfterDraw(Player p, FigureKind kind) throws RemoteException {
		checkPlayer(p);
		isRockPaperScissor(kind);
		
		game.setUpdatedKindAfterDraw(p, kind);
	}

	@Override
	public void surrender(Player p) throws RemoteException {
		checkPlayer(p);
		game.surrender(p);
	}

	@Override
	public Player getOpponent(Player p) throws RemoteException {
		return game.getOpponent(p);
	}

	/**
	 * Checks if the submitted player is the player corresponding to the client.
	 * @param p player to check
	 */
	private void checkPlayer(Player p){
		if(p != thisPlayer)
			throw new IllegalArgumentException("You cannot make a decision for your enemy");
	}
}