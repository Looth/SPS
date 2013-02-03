package rps.client.ui;


import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;


import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import rps.game.Game;
import rps.game.data.Player;

public class GamePane {

	private final  JPanel gamePane = new JPanel();
	private final JTextField chatInput = new JTextField();
	private final JTextArea chat = new JTextArea(4, 30);
	private final JScrollPane scrollPane = new JScrollPane(chat);

	private Game game;
	private Player player;
	
	private  JLabel[] labels = new JLabel[42];

	
	public GamePane(Container parent,Dimension dim) {

		gamePane.setLayout(null); 
		
		
		setSizes(dim.width,dim.height);
	
		
		
	
		gamePane.add(chatInput);
		gamePane.add(scrollPane);

		chat.setLineWrap(true);
		chat.setEditable(false);

		gamePane.setVisible(false);

		parent.add(gamePane);

		bindButtons();
	}

	
	public  void setSizes(int width,int higth){
		//ImageIcon ib =new ImageIcon();//("/Users/viernickel/Desktop/Sammler/Java/Workspace/RockPaperScissors/src/rps/client/ui/Black.jpg");
		//ImageIcon iw  =new ImageIcon();//("/Users/viernickel/Desktop/Sammler/Java/Workspace/RockPaperScissors/src/rps/client/ui/White.jpg");
		gamePane.removeAll();
		

		width = width/7;
		higth = higth/6;
		// ib.setImage(ib.getImage().getScaledInstance(width,width,Image.SCALE_DEFAULT)); 
		 //iw.setImage(iw.getImage().getScaledInstance(width,width,Image.SCALE_DEFAULT));
	/*	graph.setColor(Color.BLACK);
		 ib.paintIcon(null, graph, 0, 0);
		 graph.setColor(Color.WHITE);
		 ib.paintIcon(null, graph, 0, 0);*/
		int x1,y1;
		int z = 0;
		for(int i = 0; i < 6; i++){
			y1 = i*width;
			for(int b = 0; b < 7 ; b++){
				x1= b*width;
				labels[z] = new JLabel();
				if(z%2==0)
					labels[z].setBackground(Color.WHITE);//labels[z].setIcon(iw);
				else
					 labels[z].setBackground(new Color(0, 125, 0 ,125));//labels[z].setIcon(ib);
			
			     labels[z].setOpaque(true);
				labels[z].setSize(width,width);
				labels[z].setLocation(x1, y1);
				labels[z].setVisible(true); 
				
				gamePane.add(labels[z]);
					z++;
				
			}
	}
		
		chatInput.setLocation(0, 420);
		chatInput.setSize(width*7, 50);
		scrollPane.setLocation(0, 470);
		scrollPane.setSize(width*7,80);//größen müssen noch festgelegt werden
	}
	
	
	private void bindButtons() {
		chatInput.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				boolean isEnter = e.getKeyCode() == KeyEvent.VK_ENTER;
				if (isEnter) {
					addToChat();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
	}

	private void addToChat() {
		String message = chatInput.getText().trim();
		if (message.length() > 0) {
			try {
				game.sendMessage(player, message);
				chatInput.setText("");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void hide() {
		gamePane.setVisible(false);
	}

	public void startGame(Player player, Game game) {
		this.player = player;
		this.game = game;
		reset();
		gamePane.setVisible(true);
	}

	public void receivedMessage(Player sender, String message) {

		if (chat.getText().length() != 0) {
			chat.append("\n");
		}
	
		String formatted = sender.getNick() + ": " + message;
		chat.append(formatted);
		chat.setCaretPosition(chat.getDocument().getLength());
	}

	public void systemChat(String massage){
		if (chat.getText().length() != 0) {
			chat.append("\n");
		}
		String formatted = "System: " + massage;
		chat.append(formatted);
		chat.setCaretPosition(chat.getDocument().getLength());
		
	}
	
	private void reset() {
		chat.setText(null);
	}
	
	public JLabel[] getLabels() {
		return labels;
	}
}