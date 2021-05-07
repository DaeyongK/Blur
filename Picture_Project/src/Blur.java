
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;


public class Blur extends FlexiblePictureExplorer implements ImageObserver {
	private int row;
	private int col;
	private int depth;
	private boolean main;
	private int[] coords = {-1,-1,-1,-1};
	public Blur(){
		super(new Picture(700,600));
		displayMain();
		row = 0; 
		col = 0;
		depth = 0;
	}
	private void displayMain() {
		Picture disp = new Picture(700, 600);
		Graphics2D graphics = disp.createGraphics();
		graphics.setColor(Color.black);
		graphics.setFont(new Font("Times", Font.BOLD, 26));
		graphics.drawString("Click on two pixels to blur the area in between!", 30, 650);
		Picture pict;
		pict = new Picture("slideshow/Cat.jpg");
		graphics.drawImage(pict.getBufferedImage(), 0, 0, this);
		setImage(disp);
		// setImage() changes the title each time it's called
		setTitle("All About Me");
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
		} 
//		if(main && pix.getY()<600){
//			row = (int) pix.getY()/200;
//			col = (int) pix.getX()/200;
//			depth = 1;
//			displayPhoto(row,col,depth);
//		}
//		if(!main && pix.getY()>600){
//			if(pix.getX()/200 == 0 && depth>1){
//				depth--;
//				displayPhoto(row,col,depth);
//			}
//			else if(pix.getX()/200 == 1)
//				displayMain();
//			else if(pix.getX()/200 == 2 && depth < images[row][col].length-1){
//				depth++;
//				displayPhoto(row,col,depth);
//			}
//		}
	}
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3,
			int arg4, int arg5) {
		return false;
	}
	public static void main (String args[]){
		new Blur();
	}


}