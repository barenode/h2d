package h2d.common;

import java.awt.Color;
import java.awt.Graphics;

public interface Image {
	
	void pixel(int x, int y, Color color);
	
	void draw(Graphics graphic);
	
	void clear();
}
