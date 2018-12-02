package h2d;

import java.awt.Color;
import java.awt.event.MouseEvent;

import h2d.common.Image;
import h2d.common.NAngle;
import h2d.common.Point;
import h2d.common.Polygon;
import h2d.common.PolygonRenderer;
import h2d.common.Renderer;

public class NAngleController extends CircleController {

	private Polygon nagle;
	private Renderer<Polygon> polygonRenderer = new PolygonRenderer();
	
	public NAngleController() {
		this(Color.RED, Color.GREEN);
	}
	
	public NAngleController(Color color, Color background) {
		super();
		this.polygonRenderer = new PolygonRenderer(color, background);
	}
	
	@Override
	public void onMouseDragged(MouseEvent e, Point point, H2DCanvas canvas) {
		super.onMouseDragged(e, point, canvas);
		nagle = new NAngle(getOrigin(), getEnd(), 5).asPolygon();
	}

	@Override
	public void render(Image image) {		
		super.render(image);
		if (nagle!=null) {
			polygonRenderer.render(nagle, image);
		}
	}	
}
