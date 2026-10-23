import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class GenFormEyes {

    public static void main(String[] args) throws Exception {
        BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        int white = 0xFFFFFFFF;
        int[][] eyes = {{9, 12}, {10, 12}, {13, 12}, {14, 12}};
        for (int[] eye : eyes) {
            image.setRGB(eye[0], eye[1], white);
        }
        File out = new File(args[0]);
        out.getParentFile().mkdirs();
        ImageIO.write(image, "png", out);
    }
}
