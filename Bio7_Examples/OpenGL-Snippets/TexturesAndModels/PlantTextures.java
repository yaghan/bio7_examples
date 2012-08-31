import java.awt.Graphics2D;
import java.awt.Color;
import cern.jet.random.*;
import cern.jet.random.engine.*;
import java.util.Date; 

/*
This example demonstrates the use of the custom API to load
textures. Press the setup button and enable the execution button in the Time panel 
to see the result!

Important: 
If you use the fullscreen view the creation of the textures
must be triggered by means of the setup method to avoid a crash
of the application. See the manual for more information why.
*/

public float rotatz=0.0f;
public int x=0;
public int y=0;
public int z=0;
public Texture t;
public int []xa=new int[10000];
public int []ya=new int[10000];
public String fileroot;
public String f;


public MersenneTwister twist=new MersenneTwister(new Date());
public Uniform uni=new Uniform(-10000.0,10000.0,twist);

public void setup(GL gl, GLU glu, GLUT glut) {
	for (int i = 0; i < 10000; i++) {
		xa[i] = uni.nextInt();
		ya[i] = uni.nextInt();

	}

	fileroot = FileRoot.getFileRoot();
	/*Create the path from the root of the workspace!*/
	f = fileroot + "/Bio7_Examples/OpenGL-Snippets/TexturesAndModels/tree.gif";

	t = SpatialLoader.createTexture(f);

}
public void ecomain(){
	for (int i = 0; i < 10000; i++) {
		xa[i]=uni.nextInt();
		ya[i]=uni.nextInt();
	}
	
}
public void ecomain(GL gl, GLU glu, GLUT glut) {

	rotatz++;

	gl.glPushMatrix();
	if (t != null) {
		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

		trees(gl);
	}
	gl.glPopMatrix();

}
public void trees(GL gl) {

	for (int i = 0; i < 10000; i++) {
		gl.glPushMatrix();
		gl.glTranslatef((float) xa[i], (float) ya[i], 100.0f);
        /*Bind and enable the texture!*/
		t.bind();
		t.enable();

		gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
		/*Switch off the light. It affects the texture rendering!*/
		gl.glDisable(GL.GL_LIGHTING);
		drawTree(gl);

		gl.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
		drawTree(gl);
		gl.glEnable(GL.GL_LIGHTING);
		/*Disable the texture!*/
		t.disable();
		gl.glPopMatrix();
	}

}
public void drawTree(GL gl) {
	//gl.glRotatef(90.0f, -90.0f, 0.0f, 1.0f);
	gl.glBegin(GL.GL_QUADS);
	gl.glTexCoord2f(0, 0); // Draw A Quad
	gl.glVertex3f(-100.0f, 100.0f, 0.0f);

	gl.glTexCoord2f(1, 0); // Top Left
	gl.glVertex3f(100.0f, 100.0f, 0.0f);

	gl.glTexCoord2f(1, 1); // Top Right
	gl.glVertex3f(100.0f, -100.0f, 0.0f);

	gl.glTexCoord2f(0, 1); // Bottom Right
	gl.glVertex3f(-100.0f, -100.0f, 0.0f);
	// Bottom Left
	gl.glEnd();

}

