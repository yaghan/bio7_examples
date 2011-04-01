/*
We use the ImageJ API to easily create components for a custom
setup panel for a custom simulation etc. in a custom view of Bio7!
Please execute the setup action in "Scripts->General_Scripts->Setup"
to see the result!
*/

import ij.gui.GenericDialog;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;

String title;
int width;
int height;
int sliderValue1;
int sliderValue2;
GenericDialog gd;

public void setup() {
	
	JPanel jp = new JPanel(new BorderLayout());
	JButton jb = new JButton("Set Values");
	
   /*Here you can extend the panel with more components(see the ImageJ Api)!*/  
	gd = new GenericDialog("New Image");
	gd.addStringField("Title: ", title);
	gd.addNumericField("Width: ", width, 0);
	gd.addNumericField("Height: ", height, 0);
	gd.addSlider("Slider1", 1, 100, 50);
	gd.addSlider("Slider2", 1, 100, 50);
	gd.addNumericField("Width2: ", width, 0);
	gd.addNumericField("Height2: ", height, 0);
	jp.add(jb, BorderLayout.NORTH);
	jp.add(gd.getContentPane());
	CustomView.setPanel(jp, "custompanel");
	
    /*The action listener to set the values!*/
	jb.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(java.awt.event.ActionEvent evt) {
			/*Here we get the values!*/
			title = gd.getNextString();
			width = (int) gd.getNextNumber();
			height = (int) gd.getNextNumber();
			sliderValue1=(int) gd.getNextNumber();
			sliderValue2=(int) gd.getNextNumber();
			/*We print a few values!*/
			System.out.println(sliderValue1);
			System.out.println(sliderValue2);
			
			
			/*Important to reset the counter!*/
			gd.resetCounters();
		}
	});

}

public void ecomain(){
// Please enter your code here
}