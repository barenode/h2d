package h2d.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;


/**
 * @author hylmar
 */
public class ImageImpl implements Image {	
	
	private final int pixelSize;
	private final int width;
	private final int imageWidth;
	private final int height;
	private final int imageHeight;
	private final BufferedImage image;
	private final Dimension dimension;
	
	public ImageImpl(int pixelSize, int imageWidth, int imageHeight) {
		super();
		this.pixelSize = pixelSize;
		this.width = (imageWidth/pixelSize)+1;
		this.height = (imageHeight/pixelSize)+1;
		this.dimension = new Dimension(width, height);
		this.imageWidth = to2D(width);
		this.imageHeight = to2D(height);
		this.image = new BufferedImage(imageWidth+10, imageHeight+10, BufferedImage.TYPE_INT_ARGB);
		clear();
	}

	@Override
	public void pixel(int x, int y, Color color) {
		if (x<0 || y<0 || x>=width || y>=height) {
			return;
		}
		for (int i=to2D(x); i<to2D(x)+pixelSize;i++) {
			for (int j=to2D(y); j<to2D(y)+pixelSize;j++) {
			try {
					this.image.setRGB(i, j, color.getRGB());
				} catch (Exception e) {
					System.out.println(image.getWidth() + ":" + image.getHeight());
					System.out.println("Errosr to paint " + x + ":" + y + " - " + i + "/" + j + "[" + imageWidth + ":" + imageHeight + "]");
				}
			}
		}
	}	
	
	@Override
	public Color color(int x, int y) {
		return new Color(image.getRGB(to2D(x), to2D(y)));
	}
	
	public Dimension getDimension() {
		return dimension;
	}
	
	private int to2D(int value) {
		return value*pixelSize;
	}

	@Override
	public void draw(Graphics graphic) {
		graphic.drawImage(image, 0, 0, null);		
	}
	
	@Override
	public void clear() {
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		g2.setColor(Color.RED);
		g2.setBackground(BACKGROUND);		
		image.getGraphics().fillRect(0, 0, to2D(width), to2D(height));
	}
	
	public void save(String name) 
		throws Exception
	{
		ImageIO.write(image, "jpg", new File("./" + name + ".jpg"));
	}


}
