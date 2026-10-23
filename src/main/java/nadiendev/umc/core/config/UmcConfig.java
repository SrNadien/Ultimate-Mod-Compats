package nadiendev.umc.core.config;

import nadiendev.umc.compat.kubejs.config.KubeCompatConfig;
import nadiendev.umc.crafting.config.CraftingConfig;
import nadiendev.umc.creatures.config.CreaturesConfig;
import nadiendev.umc.mysticalagriculture.config.MysticalAgricultureConfig;
import nadiendev.umc.spirit.config.SpiritConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class UmcConfig {

    public static final String FILE = "umc.toml";
    public static final ModConfigSpec SPEC;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("Creatures: werewolves, vampires, hunters, mobs and necromancy").push("creatures");
        CreaturesConfig.define(builder);
        builder.pop();

        builder.comment("Spirit world, dreams and spirit infusions").push("spirit");
        SpiritConfig.define(builder);
        builder.pop();

        builder.comment("Witch crafting: familiars, clothing and plants").push("crafting");
        CraftingConfig.define(builder);
        builder.pop();

        builder.comment("Compatibility with other mods").push("compat");
        builder.comment("KubeJS support for other mods").push("kubejs");
        KubeCompatConfig.define(builder);
        builder.pop();
        builder.comment("Bundled Mystical Agriculture crops").push("mysticalagriculture");
        MysticalAgricultureConfig.define(builder);
        builder.pop();
        builder.pop();

        SPEC = builder.build();
    }

    private UmcConfig() {
    }
}
