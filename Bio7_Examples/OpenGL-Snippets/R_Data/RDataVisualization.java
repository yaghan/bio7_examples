import org.rosuda.REngine.REXPMismatchException;

/*
A simple example which draws a line plot with data from R.
Invoke the setup method to see the result!
*/

double[] z;

public void setup(GL gl, GLU glu, GLUT glut) {

	if (RServe.isAlive()) {
		try {

			/*We produce some random data with R!*/
			RServe.getConnection().eval("z<-runif(500)*200");
			try {
				z = (double[]) RServe.getConnection().eval("z").asDoubles();
			} catch (REXPMismatchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (RserveException e) {

			System.out.println(e.getRequestErrorDescription());
		}
	}

}
public void ecomain(GL gl, GLU glu, GLUT glut) {
	 gl.glDisable(GL.GL_LIGHTING);
	if (z != null) {
		
		gl.glBegin(GL.GL_LINES);
				
		for (int i = 0; i < z.length; i = i + 1) {
			float zv = (float) z[i];
			
			if(i>0){
			float zvb = (float) z[i-1];
			gl.glVertex3f(i, zvb, 0);
			gl.glVertex3f(i+1, zv, 0);
			}
			else{
			gl.glVertex3f(i, 0, 0);
			gl.glVertex3f(i+1, zv, 0);
				
			}
		}
		
		gl.glEnd();
	}
	 gl.glEnable(GL.GL_LIGHTING);
}