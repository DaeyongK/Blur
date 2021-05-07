
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
	private void displayPhoto(int row, int col, int depth){
//		main = false;
//		Picture disp = new Picture(700,600);
//		disp.setAllPixelsToAColor(Color.black);
//		Graphics2D graphics = disp.createGraphics();
//		//System.out.println("row:" + row + " col:" + col + " depth:" + depth);
//		Picture photo = new Picture(path + images[row][col][depth]);
//		int x = (600 - photo.getWidth())/2;
//		int y = (600 - photo.getHeight())/2;
//		graphics.drawImage(photo.getBufferedImage(), x, y, this);
//		Picture arrows = new Picture(path + "arrows.png");
//		Picture left = new Picture(path + "leftOnly.png");
//		Picture right = new Picture(path + "rightOnly.png");
//		if(depth == 1)
//			graphics.drawImage(right.getBufferedImage(),0,600,this);
//		else if(depth == images[row][col].length-1)
//			graphics.drawImage(left.getBufferedImage(),0,600,this);
//		else
//			graphics.drawImage(arrows.getBufferedImage(),0,600,this);
//		setImage(disp);
//		setTitle("All About Me");
	}

	public void mouseClickedAction(DigitalPicture pict, Pixel pix) {
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