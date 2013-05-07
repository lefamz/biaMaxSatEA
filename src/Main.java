/**
File: Main.java
@author Michal Zajacik
Company: Agent Technology Center CVUT
Version: May 7, 2013
 */

import java.util.ArrayList;
import ea.Clause;
import ea.EvoAlg;
import ea.Individual;
import utils.MyConstants;
import utils.MyReader;

public class Main {

	private static boolean debug = MyConstants.debug;
	private static int popCap = MyConstants.popCap;
	
	public static void main(String[] args) {
		int nClauses = 0;
		int nLiterals = 0;
		ArrayList<Clause> formula;
		
		MyReader reader = new MyReader("problems/01.in", debug);
		reader.read();
		
		formula=reader.formula;
		nClauses=reader.nCl;
		nLiterals=reader.nLit;
		
		int n = 6;
		Individual ind = new Individual(n);
		Individual ind2 = new Individual(n);
		
		int pntr = 1;
		for (int i = 0; i < ind.values.length; i++) {
			ind.values[i]=(byte) pntr++;
		}
		
		for (int i = 0; i < ind2.values.length; i++) {
			ind2.values[i]=(byte) pntr++;
		}
		
		Individual[] cross = Individual.uniformXover(ind, ind2);
		
		System.out.println(ind);
		System.out.println(ind2);
		System.out.println(cross[0]);
		System.out.println(cross[1]);
		
		/*EvoAlg genEA = new EvoAlg(nClauses, nLiterals, formula, popCap);
		genEA.runGenerational();
		
		System.out.println(genEA.theBestOne + " fitness: " + genEA.theBestOne.fitness);
		*/
		
	}

}
