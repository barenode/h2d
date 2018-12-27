package h3g;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.swing.JComponent;

import h2d.common.Image;
import h2d.common.ImageImpl;
import h2d.common.Line;
import h2d.common.LineRendererDDA;
import h2d.common.Point;
import h2d.common.Renderer;
import h3df.Pair;
import solids.Solid;
import transforms.Camera;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Mat4OrthoRH;
import transforms.Mat4PerspRH;
import transforms.Vec3D;

public class Scene extends JComponent implements ActionListener {
	private static final long serialVersionUID = 1L;	
	private static final Vec3D WT_1 = new Vec3D(1, -1, 1);
	private static final Vec3D WT_2 = new Vec3D(1, 1, 0);
	private static final Mat4 PROJECTION_ORTHOGONAL = new Mat4OrthoRH(20, 20, 0, 100);
	private static final Mat4 PROJECTION_PERSPECTIVE = new Mat4PerspRH(Math.PI/4, 1, 20, 100);
	
	//all blacks
	private final Renderer<Line> lineRenderer = new LineRendererDDA();
	private final List<SceneParticipant> solids = new ArrayList<>();	
	private final Image image;
	private Vec3D viewport;
	
	//mutable stuff	
	//camera
	private Camera camera;
	//transformation applied to all of the scene elements
	private Mat4 model = new Mat4Identity();	
	//projection, perspective by default
	private Mat4 projection = PROJECTION_PERSPECTIVE;		
	
	//helpers
	private int mouseX; 
	private int mouseY;
	
	public Scene() {
		this(800, 800);
	}
	
	public Scene(int width, int height) {
		super();
		setSize(width, height);
		image = new ImageImpl(1, width, height);		
		viewport = new Vec3D(
			0.5 * Double.valueOf(width - 1),
			0.5 * Double.valueOf(height - 1),
			1);
		resetCamera();
		initMotionListeners();
	}
	
	public void resetCamera() {
		camera = new Camera(new Vec3D(-20.0, 0.0, 0.0), 0.0, 0.0, 1.0, true);
	}
	
	public void perspectiveProjection() {
		projection = PROJECTION_PERSPECTIVE;		
	}
	
	public void orthogonalProjection() {
		projection = PROJECTION_ORTHOGONAL;		
	}
	
	private void initMotionListeners() {
		addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	mouseX = e.getX();
            	mouseY = e.getY();
            	requestFocus();
                super.mousePressed(e);
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                camera = camera.addAzimuth((Math.PI / 1000) * (mouseX - e.getX()));
                camera = camera.addZenith((Math.PI / 1000) * (mouseY - e.getY()));
                mouseX = e.getX();
                mouseY = e.getY();
                super.mouseDragged(e);
            }
        });		
		addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
	                case KeyEvent.VK_W:
	                	camera = camera.forward(1d);
	                    break;
	                case KeyEvent.VK_S:
	                    camera = camera.backward(1d);
	                    break;
	                case KeyEvent.VK_A:
	                    camera = camera.left(0.1);
	                    break;
	                case KeyEvent.VK_D:
	                    camera = camera.right(0.1);
	                    break;
                }
                super.keyReleased(e);
            }
        });		
	}

	public void add(Solid solid) {
		solids.add(new SceneParticipant(solid));
	}
	
	public void add(Solid solid, Mat4 transformation) {
		solids.add(new SceneParticipant(solid, transformation));
	}
	
	public void clear() {
		solids.clear();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		solids.forEach(s->s.actionPerformed(e));		
		repaint();
	}
	
	@Override
	@SuppressWarnings("all")
	public void paint(Graphics g) {
		super.paint(g);		
		image.clear();
		pipeline.get().forEach(l -> {			
			lineRenderer.render(l, image);
		});
		image.draw(g);				
	}

	/**
	 * Transformation chain.
	 *  
	 *  transforms set of {@see Solid} to stream of 2d lines.
	 */
	private Supplier<Stream<Line>> pipeline = () -> {
		final Mat4 transformation = 
			model.mul(
			camera.getViewMatrix()).mul(
			projection);	
		
		return solids.stream()
			.map(p -> p.solid())			
			.map(s -> s.getEdges())			
			.flatMap(e -> e.stream())
			.map(e -> {
				return Pair.of(
					e.first.mul(transformation),
					e.second.mul(transformation));
			})
			.map(p -> {
				return Pair.of(
					p.first.dehomog(), 
					p.second.dehomog());
			})
			.filter(p -> {
				return p.first.isPresent() && p.second.isPresent();
			})
			.map(p -> {
				return Pair.of(
					p.first.get(),
					p.second.get());
			})
			.map(p -> {
				return Pair.of(
					p.first
						.mul(WT_1)
						.add(WT_2)
						.mul(viewport),
					p.second
						.mul(WT_1)
						.add(WT_2)
						.mul(viewport));
			})
			.map(p -> {
				return new Line(
					Point.fromVec(p.first), 
					Point.fromVec(p.second));
			});
	};
	
	/**
	 * Wraps {@see Solid} together with its own transformation.
	 * Note that transformation is applied on each received event (frame).
	 */
	private static class SceneParticipant implements ActionListener {
		
		private final Solid solid;
		private final Mat4 transformation;
		
		public SceneParticipant(Solid solid) {
			this(solid, new Mat4Identity());
		}
		
		public SceneParticipant(Solid solid, Mat4 transformation) {
			super();
			this.solid = solid;
			this.transformation = transformation;
		}

		public Solid solid() {
			return solid;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			solid.transform(transformation);			
		}		
	}
}
