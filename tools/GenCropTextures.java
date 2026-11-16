import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;

/**
 * Bakes the seed and essence textures of the crops that used the generic Mystical Agriculture ones.
 *
 * args: itemOutputDir seedTemplate essenceIngotTemplate essenceDustTemplate realEssencesDir essenceGemTemplate
 * blockOutputDir flowerIngotTemplate flowerDustTemplate
 */
public class GenCropTextures {

    private record Tinted(String id, int color, String essenceBase) {
    }

    private record Essence(String id, String source) {
    }

    private static final Tinted[] TINTED = {
            new Tinted("darkstone", 0x1E1D29, "ingot"),
            new Tinted("crimson_iron", 0xFC6087, "ingot"),
            new Tinted("azure_silver", 0xFABEFC, "ingot"),
            new Tinted("unexplored_wood", 0xBF991D, "dust"),
            new Tinted("amberite_ingot", 0x794730, "ingot"),
            new Tinted("lustrous_dust", 0xB1B6C1, "dust"),
            new Tinted("tenebrite_ingot", 0x222232, "ingot"),
            new Tinted("gorium_ingot", 0x36363F, "ingot"),
            new Tinted("luminite_ingot", 0x4ECB57, "ingot"),
            new Tinted("viridite_gem", 0x35953F, "gem"),
            new Tinted("osmirite_ingot", 0x8190D9, "ingot"),
            new Tinted("umbrite_ingot", 0x3B2F17, "ingot"),
            new Tinted("ionite_ingot", 0xBAAC93, "ingot"),
            new Tinted("pyrestone_crystal", 0xB96354, "gem"),
            new Tinted("aurelite_crystal", 0x906F4D, "gem"),
            new Tinted("infernium_ingot", 0xA74EB4, "ingot"),
            new Tinted("quantum_infused_dust", 0x5B0407, "dust"),
            new Tinted("entro_ingot", 0x3EAD87, "ingot"),
            new Tinted("unstable_ingot", 0xF3F3F3, "ingot"),
            new Tinted("quantum_alloy", 0x653F89, "ingot"),
            new Tinted("glass", 0xAFD5DB, "gem"),
            new Tinted("enchanted_ingot", 0x87AB4D, "ingot"),
            new Tinted("demon_ingot", 0xDA1B00, "ingot"),
            new Tinted("evil_infused_iron_ingot", 0x61644B, "ingot"),
            new Tinted("bedrockium_ingot", 0x5A5A5A, "ingot"),
            new Tinted("soul_dust", 0x4D3A2E, "dust"),
            new Tinted("soulstone_cobble", 0x4D3930, "dust")
    };

    private static final Tinted[] ESSENCE_SEEDS = {
            new Tinted("ornium_essence", 0x6B2E8F, null),
            new Tinted("lightium_essence", 0x66859B, null),
            new Tinted("tornium_essence", 0x6E6E6E, null),
            new Tinted("ouranium_essence", 0x1F8A9C, null)
    };

    private static final Essence[] ESSENCES = {
            new Essence("imperium_essence", "imperium"),
            new Essence("insanium_essence", "insanium"),
            new Essence("prudentium_essence", "prudentium"),
            new Essence("tertium_essence", "tertium"),
            new Essence("supremium_essence_esc", "supremium"),
            new Essence("ornium_essence", "ornium"),
            new Essence("lightium_essence", "lightium"),
            new Essence("tornium_essence", "tornium"),
            new Essence("ouranium_essence", "ouranium")
    };

    public static void main(String[] args) throws Exception {
        File output = new File(args[0]);
        BufferedImage seed = ImageIO.read(new File(args[1]));
        BufferedImage ingot = ImageIO.read(new File(args[2]));
        BufferedImage dust = ImageIO.read(new File(args[3]));
        File real = new File(args[4]);
        BufferedImage gem = ImageIO.read(new File(args[5]));
        File blocks = new File(args[6]);
        BufferedImage flowerIngot = ImageIO.read(new File(args[7]));
        BufferedImage flowerDust = ImageIO.read(new File(args[8]));
        blocks.mkdirs();
        output.mkdirs();

        for (Tinted crop : TINTED) {
            write(output, crop.id() + "_seeds", recolor(seed, crop.color()));
            write(output, crop.id() + "_essence", recolor(template(crop.essenceBase(), ingot, dust, gem), crop.color()));
            write(blocks, crop.id() + "_flower", recolor("dust".equals(crop.essenceBase()) ? flowerDust : flowerIngot, crop.color()));
        }
        for (Tinted crop : ESSENCE_SEEDS) {
            write(output, crop.id() + "_seeds", recolor(seed, crop.color()));
            write(blocks, crop.id() + "_flower", recolor(flowerDust, crop.color()));
        }
        for (Essence essence : ESSENCES) {
            Files.copy(new File(real, essence.source() + ".png").toPath(),
                    new File(output, essence.id() + "_essence.png").toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static BufferedImage template(String base, BufferedImage ingot, BufferedImage dust, BufferedImage gem) {
        return switch (base) {
            case "dust" -> dust;
            case "gem" -> gem;
            default -> ingot;
        };
    }

    private static void write(File dir, String name, BufferedImage image) throws Exception {
        ImageIO.write(image, "png", new File(dir, name + ".png"));
    }

    private static BufferedImage recolor(BufferedImage original, int rgb) {
        float[] hsb = Color.RGBtoHSB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF, null);
        double min = 1.0;
        double max = 0.0;
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                int argb = original.getRGB(x, y);
                if (((argb >>> 24) & 0xFF) == 0) {
                    continue;
                }
                double luma = luma(argb);
                min = Math.min(min, luma);
                max = Math.max(max, luma);
            }
        }
        BufferedImage result = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                int argb = original.getRGB(x, y);
                int alpha = (argb >>> 24) & 0xFF;
                if (alpha == 0) {
                    continue;
                }
                double normalized = (luma(argb) - min) / Math.max(1e-6, max - min);
                float brightness = (float) Math.min(1.0, Math.max(0.08, hsb[2] * (0.55 + 0.9 * Math.min(1.0, Math.max(0.0, normalized)))));
                float saturation = hsb[1] * (float) (1.0 - 0.35 * Math.max(0.0, normalized - 0.8) / 0.2);
                int tinted = Color.HSBtoRGB(hsb[0], saturation, brightness) & 0xFFFFFF;
                result.setRGB(x, y, (alpha << 24) | tinted);
            }
        }
        return result;
    }

    private static double luma(int argb) {
        int r = (argb >> 16) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = argb & 0xFF;
        return (0.299 * r + 0.587 * g + 0.114 * b) / 255.0;
    }
}
