package hylmargx;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public final class Panel extends JFrame {
	
	private static final int FPS = 1000/60;
	private static final int width = 20;
	private static final int height = 20;

	private Image img;
	private JPanel panel;
    
	private Panel() {
		super();
		this.img = new ImageImpl(8, width, height);
	}

	public static void main(String agrs[]) {
		Panel panel = new Panel();		
		panel.init(500, 500);
		panel.draw();
	}
	
	private void init(int width, int height) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(width, height);
        setTitle("Hylmar GX");
        panel = new JPanel();
        add(panel);
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                draw();
            }
        }, 100, FPS);
	}
	
	private void draw() {
        Point p = new Point(10, 10);
		new PointRenderer(Color.BLACK).render(p, img);	
		
		img.draw(panel.getGraphics());
        panel.paintComponents(getGraphics());
        

		new PointRenderer(Color.BLACK).render(p, img);		
    }
}
