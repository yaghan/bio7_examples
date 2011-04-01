/*This snippet shows how to compile an BeanShell interpreter call.
  The sources (Calculate.java and CalculatePlus.java)need to be 
  compiled by rightclick on the *.java navigator files
  (multiple files allowed) and the execution of the submenu-action of "Add java to bsh classpath".
  After compilation the interpreter can be used to execute the compiled (faster)
  methods directly from the main loop of the program!
  Please select the files Calculate.java and CalculatePlus.java
  (multiple selection allowed !)and then compile and run this class.
  
*/
import bsh.EvalError;

public void ecomain(){
Interpreter.doInterpret("Calculate.ecomain();",null);
Interpreter.doInterpret("CalculatePlus.ecomain();",null);

}