package processing;

import processing.core.PApplet;
import com.eco.bio7.collection.CustomView;
import processing.core.*;
import java.util.ArrayList;

public class Flocking extends com.eco.bio7.compile.Model {

	PApplet embed;
	Flock flock;

	/*
	 * Flocking by Daniel Shiffman.
	 * 
	 * An implementation of Craig Reynold's Boids program to simulate the
	 * flocking behavior of birds. Each boid steers itself based on rules of
	 * avoidance, alignment, and coherence.
	 * 
	 * Click the mouse to add a new boid.
	 */

	/*
	 * Changed for Bio7!
	 * 
	 * Info: Added embed.width instead of width (instance variable of PApplet
	 * needed). Added 2.0f instead of 2.0 for all floats (Java requirement)!
	 * 
	 * See:
	 * http://processing.googlecode.com/svn/trunk/processing/build/javadoc/core
	 * /processing/core/PApplet.html
	 */

	public void setup() {

		embed = new Embedded();

		CustomView view = new CustomView();

		view.setPanel(embed, "PApplet");
		// jf.setVisible(true);
		embed.init();
	}

	/* Here starts the processing code! */
	class Embedded extends PApplet {

		public void setup() {
			size(640, 360);
			flock = new Flock();
			// Add an initial set of boids into the system
			for (int i = 0; i < 150; i++) {
				flock.addBoid(new Boid(new PVector(embed.width / 2,
						embed.height / 2), 3.0f, 0.05f));
			}
			smooth();
		}

		public void draw() {
			background(50);
			flock.run();
		}

		public void mousePressed() {
			flock.addBoid(new Boid(new PVector(mouseX, mouseY), 2.0f, 0.05f));

			// update the screen (run draw once)
			redraw();
		}
	}

	// The Flock (a list of Boid objects)

	class Flock {
		ArrayList boids; // An arraylist for all the boids

		Flock() {
			boids = new ArrayList(); // Initialize the arraylist
		}

		void run() {
			for (int i = 0; i < boids.size(); i++) {
				Boid b = (Boid) boids.get(i);
				b.run(boids); // Passing the entire list of boids to each boid
								// individually
			}
		}

		void addBoid(Boid b) {
			boids.add(b);
		}

	}

	// The Boid class

	class Boid {

		PVector loc;
		PVector vel;
		PVector acc;
		float r;
		float maxforce; // Maximum steering force
		float maxspeed; // Maximum speed

		Boid(PVector l, float ms, float mf) {
			acc = new PVector(0, 0);
			vel = new PVector(embed.random(-1, 1), embed.random(-1, 1));
			loc = l.get();
			r = 2.0f;
			maxspeed = ms;
			maxforce = mf;
		}

		void run(ArrayList boids) {
			flock(boids);
			update();
			borders();
			render();
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
				if ((slowdown) && (d < 100.0))
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

		void render() {
			// Draw a triangle rotated in the direction of velocity
			float theta = vel.heading2D() + embed.PI / 2;
			embed.fill(200, 100);
			embed.stroke(255);
			embed.pushMatrix();
			embed.translate(loc.x, loc.y);
			embed.rotate(theta);
			embed.beginShape(embed.TRIANGLES);
			embed.vertex(0, -r * 2);
			embed.vertex(-r, r * 2);
			embed.vertex(r, r * 2);
			embed.endShape();
			embed.popMatrix();
		}

		// Wraparound
		void borders() {
			if (loc.x < -r)
				loc.x = embed.width + r;
			if (loc.y < -r)
				loc.y = embed.height + r;
			if (loc.x > embed.width + r)
				loc.x = -r;
			if (loc.y > embed.height + r)
				loc.y = -r;
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
				float d = loc.dist(other.loc);
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
}
