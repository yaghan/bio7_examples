/*A simple example to show how to produce and set random cells without to set
 any individual plant-data information from the database. */

int rand; // A random number variable!

public void ecomain() {
	for (int y = 0; y < Field.getHeight(); y++) {

		for (int x = 0; x < Field.getWidth(); x++) {
			rand = (int) (Math.random() * Bio7State.getStateSize());
			Field.setState(x,y,rand);
			Field.setTempState(x,y,rand);

		}
	}

}