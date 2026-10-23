import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class GenCreatureMobs {

    enum Face { TOP, BOTTOM, RIGHT, FRONT, LEFT, BACK }

    interface Shader {
        int color(Face face, int x, int y, int w, int h, int ax, int ay);
    }

    static class Tex {
        final BufferedImage image;

        Tex(int w, int h) {
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        }

        void set(int x, int y, int c) {
            if (x >= 0 && y >= 0 && x < image.getWidth() && y < image.getHeight()) {
                image.setRGB(x, y, c);
            }
        }

        void box(int u, int v, int w, int h, int d, Shader s) {
            region(Face.TOP, u + d, v, w, d, s);
            region(Face.BOTTOM, u + d + w, v, w, d, s);
            region(Face.RIGHT, u, v + d, d, h, s);
            region(Face.FRONT, u + d, v + d, w, h, s);
            region(Face.LEFT, u + d + w, v + d, d, h, s);
            region(Face.BACK, u + 2 * d + w, v + d, w, h, s);
        }

        void region(Face f, int x0, int y0, int w, int h, Shader s) {
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    int c = s.color(f, x, y, w, h, x0 + x, y0 + y);
                    if ((c >>> 24) != 0) {
                        set(x0 + x, y0 + y, c);
                    }
                }
            }
        }
    }

    static int rgb(int hex) {
        return 0xFF000000 | hex;
    }

    static int alpha(int hex, int a) {
        return (a << 24) | (hex & 0xFFFFFF);
    }

    static int mix(int a, int b, double t) {
        int r = (int) Math.round(((a >> 16) & 255) * (1 - t) + ((b >> 16) & 255) * t);
        int g = (int) Math.round(((a >> 8) & 255) * (1 - t) + ((b >> 8) & 255) * t);
        int bl = (int) Math.round((a & 255) * (1 - t) + (b & 255) * t);
        return 0xFF000000 | (r << 16) | (g << 8) | bl;
    }

    static double noise(int x, int y, int seed) {
        int h = x * 374761393 + y * 668265263 + seed * 1274126177;
        h = (h ^ (h >>> 13)) * 1274126177;
        h ^= h >>> 16;
        return (h & 0xFFFF) / 65536.0;
    }

    static double clamp(double v) {
        return Math.max(0.0, Math.min(0.999, v));
    }

    static int pick(double n, int... colors) {
        return colors[(int) (clamp(n) * colors.length)];
    }

    static final int WOLF_DARK = rgb(0x2C2420);
    static final int WOLF_BASE = rgb(0x4A3D33);
    static final int WOLF_MID = rgb(0x5C4C3F);
    static final int WOLF_LIGHT = rgb(0x76655A);
    static final int WOLF_BELLY = rgb(0x8F7E70);
    static final int CLAW = rgb(0xE6DEC4);
    static final int CLAW_SHADE = rgb(0xB9AE92);
    static final int CLOTH = rgb(0x6B4A2B);
    static final int CLOTH_DARK = rgb(0x4A321B);

    static int fur(int ax, int ay, int seed, double bias) {
        double n = noise(ax, ay / 2, seed) + bias;
        return pick(n, WOLF_DARK, WOLF_BASE, WOLF_BASE, WOLF_MID, WOLF_MID, WOLF_LIGHT);
    }

    static Tex werewolf() {
        Tex t = new Tex(64, 64);
        t.box(0, 0, 8, 8, 8, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.FRONT) {
                if (y == 2 && (x == 1 || x == 2 || x == 5 || x == 6)) {
                    return WOLF_DARK;
                }
                if (y == 3 && (x == 0 || x == 7)) {
                    return WOLF_DARK;
                }
                if (y == 3 && (x == 1 || x == 2 || x == 5 || x == 6)) {
                    return rgb(0xFFD23A);
                }
                if (y >= 5 && x >= 2 && x <= 5) {
                    return mix(WOLF_MID, WOLF_BELLY, 0.5 + noise(ax, ay, 4) * 0.4);
                }
                return fur(ax, ay, 3, y <= 1 ? -0.2 : 0.0);
            }
            if (f == Face.TOP) {
                return fur(ax, ay, 5, -0.15);
            }
            if (f == Face.BOTTOM) {
                return mix(WOLF_BASE, WOLF_BELLY, 0.5);
            }
            return fur(ax, ay, 6, y <= 2 ? -0.1 : 0.05);
        });
        t.box(32, 0, 8, 8, 8, (f, x, y, w, h, ax, ay) -> {
            double n = noise(ax, ay, 21);
            if (f == Face.FRONT) {
                if (y <= 1 && n > 0.25) {
                    return fur(ax, ay, 22, 0.05);
                }
                if ((x == 0 || x == 7) && y <= 4 && n > 0.2) {
                    return fur(ax, ay, 23, 0.0);
                }
                return 0;
            }
            if (f == Face.BOTTOM) {
                return n > 0.45 ? fur(ax, ay, 24, -0.05) : 0;
            }
            if (f == Face.TOP) {
                return n > 0.12 ? fur(ax, ay, 25, 0.1) : 0;
            }
            if (f == Face.BACK) {
                return n > 0.18 ? fur(ax, ay, 26, 0.0) : 0;
            }
            return (y <= 2 || (n > 0.5 && y <= 5)) && n > 0.15 ? fur(ax, ay, 27, 0.05) : 0;
        });
        t.box(24, 48, 4, 3, 4, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.FRONT) {
                if (y == 0 && (x == 1 || x == 2)) {
                    return rgb(0x120E0E);
                }
                if (y == 2 && (x == 0 || x == 3)) {
                    return rgb(0xF2EDE0);
                }
                return fur(ax, ay, 31, 0.1);
            }
            if (f == Face.BOTTOM) {
                return (x == 0 || x == 3) ? rgb(0xF2EDE0) : rgb(0x8C1E22);
            }
            if (f == Face.TOP) {
                return fur(ax, ay, 32, -0.05);
            }
            return fur(ax, ay, 33, 0.12);
        });
        t.box(44, 48, 4, 1, 3, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.TOP) {
                return (x == 0 || x == 3) ? rgb(0xF2EDE0) : rgb(0x8C1E22);
            }
            return fur(ax, ay, 35, 0.0);
        });
        t.box(24, 56, 2, 3, 1, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.FRONT) {
                return x == 0 || y == 0 ? WOLF_DARK : rgb(0xA06A62);
            }
            return fur(ax, ay, 36, -0.1);
        });
        t.box(0, 48, 3, 3, 8, (f, x, y, w, h, ax, ay) -> {
            double pos;
            if (f == Face.TOP || f == Face.BOTTOM) {
                pos = y / 7.0;
            } else if (f == Face.BACK) {
                pos = 1.0;
            } else {
                pos = x / 7.0;
            }
            if (pos > 0.78) {
                return mix(WOLF_DARK, WOLF_BASE, noise(ax, ay, 37));
            }
            return fur(ax, ay, 38, 0.0);
        });
        t.box(16, 16, 8, 12, 4, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.FRONT) {
                if (y >= 9) {
                    return clothTatter(x, y, ax, ay, 40);
                }
                if (x >= 2 && x <= 5 && y <= 6) {
                    return mix(WOLF_MID, WOLF_BELLY, 0.35 + noise(ax, ay, 41) * 0.5);
                }
                return fur(ax, ay, 42, 0.0);
            }
            if (f == Face.BACK) {
                if (y >= 9) {
                    return clothTatter(x, y, ax, ay, 43);
                }
                return fur(ax, ay, 44, -0.1);
            }
            if (f == Face.TOP || f == Face.BOTTOM) {
                return fur(ax, ay, 45, -0.1);
            }
            return y >= 9 ? clothTatter(x, y, ax, ay, 46) : fur(ax, ay, 47, 0.0);
        });
        t.box(40, 16, 4, 12, 4, (f, x, y, w, h, ax, ay) -> arm(f, x, y, ax, ay, 50));
        t.box(0, 16, 4, 12, 4, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.BOTTOM) {
                return rgb(0x1C1512);
            }
            if (y >= 10) {
                if (f == Face.FRONT && y == 11 && (x == 0 || x == 3)) {
                    return CLAW;
                }
                return mix(WOLF_DARK, WOLF_BASE, noise(ax, ay, 51));
            }
            if (y >= 9) {
                return clothTatter(x, y, ax, ay, 52);
            }
            return fur(ax, ay, 53, 0.0);
        });
        t.box(16, 32, 8, 12, 4, (f, x, y, w, h, ax, ay) -> shag(ax, ay, 60, 0.5, f == Face.FRONT && x >= 2 && x <= 5 && y <= 6));
        t.box(40, 32, 4, 12, 4, (f, x, y, w, h, ax, ay) -> y >= 9 ? 0 : shag(ax, ay, 61, 0.5, false));
        t.box(0, 32, 4, 12, 4, (f, x, y, w, h, ax, ay) -> y >= 9 ? 0 : shag(ax, ay, 62, 0.55, false));
        return t;
    }

    static int clothTatter(int x, int y, int ax, int ay, int seed) {
        double jag = noise(ax, 0, seed) * 2.0;
        if (y >= 11 && y - 11 > jag) {
            return 0;
        }
        return mix(CLOTH, CLOTH_DARK, noise(ax, ay, seed + 1) * 0.8);
    }

    static int arm(Face f, int x, int y, int ax, int ay, int seed) {
        if (f == Face.BOTTOM) {
            return rgb(0x1C1512);
        }
        if (y == 11 && f != Face.TOP) {
            return (x % 2 == 0) ? CLAW : CLAW_SHADE;
        }
        if (y == 10) {
            return mix(WOLF_DARK, WOLF_BASE, noise(ax, ay, seed));
        }
        return fur(ax, ay, seed + 1, f == Face.TOP ? -0.15 : 0.0);
    }

    static int shag(int ax, int ay, int seed, double density, boolean belly) {
        double n = noise(ax, ay, seed);
        if (n > density) {
            return 0;
        }
        return belly ? WOLF_BELLY : fur(ax, ay, seed + 1, 0.12);
    }

    static Tex werewolfGlow() {
        Tex t = new Tex(64, 64);
        int eye = rgb(0xFFE75A);
        t.set(9, 11, eye);
        t.set(10, 11, rgb(0xFFC21E));
        t.set(13, 11, rgb(0xFFC21E));
        t.set(14, 11, eye);
        return t;
    }

    static final int SKIN = rgb(0xDCD2D2);
    static final int SKIN_SHADE = rgb(0xBAAEB2);
    static final int SKIN_DARK = rgb(0x9A8D94);
    static final int HAIR = rgb(0x15121A);
    static final int HAIR_LIGHT = rgb(0x2A2433);
    static final int COAT = rgb(0x1D1822);
    static final int COAT_MID = rgb(0x2B2433);
    static final int COAT_LIGHT = rgb(0x3A3144);
    static final int LINING = rgb(0x7E1226);
    static final int LINING_DARK = rgb(0x520C19);
    static final int SHIRT = rgb(0xE8E2E6);
    static final int SHIRT_SHADE = rgb(0xC4BCC4);
    static final int RUBY = rgb(0xB3122D);
    static final int GOLD = rgb(0xC9A24A);

    static Tex vampire() {
        Tex t = new Tex(64, 64);
        t.box(0, 0, 8, 8, 8, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.FRONT) {
                if (y == 2 && (x == 1 || x == 2 || x == 5 || x == 6)) {
                    return HAIR;
                }
                if (y == 3 && (x == 1 || x == 6)) {
                    return rgb(0xFF2A3C);
                }
                if (y == 3 && (x == 2 || x == 5)) {
                    return rgb(0x7A0E1C);
                }
                if (y == 4 && (x == 3 || x == 4)) {
                    return SKIN_DARK;
                }
                if (y == 5 && (x == 3 || x == 4)) {
                    return SKIN_SHADE;
                }
                if (y == 6 && x >= 2 && x <= 5) {
                    return rgb(0x6A1E28);
                }
                if (y == 7 && (x == 2 || x == 5)) {
                    return rgb(0xF4F0F0);
                }
                if (y <= 1) {
                    return HAIR;
                }
                if (y == 4 && (x == 0 || x == 7)) {
                    return SKIN_SHADE;
                }
                return mix(SKIN, SKIN_SHADE, noise(ax, ay, 71) * 0.6);
            }
            if (f == Face.TOP || f == Face.BACK) {
                return mix(HAIR, HAIR_LIGHT, noise(ax, ay, 72));
            }
            if (f == Face.BOTTOM) {
                return SKIN_DARK;
            }
            if (y <= 2) {
                return mix(HAIR, HAIR_LIGHT, noise(ax, ay, 73));
            }
            return mix(SKIN, SKIN_SHADE, noise(ax, ay, 74) * 0.6);
        });
        t.box(32, 0, 8, 8, 8, (f, x, y, w, h, ax, ay) -> {
            double n = noise(ax, ay, 75);
            if (f == Face.FRONT) {
                if (y <= 1) {
                    return mix(HAIR, HAIR_LIGHT, n);
                }
                if (y == 2 && (x <= 1 || x >= 6 || x == 3 || x == 4)) {
                    return mix(HAIR, HAIR_LIGHT, n);
                }
                if ((x == 0 || x == 7) && y <= 4) {
                    return HAIR;
                }
                return 0;
            }
            if (f == Face.BOTTOM) {
                return 0;
            }
            if (f == Face.TOP || f == Face.BACK) {
                return mix(HAIR, HAIR_LIGHT, n);
            }
            return y <= 2 ? mix(HAIR, HAIR_LIGHT, n) : 0;
        });
        t.box(16, 16, 8, 12, 4, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.FRONT) {
                if (y == 9) {
                    return (x == 3 || x == 4) ? GOLD : rgb(0x0E0B12);
                }
                if (y >= 10) {
                    return mix(COAT, COAT_MID, noise(ax, ay, 76));
                }
                if (x == 1 || x == 6) {
                    return LINING;
                }
                if (x <= 0 || x >= 7) {
                    return COAT_MID;
                }
                if (y == 0 && (x == 3 || x == 4)) {
                    return RUBY;
                }
                if (y == 1 && (x == 3 || x == 4)) {
                    return rgb(0x8A0F24);
                }
                return (x == 2 || x == 5) ? SHIRT_SHADE : SHIRT;
            }
            if (f == Face.BACK) {
                if (y == 9) {
                    return rgb(0x0E0B12);
                }
                return (x == 3 || x == 4) ? COAT_LIGHT : mix(COAT, COAT_MID, noise(ax, ay, 77));
            }
            if (f == Face.LEFT || f == Face.RIGHT) {
                if (y == 9) {
                    return rgb(0x0E0B12);
                }
                return x == 0 || x == w - 1 ? LINING_DARK : mix(COAT, COAT_MID, noise(ax, ay, 78));
            }
            return COAT;
        });
        t.box(40, 16, 4, 12, 4, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.BOTTOM) {
                return SKIN_DARK;
            }
            if (y >= 10) {
                return mix(SKIN, SKIN_SHADE, noise(ax, ay, 79) * 0.7);
            }
            if (y == 9) {
                return SHIRT_SHADE;
            }
            if (y == 8) {
                return LINING;
            }
            return mix(COAT, COAT_MID, noise(ax, ay, 80));
        });
        t.box(0, 16, 4, 12, 4, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.BOTTOM) {
                return rgb(0x0A080D);
            }
            if (y >= 10) {
                return y == 10 ? rgb(0x2E2736) : mix(rgb(0x120F15), rgb(0x1E1A24), noise(ax, ay, 81));
            }
            return mix(COAT, COAT_MID, noise(ax, ay, 82));
        });
        t.box(0, 32, 10, 16, 1, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.FRONT) {
                return mix(LINING, LINING_DARK, (x % 3 == 0 ? 0.55 : 0.1) + noise(ax, ay, 83) * 0.2);
            }
            if (f == Face.BACK) {
                return mix(COAT, COAT_LIGHT, (x % 3 == 0 ? 0.55 : 0.05) + noise(ax, ay, 84) * 0.15);
            }
            return LINING_DARK;
        });
        t.box(24, 32, 8, 4, 1, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.FRONT) {
                return mix(LINING, LINING_DARK, y / 3.0 * 0.6);
            }
            if (f == Face.BACK) {
                return mix(COAT, COAT_LIGHT, noise(ax, ay, 85) * 0.5);
            }
            return LINING_DARK;
        });
        return t;
    }

    static Tex vampireGlow() {
        Tex t = new Tex(64, 64);
        t.set(9, 11, rgb(0xFF3347));
        t.set(14, 11, rgb(0xFF3347));
        t.set(23, 20, rgb(0xFF2A44));
        t.set(24, 20, rgb(0xE01B35));
        t.set(23, 21, rgb(0xFF2A44));
        t.set(24, 21, rgb(0xE01B35));
        return t;
    }

    static final int ZOMBIE_SKIN = rgb(0x6D8A78);
    static final int ZOMBIE_SHADE = rgb(0x587262);
    static final int ZOMBIE_DARK = rgb(0x41564A);
    static final int ZOMBIE_ROT = rgb(0x7B6F58);
    static final int BONE = rgb(0xD8D0BC);
    static final int BONE_SHADE = rgb(0xB0A88F);
    static final int BONE_DARK = rgb(0x7C7560);
    static final int CAVITY = rgb(0x16130F);
    static final int RAG = rgb(0x3C4F6B);
    static final int RAG_DARK = rgb(0x2A3850);
    static final int PANTS = rgb(0x2E3340);
    static final int PANTS_DARK = rgb(0x20242F);

    static int zombieSkin(int ax, int ay, int seed) {
        double n = noise(ax, ay, seed);
        if (n > 0.93) {
            return BONE;
        }
        if (n > 0.84) {
            return ZOMBIE_ROT;
        }
        return pick(n * 1.1, ZOMBIE_DARK, ZOMBIE_SHADE, ZOMBIE_SHADE, ZOMBIE_SKIN, ZOMBIE_SKIN);
    }

    static Tex zombieServant() {
        Tex t = new Tex(64, 64);
        t.box(0, 0, 8, 8, 8, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.FRONT) {
                if (y == 2 && (x == 1 || x == 2 || x == 5 || x == 6)) {
                    return ZOMBIE_DARK;
                }
                if (y == 3 && (x == 1 || x == 2 || x == 5 || x == 6)) {
                    return rgb(0x9AF6FF);
                }
                if (y == 4 && (x == 3 || x == 4)) {
                    return ZOMBIE_DARK;
                }
                if (y == 6 && x >= 2 && x <= 5) {
                    return rgb(0x2A1A1A);
                }
                if (y == 7 && (x == 2 || x == 4)) {
                    return BONE;
                }
            }
            if (f == Face.TOP) {
                return mix(ZOMBIE_DARK, rgb(0x2C3A32), noise(ax, ay, 91));
            }
            return zombieSkin(ax, ay, 92);
        });
        t.box(32, 0, 8, 8, 8, (f, x, y, w, h, ax, ay) -> {
            double n = noise(ax, ay, 93);
            if (f == Face.TOP || ((f == Face.BACK || f == Face.LEFT || f == Face.RIGHT) && y <= 1) || (f == Face.FRONT && y == 0)) {
                return n > 0.3 ? mix(rgb(0x2C3A32), rgb(0x3E4E44), n) : 0;
            }
            return 0;
        });
        t.box(16, 16, 8, 12, 4, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.BOTTOM) {
                return PANTS_DARK;
            }
            if (y >= 9) {
                return pantsTatter(x, y, ax, ay, 94);
            }
            double n = noise(ax, ay, 95);
            if (f == Face.FRONT && y >= 3 && y <= 5 && x >= 3 && x <= 5 && n > 0.45) {
                return zombieSkin(ax, ay, 96);
            }
            if (n > 0.9) {
                return ZOMBIE_SHADE;
            }
            return mix(RAG, RAG_DARK, noise(ax, ay, 97) * 0.9);
        });
        t.box(40, 16, 4, 12, 4, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.BOTTOM) {
                return ZOMBIE_DARK;
            }
            if (y >= 7) {
                return zombieSkin(ax, ay, 98);
            }
            return mix(RAG, RAG_DARK, noise(ax, ay, 99) * 0.9);
        });
        t.box(0, 16, 4, 12, 4, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.BOTTOM) {
                return rgb(0x181B24);
            }
            if (y >= 10) {
                return mix(rgb(0x3A3028), rgb(0x2A221C), noise(ax, ay, 100));
            }
            return pantsTatter(x, y, ax, ay, 101);
        });
        return t;
    }

    static int pantsTatter(int x, int y, int ax, int ay, int seed) {
        return mix(PANTS, PANTS_DARK, noise(ax, ay, seed) * 0.9);
    }

    static Tex zombieGlow() {
        Tex t = new Tex(64, 64);
        int eye = rgb(0x9AF6FF);
        t.set(9, 11, eye);
        t.set(10, 11, eye);
        t.set(13, 11, eye);
        t.set(14, 11, eye);
        return t;
    }

    static Tex skeletonServant() {
        Tex t = new Tex(64, 64);
        t.box(0, 0, 8, 8, 8, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.FRONT) {
                if (y >= 2 && y <= 4 && (x == 1 || x == 2 || x == 5 || x == 6)) {
                    return CAVITY;
                }
                if (y == 5 && (x == 3 || x == 4)) {
                    return CAVITY;
                }
                if (y == 6 && x >= 1 && x <= 6) {
                    return (x % 2 == 1) ? CAVITY : BONE_DARK;
                }
                if (y == 7 && x >= 1 && x <= 6) {
                    return (x % 2 == 0) ? BONE : BONE_SHADE;
                }
            }
            double n = noise(ax, ay, 110);
            return pick(n, BONE_SHADE, BONE, BONE, BONE, BONE_SHADE);
        });
        t.box(16, 16, 8, 12, 4, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.FRONT || f == Face.BACK) {
                if (x == 3 || x == 4) {
                    return y % 2 == 0 ? BONE : BONE_SHADE;
                }
                if (y <= 7 && y % 2 == 1) {
                    return x >= 1 && x <= 6 ? BONE_SHADE : 0xFF000000 | 0x16130F;
                }
                if (y >= 8) {
                    return y == 8 ? BONE : (x >= 2 && x <= 5 ? BONE_DARK : CAVITY);
                }
                return CAVITY;
            }
            return mix(CAVITY, BONE_DARK, noise(ax, ay, 111) * 0.6);
        });
        t.box(40, 16, 2, 12, 2, (f, x, y, w, h, ax, ay) -> y == 5 || y == 6 ? BONE_DARK : mix(BONE, BONE_SHADE, noise(ax, ay, 112)));
        t.box(0, 16, 2, 12, 2, (f, x, y, w, h, ax, ay) -> y == 5 || y == 6 ? BONE_DARK : y >= 10 ? BONE_SHADE : mix(BONE, BONE_SHADE, noise(ax, ay, 113)));
        return t;
    }

    static Tex skeletonGlow() {
        Tex t = new Tex(64, 64);
        int eye = rgb(0x9AF6FF);
        t.set(9, 10, rgb(0x4ECBE0));
        t.set(9, 11, eye);
        t.set(10, 11, eye);
        t.set(10, 10, rgb(0x4ECBE0));
        t.set(13, 10, rgb(0x4ECBE0));
        t.set(13, 11, eye);
        t.set(14, 11, eye);
        t.set(14, 10, rgb(0x4ECBE0));
        return t;
    }

    static final int GHOST_HEX = 0xC9E3F5;
    static final int GHOST_SHADE = 0x8FB6D6;
    static final int GHOST_DARK = 0x1A2A44;

    static int ghostSkin(int ax, int ay, int seed, int a, boolean edge) {
        double n = noise(ax, ay, seed);
        int base = n > 0.8 ? 0xE6F4FF : n < 0.25 ? GHOST_SHADE : GHOST_HEX;
        int out = edge ? GHOST_SHADE : base;
        return alpha(out, Math.max(30, Math.min(255, a + (int) ((n - 0.5) * 30))));
    }

    static Tex ghost() {
        Tex t = new Tex(64, 64);
        t.box(0, 0, 8, 8, 8, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.FRONT) {
                if (y >= 2 && y <= 4 && (x == 1 || x == 2 || x == 5 || x == 6)) {
                    return alpha(GHOST_DARK, 240);
                }
                if (y >= 5 && y <= 7 && (x == 3 || x == 4)) {
                    return alpha(GHOST_DARK, y == 5 ? 220 : 245);
                }
            }
            return ghostSkin(ax, ay, 120, f == Face.BOTTOM ? 120 : 200, false);
        });
        t.box(16, 16, 8, 6, 4, (f, x, y, w, h, ax, ay) -> ghostSkin(ax, ay, 121, 180 - y * 5, y == 5));
        t.box(16, 28, 6, 6, 3, (f, x, y, w, h, ax, ay) -> ghostSkin(ax, ay, 122, 150 - y * 6, y == 5));
        t.box(16, 38, 4, 6, 2, (f, x, y, w, h, ax, ay) -> ghostSkin(ax, ay, 123, 110 - y * 8, y == 5));
        t.box(16, 48, 2, 5, 1, (f, x, y, w, h, ax, ay) -> ghostSkin(ax, ay, 124, 70 - y * 9, false));
        t.box(40, 16, 2, 10, 2, (f, x, y, w, h, ax, ay) -> y >= 8 ? alpha(GHOST_SHADE, 200) : ghostSkin(ax, ay, 125, 170 - y * 6, false));
        return t;
    }

    static Tex ghostGlow() {
        Tex t = new Tex(64, 64);
        int eye = rgb(0x7CFFFF);
        t.set(9, 11, rgb(0x3ABFD6));
        t.set(10, 11, eye);
        t.set(13, 11, eye);
        t.set(14, 11, rgb(0x3ABFD6));
        t.set(10, 12, rgb(0x2AA3BC));
        t.set(13, 12, rgb(0x2AA3BC));
        return t;
    }

    static final int IMP_BASE = rgb(0xB5361F);
    static final int IMP_SHADE = rgb(0x8A2415);
    static final int IMP_DARK = rgb(0x5E160D);
    static final int IMP_LIGHT = rgb(0xD9633A);
    static final int IMP_BELLY = rgb(0xE08A55);

    static int impSkin(int ax, int ay, int seed, double bias) {
        double n = noise(ax, ay, seed) + bias;
        return pick(n, IMP_DARK, IMP_SHADE, IMP_BASE, IMP_BASE, IMP_BASE, IMP_LIGHT);
    }

    static Tex imp() {
        Tex t = new Tex(64, 64);
        t.box(0, 0, 6, 6, 6, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.FRONT) {
                if (y == 1 && (x == 0 || x == 1 || x == 4 || x == 5)) {
                    return IMP_DARK;
                }
                if (y == 2 && (x == 1 || x == 4)) {
                    return rgb(0xFFE03A);
                }
                if (y == 3 && (x == 2 || x == 3)) {
                    return IMP_SHADE;
                }
                if (y == 4 && x >= 1 && x <= 4) {
                    return x == 1 || x == 4 ? rgb(0xF2E6C8) : rgb(0x2B0A08);
                }
            }
            return impSkin(ax, ay, 130, f == Face.TOP ? -0.1 : 0.0);
        });
        t.box(24, 0, 1, 3, 1, (f, x, y, w, h, ax, ay) -> mix(rgb(0x2A1A18), rgb(0xA08A70), y == 0 ? 0.8 : 0.1 * (2 - y)));
        t.box(32, 0, 3, 2, 1, (f, x, y, w, h, ax, ay) -> f == Face.FRONT ? mix(IMP_BASE, rgb(0x3A0E0A), 0.5) : impSkin(ax, ay, 131, 0.0));
        t.box(0, 16, 5, 6, 3, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.FRONT) {
                if (x >= 1 && x <= 3 && y <= 4) {
                    return mix(IMP_LIGHT, IMP_BELLY, noise(ax, ay, 132));
                }
                return impSkin(ax, ay, 133, 0.0);
            }
            return impSkin(ax, ay, 134, f == Face.BACK ? -0.15 : 0.0);
        });
        t.box(24, 16, 2, 6, 2, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.BOTTOM) {
                return rgb(0x1A0A08);
            }
            if (y == 5) {
                return rgb(0x1A0A08);
            }
            return impSkin(ax, ay, 135, 0.0);
        });
        t.box(40, 16, 2, 6, 2, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.BOTTOM) {
                return rgb(0x120706);
            }
            if (y >= 5) {
                return rgb(0x2A1410);
            }
            return impSkin(ax, ay, 136, -0.05);
        });
        t.box(0, 32, 9, 10, 1, (f, x, y, w, h, ax, ay) -> {
            if (f != Face.FRONT && f != Face.BACK) {
                return IMP_DARK;
            }
            int depth = (x % 3 == 1) ? 2 : (x % 3 == 2 ? 1 : 0);
            if (y >= h - 1 - depth + (f == Face.FRONT ? 0 : 0) && y > 6) {
                return 0;
            }
            if (y <= 1 || x == 0 || x == 3 || x == 6 || x == 8) {
                return mix(IMP_DARK, rgb(0x2A0A07), 0.4);
            }
            return mix(rgb(0x6B1510), rgb(0x8A2A1C), noise(ax, ay, 137));
        });
        t.box(0, 44, 1, 1, 6, (f, x, y, w, h, ax, ay) -> impSkin(ax, ay, 138, -0.1));
        t.box(16, 44, 3, 3, 1, (f, x, y, w, h, ax, ay) -> mix(IMP_SHADE, IMP_DARK, 0.3 + noise(ax, ay, 139) * 0.4));
        return t;
    }

    static Tex impGlow() {
        Tex t = new Tex(64, 64);
        t.set(7, 8, rgb(0xFFE03A));
        t.set(10, 8, rgb(0xFFE03A));
        return t;
    }

    static final int BARK_DARK = rgb(0x3B2A1B);
    static final int BARK = rgb(0x5A4128);
    static final int BARK_LIGHT = rgb(0x7A5A38);
    static final int BARK_HI = rgb(0x93713F);
    static final int MOSS = rgb(0x4E7A2E);
    static final int MOSS_LIGHT = rgb(0x6E9B3E);

    static int bark(int ax, int ay, int seed) {
        double col = noise(ax, 0, seed);
        double n = noise(ax, ay / 3, seed + 1) * 0.6 + col * 0.4;
        int c = pick(n, BARK_DARK, BARK, BARK, BARK_LIGHT, BARK_LIGHT, BARK_HI);
        if (noise(ax / 2, ay / 2, seed + 2) > 0.88) {
            return c == BARK_DARK ? MOSS : mix(c, MOSS, 0.7);
        }
        return c;
    }

    static int leaves(int ax, int ay, int seed) {
        double n = noise(ax, ay, seed);
        if (n < 0.1) {
            return 0;
        }
        return pick(noise(ax / 2, ay, seed + 1), rgb(0x2D5A22), rgb(0x3F7A2D), rgb(0x3F7A2D), rgb(0x5FA03C), rgb(0x86C24F));
    }

    static Tex ent() {
        Tex t = new Tex(128, 64);
        t.box(0, 0, 10, 18, 10, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.FRONT) {
                if (y == 3 && (x == 2 || x == 3 || x == 6 || x == 7)) {
                    return rgb(0xFFB02E);
                }
                if (y == 4 && (x == 2 || x == 3 || x == 6 || x == 7)) {
                    return rgb(0x1A120A);
                }
                if (y == 2 && (x == 1 || x == 2 || x == 7 || x == 8)) {
                    return BARK_DARK;
                }
                if (y == 3 && (x == 1 || x == 8)) {
                    return BARK_DARK;
                }
                if (y >= 8 && y <= 10 && x >= 3 && x <= 6) {
                    return (y == 10 && (x == 3 || x == 5)) ? BARK_LIGHT : rgb(0x1A120A);
                }
                if (y == 7 && x >= 2 && x <= 7) {
                    return BARK_DARK;
                }
                if (y >= 14 && noise(ax, ay, 150) > 0.4) {
                    return MOSS;
                }
            }
            if (f == Face.TOP) {
                return mix(BARK_DARK, BARK, (x + y) % 2 == 0 ? 0.3 : 0.6);
            }
            if (f == Face.BOTTOM) {
                return BARK_DARK;
            }
            return bark(ax, ay, 151);
        });
        t.box(0, 28, 12, 10, 12, (f, x, y, w, h, ax, ay) -> leaves(ax, ay, 152));
        t.box(48, 0, 4, 16, 4, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.TOP) {
                return BARK_DARK;
            }
            return y >= 13 ? mix(BARK_DARK, BARK, noise(ax, ay, 153)) : bark(ax, ay, 154);
        });
        t.box(88, 0, 6, 4, 6, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.TOP) {
                return BARK;
            }
            if (f == Face.BOTTOM) {
                return BARK_DARK;
            }
            return (x % 2 == 1) ? mix(BARK_DARK, BARK, 0.4) : bark(ax, ay, 155);
        });
        t.box(64, 0, 5, 10, 5, (f, x, y, w, h, ax, ay) -> {
            if (f == Face.BOTTOM) {
                return BARK_DARK;
            }
            if (y >= 8) {
                return mix(BARK_DARK, BARK, noise(ax, ay, 156) * 0.7);
            }
            return bark(ax, ay, 157);
        });
        return t;
    }

    static Tex entGlow() {
        Tex t = new Tex(128, 64);
        int eye = rgb(0xFFB02E);
        t.set(12, 13, rgb(0xFFD36A));
        t.set(13, 13, eye);
        t.set(16, 13, eye);
        t.set(17, 13, rgb(0xFFD36A));
        t.set(12, 14, rgb(0xFF8A1E));
        t.set(13, 14, rgb(0xFF8A1E));
        t.set(16, 14, rgb(0xFF8A1E));
        t.set(17, 14, rgb(0xFF8A1E));
        return t;
    }

    static void save(Tex t, File dir, String name, File preview) throws Exception {
        ImageIO.write(t.image, "png", new File(dir, name + ".png"));
        if (preview != null) {
            int scale = 6;
            int w = t.image.getWidth();
            int h = t.image.getHeight();
            BufferedImage big = new BufferedImage(w * scale, h * scale, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = big.createGraphics();
            for (int y = 0; y < h * scale; y += scale) {
                for (int x = 0; x < w * scale; x += scale) {
                    g.setColor(((x / scale + y / scale) % 2 == 0) ? new Color(0x55555F) : new Color(0x62626C));
                    g.fillRect(x, y, scale, scale);
                }
            }
            g.drawImage(t.image, 0, 0, w * scale, h * scale, null);
            g.dispose();
            ImageIO.write(big, "png", new File(preview, name + "_x" + scale + ".png"));
        }
    }

    public static void main(String[] args) throws Exception {
        File out = new File(args[0]);
        out.mkdirs();
        File preview = args.length > 1 ? new File(args[1]) : null;
        if (preview != null) {
            preview.mkdirs();
        }
        save(werewolf(), out, "werewolf", preview);
        save(werewolfGlow(), out, "werewolf_glow", null);
        save(vampire(), out, "vampire", preview);
        save(vampireGlow(), out, "vampire_glow", null);
        save(zombieServant(), out, "zombie_servant", preview);
        save(zombieGlow(), out, "zombie_servant_glow", null);
        save(skeletonServant(), out, "skeleton_servant", preview);
        save(skeletonGlow(), out, "skeleton_servant_glow", null);
        save(ghost(), out, "ghost", preview);
        save(ghostGlow(), out, "ghost_glow", null);
        save(imp(), out, "imp", preview);
        save(impGlow(), out, "imp_glow", null);
        save(ent(), out, "ent", preview);
        save(entGlow(), out, "ent_glow", null);
    }
}
