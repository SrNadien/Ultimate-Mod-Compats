import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class GenTierAltarTextures {

    // average color of each tier's essence texture (inferium .. ouranium)
    private static final int[] TIER_COLORS = {
            0x546600, 0x005715, 0x843300, 0x005A9B, 0x930000,
            0x49006E, 0x432454, 0x66859B, 0x4D4D4D, 0x153D44
    };

    private static final String[][] SOURCES = {
            {"infusion_altar_tier_%d_side", "infusion_altar_side"},
            {"infusion_altar_tier_%d_bottom", "infusion_altar_bottom"},
            {"infusion_altar_tier_%d_top", "infusion_pedestal_top"},
            {"awakening_altar_tier_%d_side", "awakening_altar_side"},
            {"awakening_altar_tier_%d_bottom", "awakening_altar_bottom"},
            {"awakening_altar_tier_%d_top", "awakening_pedestal_top"},
            {"infusion_pedestal_tier_%d_base", "stone"},
            {"awakening_pedestal_tier_%d_base", "soulstone"},
            {"essence_vessel_tier_%d_base", "soulstone"},
            {"essence_vessel_tier_%d_top", "essence_vessel_top"},
            {"essence_vessel_tier_%d_side", "essence_vessel_side"},
            {"machine_tier_%d_side", "machine_side"},
            {"seed_reprocessor_tier_%d_front", "seed_reprocessor_front"},
            {"seed_reprocessor_tier_%d_front_on", "seed_reprocessor_front_on"},
            {"furnace_tier_%d_front", "furnace_front"},
            {"furnace_tier_%d_front_on", "furnace_front_on"},
            {"item:infusion_crystal_tier_%d", "infusion_crystal"}
    };

    public static void main(String[] args) throws Exception {
        File input = new File(args[0]);
        File output = new File(args[1]);
        File items = new File(args[2]);
        output.mkdirs();
        items.mkdirs();
        for (String[] source : SOURCES) {
            BufferedImage original = ImageIO.read(new File(input, source[1] + ".png"));
            double[] range = lumaRange(original);
            boolean item = source[0].startsWith("item:");
            String name = item ? source[0].substring("item:".length()) : source[0];
            for (int tier = 1; tier <= TIER_COLORS.length; tier++) {
                BufferedImage tinted = recolor(original, TIER_COLORS[tier - 1], range);
                ImageIO.write(tinted, "png", new File(item ? items : output, name.formatted(tier) + ".png"));
            }
        }
    }

    private static BufferedImage recolor(BufferedImage original, int rgb, double[] range) {
        float[] hsb = Color.RGBtoHSB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF, null);
        BufferedImage result = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                int argb = original.getRGB(x, y);
                int alpha = (argb >>> 24) & 0xFF;
                if (alpha == 0) {
                    result.setRGB(x, y, 0);
                    continue;
                }
                double normalized = (luma(argb) - range[0]) / Math.max(1e-6, range[1] - range[0]);
                float brightness = (float) Math.min(1.0, Math.max(0.05, hsb[2] * (0.55 + 0.9 * Math.min(1.0, Math.max(0.0, normalized)))));
                int tinted = Color.HSBtoRGB(hsb[0], hsb[1], brightness) & 0xFFFFFF;
                result.setRGB(x, y, (alpha << 24) | tinted);
            }
        }
        return result;
    }

    private static double[] lumaRange(BufferedImage image) {
        double min = 1.0;
        double max = 0.0;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int argb = image.getRGB(x, y);
                if (((argb >>> 24) & 0xFF) == 0) {
                    continue;
                }
                double luma = luma(argb);
                min = Math.min(min, luma);
                max = Math.max(max, luma);
            }
        }
        return new double[]{min, max};
    }

    private static double luma(int argb) {
        int r = (argb >> 16) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = argb & 0xFF;
        return (0.299 * r + 0.587 * g + 0.114 * b) / 255.0;
    }
}
