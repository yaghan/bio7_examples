/*
This example shows how to get voronoi areas dynamically from the 
points in the Points panel. It is necessary to adjust the area
with a bigger size to avoid values which causes an error in RServe.
An inproved check could avoid this !
The data is also transferred to a double array of Java (d)!
Start the R server to execute this example !*/



Individual[] plantpoint = new Individual[100];
boolean library=false;
public double []d;
static RConnection c = RServe.getConnection();

public void ecomain() {
	if (RServe.getConnection() != null) {
		if (library == false) {
			try {
				// Call the R library tripack one time!
				c.eval("try(library(tripack))");

			} catch (RserveException e) {

				System.out.println(e.getRequestErrorDescription());
			}
			library = true;
		}
		/*Create random points in a big area inside the Points panel  
		  to avoid a crashing of RServe!*/

		PointPanel.delete();

		for (int i = 0; i < 100; i++) {
			int x = (int) (Math.random() * 10000);
			int y = (int) (Math.random() * 10000);
			int species = (int) (Math.random() * Bio7State.getStateSize());
			float alpha = 255;
			int diameter = 100;
			plantpoint[i] = new Individual(y, x, species, diameter, alpha);

		}
		PointPanel.setIndividual(plantpoint);
		double xydia[][] = PointPanel.pointToArray();
		//Send the values to R!

		try {
			c.assign("x", xydia[0]);
			c.assign("y", xydia[1]);
		} catch (REngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			// Call the voronoi function in R!
			c.eval("try(tri.vm <- voronoi.mosaic(x,y,duplicate=\"remove\"))");
			c.eval("try(tri.vm.areas <- voronoi.area(tri.vm))");
			// Print the areas to the console of Bio7
			RServe.print("tri.vm.areas");
			// Print a summary !
			RServe.print("summary(tri.vm.areas)");

			/*Transfer the data to Java !*/
			d = c.eval("tri.vm.areas").asDoubles();

		} catch (REXPMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RserveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} else {
		System.out.println("Not connected to Rserve !");
	}
}