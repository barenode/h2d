package h2d;

import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import h2d.common.Image;
import h2d.common.Point;

public class PolygonIntersectionController implements H2DCanvas.EventListener {

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

		@Override
		public void onMouseReleased(MouseEvent e, Point point, H2DCanvas canvas) {
			if (SwingUtilities.isRightMouseButton(e)) {
				//check polygon
				
			} 
			super.onMouseReleased(e, point, canvas);			
		}
	}	
}
