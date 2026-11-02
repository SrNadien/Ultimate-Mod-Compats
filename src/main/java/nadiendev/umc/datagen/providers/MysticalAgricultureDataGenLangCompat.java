package nadiendev.umc.datagen.providers;

import java.util.List;
import nadiendev.umc.core.registry.UmcTabs;
import nadiendev.umc.datagen.DataModule;
import nadiendev.umc.mysticalagriculture.catalog.MysticalCropCatalog;
import nadiendev.umc.mysticalagriculture.catalog.MysticalCropDefinition;
import nadiendev.umc.mysticalagriculture.tier.TierAltarRegistry;
import nadiendev.umc.mysticalagriculture.tier.TierCrystalItem;
import nadiendev.umc.mysticalagriculture.tier.TierRules;
import nadiendev.umc.mysticalagriculture.tier.TierTooltips;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class MysticalAgricultureDataGenLangCompat implements DataModule {

    @Override
    public void addEnglish(LanguageProvider lang) {
        for (MysticalCropDefinition definition : MysticalCropCatalog.all()) {
            lang.add(definition.translationKey(), definition.names().get("en_us"));
        }
        tierBlocks(lang, "Infusion Altar (Tier %s)", "Awakening Altar (Tier %s)", "Infusion Pedestal (Tier %s)", "Awakening Pedestal (Tier %s)", "Essence Vessel (Tier %s)", "Seed Reprocessor (Tier %s)", "Furnace (Tier %s)");
        crystalNames(lang, "Infusion Crystal (Tier %s)");
        lang.add(TierCrystalItem.TOOLTIP_KEY, "Used for tier %s essences");
        lang.add(TierTooltips.SPEED_KEY, "Speed: x%s");
        lang.add(TierTooltips.FUEL_KEY, "Fuel use: x%s");
        lang.add(TierTooltips.SINGLE_KEY, "Only makes tier %s seeds");
        lang.add(TierTooltips.RANGE_KEY, "Makes tier %s and tier %s seeds");
        lang.add(TierTooltips.PART_KEY, "Works with altars up to tier %s");
        lang.add(UmcTabs.MYSTICAL_KEY, "Ultimate Mod Compats: Mystical Agriculture");
    }

    @Override
    public void addSpanish(LanguageProvider lang) {
        for (MysticalCropDefinition definition : MysticalCropCatalog.all()) {
            lang.add(definition.translationKey(), definition.names().get("es_es"));
        }
        tierBlocks(lang, "Altar de infusión (nivel %s)", "Altar del despertar (nivel %s)", "Pedestal de infusión (nivel %s)", "Pedestal del despertar (nivel %s)", "Recipiente de esencia (nivel %s)", "Reprocesador de semillas (nivel %s)", "Horno (nivel %s)");
        crystalNames(lang, "Cristal de infusión (nivel %s)");
        lang.add(TierCrystalItem.TOOLTIP_KEY, "Se usa para esencias de nivel %s");
        lang.add(TierTooltips.SPEED_KEY, "Velocidad: x%s");
        lang.add(TierTooltips.FUEL_KEY, "Consumo de combustible: x%s");
        lang.add(TierTooltips.SINGLE_KEY, "Solo hace semillas de nivel %s");
        lang.add(TierTooltips.RANGE_KEY, "Hace semillas de nivel %s y de nivel %s");
        lang.add(TierTooltips.PART_KEY, "Sirve para altares hasta nivel %s");
        lang.add(UmcTabs.MYSTICAL_KEY, "Ultimate Mod Compats: Mystical Agriculture");
    }

    private static void crystalNames(LanguageProvider lang, String name) {
        List<Item> crystals = TierAltarRegistry.crystals();
        for (int tier = TierRules.MIN_TIER; tier <= TierRules.MAX_TIER; tier++) {
            Item crystal = crystals.get(tier - 1);
            lang.addItem(() -> crystal, name.formatted(tier));
        }
    }

    private static void tierBlocks(LanguageProvider lang, String... names) {
        Block[][] groups = {
                TierAltarRegistry.infusionBlocks(), TierAltarRegistry.awakeningBlocks(),
                TierAltarRegistry.infusionPedestalBlocks(), TierAltarRegistry.awakeningPedestalBlocks(),
                TierAltarRegistry.vesselBlocks(), TierAltarRegistry.reprocessorBlocks(),
                TierAltarRegistry.furnaceBlocks()};
        for (int group = 0; group < groups.length; group++) {
            for (int tier = TierRules.MIN_TIER; tier <= TierRules.MAX_TIER; tier++) {
                lang.add(groups[group][tier - 1], names[group].formatted(tier));
            }
        }
    }
}
