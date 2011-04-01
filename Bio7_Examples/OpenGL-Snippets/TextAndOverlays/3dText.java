/*
This example draws text in 3d!

!!!!Important!!!!!
If you use the fullscreen view the creation of the TextRenderer
must be triggered by means of the setup method to avoid a crash
of the application. See the manual for more information why.
*/

import java.awt.Font;
import cern.jet.random.*;
import cern.jet.random.engine.*;
import java.util.Date; 
import static com.eco.bio7.spatial.SpatialUtil.*;


public Font font = new Font("Verdana", Font.BOLD, 100);
public MersenneTwister twist=new MersenneTwister(new Date());
public Uniform uni=new Uniform(-10000.0,10000.0,twist);
public double []x=new double[1000];
public double []y=new double[1000];
public double []z=new double[1000];
public double r=0;
public TextRenderer renderer;


public void setup(GL gl, GLU glu, GLUT glut){
	font = new Font("Verdana", Font.BOLD, 100);
	/*
	When using the fullscreen option the TextRenderer has to reinitialized
	in the setup method again!!!!
	*/
	renderer = new TextRenderer(font, true, false);
	
}

public void ecomain(GL gl, GLU glu, GLUT glut) {
	
if(renderer==null){
	
	font = new Font("Verdana", Font.BOLD, 100);
	
    renderer = new TextRenderer(font, true, false);
}
	renderer.begin3DRendering();
	for (int i = 0; i < 1000; i++) {
		/*We randomize the coordinates at each step!*/
		if (canStep()) {
			x[i] = uni.nextDouble();
			y[i] = uni.nextDouble();
			z[i] = uni.nextDouble();

		}
		renderer.setColor(0, 0, 0, 1);
        /*Draw the text at the coordinates!*/
		renderer.draw3D("Bio7", (float) x[i], (float) y[i], (float) z[i], 1.0f);
		

	}
	renderer.end3DRendering();
	

}