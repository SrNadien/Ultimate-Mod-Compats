package nadiendev.umc.solarflux;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.config.CompatSwitches;
import nadiendev.umc.core.material.UmcMaterial;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.zeith.hammerlib.compat.base.CompatContext;
import org.zeith.hammerlib.event.recipe.RegisterRecipesEvent;
import org.zeith.solarflux.compat._base.SolarFluxCompat;
import org.zeith.solarflux.init.ItemsSF;
import org.zeith.solarflux.init.SolarPanelsSF;
import org.zeith.solarflux.panels.SolarPanel;

/**
 * Registers in Solar Flux Reborn the solar panels of the materials of one mod. The panel of each material needs the
 * mirror and the coating of that material, and the panel of the material before it: the first one needs the panel of
 * tier 8 of Solar Flux Reborn and the one of allthemodium needs the one of nadienite.
 */
abstract class UmcSolarFluxCompat extends SolarFluxCompat {

    private final String mod;

    protected UmcSolarFluxCompat(CompatContext context, String mod) {
        super(context);
        this.mod = mod;
    }

    private List<UmcMaterial> materials() {
        return Arrays.stream(UmcMaterial.values()).filter(material -> material.mod().equals(mod)).toList();
    }

    private static boolean active() {
        return CompatSwitches.enabled(CompatSwitches.SOLAR_FLUX);
    }

    private static ResourceLocation recipe(UmcMaterial material, String kind) {
        return ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "solarflux/" + material.id() + "_" + kind);
    }

    private static Item item(String id) {
        return BuiltInRegistries.ITEM.get(ResourceLocation.parse(id));
    }

    @Override
    public void registerSolarPanels(Supplier<SolarPanel.Builder> factory, Function<SolarPanel.Builder, SolarPanel> registrar) {
        if (!active()) {
            return;
        }
        for (UmcMaterial material : materials()) {
            SolarFluxStats stats = SolarFluxStats.of(material);
            registrar.apply(factory.get()
                    .name(SolarFluxRegistry.panelName(material))
                    .generation(stats.generation())
                    .transfer(stats.transfer())
                    .capacity(stats.capacity()));
        }
    }

    @Override
    public void indexRecipes(Consumer<ResourceLocation> recipes) {
        if (!active()) {
            return;
        }
        for (UmcMaterial material : materials()) {
            recipes.accept(recipe(material, "mirror"));
            recipes.accept(recipe(material, "coating"));
            recipes.accept(recipe(material, "panel"));
        }
    }

    @Override
    public void registerRecipes(RegisterRecipesEvent event) {
        if (!active()) {
            return;
        }
        for (UmcMaterial material : materials()) {
            UmcMaterial previous = material.previous();
            boolean chained = previous != null && previous.installed() && SolarFluxRegistry.has(previous);
            ItemLike previousMirror = chained ? SolarFluxRegistry.mirror(previous) : ItemsSF.MIRROR;
            ItemLike previousPanel = chained ? SolarFluxPanels.panel(previous) : SolarFluxPanels.tierEight();
            Item mirror = SolarFluxRegistry.mirror(material);
            Item coating = SolarFluxRegistry.coating(material);
            Item ingot = item(material.ingot());
            Item block = item(material.block());

            event.shaped()
                    .id(recipe(material, "mirror"))
                    .result(mirror, 3)
                    .shape("GGG", " I ")
                    .map('G', previousMirror)
                    .map('I', ingot)
                    .registerIf(SolarPanelsSF::isRecipeActive);

            event.shaped()
                    .id(recipe(material, "coating"))
                    .result(coating, 2)
                    .shape("M", "I", "M")
                    .map('M', mirror)
                    .map('I', ingot)
                    .registerIf(SolarPanelsSF::isRecipeActive);

            event.shaped()
                    .id(recipe(material, "panel"))
                    .result(SolarFluxPanels.panel(material), 1)
                    .shape("MCM", "BPB", "MCM")
                    .map('M', mirror)
                    .map('C', coating)
                    .map('B', block)
                    .map('P', previousPanel)
                    .registerIf(SolarPanelsSF::isRecipeActive);
        }
    }
}
