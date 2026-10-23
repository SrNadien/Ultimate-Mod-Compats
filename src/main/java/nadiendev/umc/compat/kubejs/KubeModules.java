package nadiendev.umc.compat.kubejs;

import java.util.List;
import nadiendev.umc.compat.kubejs.advancedae.AdvancedAeKubeModule;
import nadiendev.umc.compat.kubejs.ae2.Ae2KubeModule;
import nadiendev.umc.compat.kubejs.config.KubeCompatConfig;
import nadiendev.umc.compat.kubejs.create.CreateKubeModule;
import nadiendev.umc.compat.kubejs.excessiveutilities.ExcessiveUtilitiesKubeModule;
import nadiendev.umc.compat.kubejs.extendedae.ExtendedAeKubeModule;
import nadiendev.umc.compat.kubejs.functionalstorage.FunctionalStorageKubeModule;
import nadiendev.umc.compat.kubejs.generatorgalore.GeneratorGaloreKubeModule;
import nadiendev.umc.compat.kubejs.mysticalagriculture.MysticalAgricultureKubeModule;
import nadiendev.umc.compat.kubejs.thaumaturge.ThaumaturgeKubeModule;
import nadiendev.umc.compat.kubejs.witchery.WitcheryKubeModule;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.LoadingModList;

public final class KubeModules {

    private static final boolean REGISTER_ALL = Boolean.getBoolean("umc.kubejs.registerAll");

    private static final List<UmcKubeModule> ALL = List.of(
            new WitcheryKubeModule(),
            new ThaumaturgeKubeModule(),
            new Ae2KubeModule(),
            new AdvancedAeKubeModule(),
            new ExtendedAeKubeModule(),
            new CreateKubeModule(),
            new GeneratorGaloreKubeModule(),
            new ExcessiveUtilitiesKubeModule(),
            new FunctionalStorageKubeModule(),
            new MysticalAgricultureKubeModule()
    );

    private KubeModules() {
    }

    public static List<UmcKubeModule> enabled() {
        return ALL.stream()
                .filter(module -> KubeCompatConfig.moduleEnabled(module.modId()))
                .filter(module -> REGISTER_ALL || isLoaded(module.modId()))
                .toList();
    }

    private static boolean isLoaded(String modId) {
        if (ModList.get() != null) {
            return ModList.get().isLoaded(modId);
        }
        return LoadingModList.get().getModFileById(modId) != null;
    }
}
