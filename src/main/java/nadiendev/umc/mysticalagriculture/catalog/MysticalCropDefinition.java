package nadiendev.umc.mysticalagriculture.catalog;

import java.util.List;
import java.util.Map;
import nadiendev.umc.UltimateModCompats;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModList;

public record MysticalCropDefinition(
        String id,
        String tier,
        String type,
        boolean tagIngredient,
        String ingredient,
        List<String> requires,
        List<String> requiresAny,
        Map<String, String> names,
        String essenceResult) {

    private static final int ESSENCE_COUNT_NUMERATOR = 12;

    public static final String LANG_PREFIX = "umc.mysticalagriculture.crop.";

    public String translationKey() {
        return LANG_PREFIX + id;
    }

    public ResourceLocation seedTexture() {
        return texture("item/mystical/" + id + "_seeds");
    }

    public ResourceLocation essenceTexture() {
        return texture("item/mystical/" + id + "_essence");
    }

    public ResourceLocation flowerTexture() {
        return texture("block/mystical/" + id + "_flower");
    }

    private static ResourceLocation texture(String path) {
        return ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, path);
    }

    public boolean hasEssenceRecipe() {
        return essenceResult != null;
    }

    public int tierLevel() {
        return Integer.parseInt(tier.substring(tier.indexOf(':') + 1));
    }

    public int essenceCraftCount() {
        return Math.max(1, ESSENCE_COUNT_NUMERATOR / tierLevel());
    }

    public boolean modsLoaded(ModList mods) {
        boolean all = requires.stream().allMatch(mods::isLoaded);
        boolean any = requiresAny.isEmpty() || requiresAny.stream().anyMatch(mods::isLoaded);
        return all && any;
    }
}
