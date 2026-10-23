import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class GenCreatureItems {

    static class Canvas {
        final BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        void set(int x, int y, int c) {
            if (x >= 0 && y >= 0 && x < 16 && y < 16) {
                image.setRGB(x, y, c);
            }
        }

        int get(int x, int y) {
            if (x < 0 || y < 0 || x >= 16 || y >= 16) {
                return 0;
            }
            return image.getRGB(x, y);
        }

        void rect(int x, int y, int w, int h, int c) {
            for (int j = 0; j < h; j++) {
                for (int i = 0; i < w; i++) {
                    set(x + i, y + j, c);
                }
            }
        }

        void stamp(int x0, int y0, String[] art, Map<Character, Integer> palette) {
            for (int j = 0; j < art.length; j++) {
                for (int i = 0; i < art[j].length(); i++) {
                    Integer c = palette.get(art[j].charAt(i));
                    if (c != null) {
                        set(x0 + i, y0 + j, c);
                    }
                }
            }
        }

        void outline(int color) {
            boolean[][] mark = new boolean[16][16];
            for (int y = 0; y < 16; y++) {
                for (int x = 0; x < 16; x++) {
                    if ((get(x, y) >>> 24) == 0) {
                        boolean near = (get(x - 1, y) >>> 24) != 0 || (get(x + 1, y) >>> 24) != 0 || (get(x, y - 1) >>> 24) != 0 || (get(x, y + 1) >>> 24) != 0;
                        mark[y][x] = near;
                    }
                }
            }
            for (int y = 0; y < 16; y++) {
                for (int x = 0; x < 16; x++) {
                    if (mark[y][x]) {
                        set(x, y, color);
                    }
                }
            }
        }

        void line(int x0, int y0, int x1, int y1, int c) {
            int dx = Math.abs(x1 - x0);
            int dy = -Math.abs(y1 - y0);
            int sx = x0 < x1 ? 1 : -1;
            int sy = y0 < y1 ? 1 : -1;
            int err = dx + dy;
            while (true) {
                set(x0, y0, c);
                if (x0 == x1 && y0 == y1) {
                    break;
                }
                int e2 = 2 * err;
                if (e2 >= dy) {
                    err += dy;
                    x0 += sx;
                }
                if (e2 <= dx) {
                    err += dx;
                    y0 += sy;
                }
            }
        }
    }

    static int rgb(int hex) {
        return 0xFF000000 | hex;
    }

    static Map<Character, Integer> palette(Object... entries) {
        Map<Character, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < entries.length; i += 2) {
            map.put((Character) entries[i], rgb((Integer) entries[i + 1]));
        }
        return map;
    }

    static Canvas ascii(String[] art, Map<Character, Integer> palette) {
        if (art.length != 16) {
            throw new IllegalStateException("rows " + art.length);
        }
        for (int i = 0; i < art.length; i++) {
            if (art[i].length() != 16) {
                throw new IllegalStateException("row " + i + " has length " + art[i].length() + ": " + art[i]);
            }
        }
        Canvas c = new Canvas();
        c.stamp(0, 0, art, palette);
        return c;
    }

    static Canvas silverIngot() {
        Canvas c = new Canvas();
        int white = rgb(0xF4F8FC);
        int light = rgb(0xD2DBE6);
        int mid = rgb(0xA3AFBF);
        int dark = rgb(0x6F7A8A);
        int shadow = rgb(0x4E5866);
        for (int y = 5; y <= 8; y++) {
            int x0 = 3 + (8 - y);
            for (int x = x0; x <= x0 + 8; x++) {
                boolean edge = y == 5 || x == x0;
                boolean inner = y >= 6 && y <= 7 && x >= x0 + 2 && x <= x0 + 6;
                c.set(x, y, edge ? white : inner ? mid : light);
            }
        }
        for (int y = 9; y <= 12; y++) {
            for (int x = 3; x <= 11; x++) {
                c.set(x, y, y == 9 ? light : y == 12 ? dark : mid);
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int y = 8 - i; y <= 11 - i; y++) {
                c.set(12 + i, y + 1, y >= 10 - i ? shadow : dark);
            }
        }
        c.outline(rgb(0x20252D));
        return c;
    }

    static Canvas silverSword() {
        Canvas c = new Canvas();
        int outline = rgb(0x232830);
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                int s = x + y;
                int a = x - y;
                int d = s - 15;
                if (a >= 5 && a <= 13) {
                    int max = a >= 12 ? 0 : a >= 10 ? 1 : 2;
                    if (Math.abs(d) == max) {
                        c.set(x, y, outline);
                    } else if (Math.abs(d) < max) {
                        c.set(x, y, d < 0 ? rgb(0xF4F8FC) : d == 0 ? rgb(0xC6D0DC) : rgb(0x8B97A7));
                    }
                } else if (a >= 3 && a <= 4) {
                    if (Math.abs(d) == 4 || (a == 3 && Math.abs(d) == 3) || (a == 4 && Math.abs(d) == 3)) {
                        c.set(x, y, rgb(0x5A3F12));
                    } else if (Math.abs(d) < 4) {
                        c.set(x, y, a == 4 ? rgb(0xE6C25A) : rgb(0xB88A2E));
                    }
                } else if (a >= -8 && a <= 2) {
                    if (Math.abs(d) == 2) {
                        c.set(x, y, outline);
                    } else if (Math.abs(d) < 2) {
                        boolean wrap = a % 3 == 0;
                        c.set(x, y, d < 0 ? (wrap ? rgb(0x8A5A32) : rgb(0x7A4E2A)) : d == 0 ? (wrap ? rgb(0x6A4425) : rgb(0x5B3A22)) : rgb(0x3A2414));
                    }
                } else if (a >= -11 && a <= -9) {
                    if (Math.abs(d) == 2 || a == -11) {
                        c.set(x, y, outline);
                    } else if (Math.abs(d) < 2) {
                        c.set(x, y, d < 0 ? rgb(0x7FC0FF) : d == 0 ? rgb(0x3E86E0) : rgb(0x1E4FA0));
                    }
                }
            }
        }
        return c;
    }

    static Canvas woodenStake() {
        Canvas c = new Canvas();
        int outline = rgb(0x2A1A0E);
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                int s = x + y;
                int a = x - y;
                int d = s - 15;
                if (a < -10 || a > 14) {
                    continue;
                }
                int max = a <= 6 ? 2 : a <= 9 ? 1 : a <= 13 ? 0 : -1;
                if (max < 0) {
                    continue;
                }
                if (Math.abs(d) == max) {
                    c.set(x, y, outline);
                } else if (Math.abs(d) < max) {
                    boolean wrap = a >= -5 && a <= -3;
                    int color;
                    if (wrap) {
                        color = d < 0 ? rgb(0xB0473A) : d == 0 ? rgb(0x8A2F27) : rgb(0x5A1B18);
                    } else {
                        color = d < 0 ? rgb(0xE8C98A) : d == 0 ? rgb(0xC89A58) : rgb(0x946A36);
                    }
                    c.set(x, y, color);
                }
            }
        }
        c.set(5, 10, rgb(0x5E4020));
        c.set(9, 7, rgb(0x946A36));
        c.set(8, 6, rgb(0x5E4020));
        return c;
    }

    static Canvas graveDust() {
        return ascii(new String[] {
                "................",
                "................",
                "................",
                "................",
                ".......oo.......",
                "......oddo......",
                ".....odmmdo..b..",
                "....odmmlmdo....",
                "...odmmlllmdo...",
                "..odmmllwllmdo..",
                ".odmmlbllllmmdo.",
                ".odmmmlllllmmdo.",
                "..oddmmmmmmmddo.",
                "...ooddddddddoo.",
                ".....oooooooo...",
                "................"
        }, palette('o', 0x1F1B24, 'd', 0x3F3A4A, 'm', 0x625A70, 'l', 0x8C849A, 'w', 0xB7B0C4, 'b', 0xD8D0B0));
    }

    static Canvas ectoplasm() {
        Canvas c = ascii(new String[] {
                "................",
                "................",
                ".......oo.......",
                "......oddo......",
                ".....odmmdo.....",
                "....odmlmmdo....",
                "...odmlwlmmdo...",
                "...odmllllmmdo..",
                "..odmmllmmmmdo..",
                "..odmmmmmmmmdo..",
                "..odmmmmmmmddo..",
                "...oddmmmmmddo..",
                "....oodddddddo..",
                "......oooooo.o..",
                ".......o..o.....",
                "................"
        }, palette('o', 0x1C4A5A, 'd', 0x2E8DA3, 'm', 0x5ED3E0, 'l', 0xA6F3F2, 'w', 0xEFFFFF));
        int hollow = rgb(0x0E3340);
        c.set(6, 7, hollow);
        c.set(9, 7, hollow);
        c.set(6, 8, hollow);
        c.set(9, 8, hollow);
        c.set(7, 10, hollow);
        c.set(8, 10, hollow);
        return c;
    }

    static Canvas impHorn() {
        return ascii(new String[] {
                "................",
                "..............oo",
                ".............oto",
                "............olto",
                "...........olmto",
                "..........olmmo.",
                ".........olmmdo.",
                "........olmmdo..",
                "......ooolrmdo..",
                "....oolmmrmddo..",
                "...olmmrmddoo...",
                "..olmrmmddo.....",
                "..olmmddoo......",
                "..oddddoo.......",
                "...oooo.........",
                "................"
        }, palette('o', 0x140A0A, 'd', 0x3A1410, 'm', 0x6B2218, 'l', 0xA33A26, 't', 0xE8D6B0, 'r', 0x2A0E0A));
    }

    static Canvas werewolfFang() {
        return ascii(new String[] {
                "................",
                "................",
                "....oooooooo....",
                "...olllllllmo...",
                "...olwwlllmmdo..",
                "...omlllllmddo..",
                "....omlllmmdo...",
                "....omllmmdo....",
                ".....omlmmdo....",
                ".....omlmdo.....",
                "......omldo.....",
                "......omlo......",
                ".......odo......",
                ".......oo.......",
                "................",
                "................"
        }, palette('o', 0x3A3226, 'd', 0xA89E86, 'm', 0xD4C9AE, 'l', 0xF0E8D0, 'w', 0xFFFFFF));
    }

    static Canvas vampireFang() {
        return ascii(new String[] {
                "................",
                "................",
                "....ooooooo.....",
                "....olllllmo....",
                "....olwllmmdo...",
                "....omlllmmdo...",
                ".....omllmmdo...",
                ".....omlmmdo....",
                ".....omlmmdo....",
                "......omlmdo....",
                "......omlmdo....",
                ".......omldo....",
                ".......ocmdo....",
                ".......occo.....",
                "........oco.....",
                ".........o......"
        }, palette('o', 0x3A3226, 'd', 0xA89E86, 'm', 0xD4C9AE, 'l', 0xF0E8D0, 'w', 0xFFFFFF, 'c', 0x9A1426));
    }

    static Canvas purifyingTonic() {
        return ascii(new String[] {
                "................",
                "......oooo......",
                "......oCcco.....",
                "......occco.....",
                ".......oo.......",
                "......ogggo.....",
                ".....ogwlllo....",
                "....ogllllmmo...",
                "....oglllmmmdo..",
                "....oglmmmmmdo..",
                "....ogmmmmmddo..",
                "....ogmmmmddo...",
                ".....odddddo....",
                "......oooooo....",
                "................",
                "................"
        }, palette('o', 0x2B3A4A, 'g', 0xB8D8E8, 'l', 0xFFF3B0, 'm', 0xF2C94C, 'd', 0xC48A1A, 'c', 0x8B5A2B, 'C', 0xB07A3E, 'w', 0xFFFFFF));
    }

    static Canvas lunarTalisman() {
        Canvas c = new Canvas();
        int chain = rgb(0x8E98A6);
        int chainDark = rgb(0x4A525E);
        for (int i = 0; i <= 5; i++) {
            c.set(2 + i, i, (i % 2 == 0) ? chain : chainDark);
            c.set(13 - i, i, (i % 2 == 0) ? chain : chainDark);
        }
        c.set(7, 5, chain);
        c.set(8, 5, chain);
        double cx = 8.0;
        double cy = 10.5;
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                double dx = x + 0.5 - cx;
                double dy = y + 0.5 - cy;
                boolean outer = dx * dx + dy * dy <= 5.4 * 5.4;
                double ix = x + 0.5 - (cx + 3.1);
                double iy = y + 0.5 - (cy - 1.6);
                boolean inner = ix * ix + iy * iy <= 4.9 * 4.9;
                if (outer && !inner) {
                    double light = -(dx * 0.6 + dy * 0.8) / 5.4;
                    int color = light > 0.55 ? rgb(0xF6FAFE) : light > 0.15 ? rgb(0xD2DBE6) : light > -0.3 ? rgb(0xA3AFBF) : rgb(0x6F7A8A);
                    c.set(x, y, color);
                }
            }
        }
        c.outline(rgb(0x22262E));
        c.set(5, 9, rgb(0x2A5FD0));
        c.set(6, 9, rgb(0x5FA0FF));
        c.set(5, 10, rgb(0x1B3F96));
        c.set(6, 10, rgb(0x2A5FD0));
        c.set(6, 9, rgb(0xB5D8FF));
        return c;
    }

    static Canvas bloodSigil() {
        Canvas c = new Canvas();
        double cx = 8.0;
        double cy = 9.0;
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                double dx = x + 0.5 - cx;
                double dy = y + 0.5 - cy;
                double r = Math.sqrt(dx * dx + dy * dy);
                if (r <= 6.3) {
                    if (r > 5.0) {
                        double light = -(dx * 0.6 + dy * 0.8) / 6.3;
                        c.set(x, y, light > 0.3 ? rgb(0x6C6C7A) : light > -0.3 ? rgb(0x44444F) : rgb(0x2B2B33));
                    } else {
                        double glow = 1.0 - r / 5.0;
                        c.set(x, y, glow > 0.6 ? rgb(0xD11F3C) : glow > 0.3 ? rgb(0x9C1228) : rgb(0x5E0B18));
                    }
                }
            }
        }
        int gold = rgb(0xF0C255);
        int[][] pts = new int[5][2];
        for (int k = 0; k < 5; k++) {
            double angle = Math.toRadians(-90 + 72 * k);
            pts[k][0] = (int) Math.round(cx - 0.5 + Math.cos(angle) * 4.0);
            pts[k][1] = (int) Math.round(cy - 0.5 + Math.sin(angle) * 4.0);
        }
        for (int k = 0; k < 5; k++) {
            int[] from = pts[k];
            int[] to = pts[(k + 2) % 5];
            c.line(from[0], from[1], to[0], to[1], gold);
        }
        c.set(7, 8, rgb(0xFF8A9A));
        c.set(8, 8, rgb(0xFFC0C8));
        c.outline(rgb(0x14080C));
        int chain = rgb(0x8E98A6);
        c.set(7, 0, rgb(0x14080C));
        c.set(8, 0, chain);
        c.set(9, 0, rgb(0x14080C));
        c.set(7, 1, chain);
        c.set(8, 1, rgb(0x14080C));
        c.set(9, 1, chain);
        c.set(8, 2, rgb(0x14080C));
        return c;
    }

    static Canvas necromancerScepter() {
        Canvas c = new Canvas();
        int outline = rgb(0x1A1410);
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                int s = x + y;
                int a = x - y;
                int d = s - 15;
                if (a < -11 || a > 6) {
                    continue;
                }
                boolean band = (a >= -7 && a <= -6) || (a >= -1 && a <= 0) || a == 5;
                if (band && Math.abs(d) <= 2) {
                    if (Math.abs(d) == 2) {
                        c.set(x, y, outline);
                    } else {
                        c.set(x, y, d < 0 ? rgb(0xE4EAF2) : d == 0 ? rgb(0xB4BFCC) : rgb(0x7C8794));
                    }
                } else if (Math.abs(d) == 1 && a == -11) {
                    c.set(x, y, outline);
                } else if (Math.abs(d) == 2 && !band) {
                    if (Math.abs(d) == 2 && (a >= -10)) {
                        c.set(x, y, outline);
                    }
                } else if (Math.abs(d) < 2) {
                    c.set(x, y, d < 0 ? rgb(0x7A675A) : d == 0 ? rgb(0x54443A) : rgb(0x30261F));
                }
            }
        }
        Map<Character, Integer> p = palette('o', 0x1A1410, 'W', 0xEDE6D2, 'w', 0xC9C0A6, 'd', 0x8E856C, 'c', 0x5EF4FF);
        c.stamp(8, 0, new String[] {
                ".ooooo..",
                "oWWWWWo.",
                "oWcWcWo.",
                "oWWdWWo.",
                ".oWWWo..",
                "..owo..."
        }, p);
        c.set(13, 1, rgb(0x5EF4FF));
        c.set(14, 3, rgb(0x2AB6C8));
        c.set(14, 0, rgb(0xB5FBFF));
        return c;
    }

    static Canvas summoningHorn() {
        return ascii(new String[] {
                "................",
                "..oooo..........",
                ".oGGGGo.........",
                ".ogggGo.........",
                ".oWWWwo.........",
                "..oWWwdo........",
                "..oWWwdo........",
                "...oWwwdo.......",
                "....oWwwdoo.....",
                ".....oWwwddooo..",
                "......oWwwwwddo.",
                ".......oWwwGGGo.",
                "........ooWmmGo.",
                ".........oGmmGo.",
                "..........ooGGo.",
                "............oo.."
        }, palette('o', 0x2A2218, 'W', 0xF4EBCF, 'w', 0xD6C9A2, 'd', 0xA8997A, 'G', 0xE6B84A, 'g', 0xA8801E, 'm', 0x1B140C));
    }

    static Canvas demonomicon() {
        Canvas c = new Canvas();
        int outline = rgb(0x12070A);
        int spine = rgb(0x2B0F14);
        int cover = rgb(0x6E1520);
        int coverLight = rgb(0x93212F);
        int coverDark = rgb(0x4A0C15);
        int gold = rgb(0xE8B53A);
        int goldDark = rgb(0xA87A1E);
        int pages = rgb(0xEFE4C8);
        int pagesDark = rgb(0xC4B48E);
        c.rect(2, 1, 11, 14, outline);
        c.rect(3, 2, 9, 12, cover);
        c.rect(3, 2, 2, 12, spine);
        c.rect(5, 2, 7, 1, coverLight);
        c.rect(11, 2, 1, 12, coverDark);
        c.rect(5, 13, 7, 1, coverDark);
        c.rect(13, 2, 2, 13, outline);
        c.rect(13, 3, 1, 11, pages);
        c.rect(13, 13, 1, 1, pagesDark);
        c.rect(3, 14, 11, 1, outline);
        c.rect(4, 14, 9, 1, pagesDark);
        c.set(3, 2, gold);
        c.set(4, 2, goldDark);
        c.set(3, 13, gold);
        c.set(4, 13, goldDark);
        c.set(11, 2, gold);
        c.set(11, 13, gold);
        c.set(2, 4, goldDark);
        c.set(2, 7, goldDark);
        c.set(2, 10, goldDark);
        Map<Character, Integer> p = palette('g', 0xE8B53A, 'G', 0xA87A1E, 'y', 0xFFE03A, 'k', 0x12070A, 'h', 0xFF9A3A);
        c.stamp(5, 4, new String[] {
                "g.....g",
                "gg...gg",
                ".gGGGg.",
                ".GyyyG.",
                ".GykyG.",
                "..GyG..",
                "..hGh.."
        }, p);
        return c;
    }

    public static void main(String[] args) throws Exception {
        File out = new File(args[0]);
        out.mkdirs();
        Map<String, Canvas> items = new LinkedHashMap<>();
        items.put("silver_ingot", silverIngot());
        items.put("silver_sword", silverSword());
        items.put("wooden_stake", woodenStake());
        items.put("grave_dust", graveDust());
        items.put("ectoplasm", ectoplasm());
        items.put("imp_horn", impHorn());
        items.put("werewolf_fang", werewolfFang());
        items.put("vampire_fang", vampireFang());
        items.put("purifying_tonic", purifyingTonic());
        items.put("lunar_talisman", lunarTalisman());
        items.put("blood_sigil", bloodSigil());
        items.put("necromancer_scepter", necromancerScepter());
        items.put("summoning_horn", summoningHorn());
        items.put("demonomicon", demonomicon());
        for (Map.Entry<String, Canvas> entry : items.entrySet()) {
            ImageIO.write(entry.getValue().image, "png", new File(out, entry.getKey() + ".png"));
        }
        if (args.length > 1) {
            int scale = 8;
            int cols = 5;
            int rows = (items.size() + cols - 1) / cols;
            BufferedImage sheet = new BufferedImage(cols * 16 * scale, rows * 16 * scale, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = sheet.createGraphics();
            int i = 0;
            for (Canvas canvas : items.values()) {
                int ox = (i % cols) * 16 * scale;
                int oy = (i / cols) * 16 * scale;
                for (int y = 0; y < 16 * scale; y += scale) {
                    for (int x = 0; x < 16 * scale; x += scale) {
                        g.setColor(((x / scale + y / scale) % 2 == 0) ? new Color(0x8A8A8A) : new Color(0x9C9C9C));
                        g.fillRect(ox + x, oy + y, scale, scale);
                    }
                }
                g.drawImage(canvas.image, ox, oy, 16 * scale, 16 * scale, null);
                i++;
            }
            g.dispose();
            ImageIO.write(sheet, "png", new File(args[1]));
        }
    }
}
