package pointpanel;

import com.eco.bio7.database.Bio7State;
import com.eco.bio7.image.Individual;
import com.eco.bio7.image.PointPanel;

/*This snippet creates individuals dynamically in the Points panel! */
public class Snippet7_Dynamic extends com.eco.bio7.compile.Model {

	Individual[] individual = new Individual[1000];

	public void run() {

		PointPanel.delete();

		for (int i = 0; i < individual.length; i++) {
			double x = (Math.random() * 1000);
			double y = (Math.random() * 1000);
			int species = (int) (Math.random() * Bio7State.getStateSize());
			float alpha = (float) (Math.random() * 255);
			int diameter = (int) (Math.random() * 50);
			individual[i] = new Individual(x, y, species, diameter, alpha);

		}
		// Set the individuals in the Points panel - method repaints the Point
		// panel !!
		PointPanel.setIndividual(individual);

	}
}