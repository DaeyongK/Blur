import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;


public class MainScreen extends FlexiblePictureExplorer implements ImageObserver {
	private final String path = "slideshow/";
	private final String[][][] images = new String[][][] {{{"childhood.jpg","PrincetonMap.png","Nassau Hall.jpg","Amtrak.jpg","Plane.jpg","Jane.jpg","TravelCollage.jpg","Cat.jpg","watermelon-touramline.jpg","Littlebrook.PNG","Princeton-Day-School.PNG","Bucks-Rock.jpg","graduation.jpg"},
															{"school.jpg","Brown.jpg","formula.jpg","IBM.jpg","StonyBrook.jpg","Generic-Diploma.jpg","Temp-ui.jpg"},
															{"HubbleClipArt.jpg","maryland.png","STSI.jpg","hubble.jpg","JHU.jpg","Schedule.jpg","nebula.png","auditorium.jpg"}},
															{{"family.jpg","wedding.png","timonium.png","Matt.jpg","Rachel.jpg","Ellicott.PNG","Guinea Pigs.jpg","centennial.png","Columbia.PNG"},
															{"teacher.jpg","chsroom.jpg","student-crowd.png","returning-student.png","nutrition.png","homework.png","flashcards.png","student-at-computer.png","celebration.png","centennial-graduation.png"},
															{"Gender-Symbol.jpg","Trans-Pride.jpg","boy-and-girl-playing.jpg","Gender-Dysphoria.jpg","brain.jpg","Transition.jpg","career-collage.png","Discrimination.png","famous-transgender-people.png"}},
															{{"ClipArtEarth.jpg","planetEarth.jpg","GSA.jpg","OWL.jpg","Trans-Lifeline.png","support-group.png","PFLAG-Picture.PNG","pastoral-care.jpg"},
															{"Chalice.png","Menorah.png","UUA-Logo.png","Sunburst.png","Seven-UU-Principles.png","Religions-Collage.png","UUCC.png"},
															{"Boardgame.png","Power-Grid.png","Library.png","Writing.png","Sports.png","Aerial-Silks.png","Trail.png"}}};

	private int row;
	private int col;
	private int depth;
	private boolean main;
	public MainScreen(){
		super(new Picture(700,600));
		displayMain();
		row = 0; 
		col = 0;
		depth = 0;
	}
	private void displayMain() {
		main = true;
		Picture disp = new Picture(700, 600);
		Graphics2D graphics = disp.createGraphics();
		graphics.setColor(Color.black);
		graphics.setFont(new Font("Times", Font.BOLD, 26));
		graphics.drawString("Choose a catagory to learn more about me!", 60, 650);
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				Picture pict;
				pict = new Picture(path + images[x][y][0]);
				graphics.drawImage(pict.getBufferedImage(), 200*y, 200*x, this);
			}
		}

		setImage(disp);
		// setImage() changes the title each time it's called
		setTitle("All About Me");
	}
	private void displayPhoto(int row, int col, int depth){
		main = false;
		Picture disp = new Picture(700,600);
		disp.setAllPixelsToAColor(Color.black);
		Graphics2D graphics = disp.createGraphics();
		//System.out.println("row:" + row + " col:" + col + " depth:" + depth);
		Picture photo = new Picture(path + images[row][col][depth]);
		int x = (600 - photo.getWidth())/2;
		int y = (600 - photo.getHeight())/2;
		graphics.drawImage(photo.getBufferedImage(), x, y, this);
		Picture arrows = new Picture(path + "arrows.png");
		Picture left = new Picture(path + "leftOnly.png");
		Picture right = new Picture(path + "rightOnly.png");
		if(depth == 1)
			graphics.drawImage(right.getBufferedImage(),0,600,this);
		else if(depth == images[row][col].length-1)
			graphics.drawImage(left.getBufferedImage(),0,600,this);
		else
			graphics.drawImage(arrows.getBufferedImage(),0,600,this);
		setImage(disp);
		setTitle("All About Me");
	}

	public void mouseClickedAction(DigitalPicture pict, Pixel pix) {
		if(main && pix.getY()<600){
			row = (int) pix.getY()/200;
			col = (int) pix.getX()/200;
			depth = 1;
			displayPhoto(row,col,depth);
		}
		if(!main && pix.getY()>600){
			if(pix.getX()/200 == 0 && depth>1){
				depth--;
				displayPhoto(row,col,depth);
			}
			else if(pix.getX()/200 == 1)
				displayMain();
			else if(pix.getX()/200 == 2 && depth < images[row][col].length-1){
				depth++;
				displayPhoto(row,col,depth);
			}
		}
	}
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3,
			int arg4, int arg5) {
		return false;
	}
	public static void main (String args[]){
		new MainScreen();
	}


}
