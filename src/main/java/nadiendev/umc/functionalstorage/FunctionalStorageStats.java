package nadiendev.umc.functionalstorage;

import nadiendev.umc.core.material.UmcMaterial;

/**
 * How many times one storage upgrade of a material multiplies the capacity of a drawer. Functional Storage goes from
 * x8 (copper) to x32 (netherite); the upgrades of the materials start above the netherite one.
 */
public final class FunctionalStorageStats {

    private FunctionalStorageStats() {
    }

    public static int multiplier(UmcMaterial material) {
        return switch (material) {
            case MUSHASHITE -> 64;
            case JOANFOITE -> 128;
            case NADIENITE -> 1_000;
            case ALLTHEMODIUM -> 3_000;
            case VIBRANIUM -> 7_000;
            case UNOBTAINIUM -> 13_000;
            case PIGLICH -> 30_000;
        };
    }
}
