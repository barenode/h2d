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
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.swing.JComponent;

import org.apache.log4j.Logger;

import h2d.common.Image;
import h2d.common.ImageImpl;
import h2d.common.Line;
import h2d.common.LineRendererDDA;
import h2d.common.Point;
import h2d.common.Renderer;
import h3df.Pair;
import h3df.Utils;
import solids.Solid;
import transforms.Camera;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Mat4OrthoRH;
import transforms.Mat4PerspRH;
import transforms.Mat4RotX;
import transforms.Mat4RotY;
import transforms.Mat4RotZ;
import transforms.Mat4ViewRH;
import transforms.Point3D;
import transforms.Vec3D;

public class Scene extends JComponent implements ActionListener {
	private static final Logger logger = Logger.getLogger(Scene.class);
	
	private static final Vec3D WT_1 = new Vec3D(1, -1, 1);
	private static final Vec3D WT_2 = new Vec3D(1, 1, 0);

	private Image image = new ImageImpl(1, 800, 800);
	
	private List<SceneParticipant> solids = new ArrayList<>();	
	
//	private Camera camera = new Camera(
//		new Vec3D(40, 0, 0), 
//		-Math.PI/2, 
//		0.0, 
//		0.0, 
//		true);
	
	private Camera camera = new Camera(new Vec3D(-20.0, 0.0, 0.0), 0.0, 0.0, 1.0, true);
	
	private Vec3D viewport = 
//			new Vec3D(1, -1, 1).add(
//			new Vec3D(1, 1, 0)).mul(
			new Vec3D(
				0.5 * Double.valueOf(image.getDimension().getWidth() - 1),
				0.5 * Double.valueOf(image.getDimension().getHeight() - 1),
				1)
//			)
			;

	Mat4OrthoRH ortho = new Mat4OrthoRH(
			20, 
			20, 
			0, 
			100);
	
	private Mat4 persp = new Mat4PerspRH(
		Math.PI/4, 
		1, 
		20, 
		100);
	
	private Mat4 view = new Mat4ViewRH(
		new Vec3D(1, 1, 1), 
		new Vec3D(0, 0, 0), 
		new Vec3D(0, -1, -1));
	
	private Mat4 view2 = new Mat4ViewRH(
			new Vec3D(30, 0, 0), 
			new Vec3D(-1, 0, 0), 
			new Vec3D(0, 1, -0));
	
	private Renderer<Line> lineRenderer = new LineRendererDDA();
	
	private Mat4 model = new Mat4Identity();//(Math.PI / 10);
	
	
	private int initialX;
	private int initialY;
	
	public Scene() {
		super();
		setSize(800, 800);
		
//		camera = camera.withPosition(new Vec3D(0, 20, 5));
//		camera = camera.withAzimuth(-3);
//		camera = camera.withZenith(-0.5);
		
		// listeners
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	initialX = e.getX();
            	initialY = e.getY();
                super.mousePressed(e);
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                camera = camera.addAzimuth((Math.PI / 1000) * (initialX - e.getX()));
                camera = camera.addZenith((Math.PI / 1000) * (initialY - e.getY()));
                initialX = e.getX();
                initialY = e.getY();
                System.out.println(camera.getViewVector());
                System.out.println(camera.getUpVector());
                super.mouseDragged(e);
            }
        });
		
		addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
	                case KeyEvent.VK_UP:
	                    camera = camera.forward(1d);
	                    System.out.println("forward");
	                    break;
	                case KeyEvent.VK_DOWN:
	                    camera = camera.backward(1d);
	                    System.out.println("backward");
	                    break;
	                case KeyEvent.VK_LEFT:
	                    camera = camera.left(0.1);
	                    break;
	                case KeyEvent.VK_RIGHT:
	                    camera = camera.right(0.1);
	                    break;
                        
                        
//                    case KeyEvent.VK_A:
//                    	scene.azimuth(-0.1);
//                        break;
//                    case KeyEvent.VK_S:
//                    	scene.azimuth(0.1);
//                        break;
//                        
//                    case KeyEvent.VK_D:
//                    	scene.zenith(-0.1);
//                        break;
//                    case KeyEvent.VK_E:
//                    	scene.zenith(0.1);
//                        break;
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
		pipeline.get().stream().forEach(l -> {			
			lineRenderer.render(l, image);
		});
		image.draw(g);				
	}	
	
//	private Function<Point3D, Point3D> transform = (p) -> {
//		return p
//			.mul(camera.getViewMatrix())
//			//.mul(view2)
//			.mul(ortho)			
//			;			
//	};	
	
	private Function<Point3D, Point3D> transform = (p) -> {
		return p.mul(
			model.mul(
			camera.getViewMatrix()).mul(
			ortho));							
	};

	private Supplier<List<Line>> pipeline = () -> {
		return solids.stream()
			.map(p -> p.solid())			
			.map(s -> s.getEdges())			
			.flatMap(e -> e.stream())
//			.peek(e -> {
//				logger.info("-------------------------------------");
//				logger.info("edge: " + e);
//			})
			.map(e -> {
				return Pair.of(
					transform.apply(e.first),
					transform.apply(e.second));
			})
//			.filter(p->{
//				return p.first.getW()>0 && p.second.getW()>0;
//			})
//			.peek(e -> {
//				logger.info("transformed: " + e);
//			})
			.map(p -> {
				return Pair.of(
					p.first.dehomog(), 
					p.second.dehomog());
			})
//			.peek(e -> {
//				logger.info("dehomog: " + e);
//			})
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
//			.peek(e -> {
//				logger.info("viewport: " + e);
//			})
			.map(p -> {
				return new Line(
					Point.fromVec(p.first), 
					Point.fromVec(p.second));
			}).collect(Collectors.toList());
	};
	
	
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

		public Mat4 transformation() {
			return transformation;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			solid.transform(transformation);			
		}		
	}


	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}	
}
