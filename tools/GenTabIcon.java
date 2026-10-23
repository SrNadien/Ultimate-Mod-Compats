import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import javax.imageio.ImageIO;

public class GenTabIcon {

    private static final String[] ART = {
            "s.............y.",
            "..oo........oo..",
            "..opo......opo..",
            "..oppo....oppo..",
            "..oaaooooooaao..",
            ".oaccbbbbbbbbao.",
            ".oabbbbbbbbsbao.",
            ".obbeebbbbeebbo.",
            ".obbeebbbbeebbo.",
            ".oabbbbnnbbbbao.",
            ".oaabsbbbbbbaao.",
            "..oaabbbbbbaao..",
            "...ooaaaaaaoo...",
            ".....oooooo.....",
            "y.............s.",
            "...s.......s...."
    };

    private static final Map<Character, Integer> PALETTE = Map.of(
            'o', 0xFF160F36,
            'a', 0xFF3A2A8C,
            'b', 0xFF5B44CC,
            'c', 0xFF86B6FF,
            'e', 0xFF6FF7FF,
            'p', 0xFFFF7AD9,
            'n', 0xFFFF9BD0,
            's', 0xFFFFFFFF,
            'y', 0xFFFFE680
    );

    public static void main(String[] args) throws Exception {
        BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                Integer color = PALETTE.get(ART[y].charAt(x));
                image.setRGB(x, y, color == null ? 0 : color);
            }
        }
        File out = new File(args[0]);
        out.getParentFile().mkdirs();
        ImageIO.write(image, "png", out);
    }
}
