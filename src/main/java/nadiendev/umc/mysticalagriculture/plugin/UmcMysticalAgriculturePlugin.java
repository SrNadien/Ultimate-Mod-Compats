package nadiendev.umc.mysticalagriculture.plugin;

import com.blakebr0.mysticalagriculture.api.IMysticalAgriculturePlugin;
import com.blakebr0.mysticalagriculture.api.MysticalAgriculturePlugin;
import com.blakebr0.mysticalagriculture.api.crop.Crop;
import com.blakebr0.mysticalagriculture.api.crop.CropTextures;
import com.blakebr0.mysticalagriculture.api.crop.CropTier;
import com.blakebr0.mysticalagriculture.api.crop.CropType;
import com.blakebr0.mysticalagriculture.api.lib.LazyIngredient;
import com.blakebr0.mysticalagriculture.api.registry.ICropRegistry;
import java.util.LinkedHashMap;
import java.util.Map;
import nadiendev.umc.mysticalagriculture.catalog.MysticalCropCatalog;
import nadiendev.umc.mysticalagriculture.catalog.MysticalCropDefinition;
import nadiendev.umc.mysticalagriculture.config.MysticalAgricultureConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MysticalAgriculturePlugin
public class UmcMysticalAgriculturePlugin implements IMysticalAgriculturePlugin {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmcMysticalAgriculturePlugin.class);

    private final Map<Crop, MysticalCropDefinition> registered = new LinkedHashMap<>();

    @Override
    public void onRegisterCrops(ICropRegistry registry) {
        MysticalAgricultureConfig config = MysticalAgricultureConfig.get();
        if (!config.enabled()) {
            return;
        }
        ModList mods = ModList.get();
        for (MysticalCropDefinition definition : MysticalCropCatalog.all()) {
            if (!config.cropEnabled(definition.id()) || !definition.modsLoaded(mods)) {
                continue;
            }
            Crop crop = create(config, definition);
            registry.register(crop);
            registered.put(crop, definition);
        }
        LOGGER.info("Registered {} bundled Mystical Agriculture crops", registered.size());
    }

    @Override
    public void onPostRegisterCrops(ICropRegistry registry) {
        registered.forEach((crop, definition) -> {
            CropTier tier = registry.getTierById(ResourceLocation.parse(definition.tier()));
            CropType type = registry.getTypeById(ResourceLocation.parse(definition.type()));
            if (tier == null || type == null) {
                LOGGER.warn("Crop {} references an unknown tier or type, disabling it", definition.id());
                crop.setEnabled(false);
                return;
            }
            crop.setTier(tier);
            crop.setType(type);
        });
    }

    private static Crop create(MysticalAgricultureConfig config, MysticalCropDefinition definition) {
        LazyIngredient ingredient = definition.tagIngredient()
                ? LazyIngredient.tag(definition.ingredient())
                : LazyIngredient.item(definition.ingredient());
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(config.cropNamespace(), definition.id());
        Crop crop = new Crop(id, CropTier.ONE, CropType.RESOURCE, ingredient);
        CropTextures textures = crop.getTextures();
        textures.setFlowerTexture(definition.flowerTexture());
        textures.setEssenceTexture(definition.essenceTexture());
        textures.setSeedTexture(definition.seedTexture());
        crop.setDisplayName(Component.translatable(definition.translationKey()));
        crop.getRecipeConfig()
                .setSeedCraftingRecipeEnabled(config.seedCraftingRecipes())
                .setSeedInfusionRecipeEnabled(config.seedInfusionRecipes())
                .setSeedReprocessorRecipeEnabled(config.seedReprocessorRecipes());
        return crop;
    }
}
