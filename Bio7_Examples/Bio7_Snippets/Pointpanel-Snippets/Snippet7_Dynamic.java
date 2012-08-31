/*This snippet creates individuals dynamically in the Points panel! */

Individual[] individual = new Individual[1000];
public void ecomain() {

	PointPanel.delete();

	for (int i = 0; i < individual.length; i++) {
		double x = (Math.random() * 1000);
		double y = (Math.random() * 1000);
		int species = (int) (Math.random() * Bio7State.getStateSize());
		float alpha = (float) (Math.random() * 255);
		int diameter = (int) (Math.random() * 50);
		individual[i] = new Individual(x, y, species, diameter, alpha);

	}
	//Set the individuals in the Points panel - method repaints the Point panel !!
	PointPanel.setIndividual(individual);
	
}