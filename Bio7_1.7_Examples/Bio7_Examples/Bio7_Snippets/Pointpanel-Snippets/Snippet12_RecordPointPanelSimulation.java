import java.awt.*;
import java.io.*;
import ij.*;
import ij.gui.*;
import ij.process.*;
import ij.plugin.PlugIn;
import ij.io.Opener;
import javax.swing.SwingUtilities;
import ij.gui.StackWindow;

/*
This example demonstrates how to record a Points panel simulation to a stack in ImageJ.

Please invoke the setup method and start the main calculation thread!

In this example every calculation creates new Individual objects. 
It would be more effective to reuse existing objects (which can be created in the setup method).
To change the attributes of the Individual objects please look at the 
API for the 'get' and 'set' methods of the class.
*/


public ImageStack s;
public ImagePlus plus;
public ImageProcessor ip;
public ImagePlus imp;
public ImagePlus tempimp;
public ImageProcessor tempip;
public int w;
public int h;

Individual[] individual = new Individual[1000];

public void ecomain() {

	points();
	/*Repaint the panel*/
	PointPanel.doPaint();
	
	tempimp = ImageMethods.getPanelImage();

	tempip = tempimp.getProcessor();
	/*Add a new image to the stack!*/
	s.addSlice(null, tempip);

	StackWindow sw = (StackWindow) WindowManager.getCurrentWindow();
	//StackWindow sw=(StackWindow)imp.getWindow();
	/*Update the scrollbar!*/
	sw.updateSliceSelector();

}
public void setup(){
	
	points();
	/*A custom Bio7 method to create an image from the Points panel!*/
	
	imp= ImageMethods.getPanelImage();
	w=imp.getWidth();
	h=imp.getHeight();
	
		ip = imp.getProcessor();
		
		/*We create a new image stack!*/
		s = new ImageStack(w, h);
		/*We add two dummy slices!*/
		s.addSlice(null, ip);
		s.addSlice(null, ip);
		
		
		
		/*We create an image object from the stack!*/
		plus = new ImagePlus("Stack", s);
		
		/*We make it visible!*/
		plus.show();
		
		/*Now we delete the first image, we don't need it anymore!*/
		s.deleteSlice(1);
		
		
		
}
/*In this method the points are created!*/
public void points() {
    /*Delete all points!*/
	PointPanel.delete();

	for (int i = 0; i < individual.length; i++) {
		/*We simply create random coordinates!*/
		double x = (Math.random() * 1000);
		double y = (Math.random() * 1000);
		
		/*We produce some random attributes!*/
		int species = (int) (Math.random() * Bio7State.getStateSize());
		float alpha = (float) (Math.random() * 255);
		int diameter = (int) (Math.random() * 50);
		individual[i] = new Individual(x, y, species, 10, 255);

	}
	/*Set the individuals in the Points panel !!*/
	PointPanel.setIndividual(individual);
	//PointPanel.createVoronoi();if you want to record Voronoi! Disable dynamic in the Image-Methods view for performance if necessary!
	//PointPanel.createDelauney();
	PointPanel.doPaint();
}

