/*
A simple JavaFX example using the JavaFX API to create a simple Button!
A classbody compilation is necessary (see Preferences!).
*/
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXML;



public void setup() {
	
	CustomView view = new CustomView();
	view.setSceneCanvas("Hallo FX");
	Scene sc = createScene();
    view.addScene(sc);

}
private Scene createScene() {

	Group group = new Group();
	Button jfxButton = new Button("JFX Button");
	jfxButton.setId("ipad-dark-grey");
	group.getChildren().add(jfxButton);
	Scene scene = new Scene(group);

	return scene;

}

