/**
File: GenerationalEA.java
@author Michal Zajacik
Company: Agent Technology Center CVUT
Version: May 7, 2013
*/

package ea;

import java.util.ArrayList;

public class EvoAlg {
	private int nClauses;
	private int nLiterals;
	private int popCap;
	private ArrayList<Clause> formula;
	
	public static Individual theBestOne;
	public static ArrayList<Individual> population;
	public static long iterations = 0;
	
	public EvoAlg(int nClauses, int nLiterals, ArrayList<Clause> formula, int popCap) {
		this.nClauses = nClauses;
		this.nLiterals = nLiterals;
		this.formula = formula;
		this.popCap = popCap;
		
		population = new ArrayList<Individual>(popCap);
	}
	
	public void runGenerational(){
		genPop();
		
	}
	
	public void runSteadyState(){
		genPop();
	}
	
	private void genPop(){
		for (int i = 0; i < popCap; i++) {
			Individual ind = new Individual(nLiterals);
			ind.randInit();
			population.add(ind);
		}
	}
	
	
}
