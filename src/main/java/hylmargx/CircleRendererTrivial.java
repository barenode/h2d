package hylmargx;

import java.awt.Color;

public class CircleRendererTrivial implements Renderer<Circle> {

	private final Color color;
	
	public CircleRendererTrivial(Color color) {
		super();
		this.color = color;
	}
	
	@Override
	public void render(Circle circle, Image image) {
		float y;
		for (float x = -circle.getRadius(); x<=circle.getRadius(); x+=.001) {
			float r = circle.getRadius();			
			y = (float)Math.sqrt((r*r)-(x*x));
			image.pixel(
				Math.round(circle.getCentre().getX() + x), 
				Math.round(circle.getCentre().getY() + y), 
				color);
			image.pixel(
				Math.round(circle.getCentre().getX() + x), 
				Math.round(circle.getCentre().getY() - y), 
				color);
		}
	}	
}
