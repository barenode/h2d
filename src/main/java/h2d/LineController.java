package h2d;

import java.awt.Color;
import java.awt.event.MouseEvent;

import h2d.common.Image;
import h2d.common.Line;
import h2d.common.LineRendererDDAQuadrantal;
import h2d.common.Point;
import h2d.common.Renderer;

public class LineController implements H2DCanvas.EventListener {

	private Renderer<Line> renderer = new LineRendererDDAQuadrantal(Color.CYAN);
	
	private Point origin;
	private Point end;
	
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
		end = point;
		if (!end.equals(origin)) {
			canvas.add(new H2DCanvas.ShapeHandler<Line>(new Line(origin, end), renderer));
		}
		origin = null;
		end = null;
	}

	@Override
	public void render(Image image) {
		if (origin!=null && end!=null) {
			renderer.render(new Line(origin, end), image);
		}	
	}
}
