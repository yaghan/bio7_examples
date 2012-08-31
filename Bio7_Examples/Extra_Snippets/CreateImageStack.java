import java.awt.*;
import java.io.*;
import ij.*;
import ij.gui.*;
import ij.process.*;
import ij.plugin.PlugIn;
import ij.io.Opener;
import javax.swing.SwingUtilities;
import ij.gui.StackWindow;
public ImageStack s;
public ImagePlus plus;
public ImageProcessor ip;
public ImagePlus imp;

/*
This example shows how you can create dynamic(rendered)graphs with the
R application which are added autmatically in the ImageJ view as a stack
(move the scrollbar to see the animated result)!.
The stack then can be saved as an *.avi file.

Important:
For this example the Rserve application has to be started!
Furthermore the setup method has to be executed(Scripts->General_Scripts->Setup)!

This example creates R plots. This plots then are opened
in the ImageJ view directly as a stack and can be saved as an *.avi file
or as an animated *.gif.

The default path is the path to the project!
Only one image is created. In this image the different plots are rendered
and then opened in the ImageJ view as a stack.

You can change this example to use for a custom dynamic plot.
Tested with 2000 plots (500,500)!
Only the heap is the limit (watch the heap with the garbage can icon in the status bar!)
The heap can be changed in the bio7.ini file (in the folder where Bio7.exe exist!)
with an (text)editor for more memory!
See also: http://www.eclipsezone.com/eclipse/forums/t61618.html

In addition see also the similar example in: 
"Ecological Modelling/Random/CreateImageStack_RandomWalk.java".
*/

String directory = null;
String f = null;
String root = null;

/*Here you can adjust the size!*/
int w=500;
int h=500;

public void setup(){
	
	/*The setup method which creates and opens an image in a stack!*/
	setupPlot();
}

public void plotInstructions(){
	try {
	
	/*-----------Here you can place your custom plot!---------------------------*/

			RServe.getConnection().eval("x<-c(1:100)");
			RServe.getConnection().eval("y<-c(1:100)");
			RServe.getConnection().eval("z<-matrix(runif(1:10000),100,100)");
			RServe.getConnection().eval("image(x,y,z);");

	/*------------------------------------------------------------------------*/
	}
	catch (RserveException e) {

			System.out.println(e.getRequestErrorDescription());
		}
}

public void ecomain() {
    /*The main method!*/
	openPlot();

}

public void openPlot() {
	if (RServe.isAlive()) {
		/*The root of the workspace*/
		root = FileRoot.getFileRoot();
		/*Get the path and convert it for R (Windows)*/
		f = root + "/Bio7_Examples/Extra_Snippets/plot.png";

		/*For Windows we have to replace "/" to "\\" !*/
		f = f.replace("/", "\\");

		/*Transfer path to R !*/

		try {
			RServe.getConnection().assign("fileroot", f);
			/*We create a *.png file in the project!*/
			RServe.getConnection().eval(
				"try(png(fileroot,width=" + w + ",height=" + h + "))");

			plotInstructions();

			/*We close the device!*/
			RServe.getConnection().eval("dev.off();");

		}

		catch (RserveException e) {

			System.out.println(e.getRequestErrorDescription());
		}
		/*Now we open the plot in ImageJ as a tab!*/

		Opener opener = new Opener();
		opener.setSilentMode(true);
		imp = opener.openImage(f);
		
        /*We need the processor of the image!*/
		ip = imp.getProcessor();
		
		/*Here we add a new slice to the stack!*/
		s.addSlice(null, ip);
		
		StackWindow sw = (StackWindow) WindowManager.getCurrentWindow();
		
		/*Here we update the scrollbar!*/
		sw.updateSliceSelector();

	} else {

		Bio7Action.stopCalculation();
		Bio7Dialog.message("Rserve application is not alive!");
		System.out.println("Rserve is not alive!");

	}
}

public void setupPlot() {
	if (RServe.isAlive()) {
		/*The root of the workspace*/
		root = FileRoot.getFileRoot();
		/*Get the path and convert it for R(Windows)*/
		f = root + "/Bio7_Examples/Extra_Snippets/plot.png";

		/*For Windows we have to replace "/" to "\\" !*/
		f = f.replace("/", "\\");

		/*Transfer path to R !*/

		try {
			RServe.getConnection().assign("fileroot", f);
			/*We create a *.png file in the project!*/
			RServe.getConnection().eval(
				"try(png(fileroot,width="+w+",height="+h+"))");

			plotInstructions();

			/*We close the device!*/
			RServe.getConnection().eval("dev.off();");

		}

		catch (RserveException e) {

			System.out.println(e.getRequestErrorDescription());
		}
		/*Now we open the plot in ImageJ as a tab!*/

		Opener o = new Opener();
		
		o.open(f);

		imp = WindowManager.getCurrentImage();
		
		ip = imp.getProcessor();
		
		/*We create a new image stack!*/
		s = new ImageStack(w, h);
		/*We add two dummy slices which are necessary to show the stack!*/
		s.addSlice(null, ip);
		s.addSlice(null, ip);
		
		/*Now we delete the plot image, we don't need it anymore!*/
		IJTabs.deleteActiveTab();
		
		/*We create an image object from the stack!*/
		plus = new ImagePlus("Stack", s);
		/*We make it visible!*/
		plus.show();

	} else {

		Bio7Action.stopCalculation();
		
		System.out.println("Rserve is not alive!");

	}
}