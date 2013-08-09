import gov.nasa.worldwind.*;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.avlist.*;
import gov.nasa.worldwind.event.*;
import gov.nasa.worldwind.exception.*;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.globes.*;
import gov.nasa.worldwind.layers.*;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwind.util.Logging;
import gov.nasa.worldwind.util.*;
import gov.nasa.worldwind.view.*;
import com.eco.bio7.worldwind.*;
import gov.nasa.worldwind.render.DrawContext;
import static javax.media.opengl.GL2.*;

/*
This example demonstrates how to draw point primitives
dynamically and georeferenced!
Open the WorldWind perspective compile this file and enable a dynamic layer.
Fly to Germany and adjust the elevation!
The WorldWind perspective has to be active in Bio7 1.4!
*/

/*The WorldWind perspective has to be active in Bio7 1.4!*/
public Object sc;
public BasicSceneController bsc;      
public DrawContext dc;    

public Model() {
	
	WorldWindowGLCanvas wwc = WorldWindView.getWwd();
	if (wwc != null) {
		sc = wwc.getSceneController();
		bsc = (BasicSceneController) sc;
		dc = bsc.getDrawContext();
		WorldWindOptionsView.addDynamicLayer();
	}
	else{
		Bio7Dialog.message("Please open the WorldWind perspective!");
	}

}

public void ecomain(GL2 gl,GLU glu,GLUT glut){
	drawShape(gl,glu,glut);
}


protected void drawShape(GL2 gl, GLU glu, GLUT glut) {
	/*
	The step method can be used to control the speed
	and can be adjusted with the slider of the dynamic layer!
	 */
	if (DynamicLayer.canStep()) {

	}

	// Store OpenGL variables.
	gl.glPushAttrib(GL_ENABLE_BIT
		| GL_COLOR_BUFFER_BIT
		| GL_CURRENT_BIT
		| GL_POINT_BIT);

	gl.glPointSize(2f);

	// Draw geometry
	double lat, lon, elevation;

	gl.glBegin(GL.GL_POINTS);

	double c1 = 0;
	double c3 = 0;
	double c2 = 0;
	for (lat = 50.999583f; lat < 52.00042f; lat += 0.1) {

		for (lon = 9.999583; lon < 11.00042; lon += 0.1) {

			for (elevation = 10000; elevation < 100000; elevation += 1000) {
				gl.glColor4d(1, 1, 1, 255);

				Position position = new Position(Angle.fromDegrees(lat), Angle
					.fromDegrees(lon), elevation);
				Vec4 pos = dc.getGlobe().computePointFromPosition(position);

				gl.glVertex3d(pos.x, pos.y, pos.z);

				//                    

				c3++;
			}
			c2++;
		}
		c1++;
	}

	gl.glEnd();

	// Restore OpenGL variables.
	gl.glPopAttrib();
	Ww.getWwd().redraw();
}