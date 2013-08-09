import cern.jet.random.tfloat.FloatUniform;
import cern.jet.random.tfloat.engine.FloatMersenneTwister;
import java.util.Date;

/*
This Snippet produces uniformly distributed random numbers 
with a mersenne twister random generator of the colt library !
*/

private static FloatUniform uni = new FloatUniform(0.0f, 100.0f, new FloatMersenneTwister(new java.util.Date()));
//
public float random;

public void ecomain(){
	/*We only want to get integers !*/
	random  = uni.nextFloat();
	System.out.println(random);

}