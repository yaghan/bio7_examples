import jcollada.*;
import java.io.File;


/*
This snippet shows how to load a collada model in the 3d view.
Invoke the setup method to load the model! 
Please reference your collada model with the Bio7 paths!
*/

public DAE scene;
double time=0;
 
public void setup(GL gl,GLU glu,GLUT glut){
 
 scene = DAE.load(gl, new File("YourFile.dae"), new DAELoadConfig()); 

}
public void ecomain(GL gl, GLU glu, GLUT glut) {
	if (scene != null) {
		//scene.applyCam(gl);
		scene.applyLights(gl);
		scene.draw(gl, time);
	}
	
		time = time + 0.02;
	
}