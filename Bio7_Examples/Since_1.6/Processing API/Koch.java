import processing.core.PApplet;
import javax.swing.*;
import java.awt.*;
import processing.core.*;
import processing.core.PApplet;

/*
 * Koch Curve
 * by Daniel Shiffman.
 * 
 * Renders a simple fractal, the Koch snowflake. 
 * Each recursive level is drawn in sequence.
 */
 
 /*
 Changed for Bio7!
 See: http://processing.googlecode.com/svn/trunk/processing/build/javadoc/core/processing/core/PApplet.html
 */

PApplet embed;
float theta;

  
public void setup() {
	embed = new Embedded();
	embed.init();
	CustomView.setPanel(embed, "PApplet");

}

class Embedded extends PApplet {

	public void setup() {
		size(640, 360);
		smooth();
	}

	public void draw() {
		background(0);
		frameRate(30);
		stroke(255);
		// Let's pick an angle 0 to 90 degrees based on the mouse position
		float a = (mouseX / (float) width) * 90f;
		// Convert it to radians
		theta = radians(a);
		// Start the tree from the bottom of the screen
		translate(width / 2, height);
		// Draw a line 120 pixels
		line(0, 0, 0, -120);
		// Move to the end of that line
		translate(0, -120);
		// Start the recursive branching!
		branch(120);
	}

	public void mousePressed() {
		// do something based on mouse movement

		// update the screen (run draw once)
		redraw();
	}
}
void branch(float h) {
	// Each branch will be 2/3rds the size of the previous one
	h *= 0.66;

	// All recursive functions must have an exit condition!!!!
	// Here, ours is when the length of the branch is 2 pixels or less
	if (h > 2) {
		embed.pushMatrix(); // Save the current state of transformation (i.e. where are we now)
		embed.rotate(theta); // Rotate by theta
		embed.line(0, 0, 0, -h); // Draw the branch
		embed.translate(0, -h); // Move to the end of the branch
		branch(h); // Ok, now call myself to draw two new branches!!
		embed.popMatrix(); // Whenever we get back here, we "pop" in order to restore the previous matrix state

		// Repeat the same thing, only branch off to the "left" this time!
		embed.pushMatrix();
		embed.rotate(-theta);
		embed.line(0, 0, 0, -h);
		embed.translate(0, -h);
		branch(h);
		embed.popMatrix();
	}
}