package h2d.common;

import java.awt.Color;

import h2d.Settings;

public class LineRendererFactory {

	public Renderer<Line> create(Settings.LineAlogrithm lineAlgorithm, Color color) {
		switch (lineAlgorithm) {
			case Trivial :
				return new LineRendererTrivial(color);
			case DDA :
				return new LineRendererDDA(color);
			case Quadrantal :
				return new LineRendererDDAQuadrantal(color);
			case Bresenham :
				return new LineRendererBresenhamRevisited(color);
			default :
				throw new IllegalArgumentException("Unsupported line algorithm: " + lineAlgorithm);
		}
	} 
}
