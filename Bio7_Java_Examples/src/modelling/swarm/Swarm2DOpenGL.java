package modelling.swarm;

import static com.eco.bio7.spatial.SpatialEvents.getClickEvent;
import static com.eco.bio7.spatial.SpatialEvents.isMouseDoubleClicked;
import static com.eco.bio7.spatial.SpatialUtil.isStarted;
import static com.eco.bio7.spatial.SpatialUtil.resetRotation;
import static com.eco.bio7.spatial.SpatialUtil.setRotationX;
import static com.eco.bio7.spatial.SpatialUtil.setRotationY;
import static com.eco.bio7.spatial.SpatialUtil.setRotationZ;
import static com.eco.bio7.spatial.SpatialUtil.startStop;
import static javax.media.opengl.GL.GL_FRONT;
import static javax.media.opengl.GL2GL3.GL_QUADS;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT_AND_DIFFUSE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_COLOR_MATERIAL;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHTING;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SHININESS;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import processing.core.PVector;
import cern.jet.random.tfloat.FloatUniform;
import cern.jet.random.tfloat.engine.FloatMersenneTwister;
import com.eco.bio7.collection.Work;
import com.eco.bio7.spatial.SpatialCamera;
import com.eco.bio7.spatial.SpatialUtil;
import com.jogamp.opengl.util.gl2.GLUT;

/*
 * Flocking 
 * by Daniel Shiffman.  
 * 
 * An implementation of Craig Reynold's Boids program to simulate
 * the flocking behavior of birds. Each boid steers itself based on 
 * rules of avoidance, alignment, and coherence.
 *
 */

/*
 * Changed for Bio7!
 *
 * This example has been ported to Bio7 using the Bio7 3d view and OpenGL and Processing API!
 * Press the setup button in the main Bio7 toolbar to open the 3d view automatically and start the swarm model!
 * You can change the view by enabling the split view in the "Options Space" view! An individual can be added with a double-click of the mouse device!
 *
 */

/*Variables*********************************************/
public class Swarm2DOpenGL extends com.eco.bio7.compile.Model {

	public FloatMersenneTwister twist = new FloatMersenneTwister(new Date());
	public FloatUniform uni = new FloatUniform(-100f, 100f, twist);
	public int random;
	public int width = 500;
	public int height = 500;
	public ArrayList boids; // Initialize the arraylist; // An arraylist for all
							// the boids
	public float[] no_mat = { 0.0f, 0.0f, 0.0f, 1.0f };
	public float[] fBrightLight = { 1.0f, 1.0f, 1.0f, 1.0f };
	private final static int FLOOR_LEN = 11; // should be even
	private final static int BLUE_TILE = 0; // floor tile colour types
	private final static int GREEN_TILE = 1;
	public static float[] TransformMatrix = new float[16];

	/* Methods*********************************************** */

	void addBoid(Boid b) {
		boids.add(b);
	}

	public void setup() {
		Work.openPerspective("com.eco.bio7.perspective_3d");

		SpatialUtil.setup();

		if (isStarted() == false) {

			startStop();
		}

	}

	public void setup(GL2 gl, GLU glu, GLUT glut) {

		/* Reset the user interface rotation! */
		resetRotation();
		/* Reset the world rotation! */
		setRotationX(0.0f);
		setRotationY(0.0f);
		setRotationZ(0.0f);
		random = (int) uni.nextFloat();
		/*
		 * This will create a new object with the correct GL context if using
		 * the fullscreen mode.
		 */
		// axisLabelRenderer = new TextRenderer(font);

		boids = new ArrayList();
		// Add an initial set of boids into the system
		for (int i = 0; i < 150; i++) {
			addBoid(new Boid(new PVector(0, 0), 3.0f, 0.05f));

		}

	}

