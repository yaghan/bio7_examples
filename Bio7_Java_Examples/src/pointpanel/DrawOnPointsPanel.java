package pointpanel;

import java.awt.Graphics2D;
import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import com.eco.bio7.batch.FileRoot;
import com.eco.bio7.image.Individual;
import com.eco.bio7.image.PointPanel;

/*
 You can now draw customized on the Points panel with the draw(Graphics2D g2d) method
 which gives you an access to the Graphics2D context of the panel!
 Compile and execute the setup method to enable or disable the image example.
 */
public class DrawOnPointsPanel extends com.eco.bio7.compile.Model {

	public String fileroot;
	public String f;
	public Image image;

	Individual[] plantpoint = new Individual[1];
	boolean paint = false;

	public void setup() {
		/*
		 * Use the setup to enable or disable drawing of a pic on the Points
		 * panel!
		 */
		paint = !paint;
		PointPanel.doPaint();
	}

	public void run() {

	}

	/*
	 * This method has to be available to draw customized on the Points panel!
	 */
	public void draw(Graphics2D g2d) {
		/* Only if the boolean is true a pic will be drawn! */
		if (paint) {
			fileroot = FileRoot.getFileRoot();
			/* Create the path from the root of the workspace! */
			f = fileroot + "/Bio7_Java_Examples/src/pointpanel/tree.gif";

			/* Load the image from a file system location! */
			if (image == null) {
				try {
					image = ImageIO.read(new File(f));
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}
			/* Draw an image! */
			g2d.drawImage(image, 0, 100, null);
		}

	}
}