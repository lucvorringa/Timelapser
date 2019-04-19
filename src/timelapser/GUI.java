package timelapser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jcodec.api.awt.AWTSequenceEncoder;



public class GUI extends JFrame{
	public static int ImagesAmount = 0;
	public static BufferedImage[] images;
	
	//Objects for selecting the images
	JLabel LBimages = new JLabel("Images:");
	JButton BTNimages = new JButton("Choose");
	JPanel JPimageSelector = new JPanel();
	
	//Objects for selecting the FPS
	JLabel LBfps = new JLabel("FPS:");
	JTextField TFfps = new JTextField("");
	JPanel JPfps = new JPanel();
	
	//Button for starting the converting prozess
	JButton BTNgo = new JButton("GO");
	
	public int getFPS() {
		return Integer.parseInt(TFfps.getText());
	}
			
	
	public GUI() {
		this.setTitle("timelapser");
		this.setVisible(true);
		this.setSize(500,500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Objects for selecting the images
		BTNimages.addActionListener(new ChooseImagesListener());
		JPimageSelector.add(LBimages);
		JPimageSelector.add(BTNimages);
		
		//fps objects
		JPfps.add(LBfps);
		JPfps.add(TFfps);
		TFfps.setPreferredSize( new Dimension( 100, 24 ) );
		
		//Button for starting the converting prozess
		BTNgo.addActionListener(new goListener());
		
		//BorderLayout
		this.getContentPane().add(JPimageSelector, BorderLayout.NORTH);
		this.getContentPane().add(JPfps, BorderLayout.CENTER);
		this.getContentPane().add(BTNgo, BorderLayout.SOUTH);	
	}
	
	
	class goListener implements ActionListener  {
		@Override
		public void actionPerformed(ActionEvent arg0)  {
			try {
				AWTSequenceEncoder enc = AWTSequenceEncoder.createSequenceEncoder(new File("test.mp4"),getFPS() );
				for(int x=1; ImagesAmount>x;x++) {
					enc.encodeImage(images[x]);
				}
				enc.finish();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	class ChooseImagesListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
				File[] files;
	        	JFileChooser chooser = new JFileChooser();
	        	chooser.setMultiSelectionEnabled(true);
	        	FileFilter imageFilter = new FileNameExtensionFilter(
	        		    "Image files", ImageIO.getReaderFileSuffixes());
	        	chooser.setFileFilter(imageFilter);
	        	if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
	        		files = chooser.getSelectedFiles();
	        		//System.out.println(files);
	        		ImagesAmount = files.length;
	        		//System.out.println(ImagesAmount);
	        		images = new BufferedImage[ImagesAmount];
	        		
	        		//converting the files to BufferdImages
	        		for(int x=1;x<ImagesAmount;x++) {
	        			try {
							images[x] = ImageIO.read(files[x]);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        		}
	        		
	        		
	        	}
		}
	}
	
}