	public void run(GL2 gl, GLU glu, GLUT glut) {
		double xpos;
		double ypos;
		int count;
		for (int i = 0; i < boids.size(); i++) {
			count = i;
			Boid b = (Boid) boids.get(i);
			xpos = b.loc.x;
			ypos = b.loc.y;
			if (count == 50) {
				double theta = b.vel.heading2D() + Math.PI / 2;
				double theta2 = theta * (180 / Math.PI);
				// gl.glTranslated(xpos, -ypos, 0);
				// glut.glutSolidSphere(10, 20, 20);

				/*
				 * We rotate and translate the partikel in front of the camera
				 * position!
				 */

				gl.glPushMatrix();
				gl.glTranslated(xpos, ypos, 0);

				gl.glRotatef((float) theta2, 0.0f, 0.0f, 1.0f);
				gl.glTranslated(0, 35, 0);

				SpatialCamera.setCustomCamera(xpos - 0.0, ypos - 60.0, 10.0,
						xpos, ypos, 0.0);
				gl.glColor4d(0.0, 0.2, 0.6, 1.0f);
				gl.glMateriali(GL_FRONT, GL_SHININESS, 200);
				gl.glMaterialfv(GL_FRONT, GL_SPECULAR, fBrightLight, 0);
				glut.glutSolidSphere(3, 10, 10);

				gl.glPopMatrix();

			}

			b.run(boids, gl, glu, glut); // Passing the entire list of boids to
											// each boid individually
		}

		/* Add a boid if the mouse is double-clicked! */
		if (isMouseDoubleClicked()) {

			MouseEvent e = getClickEvent();
			int x = e.getX();
			int y = e.getY();

			addBoid(new Boid(new PVector(0, 0), 3.0f, 0.05f));
		}
		gl.glScaled(100, 100, 100);
		gl.glRotated(90.0, 1.0, 0, 0);
		drawFloor(gl);
	}

	// The Boid class

	class Boid {

		PVector loc;
		PVector vel;
		PVector acc;
		float r;
		float maxforce; // Maximum steering force
		float maxspeed; // Maximum speed

		public Boid(PVector l, float ms, float mf) {
			float s1 = (float) uni.nextFloat() / 100;
			float s2 = (float) uni.nextFloat() / 100;
			acc = new PVector(0, 0);
			vel = new PVector(s1, s2);
			loc = l.get();
			r = 1;
			maxspeed = ms;
			maxforce = mf;
		}

		void run(ArrayList boids, GL2 gl, GLU glu, GLUT glut) {
			// Avoid a recalculation if the Splitscreen is active!
			if (SpatialUtil.isSplitPanelDrawing() == false
					|| SpatialUtil.isSplitView() == false) {

				flock(boids);
				update();
				borders();

			}
			render(gl, glu, glut);
		}

		// We accumulate a new acceleration each time based on three rules
		void flock(ArrayList boids) {
			PVector sep = separate(boids); // Separation
			PVector ali = align(boids); // Alignment
			PVector coh = cohesion(boids); // Cohesion
			// Arbitrarily weight these forces
			sep.mult(1.5f);
			ali.mult(1.0f);
			coh.mult(1.0f);
			// Add the force vectors to acceleration
			acc.add(sep);
			acc.add(ali);
			acc.add(coh);
		}

		// Method to update location
		void update() {
			// Update velocity
			vel.add(acc);
			// Limit speed
			vel.limit(maxspeed);
			loc.add(vel);
			// Reset accelertion to 0 each cycle
			acc.mult(0);
		}

		void seek(PVector target) {
			acc.add(steer(target, false));
		}

		void arrive(PVector target) {
			acc.add(steer(target, true));
		}

		// A method that calculates a steering vector towards a target
		// Takes a second argument, if true, it slows down as it approaches the
		// target
		PVector steer(PVector target, boolean slowdown) {
			PVector steer; // The steering vector
			PVector desired = target.sub(target, loc); // A vector pointing from
														// the location to the
														// target
			float d = desired.mag(); // Distance from the target is the
										// magnitude of the vector
			// If the distance is greater than 0, calc steering (otherwise
			// return zero vector)
			if (d > 0) {
				// Normalize desired
				desired.normalize();
				// Two options for desired vector magnitude (1 -- based on
				// distance, 2 -- maxspeed)
				if ((slowdown) && (d < 100.0f))
					desired.mult(maxspeed * (d / 100.0f)); // This damping is
															// somewhat
															// arbitrary
				else
					desired.mult(maxspeed);
				// Steering = Desired minus Velocity
				steer = target.sub(desired, vel);
				steer.limit(maxforce); // Limit to maximum steering force
			} else {
				steer = new PVector(0, 0);
			}
			return steer;
		}

