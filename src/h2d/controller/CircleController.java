package h2d.controller;

import java.awt.event.MouseEvent;

import h2d.H2DApp.Settings;
import h2d.common.Circle;
import h2d.common.CircleRendererTrivial;
import h2d.common.Image;
import h2d.common.Line;
import h2d.common.LineRendererFactory;
import h2d.common.Point;
import h2d.common.Renderer;
import h2d.view.H2DCanvas;

public class CircleController implements H2DCanvas.EventListener {

	private final Settings settings;
	
	private Renderer<Line> lineRenderer;
	private Renderer<Circle> circleRenderer;	
	private Point origin;
	private Point end;
	
	public CircleController(Settings settings) {
		super();
		this.settings = settings;
		onStateChanged();
	}
	
	@Override
	public void onMousePressed(MouseEvent e, Point point, H2DCanvas canvas) {
		origin = point;
	}

	@Override
	public void onMouseDragged(MouseEvent e, Point point, H2DCanvas canvas) {
		end = point;		
	}

	@Override
	public void onMouseReleased(MouseEvent e, Point point, H2DCanvas canvas) {
	}	

	public Point getOrigin() {
		return origin;
	}

	public void setOrigin(Point origin) {
		this.origin = origin;
	}

	public Point getEnd() {
		return end;
	}

	public void setEnd(Point end) {
		this.end = end;
	}		

	public Settings getSettings() {
		return settings;
	}

	@Override
	public void render(Image image) {
		if (origin!=null && end!=null) {
			lineRenderer.render(new Line(origin, end), image);
			circleRenderer.render(new Circle(origin, (int)Math.round(origin.distance(end))), image); 
		}
	}

	@Override
	public void onStateChanged() {
		origin = null;
		end = null;
		LineRendererFactory factory = new LineRendererFactory();
		lineRenderer = factory.create(settings.getLineAlgorithm(), settings.getColor());
		circleRenderer = new CircleRendererTrivial(settings.getColor());
	}

	@Override
	public String getHint() {
		return "Táhnutím zvolte poloměr kružnice";		
	}
}
