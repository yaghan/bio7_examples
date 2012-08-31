import java.awt.Graphics2D;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.text.*;
import java.awt.geom.*;
import com.sun.opengl.util.j2d.*;
import gov.nasa.worldwind.*;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.render.*;
import com.eco.bio7.worldwind.*;
import gov.nasa.worldwind.render.DrawContext;
/*
This example demonstrates a panel overlay on top of the WorldWind view!
Add a dynamic layer to see the result!
The WorldWind perspective has to be active in Bio7 1.4!
*/



/*The WorldWind perspective has to be active in Bio7 1.4!*/

public Object sc = Ww.getWwd().getSceneController();
public BasicSceneController bsc = (BasicSceneController)sc;      
public DrawContext dc=bsc.getDrawContext();
 
public Overlay infoOverlay = new Overlay(dc.getGLDrawable());
/*We create to alpha values for demonstration purposes!*/
public AlphaComposite ac1= AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                           0.5f);
public AlphaComposite ac2= AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                           1.0f);

public int height=100;
public int width=200;
public int counter=0;
public Font font = new Font("SansSerif", Font.BOLD, 14);
public String fileroot;
public String f;
public Image image;

public void setup(GL gl,GLU glu,GLUT glut){

}
public void ecomain(GL gl,GLU glu,GLUT glut){
	if(sc!=null){
	showOverlayInfo(gl);
	}

}
private void showOverlayInfo(GL gl) {

	Graphics2D g2d = infoOverlay.createGraphics();
	g2d.setComposite(ac1);
	g2d.setColor(new Color(0, 0, 0));
	g2d.fillRect(0, 0, width, height);
	g2d.setColor(Color.WHITE);
	g2d.drawLine(0, 0, width, height);
	fileroot = FileRoot.getFileRoot();
	/*Create the path from the root of the workspace!*/
	f = fileroot + "/Bio7_Examples/OpenGL-Snippets/TextAndOverlays/tree.gif";

	g2d.setComposite(ac2);

	/*Load the image from a file system location!*/
	if (image == null) {
		try {
			image = ImageIO.read(new File(f));
		} catch (IOException e1) {

			e1.printStackTrace();
		}
	}
	/*Draw an image!*/
	g2d.drawImage(image, 0, 100, null);

	/*
	Draw the information with the graphics context!
	 */
	g2d.setColor(Color.WHITE);
	g2d.setFont(font);
	g2d.drawString("Frames counted: " + counter, 5, 15);
	g2d.drawString("Bio7", 5, 33);
    counter++;
	/*
	Mark only the needed region for a repaint dirty (counter) for efficiency!!!!!!
	 */
	infoOverlay.markDirty(0, 0, 200, 50);
	infoOverlay.drawAll();
	g2d.dispose();
	Ww.getWwd().redraw();
}