package h3df;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import solids.Cube;
import solids.Spire;

public class App extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private final Scene scene;
	
	public App() {
		super();	
		this.scene = new Scene();
		prepareScene();
		startShow();
	}
	
	private void prepareScene() {
		Cube cube = new Cube(20);
		scene.add(cube);
		
		Spire spire = new Spire(15, 10);
		scene.add(spire);
	}
	
	private void startShow() {
		new Timer(1000/60, this).start();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new App().setVisible(true);
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e);
	}			
}
