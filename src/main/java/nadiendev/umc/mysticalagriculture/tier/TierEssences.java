package nadiendev.umc.mysticalagriculture.tier;

import java.util.List;
import nadiendev.umc.UltimateModCompats;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class TierEssences {

    private static final List<ResourceLocation> ESSENCES = List.of(
            id("mysticalagriculture", "inferium_essence"),
            id("mysticalagriculture", "prudentium_essence"),
            id("mysticalagriculture", "tertium_essence"),
            id("mysticalagriculture", "imperium_essence"),
            id("mysticalagriculture", "supremium_essence"),
            id("mysticalagradditions", "insanium_essence"),
            id("mysticalextendedtier", "ornium_essence"),
            id("mysticalextendedtier", "lightium_essence"),
            id("mysticalextendedtier", "tornium_essence"),
            id("mysticalextendedtier", "ouranium_essence")
    );

    private TierEssences() {
    }

    public static ResourceLocation essence(int tier) {
        return ESSENCES.get(tier - 1);
    }

    public static TagKey<Item> tag(int tier) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "tier_essence/" + tier));
    }

    private static ResourceLocation id(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }
}
