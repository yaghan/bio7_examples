package pointpanel;

import ij.WindowManager;
import ij.gui.Roi;
import java.awt.Polygon;
import com.eco.bio7.image.Individual;
import com.eco.bio7.image.PointPanel;

/*
 This example draws points on the Points panel along 
 the ImageJ selection coordinates.
 Open a picture and select an area in ImageJ before you start!*/

public class Snippet8_DynamicWithIJSelection extends com.eco.bio7.compile.Model {

	Individual[] plantpoint;

	public void run() {
		PointPanel.delete();
		Roi roi = WindowManager.getCurrentImage().getRoi();
		int[] roix = null;
		int[] roiy = null;

		Polygon po = roi.getPolygon();
		roix = po.xpoints;
		roiy = po.ypoints;
		plantpoint = new Individual[roix.length];

		for (int z = 0; z < roix.length; z++) {

			plantpoint[z] = new Individual(roix[z], roiy[z], 1, 5, 255.0f);

		}

		PointPanel.setIndividual(plantpoint);
	}
}