public void ecomain(){
// Please enter your code here
for (int y = 0; y < Field.sizey; y++) {
	for (int x = 0; x < Field.sizex; x++) { 
		Field.xyold[y][x]=(int)(Math.random()*State.getStateSize());
       }
  }
}