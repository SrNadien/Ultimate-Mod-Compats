import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;

public class GenSpiritTextures {

    private static File root;

    public static void main(String[] args) throws Exception {
        root = new File(args[0]);
        blocks();
        items();
        effects();
        nightmare();
    }

    private static BufferedImage image(int w, int h) {
        return new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    }

    private static void save(BufferedImage image, String path) throws Exception {
        File out = new File(root, path);
        out.getParentFile().mkdirs();
        ImageIO.write(image, "png", out);
    }

    private static int rgb(int hex) {
        return 0xFF000000 | hex;
    }

    private static int mix(int a, int b, double t) {
        int ar = (a >> 16) & 255, ag = (a >> 8) & 255, ab = a & 255;
        int br = (b >> 16) & 255, bg = (b >> 8) & 255, bb = b & 255;
        int r = (int) Math.round(ar + (br - ar) * t);
        int g = (int) Math.round(ag + (bg - ag) * t);
        int bl = (int) Math.round(ab + (bb - ab) * t);
        return 0xFF000000 | (r << 16) | (g << 8) | bl;
    }

    private static void set(BufferedImage image, int x, int y, int color) {
        if (x >= 0 && y >= 0 && x < image.getWidth() && y < image.getHeight()) {
            image.setRGB(x, y, color);
        }
    }

