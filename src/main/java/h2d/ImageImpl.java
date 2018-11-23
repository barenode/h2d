package h2d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

/**
 * @author hylmar
 */
public class ImageImpl implements Image {
	private static final Logger log = Logger.getLogger("Image");
	
	private final int pixelSize;
	private final int width;
	private final int height;
	private final BufferedImage image;
	
	public ImageImpl(int pixelSize, int width, int height) {
		super();
		this.pixelSize = pixelSize;
		this.width = width;
		this.height = height;	
		this.image = new BufferedImage(to2D(width), to2D(height), BufferedImage.TYPE_INT_ARGB);
		clear();
	}

	@Override
	public void pixel(int x, int y, Color color) {
//		if (log.isDebugEnabled()) {
//			log.debug("pixel [" + x + ", " + y + "]");
//		}
		for (int i=to2D(x); i<to2D(x)+pixelSize;i++) {
			for (int j=to2D(y); j<to2D(y)+pixelSize;j++) {
				this.image.setRGB(i, j, color.getRGB());
			}
		}
	}	
	
	private int to2D(int value) {
		return value*pixelSize;
	}

	@Override
	public void draw(Graphics graphic) {
		graphic.drawImage(image, 0, 0, null);		
	}
	
	private void clear() {
		this.image.getGraphics().setColor(Color.RED);
		this.image.getGraphics().fillRect(0, 0, to2D(width), to2D(height));
	}
	
	public void save(String name) 
		throws Exception
	{
		ImageIO.write(image, "jpg", new File("./" + name + ".jpg"));
	}
}
