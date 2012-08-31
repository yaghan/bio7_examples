import javax.swing.*;
/*
BeanShell Objects can be called when available in the BeanShell namespace. 
In this example an JFrame Object with name "frame" has to be created in BeanShell!
Type in the Bsh-Console:

JFrame frame=new JFrame();
frame.setSize(300,300);

Select this file with a right-click of the mouse.
Select action "Setup" to see the result!"*/



public void setup() {

	JFrame f = (JFrame)Interpreter.getObject("frame");
	f.setSize(300,300);
	f.setVisible(true);
}