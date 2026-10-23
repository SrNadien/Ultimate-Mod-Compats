package nadiendev.umc.compat.kubejs.excessiveutilities.component;

import dev.latvian.mods.kubejs.recipe.component.EnumComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentTypeRegistry;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.compat.kubejs.excessiveutilities.type.DivisionInversion;
import nadiendev.umc.compat.kubejs.excessiveutilities.type.FluidFuelGeneratorType;
import nadiendev.umc.compat.kubejs.excessiveutilities.type.FuelGeneratorType;
import nadiendev.umc.compat.kubejs.excessiveutilities.type.UnstableStability;
import net.minecraft.resources.ResourceLocation;

public final class ExuComponents {

    public static final RecipeComponentType<String> BLOCK_OR_TAG = RecipeComponentType.unit(id("block_or_tag"), BlockOrTagComponent::new);

    public static final RecipeComponentType<FuelGeneratorType> FUEL_GENERATOR_TYPE = EnumComponent.of(id("fuel_generator_type"), FuelGeneratorType.class);

    public static final RecipeComponentType<FluidFuelGeneratorType> FLUID_FUEL_GENERATOR_TYPE = EnumComponent.of(id("fluid_fuel_generator_type"), FluidFuelGeneratorType.class);

    public static final RecipeComponentType<DivisionInversion> DIVISION_INVERSION = EnumComponent.of(id("division_inversion"), DivisionInversion.class);

    public static final RecipeComponentType<UnstableStability> UNSTABLE_STABILITY = EnumComponent.of(id("unstable_stability"), UnstableStability.class);

    private ExuComponents() {
    }

    public static void register(RecipeComponentTypeRegistry registry) {
        registry.register(BLOCK_OR_TAG);
        registry.register(FUEL_GENERATOR_TYPE);
        registry.register(FLUID_FUEL_GENERATOR_TYPE);
        registry.register(DIVISION_INVERSION);
        registry.register(UNSTABLE_STABILITY);
    }

    public static <T> RecipeComponent<T> of(RecipeComponentType<T> type) {
        return type.instance();
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "exu_" + path);
    }
}
