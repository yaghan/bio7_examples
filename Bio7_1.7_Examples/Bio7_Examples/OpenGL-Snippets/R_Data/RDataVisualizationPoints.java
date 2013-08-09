import static com.eco.bio7.spatial.SpatialUtil.*;
import org.rosuda.REngine.REXPMismatchException;
import static javax.media.opengl.GL2.*; 
/*
This example creates random (normal distributed) numbers dynamically 
in R and transfers them to the Space view!
Rserve has to be alive!
*/

public double[] x;
public double[] y;
public double[] z;
public float []no_mat = { 0.0f, 0.0f, 0.0f, 1.0f };
public float []fBrightLight = { 1.0f, 1.0f, 1.0f, 1.0f };

public void setup(GL gl, GLU glu, GLUT glut) {
	
	createRData();

}

public void createRData(){
	if (RServe.isAlive()) {
		try {
			/*Create the data in R!*/
			RServe.getConnection().eval("x<-rnorm(1000)*300");
			RServe.getConnection().eval("y<-rnorm(1000)*300");
			RServe.getConnection().eval("z<-rnorm(1000)*300");
			/*Transfer the data from R!*/
			try{
			x = (double[]) RServe.getConnection().eval("x").asDoubles();
			y = (double[]) RServe.getConnection().eval("y").asDoubles();
			z = (double[]) RServe.getConnection().eval("z").asDoubles();
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
public void ecomain(GL2 gl, GLU glu, GLUT glut) {
	if (canStep()) {
		createRData();
	}
	gl.glLightModeli(GL_LIGHT_MODEL_COLOR_CONTROL,GL_SEPARATE_SPECULAR_COLOR);
	if (z != null) {

		for (int i = 0; i < z.length; i++) {
			/*
			Here we draw the spheres!
			The next point is drawn from the origin (PushMatrix)!*/

			gl.glPushMatrix();
			
			/*We translate the points from the origin!*/
			gl.glTranslated(x[i], y[i], z[i]);
			
			gl.glEnable(GL_COLOR_MATERIAL);

			gl.glColor4f(0.5f, 0.5f, 0.8f, 1.0f);
			
			gl.glEnable(GL_COLOR_MATERIAL);
			gl.glColorMaterial(GL.GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
			gl.glMaterialfv(GL.GL_FRONT, GL_SPECULAR, fBrightLight, 0);
			gl.glMateriali(GL.GL_FRONT, GL_SHININESS, 110);		
            /*Here we draw the sphere!*/
			glut.glutSolidSphere(20, 6, 6);
					
			gl.glEnd();
			
			gl.glPopMatrix();

		}

	}

}