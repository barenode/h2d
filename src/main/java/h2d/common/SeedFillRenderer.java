package h2d.common;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

public class SeedFillRenderer implements Renderer<Point> {
	private static final Logger logger = Logger.getLogger(SeedFillRenderer.class);
	
	private final Color boundaryColor;
	private final Color fillColor;	
	
	public SeedFillRenderer(Color boundaryColor, Color fillColor) {
		super();
		this.boundaryColor = boundaryColor;
		this.fillColor = fillColor;
	}
	
	@Override
	public void render(Point point, Image image) {
		Stack<Point> stack = new Stack<>();
		stack.push(point);
		while (!stack.isEmpty()) {
			Point p = stack.pop();
			int xLeft = xLeft(p.getX(), p.getY(), image);
			int xRight = xRight(p.getX(), p.getY(), image);
			fill(
				xLeft,
				xRight,
				p.getY(),
				image);
			stack.addAll(findUnfilledSegments(xLeft, xRight, p.getY()-1, image));
			stack.addAll(findUnfilledSegments(xLeft, xRight, p.getY()+1, image));
		}		
	}	
	
	private void fill(int startX, int endX, int y, Image image) {
		for (int i=startX; i<=endX; i++) {
			image.pixel(i, y, fillColor);
		}
	}
	
	private List<Point> findUnfilledSegments(int startX, int endX, int y, Image image) {
		if (y<0 || y>image.getDimension().getHeight()) {
			return Collections.emptyList();
		}
		List<Point> segments = new ArrayList<>();
		Integer unfilledX = null;
		for (int i=startX; i<=endX; i++) {
			if (fillPixel(i, y, image)) {
				if (unfilledX==null) {
					segments.add(new Point(i, y));
				}			
				unfilledX = i;
			} else {
				unfilledX = null;
			}		
		}
		return segments;		
	}
	
	private int xLeft(int x, int y, Image image) {
		if (x==0) {
			return x;
		} else if (!fillPixel(x-1, y, image)) {
			return x;
		} else {
			return xLeft(x-1, y, image);
		}
	}
	
	private int xRight(int x, int y, Image image) {
		if (x==image.getDimension().getWidth()) {
			return x;
		} else if (!fillPixel(x+1, y, image)) {
			return x;
		} else {
			return xRight(x+1, y, image);
		}
	}
	
	private boolean fillPixel(int x, int y, Image image) {		
		if (image.color(x, y).equals(Image.BACKGROUND)) {
		//if (!image.color(x, y).equals(boundaryColor) && !image.color(x, y).equals(fillColor)) {
			return true;
		} else {
			logger.debug("RGB: [" + x + ", " + y + "]: " + image.color(x, y));
			return false;
		}
	}
}
