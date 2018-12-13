package h3d;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

import h2d.common.Image;
import h2d.common.ImageImpl;
import solids.Solid;
import transforms.Camera;
import transforms.Mat4PerspRH;
import transforms.Mat4RotY;
import transforms.Mat4RotZ;
import transforms.Vec3D;

@SuppressWarnings("serial")
public class H3DCanvas extends JComponent {

	private Image image;	
	private Transformer transformer;
	private int pixelSize = 1;
	
	private Camera camera;
	private java.util.List<Solid> solids = new java.util.ArrayList<>();
	
	private int beginX, beginY;
	private double moveX, moveY, moveZ;
	
	public H3DCanvas() {
		super();		
		this.image = new ImageImpl(pixelSize, 1000, 800);
		this.transformer = new Transformer(this.image);
		this.transformer.setProjection(new Mat4PerspRH(1, 1, 1, 100));
		this.transformer.setModel(new Mat4RotY(Math.PI/4).mul(new Mat4RotZ(Math.PI/4)));
		resetCamera();
		
		// listeners
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                beginX = e.getX();
                beginY = e.getY();
                super.mousePressed(e);
                onStateChange();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                camera = camera.addAzimuth((Math.PI / 1000) * (beginX - e.getX()));
                camera = camera.addZenith((Math.PI / 1000) * (beginY - e.getY()));
                beginX = e.getX();
                beginY = e.getY();
                super.mouseDragged(e);
                onStateChange();
            }
        });        
		onStateChange();
	}
	
	public void onKey(int keyEvent) {
		switch (keyEvent) {
	        case KeyEvent.VK_UP:
	            camera = camera.forward(0.1);
	            break;
	        case KeyEvent.VK_DOWN:
	            camera = camera.backward(0.1);
	            break;
	        case KeyEvent.VK_LEFT:
	            camera = camera.left(0.1);
	            break;
	        case KeyEvent.VK_RIGHT:
	            camera = camera.right(0.1);
	            break;
	        // TODO - camera.up(), camera.down()
	
	        case KeyEvent.VK_A:
	            moveX -= 0.1;
	            break;
	        case KeyEvent.VK_W:
	            moveZ += 0.1;
	            break;
	        case KeyEvent.VK_S:
	            moveZ -= 0.1;
	            break;
	        case KeyEvent.VK_D:
	            moveX += 0.1;
	            break;
	        // TODO - moveY, rotateX, rotateY, scaleX, scaleY...
	
	        case KeyEvent.VK_R:
	            resetCamera();
	            break;	
	    }
		onStateChange();
	}
	
	private void resetCamera() {
        moveX = 0;
        moveY = 0;
        moveZ = 0;
        camera = new Camera(new Vec3D(10, 5, 0), -2.5, -0.1, 1.0, true);                
    }
	
	@Override
	@SuppressWarnings("all")
	public void paint(Graphics g) {
		super.paint(g);
		image.clear();	
		this.transformer.setView(camera.getViewMatrix());  
		for (Solid solid : solids) {
			transformer.drawWireFrame(solid);
		}
		image.draw(g);		
	}
	
	public void onStateChange() {		
		repaint();
	}
	
	public void add(Solid solid) {
		solids.add(solid);
		onStateChange();
	}
}
