package hylmargx;

import java.awt.Color;

public class CircleRendererParametrical implements Renderer<Circle> {

	private final Color color;
	
	public CircleRendererParametrical(Color color) {
		super();
		this.color = color;
	}
	
	@Override
	public void render(Circle circle, Image image) {
		final float r = circle.getRadius();
		int x, y;
		for(float fi=0; fi<=Math.PI/2; fi+=.3f) {
			x = (int)Math.round(r*Math.cos(fi));
			y = (int)Math.round(r*Math.sin(fi));
			image.pixel(circle.getCentre().getX()+x, circle.getCentre().getY()+y, color);
			image.pixel(circle.getCentre().getX()-x, circle.getCentre().getY()+y, color);
			image.pixel(circle.getCentre().getX()+x, circle.getCentre().getY()-y, color);
			image.pixel(circle.getCentre().getX()-x, circle.getCentre().getY()-y, color);
		}
	}
}
