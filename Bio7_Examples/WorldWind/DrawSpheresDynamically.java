import gov.nasa.worldwind.*;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.render.*;
import com.eco.bio7.worldwind.WorldWindView;
import gov.nasa.worldwind.render.DrawContext;
import cern.jet.random.*;
import cern.jet.random.engine.*;
import java.util.Date; 
import com.eco.bio7.worldwind.*;

/*
This example demonstrates how to draw lighted spheres
dynamically and georeferenced!
Open the WorldWind perspective compile this file and enable a dynamic layer.
Fly to the Brocken in Germany (N 51.7763,E 10.6069) and adjust the elevation!
The WorldWind perspective has to be active in Bio7 1.4!
*/
  
public float []fBrightLight = { 1.0f, 1.0f, 1.0f, 1.0f };
private Material junctionMaterial = Material.RED;
/*The WorldWind perspective has to be active!*/
public Object sc = WorldWindView.getWwd().getSceneController();
public BasicSceneController bsc = (BasicSceneController)sc;      
public DrawContext dc=bsc.getDrawContext();
   
public MersenneTwister twist=new MersenneTwister(new Date());
public Uniform uni=new Uniform(-0.02,0.02,twist);

public double lat = 51.7991;
public double lon = 10.6158;
public double elevation = 1141;

public void setup(GL gl,GLU glu,GLUT glut){

}
public void ecomain(GL gl,GLU glu,GLUT glut){
	
drawShape(gl,glu,glut);
}


public void drawShape(GL gl, GLU glu, GLUT glut) {

	if (DynamicLayer.canStep()) {
		/*
		The step method can be used to control the speed
		and can be adjusted with the slider of the dynamic layer!
		*/
	}
	/*
	This is the way how we draw the points on the 
	  correct coordinates in WorldWind!
	*/
	DynamicLayer.getDynamicLayer().begin(dc);

	for (int i = 0; i < 20; i++) {
		/*We create random coordinates in a certain range!*/
		lat = lat + uni.nextDouble();
		lon = lon + uni.nextDouble();
		/*Calculate the elevations and positions!*/
		elevation = Ww.getWwd().getModel().getGlobe()
			.getElevation(Angle.fromDegrees(lat), Angle.fromDegrees(lon));
		Position position = new Position(Angle.fromDegrees(lat), Angle
			.fromDegrees(lon), elevation);
		Vec4 pos = dc.getGlobe().computePointFromPosition(position);

		dc.getView().pushReferenceCenter(dc, pos);
		/*
		Here we draw the spheres with material attributes
		and with red colour!
		 */
		gl.glColor4d(0.5, 0.1, 0.1, 1.0);
		gl.glEnable(GL.GL_COLOR_MATERIAL);
		gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, fBrightLight, 0);
		gl.glMateriali(GL.GL_FRONT, GL.GL_SHININESS, 110);
		glut.glutSolidSphere(100, 20, 20);
		lat = 51.7991;
		lon = 10.6158;
	}

	DynamicLayer.getDynamicLayer().end(dc);
	// Restore OpenGL variables.
	dc.getView().popReferenceCenter(dc);
	Ww.getWwd().redraw();
}