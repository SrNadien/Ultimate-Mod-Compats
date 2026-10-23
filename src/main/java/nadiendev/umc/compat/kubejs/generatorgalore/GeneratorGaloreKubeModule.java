package nadiendev.umc.compat.kubejs.generatorgalore;

import dev.latvian.mods.kubejs.script.BindingRegistry;
import nadiendev.umc.compat.kubejs.UmcKubeModule;
import nadiendev.umc.compat.kubejs.generatorgalore.binding.GeneratorGaloreBinding;

public class GeneratorGaloreKubeModule implements UmcKubeModule {

    @Override
    public String modId() {
        return "generatorgalore";
    }

    @Override
    public void registerBindings(BindingRegistry bindings) {
        bindings.add("GeneratorGalore", GeneratorGaloreBinding.class);
    }
}
