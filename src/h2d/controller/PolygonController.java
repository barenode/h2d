package h2d.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import h2d.H2DApp.Settings;
import h2d.common.Image;
import h2d.common.Line;
import h2d.common.LineRendererFactory;
import h2d.common.Point;
import h2d.common.PointRenderer;
import h2d.common.Polygon;
import h2d.common.PolygonRenderer;
import h2d.common.Renderer;
import h2d.view.H2DCanvas;

public class PolygonController implements H2DCanvas.EventListener {
	
	private final Settings settings;
	private final List<Point> points = new ArrayList<>();	
	
	private Renderer<Point> pointRenderer = new PointRenderer(Color.RED);
	private Renderer<Polygon> polygonRenderer;	
	
	public PolygonController(Settings settings) {
		super();
		this.settings = settings;
		onStateChanged();
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
	
	@Override
	public void onStateChanged() {
		clear();
		LineRendererFactory factory = new LineRendererFactory();
		Renderer<Line> lineRenderer = factory.create(settings.getLineAlgorithm(), settings.getColor());
		Renderer<Line> backgroundRenderer = null;
		if (settings.getBackground()!=null) {
			backgroundRenderer = factory.create(settings.getLineAlgorithm(), settings.getBackground());
		}
		polygonRenderer = new PolygonRenderer(
			lineRenderer,
			backgroundRenderer);
	}
	
	@Override
	public String getHint() {
		return "Stisknutím levého tlačítka myši přidáte další bod n-úhelníku. Stisknutím pravého tlačítka myši dokončíte n-úhelník.";		
	}
}
