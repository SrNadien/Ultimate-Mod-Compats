package nadiendev.umc.compat.kubejs.generatorgalore.binding;

import nadiendev.umc.compat.kubejs.generatorgalore.fuel.GeneratorFuelMaps;

public final class GeneratorGaloreBinding {

    private GeneratorGaloreBinding() {
    }

    public static GeneratorFuelMaps fuels() {
        return new GeneratorFuelMaps();
    }
}
