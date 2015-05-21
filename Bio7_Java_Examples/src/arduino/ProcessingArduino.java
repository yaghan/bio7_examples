package arduino;

import com.eco.bio7.batch.Bio7Dialog;

import cc.arduino.*;
import processing.core.PApplet;

public class ProcessingArduino extends com.eco.bio7.compile.Model {

	public Arduino arduino;
	public int pin = 13;

	/*
	 * Bio7 Java Template for Processing Java API!
	 * 
	 * See:
	 * http://processing.googlecode.com/svn/trunk/processing/build/javadoc/core
	 * /processing/core/PApplet.html Adapted example from:
	 * http://playground.arduino.cc/Interfacing/Processing
	 */

	/* Please install the Arduino IDE for the driver installation of the ports!
	 * Please press setup again and dispose the Arduino class before you recompile! Else a restart of Bio7 is necessary to open the port!
	 */
	public void setup() {

		if (arduino != null) {
			arduino.dispose();
			arduino = null;
			System.out.println("Closed port!");
		} else {
			PApplet embed = new Embedded();
			Bio7Dialog.message("Please press setup again and dispose the Arduino class before you recompile else a restart of Bio7 is necessary to reopen the port!");
			embed.init();
			
		}

	}

	/* Here starts the processing code! */
	class Embedded extends PApplet {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void setup() {

			println(Arduino.list());
			// arduino = new Arduino(this, Arduino.list()[0], 57600);
			arduino = new Arduino(this, "COM4", 57600);
			arduino.pinMode(pin, Arduino.OUTPUT);
			
		}

		public void draw() {
			if (arduino != null) {
				arduino.digitalWrite(pin, Arduino.HIGH);
				delay(1000);
				if (arduino != null) {
					arduino.digitalWrite(pin, Arduino.LOW);
					/* Read from an analog pin! */
					int aRead = arduino.analogRead(pin);
					System.out.println(aRead);
				}
			}
		}

	}
}