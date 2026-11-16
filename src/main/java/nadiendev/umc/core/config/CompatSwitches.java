package nadiendev.umc.core.config;

import java.util.LinkedHashMap;
import java.util.Map;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * One switch per compatibility that adds its own items. A compatibility is only active when its mod is installed and
 * its switch is on; none of them is required.
 */
public final class CompatSwitches {

    public static final String ENDERIO = "enderio";
    public static final String INDUSTRIAL_FOREGOING = "industrialforegoing";
    public static final String ARS_NOUVEAU = "arsnouveau";
    public static final String THAUMATURGE_WANDS = "thaumaturge_wands";
    public static final String SOLAR_FLUX = "solarflux";
    public static final String FUNCTIONAL_STORAGE = "functionalstorage";

    private static final Map<String, String> MODS = Map.of(
            FUNCTIONAL_STORAGE, "functionalstorage",
            ENDERIO, "enderio",
            INDUSTRIAL_FOREGOING, "industrialforegoing",
            ARS_NOUVEAU, "ars_nouveau",
            THAUMATURGE_WANDS, "thaumaturge",
            SOLAR_FLUX, "solarflux");
    private static final Map<String, String> COMMENTS = Map.of(
            ENDERIO, "Grinding balls, capacitors and conduits of the Ultimate Armoury materials for EnderIO.",
            INDUSTRIAL_FOREGOING, "Range, speed, efficiency and processing addons of the Ultimate Armoury materials for Industrial Foregoing.",
            ARS_NOUVEAU, "Source jars of the Ultimate Armoury materials for Ars Nouveau.",
            THAUMATURGE_WANDS, "Wand caps and rods of the Ultimate Armoury materials for Thaumaturge.",
            SOLAR_FLUX, "Solar panels, mirrors and coatings of the materials for Solar Flux Reborn.",
            FUNCTIONAL_STORAGE, "Storage upgrades of the materials and the drawers of the woods of other mods for Functional Storage.");
    private static final Map<String, ModConfigSpec.BooleanValue> VALUES = new LinkedHashMap<>();

    private CompatSwitches() {
    }

    public static void define(ModConfigSpec.Builder builder) {
        for (String key : new String[]{ENDERIO, INDUSTRIAL_FOREGOING, ARS_NOUVEAU, THAUMATURGE_WANDS, SOLAR_FLUX, FUNCTIONAL_STORAGE}) {
            builder.push(key);
            VALUES.put(key, builder.comment(COMMENTS.get(key)).define("enabled", true));
            builder.pop();
        }
    }

    /** True when the switch is on, whether or not the mod is installed. Used by the data conditions. */
    public static boolean switchedOn(String key) {
        if (!MODS.containsKey(key)) {
            return false;
        }
        // Another mod can ask before the configuration of this one is defined (Solar Flux Reborn asks while it is being
        // constructed), so the file is read directly until the configuration is loaded.
        ModConfigSpec.BooleanValue value = VALUES.get(key);
        if (value != null && UmcConfig.SPEC.isLoaded()) {
            return value.get();
        }
        return EarlyConfig.bool("compat.items." + key + ".enabled", true);
    }

    /** True when the mod is installed and the switch is on. */
    public static boolean enabled(String key) {
        return ModList.get().isLoaded(MODS.get(key)) && switchedOn(key);
    }
}
