import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
public class Blur extends FlexiblePictureExplorer implements ImageObserver {
	//not the same 
	public static int[] coords = {-1,-1,-1,-1};
	public static String basePic = "shiina.jpeg";
	public static Picture pict1 = new Picture("User_Images/" + basePic);
	public static Graphics2D graphics;
	public static Picture disp;
	public static int kernelIndex = 7;
	public int resetIndex = 99;
	public static int count = 0;
	public static ArrayList<String> imageNames = new ArrayList<String>();
	public static ArrayList<Integer> counts = new ArrayList<Integer>();
	public static int currentIndex = 0;
	public static HashMap<String, Stack<Picture>> allImages = new HashMap<String, Stack<Picture>>(); 
	// GUI components
	//kernel
	private JLabel kernelLabel;
	private JButton kernelPrevButton;
	private JButton kernelNextButton;
	private JTextField kernelValue;
	//reset
	private JButton resetButton;
	private JLabel resetLabel;
	//redo
	private JButton redoButton;
	private JLabel  redoLabel;
	//undo
	private JButton undoButton;
	private JLabel undoLabel;
	public static int undoRedoValue;
	//picture switching
	private JButton nextPic;
	private JButton prevPic;
	private JTextField picName;
	private JLabel picLabel;
	//addFile
	private JButton addFile;
	private JLabel addFileLabel;
	//savePicture
	private JButton savePic;
	private JLabel savePicLabel;
	private File savedFile;
	//deleteButton
	private JButton delPic;
	private JLabel delPicLabel;
	public Blur(){
		super(new Picture(1280,720));
		File folderOfImages = new File("/Users/daeyong/git/Blur/Picture_Project/User_Images/"); //file name for the junk folder
		String[]images = folderOfImages.list();
		for (String image : images) {
			if (!basePic.equals(image)) {
				File theCurrentFile = new File(folderOfImages.getPath(),image);
				theCurrentFile.delete();
			}
		}
		Stack<Picture> changes = new Stack<Picture>();
		changes.push(pict1);
		Stack<Picture> retractions = new Stack<Picture>();
		allImages.put(basePic+":c", changes);
		allImages.put(basePic+":r", retractions);
		counts.add(0);
		displayMain();
	}
	private void setUpNextAndPreviousButtons()
	
