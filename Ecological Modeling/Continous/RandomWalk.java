/*
This examples demonstrates a random walk in the Points panel.
Compile the source, start the calculation and then switch to the Points panel!
*/
import static com.eco.bio7.image.PointPanel.*;

int x = 500;
int y = 500;
double dx; 
double dy;
double r;
double angle;
double rad;
Individual[] plantpoint = new Individual[1];
public void ecomain() {
	
	PointPanel.delete();
	
	r = Math.random() * 100;// The distance for a step by chance!

	angle = Math.random() * 360;

	rad = angle * Math.PI / 180;

	dx = r * Math.sin(rad);
	dy = r * Math.cos(rad);
   
	
	
	//A torus function if values>field or values<field.
	int[] point = PointPanel.torus(x, y, (int) dx, (int) dy);// torus(from_x,from_y,to_x, to_y)
	x = point[0];// Get the values from the array (0=x,1=y)
	y = point[1];
	plantpoint[0] = new Individual(x, y, 0, 100, 255.0f);
	setIndividual(plantpoint);
}