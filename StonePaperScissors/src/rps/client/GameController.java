package rps.client;
import static rps.network.NetworkUtil.hostNetworkGame;


import java.rmi.RemoteException;

import rps.client.ui.GamePane;
import rps.game.Game;
import rps.game.data.Player;
import rps.network.GameRegistry;
import rps.network.NetworkUtil;

import java.awt.Dimension;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import Berechnungen.GetField;



/**
 * this class is responsible for controlling all game related events.
 */
public class GameController implements GameListener, MouseListener {

	private UIController uiController;
	private GamePane gamePane;

	private GameRegistry registry;
	private Player player;
	private Game game;
	private Dimension dim;

	public void setComponents(UIController uiController, GamePane gamePane,Dimension dim) {
		this.uiController = uiController;
		this.gamePane = gamePane;
		this.dim = dim;
	}

	public void startHostedGame(Player player, String host) {
		this.player = player;
		registry = hostNetworkGame(host);
		register(player, this);
	}

	public void startJoinedGame(Player player, String host) {
		this.player = player;
		registry = NetworkUtil.requestRegistry(host);
		register(player, this);
	}

	public void startAIGame(Player player, GameListener ai) {
		this.player = player;
		registry = NetworkUtil.hostLocalGame();
		register(new Player(ai.toString()), ai);
		register(player, this);
	}

	private void register(Player player, GameListener listener) {
		try {
			GameListener multiThreadedListener = decorateListener(listener);
			registry.register(player, multiThreadedListener);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	private static GameListener decorateListener(GameListener listener) {
		try {
			listener = new MultiThreadedGameListener(listener);
			listener = new RMIGameListener(listener);
			return listener;
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public void unregister() {
		try {
			if (registry != null) {
				registry.unregister(player);
			}
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public void surrender() {
		try {
			game.surrender(player);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public void resetForNewGame() {
		surrender();
	}

	public void exit() {
		if (registry != null) {
			unregister();
		}
		if (game != null) {
			surrender();
		}
	}

	@Override
	public void chatMessage(Player sender, String message) throws RemoteException {
			gamePane.receivedMessage(sender, message);
		

	}

	@Override
	public void provideInitialAssignment(Game game) throws RemoteException {
		this.game = game;
		uiController.switchToGamePane();
		gamePane.startGame(player, game);
		gamePane.systemChat("Set your figures \n You have to set your 14 figures now!");
	
		refreshLabels();
	}
	
	public void refreshLabels(){
		
		JLabel[] labels= gamePane.getLabels();
		for(int i = 0; i < labels.length;i++){
			labels[i].addMouseListener(this);
			
		}
		
	}

	
	@Override
	public void provideInitialChoice() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void startGame() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void provideNextMove() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void figureMoved() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void figureAttacked() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void provideChoiceAfterFightIsDrawn() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void gameIsLost() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void gameIsWon() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void gameIsDrawn() throws RemoteException {
		// TODO Auto-generated method stub

	}
	
	//MouseListener
	
	
	
	
	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		
		
		gamePane.systemChat("Figur placed at field " + GetField.getField(dim,e.getComponent().getLocation()));
		
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {

	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {

	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
	
	
}