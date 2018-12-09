package h2d.common;

import java.awt.Color;

public class PointRenderer implements Renderer<Point> {
	
	private final Color color;
	
	public PointRenderer(Color color) {
		super();
		this.color = color;
	}
	
	@Override
	public void render(Point shape, Image image) {
		image.pixel(shape.getX(), shape.getY(), color);		
	}	
}
