package nadiendev.umc.mysticalagriculture.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import nadiendev.umc.mysticalagriculture.catalog.MysticalCropCatalog;
import nadiendev.umc.mysticalagriculture.config.MysticalAgricultureConfig;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.conditions.ICondition;

public record MysticalCropCondition(String crop, String recipe) implements ICondition {

    public static final String CROP = "crop";
    public static final String SEED_INFUSION = "seed_infusion";
    public static final String SEED_REPROCESSOR = "seed_reprocessor";
    public static final String ESSENCE_CRAFTING = "essence_crafting";

    public static final MapCodec<MysticalCropCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("crop").forGetter(MysticalCropCondition::crop),
            Codec.STRING.optionalFieldOf("recipe", ESSENCE_CRAFTING).forGetter(MysticalCropCondition::recipe)
    ).apply(instance, MysticalCropCondition::new));

    @Override
    public boolean test(IContext context) {
        MysticalAgricultureConfig config = MysticalAgricultureConfig.get();
        if (!config.enabled() || !config.cropEnabled(crop) || !recipeEnabled(config)) {
            return false;
        }
        return MysticalCropCatalog.all().stream()
                .filter(definition -> definition.id().equals(crop))
                .anyMatch(definition -> definition.modsLoaded(ModList.get()));
    }

    private boolean recipeEnabled(MysticalAgricultureConfig config) {
        return switch (recipe) {
            case SEED_INFUSION -> config.seedInfusionRecipes();
            case SEED_REPROCESSOR -> config.seedReprocessorRecipes();
            case ESSENCE_CRAFTING -> config.essenceCraftingRecipes();
            default -> true;
        };
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
