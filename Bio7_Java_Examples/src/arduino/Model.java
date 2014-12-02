package arduino;

/*
Adapted Serial example from the Arduino website using JavaFX panel for a simple GUI:
Example from: http://playground.arduino.cc/Interfacing/Java
*/
import javafx.fxml.FXML;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import com.eco.bio7.collection.*;
import javafx.event.ActionEvent;
import com.eco.bio7.batch.*;

public class Model extends com.eco.bio7.compile.Model {
	public  SerialTest serial;
	public Model main;
	
	public Model(){
		
		CustomView view = new CustomView();     
		view.setFxmlCanvas("Embedd", FileRoot.getFileRoot()+ "/Bio7_Java_Examples/src/arduino/Guil.fxml", this);
		serial = new SerialTest();
			
	}
	public void setup(){


	}
	
	

	public static void main(String[] args) {

		
	}
	
    // Handler for Button[Button[id=null, styleClass=button]] onAction
   @FXML public void startIt(ActionEvent event) {
        serial.initialize();
		
    }
	

    // Handler for Button[Button[id=null, styleClass=button]] onAction
    @FXML public void stopit(ActionEvent event) {
       serial.close();
    }
	
	
}