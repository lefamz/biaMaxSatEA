/**
File: Formula.java
@author Michal Zajacik
Company: Agent Technology Center CVUT
Version: May 7, 2013
*/

package ea;


public class Clause {
	public int[][] literals;

	public Clause(int len) {
		literals = new int[2][len];
	}
	
	/** finds whether this clause is satisfied (return 1) or not*/
	public byte eval(byte[] values){
		
		boolean atLeastOneTrue = false;
		
		for (int i = 0; i < literals[1].length; i++) {
			int lit = literals[0][i];
			int val = values[lit];
			
			int res;
			
			if(literals[1][i] == 1){
				res = val;
			} else {
				// literal in negative form, so we take the inverse value
				res = (byte)(1-val);
			}
			
			if(res == 1){
				atLeastOneTrue = true;
				break;
			}
			
		}
		
		if(atLeastOneTrue){
			return 1;
		}
		
		return 0;
	}
	
	@Override
	public String toString(){
		StringBuilder s = new StringBuilder();
		
		for (int i = 0; i < literals[0].length; i++) {
			s.append(literals[0][i]); s.append(" ");
		}
		
		s.append("\n");
		
		for (int i = 0; i < literals[0].length; i++) {
			s.append(literals[1][i]); s.append(" ");
		}
		
		
		return s.toString();
	}
	
	

}
