package h3df;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.swing.JComponent;

import org.apache.log4j.Logger;

import h2d.H2DCanvas.ShapeHandler;
import h2d.common.Image;
import h2d.common.ImageImpl;
import h2d.common.Line;
import h2d.common.LineRendererDDA;
import h2d.common.Point;
import h2d.common.Renderer;
import solids.Solid;
import transforms.Camera;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Mat4OrthoRH;
import transforms.Mat4PerspRH;
import transforms.Mat4ViewRH;
import transforms.Point3D;
import transforms.Vec3D;

public class Scene extends JComponent implements ActionListener {
	private static final Logger logger = Logger.getLogger(Scene.class);
	
	private static final Vec3D WT_1 = new Vec3D(1, 1, 1);
	private static final Vec3D WT_2 = new Vec3D(1, 1, 0);
	
	private Camera camera = new Camera();
	private Image image = new ImageImpl(5, 500, 500);
	private List<SceneParticipant> solids = new ArrayList<>();	
	private Vec3D viewport = 
//			new Vec3D(1, -1, 1).add(
//			new Vec3D(1, 1, 0)).mul(
			new Vec3D(
				0.5 * Double.valueOf(image.getDimension().getWidth() - 1),
				0.5 * Double.valueOf(image.getDimension().getHeight() - 1),
				1)
//			)
			;
	private Mat4OrthoRH ortho = new Mat4OrthoRH(
		image.getDimension().getWidth(), 
		image.getDimension().getHeight(), 
		0, 
		100);
	
	private Mat4 persp = new Mat4PerspRH(Math.PI/4, 1, 20, 100);
	
	private Mat4 view = new Mat4ViewRH(
		new Vec3D(0, 0, 0), 
		new Vec3D(1, 0, 0), 
		new Vec3D(0, 0, 1));
	
	private Renderer<Line> lineRenderer = new LineRendererDDA();
	
	public Scene() {
		super();
	}
	
	public void add(Solid solid) {
		solids.add(new SceneParticipant(solid));
	}
	
	public void add(Solid solid, Mat4 transformation) {
		solids.add(new SceneParticipant(solid, transformation));
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
			logger.info("paint: " + l);
			lineRenderer.render(l, image);
		});
		logger.info("pipeline done");
		image.draw(g);				
	}	
	
	private Function<Point3D, Point3D> transform = (p) -> {
		return p
			//.mul(camera.getViewMatrix())
			.mul(view)
			.mul(persp)			
			;			
	};	

	private Supplier<List<Line>> pipeline = () -> {
		logger.info("========================================");
		logger.info("Eye: " + 			camera.getEye());
		logger.info("ViewVector: " + 	camera.getViewVector());
		logger.info("UpVector: " + 		camera.getUpVector());
		logger.info("-------------------------------------");
		logger.info("ViewMat:\n " + 		camera.getViewMatrix());
		logger.info("-------------------------------------");
		
		return solids.stream()
			.map(p -> p.solid())			
			.map(s -> s.getEdges())			
			.flatMap(e -> e.stream())
			.peek(e -> {
				logger.info("-------------------------------------");
				logger.info("edge: " + e);
			})
			.map(e -> {
				return Pair.of(
					transform.apply(e.first),
					transform.apply(e.second));
			})
			.peek(e -> {
				logger.info("transformed: " + e);
			})
			.map(p -> {
				return Pair.of(
					p.first.dehomog(), 
					p.second.dehomog());
			})
			.peek(e -> {
				logger.info("dehomog: " + e);
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
			.peek(e -> {
				logger.info("viewport: " + e);
			})
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
}
