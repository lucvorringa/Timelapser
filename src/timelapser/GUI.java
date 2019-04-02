package timelapser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jcodec.*;
import org.jcodec.api.awt.AWTSequenceEncoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.Rational;

public class GUI extends JFrame{
	public static int ImagesAmount = 0;
	public static int VideoLenght;
	public static File[] files;
	
	
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
		
		JButton BTNgo = new JButton("GO");
		BTNgo.addActionListener(new goListener());
		
		
		//JPanel Image Selector	
		JPanel JPimageSelector = new JPanel();
		JPimageSelector.add(LBimages);
		JPimageSelector.add(BTNimages);
		
		//JPanel FPS
		JPanel JPfps = new JPanel();
		JPfps.add(LBfps);
		JPfps.add(TFfps);
		
		//BorderLayout
		this.getContentPane().add(JPimageSelector, BorderLayout.NORTH);
		this.getContentPane().add(JPfps, BorderLayout.CENTER);
		
		
		
		
		
	}
	class goListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			
		}
		
	}
	class ChooseImagesListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
	        JFileChooser chooser = new JFileChooser();
	        chooser.setMultiSelectionEnabled(true);
	        if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
	        	files = chooser.getSelectedFiles();
	        	ImagesAmount = files.length;
	        }
		}
	}
	
}
