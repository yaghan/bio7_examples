import static com.eco.bio7.discrete.Field.*; 

/*
You can now compile full classes and e.g. its subclasses.
Select full class compilation in the preferences (Preferences Bio7->Preferences Java) of Bio7!

 */

public class Model extends com.eco.bio7.compile.Model {
	
public int n = 1;//The neighbourhood value!
public int n2 = 1;//The neighbourhood value!

	public void setup() {
		
	}
    /*Game of Life!*/
    public void ecomain() {

	for (int i = 0; i < getHeight(); i++) {

		for (int u = 0; u < getWidth(); u++) {
						
			
			/*Special method to sum up the neighbourhood without the center cell
			(Bio7 API)!*/
			int sum = torusMooreSum(n, u, i);
			
			if (sum == 2 && getState(u,i) == 1) {
				setTempState(u,i,1);
			} 
			else if (sum == 3 && getState(u,i) == 0) {
				
				setTempState(u,i,1);
			} 
			else if (sum == 3 && getState(u,i) == 1) {
				
				setTempState(u,i,1);
				
			} else {
				
				setTempState(u,i,0);
			}
		}

	}
	
	/*We copy the values from the temporary state array to the state array!*/
	for (int i = 0; i < getHeight(); i++) {

		for (int u = 0; u < getWidth(); u++) { 

			setState(u,i,getTempState(u,i));
		}
	}
  }
}