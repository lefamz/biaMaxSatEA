package utils;
import java.util.Random;

/**
File: Constants.java
@author Michal Zajacik
Company: Agent Technology Center CVUT
Version: May 7, 2013
 */

public abstract class MyConstants {
	/** Limit of epochs */
	public static final int epochCap = 1500;
	
	/** population size limit */
	public static final int popCap = 150;
	
	/** debug mode on-off */
	public static final boolean debug = false;
	
	/** Random number generator*/
	public static final Random random = new Random(135681);
	
	/** probability of mutation of individual */
	public static final double prMut = 0.15;
	
	/** probability of crossover of two individuals */
	public static final double prCross = 0.60;
	
	/** generational replacement strategy */
	public static final byte generational = 1;
	
	/** steady-state replacement strategy */
	public static final byte steadyState = 2;
	
	/** how many individuals are preserved (untouched) in the next generation */
	public static final int preserve = 2;
	
	/** how many individuals are replaced in steady-state replacement strategy */
	public static final int replaceSteady = 10;
	
	/** 1-point crossover strategy */
	public static final byte onePointXover = 0;
	
	/** 2-point crossover strategy */
	public static final byte twoPointXover = 1;
	
	/** uniform crossover strategy */
	public static final byte uniformXover = 2;
	
	/** number of generated and tested problems */
	public static final int nProblem = 20;
	
}
