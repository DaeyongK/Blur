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
	private int[] coords = {-1,-1,-1,-1};
	public static String thePicture = "slideshow/loveIsWar.png";
	private Picture pict = new Picture(thePicture);
	public static Stack<Picture> changes = new Stack<Picture>();
	public static Stack<Picture> retractions = new Stack<Picture>();
	public Blur(){
		super(new Picture(1280,720));
		changes.push(pict);
		displayMain();
	}
	private void displayMain() {
		Picture disp = new Picture(1280,720);
		Graphics2D graphics = disp.createGraphics();
		graphics.setColor(Color.black);
		graphics.setFont(new Font("Times", Font.BOLD, 26));
		graphics.drawString("Click on two pixels to blur the area in between!", 30, 650);
		graphics.drawImage(pict.getBufferedImage(), 0, 0, this);
		setImage(disp);
		// setImage() changes the title each time it's called
		setTitle("Blur Project");
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
						Pixel pix = new Pixel(pict, j+col, i+row);
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
		
		Picture newPict = new Picture(changes.peek());
		for(int i = coords[up]; i < coords[down]; i++) {
			for(int j = coords[left]; j < coords[right]; j++) {
				newPict.getPixel(j, i).setColor(blurredPortion[i-coords[up]][j-coords[left]]);
			}
		}
		setImage(newPict);
		changes.push(newPict);
	}

	public void mouseClickedAction(DigitalPicture pict, Pixel pix) {
		int y = pix.getY();
		int x = pix.getX();
		if(coords[0]==-1) {
			coords[0] = x;
			coords[1] = y;
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