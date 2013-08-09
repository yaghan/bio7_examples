import processing.core.PApplet;
import javax.swing.*;
import java.awt.*;
import processing.core.*;
import processing.core.PApplet;

/*
 * Crazy Flocking 3D Birds 
 * by Ira Greenberg. 
 * 
 * Simulates a flock of birds using a Bird class and nested
 * pushMatrix() / popMatrix() functions. 
 * Trigonometry functions handle the flapping and sinuous movement.
 *
 */
 
 /*
 Changed for Bio7!
 See: http://processing.googlecode.com/svn/trunk/processing/build/javadoc/core/processing/core/PApplet.html
 */

// Flock array

int birdCount = 200;

Bird[]birds = new Bird[birdCount];
float[]x = new float[birdCount];
float[]y = new float[birdCount];
float[]z = new float[birdCount];
float[]rx = new float[birdCount];
float[]ry = new float[birdCount];
float[]rz = new float[birdCount];
float[]spd = new float[birdCount];
float[]rot = new float[birdCount];

PApplet embed;

public void setup() {
	
	embed = new Embedded();
	
    CustomView view=new CustomView();
    
	view.setPanel(embed, "PApplet");

	embed.init();
}

class Embedded extends PApplet {

     public void setup() {
        size(640, 360, P3D);
  noStroke();

  // Initialize arrays with random values
  for (int i = 0; i < birdCount; i++){
    birds[i] = new Bird(random(-300, 300), random(-300, 300), 
               random(-500, -2500), random(5, 30), random(5, 30)); 

    x[i] = embed.random(20, 340);
    y[i] = embed.random(30, 350);
    z[i] = embed.random(1000, 4800);
    rx[i] = embed.random(-160, 160);
    ry[i] = embed.random(-55, 55);
    rz[i] = embed.random(-20, 20);
    spd[i] = embed.random(.1f, 3.75f);
    rot[i] = embed.random(.025f, .15f);
  }
     }

     public void draw() {
        background(0);
  lights();
  for (int i = 0; i < birdCount; i++){
    birds[i].setFlight(x[i], y[i], z[i], rx[i], ry[i], rz[i]);
    birds[i].setWingSpeed(spd[i]);
    birds[i].setRotSpeed(rot[i]);
    birds[i].fly();
  }
     }

     public void mousePressed() {
         // do something based on mouse movement

         // update the screen (run draw once)
         redraw();
     }
 }
 
 class Bird {
  
  // Properties
  float offsetX, offsetY, offsetZ;
  float w, h;
  int bodyFill;
  int wingFill;
  float ang = 0, ang2 = 0, ang3 = 0, ang4 = 0;
  float radiusX = 120, radiusY = 200, radiusZ = 700;
  float rotX = 15, rotY = 10, rotZ = 5;
  float flapSpeed = 0.4f;
  float rotSpeed = 0.1f;

  // Constructors
  Bird(){
    this(0, 0, 0, 60, 80);
  }

  Bird(float offsetX, float offsetY, float offsetZ, 
  float w, float h){
    this.offsetX = offsetX;
    this.offsetY = offsetY;
    this.offsetZ = offsetZ;
    this.h = h;
    this.w = w;
    bodyFill = embed.color(153);
    wingFill = embed.color(204);
  }

  void setFlight(float radiusX, float radiusY, float radiusZ, 
    float rotX, float rotY, float rotZ){
    this.radiusX = radiusX;
    this.radiusY = radiusY;
    this.radiusZ = radiusZ;

    this.rotX = rotX;
    this.rotY = rotY;
    this.rotZ = rotZ;
  }

  void setWingSpeed(float flapSpeed){
    this.flapSpeed = flapSpeed;
  }

  void setRotSpeed(float rotSpeed){
    this.rotSpeed = rotSpeed;
  }

  void fly() {
    embed.pushMatrix();
    float px, py, pz;
    
    // Flight
    px = embed.sin(embed.radians(ang3)) * radiusX;
    py = embed.cos(embed.radians(ang3)) * radiusY;
    pz = embed.sin(embed.radians(ang4)) * radiusZ;
    
    embed.translate(embed.width/2 + offsetX + px, embed.height/2 + offsetY+py, -700 + offsetZ+pz);

    embed.rotateX(embed.sin(embed.radians(ang2)) * rotX);
    embed.rotateY(embed.sin(embed.radians(ang2)) * rotY);
    embed.rotateZ(embed.sin(embed.radians(ang2)) * rotZ);

    // Body
    embed.fill(bodyFill);
    embed.box(w/5, h, w/5);

    // Left wing
    embed.fill(wingFill);
    embed.pushMatrix();
    embed.rotateY(embed.sin(embed.radians(ang)) * 20);
    embed.rect(0, -h/2, w, h);
    embed.popMatrix();

    // Right wing
    embed.pushMatrix();
    embed.rotateY(embed.sin(embed.radians(ang)) * -20);
    embed.rect(-w, -h/2, w, h);
    embed.popMatrix();

    // Wing flap
    ang += flapSpeed;
    if (ang > 3) {
      flapSpeed*=-1;
    } 
    if (ang < -3) {
      flapSpeed*=-1;
    }

    // Ang's run trig functions
    ang2 += rotSpeed;
    ang3 += 1.25;
    ang4 += 0.55;
    embed.popMatrix();
  }
}