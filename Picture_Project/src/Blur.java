import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.lang.Math;
import java.util.Stack;
public class Blur extends FlexiblePictureExplorer implements ImageObserver {
	public static int[] coords = {-1,-1,-1,-1};
	public static String thePicture = "slideshow/loveIsWar.png";
	public static String thePicture2 = "slideshow/SumichanByeBye.jpg";
	public static String thePicture3 = "Pictures/flower1.jpg";
	public static Picture pict = new Picture(thePicture);
	public static Picture pict2 = new Picture(thePicture2);
	public static Picture pict3 = new Picture(thePicture3);
	public static Stack<Picture> changes = new Stack<Picture>();
	public static Stack<Picture> retractions = new Stack<Picture>();
	public static Stack<Picture> changes2 = new Stack<Picture>();
	public static Stack<Picture> retractions2 = new Stack<Picture>();
	public static Stack<Picture> changes3 = new Stack<Picture>();
	public static Stack<Picture> retractions3 = new Stack<Picture>();
	public static String pictureName = "loveIsWar";
	public static String pictureName2 = "SumichanByeBye";
	public static String pictureName3 = "flower1";
	public static Graphics2D graphics;
	public static Picture disp;
	public static int picValue = 0;
	
	public Blur(){
		
		super(new Picture(1280,720));
		changes.push(pict);
		changes2.push(pict2);
		changes3.push(pict3);
		displayMain();
	}
	private void displayMain() {
		if (picValue ==0) {
			int h = pict.getHeight();
			int w = pict.getWidth();
			disp = new Picture(w+100, h+100);
			graphics = disp.createGraphics();
			graphics.setColor(Color.black);
			graphics.setFont(new Font("Times", Font.BOLD, 26));
			graphics.drawString("Click on two pixels to blur the area in between!", 60, h+100);
			graphics.drawImage(pict.getBufferedImage(), 0, 0, this);
			setImage(disp);
			// setImage() changes the title each time it's called
			setTitle("Blur Project");
		} else if (picValue ==1) {
			int h = pict2.getHeight();
			int w = pict2.getWidth();
			disp = new Picture(w+100, h+100);
			graphics = disp.createGraphics();
			graphics.setColor(Color.black);
			graphics.setFont(new Font("Times", Font.BOLD, 26));
			graphics.drawString("Click on two pixels to blur the area in between!", 60, h+100);
			graphics.drawImage(pict2.getBufferedImage(), 0, 0, this);
			setImage(disp);
			// setImage() changes the title each time it's called
			setTitle("Blur Project");
		} else if (picValue == 2) {
			int h = pict3.getHeight();
			int w = pict3.getWidth();
			disp = new Picture(w+100, h+100);
			graphics = disp.createGraphics();
			graphics.setColor(Color.black);
			graphics.setFont(new Font("Times", Font.BOLD, 26));
			graphics.drawString("Click on two pixels to blur the area in between!", 60, h+100);
			graphics.drawImage(pict3.getBufferedImage(), 0, 0, this);
			setImage(disp);
			// setImage() changes the title each time it's called
			setTitle("Blur Project");
		}
		
	}
	public static Picture inverse_color() {
		int red;
		int green;
		int blue;
		if (picValue ==0) {
			if(count==1) {
				changes.pop();
				count=0;
			}
			Picture newPict = new Picture(changes.peek());
			int w = pict.getWidth();
			int h = pict.getHeight();
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
		} else if (picValue ==1) {
			if(count==1) {
				changes2.pop();
				count=0;
			}
			Picture newPict = new Picture(changes2.peek());
			int w = pict2.getWidth();
			int h = pict2.getHeight();
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
		} else {
			if(count==1) {
				changes3.pop();
				count=0;
			}
			Picture newPict = new Picture(changes3.peek());
			int w = pict3.getWidth();
			int h = pict3.getHeight();
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
		if (picValue == 0) {
			if(count==1) {
				changes.pop();
				count=0;
			}
		} else if (picValue == 1) {
			if(count==1) {
				changes2.pop();
				count=0;
			}
		} else if (picValue == 2) {
			if(count==1) {
				changes3.pop();
				count=0;
			}
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
				if (picValue ==0) {
					for(int row = -(kernelIndex-1)/2; row < ((kernelIndex-1)/2)+1; row++) {
						for(int col = -(kernelIndex-1)/2; col < ((kernelIndex-1)/2)+1; col++) {
							Pixel pix = new Pixel(changes.peek(), j+col, i+row);
							double weight = weights[row+(kernelIndex-1)/2][col+(kernelIndex-1)/2];
							red+=(pix.getRed()*weight);
							green+=(pix.getGreen()*weight);
							blue+=(pix.getBlue()*weight);
						}
					}
				} else if(picValue ==1) {
					for(int row = -(kernelIndex-1)/2; row < ((kernelIndex-1)/2)+1; row++) {
						for(int col = -(kernelIndex-1)/2; col < ((kernelIndex-1)/2)+1; col++) {
							Pixel pix = new Pixel(changes2.peek(), j+col, i+row);
							double weight = weights[row+(kernelIndex-1)/2][col+(kernelIndex-1)/2];
							red+=(pix.getRed()*weight);
							green+=(pix.getGreen()*weight);
							blue+=(pix.getBlue()*weight);
						}
					}
				} else {
					for(int row = -(kernelIndex-1)/2; row < ((kernelIndex-1)/2)+1; row++) {
						for(int col = -(kernelIndex-1)/2; col < ((kernelIndex-1)/2)+1; col++) {
							Pixel pix = new Pixel(changes3.peek(), j+col, i+row);
							double weight = weights[row+(kernelIndex-1)/2][col+(kernelIndex-1)/2];
							red+=(pix.getRed()*weight);
							green+=(pix.getGreen()*weight);
							blue+=(pix.getBlue()*weight);
						}
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
		if (picValue == 0) {
			Picture newPict = new Picture(changes.peek());
			for(int i = coords[up]; i < coords[down]; i++) {
				for(int j = coords[left]; j < coords[right]; j++) {
					newPict.getPixel(j, i).setColor(blurredPortion[i-coords[up]][j-coords[left]]);
				}
			}
			setImage(newPict);
			changes.push(newPict);	
		} else if (picValue ==1) {
			Picture newPict = new Picture(changes2.peek());
			for(int i = coords[up]; i < coords[down]; i++) {
				for(int j = coords[left]; j < coords[right]; j++) {
					newPict.getPixel(j, i).setColor(blurredPortion[i-coords[up]][j-coords[left]]);
				}
			}
			setImage(newPict);
			changes2.push(newPict);
		} else if (picValue ==2) {
			Picture newPict = new Picture(changes3.peek());
			for(int i = coords[up]; i < coords[down]; i++) {
				for(int j = coords[left]; j < coords[right]; j++) {
					newPict.getPixel(j, i).setColor(blurredPortion[i-coords[up]][j-coords[left]]);
				}
			}
			setImage(newPict);
			changes3.push(newPict);
		}
		
	}

	public void mouseClickedAction(DigitalPicture testPic, Pixel pix) {
		int y = pix.getY();
		int x = pix.getX();
		int w = testPic.getWidth();
		int h = testPic.getHeight();
		if (picValue ==0) {
			if(!((x<kernelIndex/2||x>w-kernelIndex/2) || (y<kernelIndex/2||y>h-kernelIndex/2))) {
				if(coords[0]==-1) {
					coords[0] = x;
					coords[1] = y;
					Blur.changes.push(Blur.inverse_color());
			        count++;
					setImage(changes.peek());
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
					Blur.changes.push(Blur.inverse_color());
			        count++;
					setImage(changes.peek());
				}
			} else {
				graphics.drawString("Clicked out of bounds!", 60, h+100);
			}
		} else if (picValue ==1) {
			if(!((x<kernelIndex/2||x>w-kernelIndex/2) || (y<kernelIndex/2||y>h-kernelIndex/2))) {
				if(coords[0]==-1) {
					coords[0] = x;
					coords[1] = y;
					Blur.changes2.push(Blur.inverse_color());
			        count++;
					setImage(changes2.peek());
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
					Blur.changes2.push(Blur.inverse_color());
			        count++;
					setImage(changes2.peek());
				}
			} else {
				graphics.drawString("Clicked out of bounds!", 60, h+100);
			}
		} else {
			if(!((x<kernelIndex/2||x>w-kernelIndex/2) || (y<kernelIndex/2||y>h-kernelIndex/2))) {
				if(coords[0]==-1) {
					coords[0] = x;
					coords[1] = y;
					Blur.changes3.push(Blur.inverse_color());
			        count++;
					setImage(changes3.peek());
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
					Blur.changes3.push(Blur.inverse_color());
			        count++;
					setImage(changes3.peek());
				}
			} else {
				graphics.drawString("Clicked out of bounds!", 60, h+100);
			}
		}
		
	}
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3,
			int arg4, int arg5) {
		return false;
	}
	public static void main (String args[]){
		new Blur();
	}


}