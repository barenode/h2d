package h2d;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import h2d.common.Image;
import h2d.common.LineRendererFactory;
import h2d.common.Point;
import h2d.common.Polygon;
import h2d.common.PolygonIntersection;
import h2d.common.PolygonRenderer;

public class PolygonIntersectionController implements H2DCanvas.EventListener {	
	
	private final Settings settings;
	
	private Polygon clippingPolygon;
	private Polygon clippedPolygon;
	private Polygon intersected;	
	private PolygonRenderer intersectedRenderer;
	
	private H2DCanvas.EventListener state;
	
	public PolygonIntersectionController(Settings settings) {
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
	}	
	
	@Override
	public void onStateChanged() {
		clippingPolygon = null;
		clippedPolygon = null;
		intersected = null;
		LineRendererFactory factory = new LineRendererFactory();
		intersectedRenderer = new PolygonRenderer(
			factory.create(settings.getLineAlgorithm(), settings.getColor()),
			factory.create(settings.getLineAlgorithm(), settings.getBackground()));
		clipingPolygonState();
	}
	
	@Override
	public String getHint() {
		return state.getHint();
	}
	
	private void clipingPolygonState() {
		Settings clipingPolygonSettings = (Settings)settings.clone();
		clipingPolygonSettings.setColor(Color.GRAY);
		clipingPolygonSettings.setBackground(null);
		state = new ClippingPygonState(clipingPolygonSettings);	
	}
	
	private void clippedPolygonState() {
		Settings clippedPolygonSettings = (Settings)settings.clone();
		clippedPolygonSettings.setColor(Color.MAGENTA);
		clippedPolygonSettings.setBackground(null);
		state = new ClippedPygonState(clippedPolygonSettings);
	}
	
	private class ClippingPygonState extends PolygonController {		

		public ClippingPygonState(Settings settings) {
			super(settings);
		}
		
		@Override
		public void onMouseReleased(MouseEvent e, Point point, H2DCanvas canvas) {
			if (SwingUtilities.isRightMouseButton(e)) {
				//check polygon
				if (getPoints().size()>1) {
					clippingPolygon = new Polygon(getPoints());
					clippedPolygonState();
				}
			} 
			super.onMouseReleased(e, point, canvas);			
		}
		
		@Override
		public String getHint() {
			return 
				"Stisknutím levého tlačítka myši přidáte další bod ořezávajícího n-úhelníku. Stisknutím pravého tlačítka myši dokončíte ořezávající n-úhelník." + 
				"Ořezávající n-úhelník musí být konvexní!";		
		}
	}	
	
	private class ClippedPygonState extends PolygonController {		

		public ClippedPygonState(Settings settings) {
			super(settings);
		}
		
		@Override
		public void onMouseReleased(MouseEvent e, Point point, H2DCanvas canvas) {		
			if (SwingUtilities.isLeftMouseButton(e)) {
				super.onMouseReleased(e, point, canvas);
				if (getPoints().size()>1) {
					clippedPolygon = new Polygon(getPoints());
					intersected = new PolygonIntersection().apply(clippingPolygon, clippedPolygon);
				}				
			} else if (SwingUtilities.isRightMouseButton(e)) {
				//check polygon
				if (intersected!=null) {
					canvas.add(new H2DCanvas.ShapeHandler<Polygon>(intersected, intersectedRenderer));									
				}
				clippingPolygon = null;
				clippedPolygon = null;
				intersected = null;	
				clipingPolygonState();
				super.onMouseReleased(e, point, canvas);			
			}			
		}		
		
		@Override
		public void render(Image image) {
			super.render(image);
			if (intersected!=null) {
				intersectedRenderer.render(intersected, image);
			}
		}
		
		@Override
		public String getHint() {
			return "Stisknutím levého tlačítka myši přidáte další bod ořezávaného n-úhelníku. Stisknutím pravého tlačítka myši dokončíte ořezávaný n-úhelník.";		
		}
	}
}
