/*
A simple example to show how to fill data dynamically an ImageJ 
image as a soil layer! 
*/
import java.awt.*;
import ij.*;
import ij.gui.*;
import ij.process.*;
import ij.plugin.PlugIn;
import ij.WindowManager;
import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
int WIDTH = Field.getWidth();
int HEIGHT =Field.getHeight();
float [][]temp;
public ImagePlus imp;

public void setup() {

	/*If no image is present we create one with random pixels!*/
	ImageProcessor ip = new FloatProcessor(WIDTH, HEIGHT);
	ip.setColor(Color.white);
	ip.fill();

	int w = ip.getWidth();
	int h = ip.getHeight();
	temp = new float[w][h];
	for (int u = 0; u < h; u++) {
		for (int v = 0; v < w; v++) {
			float b = (float) (Math.random() * 1000);

			ip.putPixelValue(v, u, b);

		}
	}
	imp = new ImagePlus("Nitrate", ip);
	/*We have to calibrate the float matrix (image)!*/
	ip.resetMinAndMax();
	imp.show();

}
public void ecomain() {
	ImagePlus imp = WindowManager.getImage(1);
	//ImagePlus imp = WindowManager.getCurrentImage();
	if(imp!=null){
	ImageProcessor ip = imp.getProcessor();
		int w = ip.getWidth();
		int h = ip.getHeight();

		for (int i = 0; i < h; i++) {
			for (int u = 0; u < w; u++) {

				float b = (float) (Math.random() * 1000);
                temp[u][i]=b;
			}
		}

		for (int i = 0; i < h; i++) {
			for (int u = 0; u < w; u++) {

				ip.putPixelValue(u, i, temp[u][i]);

			}
		}

		imp.updateAndDraw();
	}
}