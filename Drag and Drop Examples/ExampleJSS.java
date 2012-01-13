import static com.eco.bio7.spatial.SpatialUtil.*;

/*Java container (arrays) for the Data created with R!*/
int[] x; 
int[] y; 
int[] z;

public void setup() {
	if (RServe.isConnected() == false) {
		Bio7Action.callRserve();
	}
	Work.openPerspective("com.eco.bio7.perspective_3d");

	if (isStarted() == false) {
		
		startStop();
	}

}

/*The main method with the OpenGL context can be triggered
  in different time intervals!*/
public void ecomain(GL gl, GLU glu, GLUT glut) {
/*After a specified time intervall the data is created!*/
if (canStep()) {
/*Method to dynamically create the data in R!*/
	createRData(); 	
}        
/*Rendering of the data with OpenGL!*/ 	
if (z != null) {
	for (int i = 0; i < z.length; i++) { 			
	/*OpenGL commands to render the data as primitive points!*/
		gl.glPushMatrix(); 			
		gl.glPointSize(1); 			
		gl.glDisable(GL.GL_LIGHTING); 			
		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f); 			
		gl.glBegin(GL.GL_POINTS); 			
		gl.glVertex3i(x[i], y[i], z[i]); 			
		gl.glEnd(); 			
		gl.glEnable(GL.GL_LIGHTING); 			
		gl.glPopMatrix();
		}
	}
}
public void createRData(){
RConnection c=RServe.getConnection();
if (c!=null) {	
	try { 			
			/*Creation of random data in R and the transfer to the Java arrays!*/
			x = c.eval("rnorm(100000)*100").asIntegers(); 			
			y = c.eval("rnorm(100000)*100").asIntegers(); 			
			z = c.eval("rnorm(100000)*100").asIntegers();  			
		} 
		catch (RserveException rse) {
			/*error!*/								
		} 				
		catch (REXPMismatchException mme) {
		 /*error!*/
		}
	} 
}