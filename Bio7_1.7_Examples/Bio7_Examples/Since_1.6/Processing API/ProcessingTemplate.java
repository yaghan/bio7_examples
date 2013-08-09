import processing.core.PApplet;
import javax.swing.*;
import java.awt.*;
import processing.core.*;
import processing.core.PApplet;

/*
Bio7 Java Template for Processing Java API!

See: http://processing.googlecode.com/svn/trunk/processing/build/javadoc/core/processing/core/PApplet.html

*/
public void setup(){
	PApplet embed = new Embedded();
	
	 CustomView view=new CustomView();
	 
     view.setPanel(embed,"PApplet");
     //jf.setVisible(true);
     embed.init();
}
/*Here starts the processing code!*/
class Embedded extends PApplet {

     public void setup() {
         // original setup code here ...
         size(400, 400);

         // prevent thread from starving everything else
        // noLoop();
     }

     public void draw() {
         // drawing code goes here
     }

     public void mousePressed() {
         // do something based on mouse movement

         // update the screen (run draw once)
         redraw();
     }
 }