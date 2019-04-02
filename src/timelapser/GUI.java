package timelapser;

import java.awt.BorderLayout;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI extends JFrame{
	
	public GUI() {
		this.setTitle("timelapser");
		this.setVisible(true);
		this.setSize(1000, 1000);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JLabel LBimages = new JLabel("Images:");
		JButton BTNimages = new JButton("Choose");
		JPanel JPnorth = new JPanel();
		
		//BorderLayout North
		JPnorth.add(LBimages);
		JPnorth.add(BTNimages);
		this.getContentPane().add(JPnorth, BorderLayout.NORTH);
		
		
		
	}
	
}
