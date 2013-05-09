/**
File: GenerationalEA.java
@author Michal Zajacik
Company: Agent Technology Center CVUT
Version: May 7, 2013
*/

package ea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import utils.MyConstants;

public class EvoAlg {
	private int nClauses;
	private int nLiterals;
	private int popCap;
	private ArrayList<Clause> formula;
	
	public Individual theBestOne;
	public Individual[] population;
	public double averageFitneses[];
	public long iterations = 0;
	public double averageFitness = 0;
	private byte replacementStrategy;
	private byte crossoverStrategy;
	
	public EvoAlg(int nClauses, int nLiterals, ArrayList<Clause> formula, int popCap, byte replacementStrategy, byte crossoverStrategy) {
		this.nClauses = nClauses;
		this.nLiterals = nLiterals;
		this.formula = formula;
		this.popCap = popCap;
		
		population = new Individual[popCap];
		averageFitneses = new double[MyConstants.epochCap+2];
		this.replacementStrategy = replacementStrategy;
		this.crossoverStrategy = crossoverStrategy;
 	}
	
	public void run(){
		if (replacementStrategy==MyConstants.generational){
			runGenerational();
		} else if (replacementStrategy == MyConstants.steadyState){
			runSteadyState();
		} else {
			System.err.println("Unknown replacement strategy.");
		}
	}
	
	private void runGenerational(){
		genPop();
		averageFitness = getFitnessPop();
		averageFitneses[0]=averageFitness;
		Arrays.sort(population);
		theBestOne = population[0];
		
		if(MyConstants.debug) System.out.println("Best: " + theBestOne + " fitness:" + theBestOne.fitness);
		
		while(theBestOne.fitness != nClauses && iterations <= MyConstants.epochCap){
			if(MyConstants.debug) System.out.println("New epoch: " + iterations);
			
			iterations++;
			Individual nextPop[] = new Individual[popCap];
			 
			// we preserve the best MyConstants.preserve individuals
			for (int i = 0; i < MyConstants.preserve; i++) {
				nextPop[i]=population[i];
			}
			
			// fill the rest of new population with tournament selection
			for (int i = MyConstants.preserve; i < nextPop.length; i++) {
				nextPop[i] = tournamentSelection();
			}
			
			population = nextPop;
			 
			// crossover
			for (int i = MyConstants.preserve; i < popCap; i++) {
				if(MyConstants.random.nextDouble() <= MyConstants.prCross){
					int from=MyConstants.preserve;
					int range = popCap - MyConstants.preserve - 1;
					
					int papa = from+MyConstants.random.nextInt(range);
					int mama = from+MyConstants.random.nextInt(range);
					
					Individual[] offspring = null;
					
					if(crossoverStrategy == MyConstants.onePointXover){
						offspring = Individual.onePointXover(population[mama], population[papa]);
					} else if (crossoverStrategy == MyConstants.twoPointXover){
						offspring = Individual.twoPointXover(population[mama], population[papa]);
					} else {
						offspring = Individual.uniformXover(population[mama], population[papa]);
					}
					
					// replace the parents with offspring
					population[mama] = offspring[0];
					population[papa] = offspring[1];
				}
			}
			
			// mutation
			for (int i = MyConstants.preserve; i < nextPop.length; i++) {
				if(MyConstants.random.nextDouble() <= MyConstants.prMut){
					population[i].mutate();
				}
			}
			
			averageFitness = getFitnessPop();
			averageFitneses[(int)iterations]=averageFitness;
			// sort population according to the fittness
			Arrays.sort(population);
			theBestOne = population[0];
			if(MyConstants.debug) System.out.println("Best: " + theBestOne + " fitness:" + theBestOne.fitness);
		}
		
	}
	
	private void runSteadyState(){
		genPop();
		averageFitness = getFitnessPop();
		averageFitneses[0]=averageFitness;
		Arrays.sort(population);
		theBestOne = population[0];
		
		if(MyConstants.debug) System.out.println("Best: " + theBestOne + " fitness:" + theBestOne.fitness);
		
		while(theBestOne.fitness != nClauses && iterations <= MyConstants.epochCap){
			if(MyConstants.debug) System.out.println("New epoch: " + iterations);
			
			iterations++;
		//	Individual nextPop[] = new Individual[popCap];
			 
			ArrayList<Individual> nextPop = new ArrayList<Individual>();
			
			for (int i = 0; i < popCap; i++) {
				nextPop.add(population[i]);
			}
			 
			// crossover
			for (int i = 0; i < popCap; i++) {
				if(MyConstants.random.nextDouble() <= MyConstants.prCross){
					
					int papa = MyConstants.random.nextInt(popCap);
					int mama = MyConstants.random.nextInt(popCap);
					
					Individual[] offspring = null;
					
					if(crossoverStrategy == MyConstants.onePointXover){
						offspring = Individual.onePointXover(population[mama], population[papa]);
					} else if (crossoverStrategy == MyConstants.twoPointXover){
						offspring = Individual.twoPointXover(population[mama], population[papa]);
					} else {
						offspring = Individual.uniformXover(population[mama], population[papa]);
					}
					
					offspring[0].calcFitness(formula);
					offspring[1].calcFitness(formula);
					// add offspring to population
					nextPop.add(offspring[0]);
					nextPop.add(offspring[1]);
					
				}
			}
			
			// mutation
			for (int i = 0; i < nextPop.size(); i++) {
				if(MyConstants.random.nextDouble() <= MyConstants.prMut){
					int mutant = MyConstants.random.nextInt(popCap);
					nextPop.get(mutant).mutate();
					nextPop.get(mutant).calcFitness(formula);
				}
			}
			
			Collections.sort(nextPop);
			
			// cut to the pop limit size
			for (int i = 0; i < popCap; i++) {
				population[i]=nextPop.get(i);
			}
			
			averageFitness = getAvFitnessPop();
			averageFitneses[(int)iterations]=averageFitness;
			
			theBestOne = population[0];
			if(MyConstants.debug) System.out.println("Best: " + theBestOne + " fitness:" + theBestOne.fitness);
		}
	}
	
	private void genPop(){
		for (int i = 0; i < popCap; i++) {
			Individual ind = new Individual(nLiterals);
			ind.randInit();
			ind.calcFitness(formula);
			population[i]=ind;
		}
	}
	
	private double getAvFitnessPop(){
		double averageFitness = 0;
		for (int i = 0; i < popCap; i++) {
			averageFitness+=population[i].fitness;
		}
		
		return averageFitness/popCap;
	}
	
	
	private double getFitnessPop(){
		double averageFitness = 0;
		for (int i = 0; i < popCap; i++) {
			population[i].calcFitness(formula);
			averageFitness+=population[i].fitness;
		}
		
		return averageFitness/popCap;
	}
	
	private Individual tournamentSelection(){
		Individual i1 = population[MyConstants.random.nextInt(popCap)];
		Individual i2 = population[MyConstants.random.nextInt(popCap)];
		Individual i3 = population[MyConstants.random.nextInt(popCap)];
		
		Individual chosenOne = null;
		
		if(i1.fitness > i2.fitness){
			chosenOne = i1;
		} else {
			chosenOne = i2;
		}
		
		if(chosenOne.fitness < i3.fitness){
			chosenOne = i3;
		}
		
		return chosenOne;
	}
	
	
	
	
}
