import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;

public class GenTabBackground {

    private static final int[][] STOPS = {
            {0, 0x05030F},
            {40, 0x0E0A2A},
            {85, 0x171046},
            {139, 0x1C1458},
            {198, 0x34277E},
            {230, 0x6F5CD0},
            {255, 0xB7A8FF}
    };

    private static final String[] CAT = {
            "oo........oo",
            "oao......oao",
            "oaaooooooaao",
            "oaaaaaaaaaao",
            "oaeeaaaaeeao",
            "oaeeaaaaeeao",
            "oaaaannaaaao",
            ".oaaaaaaaao.",
            "..oooooooo.."
    };

    private static final Map<Character, Integer> CAT_PALETTE = Map.of(
            'o', 0xFF0B0820,
            'a', 0xFF6A54D6,
            'e', 0xFF6FF7FF,
            'n', 0xFFFF9BD0
    );

    private static final int[] STAR_COLORS = {0xFFFFFFFF, 0xFFFFFFFF, 0xFFC9E4FF, 0xFFFFE9A8, 0xFFFFB8F0};

    public static void main(String[] args) throws Exception {
        BufferedImage source = ImageIO.read(new File(args[0]));
        int width = source.getWidth();
        int height = source.getHeight();
        BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Random random = new Random(26);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = source.getRGB(x, y);
                int alpha = argb >>> 24;
                if (alpha == 0) {
                    out.setRGB(x, y, 0);
                    continue;
                }
                int lum = (((argb >> 16) & 0xFF) + ((argb >> 8) & 0xFF) + (argb & 0xFF)) / 3;
                int rgb = map(lum);
                if (lum >= 194 && lum <= 202) {
                    rgb = nebula(rgb, x, y, width, height);
                    if (random.nextInt(100) < 5) {
                        out.setRGB(x, y, STAR_COLORS[random.nextInt(STAR_COLORS.length)]);
                        continue;
                    }
                }
                out.setRGB(x, y, (alpha << 24) | rgb);
            }
        }

        int originX = 177;
        int originY = 3;
        for (int row = 0; row < CAT.length; row++) {
            for (int column = 0; column < CAT[row].length(); column++) {
                Integer color = CAT_PALETTE.get(CAT[row].charAt(column));
                if (color != null) {
                    out.setRGB(originX + column, originY + row, color);
                }
            }
        }

        File target = new File(args[1]);
        target.getParentFile().mkdirs();
        ImageIO.write(out, "png", target);
    }

    private static int map(int lum) {
        for (int i = 1; i < STOPS.length; i++) {
            if (lum <= STOPS[i][0]) {
                double t = (lum - STOPS[i - 1][0]) / (double) (STOPS[i][0] - STOPS[i - 1][0]);
                return lerp(STOPS[i - 1][1], STOPS[i][1], t);
            }
        }
        return STOPS[STOPS.length - 1][1];
    }

    private static int nebula(int base, int x, int y, int width, int height) {
        double t = (x + y) / (double) (width + height) * 2.4;
        int tint = t < 0.5 ? lerp(0x2B1F66, 0x1F3A7A, t / 0.5) : lerp(0x1F3A7A, 0x5A2A7A, Math.min(1.0, (t - 0.5) / 0.5));
        return lerp(base, tint, 0.65);
    }

    private static int lerp(int from, int to, double t) {
        int r = (int) Math.round(((from >> 16) & 0xFF) * (1 - t) + ((to >> 16) & 0xFF) * t);
        int g = (int) Math.round(((from >> 8) & 0xFF) * (1 - t) + ((to >> 8) & 0xFF) * t);
        int b = (int) Math.round((from & 0xFF) * (1 - t) + (to & 0xFF) * t);
        return (r << 16) | (g << 8) | b;
    }
}
