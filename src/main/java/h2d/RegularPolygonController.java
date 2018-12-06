package h2d;

import java.awt.Color;
import java.awt.event.MouseEvent;

import h2d.common.Image;
import h2d.common.LineRendererFactory;
import h2d.common.Point;
import h2d.common.Polygon;
import h2d.common.PolygonRenderer;
import h2d.common.RegularPolygon;
import h2d.common.Renderer;

public class RegularPolygonController implements H2DCanvas.EventListener {	
	
	private final Settings settings;
	
	private Renderer<Polygon> polygonRenderer;
	private Point origin;
	private Point end;
	private int angleCount;
	private Polygon regularPolygon;	
	
	private H2DCanvas.EventListener state;
	
	public RegularPolygonController(Settings settings) {
		super();		
		this.settings = settings;
		onStateChanged();
	}
	
	@Override
	public void onMousePressed(MouseEvent e, Point point, H2DCanvas canvas) {
		state.onMousePressed(e, point, canvas);		
	}

	@Override
	public void onMouseDragged(MouseEvent e, Point point, H2DCanvas canvas) {
		state.onMouseDragged(e, point, canvas);	
	}

	@Override
	public void onMouseReleased(MouseEvent e, Point point, H2DCanvas canvas) {
		state.onMouseReleased(e, point, canvas);		
	}

	@Override
	public void render(Image image) {	
		state.render(image);
		if (regularPolygon!=null) {
			polygonRenderer.render(regularPolygon, image);
		}
	}	
	
	@Override
	public void onStateChanged() {
		origin = null;
		end = null;
		angleCount = 3;
		regularPolygon = null;
		LineRendererFactory factory = new LineRendererFactory();
		polygonRenderer = new PolygonRenderer(
			factory.create(settings.getLineAlgorithm(), settings.getColor()),
			factory.create(settings.getLineAlgorithm(), settings.getBackground()));
		polygonSizeState();
	}
	
	@Override
	public String getHint() {
		return state.getHint();
	}
	
	private void onPolygonChanged() {
		regularPolygon = new RegularPolygon(origin, end, angleCount).asPolygon();
	}
	
	private void polygonSizeState() {
		Settings polygonSizeSettings = (Settings)settings.clone();
		polygonSizeSettings.setColor(Color.GRAY);
		polygonSizeSettings.setBackground(null);
		state = new PygonSizeState(polygonSizeSettings);	
	}
	
	private void angleCountState() {
		state = new AngleCountState();
	}
	
	private class PygonSizeState extends CircleController {

		public PygonSizeState(Settings settings) {
			super(settings);
		}		
		
		@Override
		public void onMouseDragged(MouseEvent e, Point point, H2DCanvas canvas) {
			super.onMouseDragged(e, point, canvas);
			RegularPolygonController.this.origin = getOrigin();
			RegularPolygonController.this.end = getEnd();
			onPolygonChanged();
		}
		
		@Override
		public void onMouseReleased(MouseEvent e, Point point, H2DCanvas canvas) {
			angleCountState();
		}
		
		@Override
		public String getHint() {
			return "Táhnutím zvolte velikost pravidelného n-uhelníku";		
		}
	}	
	
	class AngleCountState implements H2DCanvas.EventListener {
		
		private Point origin;
		private Point end;		
		
		public AngleCountState() {
			super();
		}
		
		@Override
		public void onMousePressed(MouseEvent e, Point point, H2DCanvas canvas) {
			origin = point;
		}

		@Override
		public void onMouseDragged(MouseEvent e, Point point, H2DCanvas canvas) {
			end = point;
			double distance = origin.distance(end);
			RegularPolygonController.this.angleCount = 3 + (int)(distance/5);
			onPolygonChanged();			
		}

		@Override
		public void onMouseReleased(MouseEvent e, Point point, H2DCanvas canvas) {
			canvas.add(
				new H2DCanvas.ShapeHandler<Polygon>(
					regularPolygon,
					polygonRenderer));
			RegularPolygonController.this.onStateChanged();
		}

		@Override
		public void render(Image image) {}		
		
		@Override
		public void onStateChanged() {}
		
		
		@Override
		public String getHint() {
			return "Táhnutím zvolte počet stran pravidelného n-uhelníku";		
		}
	}
}
