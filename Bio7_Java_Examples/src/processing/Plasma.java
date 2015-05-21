package processing;

import processing.core.PApplet;

import javax.swing.*;

import com.eco.bio7.collection.CustomView;

import java.awt.*;

import processing.core.*;

/*
 * Plasma Demo Effect
 * by luis2048. 
 * 
 * Cycles of changing colours warped to give an illusion 
 * of liquid, organic movement.Colors are the sum of sine 
 * functions and various formulas. Based on formula by Robert Klep. 
 */

/*
Changed for Bio7!
See: http://processing.googlecode.com/svn/trunk/processing/build/javadoc/core/processing/core/PApplet.html
 */
public class Plasma extends com.eco.bio7.compile.Model {

	PApplet embed;

	int pixelSize = 2;

	PGraphics pg;

	public void setup() {

		embed = new Embedded();

		CustomView view = new CustomView();

		view.setPanel(embed, "PApplet");

		embed.init();
	}

	class Embedded extends PApplet {

		public void setup() {
			size(640, 360);
			// Create buffered image for plasma effect
			pg = createGraphics(160, 90);
			colorMode(HSB);
			noSmooth();
		}

		public void draw() {
			float xc = 25;

			// Enable this to control the speed of animation regardless of CPU
			// power
			// int timeDisplacement = millis()/30;

			// This runs plasma as fast as your computer can handle
			int timeDisplacement = frameCount;

			// No need to do this math for every pixel
			float calculation1 = sin(radians(timeDisplacement * 0.61655617f));
			float calculation2 = sin(radians(timeDisplacement * -3.6352262f));

			// Output into a buffered image for reuse
			pg.beginDraw();
			pg.loadPixels();

			// Plasma algorithm
			for (int x = 0; x < pg.width; x++, xc += pixelSize) {
				float yc = 25;
				float s1 = 128 + 128 * sin(radians(xc) * calculation1);

				for (int y = 0; y < pg.height; y++, yc += pixelSize) {
					float s2 = 128 + 128 * sin(radians(yc) * calculation2);
					float s3 = 128 + 128 * sin(radians((xc + yc + timeDisplacement * 5) / 2));
					float s = (s1 + s2 + s3) / 3;
					pg.pixels[x + y * pg.width] = color(s, 255 - s / 2.0f, 255);
				}
			}
			pg.updatePixels();
			pg.endDraw();

			// display the results
			image(pg, 0, 0, width, height);

		}

		public void mousePressed() {
			// do something based on mouse movement

			// update the screen (run draw once)
			redraw();
		}
	}
}