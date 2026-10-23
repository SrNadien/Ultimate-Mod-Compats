import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class GenCraftingEntities {

    private static BufferedImage image;

    private static int noise(int x, int y, int seed) {
        int h = x * 374761393 + y * 668265263 + seed * 1274126177;
        h = (h ^ (h >>> 13)) * 1274126177;
        h ^= h >>> 16;
        return h & 0xFFFF;
    }

    private static int pick(int[] palette, int x, int y, int seed, int accentChance) {
        int n = noise(x, y, seed);
        if (accentChance > 0 && (n % 1000) < accentChance) {
            return palette[3];
        }
        int r = (n >> 4) % 100;
        if (r < 18) {
            return palette[0];
        }
        if (r < 78) {
            return palette[1];
        }
        return palette[2];
    }

    private static void px(int x, int y, int color) {
        if (x >= 0 && y >= 0 && x < image.getWidth() && y < image.getHeight()) {
            image.setRGB(x, y, color);
        }
    }

    private static final class Box {
        final int u;
        final int v;
        final int w;
        final int h;
        final int d;

        Box(int u, int v, int w, int h, int d) {
            this.u = u;
            this.v = v;
            this.w = w;
            this.h = h;
            this.d = d;
        }

        void front(int x, int y, int c) {
            px(u + d + x, v + d + y, c);
        }

        void back(int x, int y, int c) {
            px(u + 2 * d + w + x, v + d + y, c);
        }

        void right(int x, int y, int c) {
            px(u + x, v + d + y, c);
        }

        void left(int x, int y, int c) {
            px(u + d + w + x, v + d + y, c);
        }

        void top(int x, int y, int c) {
            px(u + d + x, v + y, c);
        }

        void bottom(int x, int y, int c) {
            px(u + d + w + x, v + y, c);
        }

        void fill(int[] top, int[] side, int[] bottom, int seed, int accent) {
            for (int y = 0; y < d; y++) {
                for (int x = 0; x < w; x++) {
                    top(x, y, pick(top, x, y, seed, accent));
                    bottom(x, y, pick(bottom, x, y, seed + 1, 0));
                }
            }
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    front(x, y, pick(side, x, y, seed + 2, accent));
                    back(x, y, pick(side, x, y, seed + 3, accent));
                }
                for (int x = 0; x < d; x++) {
                    right(x, y, pick(side, x, y, seed + 4, accent));
                    left(x, y, pick(side, x, y, seed + 5, accent));
                }
            }
        }

        void shadeVertical(int dark, int amount) {
            for (int y = 0; y < h; y++) {
                if (y * 3 >= h * 2) {
                    for (int x = 0; x < w; x++) {
                        blend(u + d + x, v + d + y, dark, amount);
                        blend(u + 2 * d + w + x, v + d + y, dark, amount);
                    }
                    for (int x = 0; x < d; x++) {
                        blend(u + x, v + d + y, dark, amount);
                        blend(u + d + w + x, v + d + y, dark, amount);
                    }
                }
            }
        }
    }

    private static void blend(int x, int y, int color, int amount) {
        int base = image.getRGB(x, y);
        int a = (base >>> 24) & 0xFF;
        if (a == 0) {
            return;
        }
        int r = mix((base >> 16) & 0xFF, (color >> 16) & 0xFF, amount);
        int g = mix((base >> 8) & 0xFF, (color >> 8) & 0xFF, amount);
        int b = mix(base & 0xFF, color & 0xFF, amount);
        image.setRGB(x, y, (a << 24) | (r << 16) | (g << 8) | b);
    }

    private static int mix(int a, int b, int amount) {
        return (a * (100 - amount) + b * amount) / 100;
    }

    private static void begin(int w, int h) {
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    }

    private static void save(String root, String path) throws Exception {
        File out = new File(root, path);
        out.getParentFile().mkdirs();
        ImageIO.write(image, "png", out);
    }

    private static void toad(String root) throws Exception {
        begin(64, 64);
        int[] top = {0xFF3F3519, 0xFF5E5028, 0xFF7A6934, 0xFF2D2512};
        int[] side = {0xFF4B4020, 0xFF6A5A2E, 0xFF85733B, 0xFF33291A};
        int[] belly = {0xFFB4A468, 0xFFC7B877, 0xFFD8CA90, 0xFFC7B877};
        Box body = new Box(0, 0, 8, 5, 9);
        body.fill(top, side, belly, 11, 55);
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 8; x++) {
                if (y >= 3) {
                    body.front(x, y, pick(belly, x, y, 21, 0));
                    body.back(x, y, pick(belly, x, y, 22, 0));
                }
            }
        }
        body.shadeVertical(0xFF1B150A, 20);
        Box head = new Box(0, 16, 7, 4, 4);
        head.fill(top, side, belly, 31, 40);
        for (int x = 0; x < 7; x++) {
            head.front(x, 2, 0xFF2A2112);
            head.front(x, 3, pick(belly, x, 3, 41, 0));
        }
        head.front(1, 1, 0xFF1D160B);
        head.front(5, 1, 0xFF1D160B);
        Box leftEye = new Box(24, 16, 2, 2, 2);
        Box rightEye = new Box(32, 16, 2, 2, 2);
        for (Box eye : new Box[]{leftEye, rightEye}) {
            eye.fill(new int[]{0xFFC8922A, 0xFFE0AE36, 0xFFF2C955, 0xFFE0AE36}, new int[]{0xFFC8922A, 0xFFE0AE36, 0xFFF2C955, 0xFFE0AE36}, new int[]{0xFFC8922A, 0xFFC8922A, 0xFFC8922A, 0xFFC8922A}, 51, 0);
            eye.front(0, 1, 0xFF14100A);
            eye.front(1, 1, 0xFF14100A);
        }
        Box[] frontLegs = {new Box(0, 32, 2, 3, 2), new Box(8, 32, 2, 3, 2)};
        for (Box leg : frontLegs) {
            leg.fill(top, side, side, 61, 30);
            leg.front(0, 2, 0xFFB4A468);
            leg.front(1, 2, 0xFFB4A468);
        }
        Box[] backLegs = {new Box(16, 32, 3, 3, 4), new Box(32, 32, 3, 3, 4)};
        for (Box leg : backLegs) {
            leg.fill(top, side, side, 71, 45);
            leg.shadeVertical(0xFF1B150A, 15);
            for (int z = 0; z < 4; z++) {
                leg.bottom(0, z, 0xFFB4A468);
                leg.bottom(1, z, 0xFFB4A468);
                leg.bottom(2, z, 0xFFB4A468);
            }
        }
        save(root, "textures/entity/toad.png");

        begin(64, 64);
        for (Box eye : new Box[]{leftEye, rightEye}) {
            eye.front(0, 0, 0xFFFFC24A);
            eye.front(1, 0, 0xFFFFC24A);
            eye.front(0, 1, 0xFFFF6A1E);
            eye.front(1, 1, 0xFFFF6A1E);
            for (int x = 0; x < 2; x++) {
                eye.top(x, 0, 0xFFFFA83A);
                eye.top(x, 1, 0xFFFFA83A);
            }
        }
        save(root, "textures/entity/toad_eyes.png");
    }

    private static void owl(String root) throws Exception {
        begin(64, 32);
        int[] feather = {0xFF5A4128, 0xFF7C5B38, 0xFF9A7448, 0xFF3E2B18};
        int[] belly = {0xFFCDB98C, 0xFFE2D2A8, 0xFFF0E4C4, 0xFF8B7048};
        Box body = new Box(0, 0, 6, 6, 5);
        body.fill(feather, feather, belly, 101, 80);
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 6; x++) {
                int c = (y % 2 == 0) ? 0xFFE2D2A8 : 0xFFCDB98C;
                if ((x + y) % 3 == 0) {
                    c = 0xFF8B7048;
                }
                body.front(x, y, c);
            }
        }
        Box head = new Box(22, 0, 6, 5, 5);
        head.fill(feather, feather, belly, 111, 60);
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 6; x++) {
                head.front(x, y, y == 0 ? 0xFF5A4128 : 0xFFEADBB5);
            }
        }
        head.front(0, 1, 0xFF3E2B18);
        head.front(5, 1, 0xFF3E2B18);
        head.front(0, 4, 0xFFCDB98C);
        head.front(5, 4, 0xFFCDB98C);
        int yellow = 0xFFF2C230;
        int black = 0xFF120D08;
        head.front(0, 2, yellow);
        head.front(1, 2, yellow);
        head.front(0, 3, black);
        head.front(1, 3, yellow);
        head.front(0, 1, yellow);
        head.front(1, 1, yellow);
        head.front(4, 2, yellow);
        head.front(5, 2, yellow);
        head.front(4, 3, yellow);
        head.front(5, 3, black);
        head.front(4, 1, yellow);
        head.front(5, 1, yellow);
        head.front(1, 3, black);
        head.front(4, 3, black);
        head.front(0, 3, yellow);
        head.front(5, 3, yellow);
        Box beak = new Box(44, 0, 1, 2, 1);
        beak.fill(new int[]{0xFF9A8458, 0xFFB09A66, 0xFFC8B27C, 0xFF9A8458}, new int[]{0xFF9A8458, 0xFFB09A66, 0xFFC8B27C, 0xFF9A8458}, new int[]{0xFF7A6842, 0xFF7A6842, 0xFF7A6842, 0xFF7A6842}, 121, 0);
        Box leftTuft = new Box(48, 0, 1, 2, 1);
        Box rightTuft = new Box(52, 0, 1, 2, 1);
        leftTuft.fill(feather, feather, feather, 131, 0);
        rightTuft.fill(feather, feather, feather, 132, 0);
        Box leftWing = new Box(0, 12, 1, 7, 4);
        Box rightWing = new Box(10, 12, 1, 7, 4);
        for (Box wing : new Box[]{leftWing, rightWing}) {
            wing.fill(feather, feather, feather, 141, 0);
            for (int y = 0; y < 7; y++) {
                for (int x = 0; x < 4; x++) {
                    int c = (y % 2 == 0) ? 0xFF6A4B2D : 0xFF86613A;
                    if (y >= 4) {
                        c = (x % 2 == 0) ? 0xFF4A3420 : 0xFF6A4B2D;
                    }
                    wing.left(x, y, c);
                    wing.right(x, y, c);
                }
            }
        }
        Box tail = new Box(20, 12, 4, 4, 1);
        tail.fill(feather, feather, feather, 151, 0);
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                int c = (y % 2 == 0) ? 0xFF6A4B2D : 0xFF86613A;
                tail.front(x, y, c);
                tail.back(x, y, c);
            }
        }
        Box leftLeg = new Box(30, 12, 1, 2, 1);
        Box rightLeg = new Box(34, 12, 1, 2, 1);
        int[] legPal = {0xFFB89A52, 0xFFC9AA5E, 0xFFD8BC74, 0xFFB89A52};
        leftLeg.fill(legPal, legPal, legPal, 161, 0);
        rightLeg.fill(legPal, legPal, legPal, 162, 0);
        save(root, "textures/entity/owl.png");

        begin(64, 32);
        int glow = 0xFFFFD84A;
        int[][] cells = {{0, 1}, {1, 1}, {0, 2}, {1, 2}, {0, 3}, {4, 1}, {5, 1}, {4, 2}, {5, 2}, {5, 3}};
        for (int[] cell : cells) {
            head.front(cell[0], cell[1], glow);
        }
        head.front(0, 3, 0xFFFF8A2A);
        head.front(5, 3, 0xFFFF8A2A);
        head.front(1, 3, 0x00000000);
        head.front(4, 3, 0x00000000);
        save(root, "textures/entity/owl_eyes.png");
    }

    private static void raven(String root) throws Exception {
        begin(64, 32);
        int[] plume = {0xFF101018, 0xFF1A1A28, 0xFF272540, 0xFF4B4478};
        int[] under = {0xFF0E0E16, 0xFF181824, 0xFF22202F, 0xFF2D2A44};
        Box body = new Box(0, 0, 5, 5, 8);
        body.fill(plume, plume, under, 201, 35);
        Box head = new Box(26, 0, 4, 4, 4);
        head.fill(plume, plume, under, 211, 35);
        head.front(0, 1, 0xFFB9C4FF);
        head.front(3, 1, 0xFFB9C4FF);
        head.front(0, 2, 0xFF0A0A10);
        head.front(3, 2, 0xFF0A0A10);
        Box beak = new Box(42, 0, 1, 1, 3);
        int[] beakPal = {0xFF2A2A34, 0xFF3A3A48, 0xFF50505F, 0xFF3A3A48};
        beak.fill(beakPal, beakPal, beakPal, 221, 0);
        Box leftWing = new Box(0, 14, 1, 5, 7);
        Box rightWing = new Box(16, 14, 1, 5, 7);
        for (Box wing : new Box[]{leftWing, rightWing}) {
            wing.fill(plume, plume, under, 231, 60);
            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 7; x++) {
                    int c = (x % 2 == 0) ? 0xFF14141F : 0xFF222038;
                    if (y == 0) {
                        c = 0xFF2B2848;
                    }
                    if (x >= 5 && y >= 3) {
                        c = 0xFF0C0C14;
                    }
                    wing.left(x, y, c);
                    wing.right(x, y, c);
                }
            }
        }
        Box tail = new Box(32, 14, 4, 1, 6);
        tail.fill(plume, plume, under, 241, 40);
        for (int z = 0; z < 6; z++) {
            for (int x = 0; x < 4; x++) {
                tail.top(x, z, (x % 2 == 0) ? 0xFF1A1A28 : 0xFF2A2848);
            }
        }
        Box leftLeg = new Box(0, 26, 1, 2, 1);
        Box rightLeg = new Box(4, 26, 1, 2, 1);
        int[] legPal = {0xFF35333F, 0xFF454352, 0xFF565466, 0xFF454352};
        leftLeg.fill(legPal, legPal, legPal, 251, 0);
        rightLeg.fill(legPal, legPal, legPal, 252, 0);
        save(root, "textures/entity/raven.png");

        begin(64, 32);
        head.front(0, 1, 0xFFFF3A3A);
        head.front(3, 1, 0xFFFF3A3A);
        head.front(0, 2, 0xFFFF9A5A);
        head.front(3, 2, 0xFFFF9A5A);
        save(root, "textures/entity/raven_eyes.png");
    }

    private static void hound(String root) throws Exception {
        begin(64, 32);
        px(5, 6, 0xFFFF7A1A);
        px(5, 7, 0xFFFFC24A);
        px(8, 6, 0xFFFF7A1A);
        px(8, 7, 0xFFFFC24A);
        save(root, "textures/entity/hound_eyes.png");
    }

    private static void armorHat(String root) throws Exception {
        begin(64, 64);
        int[] cloth = {0xFF2F1A55, 0xFF432773, 0xFF553591, 0xFF6E4AB0};
        int[] clothDark = {0xFF241344, 0xFF331D5C, 0xFF432773, 0xFF553591};
        Box brim = new Box(0, 0, 14, 1, 14);
        brim.fill(cloth, cloth, clothDark, 301, 25);
        for (int y = 0; y < 14; y++) {
            for (int x = 0; x < 14; x++) {
                int edge = Math.min(Math.min(x, 13 - x), Math.min(y, 13 - y));
                if (edge == 0) {
                    brim.top(x, y, 0xFF2A1750);
                    brim.bottom(x, y, 0xFF1D0F3A);
                }
            }
        }
        Box band = new Box(0, 40, 10, 1, 10);
        int[] bandPal = {0xFF1B1230, 0xFF26193F, 0xFF33224F, 0xFF26193F};
        band.fill(bandPal, bandPal, bandPal, 311, 0);
        band.front(4, 0, 0xFFE9C14A);
        band.front(5, 0, 0xFFF6DB7A);
        band.top(4, 9, 0xFFE9C14A);
        Box cone1 = new Box(0, 15, 9, 3, 9);
        cone1.fill(cloth, cloth, clothDark, 321, 30);
        Box cone2 = new Box(0, 28, 6, 3, 6);
        cone2.fill(cloth, cloth, clothDark, 331, 30);
        Box cone3 = new Box(24, 28, 4, 3, 4);
        cone3.fill(cloth, cloth, clothDark, 341, 30);
        Box cone4 = new Box(40, 28, 2, 3, 2);
        cone4.fill(cloth, cloth, clothDark, 351, 0);
        Box tip = new Box(48, 28, 1, 3, 1);
        tip.fill(cloth, cloth, clothDark, 361, 0);
        for (int y = 0; y < 3; y++) {
            cone1.front(4, y, 0xFF6E4AB0);
        }
        cone2.front(3, 1, 0xFFF0D060);
        save(root, "textures/models/armor/witch_hat_layer_1.png");
    }

    private static void armorRobe(String root) throws Exception {
        begin(64, 64);
        int[] cloth = {0xFF3A2068, 0xFF4E2D88, 0xFF633AA6, 0xFF7E55C4};
        int[] clothDark = {0xFF2B1850, 0xFF3A2068, 0xFF4E2D88, 0xFF633AA6};
        int[] trim = {0xFFB8902F, 0xFFD2A93C, 0xFFE9C85A, 0xFFF6DE88};
        Box body = new Box(0, 0, 8, 12, 4);
        body.fill(cloth, cloth, clothDark, 401, 30);
        for (int y = 0; y < 12; y++) {
            body.front(3, y, 0xFF2B1850);
            body.front(4, y, 0xFF2B1850);
        }
        for (int y = 1; y < 12; y += 3) {
            body.front(2, y, trim[1]);
            body.front(5, y, trim[1]);
        }
        Box skirt1 = new Box(0, 16, 9, 5, 5);
        skirt1.fill(cloth, cloth, clothDark, 411, 30);
        Box skirt2 = new Box(0, 28, 11, 6, 6);
        skirt2.fill(cloth, cloth, clothDark, 421, 30);
        for (int x = 0; x < 11; x++) {
            skirt2.front(x, 5, trim[1]);
            skirt2.back(x, 5, trim[1]);
            if (x % 2 == 0) {
                skirt2.front(x, 4, trim[0]);
            }
        }
        for (int x = 0; x < 6; x++) {
            skirt2.right(x, 5, trim[1]);
            skirt2.left(x, 5, trim[1]);
        }
        for (int x = 0; x < 9; x++) {
            skirt1.front(x, 4, 0xFF2B1850);
        }
        Box collar = new Box(0, 42, 10, 2, 6);
        collar.fill(trim, trim, trim, 431, 0);
        Box arm = new Box(24, 0, 4, 12, 4);
        arm.fill(cloth, cloth, clothDark, 441, 30);
        Box sleeve = new Box(36, 32, 5, 5, 5);
        sleeve.fill(clothDark, clothDark, clothDark, 451, 25);
        for (int x = 0; x < 5; x++) {
            sleeve.front(x, 4, trim[1]);
            sleeve.back(x, 4, trim[1]);
            sleeve.right(x, 4, trim[1]);
            sleeve.left(x, 4, trim[1]);
        }
        for (int y = 0; y < 12; y++) {
            if (y % 4 == 1) {
                arm.front(1, y, trim[0]);
                arm.back(2, y, trim[0]);
            }
        }
        save(root, "textures/models/armor/witch_robe_layer_1.png");
    }

    private static void armorCloak(String root) throws Exception {
        begin(64, 64);
        int[] cloth = {0xFF1D1830, 0xFF2A2347, 0xFF383062, 0xFF4B417F};
        int[] lining = {0xFF4B1F5E, 0xFF5E2A75, 0xFF74398F, 0xFF8E4FAB};
        int[] trim = {0xFFB8902F, 0xFFD2A93C, 0xFFE9C85A, 0xFFF6DE88};
        Box body = new Box(0, 0, 8, 12, 4);
        body.fill(cloth, cloth, cloth, 501, 20);
        Box cape = new Box(24, 0, 10, 22, 1);
        cape.fill(cloth, cloth, cloth, 511, 25);
        for (int y = 0; y < 22; y++) {
            for (int x = 0; x < 10; x++) {
                int edge = Math.min(x, 9 - x);
                cape.back(x, y, pick(cloth, x, y, 512, 25));
                cape.front(x, y, pick(lining, x, y, 513, 25));
                if (y >= 20 || edge == 0) {
                    cape.back(x, y, 0xFF14102A);
                    cape.front(x, y, 0xFF3A1A4A);
                }
            }
        }
        for (int x = 0; x < 10; x += 3) {
            cape.back(x, 19, trim[1]);
            cape.back(x, 20, trim[0]);
        }
        Box mantle = new Box(0, 24, 12, 3, 6);
        mantle.fill(cloth, cloth, lining, 521, 20);
        for (int x = 0; x < 12; x++) {
            mantle.front(x, 2, trim[1]);
            mantle.back(x, 2, trim[1]);
        }
        for (int x = 0; x < 6; x++) {
            mantle.right(x, 2, trim[1]);
            mantle.left(x, 2, trim[1]);
        }
        Box clasp = new Box(36, 24, 2, 2, 1);
        clasp.fill(trim, trim, trim, 531, 0);
        clasp.front(0, 0, 0xFFFF5A8A);
        clasp.front(1, 0, 0xFFFF8AAE);
        clasp.front(0, 1, 0xFFC02A5A);
        clasp.front(1, 1, 0xFFFF5A8A);
        Box pad = new Box(0, 36, 5, 3, 5);
        pad.fill(cloth, cloth, lining, 541, 20);
        for (int x = 0; x < 5; x++) {
            pad.front(x, 2, trim[1]);
            pad.back(x, 2, trim[1]);
        }
        save(root, "textures/models/armor/witch_cloak_layer_1.png");
    }

    private static void armorBoots(String root) throws Exception {
        begin(64, 64);
        int[] leather = {0xFF2A1A12, 0xFF3C271B, 0xFF52362A, 0xFF6E4A3A};
        int[] trim = {0xFFB8902F, 0xFFD2A93C, 0xFFE9C85A, 0xFFF6DE88};
        Box shaft = new Box(0, 0, 4, 5, 4);
        shaft.fill(leather, leather, leather, 601, 25);
        shaft.front(1, 3, trim[1]);
        shaft.front(2, 3, trim[2]);
        for (int x = 0; x < 4; x++) {
            shaft.front(x, 4, 0xFF1B100B);
            shaft.back(x, 4, 0xFF1B100B);
        }
        Box cuff = new Box(0, 10, 5, 2, 5);
        cuff.fill(trim, trim, trim, 611, 0);
        for (int x = 0; x < 5; x++) {
            cuff.front(x, 1, trim[0]);
            cuff.back(x, 1, trim[0]);
        }
        Box toe = new Box(20, 10, 3, 2, 3);
        toe.fill(leather, leather, new int[]{0xFF1B100B, 0xFF1B100B, 0xFF1B100B, 0xFF1B100B}, 621, 25);
        toe.front(1, 0, 0xFF6E4A3A);
        save(root, "textures/models/armor/witch_boots_layer_1.png");
    }

    public static void main(String[] args) throws Exception {
        String root = args[0];
        toad(root);
        owl(root);
        raven(root);
        hound(root);
        armorHat(root);
        armorRobe(root);
        armorCloak(root);
        armorBoots(root);
    }
}
