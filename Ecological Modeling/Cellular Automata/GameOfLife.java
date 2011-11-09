/*
For the game of life soil=dead; select a plant as state alive!
Only the state arrays are used for this example!

Enable the setup method if you want to set default states.
After compilation you can call this method in the scripts menu.
Scripts->General-Scripts->Setup*/

/*
We import the Field so we can access the static methods of the field directly.
For example: getHeight() instead of Field.getHeight()
*/
import static com.eco.bio7.discrete.Field.*;




public int n = 1;//The neighbourhood value!

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

