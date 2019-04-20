package timelapser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jcodec.api.awt.AWTSequenceEncoder;



public class GUI extends JFrame{
	public boolean ImagesChosen = false;
	
	public boolean isInt(String s) {
		try{
			  int num = Integer.parseInt(s);
			  return true;
			} catch (NumberFormatException e) {
			  return false;
			}
	}
	
	//about section
	JTextArea TAabout = new JTextArea();
    JScrollPane AboutscrollPane = new JScrollPane(TAabout);
    JPanel JPabout = new JPanel(new BorderLayout());
	
	//Tabbed View
	JTabbedPane tabs = new JTabbedPane();
	
	public ArrayList<File> Images = new ArrayList<File>();
	public String destination;
	
	//The Main JPanel
	JPanel JPBody = new JPanel();
	
	//Objects for selecting the images
	JLabel LBimages = new JLabel("Bilder(.jpg):");
	JButton BTNimages = new JButton("auswählen");
	JPanel JPimageSelector = new JPanel();
	//JLabel LBchooseImagesStatus = new JLabel("Please Choose!");
	
	//Objects Destination Path
	JLabel LBdestination = new JLabel("Ziel(.mp4):");
	JButton BTNdestination = new JButton("auswählen");
	JPanel JPdestination = new JPanel();
	//JLabel LBchooseDestinationStatus = new JLabel("Please Choose!");
	
	//Objects for selecting the FPS
	JLabel LBfps = new JLabel("FPS:");
	JTextField TFfps = new JTextField("2");
	JPanel JPfps = new JPanel();
	
	//Button for starting the converting prozess
	JButton BTNgo = new JButton("GO");
	
	public void InitAbout() {
		//About section
		JPabout.add(AboutscrollPane);
		TAabout.setEditable(false);
		TAabout.setText("(C) 2019 Luca Vornheder  \n This Software is using the JCodec libary. \n\n\n "
				+ "Copyright 2008-2017 JCodecProject \r\n" + 
				"\r\n" + 
				"Redistribution  and  use  in   source  and   binary   forms,  with  or  without \r\n" + 
				"modification, are permitted provided  that the following  conditions  are  met:\r\n" + 
				"\r\n" + 
				"Redistributions of  source code  must  retain the above  copyright notice, this\r\n" + 
				"list of conditions and the following disclaimer. Redistributions in binary form\r\n" + 
				"must  reproduce  the above  copyright notice, this  list of conditions  and the\r\n" + 
				"following disclaimer in the documentation and/or other  materials provided with\r\n" + 
				"the distribution.\r\n" + 
				"\r\n" + 
				"THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\" AND\r\n" + 
				"ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,  BUT NOT LIMITED TO, THE  IMPLIED\r\n" + 
				"WARRANTIES  OF  MERCHANTABILITY  AND  FITNESS  FOR  A  PARTICULAR  PURPOSE  ARE\r\n" + 
				"DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR\r\n" + 
				"ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,  OR CONSEQUENTIAL DAMAGES\r\n" + 
				"(INCLUDING,  BUT NOT LIMITED TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES;\r\n" + 
				"LOSS OF USE, DATA, OR PROFITS;  OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON\r\n" + 
				"ANY  THEORY  OF  LIABILITY,  WHETHER  IN  CONTRACT,  STRICT LIABILITY,  OR TORT \r\n" + 
				"(INCLUDING  NEGLIGENCE OR OTHERWISE)  ARISING IN ANY WAY OUT OF THE USE OF THIS\r\n" + 
				"SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.");
		
		
	}
	public void InitListener() {
		//Button for starting the converting prozess
		BTNgo.addActionListener(new goListener());
				
		//Button for the Destination
		BTNdestination.addActionListener(new DestinationListener());
	}
			
	
	public GUI() {
		//init About Tab
		InitAbout();
		//Init all ActionListener
		InitListener();
		
		//creating the tabs
		tabs.addTab("Timelapse erstellen", JPBody);
		tabs.addTab("Über", JPabout);
		
		//init
		this.setTitle("Timelapser");
		this.setVisible(true);
		this.setSize(300,300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//set GridLayout
		JPBody.setLayout(new GridLayout(0,1));
		this.getContentPane().add(tabs);
		
		//Objects for selecting the images
		BTNimages.addActionListener(new ChooseImagesListener());
		JPimageSelector.add(LBimages);
		JPimageSelector.add(BTNimages);
		//JPimageSelector.add(LBchooseImagesStatus);
		
		//Objects for the destination
		JPdestination.add(LBdestination);
		JPdestination.add(BTNdestination);
		//JPdestination.add(LBchooseDestinationStatus);
		
		//fps objects
		JPfps.add(LBfps);
		JPfps.add(TFfps);
		TFfps.setPreferredSize( new Dimension( 100, 24 ) );
		
		//adding everything to the Main JPanel
		JPBody.add(JPfps);
		JPBody.add(JPdestination);
		JPBody.add(JPimageSelector);
		JPBody.add(BTNgo);
		
	}
	
	
	
	class goListener implements ActionListener  {
		
		@Override
		public void actionPerformed(ActionEvent arg0)  {
			
			
			if(destination == null) {
				JOptionPane.showMessageDialog(JPBody,"Bitte wähle ein Ziel aus","Error",JOptionPane.ERROR_MESSAGE);
			} if(ImagesChosen==false) {
				JOptionPane.showMessageDialog(JPBody,"Bitte wähle die Bilder aus ","Error",JOptionPane.ERROR_MESSAGE);
			} if (isInt(TFfps.getText())==false) {
				JOptionPane.showMessageDialog(JPBody,"Die FPS sind im falschen Format","Error",JOptionPane.ERROR_MESSAGE);
			}
			if(destination !=null &&isInt(TFfps.getText())==true&&ImagesChosen==true) {
				try {
					AWTSequenceEncoder enc = AWTSequenceEncoder.createSequenceEncoder(new File(destination),Integer.parseInt(TFfps.getText()));
					for(File i : Images) {
						enc.encodeImage(ImageIO.read(i));
					}
					enc.finish();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}
	}
	class ChooseImagesListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(destination==null) {
				JOptionPane.showMessageDialog(JPBody,"Bitte wähle ein Ziel aus","Error",JOptionPane.ERROR_MESSAGE);
			}else {
				JFileChooser chooser = new JFileChooser();
	        	chooser.setMultiSelectionEnabled(true);
	        	FileFilter imageFilter = new FileNameExtensionFilter(
	        		    "Image files", ImageIO.getReaderFileSuffixes());
	        	chooser.setFileFilter(imageFilter);
	        	if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
	        		//LBchooseImagesStatus.setText("Please Wait...");
	        		for(File i : chooser.getSelectedFiles()) {
	        			Images.add(i.getAbsoluteFile());
	        		}
	        		ImagesChosen=true;
	        		
	        		//LBchooseImagesStatus.setText("OK");
	        		
	        		
	        	}
			}
	        	
		}
	}
	class DestinationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser destinationChooser = new JFileChooser();
			destinationChooser.setMultiSelectionEnabled(false);
			if(destinationChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				if(destinationChooser.getSelectedFile().toString().endsWith(".mp4")) {
					destination = destinationChooser.getSelectedFile().getAbsolutePath();
				}else {
					JOptionPane.showMessageDialog(JPBody,"Datei muss eine .mp4 sein","Error",JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		}
		
	}
		
	
}
