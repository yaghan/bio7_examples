/*
This Snippet produces uniformly distributed random numbers 
with a mersenne twister random generator of the colt library !
*/
import cern.jet.random.*;
import cern.jet.random.engine.*;
import java.util.Date;
public MersenneTwister twist=new MersenneTwister(new Date());
public Uniform uni=new Uniform(0.0,100.0,twist);
//
public int random;

public void ecomain(){
	/*We only want to get integers !*/
	random = (int) uni.nextDouble();
	System.out.println(random);

}