	  {
	    // create the image icons for the buttons
	    Icon prevIcon = new ImageIcon(DigitalPicture.class.getResource("leftArrow.gif"), 
	                                  "previous index");
	    Icon nextIcon = new ImageIcon(DigitalPicture.class.getResource("rightArrow.gif"), 
	                                  "next index");
	    Icon resetIcon = new ImageIcon("slideshow/resettwoNEW_27x27.png"); 
	    Icon undoIcon = new ImageIcon("slideshow/Undo_Icon_27x27.png"); 
	    Icon redoIcon = new ImageIcon("slideshow/redo_icon_27x27.png"); 
	    Icon addFileIcon = new ImageIcon("slideshow/plusImage_27x27.png");
	    Icon savePicIcon = new ImageIcon("slideshow/DownloadButton_27x27.png");
	    Icon delPicIcon = new ImageIcon("slideshow/Delete_Icon_27x27.png");
	    //create the icons for each button
	    kernelPrevButton = new JButton(prevIcon);
	    kernelNextButton = new JButton(nextIcon);
	    resetButton = new JButton(resetIcon);
	    redoButton = new JButton(redoIcon);
	    undoButton = new JButton(undoIcon);
	    prevPic = new JButton(prevIcon);
	    nextPic = new JButton(nextIcon);
	    addFile = new JButton(addFileIcon);
	    savePic = new JButton(savePicIcon);
	    delPic = new JButton(delPicIcon);
	    
	    // set the tool tip text
	    kernelNextButton.setToolTipText("Click to increase Kernel size");
	    kernelPrevButton.setToolTipText("Click to decrease Kernel size");
	    resetButton.setToolTipText("Click to reset to the Original Picture");
	    redoButton.setToolTipText("Click to redo your last action");
	    undoButton.setToolTipText("Click to undo your last action");
	    prevPic.setToolTipText("Click to go to the previous picture");
	    nextPic.setToolTipText("Click to go to the next picture");
	    addFile.setToolTipText("Click to add another Picture to Blur");
	    savePic.setToolTipText("Click to save the Picture to your computer");
	    delPic.setToolTipText("Click to delete the current image");
	    // set the sizes of the buttons
	    int prevWidth = prevIcon.getIconWidth() + 2;
	    int nextWidth = nextIcon.getIconWidth() + 2;
	    int prevHeight = prevIcon.getIconHeight() + 2;
	    int nextHeight = nextIcon.getIconHeight() + 2;
	    Dimension prevDimension = new Dimension(prevWidth,prevHeight);
	    Dimension nextDimension = new Dimension(nextWidth, nextHeight);
	    kernelNextButton.setPreferredSize(nextDimension);
	    kernelNextButton.setPreferredSize(nextDimension);
	    resetButton.setPreferredSize(nextDimension);
	    redoButton.setPreferredSize(nextDimension);
	    undoButton.setPreferredSize(prevDimension);
	    prevPic.setPreferredSize(prevDimension);
	    nextPic.setPreferredSize(nextDimension);
	    addFile.setPreferredSize(nextDimension);
	    savePic.setPreferredSize(nextDimension);
	    delPic.setPreferredSize(nextDimension);
	    //kernelPrevButton button press
	    kernelPrevButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	          kernelIndex-=2;
	          if (kernelIndex < 3)
	            kernelIndex = 3;
	          displayPixelInformation(colIndex,rowIndex);
	          allImages.get(imageNames.get(currentIndex)+":c").push(Blur.inverse_color());
	          counts.set(currentIndex, counts.get(currentIndex)+1);
	          setImage(allImages.get(imageNames.get(currentIndex)+":c").peek());
	        }
	     });
	  //kernelNextButton button press
	    kernelNextButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	          kernelIndex+=2;
	          if (kernelIndex > 99)
	            kernelIndex = 99;
	          if(Blur.coords[0]>allImages.get(imageNames.get(currentIndex)+":c").peek().getWidth()-kernelIndex/2 ||
	        	Blur.coords[0]<kernelIndex/2 || Blur.coords[1]<kernelIndex/2 ||
	        	Blur.coords[1]>allImages.get(imageNames.get(currentIndex)+":c").peek().getHeight()-kernelIndex/2) {
	        	  Blur.coords[0] = -1;
	        	  Blur.coords[1] = -1;
	          }
	          displayPixelInformation(colIndex,rowIndex);
	          allImages.get(imageNames.get(currentIndex)+":c").push(Blur.inverse_color());
	          counts.set(currentIndex, counts.get(currentIndex)+1);
	          setImage(allImages.get(imageNames.get(currentIndex)+":c").peek());
	        }
	     });
	  //resetButton button press
	    resetButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	        	if(counts.get(currentIndex)==1) {
	        		allImages.get(imageNames.get(currentIndex)+":c").pop();
	        		counts.set(currentIndex, 0);
	        	}
	            Picture resetPicture = new Picture("User_Images/"+imageNames.get(currentIndex));
	            allImages.get(imageNames.get(currentIndex)+":c").push(resetPicture);
	            setImage(allImages.get(imageNames.get(currentIndex)+":c").peek());	
	         }
	    });
	   //redoButton Button press
	    redoButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	        	if (!allImages.get(imageNames.get(currentIndex)+":r").empty()) {
	        		allImages.get(imageNames.get(currentIndex)+":c").push(allImages.get(imageNames.get(currentIndex)+":r").pop());
	            } 
	            setImage(allImages.get(imageNames.get(currentIndex)+":c").peek());
	        }	
	     });
	    //undoButton Button press
	    undoButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	        		if(counts.get(currentIndex)==1) {
	        			allImages.get(imageNames.get(currentIndex)+":c").pop();
	        			counts.set(currentIndex, 0);
	        		}
	            	if (allImages.get(imageNames.get(currentIndex)+":c").size() > 1) {
	            		allImages.get(imageNames.get(currentIndex)+":r").push(allImages.get(imageNames.get(currentIndex)+":c").pop());
	                	if (allImages.get(imageNames.get(currentIndex)+":c").empty()) {
	                		allImages.get(imageNames.get(currentIndex)+":c").push(allImages.get(imageNames.get(currentIndex)+":r").pop());
	                	}
	            	} 
	            	setImage(allImages.get(imageNames.get(currentIndex)+":c").peek()); 	
	          }
	     });
	    //prevPic Button press
	    prevPic.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	        	if(currentIndex==0) {
	        		currentIndex = imageNames.size()-1;
	        	} else {
	        		currentIndex--;
	        	}
	        	setImage(allImages.get(imageNames.get(currentIndex)+":c").peek());
	        	displayPixelInformation(colIndex,rowIndex);
	        }});
	    //nextPic Button press
	    nextPic.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	        	if(currentIndex==imageNames.size()-1) {
	        		currentIndex = 0;
	        	} else {
	        		currentIndex++;
	        	}
	        	setImage(allImages.get(imageNames.get(currentIndex)+":c").peek());
	        	displayPixelInformation(colIndex,rowIndex);
	        }});
	    //addFile Button
	    addFile.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	        	JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
	        	File folder = new File("/Users/daeyong/git/Blur/Picture_Project/User_Images/njfedsfbhabdbi1322312.png");
	        	j.setCurrentDirectory(folder);
	        	int selection = j.showSaveDialog(null);
	        	if  (selection == JFileChooser.APPROVE_OPTION) {
	        		File save = j.getSelectedFile();
	        		Picture temporaryPic = new Picture("User_Images/"+imageNames.get(currentIndex));
	        		BufferedImage in = temporaryPic.getBufferedImage();
					try {
						in = ImageIO.read(save);
					} catch (IOException e1) {
					}
	        		try {
	        			ImageIO.write(in,"png",folder);
					} catch (IOException e) {
					}
	        		String imageName = save.getName();
	        		File temp = new File("/Users/daeyong/git/Blur/Picture_Project/User_Images/" + imageName);
	        		folder.renameTo(temp);
	        		imageNames.add(imageName);
	        		counts.add(0);
	        		Stack<Picture> c = new Stack<Picture>();
	        		c.push(new Picture("User_Images/"+imageName));
	        		Stack<Picture> r = new Stack<Picture>();
	        		allImages.put(imageName+":c", c);
	        		allImages.put(imageName+":r", r);
	        		
	        	}
	        }
	     });
	    //Delete Button press
	    delPic.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	        	File folderOfImages = new File("/Users/daeyong/git/Blur/Picture_Project/User_Images/");
	        	if (!basePic.equals(imageNames.get(currentIndex))) {
					File theCurrentFile = new File(folderOfImages.getPath(),imageNames.get(currentIndex));
					theCurrentFile.delete();
					allImages.remove(imageNames.get(currentIndex)+":c");
					allImages.remove(imageNames.get(currentIndex)+":r");
					imageNames.remove(currentIndex);
					currentIndex--;
		        	if(currentIndex==-1) {
		        		currentIndex = imageNames.size();
		        	}
		        	setImage(allImages.get(imageNames.get(currentIndex)+":c").peek());
		        	displayPixelInformation(colIndex,rowIndex);
				}
	        }});
	    //Save Picture
	    savePic.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	        	JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
	   	     		BufferedImage image = null;
	   	     		File folder = null;
	   	     		File f = null;
	   	        	int selection = j.showSaveDialog(null);
	   	        	if (selection == j.APPROVE_OPTION) {
	   	        		try {
	   	        			Picture temporaryPic = allImages.get(imageNames.get(currentIndex)+":c").peek();
	   		        		BufferedImage in = temporaryPic.getBufferedImage();
	   	        			folder = j.getSelectedFile(); //dedicates the position of the new file
	   	        			ImageIO.write(in, "png", folder); //writes the image info to this new file
	   	        		} catch (IOException e) {
	   	        		}	
	   	        	}
	        }});
	  }
	public void displayPixelInformation(String xString, String yString)
	  {
		super.displayPixelInformation(xString, yString);
	  }
	public JPanel createLocationPanel(Font labelFont) {
		// create a location panel
	    JPanel locationPanel = new JPanel();
	    locationPanel.setLayout(new FlowLayout());
	    Box hBox = Box.createHorizontalBox();
	    
	    // create the labels
	    rowLabel = new JLabel("Row: ");
	    colLabel = new JLabel("Column: ");
	    kernelLabel = new JLabel("Kernel Size: ");
	    resetLabel = new JLabel("Reset Button: ");
	    redoLabel = new JLabel("Redo: ");
	    undoLabel = new JLabel("Undo: ");
	    picLabel = new JLabel("Switch Pictures: ");
	    addFileLabel = new JLabel("Add File: ");
	    savePicLabel = new JLabel("Download Picture: ");
	    delPicLabel = new JLabel("Delete Picture: ");
	    // create the text fields
	    colValue = new JTextField(Integer.toString(colIndex + numberBase),6);
	    colValue.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        displayPixelInformation(colValue.getText(),rowValue.getText());
	      }
	    });
	    rowValue = new JTextField(Integer.toString(rowIndex + numberBase),6);
	    rowValue.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        displayPixelInformation(colValue.getText(),rowValue.getText());
	      }
	    });
	    kernelValue = new JTextField(Integer.toString(kernelIndex + numberBase),6);
	    kernelValue.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        displayPixelInformation(colValue.getText(),rowValue.getText());
	      }
	    });
	    imageNames.add(Blur.basePic);
	    picName = new JTextField(imageNames.get(currentIndex));
	    picName.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        displayPixelInformation(colValue.getText(),rowValue.getText());
	      }
	    });
	    
	    
	    
	    // set up the next and previous buttons
	    setUpNextAndPreviousButtons();
	    
	    // set up the font for the labels
	    colLabel.setFont(labelFont);
	    rowLabel.setFont(labelFont);
	    colValue.setFont(labelFont);
	    rowValue.setFont(labelFont);
	    
	    // add the items to the vertical box and the box to the panel
	    hBox.add(picLabel);
	    hBox.add(prevPic);
	    hBox.add(picName);
	    hBox.add(nextPic);
	    hBox.add(Box.createHorizontalStrut(10));
	    hBox.add(kernelLabel);
	    hBox.add(kernelPrevButton);
	    hBox.add(kernelValue);
	    hBox.add(kernelNextButton);
	    hBox.add(Box.createHorizontalStrut(10));
	    hBox.add(resetLabel);
	    hBox.add(resetButton);
	    hBox.add(Box.createHorizontalStrut(10));
	    hBox.add(undoLabel);
	    hBox.add(undoButton);
	    hBox.add(Box.createHorizontalStrut(10));
	    hBox.add(redoLabel);
	    hBox.add(redoButton);
	    hBox.add(Box.createHorizontalStrut(10));
	    hBox.add(addFileLabel);
	    hBox.add(addFile);
	    hBox.add(Box.createHorizontalStrut(10));
	    hBox.add(savePicLabel);
	    hBox.add(savePic);
	    hBox.add(Box.createHorizontalStrut(10));
	    hBox.add(delPicLabel);
	    hBox.add(delPic);
	    locationPanel.add(hBox);
	    hBox.add(Box.createHorizontalGlue());
	    
	    return locationPanel;
	  }
	
	private void displayPixelInformation(int pictureX, int pictureY)
	  {
	    // check that this x and y are in range
	    if (isLocationInPicture(pictureX, pictureY))
	    {
	      // save the current x and y index
	      colIndex = pictureX;
	      rowIndex = pictureY;
	      
	      // get the pixel at the x and y
	      Pixel pixel = new Pixel(picture,colIndex,rowIndex);
	      
	      // set the values based on the pixel
	      colValue.setText(Integer.toString(colIndex  + numberBase));
	      rowValue.setText(Integer.toString(rowIndex + numberBase));
	      rValue.setText("R: " + pixel.getRed());
	      gValue.setText("G: " + pixel.getGreen());
	      bValue.setText("B: " + pixel.getBlue());
	      colorPanel.setBackground(new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue()));
	      
	      // Call mouseClickedAction() for the subclasses
	      mouseClickedAction(picture, pixel);
	    } 
	    else
	    {
	      clearInformation();
	    }
	    
	    // notify the image display of the current x and y
	    imageDisplay.setCurrentX((int) (colIndex * zoomFactor));
	    imageDisplay.setCurrentY((int) (rowIndex * zoomFactor));
	    kernelValue.setText(Integer.toString(kernelIndex));
	    picName.setText(imageNames.get(currentIndex));
	  }
	

	private void displayMain() {
		int h = pict1.getHeight();
		int w = pict1.getWidth();
		disp = new Picture(w+100, h+100);
		graphics = disp.createGraphics();
		graphics.setColor(Color.black);
		graphics.setFont(new Font("Times", Font.BOLD, 26));
		graphics.drawString("Click on two pixels to blur the area in between!", 60, h+100);
		graphics.drawImage(pict1.getBufferedImage(), 0, 0, this);
		setImage(disp);
		// setImage() changes the title each time it's called
		setTitle("Blur Project");	
	}
	public static Picture inverse_color() {
		int red;
		int green;
		int blue;
		if(counts.get(currentIndex)==1) {
			allImages.get(imageNames.get(currentIndex)+":c").pop();
			counts.set(currentIndex,0);
		}
		Picture newPict = new Picture(allImages.get(imageNames.get(currentIndex)+":c").peek());
		int w = newPict.getWidth();
		int h = newPict.getHeight();
		for(int i = 0; i < h; i++) {
			for(int j = 0; j < w; j++) {
				if(((j==kernelIndex/2||j==w-kernelIndex/2) && (i>kernelIndex/2&&i<h-kernelIndex/2)) || ((i==kernelIndex/2||i==h-kernelIndex/2) && (j>kernelIndex/2&&j<w-kernelIndex/2))) {
					Pixel pix=newPict.getPixel(j, i);
					red=255-pix.getRed();
					green=255-pix.getGreen();
					blue=255-pix.getBlue();
					newPict.getPixel(j, i).setColor(new Color((int)red, (int)green, (int)blue));
				}
			}
		}
		return newPict;
		
	}
	
	public void gaussian_blur(int[] coords) {
		int up = -1;
		int down = -1;
		int left = -1;
		int right = -1;
		double red = 0;
		double green = 0;
		double blue = 0;
		double sum = 0;
		if(coords[3]>coords[1]) {
			up = 1;
			down = 3;
		} else {
			up = 3;
			down = 1;
		}
		if(coords[2]>coords[0]) {
			right = 2;
			left = 0;
		} else {
			right = 0;
			left = 2;
		}
		if(counts.get(currentIndex)==1) {
			allImages.get(imageNames.get(currentIndex)+":c").pop();
			counts.set(currentIndex,0);
		}
		
		
		double[][] weights = new double[kernelIndex][kernelIndex];
		Color[][] blurredPortion = new Color[coords[down]-coords[up]+1][coords[right]-coords[left]+1];
		for(int i = coords[up]; i < coords[down]; i++) {
			for(int j = coords[left]; j < coords[right]; j++) {
				for(int row = 0; row < kernelIndex; row++) {
					for(int col = 0; col < kernelIndex; col++) {
						double mult = (1/(2*Math.PI*Math.pow(10,2))*Math.exp(-(Math.pow(col-kernelIndex/2, 2) + Math.pow(row-kernelIndex/2, 2))/(2*Math.pow(10, 2))));
						weights[row][col] = mult;
						sum+=mult;
					}
				}
				for(int row = 0; row < kernelIndex; row++) {
					for(int col = 0; col < kernelIndex; col++) {
						weights[row][col] /= sum;
					}
				}
				for(int row = -(kernelIndex-1)/2; row < ((kernelIndex-1)/2)+1; row++) {
					for(int col = -(kernelIndex-1)/2; col < ((kernelIndex-1)/2)+1; col++) {
						Pixel pix = new Pixel(allImages.get(imageNames.get(currentIndex)+":c").peek(), j+col, i+row);
						double weight = weights[row+(kernelIndex-1)/2][col+(kernelIndex-1)/2];
						red+=(pix.getRed()*weight);
						green+=(pix.getGreen()*weight);
						blue+=(pix.getBlue()*weight);
					}
				}
				Color newColor = new Color((int)red, (int)green, (int)blue);
				blurredPortion[i-coords[up]][j-coords[left]] = newColor;
				red=0;
				blue=0;
				green=0;
				sum=0;
			}
		}
		Picture newPict = new Picture(allImages.get(imageNames.get(currentIndex)+":c").peek());
		for(int i = coords[up]; i < coords[down]; i++) {
			for(int j = coords[left]; j < coords[right]; j++) {
				newPict.getPixel(j, i).setColor(blurredPortion[i-coords[up]][j-coords[left]]);
			}
		}
		setImage(newPict);
		allImages.get(imageNames.get(currentIndex)+":c").push(newPict);		
	}
	public void mouseClickedAction(DigitalPicture pic, Pixel pix) {
		
		pic = new Picture("User_Images/"+imageNames.get(currentIndex));

		int y = pix.getY();
		int x = pix.getX();
		int w = pic.getWidth();
		int h = pic.getHeight();
		if(!((x<kernelIndex/2||x>w-kernelIndex/2) || (y<kernelIndex/2||y>h-kernelIndex/2))) {
			if(coords[0]==-1) {
				coords[0] = x;
				coords[1] = y;
				allImages.get(imageNames.get(currentIndex)+":c").push(Blur.inverse_color());
				counts.set(currentIndex, counts.get(currentIndex)+1);
				setImage(allImages.get(imageNames.get(currentIndex)+":c").peek());
			} else if (coords[2]==-1){
				coords[2] = x;
				coords[3] = y;
					gaussian_blur(coords);
			} else {
				for(int i = 0; i < 4; i++) {
					coords[i] = -1;
				}
				coords[0] = x;
				coords[1] = y;
				allImages.get(imageNames.get(currentIndex)+":c").push(Blur.inverse_color());
				counts.set(currentIndex, counts.get(currentIndex)+1);
				setImage(allImages.get(imageNames.get(currentIndex)+":c").peek());
			}
		} else {
			graphics.drawString("Clicked out of bounds!", 60, h+100);
		}
	}
	
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3,
			int arg4, int arg5) {
		return false;
	}
	
	public static void main(String args[]){
		Blur test = new Blur();
	}


}