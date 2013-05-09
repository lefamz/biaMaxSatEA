/**
File: MyReader.java
@author Michal Zajacik
Company: Agent Technology Center CVUT
Version: May 7, 2013
*/

package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import ea.Clause;

public class MyReader {
	
		public int nCl, nLit;
		
		public ArrayList<Clause> formula = new ArrayList<Clause>();
		
		private String fileName;
		private boolean debug = true;
		
		
		/**
		 * @param fileName
		 */
		public MyReader(String fileName, boolean debug) {
			this.fileName = fileName;
			this.debug = debug;
		}
		
		public void read(){
			BufferedReader buff = null;
			
			try{
				File file = new File(fileName);
				FileReader fr = new FileReader(file);
				buff = new BufferedReader(fr);
				
				Pattern patt = java.util.regex.Pattern.compile(" ");
				String[] hlp = patt.split(buff.readLine());
				String[] hlp2;
				
				nLit=Integer.parseInt(hlp[0]);
				nCl =Integer.parseInt(hlp[1]);
				
				if(debug)System.out.println("Number of Literals: " + nLit + " Number of clauses: " + nCl);
				
				for (int i = 0; i < nCl; i++) {
					hlp  = patt.split(buff.readLine());
					hlp2 = patt.split(buff.readLine());
					
					int len = hlp.length;
					
					if(MyConstants.debug) System.out.println("Clause " + i +" len: " + len);
					Clause cl = new Clause(len);
					
					for (int j = 0; j < len; j++) {
						cl.literals[0][j]=Integer.parseInt(hlp[j]);
						cl.literals[1][j]=Integer.parseInt(hlp2[j]);
					}
					
					formula.add(cl);
					
					if(debug) System.out.println(cl);
				}
				
				
				if(buff!=null) buff.close();
				
			} catch (FileNotFoundException e) {
				System.err.println("Error accesing file: " + e.getMessage());
				e.printStackTrace();
			} catch (IOException e){
				System.err.println("Error reading file: " + e.getMessage());
				e.printStackTrace();
			}
				
		}

}

