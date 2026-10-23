import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;

public class GenSpoiler {

    private static final int WIDTH = 1600;
    private static final int HEIGHT = 900;
    private static final int ICON = 48;
    private static final int CELL = 64;

    private static File textures;

    public static void main(String[] args) throws Exception {
        textures = new File(args[0]);
        BufferedImage screenshot = ImageIO.read(new File(args[1]));
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.setPaint(new GradientPaint(0, 0, new Color(0x0B0820), WIDTH, HEIGHT, new Color(0x2B1F66)));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setPaint(new GradientPaint(WIDTH, 0, new Color(0x5A2A7A, true), 0, HEIGHT, new Color(0x1F3A7A, true)));
        g.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.35f));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setComposite(java.awt.AlphaComposite.SrcOver);

        Random random = new Random(26);
        for (int i = 0; i < 420; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            int size = random.nextInt(10) == 0 ? 3 : 2;
            g.setColor(new Color(255, 255, 255, 80 + random.nextInt(175)));
            g.fillRect(x, y, size, size);
        }

        drawScaled(g, "item/tab_icon.png", 48, 36, 144);
        g.setFont(new Font("Segoe UI", Font.BOLD, 66));
        shadowText(g, "ULTIMATE MOD COMPATS", 220, 100, Color.WHITE);
        g.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        shadowText(g, "Magia inspirada en Witchery para Enchanted · NeoForge 1.21.1", 222, 150, new Color(0xC9B8FF));

        g.setColor(new Color(0xFF7AD9));
        g.fillRoundRect(222, 168, 190, 44, 16, 16);
        g.setFont(new Font("Segoe UI", Font.BOLD, 28));
        g.setColor(new Color(0x1B1340));
        g.drawString("SPOILER", 250, 200);

        column(g, 40, "CRIATURAS", List.of(
                "item/werewolf_fang.png", "item/vampire_fang.png", "item/silver_sword.png", "item/wooden_stake.png", "item/silver_ingot.png",
                "item/necromancer_scepter.png", "item/summoning_horn.png", "item/demonomicon.png", "item/blood_sigil.png", "item/lunar_talisman.png",
                "item/purifying_tonic.png", "item/grave_dust.png", "item/ectoplasm.png", "item/imp_horn.png"),
                List.of("Hombre lobo y vampiro jugables", "Fantasmas, imps y ents", "Siervos no-muertos y demonios", "9 ritos de necromancia"));

        column(g, 420, "MUNDO ESPIRITUAL", List.of(
                "item/spirit_dust.png", "item/dream_thread.png", "item/leap_essence.png", "item/climb_essence.png", "item/sight_essence.png",
                "block/dream_catcher.png", "block/spirit_bloom.png", "block/spirit_portal_top_active.png", "block/coven_altar_top.png", "block/spirit_stone.png",
                "block/spirit_grass.png", "block/spirit_log.png", "block/dream_loom_wood.png"),
                List.of("Dimensión propia y portal", "Atrapasueños y pesadillas", "Infusiones: salto, trepar, visión", "Covens: /umc coven"));

        column(g, 800, "BRUJERÍA RURAL", List.of(
                "item/witch_hat.png", "item/witch_robe.png", "item/witch_cloak.png", "item/witch_boots.png", "item/witch_cloth.png",
                "item/wormwood_sprig.png", "item/vervain_flower.png", "block/moonbloom.png", "block/mugwort.png", "item/moonthread.png",
                "item/wormwood_tincture.png", "item/vervain_tonic.png", "item/moondew.png", "item/wormwood_seeds.png", "item/vervain_seeds.png"),
                List.of("Sapo, lechuza, cuervo y sabueso", "Sombrero, túnica, capa y botas", "Ajenjo, verbena y luna-flor", "Guía propia en Modopedia"));

        panel(g, 1180, 235, 390, 600);
        g.setFont(new Font("Segoe UI", Font.BOLD, 28));
        shadowText(g, "PESTAÑA PROPIA", 1204, 280, new Color(0xFFE680));
        BufferedImage crop = screenshot.getSubimage(240, 82, 392, 326);
        g.drawImage(crop, 1186, 310, 378, 315, null);
        g.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        shadowText(g, "Gato espacial, fondo con estrellas", 1204, 670, new Color(0xE6DCFF));
        shadowText(g, "y todo el contenido en un solo lugar", 1204, 700, new Color(0xE6DCFF));
        shadowText(g, "Textos: es_es, es_ar, es_mx y en_us", 1204, 770, new Color(0xC9B8FF));

        g.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        shadowText(g, "Requiere Enchanted 4.2.7 · Todo el código y las texturas, desde cero", 40, 870, new Color(0xC9B8FF));

        g.dispose();
        File out = new File(args[2]);
        out.getParentFile().mkdirs();
        ImageIO.write(image, "png", out);
    }

    private static void column(Graphics2D g, int x, String title, List<String> icons, List<String> bullets) throws Exception {
        panel(g, x, 235, 360, 600);
        g.setFont(new Font("Segoe UI", Font.BOLD, 28));
        shadowText(g, title, x + 24, 280, new Color(0xFFE680));
        for (int i = 0; i < icons.size(); i++) {
            int cx = x + 24 + (i % 5) * CELL;
            int cy = 305 + (i / 5) * CELL;
            g.setColor(new Color(0x14, 0x0E, 0x38, 200));
            g.fillRoundRect(cx - 4, cy - 4, CELL - 4, CELL - 4, 10, 10);
            drawScaled(g, icons.get(i), cx + 2, cy + 2, ICON - 4);
        }
        g.setFont(new Font("Segoe UI", Font.PLAIN, 21));
        for (int i = 0; i < bullets.size(); i++) {
            int y = 560 + i * 56;
            g.setColor(new Color(0xFF7AD9));
            g.fillOval(x + 24, y - 14, 12, 12);
            shadowText(g, bullets.get(i), x + 46, y, Color.WHITE);
        }
    }

    private static void panel(Graphics2D g, int x, int y, int w, int h) {
        g.setColor(new Color(0x0B, 0x08, 0x20, 170));
        g.fillRoundRect(x, y, w, h, 28, 28);
        g.setColor(new Color(0x86, 0x76, 0xE0, 200));
        g.setStroke(new java.awt.BasicStroke(3f));
        g.drawRoundRect(x, y, w, h, 28, 28);
    }

    private static void drawScaled(Graphics2D g, String path, int x, int y, int size) throws Exception {
        BufferedImage icon = ImageIO.read(new File(textures, path));
        if (icon.getHeight() > icon.getWidth()) {
            icon = icon.getSubimage(0, 0, icon.getWidth(), icon.getWidth());
        }
        Object previous = g.getRenderingHint(RenderingHints.KEY_INTERPOLATION);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.drawImage(icon, x, y, size, size, null);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, previous == null ? RenderingHints.VALUE_INTERPOLATION_BILINEAR : previous);
    }

    private static void shadowText(Graphics2D g, String text, int x, int y, Color color) {
        g.setColor(new Color(0, 0, 0, 170));
        g.drawString(text, x + 2, y + 2);
        g.setColor(color);
        g.drawString(text, x, y);
    }
}
