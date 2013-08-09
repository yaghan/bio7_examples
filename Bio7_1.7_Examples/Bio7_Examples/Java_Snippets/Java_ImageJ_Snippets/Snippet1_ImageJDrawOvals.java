import java.awt.*;
import ij.*;
import ij.gui.*;
import ij.process.*;
import ij.plugin.PlugIn;

/*
An example from the ImageJ website.
http://rsb.info.nih.gov/ij/

Select this file with a right-click of the mouse.
Select action "Setup" to start this example!*/


public void setup() {

	int w = 324, h = 200;
	ImageProcessor ip = new ColorProcessor(w, h);
	int centerx = 162, centery = 100;
	double radius = 90;
	for (double counter = 0; counter < 20; counter = counter + 0.001) {
		double x = Math.sin(counter) * radius + centerx;
		double y = Math.cos(counter) * radius + centery;
		ip.putPixel((int) x, (int) y, -1);

	}
	new ImagePlus("Circle Test", ip).show();

}