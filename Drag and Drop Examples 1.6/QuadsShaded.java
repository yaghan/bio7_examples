/*
In this example coloured quads in a rectangular form are drawn!
*/


import static com.eco.bio7.spatial.SpatialUtil.*;
import cern.jet.random.*;
import cern.jet.random.engine.*;
import java.util.Date; 


public MersenneTwister twist=new MersenneTwister(new Date());
public Uniform uni=new Uniform(-1000.0,1000.0,twist);
public int random;
int xlength=10;
int ylength=10;
int zlength=10;
int sizeCube=100;
int distCubes=10;
double xstart=(xlength*(sizeCube+distCubes))/2;
double ystart=(ylength*(sizeCube+distCubes))/2;
double zstart=(zlength*(sizeCube+distCubes))/2;

double [] c1=new double[xlength*ylength*zlength];
double [] c2=new double[xlength*ylength*zlength];
double [] c3=new double[xlength*ylength*zlength];
double [] c4=new double[xlength*ylength*zlength];

float []no_mat = { 0.0f, 0.0f, 0.0f, 1.0f };
double r;
double g;
double b;
double a;
int rand;
public void setup() {

	Work.openPerspective("com.eco.bio7.perspective_3d");

	if (SpatialUtil.isStarted() == false) {

		SpatialUtil.startStop();
	}
	SpatialUtil.setFullscreen();

}
public void ecomain(GL gl, GLU glu, GLUT glut) {

	/*Here we use the canStep method from the control panel!*/
	if (canStep()) {
		for (int i = 0; i < c1.length; i++) {

			c1[i] = Math.random();
			c2[i] = Math.random();
			c3[i] = Math.random();
			c4[i] = Math.random();
		}
	}

	gl.glPushMatrix();
	gl.glTranslated(-xstart, -ystart, -zstart);

	int cRun = 0;
	for (int x = 0; x < xlength; x++) {
		gl.glTranslated(sizeCube + distCubes, 0, 0);
		for (int y = 0; y < ylength; y++) {
			gl.glTranslated(0, sizeCube + distCubes, 0);
			for (int z = 0; z < zlength; z++) {

				gl.glTranslated(0, 0, sizeCube + distCubes);

				gl.glColor4d(c1[cRun], c2[cRun], c3[cRun], 1.0);
				gl.glEnable(GL.GL_COLOR_MATERIAL);

				gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_SPECULAR,no_mat,0);

				/*We use default glut routines to draw the quads!*/
				glut.glutSolidCube(sizeCube);

				cRun++;
			}
			gl.glTranslated(0, 0, -zlength * (sizeCube + distCubes));

		}
		gl.glTranslated(0, -ylength * (sizeCube + distCubes), 0);

	}

	gl.glTranslated(-xlength * (sizeCube + distCubes), 0, 0);

	gl.glPopMatrix();
}