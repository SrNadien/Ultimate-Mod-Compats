package nadiendev.umc.core.material;

import java.util.Arrays;
import java.util.List;
import net.neoforged.fml.ModList;

/**
 * The materials that the compatibilities with other mods make their own items from: the three of Ultimate Armoury and
 * the three of Allthemodium. The order of the constants is the order of the items in the creative tab and of the
 * crafting chains, each item is made from the one of the material before it inside its mod.
 */
public enum UmcMaterial {

    MUSHASHITE("mushashite", "Mushashite", "mushashite", 0x648789, "ultimatearmoury", true),
    JOANFOITE("joanfoite", "Joanfoite", "joanfoite", 0x7F3F50, "ultimatearmoury", true),
    NADIENITE("nadienite", "Nadienite", "nadienite", 0x749667, "ultimatearmoury", true),
    ALLTHEMODIUM("allthemodium", "Allthemodium", "allthemodium", 0xE89725, "allthemodium", true),
    VIBRANIUM("vibranium", "Vibranium", "vibranium", 0x209187, "allthemodium", true),
    UNOBTAINIUM("unobtainium", "Unobtainium", "unobtainium", 0x7E35C3, "allthemodium", true),
    /** Its "ingot" is the piglich heart. */
    PIGLICH("piglich", "Piglich", "piglich", 0xC7932C, "allthemodium", true);

    public static final String ARMOURY_MOD = "ultimatearmoury";
    public static final String ATM_MOD = "allthemodium";

    private final String id;
    private final String english;
    private final String spanish;
    private final int color;
    private final String mod;
    private final boolean wands;

    UmcMaterial(String id, String english, String spanish, int color, String mod, boolean wands) {
        this.id = id;
        this.english = english;
        this.spanish = spanish;
        this.color = color;
        this.mod = mod;
        this.wands = wands;
    }

    public String id() {
        return id;
    }

    /** Capitalized name, as it starts an English item name. */
    public String english() {
        return english;
    }

    /** Lowercase name, as it ends a Spanish item name ("Bola de molienda de mushashite"). */
    public String spanish() {
        return spanish;
    }

    /** Average color of the ingot, used to recolor the textures. */
    public int color() {
        return color;
    }

    /** The mod that has the ingot and the block of this material. */
    public String mod() {
        return mod;
    }

    public String ingot() {
        return this == PIGLICH ? mod + ":piglich_heart" : mod + ":" + id + "_ingot";
    }

    public String block() {
        return this == PIGLICH ? mod + ":piglich_heart_block" : mod + ":" + id + "_block";
    }

    /** The plate of the material, which the best addons of Industrial Foregoing are made from. The piglich has none. */
    public String plate() {
        return this == PIGLICH ? ingot() : mod + ":" + id + "_plate";
    }

    /** True when the material has a wand cap and a wand rod. */
    public boolean hasWands() {
        return wands;
    }

    /**
     * The material before this one in its crafting chain, or null when it is the first one. The chain of Allthemodium
     * goes on from the last one of Ultimate Armoury.
     */
    public UmcMaterial previous() {
        if (this == ALLTHEMODIUM) {
            return NADIENITE;
        }
        int index = ordinal() - 1;
        if (index < 0 || values()[index].mod != mod) {
            return null;
        }
        return values()[index];
    }

    public boolean installed() {
        return ModList.get().isLoaded(mod);
    }

    /** The materials of the wand caps and rods, whose mod is installed. */
    public static List<UmcMaterial> wandMaterials() {
        return available().stream().filter(UmcMaterial::hasWands).toList();
    }

    /** The materials whose mod is installed. */
    public static List<UmcMaterial> available() {
        return Arrays.stream(values()).filter(UmcMaterial::installed).toList();
    }
}
