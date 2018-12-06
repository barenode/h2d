package h2d.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public interface Image {
	Color BACKGROUND = Color.WHITE;
	
	void pixel(int x, int y, Color color);
	
	Color color(int x, int y);
	
	Dimension getDimension();
	
	void draw(Graphics graphic);
	
	void clear();
}
