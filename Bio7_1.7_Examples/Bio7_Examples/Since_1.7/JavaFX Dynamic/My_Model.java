/*
This example demonstrates the use of JavaFX embedded in a custom view of Bio7 for dynamic data.
For a successful compilation switch to the full class(es) compilation in the Java preferences of Bio7.

After the compilation a custom view will be opened.
 
Invoke the Start/Stop action in the main toolbar to plot random
values dynamically!

*/
import javafx.fxml.FXML;
import com.eco.bio7.collection.*;
import com.eco.bio7.batch.*;
import javafx.application.Platform;

public class My_Model extends com.eco.bio7.compile.Model {

	PleaseProvideControllerClassName p = new PleaseProvideControllerClassName();

	public  My_Model() {

		CustomView view = new CustomView();

		view.setFxmlCanvas("dynamic", FileRoot.getFileRoot()+ "/Bio7_Examples/Since_1.7/JavaFX Dynamic/My_Model.fxml", p);

	}

	public void ecomain() {

		Platform.runLater(new Runnable() {

			public void run() {
				p.dynamic();
			}
		});
	}
}