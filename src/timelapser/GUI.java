package timelapser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
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
		BTNimages.addActionListener(new ChooseImagesListener());
		
		JLabel LBfps = new JLabel("FPS:");
		JTextField TFfps = new JTextField("");
		TFfps.setPreferredSize( new Dimension( 100, 24 ) );
		
		//BorderLayout North	
		JPanel JPnorth = new JPanel();
		JPnorth.add(LBimages);
		JPnorth.add(BTNimages);
		this.getContentPane().add(JPnorth, BorderLayout.NORTH);
		
		//BorderLayout Center
		JPanel JPcenter = new JPanel();
		JPcenter.add(LBfps);
		JPcenter.add(TFfps);
		this.getContentPane().add(JPcenter, BorderLayout.CENTER);
		
	}
	class ChooseImagesListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
	        JFileChooser chooser = new JFileChooser();
	        int rueckgabeWert = chooser.showOpenDialog(null);
	        if(rueckgabeWert == JFileChooser.APPROVE_OPTION){
	            System.out.println("Die zu öffnende Datei ist: " +
	            chooser.getSelectedFile().getName());
	        }
	        
		}
		
	}
	
}
