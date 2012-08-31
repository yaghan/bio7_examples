import java.awt.Graphics2D;
import java.awt.Color;

/*
This example shows how to texture a cube.
Use the setup and main  method to execute the example!

!!!!!Please load the texture in the setup method to avoid a crash when using the fullscreen
feature. See the manual for more details!
*/

public Texture t;
public float rot=0f;
public String fileroot;
public String f;

public void setup(GL gl, GLU glu, GLUT glut) {

	fileroot = FileRoot.getFileRoot();
	/*Create the path from the root of the workspace!*/
	f = fileroot+ "/Bio7_Examples/OpenGL-Snippets/TexturesAndModels/FluorescentCells.jpg";
	/*Load the texture!*/
	t = SpatialLoader.createTexture(f);

}

public void ecomain(GL gl, GLU glu, GLUT glut) {
	rot = rot + 0.01f;
	gl.glPushMatrix();
	gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

	if (t != null) {
		t.bind();
		t.enable();

		drawCube(gl);

		t.disable();
	}
	gl.glPopMatrix();

}
/*Here we draw a cube and assign the texture on each face!*/
public void drawCube(GL gl){
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-100.0f, -100.0f, 100.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(100.0f, -100.0f, 100.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(100.0f, 100.0f, 100.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-100.0f, 100.0f, 100.0f);
        // Back Face
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-100.0f, -100.0f, -100.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-100.0f, 100.0f, -100.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(100.0f, 100.0f, -100.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(100.0f, -100.0f, -100.0f);
        // Top Face
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-100.0f, 100.0f, -100.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-100.0f, 100.0f, 100.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(100.0f, 100.0f, 100.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(100.0f, 100.0f, -100.0f);
        // Bottom Face
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-100.0f, -100.0f, -100.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(100.0f, -100.0f, -100.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(100.0f, -100.0f, 100.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-100.0f, -100.0f, 100.0f);
        // Right face
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(100.0f, -100.0f, -100.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(100.0f, 100.0f, -100.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(100.0f, 100.0f, 100.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(100.0f, -100.0f, 100.0f);
        // Left Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-100.0f, -100.0f, -100.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-100.0f, -100.0f, 100.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-100.0f, 100.0f, 100.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-100.0f, 100.0f, -100.0f);
        
        gl.glEnd();
}