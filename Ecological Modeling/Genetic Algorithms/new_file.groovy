// Please enter your Groovy code here
// initialize a new genetic algorithm
import org.apache.commons.math.genetics.*;
GeneticAlgorithm ga = new GeneticAlgorithm(new OnePointCrossover<Integer>(),1,new RandomKeyMutation(),0.10,new TournamentSelection(1));
        
// initial population
Population initial = getInitialPopulation();
        
// stopping condition
StoppingCondition stopCond = new FixedGenerationCount(10);
        
// run the algorithm
Population finalPopulation = ga.evolve(initial, stopCond);
        
// best chromosome from the final population
Chromosome bestFinal = finalPopulation.getFittestChromosome();

