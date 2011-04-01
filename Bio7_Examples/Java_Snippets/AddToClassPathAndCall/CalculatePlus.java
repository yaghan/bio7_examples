/*A simple random function.
Use together with CalculatePlus and Snippet1_Compiler!*/

public void ecomain() {
	for (int i = 0; i < Field.getHeight(); i++) {

		for (int u = 0; u < Field.getWidth(); u++) {

			Field.setState(u,i,(int) (Math.random() * Bio7State.getStateSize()));

		}
	}

}