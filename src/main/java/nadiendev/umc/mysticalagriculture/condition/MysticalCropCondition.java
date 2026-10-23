package nadiendev.umc.mysticalagriculture.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import nadiendev.umc.mysticalagriculture.catalog.MysticalCropCatalog;
import nadiendev.umc.mysticalagriculture.config.MysticalAgricultureConfig;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.conditions.ICondition;

public record MysticalCropCondition(String crop) implements ICondition {

    public static final MapCodec<MysticalCropCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("crop").forGetter(MysticalCropCondition::crop)
    ).apply(instance, MysticalCropCondition::new));

    @Override
    public boolean test(IContext context) {
        MysticalAgricultureConfig config = MysticalAgricultureConfig.get();
        if (!config.enabled() || !config.essenceCraftingRecipes() || !config.cropEnabled(crop)) {
            return false;
        }
        return MysticalCropCatalog.all().stream()
                .filter(definition -> definition.id().equals(crop))
                .anyMatch(definition -> definition.modsLoaded(ModList.get()));
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
