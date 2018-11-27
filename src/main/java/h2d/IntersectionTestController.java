package h2d;

import java.awt.Color;
import java.awt.event.MouseEvent;

import h2d.common.Image;
import h2d.common.Point;
import h2d.common.Polygon;
import h2d.common.PolygonIntersection;
import h2d.common.PolygonRenderer;

public class IntersectionTestController implements H2DCanvas.EventListener {
	
	private PolygonRenderer intersectedRenderer = new PolygonRenderer(Color.MAGENTA, null);
	private PolygonRenderer clippingRenderer = new PolygonRenderer(Color.BLACK, null);
	private PolygonRenderer clippedRenderer = new PolygonRenderer(Color.GRAY, null);
	
	@Override
	public void onMousePressed(MouseEvent e, Point point, H2DCanvas canvas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseDragged(MouseEvent e, Point point, H2DCanvas canvas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseReleased(MouseEvent e, Point point, H2DCanvas canvas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Image image) {
		Polygon clipping = new Polygon(new Point(40, 20), new Point(10, 10), new Point(30, 50));
		clippingRenderer.render(clipping, image);
		Polygon clipped = new Polygon(new Point(20, 40), new Point(50, 50), new Point(30, 10));
		clippedRenderer.render(clipped, image);
		intersectedRenderer.render(new PolygonIntersection().apply(clipping, clipped), image);
		
	}
}