    private static double[][] valueNoise(int size, int cell, long seed) {
        Random random = new Random(seed);
        int n = size / cell + 2;
        double[][] grid = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = random.nextDouble();
            }
        }
        double[][] out = new double[size][size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                double gx = (double) x / cell;
                double gy = (double) y / cell;
                int x0 = (int) gx;
                int y0 = (int) gy;
                double fx = gx - x0;
                double fy = gy - y0;
                fx = fx * fx * (3 - 2 * fx);
                fy = fy * fy * (3 - 2 * fy);
                int xa = x0 % (n - 1);
                int ya = y0 % (n - 1);
                double top = grid[ya][xa] * (1 - fx) + grid[ya][xa + 1] * fx;
                double bottom = grid[ya + 1][xa] * (1 - fx) + grid[ya + 1][xa + 1] * fx;
                out[y][x] = top * (1 - fy) + bottom * fy;
            }
        }
        return out;
    }

    private static int ramp(int[] palette, double value) {
        double v = Math.max(0.0, Math.min(0.9999, value));
        return rgb(palette[(int) (v * palette.length)]);
    }

    private static BufferedImage noiseTexture(int[] palette, long seed, int cell, double jitter) {
        BufferedImage image = image(16, 16);
        double[][] noise = valueNoise(16, cell, seed);
        Random random = new Random(seed * 31 + 7);
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                double v = noise[y][x] + (random.nextDouble() - 0.5) * jitter;
                image.setRGB(x, y, ramp(palette, v));
            }
        }
        return image;
    }

    private static void line(BufferedImage image, int x0, int y0, int x1, int y1, int color) {
        int dx = Math.abs(x1 - x0), dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1, sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;
        while (true) {
            set(image, x0, y0, color);
            if (x0 == x1 && y0 == y1) {
                break;
            }
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }

    private static void star(BufferedImage image, double cx, double cy, double r, int color) {
        int[] xs = new int[5];
        int[] ys = new int[5];
        for (int i = 0; i < 5; i++) {
            double a = -Math.PI / 2 + i * 2 * Math.PI / 5;
            xs[i] = (int) Math.round(cx + Math.cos(a) * r);
            ys[i] = (int) Math.round(cy + Math.sin(a) * r);
        }
        for (int i = 0; i < 5; i++) {
            int j = (i + 2) % 5;
            line(image, xs[i], ys[i], xs[j], ys[j], color);
        }
    }

    private static void ring(BufferedImage image, double cx, double cy, double inner, double outer, int color) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                double d = Math.hypot(x - cx, y - cy);
                if (d >= inner && d <= outer) {
                    image.setRGB(x, y, color);
                }
            }
        }
    }

    private static void blocks() throws Exception {
        BufferedImage stone = noiseTexture(new int[] {0x3B4A6B, 0x465982, 0x56688F, 0x6A7EA8, 0x7A8DB8}, 11, 4, 0.28);
        Random veins = new Random(5);
        for (int v = 0; v < 3; v++) {
            int x = veins.nextInt(12) + 2;
            int y = veins.nextInt(12) + 2;
            for (int i = 0; i < 4; i++) {
                set(stone, x, y, rgb(0x7FFFE8));
                set(stone, x + 1, y, mix(stone.getRGB(Math.min(15, x + 1), y), 0x7FFFE8, 0.35));
                x += veins.nextInt(3) - 1;
                y += veins.nextInt(2);
            }
        }
        save(stone, "block/spirit_stone.png");

        int[] soilPalette = {0x2F7F86, 0x3A9296, 0x4CAEA6, 0x66C7B8};
        BufferedImage top = noiseTexture(soilPalette, 21, 3, 0.35);
        Random speck = new Random(9);
        for (int i = 0; i < 10; i++) {
            set(top, speck.nextInt(16), speck.nextInt(16), rgb(0x9BEBD0));
        }
        save(top, "block/spirit_soil_top.png");

        int[] dirtPalette = {0x3A3050, 0x453A60, 0x514570, 0x5D5080};
        BufferedImage bottom = noiseTexture(dirtPalette, 22, 3, 0.4);
        save(bottom, "block/spirit_soil_bottom.png");

        BufferedImage side = noiseTexture(dirtPalette, 23, 3, 0.4);
        Random drip = new Random(3);
        for (int x = 0; x < 16; x++) {
            int depth = 3 + drip.nextInt(3);
            for (int y = 0; y < depth; y++) {
                int c = top.getRGB(x, y);
                if (y == depth - 1) {
                    c = mix(c, 0x2F7F86, 0.5);
                }
                side.setRGB(x, y, c);
            }
        }
        save(side, "block/spirit_soil_side.png");

        BufferedImage log = image(16, 16);
        int[] bark = {0x5C5878, 0x77739A, 0x8A86AC, 0xA29EC4};
        Random streak = new Random(4);
        double[] column = new double[16];
        for (int x = 0; x < 16; x++) {
            column[x] = streak.nextDouble();
        }
        double[][] barkNoise = valueNoise(16, 5, 14);
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                double v = column[x] * 0.7 + barkNoise[(y * 3) % 16][x] * 0.5 - 0.1;
                if ((x + (x / 5)) % 4 == 0) {
                    v -= 0.25;
                }
                log.setRGB(x, y, ramp(bark, v));
            }
        }
        save(log, "block/spirit_log.png");

        BufferedImage logTop = image(16, 16);
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                int d = Math.max(Math.abs(2 * x - 15), Math.abs(2 * y - 15));
                int c;
                if (d >= 13) {
                    c = bark[0];
                } else if (d >= 11) {
                    c = bark[2];
                } else {
                    c = (d / 2) % 2 == 0 ? 0xCFCBEA : 0xB4AFD8;
                }
                logTop.setRGB(x, y, rgb(c));
            }
        }
        set(logTop, 7, 7, rgb(0x8F8AC0));
        set(logTop, 8, 7, rgb(0x8F8AC0));
        set(logTop, 7, 8, rgb(0x8F8AC0));
        set(logTop, 8, 8, rgb(0x8F8AC0));
        save(logTop, "block/spirit_log_top.png");

        BufferedImage leaves = image(16, 16);
        int[] leafPalette = {0x4FA9CC, 0x6CC8E0, 0x8FE0EE, 0xB4F2F5};
        double[][] leafNoise = valueNoise(16, 3, 17);
        Random hole = new Random(8);
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                if (hole.nextDouble() < 0.16) {
                    leaves.setRGB(x, y, 0);
                    continue;
                }
                double v = leafNoise[y][x] + (hole.nextDouble() - 0.5) * 0.3;
                leaves.setRGB(x, y, ramp(leafPalette, v));
            }
        }
        Random glow = new Random(12);
        for (int i = 0; i < 7; i++) {
            set(leaves, glow.nextInt(16), glow.nextInt(16), rgb(0xFFD6F4));
        }
        save(leaves, "block/spirit_leaves.png");

        BufferedImage planks = image(16, 16);
        int[] plankPalette = {0xA39CCB, 0xB1AAD8, 0xBFB9E4, 0xCCC6EC};
        Random grain = new Random(15);
        for (int row = 0; row < 4; row++) {
            double base = grain.nextDouble() * 0.5;
            for (int y = row * 4; y < row * 4 + 4; y++) {
                for (int x = 0; x < 16; x++) {
                    double v = base + (grain.nextDouble() - 0.5) * 0.35 + ((x + row * 5) % 7 == 0 ? 0.15 : 0.0);
                    int c = ramp(plankPalette, v);
                    if (y % 4 == 3) {
                        c = rgb(0x7D76A8);
                    }
                    planks.setRGB(x, y, c);
                }
            }
            set(planks, (row * 7 + 3) % 16, row * 4 + 1, rgb(0x8A83B8));
        }
        save(planks, "block/spirit_planks.png");

        plants();
        portal();
        dreamCatcher();
        loomTextures();
        altarTextures();
    }

    private static void plants() throws Exception {
        BufferedImage bloom = image(16, 16);
        int stem = rgb(0x3FA088);
        int stemDark = rgb(0x2D7A6A);
        for (int y = 8; y < 16; y++) {
            set(bloom, 8, y, y % 3 == 0 ? stemDark : stem);
        }
        set(bloom, 7, 12, stem);
        set(bloom, 6, 11, stem);
        set(bloom, 5, 11, stemDark);
        set(bloom, 9, 13, stem);
        set(bloom, 10, 12, stem);
        set(bloom, 11, 12, stemDark);
        int[] petals = {0xFF8AD8, 0xFFB0E8, 0xFFD6F4};
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                double dx = x - 7.5;
                double dy = y - 5.0;
                double d = Math.hypot(dx, dy);
                if (d <= 4.4 && d >= 1.4) {
                    double angle = Math.atan2(dy, dx);
                    int petal = (int) Math.floor((angle + Math.PI) / (2 * Math.PI) * 5);
                    int c = petals[(petal + (d > 3.2 ? 1 : 0)) % 3];
                    if (d > 3.9) {
                        c = 0xE06AC0;
                    }
                    bloom.setRGB(x, y, rgb(c));
                } else if (d < 1.4) {
                    bloom.setRGB(x, y, rgb(0xFFE680));
                }
            }
        }
        save(bloom, "block/spirit_bloom.png");

        BufferedImage grass = image(16, 16);
        int[] blade = {0x2A7F7A, 0x3FA79A, 0x66CDB6, 0xA6F5DE};
        Random random = new Random(33);
        for (int x = 1; x < 15; x++) {
            if (random.nextInt(5) == 0) {
                continue;
            }
            int height = 5 + random.nextInt(8);
            int lean = random.nextInt(3) - 1;
            for (int i = 0; i < height; i++) {
                int y = 15 - i;
                int xx = x + (i > height / 2 ? lean : 0);
                double t = (double) i / height;
                set(grass, xx, y, ramp(blade, t * 0.95 + random.nextDouble() * 0.12));
            }
        }
        save(grass, "block/spirit_grass.png");
    }

    private static void portal() throws Exception {
        for (int pass = 0; pass < 2; pass++) {
            boolean active = pass == 1;
            BufferedImage top = noiseTexture(new int[] {0x2A2146, 0x32285A, 0x3A2F66}, 41, 3, 0.3);
            for (int i = 0; i < 16; i++) {
                int c = rgb(active ? 0x6A5CC0 : 0x4A3C80);
                set(top, i, 0, c);
                set(top, i, 15, c);
                set(top, 0, i, c);
                set(top, 15, i, c);
            }
            if (active) {
                ring(top, 7.5, 7.5, 4.0, 7.2, rgb(0x2F7F9A));
            }
            ring(top, 7.5, 7.5, 5.6, 6.6, rgb(active ? 0x7FFFE8 : 0x9D7CFF));
            star(top, 7.5, 7.5, 5.5, rgb(active ? 0xFFFFFF : 0xD8C6FF));
            for (int dy = -1; dy <= 0; dy++) {
                for (int dx = -1; dx <= 0; dx++) {
                    set(top, 8 + dx, 8 + dy, rgb(active ? 0xFFB8F0 : 0x7A5CD8));
                }
            }
            save(top, active ? "block/spirit_portal_top_active.png" : "block/spirit_portal_top.png");
        }
        BufferedImage side = noiseTexture(new int[] {0x2A2146, 0x32285A, 0x3A2F66}, 42, 3, 0.25);
        for (int x = 0; x < 16; x++) {
            set(side, x, 13, rgb(0x6A5CC0));
            set(side, x, 14, rgb(0x3A2F66));
            set(side, x, 15, rgb(0x241C3C));
        }
        for (int x = 2; x < 16; x += 5) {
            set(side, x, 14, rgb(0x9D7CFF));
        }
        save(side, "block/spirit_portal_side.png");
    }

    private static void dreamCatcher() throws Exception {
        BufferedImage image = image(16, 16);
        int hoopLight = rgb(0xD8B98A);
        int hoopDark = rgb(0x9C7A55);
        int web = rgb(0xEAF2FF);
        double cx = 7.5;
        double cy = 6.0;
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                double d = Math.hypot(x - cx, y - cy);
                if (d >= 4.3 && d <= 5.6) {
                    image.setRGB(x, y, ((x + y) % 3 == 0 || y > 7) ? hoopDark : hoopLight);
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            double a = i * Math.PI / 4;
            line(image, (int) Math.round(cx + Math.cos(a) * 2.4), (int) Math.round(cy + Math.sin(a) * 2.4), (int) Math.round(cx + Math.cos(a) * 4.2), (int) Math.round(cy + Math.sin(a) * 4.2), web);
        }
        ring(image, cx, cy, 1.2, 1.9, rgb(0xB98CFF));
        set(image, 7, 6, rgb(0xFF9BE0));
        set(image, 8, 6, rgb(0xFF9BE0));
        set(image, 7, 0, web);
        set(image, 8, 0, web);
        set(image, 7, 1, hoopDark);
        set(image, 8, 1, hoopDark);
        int[][] strings = {{4, 1}, {8, 2}, {11, 1}};
        int[] featherA = {0x7FE8FF, 0xF2A8E8, 0xB98CFF};
        int[] featherB = {0x4FB4D8, 0xD070C0, 0x8A5CD8};
        for (int i = 0; i < 3; i++) {
            int x = strings[i][0];
            int length = strings[i][1];
            for (int y = 11; y < 11 + length; y++) {
                set(image, x, y, web);
            }
            int end = 11 + length;
            set(image, x, end, rgb(0xFFFFFF));
            for (int f = 0; f < 3; f++) {
                int y = end + 1 + f;
                if (y > 15) {
                    break;
                }
                set(image, x, y, rgb(featherA[i]));
                set(image, x + 1, y, rgb(featherB[i]));
                if (f < 2) {
                    set(image, x - 1, y, rgb(featherB[i]));
                }
            }
        }
        save(image, "block/dream_catcher.png");
    }

    private static void loomTextures() throws Exception {
        BufferedImage wood = image(16, 16);
        int[] palette = {0x594C86, 0x6A5C9A, 0x7E70B0, 0x8E81C2};
        Random random = new Random(51);
        for (int y = 0; y < 16; y++) {
            double rowBase = (y % 4) * 0.12;
            for (int x = 0; x < 16; x++) {
                double v = rowBase + random.nextDouble() * 0.5;
                int c = ramp(palette, v);
                if (y % 4 == 3) {
                    c = rgb(0x463B6C);
                }
                wood.setRGB(x, y, c);
            }
        }
        save(wood, "block/dream_loom_wood.png");

        BufferedImage thread = image(16, 16);
        Random lines = new Random(52);
        for (int x = 1; x < 16; x += 2) {
            for (int y = 0; y < 16; y++) {
                if (lines.nextInt(14) == 0) {
                    continue;
                }
                thread.setRGB(x, y, rgb(x % 4 == 1 ? 0xEAF4FF : 0xB6E4FF));
            }
        }
        for (int y = 2; y < 16; y += 5) {
            for (int x = 0; x < 16; x++) {
                thread.setRGB(x, y, rgb(0xD8C6FF));
            }
        }
        save(thread, "block/dream_loom_thread.png");
    }

    private static void altarTextures() throws Exception {
        BufferedImage side = noiseTexture(new int[] {0x2A1F3F, 0x33264C, 0x3B2C58, 0x45346A}, 61, 3, 0.25);
        for (int x = 0; x < 16; x++) {
            set(side, x, 0, rgb(0x8A6FD0));
            set(side, x, 1, rgb(0x5A4590));
            set(side, x, 15, rgb(0x20172F));
        }
        for (int y = 2; y < 15; y += 4) {
            for (int x = 0; x < 16; x++) {
                set(side, x, y, rgb(0x241A38));
            }
        }
        save(side, "block/coven_altar_side.png");

        BufferedImage top = noiseTexture(new int[] {0x2F2347, 0x3B2C58, 0x45346A}, 62, 3, 0.25);
        for (int i = 0; i < 16; i++) {
            set(top, i, 0, rgb(0x8A6FD0));
            set(top, i, 15, rgb(0x8A6FD0));
            set(top, 0, i, rgb(0x8A6FD0));
            set(top, 15, i, rgb(0x8A6FD0));
        }
        ring(top, 7.5, 7.5, 5.7, 6.7, rgb(0xC8A0FF));
        star(top, 7.5, 7.5, 5.5, rgb(0xFFD98A));
        set(top, 7, 7, rgb(0xFFFFFF));
        set(top, 8, 7, rgb(0xFFFFFF));
        set(top, 7, 8, rgb(0xFFFFFF));
        set(top, 8, 8, rgb(0xFFFFFF));
        save(top, "block/coven_altar_top.png");
    }

    private static void items() throws Exception {
        dust();
        thread();
        bottle("leap_essence", 0x7FE8FF, 0x35A9D0, 0x1F6A99, new String[] {
                "..XX..",
                ".XXXX.",
                "X.XX.X",
                "..XX..",
                "..XX.."
        });
        bottle("climb_essence", 0xC9A6FF, 0x8A5CD8, 0x5A3A9A, new String[] {
                "X....X",
                "XXXXXX",
                "X....X",
                "XXXXXX",
                "X....X"
        });
        bottle("sight_essence", 0xFFE680, 0xE0A820, 0xA06A10, new String[] {
                "......",
                ".XXXX.",
                "XXOOXX",
                ".XXXX.",
                "......"
        });
    }

    private static void dust() throws Exception {
        BufferedImage image = image(16, 16);
        int outline = rgb(0x3A4A8A);
        int dark = rgb(0x6A86C8);
        int mid = rgb(0x8FB7E8);
        int light = rgb(0xB9E4F5);
        Random random = new Random(71);
        for (int y = 5; y <= 13; y++) {
            double half = 1.0 + (y - 5) * 0.85;
            for (int x = 0; x < 16; x++) {
                double dx = x - 7.5;
                if (Math.abs(dx) <= half) {
                    int c;
                    if (Math.abs(dx) >= half - 1.0 || y == 13) {
                        c = outline;
                    } else if (dx > 1.5) {
                        c = dark;
                    } else if (dx < -2.0) {
                        c = mid;
                    } else {
                        c = light;
                    }
                    if (c != outline && random.nextInt(5) == 0) {
                        c = rgb(0xFFFFFF);
                    }
                    image.setRGB(x, y, c);
                }
            }
        }
        int[][] sparkles = {{3, 3}, {12, 2}, {9, 14}, {2, 11}};
        for (int[] s : sparkles) {
            set(image, s[0], s[1], rgb(0xFFFFFF));
            set(image, s[0] - 1, s[1], rgb(0xB9E4F5));
            set(image, s[0] + 1, s[1], rgb(0xB9E4F5));
            set(image, s[0], s[1] - 1, rgb(0xB9E4F5));
            set(image, s[0], s[1] + 1, rgb(0xB9E4F5));
        }
        save(image, "item/spirit_dust.png");
    }

    private static void thread() throws Exception {
        BufferedImage image = image(16, 16);
        int dark = rgb(0x5A7AC0);
        int mid = rgb(0x8FB7E8);
        int light = rgb(0xCFE8FF);
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                double d = Math.hypot((x - 6.5) / 5.6, (y - 6.5) / 4.8);
                if (d <= 1.0 && d >= 0.45) {
                    int c = ((x + y) % 4 < 2) ? mid : light;
                    if (d > 0.85 || y > 9) {
                        c = dark;
                    }
                    image.setRGB(x, y, c);
                }
            }
        }
        line(image, 4, 5, 10, 9, rgb(0xDDEEFF));
        line(image, 5, 9, 9, 4, mid);
        int[][] tail = {{11, 10}, {12, 11}, {12, 12}, {11, 13}, {10, 13}, {10, 14}, {11, 15}};
        for (int[] t : tail) {
            set(image, t[0], t[1], light);
        }
        set(image, 12, 10, dark);
        set(image, 13, 12, dark);
        set(image, 6, 2, rgb(0xFFFFFF));
        set(image, 5, 2, light);
        set(image, 7, 2, light);
        set(image, 6, 1, light);
        set(image, 6, 3, light);
        save(image, "item/dream_thread.png");
    }

    private static void bottle(String name, int light, int mid, int dark, String[] symbol) throws Exception {
        BufferedImage image = image(16, 16);
        int outline = mix(dark, 0x000000, 0.45);
        int glass = rgb(0xDDEBFF);
        int cork = rgb(0xA67C52);
        int corkDark = rgb(0x7A583A);
        int[][] bounds = {
                {6, 9}, {6, 9}, {6, 9}, {7, 8}, {7, 8}, {5, 10}, {4, 11}, {3, 12}, {3, 12}, {3, 12}, {3, 12}, {3, 12}, {4, 11}, {5, 10}
        };
        for (int i = 0; i < bounds.length; i++) {
            int y = i + 1;
            int x0 = bounds[i][0];
            int x1 = bounds[i][1];
            for (int x = x0; x <= x1; x++) {
                boolean edge = x == x0 || x == x1 || i == bounds.length - 1;
                int c;
                if (i <= 2) {
                    c = i == 0 ? cork : corkDark;
                    if (x == x0 || x == x1) {
                        c = corkDark;
                    }
                } else if (edge) {
                    c = outline;
                } else if (i < 5) {
                    c = glass;
                } else if (i == 5) {
                    c = glass;
                } else {
                    double t = (double) (x - x0) / (x1 - x0);
                    c = t < 0.35 ? rgb(light) : t > 0.75 ? rgb(dark) : rgb(mid);
                }
                image.setRGB(x, y, c);
            }
        }
        set(image, 7, 3, rgb(0xFFFFFF));
        set(image, 5, 7, rgb(0xFFFFFF));
        set(image, 5, 8, rgb(0xFFFFFF));
        for (int sy = 0; sy < symbol.length; sy++) {
            for (int sx = 0; sx < symbol[sy].length(); sx++) {
                char c = symbol[sy].charAt(sx);
                if (c == 'X') {
                    set(image, 5 + sx, 7 + sy, rgb(0xFFFFFF));
                } else if (c == 'O') {
                    set(image, 5 + sx, 7 + sy, rgb(dark));
                }
            }
        }
        int[][] glints = {{2, 2}, {13, 4}, {13, 14}};
        for (int[] g : glints) {
            set(image, g[0], g[1], rgb(light));
        }
        save(image, "item/" + name + ".png");
    }

    private static void disc(BufferedImage image, double cx, double cy, double r, int color) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (Math.hypot(x - cx, y - cy) <= r) {
                    image.setRGB(x, y, color);
                }
            }
        }
    }

    private static void effects() throws Exception {
        BufferedImage nightmare = image(18, 18);
        disc(nightmare, 8.5, 8.5, 8.3, rgb(0x6A4AAA));
        disc(nightmare, 8.5, 8.5, 7.3, rgb(0x2A1A4A));
        int eye = rgb(0xFF7AD9);
        int[][] eyes = {{4, 6}, {5, 6}, {6, 7}, {7, 7}, {10, 7}, {11, 7}, {12, 6}, {13, 6}};
        for (int[] e : eyes) {
            set(nightmare, e[0], e[1], eye);
            set(nightmare, e[0], e[1] + 1, mix(0x2A1A4A, 0xFF7AD9, 0.5));
        }
        for (int x = 5; x <= 12; x++) {
            set(nightmare, x, 12 + (x % 2 == 0 ? 0 : 1), eye);
        }
        save(nightmare, "mob_effect/nightmare.png");

        BufferedImage dream = image(18, 18);
        disc(dream, 8.5, 8.5, 8.3, rgb(0x8FB7FF));
        disc(dream, 8.5, 8.5, 7.3, rgb(0x2E4A9A));
        disc(dream, 8.0, 8.5, 5.0, rgb(0xFFF0A0));
        disc(dream, 10.5, 7.5, 4.4, rgb(0x2E4A9A));
        int[][] stars = {{12, 4}, {13, 10}, {5, 3}, {11, 13}};
        for (int[] s : stars) {
            set(dream, s[0], s[1], rgb(0xFFFFFF));
        }
        save(dream, "mob_effect/restful_dream.png");

        BufferedImage bond = image(18, 18);
        disc(bond, 8.5, 8.5, 8.3, rgb(0xB05CFF));
        disc(bond, 8.5, 8.5, 7.3, rgb(0x2A1A4A));
        ring(bond, 8.5, 8.5, 5.6, 6.6, rgb(0xB05CFF));
        star(bond, 8.5, 8.5, 6.0, rgb(0xFFD0FF));
        save(bond, "mob_effect/coven_bond.png");
    }

    private interface Shader {

        int color(int face, int x, int y, int w, int h);
    }

    private static void paintBox(BufferedImage image, int u, int v, int w, int h, int d, Shader shader) {
        int[][] faces = {
                {d, 0, w, d},
                {d + w, 0, w, d},
                {0, d, d, h},
                {d, d, w, h},
                {d + w, d, d, h},
                {d + w + d, d, w, h}
        };
        for (int f = 0; f < faces.length; f++) {
            for (int y = 0; y < faces[f][3]; y++) {
                for (int x = 0; x < faces[f][2]; x++) {
                    int c = shader.color(f, x, y, faces[f][2], faces[f][3]);
                    if (c != 0) {
                        set(image, u + faces[f][0] + x, v + faces[f][1] + y, c);
                    }
                }
            }
        }
    }

    private static void nightmare() throws Exception {
        BufferedImage skin = image(64, 64);
        BufferedImage eyes = image(64, 64);
        Random random = new Random(81);
        int[] robe = {0x1C1233, 0x25193F, 0x2F2150, 0x3A2A63};
        Shader robeShader = (face, x, y, w, h) -> {
            double t = (double) y / Math.max(1, h - 1);
            double v = 0.2 + t * 0.35 + random.nextDouble() * 0.35;
            return ramp(robe, v);
        };
        paintBox(skin, 0, 0, 8, 8, 8, (face, x, y, w, h) -> {
            if (face == 3) {
                if ((y == 3 || y == 4) && (x >= 1 && x <= 6)) {
                    return rgb(0x0B0616);
                }
                if (y == 6 && x >= 1 && x <= 6) {
                    return x % 2 == 0 ? rgb(0xCFC0F0) : rgb(0x0B0616);
                }
                if (y <= 1) {
                    return rgb(0x2F2150);
                }
                return rgb(0x14102A);
            }
            return robeShader.color(face, x, y, w, h);
        });
        paintBox(skin, 32, 0, 2, 4, 2, (face, x, y, w, h) -> {
            double t = (double) y / 3.0;
            return mix(0xE8E2FF, 0x8A80B8, t);
        });
        paintBox(skin, 0, 16, 8, 12, 4, (face, x, y, w, h) -> {
            int c = robeShader.color(face, x, y, w, h);
            if (face == 3 && y >= 2 && y <= 9 && (x == 3 || x == 4)) {
                return mix(c, 0x6A4AAA, 0.45);
            }
            return c;
        });
        paintBox(skin, 24, 16, 3, 15, 3, (face, x, y, w, h) -> {
            double t = (double) y / Math.max(1, h - 1);
            if (face >= 2 && y >= h - 3) {
                return rgb(0xCFC0F0);
            }
            return ramp(robe, 0.15 + t * 0.3 + random.nextDouble() * 0.3);
        });
        paintBox(skin, 0, 32, 6, 5, 4, (face, x, y, w, h) -> {
            double t = (double) y / Math.max(1, h - 1);
            return ramp(robe, 0.3 + t * 0.4 + random.nextDouble() * 0.25);
        });
        paintBox(skin, 0, 42, 4, 5, 3, (face, x, y, w, h) -> {
            double t = (double) y / Math.max(1, h - 1);
            if (face >= 2 && y >= 3 && random.nextInt(3) == 0) {
                return 0;
            }
            return mix(0x3A2A63, 0x9D7CFF, t * 0.7 + random.nextDouble() * 0.2);
        });
        save(skin, "entity/nightmare.png");

        int glowA = rgb(0xFF7AD9);
        int glowB = rgb(0xFFD0F5);
        int[][] left = {{9, 11}, {10, 11}, {10, 12}, {11, 12}};
        int[][] right = {{14, 11}, {13, 11}, {13, 12}, {12, 12}};
        for (int[] p : left) {
            eyes.setRGB(p[0], p[1], p[1] == 11 ? glowB : glowA);
        }
        for (int[] p : right) {
            eyes.setRGB(p[0], p[1], p[1] == 11 ? glowB : glowA);
        }
        save(eyes, "entity/nightmare_eyes.png");
    }
}
