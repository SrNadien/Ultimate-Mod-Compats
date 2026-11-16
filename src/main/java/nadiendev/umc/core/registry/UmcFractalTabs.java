package nadiendev.umc.core.registry;

import de.dafuqs.fractal.api.CreativeSubTab;
import java.util.ArrayList;
import java.util.List;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.arsnouveau.ArsNouveauFractalTabs;
import nadiendev.umc.arsnouveau.ArsNouveauRegistry;
import nadiendev.umc.core.config.CompatSwitches;
import nadiendev.umc.core.witchcraft.WitchcraftModule;
import nadiendev.umc.enderio.EnderIoFractalTabs;
import nadiendev.umc.foregoing.ForegoingFractalTabs;
import nadiendev.umc.foregoing.ForegoingRegistry;
import nadiendev.umc.functionalstorage.FunctionalStorageFractalTabs;
import nadiendev.umc.functionalstorage.FunctionalStorageRegistry;
import nadiendev.umc.solarflux.SolarFluxFractalTabs;
import nadiendev.umc.solarflux.SolarFluxRegistry;
import nadiendev.umc.thaumaturge.ThaumaturgeFractalTabs;
import nadiendev.umc.thaumaturge.ThaumaturgeRegistry;
import nadiendev.umc.enderio.EnderIoRegistry;
import nadiendev.umc.mysticalagriculture.tier.MysticalFractalTabs;
import nadiendev.umc.mysticalagriculture.tier.TierAltarRegistry;
import nadiendev.umc.mysticalagriculture.tier.TierTabOrder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;

/**
 * The only creative tab of the mod, built with Fractal. It always has a witchcraft sub tab with the items of the mod
 * itself, and each compatibility adds its own sub tabs only when its mod is loaded. The example sub tab with an
 * example item is only shown when none of them is loaded, and always in the development environment.
 */
public final class UmcFractalTabs {

    public static final String TITLE_KEY = "itemGroup." + UltimateModCompats.MODID + ".main";
    public static final String WITCHCRAFT = "witchcraft";
    public static final String EXAMPLE = "example";

    private static final String MYSTICAL_AGRICULTURE = "mysticalagriculture";
    private static final String KUBEJS_NAMESPACE = "kubejs";
    private static final String ENCHANTED_PACKAGE = "net.favouriteless.enchanted.";
    private static final List<CreativeSubTab> SUBTABS = new ArrayList<>();

    private UmcFractalTabs() {
    }

    public static String sectionKey(String id) {
        return TITLE_KEY + "." + id;
    }

    static CreativeModeTab create() {
        CreativeModeTab parent = CreativeModeTab.builder()
                .title(Component.translatable(TITLE_KEY))
                .icon(() -> new ItemStack(UmcItems.TAB_ICON.get()))
                .withLabelColor(0xFFE6DCFF)
                .backgroundTexture(ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "textures/gui/container/creative_inventory/tab_space_cat.png"))
                .displayItems((parameters, output) -> {
                    output.accept(UmcItems.TAB_ICON.get(), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
                    for (CreativeSubTab subTab : SUBTABS) {
                        subTab.buildContents(parameters);
                        output.acceptAll(subTab.getSearchTabDisplayItems(), CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY);
                    }
                })
                .build();
        SUBTABS.clear();
        boolean mystical = ModList.get().isLoaded(MYSTICAL_AGRICULTURE);
        if (mystical) {
            add(MysticalFractalTabs.build(parent, TierTabOrder.Section.SEEDS));
            add(MysticalFractalTabs.build(parent, TierTabOrder.Section.ALTARS));
            add(MysticalFractalTabs.build(parent, TierTabOrder.Section.PEDESTALS));
        }
        if (WitchcraftModule.enabled()) {
            SUBTABS.add(witchcraftSubTab(parent));
        }
        if (mystical) {
            add(MysticalFractalTabs.build(parent, TierTabOrder.Section.ESSENCES));
            add(MysticalFractalTabs.build(parent, TierTabOrder.Section.CRYSTALS));
            add(MysticalFractalTabs.build(parent, TierTabOrder.Section.MACHINES));
            add(MysticalFractalTabs.build(parent, TierTabOrder.Section.TABLES));
        }
        if (CompatSwitches.enabled(CompatSwitches.ENDERIO)) {
            SUBTABS.add(EnderIoFractalTabs.build(parent));
        }
        if (CompatSwitches.enabled(CompatSwitches.INDUSTRIAL_FOREGOING)) {
            SUBTABS.add(ForegoingFractalTabs.build(parent));
        }
        if (CompatSwitches.enabled(CompatSwitches.THAUMATURGE_WANDS)) {
            SUBTABS.add(ThaumaturgeFractalTabs.build(parent));
        }
        if (CompatSwitches.enabled(CompatSwitches.ARS_NOUVEAU)) {
            SUBTABS.add(ArsNouveauFractalTabs.build(parent));
        }
        if (CompatSwitches.enabled(CompatSwitches.SOLAR_FLUX)) {
            SUBTABS.add(SolarFluxFractalTabs.build(parent));
        }
        if (CompatSwitches.enabled(CompatSwitches.FUNCTIONAL_STORAGE)) {
            SUBTABS.add(FunctionalStorageFractalTabs.build(parent));
        }
        if (SUBTABS.isEmpty() || !FMLEnvironment.production) {
            SUBTABS.add(exampleSubTab(parent));
        }
        return parent;
    }

    private static void add(CreativeSubTab subTab) {
        if (subTab != null) {
            SUBTABS.add(subTab);
        }
    }

    private static CreativeSubTab witchcraftSubTab(CreativeModeTab parent) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, WITCHCRAFT);
        return new CreativeSubTab.Builder(parent, id, Component.translatable(sectionKey(WITCHCRAFT)))
                .entries((parameters, output) -> BuiltInRegistries.ITEM.entrySet().stream()
                        .filter(entry -> entry.getKey().location().getNamespace().equals(UltimateModCompats.MODID)
                                || isScriptedWitcheryItem(entry.getKey().location().getNamespace(), entry.getValue()))
                        .map(entry -> entry.getValue())
                        .filter(item -> item != UmcItems.TAB_ICON.get())
                        .filter(item -> item != UmcItems.EXAMPLE.get())
                        .filter(item -> !TierAltarRegistry.items().contains(item))
                        .filter(item -> !EnderIoRegistry.items().contains(item))
                        .filter(item -> !ForegoingRegistry.items().contains(item))
                        .filter(item -> !ThaumaturgeRegistry.items().contains(item))
                        .filter(item -> !ArsNouveauRegistry.items().contains(item))
                        .filter(item -> !SolarFluxRegistry.items().contains(item))
                        .filter(item -> !FunctionalStorageRegistry.items().contains(item))
                        .forEach(output::accept))
                .build();
    }

    private static CreativeSubTab exampleSubTab(CreativeModeTab parent) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, EXAMPLE);
        return new CreativeSubTab.Builder(parent, id, Component.translatable(sectionKey(EXAMPLE)))
                .entries((parameters, output) -> output.accept(UmcItems.EXAMPLE.get()))
                .build();
    }

    private static boolean isScriptedWitcheryItem(String namespace, Item item) {
        if (!namespace.equals(KUBEJS_NAMESPACE)) {
            return false;
        }
        for (Class<?> type = item.getClass(); type != null; type = type.getSuperclass()) {
            if (type.getName().startsWith(ENCHANTED_PACKAGE)) {
                return true;
            }
        }
        return false;
    }
}
