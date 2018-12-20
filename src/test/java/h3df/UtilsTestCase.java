package h3df;

import junit.framework.TestCase;

public class UtilsTestCase extends TestCase {

	public void test() throws Exception {
		for (int i=0; i<1000; i++) {
			System.out.println(Utils.randomDouble(3.0));
		}
	}
}
