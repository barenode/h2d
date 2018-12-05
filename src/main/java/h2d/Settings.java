package h2d;

import java.awt.Color;

public class Settings implements Cloneable {

	private int pixelSize = 4;
	private Color color = Color.RED;
	private Color background = Color.GREEN;
	private LineAlogrithm lineAlgorithm = LineAlogrithm.DDA;
	
	public Settings() {
		super();
	}	
	
	public int getPixelSize() {
		return pixelSize;
	}

	public void setPixelSize(int pixelSize) {
		this.pixelSize = pixelSize;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public LineAlogrithm getLineAlgorithm() {
		return lineAlgorithm;
	}

	public void setLineAlgorithm(LineAlogrithm lineAlgorithm) {
		this.lineAlgorithm = lineAlgorithm;
	}

	public static enum LineAlogrithm {		
		Trivial,
		DDA,
		Quadrantal,
		Bresenham
	}

	@Override
	protected Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new IllegalStateException(e);
		}
	}	
}