		void render(GL2 gl, GLU glu, GLUT glut) {
			// Draw a triangle rotated in the direction of velocity
			double theta = vel.heading2D() + Math.PI / 2;
			double theta2 = theta * (180 / Math.PI);
			// System.out.println(theta2);
			gl.glDisable(GL_LIGHTING);
			gl.glPushMatrix();
			gl.glTranslatef(loc.x, loc.y, 0.0f);

			gl.glRotatef((float) theta2, 0.0f, 0.0f, 1.0f);
			gl.glEnable(GL_COLOR_MATERIAL);
			gl.glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
			gl.glMaterialfv(GL_FRONT, GL_SPECULAR, fBrightLight, 0);

			gl.glMateriali(GL_FRONT, GL_SHININESS, 110);

			// glut.glutSolidSphere(10, 3, 3);
			gl.glPushMatrix();
			gl.glRotatef(-90.0f, 0.0f, 0.0f, 1.0f);
			gl.glBegin(GL.GL_TRIANGLES);
			// Drawing Using Triangles
			gl.glColor3f(1.0f, 0.0f, 0.0f);
			gl.glVertex3f(10f, 0.0f, 0.0f);
			gl.glVertex3f(-10f, 5.0f, 0.0f);
			gl.glVertex3f(-10f, -5.0f, 0.0f);

			gl.glColor3f(1.0f, 0.0f, 0.0f);
			gl.glVertex3f(10.0f, 0.0f, 0.0f);
			gl.glVertex3f(-10.0f, 5.0f, 0.0f);
			gl.glVertex3f(-10.0f, 0.0f, 5.0f);

			gl.glColor3f(0.0f, 1.0f, 0.0f);
			gl.glVertex3f(10.0f, 0.0f, 0.0f);
			gl.glVertex3f(-10.0f, 0.0f, 5.0f);
			gl.glVertex3f(-10.0f, -5.0f, 0.0f);

			gl.glColor3f(1.0f, 0.5f, 0.0f);
			gl.glVertex3f(-10.0f, 0.0f, 5.0f);
			gl.glVertex3f(-10.0f, 5.0f, 0.0f);
			gl.glVertex3f(-10.0f, -5.0f, 0.0f);

			gl.glEnd();
			gl.glPopMatrix();

			gl.glPopMatrix();
		}

		// Wraparound
		void borders() {
			if (loc.x < (-r - width))
				loc.x = width + r;
			if (loc.y < (-r - height))
				loc.y = height + r;
			if (loc.x > width + r)
				loc.x = (-r - width);
			if (loc.y > height + r)
				loc.y = (-r - height);
		}

		// Separation
		// Method checks for nearby boids and steers away
		PVector separate(ArrayList boids) {
			float desiredseparation = 20.0f;
			PVector steer = new PVector(0, 0, 0);
			int count = 0;
			// For every boid in the system, check if it's too close
			for (int i = 0; i < boids.size(); i++) {
				Boid other = (Boid) boids.get(i);
				float d = PVector.dist(loc, other.loc);
				// If the distance is greater than 0 and less than an arbitrary
				// amount (0 when you are yourself)
				if ((d > 0) && (d < desiredseparation)) {
					// Calculate vector pointing away from neighbor
					PVector diff = PVector.sub(loc, other.loc);
					diff.normalize();
					diff.div(d); // Weight by distance
					steer.add(diff);
					count++; // Keep track of how many
				}
			}
			// Average -- divide by how many
			if (count > 0) {
				steer.div((float) count);
			}

			// As long as the vector is greater than 0
			if (steer.mag() > 0) {
				// Implement Reynolds: Steering = Desired - Velocity
				steer.normalize();
				steer.mult(maxspeed);
				steer.sub(vel);
				steer.limit(maxforce);
			}
			return steer;
		}

