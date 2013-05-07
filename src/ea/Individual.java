/**
File: Individual.java
@author Michal Zajacik
Company: Agent Technology Center CVUT
Version: May 7, 2013
*/

package ea;

import java.util.ArrayList;
import java.util.Arrays;

import utils.MyConstants;

public class Individual implements Comparable<Individual> {
	public int fitness;
	public byte[] values;
	
	public Individual(int n) {
		fitness = 0;
		values = new byte[n];
	}
	
	/** fitness is number of satisfied clauses */
	public void calcFitness(ArrayList<Clause> clauses){
		fitness = 0;
		
		for (Clause clausule : clauses) {
			fitness+=clausule.eval(values);
		}
	}
	
	/** randomly assignes values to all variables */
	public void randInit(){
		for (int i = 0; i < values.length; i++) {
			
			if(MyConstants.random.nextDouble()>0.5){
				values[i]=1;
			} else {
				values[i]=0;
			}
		}
	}
	
	/** Mutation procedure. We allways try to mutate n=ceil(1/6) of literals. Mutation of one literal is done with probability 1/n. */
	public void mutate(){
		int len = this.values.length;
		int n = (int)Math.ceil(((double)len)/6);
		
		for (int i = 0; i < n; i++) {
			if(MyConstants.random.nextDouble() <= 1.00/n){
				int which = MyConstants.random.nextInt(len);
				this.values[which]=(byte)(1-this.values[which]);	
			}
			
		}
	}
	
	/** 1-point crossover, produces two offsprings */
	public static Individual[] onePointXover(Individual mama, Individual papa){
		Individual[] offspring = new Individual[2];
		
		int len = mama.values.length;
		int half = len/2;
		
		Individual brother = new Individual(len);
		Individual sister = new Individual(len);
		
		// first half from mama, other half from papa
		System.arraycopy(mama.values, 0, brother.values, 0, half);
		System.arraycopy(papa.values, half, brother.values, half, len-half);
		
		// first half from papa, second half from mama
		System.arraycopy(papa.values, 0, sister.values, 0, half);
		System.arraycopy(mama.values, half, sister.values, half, len-half);
		
		offspring[0]=brother;
		offspring[1]=sister;
		
		return offspring;
	}
	
	/** 2-point crossover, produces two offsprings */
	public static Individual[] twoPointXover(Individual mama, Individual papa){
		Individual[] offspring = new Individual[2];
		
		int len = mama.values.length;
		int third = len/3;
		
		Individual brother = new Individual(len);
		Individual sister = new Individual(len);
		
		// middle third from papa, rest from mama
		System.arraycopy(mama.values, 0, brother.values, 0, len);
		System.arraycopy(papa.values, third, brother.values, third, third);
		
		// middle third from mama, rest from papa
		System.arraycopy(papa.values, 0, sister.values, 0, len);
		System.arraycopy(mama.values, third, sister.values, third, third);
		
		offspring[0]=brother;
		offspring[1]=sister;
		
		return offspring;
	}
	
	/** Uniform crossover, produces two offsprings */
	public static Individual[] uniformXover(Individual mama, Individual papa){
		Individual[] offspring = new Individual[2];
		
		int len = mama.values.length;
		
		Individual brother = new Individual(len);
		Individual sister = new Individual(len);
		
		for (int i = 0; i < len; i++) {			
			if(MyConstants.random.nextDouble()>0.5){
				brother.values[i]=mama.values[i];
			} else{
				brother.values[i]=papa.values[i];
			}
		}
		
		for (int i = 0; i < len; i++) {
			if(MyConstants.random.nextDouble()>0.5){
				sister.values[i]=mama.values[i];
			} else{
				sister.values[i]=papa.values[i];
			}
		}
		
		offspring[0]=brother;
		offspring[1]=sister;
		
		return offspring;
	}
	
	@Override
	public String toString(){
		return new String(Arrays.toString(values));
	}
	
	@Override
	public int compareTo(Individual o) {
		return Double.compare(this.fitness, o.fitness);
	}

}
