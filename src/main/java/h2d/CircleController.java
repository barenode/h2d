package h2d;

import java.awt.Color;
import java.awt.event.MouseEvent;

import h2d.common.Circle;
import h2d.common.CircleRendererTrivial;
import h2d.common.Image;
import h2d.common.Line;
import h2d.common.LineRendererDDAQuadrantal;
import h2d.common.Point;
import h2d.common.Renderer;

public class CircleController implements H2DCanvas.EventListener {

	private Renderer<Line> lineRenderer = new LineRendererDDAQuadrantal(Color.CYAN);
	private Renderer<Circle> circleRenderer = new CircleRendererTrivial(Color.RED);
	
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
	}

	@Override
	public void render(Image image) {
		if (origin!=null && end!=null) {
			lineRenderer.render(new Line(origin, end), image);
			circleRenderer.render(new Circle(origin, (int)Math.round(origin.distance(end))), image); 
		}
	}
}
