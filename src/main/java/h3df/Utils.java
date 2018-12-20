package h3df;

import java.util.Locale;
import java.util.Random;

public class Utils {
	
	private static final Random random = new Random();	
	
	/**
	 * Returns random double within interval <-limit, limit>
	 */
	public static double randomDouble(double limit) {
		return Math.abs(2.0*limit) * random.nextDouble() - limit;
	}
	
	public static double parseDouble(String val) {
		return Double.parseDouble(val);
	}
	
	public static String formatDouble(double val) {
		return String.format(Locale.US, "%4.3f", val);
	}
}
