package h2d;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import h2d.common.Image;
import h2d.common.Point;
import h2d.common.PointRenderer;
import h2d.common.Polygon;
import h2d.common.PolygonRenderer;
import h2d.common.Renderer;

public class PolygonController implements H2DCanvas.EventListener {
	
	private Renderer<Point> pointRenderer = new PointRenderer(Color.RED);
	private Renderer<Polygon> polygonRenderer = new PolygonRenderer();
	
	private List<Point> points = new ArrayList<>();
	
	public PolygonController() {
		this(Color.RED, Color.GREEN);
	}
	
	public PolygonController(Color color, Color background) {
		super();
		this.pointRenderer = new PointRenderer(color);
		this.polygonRenderer = new PolygonRenderer(color, background);
	}
	
	@Override
	public void onMousePressed(MouseEvent e, Point point, H2DCanvas canvas) {}

	@Override
	public void onMouseDragged(MouseEvent e, Point point, H2DCanvas canvas) {}			

	@Override
	public void onMouseReleased(MouseEvent e, Point point, H2DCanvas canvas) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			points.add(point);
		} else if (SwingUtilities.isRightMouseButton(e)) {
			canvas.add(
				new H2DCanvas.ShapeHandler<Polygon>(
					new Polygon(new ArrayList<>(points)),
					polygonRenderer));
			clear();
		}
	}

	@Override
	public void render(Image image) {
		if (points.size()==1) {
			pointRenderer.render(points.get(0), image);
		} else if (points.size()>1) {
			polygonRenderer.render(new Polygon(points), image);
		}
	}
	
	protected List<Point> getPoints() {
		return points;
	}
	
	protected void clear() {
		points.clear();
	}
}
