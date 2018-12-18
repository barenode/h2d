package h3df;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import solids.Axis;
import solids.Cube;
import solids.Point;
import solids.Solid;
import solids.Spire;
import transforms.Mat4;
import transforms.Mat4RotX;
import transforms.Mat4RotY;
import transforms.Mat4RotZ;
import transforms.Mat4Scale;
import transforms.Mat4Transl;

public class App extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private final Scene scene;
	
	public App() {
		super();	
		setTitle("H3D");
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
		setLayout(new BorderLayout());
		setSize(1200, 850);
		//main
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		add(main, BorderLayout.CENTER);
		
		scene = new Scene();
		main.add(scene, BorderLayout.CENTER);		
		prepareScene();
		
		//scene.onStateChange();
		
		addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                    	System.out.println();
                    	scene.forward(1.0);
                        break;
                    case KeyEvent.VK_DOWN:
                    	scene.backward(1d);
                        break;
                    case KeyEvent.VK_LEFT:
                    	scene.left(0.1);
                        break;
                    case KeyEvent.VK_RIGHT:
                    	scene.right(0.1);
                        break;
                    case KeyEvent.VK_R:
                    	scene.resetCamera();
                        break;
                        
                        
                    case KeyEvent.VK_A:
                    	scene.azimuth(-0.1);
                        break;
                    case KeyEvent.VK_S:
                    	scene.azimuth(0.1);
                        break;
                        
                    case KeyEvent.VK_D:
                    	scene.zenith(-0.1);
                        break;
                    case KeyEvent.VK_E:
                    	scene.zenith(0.1);
                        break;
                }
                super.keyReleased(e);
            }
        });
		
		
		addMouseListener(new MouseAdapter() {
		
			
		});
		startShow();
	}
	
	private void prepareScene() {
		Cube solid1 = new Cube(2);
		Spire solid2 = new Spire(4, 6);
		Point  solid3 = new Point();
		//cube.transform(new Mat4RotY(Math.PI / 10));		
		
		Solid solid = solid1;
		//Mat4 t = new Mat4RotX(-1 * Math.PI / 100).mul(new Mat4RotY(Math.PI / 100));
		
		Mat4 t = 
			new Mat4Transl(-solid.getCentroid().getX(), -solid.getCentroid().getY(), -solid.getCentroid().getZ()).mul(	
			new Mat4RotZ(-1 * Math.PI / 100)).mul(
			new Mat4RotZ(-1 * Math.PI / 100)).mul(
			new Mat4RotX(-1 * Math.PI / 100)).mul(
			new Mat4Transl(solid.getCentroid().getX(), solid.getCentroid().getY(), solid.getCentroid().getZ()));
			
			
		//solid.transform(t);
		
		//solid.transform(new Mat4Transl(50, -30, 0));
		scene.add(solid, t);
		
		Cube c2 = new Cube(2);
		c2.transform(new Mat4Transl(15, 0, 0));
		scene.add(c2);
		
		Cube c3 = new Cube(2);
		c3.transform(new Mat4Transl(-15, 0, 0));
		scene.add(c3);
		
		Cube c4 = new Cube(2);
		c4.transform(new Mat4Transl(-30, 0, 0));
		scene.add(c4);
		
		
		Axis axis = new Axis();
		//axis.transform(new Mat4Scale(20));
		scene.add(axis);
//		Spire spire = new Spire(15, 10);
//		scene.add(spire);
	}
	
	private void startShow() {
		//scene.actionPerformed(null);
		new Timer(1000/60, this).start();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new App().setVisible(true);
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		scene.actionPerformed(e);
	}			
}
