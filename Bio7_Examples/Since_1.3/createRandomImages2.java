/*
This example calls a RScript which produces a raw vector and transfers
it to ImageJ as an greyscale image. One image works as a container.
Please start the R server!
 */

public void ecomain() {
	if (RServe.isAlive()) {
		if (RState.isBusy() == false) {
			/*Get the relative path to the script!q*/
			RServe.callRScript("/Bio7_Examples/Since_1.3/randomScript.R");

			/*
			Create the byte image directly in an ImageJ image and update
			this image with the new values!
			 */
			/*Special method which only transfers raws*/
			ImageMethods.transferImageInPlace("imageMatrix");
			
		} else {
			System.out.println("RServer is busy!");
		}
	} 
	else {
		System.out.println("RServer is not alive!");
	}
}