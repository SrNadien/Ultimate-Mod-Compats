package nadiendev.umc.compat.kubejs.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import nadiendev.umc.core.config.EarlyConfig;
import nadiendev.umc.core.config.UmcConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class KubeCompatConfig {

    public static final List<String> MODULES = List.of(
            "enchanted",
            "thaumaturge",
            "ae2",
            "advanced_ae",
            "extendedae",
            "create",
            "generatorgalore",
            "excessive_utilities",
            "functionalstorage",
            "mysticalagriculture"
    );

    private static final String ROOT = "compat.kubejs";
    private static final Map<String, ModConfigSpec.BooleanValue> FLAGS = new LinkedHashMap<>();

    private static ModConfigSpec.BooleanValue enabled;

    private KubeCompatConfig() {
    }

    public static void define(ModConfigSpec.Builder builder) {
        enabled = builder.comment("Master switch. When false no KubeJS compatibility module of this mod is registered.")
                .define("enabled", true);
        builder.comment("Register the KubeJS compatibility (events, recipe types and builders) of each mod when it is loaded.").push("modules");
        for (String id : MODULES) {
            FLAGS.put(id, builder.define(id, true));
        }
        builder.pop();
    }

    public static boolean moduleEnabled(String modId) {
        ModConfigSpec.BooleanValue flag = FLAGS.get(modId);
        return flag(enabled, ROOT + ".enabled") && (flag == null || flag(flag, ROOT + ".modules." + modId));
    }

    private static boolean flag(ModConfigSpec.BooleanValue value, String key) {
        return UmcConfig.SPEC.isLoaded() ? value.get() : EarlyConfig.bool(key, true);
    }
}
