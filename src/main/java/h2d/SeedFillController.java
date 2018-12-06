package h2d;

import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import h2d.common.Point;
import h2d.common.SeedFillRenderer;

public class SeedFillController extends LineController {	
	
	public SeedFillController(Settings settings) {
		super(settings);
	}		

	@Override
	public void onMouseReleased(MouseEvent e, Point point, H2DCanvas canvas) {
		super.onMouseReleased(e, point, canvas);
		if (SwingUtilities.isRightMouseButton(e)) {
			canvas.add(
				new H2DCanvas.ShapeHandler<Point>(
					point,
					new SeedFillRenderer(getSettings().getColor(), getSettings().getBackground())));
		}
		
	}				

	@Override
	public String getHint() {
		return "";
	}		
}
