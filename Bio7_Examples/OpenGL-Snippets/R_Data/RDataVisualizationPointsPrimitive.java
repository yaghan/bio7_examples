/*
This example creates random (normal distributed) numbers dynamically 
in R and transfers them to the Space view.
In this example we draw points primitives!
Rserve has to be alive!
*/
import org.rosuda.REngine.REXPMismatchException;
import static com.eco.bio7.spatial.SpatialUtil.*;


int[] x;
int[] y;
int[] z;
float []no_mat = { 0.0f, 0.0f, 0.0f, 1.0f };
float []fBrightLight = { 1.0f, 1.0f, 1.0f, 1.0f };

public void setup(GL gl, GLU glu, GLUT glut) {
	
	createRData();

}

public void createRData(){
	if (RServe.isAlive()) {
		try {
			/*Create the random data in R! This time we use 
			integers to save memory!*/
			try{
			x = (int[]) RServe.getConnection().eval("x<-as.integer(rnorm(100000)*100)").asIntegers();
			y = (int[]) RServe.getConnection().eval("y<-as.integer(rnorm(100000)*100)").asIntegers();
			z = (int[]) RServe.getConnection().eval("z<-as.integer(rnorm(100000)*100)").asIntegers();
			}
			catch (REXPMismatchException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		} catch (RserveException e) {

			System.out.println(e.getRequestErrorDescription());
		}
	}
	
}
public void ecomain(GL gl, GLU glu, GLUT glut) {
	if (canStep()) {
		createRData();
	}
	//gl.glLightModeli(GL.GL_LIGHT_MODEL_COLOR_CONTROL,GL.GL_SEPARATE_SPECULAR_COLOR);
	if (z != null) {

		for (int i = 0; i < z.length; i++) {
			/*Here we draw the spheres! We translate the points
			The next point is drawn from the origin (PushMatrix!!)!*/

			gl.glPushMatrix();
			//gl.glTranslated(x[i], y[i], z[i]);
			gl.glPointSize(1);
			
			gl.glDisable(GL.GL_LIGHTING);
			gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);	 	    					
	       /*Here we draw the points!*/
		    gl.glBegin(GL.GL_POINTS);	
		    /*We set the vertex with integers!*/
		    gl.glVertex3i(x[i],y[i],z[i]);
			gl.glEnd();
			gl.glEnable(GL.GL_LIGHTING);
			gl.glPopMatrix();

		}

	}

}