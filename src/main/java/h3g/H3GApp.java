package h3g;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import h3df.Utils;
import h3g.view.Point3DListPanel;
import solids.Cube;
import solids.Curve;
import solids.Solid;
import transforms.Cubic;
import transforms.Mat4;
import transforms.Mat4RotX;
import transforms.Mat4RotZ;
import transforms.Mat4Transl;
import transforms.Point3D;
import transforms.Vec3D;

@SuppressWarnings("serial")
public class H3GApp extends JFrame implements ActionListener {

	private final Point3DListPanel points;
	private final Scene scene;
	
	public H3GApp() {
		super();
		setTitle("H3GApp");
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
		setLayout(new BorderLayout());
		setSize(1105, 805);	
		
		//points
		points = new Point3DListPanel();
		add(points, BorderLayout.WEST);		
		
		//scene
		scene = new Scene();
		add(scene, BorderLayout.CENTER);		
		
		points.shuffle();	
		prepareScene();
		startShow();
		//pack();
	}	
	
	private void prepareScene() {
		List<Point3D> ps = points.getPoints();
		Solid solid1 = solid(1, ps.get(0).ignoreW());
		scene.add(solid1, rotation(solid1));
		Solid solid2 = solid(2, ps.get(1).ignoreW());
		scene.add(solid2, rotation(solid2));
		Solid solid3 = solid(3, ps.get(2).ignoreW());
		scene.add(solid3, rotation(solid3));
		Solid solid4 = solid(4, ps.get(3).ignoreW());
		scene.add(solid4, rotation(solid4));
//		Solid solid5 = solid(5, ps.get(4).ignoreW());
//		scene.add(solid5, rotation(solid5));
//		Solid solid6 = solid(6, ps.get(5).ignoreW());
//		scene.add(solid6, rotation(solid6));
		
		scene.add(curve(Cubic.BEZIER));
//		scene.add(curve(Cubic.COONS));
//		scene.add(curve(Cubic.FERGUSON));
	}
	
	private Solid solid(int index, Vec3D initialPosition) { 
		Cube c = new Cube(1);
		c.transform(new Mat4Transl(initialPosition));
		return c;
	}
	
	private Mat4 rotation(Solid solid) {
		return 
			new Mat4Transl(-solid.getCentroid().getX(), -solid.getCentroid().getY(), -solid.getCentroid().getZ()).mul(	
			new Mat4RotZ(Utils.randomDouble(Math.PI / 100))).mul(
			new Mat4RotZ(Utils.randomDouble(Math.PI / 100))).mul(
			new Mat4RotX(Utils.randomDouble(Math.PI / 100))).mul(
			new Mat4Transl(solid.getCentroid().getX(), solid.getCentroid().getY(), solid.getCentroid().getZ()));
	}
	
	private Curve curve(Mat4 baseMat) {
		List<Point3D> ps = points.getPoints();
		Cubic cubic = new Cubic(baseMat, new Point3D[]{ps.get(0), ps.get(1), ps.get(2), ps.get(3)}, 0);
		List<Point3D> result = new ArrayList<>();
		for (double d=0.0; d<=2.0; d+=0.01) {
			result.add(cubic.compute(d));
		}
		return new Curve(result);
	}
	
	private void startShow() {
		new Timer(1000/60, this).start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		scene.actionPerformed(e);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new H3GApp().setVisible(true);
		});
	}	
}
