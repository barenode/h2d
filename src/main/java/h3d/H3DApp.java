package h3d;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import solids.Axis;
import solids.Cube;

@SuppressWarnings("serial")
public class H3DApp extends JFrame {

	private final H3DCanvas canvas;
	
	public H3DApp() {
		super();
		setTitle("H3D");
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
		setLayout(new BorderLayout());
		setSize(1200, 850);
		
		//main
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		add(main, BorderLayout.CENTER);
		
		//canvas
		canvas = new H3DCanvas();
		main.add(canvas, BorderLayout.CENTER);

		canvas.add(new Axis(3));
		canvas.add(new Cube());
		
		addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
            	canvas.onKey(e.getKeyCode());
            	super.keyReleased(e);
            }
		});
		//pack();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new H3DApp().setVisible(true);
		});
	}
}
