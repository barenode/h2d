package h3df;

import junit.framework.TestCase;
import solids.Cube;

public class PipelineTestCase extends TestCase {

	public void test() throws Exception {
		Scene scene = new Scene();
		scene.add(new Cube(10d));
		
		new Pipeline().accept(scene);
	}
}
