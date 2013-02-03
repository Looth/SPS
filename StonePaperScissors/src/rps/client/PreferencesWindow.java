

//This could be the PreferencesWindow

/*  
package rps.client;

import javax.swing.JFrame;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;


public class PreferencesWindow extends JFrame {

	private final JComboBox resizeBox = new JComboBox();
	public Application appliaction;
	
	public PreferencesWindow(Vector<GameListener> ais){

		 appliaction = new Application(ais);
		getContentPane().setLayout(null);

		JLabel toolBarWindowLabel = new JLabel("Wndow");
		toolBarWindowLabel.setBounds(6, 6, 70, 29);
		getContentPane().add(toolBarWindowLabel);
		
		resizeBox.setModel(new DefaultComboBoxModel(new String[] {"490x600", "700x900"}));
		resizeBox.setBounds(6, 61, 120, 27);
		getContentPane().add(resizeBox);
		
		JLabel resizeLabel = new JLabel("Size");
		resizeLabel.setBounds(16, 47, 61, 16);
		getContentPane().add(resizeLabel);
		
		JButton btnNewButton = new JButton("Apply");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int x,y;
			int index =	resizeBox.getSelectedIndex();
			switch (index) {
			case 0:
				x = 490;
				y = 600;
				break;
			case 1:
				x = 700;
				y = 900;
				break;
			default:
				 x = 490;y=600;
				break;
			}
			appliaction.resizeMe(x,y);
			}
		});

		btnNewButton.setBounds(9, 100, 117, 29);
		getContentPane().add(btnNewButton);
		
		setTitle("Preferences");
		setSize(400,300);
		setVisible(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setResizable(false);
	
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}*/