		// Alignment
		// For every nearby boid in the system, calculate the average velocity
		PVector align(ArrayList boids) {
			float neighbordist = 25.0f;
			PVector steer = new PVector(0, 0, 0);
			int count = 0;
			for (int i = 0; i < boids.size(); i++) {
				Boid other = (Boid) boids.get(i);
				float d = PVector.dist(loc, other.loc);
				if ((d > 0) && (d < neighbordist)) {
					steer.add(other.vel);
					count++;
				}
			}
			if (count > 0) {
				steer.div((float) count);
			}

			// As long as the vector is greater than 0
			if (steer.mag() > 0) {
				// Implement Reynolds: Steering = Desired - Velocity
				steer.normalize();
				steer.mult(maxspeed);
				steer.sub(vel);
				steer.limit(maxforce);
			}
			return steer;
		}

		// Cohesion
		// For the average location (i.e. center) of all nearby boids, calculate
		// steering vector towards that location
		PVector cohesion(ArrayList boids) {
			float neighbordist = 25.0f;
			PVector sum = new PVector(0, 0); // Start with empty vector to
												// accumulate all locations
			int count = 0;
			for (int i = 0; i < boids.size(); i++) {
				Boid other = (Boid) boids.get(i);
				float d = PVector.dist(loc, other.loc);
				if ((d > 0) && (d < neighbordist)) {
					sum.add(other.loc); // Add location
					count++;
				}
			}
			if (count > 0) {
				sum.div((float) count);
				return steer(sum, false); // Steer towards the location
			}
			return sum;
		}
	}

	/*
	 * Example from the book "Pro Java 6 3D Game Development" from Andrew
	 * Davison to draw a Checkerboard with text.
	 */

	private void drawFloor(GL2 gl)
	/*
	 * Create tiles, the origin marker, then the axes labels. The tiles are in a
	 * checkboard pattern, alternating between green and blue.
	 */
	{
		gl.glDisable(GL_LIGHTING);

		drawTiles(BLUE_TILE, gl); // blue tiles
		drawTiles(GREEN_TILE, gl); // green

		gl.glEnable(GL_LIGHTING);
	} // end of CheckerFloor()

	private void drawTiles(int drawType, GL2 gl)
	/*
	 * Create a series of quads, all with the same colour. They are spaced out
	 * over a FLOOR_LEN*FLOOR_LEN area, with the area centered at (0,0) on the
	 * XZ plane, and y==0.
	 */
	{
		if (drawType == BLUE_TILE)
			gl.glColor3f(0.0f, 0.1f, 0.4f);
		else
			// green
			gl.glColor3f(0.0f, 0.5f, 0.1f);

		gl.glBegin(GL_QUADS);
		boolean aBlueTile;
		for (int z = -FLOOR_LEN / 2; z <= (FLOOR_LEN / 2) - 1; z++) {
			aBlueTile = (z % 2 == 0) ? true : false; // set colour type for new
														// row
			for (int x = -FLOOR_LEN / 2; x <= (FLOOR_LEN / 2) - 1; x++) {
				if (aBlueTile && (drawType == BLUE_TILE)) // blue tile and
															// drawing blue
					drawTile(x, z, gl);
				else if (!aBlueTile && (drawType == GREEN_TILE)) // green
					drawTile(x, z, gl);
				aBlueTile = !aBlueTile;
			}
		}
		gl.glEnd();
	} // end of drawTiles()

	private void drawTile(int x, int z, GL2 gl)
	/*
	 * Coords for a single blue or green square; its top left hand corner at
	 * (x,0,z).
	 */
	{
		// points created in counter-clockwise order
		gl.glVertex3f(x, 0.0f, z + 1.0f); // bottom left point
		gl.glVertex3f(x + 1.0f, 0.0f, z + 1.0f);
		gl.glVertex3f(x + 1.0f, 0.0f, z);
		gl.glVertex3f(x, 0.0f, z);
	} // end of drawTile()

}
