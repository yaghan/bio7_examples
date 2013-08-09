import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import com.eco.bio7.time.Time;

public class PleaseProvideControllerClassName implements Initializable {
    	
    @FXML 
    private LineChart<?, ?> Line; // Value injected by FXMLLoader	
    	
    XYChart.Series series;   

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    	
    	Line.setTitle("Dynamic Random Data");
    	series = new XYChart.Series();
        series.setName("Random");   
        Line.getData().add(series);          

    }
    /*Called from the ecomain method in the main class!*/
    public void dynamic() {
    	
		if (series.getData().size() > 100) {
			
		series.getData().remove(0);
		
		}

		series.getData().add(new XYChart.Data("" + (Time.getCounter()+1), Math.random() * 100));
		

	}
}
