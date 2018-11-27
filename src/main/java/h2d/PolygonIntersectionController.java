package h2d;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import h2d.common.Image;
import h2d.common.Line;
import h2d.common.Point;
import h2d.common.Polygon;
import h2d.common.PolygonIntersection;
import h2d.common.PolygonRenderer;

public class PolygonIntersectionController implements H2DCanvas.EventListener {
	private static final Logger logger = Logger.getLogger(PolygonIntersectionController.class);
	
	private Polygon clippingPolygon;
	private Polygon clippedPolygon;
	private Polygon intersected;
	
	private PolygonRenderer intersectedRenderer = new PolygonRenderer(Color.MAGENTA, Color.DARK_GRAY);
	
	private H2DCanvas.EventListener state = new ClippingPygonState();
	
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
	
	private class ClippingPygonState extends PolygonController {		

		public ClippingPygonState() {
			super(Color.GRAY, null);
		}
		
		@Override
		public void onMouseReleased(MouseEvent e, Point point, H2DCanvas canvas) {
			if (SwingUtilities.isRightMouseButton(e)) {
				//check polygon
				if (getPoints().size()>1) {
					clippingPolygon = new Polygon(getPoints());
					System.out.println("clippingPolygon: " + clippingPolygon);
					state = new ClippedPygonState();
				}
			} 
			super.onMouseReleased(e, point, canvas);			
		}
	}	
	
	private class ClippedPygonState extends PolygonController {		

		public ClippedPygonState() {
			super(Color.YELLOW, null);
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
				state = new ClippingPygonState();
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

//		private void createIntersection() {
//			logger.info("==================================================");
//			logger.info("clippingPolygon: " + clippingPolygon);
//			intersected = new Polygon(clippedPolygon.getPoints());
//			List<Point> points = new ArrayList<>();
//			for (Line edge : clippingPolygon.getEdges()) {				
//				logger.info("intersected: " + intersected);
//				for (Line e : intersected.getEdges()) {
//					logger.info("Checking " + edge + " with " + e);
//					if (edge.isInside(e.getEnd())) {
//						logger.info(" End inside");
//						if (!edge.isInside(e.getOrigin())) {
//							logger.info(" Origin not inside");
//							Point intersection = edge.getIntersection(e);
//							logger.info(" Intersection " + intersection);
//							if (intersection!=null) {
//								points.add(intersection);
//							}							
//						}	
//						points.add(e.getEnd());
//					} else {
//						if (edge.isInside(e.getOrigin())) {
//							logger.info(" Origin inside");
//							points.add(e.getOrigin());
//						}						
//					}
//				}
//				logger.info(" Result: " + points);
//				if (points.size()>2) {
//					intersected = new Polygon(points);
//				}			
//				points.clear();
//			}
//		}
	}	
}
