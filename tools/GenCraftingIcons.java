import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class GenCraftingIcons {

    private static BufferedImage image;

    private static void begin() {
        image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    }

    private static void px(int x, int y, int color) {
        if (x >= 0 && y >= 0 && x < 16 && y < 16) {
            image.setRGB(x, y, color);
        }
    }

    private static boolean filled(int x, int y) {
        return x >= 0 && y >= 0 && x < 16 && y < 16 && ((image.getRGB(x, y) >>> 24) != 0);
    }

    private static void outline(int color) {
        boolean[][] mark = new boolean[16][16];
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                if (!filled(x, y) && (filled(x - 1, y) || filled(x + 1, y) || filled(x, y - 1) || filled(x, y + 1))) {
                    mark[y][x] = true;
                }
            }
        }
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                if (mark[y][x]) {
                    image.setRGB(x, y, color);
                }
            }
        }
    }

    private static void art(String[] rows, Map<Character, Integer> palette, boolean mirror) {
        if (rows.length != 16) {
            throw new IllegalStateException("rows " + rows.length);
        }
        for (int y = 0; y < 16; y++) {
            String row = rows[y];
            if (mirror) {
                if (row.length() != 8) {
                    throw new IllegalStateException("half row " + y + " is " + row.length() + ": " + row);
                }
                row = row + new StringBuilder(row).reverse();
            }
            if (row.length() != 16) {
                throw new IllegalStateException("row " + y + " is " + row.length() + ": " + row);
            }
            for (int x = 0; x < 16; x++) {
                Integer color = palette.get(row.charAt(x));
                if (color != null) {
                    px(x, y, color);
                }
            }
        }
    }

    private static Map<Character, Integer> pal(String spec) {
        Map<Character, Integer> map = new HashMap<>();
        for (String entry : spec.split(",")) {
            map.put(entry.charAt(0), 0xFF000000 | Integer.parseInt(entry.substring(2), 16));
        }
        return map;
    }

    private static void save(String root, String path) throws Exception {
        File out = new File(root, path);
        out.getParentFile().mkdirs();
        ImageIO.write(image, "png", out);
    }

    private static void rows(String[] rows, String palette, boolean mirror, int outlineColor, String root, String path) throws Exception {
        begin();
        art(rows, pal(palette), mirror);
        if (outlineColor != 0) {
            outline(0xFF000000 | outlineColor);
        }
        save(root, path);
    }

    private static final String CLOTH = "o=190E33,P=4B2A7F,p=7B52B5,d=2F1A55,y=F3C94B,Y=FFE9A0,B=2A1740,r=D83A64,l=8E4FAB";

    private static void witchHat(String root) throws Exception {
        begin();
        Map<Character, Integer> p = pal(CLOTH);
        for (int y = 1; y <= 9; y++) {
            double t = (10.0 - y) / 9.0;
            double cx = 7.5 + 5.0 * Math.pow(t, 1.7);
            double hw = 0.9 + (y - 1) * 0.42;
            int x0 = (int) Math.round(cx - hw);
            int x1 = (int) Math.round(cx + hw);
            for (int x = x0; x <= x1; x++) {
                int w = x1 - x0 + 1;
                char c = 'P';
                if (x - x0 < Math.max(1, w / 3)) {
                    c = 'p';
                } else if (x1 - x < Math.max(1, w / 4)) {
                    c = 'd';
                }
                px(x, y, p.get(c));
            }
        }
        for (int x = 3; x <= 12; x++) {
            px(x, 10, p.get('B'));
        }
        px(7, 10, p.get('y'));
        px(8, 10, p.get('Y'));
        for (int x = 1; x <= 14; x++) {
            px(x, 11, p.get(x < 5 ? 'p' : 'P'));
        }
        for (int x = 2; x <= 13; x++) {
            px(x, 12, p.get('d'));
        }
        outline(0xFF190E33);
        save(root, "textures/item/witch_hat.png");
    }

    private static void witchRobe(String root) throws Exception {
        String[] half = {
                "........",
                ".....yyy",
                "..pPPPyy",
                ".pPPPPPP",
                ".pPPdPPP",
                ".pPPdPPP",
                ".PPPdPPP",
                ".yyy.PPP",
                "....dPPP",
                "...pPPPP",
                "...pPPPl",
                "..pPPPll",
                "..PPPPll",
                ".pPPPPll",
                ".yyyyyyy",
                "........"
        };
        rows(half, CLOTH, true, 0x190E33, root, "textures/item/witch_robe.png");
    }

    private static void witchCloak(String root) throws Exception {
        String[] half = {
                "........",
                "...ppPPP",
                "..pPPPPP",
                "..pPyrPP",
                ".pPPPPPP",
                ".pPPPPPd",
                ".pPPPPll",
                ".pPPPPll",
                ".pPPPPll",
                ".PPPPPll",
                ".PPPPPll",
                ".PPPPPll",
                ".PPPPPll",
                ".PPPPPPl",
                ".yyyyyyy",
                "........"
        };
        rows(half, CLOTH, true, 0x190E33, root, "textures/item/witch_cloak.png");
    }

    private static void witchBoots(String root) throws Exception {
        String[] rows = {
                "................",
                "................",
                "....yyyyyy......",
                "....pBBBBd......",
                "....pBBBBd......",
                "....pBBBBd......",
                "....pBBBBd......",
                "....pBBBBBd.....",
                "....pBBBBBBd.bB.",
                "....pBBBBBBBBbB.",
                "...pBBBBBBBBBBb.",
                "...BBBBBBBBBBb..",
                "...dddddddddd...",
                "................",
                "................",
                "................"
        };
        rows(rows, "o=140C08,B=4A2E22,p=6E4A3A,d=24160F,y=D2A93C", false, 0x140C08, root, "textures/item/witch_boots.png");
    }

    private static void bottle(String root, String name, String liquid) throws Exception {
        String[] rows = {
                "................",
                "......CCCC......",
                "......CccC......",
                ".....GGGGGG.....",
                "......GGGG......",
                ".....GggggG.....",
                "....GLLLLLLG....",
                "...GLLwLLLLlG...",
                "...GLLwLLLLlG...",
                "...GLLLLLLLlG...",
                "...GLLLLLLllG...",
                "...GLLLLLllLG...",
                "....GLLLLLlG....",
                ".....GGGGGG.....",
                "................",
                "................"
        };
        rows(rows, "C=A67B45,c=7A5530,G=C9E4EE,g=9CC4D4," + liquid, false, 0x1B2A33, root, "textures/item/" + name + ".png");
    }

    private static void spool(String root) throws Exception {
        String[] rows = {
                "................",
                "...WWWWWWWWWW...",
                "..WwwwwwwwwwwW..",
                "...WWWWWWWWWW...",
                "...BbBBbBBbBB...",
                "...bBBbBBbBBb...",
                "...BBbBBbBBbB...",
                "...BbBBbBBbBB...",
                "...bBBbBBbBBb...",
                "...BBbBBbBBbB...",
                "...WWWWWWWWWW...",
                "..WwwwwwwwwwwW..",
                "...WWWWWWWWWW...",
                ".....sB.........",
                "....sB..........",
                "................"
        };
        rows(rows, "W=8A6A45,w=B08A5A,B=C5DBFF,b=8FB4F0,s=FFFFFF", false, 0x1E2A4A, root, "textures/item/moonthread.png");
    }

    private static void cloth(String root) throws Exception {
        String[] rows = {
                "................",
                "................",
                "..pppppppppppp..",
                "..PPPPPPyPPPPP..",
                "..PPPPPPPPPPyP..",
                "..PPyPPPPPPPPP..",
                "..PPPPPPPPyPPP..",
                "..PPPPyPPPPPPP..",
                "..PPPPPPPPPPPP..",
                "..dddddddddddd..",
                "..pppppppppppp..",
                "..PPPPPPPPPPPP..",
                "..PyPPPPPyPPPP..",
                "..dddddddddddd..",
                "................",
                "................"
        };
        rows(rows, CLOTH, false, 0x190E33, root, "textures/item/witch_cloth.png");
    }

    private static void seeds(String root, String name, String colors, int line) throws Exception {
        String[] rows = {
                "................",
                "................",
                "................",
                "....SS......SS..",
                "...SssS....SssS.",
                "...SsSS....SsSS.",
                "....SS......SS..",
                "................",
                ".......SS.......",
                "......SssS......",
                "......SsSS......",
                ".......SS.......",
                "..SS........SS..",
                ".SssS......SssS.",
                ".SsSS......SsSS.",
                "..SS........SS.."
        };
        rows(rows, colors, false, line, root, "textures/item/" + name + ".png");
    }

    private static void sprig(int x0, int y0, int x1, int y1, int leafMax, int stem, int leaf, int leafLight, int tipColor) {
        int steps = Math.max(Math.abs(x1 - x0), Math.abs(y1 - y0));
        for (int i = 0; i <= steps; i++) {
            int x = x0 + (x1 - x0) * i / Math.max(1, steps);
            int y = y0 + (y1 - y0) * i / Math.max(1, steps);
            px(x, y, stem);
            if (i % 2 == 1 && i < steps - 1) {
                int len = Math.max(1, leafMax - (i * leafMax) / Math.max(1, steps));
                for (int k = 1; k <= len; k++) {
                    int ly = y - (k > 1 ? 1 : 0);
                    px(x - k, ly, k == len ? leafLight : leaf);
                    px(x + k, ly, k == len ? leafLight : leaf);
                }
            }
        }
        px(x1, y1 - 1, tipColor);
        px(x1, y1 - 2, leafLight);
    }

    private static void wormwoodSprig(String root) throws Exception {
        begin();
        sprig(5, 15, 9, 2, 4, 0xFF566B34, 0xFF7E9F78, 0xFFB7D0AA, 0xFF9DBB8A);
        px(9, 3, 0xFFB7D0AA);
        px(10, 2, 0xFFE8E4A0);
        px(8, 2, 0xFFE8E4A0);
        outline(0xFF263316);
        save(root, "textures/item/wormwood_sprig.png");
    }

    private static void vervainFlower(String root) throws Exception {
        begin();
        for (int y = 6; y <= 15; y++) {
            px(8, y, 0xFF4F7A3A);
        }
        px(7, 12, 0xFF6FA04A);
        px(6, 11, 0xFF6FA04A);
        px(9, 10, 0xFF6FA04A);
        px(10, 9, 0xFF6FA04A);
        px(6, 14, 0xFF6FA04A);
        px(5, 13, 0xFF6FA04A);
        int[] cols = {0xFF8E5BD0, 0xFFB890F0, 0xFF6A3FA8, 0xFFD2B4FF};
        for (int y = 1; y <= 7; y++) {
            for (int dx = -1; dx <= 1; dx++) {
                if ((y + dx) % 2 == 0 || dx == 0) {
                    px(8 + dx, y, cols[(y * 3 + dx + 3) % 4]);
                }
            }
        }
        px(8, 0, 0xFFD2B4FF);
        outline(0xFF2A1A4A);
        save(root, "textures/item/vervain_flower.png");
    }

    private static void bloom(int cx, int top, int[] petals, int center) {
        for (int dy = 0; dy < 5; dy++) {
            for (int dx = -2; dx <= 2; dx++) {
                int dist = Math.abs(dx) + Math.abs(dy - 2);
                if (dist <= 3) {
                    px(cx + dx, top + dy, dist == 0 ? center : petals[Math.min(petals.length - 1, dist)]);
                }
            }
        }
    }

    private static void moonbloom(String root) throws Exception {
        begin();
        int stem = 0xFF3E7A52;
        for (int y = 8; y <= 15; y++) {
            px(8, y, stem);
        }
        px(7, 11, 0xFF52A06A);
        px(6, 10, 0xFF52A06A);
        px(9, 13, 0xFF52A06A);
        px(10, 12, 0xFF52A06A);
        px(7, 14, 0xFF52A06A);
        bloom(8, 3, new int[]{0xFFFFFFFF, 0xFFE6F2FF, 0xFFB5D2FF, 0xFF8FB0F0}, 0xFFFFF0A0);
        px(8, 2, 0xFFE6F2FF);
        px(3, 4, 0xFFC8E0FF);
        px(13, 6, 0xFFC8E0FF);
        px(12, 1, 0xFFFFFFFF);
        px(4, 9, 0xFFFFFFFF);
        save(root, "textures/block/moonbloom.png");
    }

    private static void mugwort(String root) throws Exception {
        begin();
        int stem = 0xFF5B6B3E;
        int leaf = 0xFF7F9378;
        int light = 0xFFB4C5A8;
        int bud = 0xFFD9C070;
        int[][] stems = {{4, 15, 3, 5}, {8, 15, 8, 2}, {12, 15, 13, 4}};
        for (int[] s : stems) {
            int steps = 15 - s[3];
            for (int i = 0; i <= steps; i++) {
                int x = s[0] + (s[2] - s[0]) * i / Math.max(1, steps);
                int y = s[1] - i;
                px(x, y, stem);
                if (i % 3 == 2) {
                    px(x - 1, y, leaf);
                    px(x - 2, y - 1, light);
                    px(x + 1, y - 1, leaf);
                    px(x + 2, y - 1, light);
                }
            }
            px(s[2], s[3], bud);
            px(s[2], s[3] - 1, 0xFFE8D890);
            px(s[2] - 1, s[3] + 1, bud);
        }
        save(root, "textures/block/mugwort.png");
    }

    private static void crop(String root, String name, int stage, int stem, int leaf, int leafLight, int accent, int accentLight) throws Exception {
        int[] heights = {4, 8, 12, 15};
        int h = heights[stage];
        begin();
        int[] xs = {3, 8, 12};
        for (int i = 0; i < xs.length; i++) {
            int bx = xs[i];
            int height = Math.max(3, h - (i == 1 ? 0 : 1 + i % 2 * 2));
            int top = 15 - height;
            for (int y = 15; y > top; y--) {
                px(bx, y, stem);
                if (stage >= 1 && (15 - y) % 3 == 1 && y > top + 1) {
                    px(bx - 1, y, leaf);
                    px(bx + 1, y, leaf);
                    if (stage >= 2) {
                        px(bx - 2, y - 1, leafLight);
                        px(bx + 2, y - 1, leafLight);
                    }
                }
            }
            if (stage == 0) {
                px(bx - 1, top + 1, leaf);
                px(bx + 1, top + 1, leaf);
                px(bx, top, leafLight);
            } else if (stage < 3) {
                px(bx, top, leafLight);
                px(bx - 1, top + 1, leaf);
                px(bx + 1, top + 1, leaf);
            } else {
                for (int k = 0; k < 4; k++) {
                    px(bx, top + k, (k % 2 == 0) ? accent : accentLight);
                    if (k % 2 == 1) {
                        px(bx - 1, top + k, accent);
                        px(bx + 1, top + k, accent);
                    }
                }
                px(bx, top - 1, accentLight);
            }
        }
        save(root, "textures/block/" + name + "_stage" + stage + ".png");
    }

    public static void main(String[] args) throws Exception {
        String root = args[0];
        witchHat(root);
        witchRobe(root);
        witchCloak(root);
        witchBoots(root);
        bottle(root, "wormwood_tincture", "L=86A34A,l=5E7A32,w=D4E8A0");
        bottle(root, "vervain_tonic", "L=8B5BD6,l=5E3AA0,w=D6BCFF");
        bottle(root, "moondew", "L=8CC8FF,l=5E98E0,w=FFFFFF");
        spool(root);
        cloth(root);
        seeds(root, "wormwood_seeds", "S=7E9A6A,s=C4D8B0", 0x2A3320);
        seeds(root, "vervain_seeds", "S=5A3E78,s=A88AC8", 0x1E1230);
        wormwoodSprig(root);
        vervainFlower(root);
        moonbloom(root);
        mugwort(root);
        for (int stage = 0; stage < 4; stage++) {
            crop(root, "wormwood", stage, 0xFF566B34, 0xFF6F9068, 0xFFA6C29A, 0xFFE0D27A, 0xFFF4EBA8);
            crop(root, "vervain", stage, 0xFF3F6B30, 0xFF5E9440, 0xFF8CC066, 0xFF8E5BD0, 0xFFC3A0F4);
        }
    }
}
