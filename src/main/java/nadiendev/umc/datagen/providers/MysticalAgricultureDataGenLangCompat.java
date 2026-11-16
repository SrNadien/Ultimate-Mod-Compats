package nadiendev.umc.datagen.providers;

import java.util.List;
import nadiendev.umc.core.registry.UmcFractalTabs;
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

    private static final String[] TIER_NAMES_EN = {"Inferium", "Prudentium", "Tertium", "Imperium", "Supremium", "Insanium", "Ornium", "Lightium", "Tornium", "Ouranium"};
    private static final String[] TIER_NAMES_ES = {"inferium", "prudentium", "tertium", "imperium", "supremium", "insanium", "ornium", "lightium", "tornium", "ouranium"};

    @Override
    public void addEnglish(LanguageProvider lang) {
        for (MysticalCropDefinition definition : MysticalCropCatalog.all()) {
            lang.add(definition.translationKey(), definition.names().get("en_us"));
        }
        tierBlocks(lang, TIER_NAMES_EN, "%s Infusion Altar", "%s Awakening Altar", "%s Infusion Pedestal", "%s Awakening Pedestal", "%s Essence Vessel", "%s Seed Reprocessor", "%s Furnace");
        crystalNames(lang, "%s Crystal", TIER_NAMES_EN);
        lang.add(TierCrystalItem.TOOLTIP_KEY, "Used for tier %s essences");
        lang.add(TierTooltips.SPEED_KEY, "Speed: x%s");
        lang.add(TierTooltips.FUEL_KEY, "Fuel use: x%s");
        lang.add(TierTooltips.SINGLE_KEY, "Only makes tier %s seeds");
        lang.add(TierTooltips.RANGE_KEY, "Makes tier %s and tier %s seeds");
        lang.add(TierTooltips.PART_KEY, "Works with altars up to tier %s");
        lang.add(UmcFractalTabs.sectionKey("seeds"), "Seeds");
        lang.add(UmcFractalTabs.sectionKey("altars"), "Altars");
        lang.add(UmcFractalTabs.sectionKey("pedestals"), "Pedestals");
        lang.add(UmcFractalTabs.sectionKey("essences"), "Essences");
        lang.add(UmcFractalTabs.sectionKey("crystals"), "Crystals");
        lang.add(UmcFractalTabs.sectionKey("machines"), "Machines");
        lang.add(UmcFractalTabs.sectionKey("tables"), "Tables");
    }

    @Override
    public void addSpanish(LanguageProvider lang) {
        for (MysticalCropDefinition definition : MysticalCropCatalog.all()) {
            lang.add(definition.translationKey(), definition.names().get("es_es"));
        }
        tierBlocks(lang, TIER_NAMES_ES, "Altar de infusión de %s", "Altar del despertar de %s", "Pedestal de infusión de %s", "Pedestal del despertar de %s", "Recipiente de esencia de %s", "Reprocesador de semillas de %s", "Horno de %s");
        crystalNames(lang, "Cristal de %s", TIER_NAMES_ES);
        lang.add(TierCrystalItem.TOOLTIP_KEY, "Se usa para esencias de nivel %s");
        lang.add(TierTooltips.SPEED_KEY, "Velocidad: x%s");
        lang.add(TierTooltips.FUEL_KEY, "Consumo de combustible: x%s");
        lang.add(TierTooltips.SINGLE_KEY, "Solo hace semillas de nivel %s");
        lang.add(TierTooltips.RANGE_KEY, "Hace semillas de nivel %s y de nivel %s");
        lang.add(TierTooltips.PART_KEY, "Sirve para altares hasta nivel %s");
        lang.add(UmcFractalTabs.sectionKey("seeds"), "Semillas");
        lang.add(UmcFractalTabs.sectionKey("altars"), "Altares");
        lang.add(UmcFractalTabs.sectionKey("pedestals"), "Pedestales");
        lang.add(UmcFractalTabs.sectionKey("essences"), "Esencias");
        lang.add(UmcFractalTabs.sectionKey("crystals"), "Cristales");
        lang.add(UmcFractalTabs.sectionKey("machines"), "Maquinas");
        lang.add(UmcFractalTabs.sectionKey("tables"), "Mesas");
    }

    private static void crystalNames(LanguageProvider lang, String format, String[] tierNames) {
        List<Item> crystals = TierAltarRegistry.crystals();
        for (int tier = TierRules.MIN_TIER; tier <= TierRules.MAX_TIER; tier++) {
            Item crystal = crystals.get(tier - 1);
            lang.addItem(() -> crystal, format.formatted(tierNames[tier - 1]));
        }
    }

    private static void tierBlocks(LanguageProvider lang, String[] tierNames, String... names) {
        Block[][] groups = {
                TierAltarRegistry.infusionBlocks(), TierAltarRegistry.awakeningBlocks(),
                TierAltarRegistry.infusionPedestalBlocks(), TierAltarRegistry.awakeningPedestalBlocks(),
                TierAltarRegistry.vesselBlocks(), TierAltarRegistry.reprocessorBlocks(),
                TierAltarRegistry.furnaceBlocks()};
        for (int group = 0; group < groups.length; group++) {
            for (int tier = TierRules.MIN_TIER; tier <= TierRules.MAX_TIER; tier++) {
                lang.add(groups[group][tier - 1], names[group].formatted(tierNames[tier - 1]));
            }
        }
    }
}
