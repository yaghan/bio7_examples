import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.Roi;
import java.awt.Polygon;

/*
This example draws points on the Points panel along 
the ImageJ selection coordinates.
Open a picture and select an area in ImageJ before you start!*/

				
Individual[] plantpoint; 



public void ecomain(){
	PointPanel.delete();
	Roi roi = WindowManager.getCurrentImage().getRoi();
				int[] roix = null;
				int[] roiy = null;
				
					Polygon po = roi.getPolygon();
					roix = po.xpoints;
					roiy = po.ypoints;
	               plantpoint= new Individual[roix.length];	
	

for (int z = 0; z < roix.length; z++) {
		
    plantpoint[z]=new Individual(roix[z],roiy[z],1,5,255.0f);
	
    }

    PointPanel.setIndividual(plantpoint);
}