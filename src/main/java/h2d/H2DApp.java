package h2d;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class H2DApp extends JFrame {

	public H2DApp() {
		super();
		setTitle("H2D");
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
		setLayout(new BorderLayout());
		setSize(600, 600);
		H2DCanvas canvas = new H2DCanvas();
		add(canvas, BorderLayout.CENTER);
		canvas.setEventListener(new PolygonController());
		//pack();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new H2DApp().setVisible(true);
		});
	}
}
