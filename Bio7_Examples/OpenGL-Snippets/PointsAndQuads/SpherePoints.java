/*
This example draws spheres randomly!
*/
import static com.eco.bio7.spatial.SpatialUtil.*;
import cern.jet.random.*;
import cern.jet.random.engine.*;
import java.util.Date; 

public MersenneTwister twist=new MersenneTwister(new Date());
public Uniform uni=new Uniform(-1000.0,1000.0,twist);
public int random;
public double []x=new double[1000];
public double []y=new double[1000];
public double []z=new double[1000];
public double [] c1=new double[1000];
public double [] c2=new double[1000];
public double [] c3=new double[1000];
public float []no_mat = { 0.0f, 0.0f, 0.0f, 1.0f };
public float []fBrightLight = { 1.0f, 1.0f, 1.0f, 1.0f };
public int rand;

public void ecomain(GL gl, GLU glu, GLUT glut) {

	/*We enable a specular light modus in OpenGL!*/
	gl.glLightModeli(GL.GL_LIGHT_MODEL_COLOR_CONTROL,GL.GL_SEPARATE_SPECULAR_COLOR);
	/*
	gl.glLightModeli( GL.GL_LIGHT_MODEL_COLOR_CONTROL, GL.GL_SINGLE_COLOR );
	
	*/
	

	for (int i = 0; i < 1000; i++) {

		/*Here we use the canStep method from the control panel!*/
		if (canStep()) {
			/*We randomize the coordinates at each step!*/
			x[i] = uni.nextDouble();
			y[i] = uni.nextDouble();
			z[i] = uni.nextDouble();
			/*We randomize the colour!*/
			c1[i] = Math.random();
			c2[i] = Math.random();
			c3[i] = Math.random();
		}

		gl.glPushMatrix();

		gl.glTranslated(x[i], y[i], z[i]);
		gl.glColor4d(c1[i], c2[i], c3[i], 1.0f);
		gl.glEnable(GL.GL_COLOR_MATERIAL);
		gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, fBrightLight, 0);

		gl.glMateriali(GL.GL_FRONT, GL.GL_SHININESS, 110);

		glut.glutSolidSphere(10, 8, 8);

		gl.glPopMatrix();

	}

}