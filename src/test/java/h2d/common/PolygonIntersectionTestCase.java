package h2d.common;

import java.awt.Color;

import h2d.Settings;
import junit.framework.TestCase;

public class PolygonIntersectionTestCase extends TestCase {

	private Settings clippingSettings;
	private Settings clippedSettings;
	private Settings resultSettings;
	
	@Override
	protected void setUp() throws Exception {
		clippingSettings = new Settings();
		clippingSettings.setBackground(null);
		clippingSettings.setColor(Color.BLACK);
		
		clippedSettings = new Settings();
		clippedSettings.setBackground(null);
		clippedSettings.setColor(Color.GRAY);
		
		resultSettings = new Settings();
		resultSettings.setBackground(null);
		resultSettings.setColor(Color.YELLOW);
		
		super.setUp();
	}

	public void test() throws Exception {
		Polygon clipping = new Polygon(new Point(40, 50), new Point(70, 50), new Point(55, 20));
		Polygon clipped = new Polygon(new Point(10, 60), new Point(60, 60), new Point(35, 10));
		Polygon result = new PolygonIntersection().apply(clipping, clipped);
		
		ImageImpl image = new ImageImpl(3, 300, 300);
		
		new PolygonRenderer(new LineRendererDDA(Color.BLACK), null).render(clipping, image);
		new PolygonRenderer(new LineRendererDDA(Color.GRAY), null).render(clipped, image);
		new PolygonRenderer(new LineRendererDDA(Color.YELLOW), null).render(result, image);
		
		image.save("p1");
	}
}
