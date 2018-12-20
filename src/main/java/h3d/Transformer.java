package h3d;

import java.awt.Color;
import java.util.Optional;

import h2d.common.Image;
import solids.Axis;
import solids.Solid;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;
import transforms.Vec3D;

public class Transformer {

    private Image image;

    private Mat4 model;
    private Mat4 view;
    private Mat4 projection;

    public Transformer(Image image) {
        this.image = image;
        this.model = new Mat4Identity();
        this.view = new Mat4Identity();
        this.projection = new Mat4Identity();
    }

    public void drawWireFrame(Solid solid) {
        // vysledna matice zobrazeni
        Mat4 matFinal;
        if (solid instanceof Axis){
            // Axis nenásobíme MODELEM
            matFinal = view.mul(projection);
        } else {
            matFinal = model.mul(view).mul(projection);
        }

        // první index: 1. bod, druhý index: druhý bod úse�?ky
        for (int i = 0; i < solid.getIndicies().size(); i += 2) {
            Point3D p1 = solid.getVerticies().get(solid.getIndicies().get(i));
            Point3D p2 = solid.getVerticies().get(solid.getIndicies().get(i + 1));
            transformEdge(matFinal, p1, p2, Color.BLACK);//solid.getColorByEdge(i / 2));
        }
    }

    private void transformEdge(Mat4 mat, Point3D p1, Point3D p2, Color color) {
        // 1.) vynásobit body maticí
        p1 = p1.mul(mat);
        p2 = p2.mul(mat);

        // 2.) ořez dle W bodů
        if (p1.getW() <= 0 && p2.getW() <= 0) return;

        // 3.) tvorba z vektorů dehomogenizací (Point3D.dehomog())
        Optional<Vec3D> vo1 = p1.dehomog();
        Optional<Vec3D> vo2 = p2.dehomog();

        if (!vo1.isPresent() || !vo2.isPresent())
            return;

        Vec3D v1 = vo1.get();
        Vec3D v2 = vo2.get();

        // 4.) přepo�?et souřadnic na výšku/šírku našeho okna (viewport)
        v1 = v1.mul(new Vec3D(1, 1, 1))
                .add(new Vec3D(1, 1, 0))
                .mul(new Vec3D(
                        0.5 * (image.getDimension().getWidth() - 1),
                        0.5 * (image.getDimension().getHeight() - 1),
                        1));

        v2 = v2.mul(new Vec3D(1, 1, 1))
                .add(new Vec3D(1, 1, 0))
                .mul(new Vec3D(
                        0.5 * (image.getDimension().getWidth() - 1),
                        0.5 * (image.getDimension().getHeight() - 1),
                        1));

        // 5.) výsledek vykreslit
        lineDDA((int) v1.getX(), (int) v1.getY(), (int) v2.getX(),
                (int) v2.getY(), color);
    }

    // metody vykreslování
    public void lineDDA(int x1, int y1, int x2, int y2, Color color) {

        float k, g, h; //G = P�?�?RŮSTEK X, H = P�?�?RŮSTEK Y
        int dy = y2 - y1;
        int dx = x2 - x1;
        k = dy / (float) dx;

        //ur�?ení řídící osy
        if (Math.abs(dx) > Math.abs(dy)) {
            g = 1;
            h = k;
            if (x1 > x2) { // prohození
                int temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 = y2;
                y2 = temp;
            }
        } else {
            g = 1 / k;
            h = 1;
            if (y1 > y2) { //oto�?ení
                int temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 = y2;
                y2 = temp;
            }
        }

        float x = x1;
        float y = y1;
        int max = Math.max(Math.abs(dx), Math.abs(dy));

        for (int i = 0; i <= max; i++) {
            drawPixel(Math.round(x), Math.round(y), color);
            x += g;
            y += h;
        }
    }

    private void drawPixel(int x, int y, Color color) {
        image.pixel(x, y, color);
    }

    public void setModel(Mat4 model) {
        this.model = model;
    }

    public void setView(Mat4 view) {
        this.view = view;
    }

    public void setProjection(Mat4 projection) {
        this.projection = projection;
    }
}
