import java.awt.Graphics2D;
import java.awt.Color;
import com.eco.bio7.loader3d.Tuple3;
import static com.eco.bio7.spatial.SpatialUtil.*;
import static javax.media.opengl.GL2.*; 

/*
This example demonstrates the use of the custom API to load
*.obj models. Press the setup button
and enable the play (execution)button in the Time panel of the Spatial
perpspective to see the result!

Important: 
If you use the fullscreen view the creation of the textures
and models must be triggered by means of the setup method to avoid a crash
of the application. See the manual for more information why.
*/

public float rotatz=0.0f;
public int x=0;
public int y=0;
public int z=0;
public OBJModel t;
public String fileroot;
public String f;

public void setup(GL2 gl, GLU glu, GLUT glut) {
   
	fileroot = FileRoot.getFileRoot();
	/*Create the path from the root of the workspace!*/
	f = fileroot + "/Bio7_Examples/OpenGL-Snippets/TexturesAndModels/earth.obj";

	t = SpatialLoader.loadModel(f, 99.9925f);


}

public void ecomain(GL2 gl, GLU glu, GLUT glut) {
	if (canStep()) {
		if (t != null) {
			/*Some attributes of the object! Not documented in the Bio7 API!!!*/
			/*
			t.getModelDims();
			System.out.println(t.getModelDims().getCenter());
			System.out.println(t.getModelDims().getCenter().getZ());
			System.out.println(t.getModelDims().getWidth());
			System.out.println(t.getModelDims().getHeight());
			System.out.println(t.getModelDims().getDepth());
			System.out.println(t.getModelDims().getLargest());
			System.out.println(t.getVerts().size());
			Tuple3 tuple=(Tuple3)t.getVerts().get(0);
			 */

		}
	}
	rotatz++;

	if (t != null) {
	/*
	We set the colour to white!
	The material colour is disabled if a non textured model is loaded with colour!*/
	gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	gl.glDisable(GL_COLOR_MATERIAL);
		t.draw(gl);

		/*The model with rotation!*/
		gl.glPushMatrix();
		gl.glRotatef(rotatz, 0.0f, 0.0f, 1.0f);
		gl.glTranslatef(200.0f, 0.0f, 0.0f);

		t.draw(gl);
		gl.glPopMatrix();
	}
	/*Enable the material colour again!*/
    gl.glEnable(GL_COLOR_MATERIAL);
	
}
