package utils;
import java.util.Random;

/**
File: Constants.java
@author Michal Zajacik
Company: Agent Technology Center CVUT
Version: May 7, 2013
 */

public abstract class MyConstants {
	/** population size limit */
	public static final int popCap = 100;
	/** debug mode on-off */
	public static final boolean debug = true;
	
	/** Random number generator*/
	public static final Random random = new Random(42);
}
