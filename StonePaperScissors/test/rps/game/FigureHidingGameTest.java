package rps.game;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.rmi.RemoteException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import rps.game.data.Figure;
import rps.game.data.FigureKind;
import rps.game.data.Move;
import rps.game.data.Player;

public class FigureHidingGameTest {
	
	private Game game;
	private Player thePlayer;
	private Player otherPlayer;

	private Figure[] fields;

	private FigureHidingGame sut;

	@Before
	public void setup() throws RemoteException {

		game = mock(Game.class);
		
		fields = new Figure[42];
		when(game.getField(any(Player.class))).thenReturn(fields);

		thePlayer = new Player("A");
		otherPlayer = new Player("B");

		sut = new FigureHidingGame(game);
	}
	
	/*
	@Test
	public void addTestsHere() {
		fail();
	}
	*/
	
	@Test
	public void getFieldTest() throws RemoteException{
		fields[1] = createFigureForPlayer(thePlayer);
		fields[2] = createFigureForPlayer(otherPlayer);
		fields[3] = createFigureForPlayer(otherPlayer);
		fields[3].setDiscovered();
		
		fields = sut.getField(thePlayer);
		
		for(int i = 0; i < 42; i++){
			if(i == 1){
				Assert.assertTrue(fields[1].getKind() == FigureKind.PAPER); //own figure won't be hidden
			}else if(i == 2){
				Assert.assertTrue(fields[2].getKind() == FigureKind.HIDDEN);//enemy figure (undiscovered) will be hidden
			}else if(i == 3){
				Assert.assertTrue(fields[3].getKind() == FigureKind.PAPER);// enemy figure (discovered) won't be hidden
			}
			else{
				Assert.assertTrue(fields[i] == null);
			}
		}
	}
	
	@Test
	public void getLastMoveTest() throws RemoteException{
		fields[1] = createFigureForPlayer(thePlayer);
		fields[2] = createFigureForPlayer(otherPlayer);
		fields[3] = createFigureForPlayer(otherPlayer);
		fields[3].setDiscovered();
		
		sut.move(thePlayer, 1, 0);
		sut.move(otherPlayer, 3, 4);
		sut.move(thePlayer, 0, 1);
		//Assert.assertTrue(fields[0].getKind() == FigureKind.PAPER);
		//Assert.assertTrue(fields[1] == null);
		
		
		Move lastMove = sut.getLastMove(otherPlayer);
		
		
	}
	
	@Test
	public void moveIsPropagatedToGame() throws RemoteException {
		fields[1] = createFigureForPlayer(thePlayer);
		sut.move(thePlayer, 1, 2);
		verify(game).move(thePlayer, 1, 2);
	}
	
	private static Figure createFigureForPlayer(Player p) {
		return new Figure(FigureKind.PAPER, p);
	}
}