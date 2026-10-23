import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class GenLogo {

    private static final int SIZE = 1254;
    private static final Color OUTLINE = new Color(0x12041F);
    private static final Color MOON_LIGHT = new Color(0xD7BEFF);
    private static final Color MOON_MID = new Color(0xB48CF0);
    private static final Color MOON_DARK = new Color(0x8E5CD9);
    private static final Color WOLF = new Color(0x1B0A35);
    private static final Color WOLF_EDGE = new Color(0x9B6BE8);
    private static final Color GREEN = new Color(0x5BFF8A);
    private static final Color RED = new Color(0xFF3B5C);
    private static final Color FANG = new Color(0xFFF3E6);

    public static void main(String[] args) throws Exception {
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        g.setPaint(new RadialGradientPaint(SIZE / 2f, SIZE * 0.35f, SIZE * 0.85f, new float[]{0f, 1f},
                new Color[]{new Color(0x2C1050), new Color(0x0F0520)}));
        g.fillRect(0, 0, SIZE, SIZE);

        moon(g);
        wolf(g);
        g.dispose();
        File out = new File(args[0]);
        out.getParentFile().mkdirs();
        ImageIO.write(image, "png", out);
    }

    private static void moon(Graphics2D g) {
        double cx = SIZE / 2.0;
        double cy = 600;
        double r = 340;
        Shape disc = new Ellipse2D.Double(cx - r, cy - r, r * 2, r * 2);
        g.setPaint(new GradientPaint((float) (cx - r), (float) (cy - r), MOON_LIGHT, (float) (cx + r), (float) (cy + r), MOON_DARK));
        g.fill(disc);
        g.setColor(MOON_MID);
        g.fill(new Ellipse2D.Double(cx + 40, cy - 250, 260, 260));
        g.fill(new Ellipse2D.Double(cx - 230, cy + 80, 150, 150));
        g.fill(new Ellipse2D.Double(cx + 120, cy + 150, 100, 100));
        g.setColor(OUTLINE);
        g.setStroke(new BasicStroke(18f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(disc);
    }

    private static void rings(Graphics2D g) {
        g.setColor(new Color(0xB07CF5));
        g.setStroke(new BasicStroke(16f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        double cx = SIZE / 2.0;
        double cy = 600;
        double r = 400;
        for (int start : new int[]{25, 115, 205, 295}) {
            g.draw(new java.awt.geom.Arc2D.Double(cx - r, cy - r, r * 2, r * 2, start, 40, java.awt.geom.Arc2D.OPEN));
        }
    }

    private static void wolf(Graphics2D g) {
        double[][] right = {
                {60, -150}, {118, -310}, {172, -118}, {222, -42}, {172, -22}, {236, 62}, {182, 74}, {222, 156},
                {126, 150}, {66, 176}, {44, 216}
        };
        double[][] outline = new double[right.length * 2][];
        for (int i = 0; i < right.length; i++) {
            outline[i] = right[i];
        }
        for (int i = right.length - 1, k = right.length; i >= 0; i--, k++) {
            outline[k] = new double[]{-right[i][0], right[i][1]};
        }
        AffineTransform place = AffineTransform.getTranslateInstance(SIZE / 2.0, 585);
        Path2D head = polygon(outline);
        Shape placed = place.createTransformedShape(head);
        g.setColor(WOLF);
        g.fill(placed);
        g.setColor(new Color(0x46257D));
        g.fill(place.createTransformedShape(polygon(new double[][]{{-72, -166}, {-116, -282}, {-152, -138}})));
        g.fill(place.createTransformedShape(polygon(new double[][]{{72, -166}, {116, -282}, {152, -138}})));
        g.setColor(new Color(0x33195C));
        g.fill(place.createTransformedShape(polygon(new double[][]{{-62, 6}, {-96, 150}, {-44, 216}, {44, 216}, {96, 150}, {62, 6}})));
        g.setColor(WOLF_EDGE);
        g.setStroke(new BasicStroke(9f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(placed);
        g.setColor(OUTLINE);
        g.setStroke(new BasicStroke(22f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        Area outside = new Area(new java.awt.geom.Rectangle2D.Double(0, 0, SIZE, SIZE));
        outside.subtract(new Area(placed));
        g.setClip(outside);
        g.draw(placed);
        g.setClip(null);

        g.setColor(GREEN);
        g.fill(place.createTransformedShape(polygon(new double[][]{{-128, -62}, {-46, -34}, {-64, -4}, {-124, -30}})));
        g.setColor(RED);
        g.fill(place.createTransformedShape(polygon(new double[][]{{128, -62}, {46, -34}, {64, -4}, {124, -30}})));

        g.setColor(new Color(0x120626));
        g.fill(place.createTransformedShape(polygon(new double[][]{{-34, 96}, {34, 96}, {0, 140}})));

        g.setColor(FANG);
        g.fill(place.createTransformedShape(polygon(new double[][]{{-40, 216}, {-12, 216}, {-26, 292}})));
        g.fill(place.createTransformedShape(polygon(new double[][]{{12, 216}, {40, 216}, {26, 292}})));
        g.setColor(OUTLINE);
        g.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(place.createTransformedShape(polygon(new double[][]{{-40, 216}, {-12, 216}, {-26, 292}})));
        g.draw(place.createTransformedShape(polygon(new double[][]{{12, 216}, {40, 216}, {26, 292}})));
        g.setColor(RED);
        g.fill(place.createTransformedShape(polygon(new double[][]{{-32, 300}, {-20, 300}, {-26, 328}})));
    }

    private static void stars(Graphics2D g) {
        double[][] big = {{1000, 250, 56}, {1090, 380, 34}, {230, 930, 30}, {340, 1010, 54}, {520, 1070, 28}, {190, 300, 26}, {1060, 930, 30}};
        g.setStroke(new BasicStroke(12f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(new Color(0x6C3FB5));
        g.drawLine(1000, 250, 1090, 380);
        g.drawLine(230, 930, 340, 1010);
        g.drawLine(340, 1010, 520, 1070);
        for (double[] s : big) {
            star(g, s[0], s[1], s[2]);
        }
    }

    private static void star(Graphics2D g, double x, double y, double r) {
        Path2D path = new Path2D.Double();
        for (int i = 0; i < 8; i++) {
            double angle = Math.PI / 4 * i - Math.PI / 2;
            double radius = i % 2 == 0 ? r : r * 0.28;
            double px = x + Math.cos(angle) * radius;
            double py = y + Math.sin(angle) * radius;
            if (i == 0) {
                path.moveTo(px, py);
            } else {
                path.lineTo(px, py);
            }
        }
        path.closePath();
        g.setColor(new Color(0xBE92FF));
        g.fill(path);
        g.setColor(OUTLINE);
        g.setStroke(new BasicStroke(6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(path);
    }

    private static Path2D polygon(double[][] points) {
        Path2D path = new Path2D.Double();
        for (int i = 0; i < points.length; i++) {
            if (i == 0) {
                path.moveTo(points[i][0], points[i][1]);
            } else {
                path.lineTo(points[i][0], points[i][1]);
            }
        }
        path.closePath();
        return path;
    }
}
