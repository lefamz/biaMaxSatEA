import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;

import utils.MyConstants;

/**
File: formulaGenerator.java
@author Michal Zajacik
Company: Agent Technology Center CVUT
Version: May 8, 2013
 */

public class formulaGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int clauses = 3500;
		int variables = 28;
		int nProlems = MyConstants.nProblem;
		int minLenOfClause = 5;
		Random random = new Random();
		
		try{
			
		for (int i = 0; i < nProlems; i++) {
			String filename="problems/"+Integer.toString(i)+".in";
			FileWriter fw = new FileWriter(filename);
			BufferedWriter buff = new BufferedWriter(fw);
			PrintWriter pr = new PrintWriter(buff);
			
			pr.println(variables + " " + clauses);
			
			for (int k = 0; k < clauses; k++) {
				int len = minLenOfClause+random.nextInt(variables-minLenOfClause+1);
				for (int j = 0; j < len; j++) {
					pr.print(random.nextInt(variables) + " ");
				}
				pr.println();
				for (int j = 0; j < len; j++) {
					pr.print(random.nextInt(2) + " ");
				}
				pr.println();
			}
			
			pr.flush();
			pr.close();
			
			
		}
		
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
