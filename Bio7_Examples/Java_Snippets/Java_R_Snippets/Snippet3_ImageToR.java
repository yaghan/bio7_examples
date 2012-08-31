/*
Right mouse-click on file -> select "Compile and Run" 
to start this example !
This example transfers image data (8-bit)from ImageJ to R!
Remove the if-condition if you want to be able to transfer RGB images!

Plot in R (with RGui R->Start RGui with...)e.g. with a matrix: 
im<-matrix(p,width,height);image(im);

The resulted image looks horizontally flipped because of the default y-origin
of R plots (0,0 is bottom left in R!). 
Transferred *.gif files look inverted because of the different display
of the values.(*.jpegs files will be displayed normally!).
*/

import ij.*;
import ij.gui.*;
import ij.process.*;
int[] p;
int i = 0;
int u = 0;
public void ecomain() {

	/*Get the active image !*/
	ImagePlus imp = WindowManager.getCurrentImage();
	if (imp != null) {
		/*Get the image processor of the image !*/
		ImageProcessor ip = imp.getProcessor();
		int w = ip.getWidth();
		int h = ip.getHeight();
		p = new int[w * h];
		/*We proof if the image is 8-bit!
		Please remove the if-condition if you want to be able to transfer RGB images!*/
		if (ip instanceof ByteProcessor) {

			for (int z = 0; z < h * w; z++) {

				if (u > (w - 1)) {
					i++;
					u = 0;
				}
				p[z] = ip.getPixel(u, i);

				if (u < w) {
					u++;
				}
			}
			try {

				RServe.getConnection().assign("p", p);

			} catch (REngineException e) {
				
				e.printStackTrace();
			}

		} else {
			System.out.println("Requires an 8-bit image !");
		}

	} else {
		System.out.println("No image opened !");
	}
	// Please enter your code here
}