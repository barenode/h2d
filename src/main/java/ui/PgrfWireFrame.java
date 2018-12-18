package ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import solids.Axis;
import solids.Cube;
import solids.Solid;
import transforms.Camera;
import transforms.Mat4;
import transforms.Mat4OrthoRH;
import transforms.Mat4PerspRH;
import transforms.Mat4RotY;
import transforms.Mat4RotZ;
import transforms.Mat4Scale;
import transforms.Mat4Transl;
import transforms.Point3D;
import transforms.Vec3D;
import utils.Transformer;

public class PgrfWireFrame extends JFrame {
	private static final Logger logger = Logger.getLogger(PgrfWireFrame.class);
	
    static int FPS = 1000 / 30;
    static int width = 800;
    static int height = 600;
    private JPanel panel;
    private Transformer transformer;
    private Camera camera;
    private List<Solid> solids;
    private BufferedImage img;

    private int beginX, beginY;
    private double moveX, moveY, moveZ;
    
    

    public static void main(String[] args) {
        PgrfWireFrame frame = new PgrfWireFrame();
        frame.init(width, height);
    }

    private void init(int width, int height) {
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        // nastavenĂ­ frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(width, height);
        setTitle("");
        panel = new JPanel();
        add(panel);
        solids = new ArrayList<>();
        transformer = new Transformer(img);
        
        Mat4OrthoRH ortho = new Mat4OrthoRH(
        		10, 
        		10, 
        		1, 
        		100);
        System.out.println(ortho);
        
        Mat4 persp = new Mat4PerspRH(Math.PI/2, 2, 10, 100);        
        System.out.println(persp); 
        
        transformer.setProjection(persp);
        //transformer.setProjection(ortho);
        
        camera = new Camera();

        //{
        //int i = 0;
        int count = 5;
        for (int i = 0; i < count; i++) {
            Cube cube = new Cube(1);
            for (int v = 0; v < cube.getVerticies().size(); v++) {
                Point3D point3D = cube.getVerticies().get(v);
                Point3D newPoint = point3D
                        .mul(new Mat4Transl(0, 2, 0))
                        .mul(new Mat4RotZ((double) i * 2d * Math.PI / (double) count))
                 ;
                cube.getVerticies().set(v, newPoint);
            }
            solids.add(cube);
        }

        Axis axis = new Axis();
        for (int i = 0; i < axis.getVerticies().size(); i++) {
            Point3D point3D = axis.getVerticies().get(i);
            axis.getVerticies().set(i, point3D.mul(new Mat4Scale(3)));
        }
        solids.add(axis);

        // listeners
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                beginX = e.getX();
                beginY = e.getY();
                super.mousePressed(e);
            }
        });
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                camera = camera.addAzimuth((Math.PI / 1000) * (beginX - e.getX()));
                camera = camera.addZenith((Math.PI / 1000) * (beginY - e.getY()));
                beginX = e.getX();
                beginY = e.getY();
                super.mouseDragged(e);
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        camera = camera.forward(1d);
                        break;
                    case KeyEvent.VK_DOWN:
                        camera = camera.backward(1d);
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
                super.keyReleased(e);
            }
        });
        // timer pro refresh draw()
        setLocationRelativeTo(null);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                draw();
            }
        }, 100, FPS);

    }

    private void resetCamera() {
        // TODO SPRĂ�VNĂ‰ HODNOTY
        moveX = 0;
        moveY = 0;
        moveZ = 0;
        camera = new Camera(new Vec3D(10, 5, 0),
                -2.5, -0.1, 1.0, true);
    }

    private void draw() {
        // clear
        img.getGraphics().fillRect(0, 0, img.getWidth(), img.getHeight());

        //transformer.setModel(); // todo Ăşloha 3
        
		logger.info("========================================");
		logger.info("Eye: " + 			camera.getEye());
		logger.info("ViewVector: " + 	camera.getViewVector());
		logger.info("UpVector: " + 	camera.getUpVector());
		
        transformer.setView(camera.getViewMatrix());

        for (Solid solid : solids) {
            transformer.drawWireFrame(solid);
        }

        panel.getGraphics().drawImage(img, 0, 0, null);
        panel.paintComponents(getGraphics());
    }
}
