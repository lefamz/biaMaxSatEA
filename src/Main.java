/**
File: Main.java
@author Michal Zajacik
Company: Agent Technology Center CVUT
Version: May 7, 2013
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import ea.Clause;
import ea.EvoAlg;
import utils.MyConstants;
import utils.MyReader;

public class Main {

	private static boolean debug = MyConstants.debug;
	private static int popCap = MyConstants.popCap;
	private static byte replacementStrategy = MyConstants.generational;
	
	/**/private static byte crossoverStrategy = MyConstants.onePointXover;/**/
	/*private static byte crossoverStrategy = MyConstants.twoPointXover;/**/
	/*private static byte crossoverStrategy = MyConstants.uniformXover;/**/
	
	// generat/steady, 3 ways of crossover, logging 3 variables: best fitness average fitness num of iter, 3 experiments, repeat experiment 5 times
	private static double[][][][][] results = new double[2][3][7][3][10];
	private static double[][][][][] meanIterConv = new double[2][3][7][10][MyConstants.epochCap + 2];
	
	public static void main(String[] args) {
		
		int nClauses = 0;
		int nLiterals = 0;
		ArrayList<Clause> formula;
		
		
		for (int i = 0; i < 2; i++) {
			if(i==1) replacementStrategy = MyConstants.steadyState;
			System.out.println("Replacement strategy: " + replacementStrategy);
			
			for (int j = 0; j < 3; j++) {
				if(j==1) crossoverStrategy=MyConstants.twoPointXover;
				else if(j==2) crossoverStrategy=MyConstants.uniformXover;
				
				System.out.println("Replacement strategy: " + crossoverStrategy);
				
				for (int k = 0; k < 7; k++) {
					MyReader reader = new MyReader("problems/"+k+".in", debug);
					System.out.println("Problem: " + k);
					
					reader.read();
					
					formula=reader.formula;
					nClauses=reader.nCl;
					nLiterals=reader.nLit;
					
					for (int l = 0; l < 10; l++) {
						
						System.out.println("Run: " + l);
						
						EvoAlg genEA = new EvoAlg(nClauses, nLiterals, formula, popCap, replacementStrategy,crossoverStrategy);
						genEA.run();
						
						if(genEA.iterations > 0 && genEA.iterations < 1001){
							System.out.println("---------------------------------------");
							System.out.println(genEA.theBestOne + " fitness: " + genEA.theBestOne.fitness);
							System.out.println("Epochs: " + (genEA.iterations));
						}

						System.out.println(k+".in: Epochs " + (genEA.iterations));
						
						// 0 .. iterations
						results[i][j][k][0][l]=(int)genEA.theBestOne.fitness;
						// 1 .. best fitness
						results[i][j][k][1][l]=genEA.averageFitness;
						// 2 .. average fitness
						results[i][j][k][2][l]=(int)genEA.iterations;
						
						meanIterConv[i][j][k][l] = genEA.averageFitneses;
						
					}
					
				    
				}
			}
			
		}
		
		saveResultsToFile("problems/solution.out");
			
	}
	
	private static void saveResultsToFile(String filename){
		FileWriter fw;
		try {
			fw = new FileWriter(filename);
			BufferedWriter buff = new BufferedWriter(fw);
			PrintWriter pr = new PrintWriter(buff);
			
			for (int i = 0; i < 2; i++) {
				if(i==0) pr.println("Generational\n=================================================================");
				else pr.println("Steady-state\n=====================================================================");
				
				for (int j = 0; j < 3; j++) {
					if(j==0) pr.println("==> OnePointXover: ===========================");
					else if(j==1) pr.println("==> TwoPointXover: ===========================");
					else pr.println("==> UniformXover: ===========================");
					
					for (int j2 = 0; j2 < 7; j2++) {
						pr.println("======> Problem: " + j2+".in");
						
						for (int k = 0; k < 10; k++) {
							pr.println(results[i][j][j2][0][k] + " " + results[i][j][j2][1][k] + " " + results[i][j][j2][2][k]);
						}
						pr.println("\nMean fitness during iterations:");
						for (int k = 0; k < 10; k++) {
							for (int k2 = 0; k2 < MyConstants.epochCap+2; k2++) {
								pr.print(meanIterConv[i][j][j2][k][k2] + " ");
							}
							pr.println();
							
						}
						
					}
				}
			}
			
			pr.flush();
			pr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
