package h2d;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import h2d.common.Image;
import h2d.common.ImageImpl;
import h2d.common.Point;
import h2d.common.Renderer;

@SuppressWarnings("serial")
public class H2DCanvas extends JComponent {

	private final Image image;	
	private final List<ShapeHandler<?>> shapes = new ArrayList<>();
	//zoom
	private int pixelSize = 5;
	private H2DCanvas.EventListener eventListener;	
	
	public H2DCanvas() {
		super();		
		this.image = new ImageImpl(pixelSize, 200,200);
		setSize(600, 600);		
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (eventListener!=null) {
					eventListener.onMouseDragged(e, eventToPoint(e), H2DCanvas.this);
					onStateChange();
				}	          	
	        }
	    });
		addMouseListener(new MouseAdapter() {
	        public void mousePressed(MouseEvent e) {
	        	if (eventListener!=null) {
	        		eventListener.onMousePressed(e, eventToPoint(e), H2DCanvas.this);
					onStateChange();
	        	}	
	        }

	        public void mouseReleased(MouseEvent e) {
	        	if (eventListener!=null) {
	        		eventListener.onMouseReleased(e, eventToPoint(e), H2DCanvas.this);
					onStateChange();
	        	}	
	        }
		});			
		onStateChange();
	}

	@Override
	@SuppressWarnings("all")
	public void paint(Graphics g) {
		super.paint(g);
		image.clear();
		for (ShapeHandler shape : shapes) {
			shape.getRenderer().render(shape.getShape(), image);
		}
		if (eventListener!=null) {
			eventListener.render(image);
		}
		image.draw(g);
	}
	
	public <T> void add(ShapeHandler<T> shape) {
		shapes.add(shape);
		onStateChange();
	}
	
	public void onStateChange() {
		repaint();
	}

	public void setEventListener(H2DCanvas.EventListener eventListener) {
		this.eventListener = eventListener;
	}
	
	//helpers
	private Point eventToPoint(MouseEvent e) {
		return new Point(toImagePixel(e.getX()), toImagePixel(e.getY()));
	}
	
	private int toImagePixel(int value) {
		return value/pixelSize;
	}

	//inner stuff
	public static final class ShapeHandler<T> {
		
		private final T shape;
		private final Renderer<T> renderer;
			
		public ShapeHandler(
			T shape,
			Renderer<T> renderer
		) {
			super();
			this.shape = shape;
			this.renderer = renderer;
		}

		public T getShape() {
			return shape;
		}

		public Renderer<T> getRenderer() {
			return renderer;
		}		
	}
	
	
	public interface EventListener {

		void onMousePressed(MouseEvent e, Point point, H2DCanvas canvas);
		
		void onMouseDragged(MouseEvent e, Point point, H2DCanvas canvas);
		
		void onMouseReleased(MouseEvent e, Point point, H2DCanvas canvas);
		
		void render(Image image);
	}
}
