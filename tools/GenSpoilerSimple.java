import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class GenSpoilerSimple {

    private static final int WIDTH = 1600;
    private static final int HEIGHT = 900;
    private static final int LOGO = 640;
    private static final int MOON_X = 627;
    private static final int MOON_Y = 600;
    private static final int MOON_R = 410;

    public static void main(String[] args) throws Exception {
        BufferedImage logo = ImageIO.read(new File(args[0]));
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        g.setPaint(new RadialGradientPaint(WIDTH * 0.3f, HEIGHT * 0.4f, WIDTH * 0.9f, new float[]{0f, 1f},
                new Color[]{new Color(0x2C1050), new Color(0x0F0520)}));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        BufferedImage moon = logo.getSubimage(MOON_X - MOON_R, MOON_Y - MOON_R - 60, MOON_R * 2, MOON_R * 2 + 60);
        moon = feather(moon);
        int moonWidth = LOGO;
        int moonHeight = moon.getHeight() * LOGO / moon.getWidth();
        g.drawImage(moon, 90, (HEIGHT - moonHeight) / 2, moonWidth, moonHeight, null);

        int x = 830;
        g.setFont(new Font("Segoe UI", Font.BOLD, 88));
        shadow(g, "ULTIMATE", x, 340, Color.WHITE);
        shadow(g, "MOD COMPATS", x, 430, Color.WHITE);

        g.setFont(new Font("Segoe UI", Font.PLAIN, 40));
        shadow(g, "Hombres lobo y vampiros", x, 540, new Color(0xD7BEFF));
        shadow(g, "para Enchanted", x, 590, new Color(0xD7BEFF));

        g.setFont(new Font("Segoe UI", Font.BOLD, 34));
        FontMetrics metrics = g.getFontMetrics();
        String tag = "SPOILER";
        int tagWidth = metrics.stringWidth(tag) + 56;
        g.setColor(new Color(0xFF7AD9));
        g.fillRoundRect(x, 650, tagWidth, 60, 20, 20);
        g.setColor(new Color(0x1B1340));
        g.drawString(tag, x + 28, 650 + 42);

        g.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        shadow(g, "NeoForge 1.21.1", x + tagWidth + 30, 692, new Color(0xB48CF0));

        g.dispose();
        File out = new File(args[1]);
        out.getParentFile().mkdirs();
        ImageIO.write(image, "png", out);
    }

    private static BufferedImage feather(BufferedImage source) {
        BufferedImage result = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int fade = 70;
        for (int y = 0; y < source.getHeight(); y++) {
            for (int x = 0; x < source.getWidth(); x++) {
                int edge = Math.min(Math.min(x, source.getWidth() - 1 - x), Math.min(y, source.getHeight() - 1 - y));
                double factor = Math.min(1.0, edge / (double) fade);
                int argb = source.getRGB(x, y);
                int alpha = (int) (((argb >>> 24) & 0xFF) * factor);
                result.setRGB(x, y, (alpha << 24) | (argb & 0xFFFFFF));
            }
        }
        return result;
    }

    private static void shadow(Graphics2D g, String text, int x, int y, Color color) {
        g.setColor(new Color(0, 0, 0, 160));
        g.drawString(text, x + 4, y + 4);
        g.setColor(color);
        g.drawString(text, x, y);
    }
}
