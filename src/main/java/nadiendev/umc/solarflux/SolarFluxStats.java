package nadiendev.umc.solarflux;

import nadiendev.umc.core.material.UmcMaterial;

/** What the solar panel of one material generates and stores, in FE (generation and transfer per tick). */
public record SolarFluxStats(long generation, long transfer, long capacity) {

    public static SolarFluxStats of(UmcMaterial material) {
        return switch (material) {
            case MUSHASHITE -> new SolarFluxStats(150_900L, 150_900L, 128_000_000L);
            case JOANFOITE -> new SolarFluxStats(450_900L, 350_900L, 290_000_000L);
            case NADIENITE -> new SolarFluxStats(550_900_000L, 1_850_900L, 590_000_000L);
            case ALLTHEMODIUM -> new SolarFluxStats(650_900_000L, 2_850_900L, 690_000_000L);
            case VIBRANIUM -> new SolarFluxStats(750_900_000L, 3_850_900L, 790_000_000L);
            case UNOBTAINIUM -> new SolarFluxStats(850_900_000L, 4_850_900L, 890_000_000L);
            case PIGLICH -> new SolarFluxStats(950_900_000L, 5_850_900L, 990_000_000L);
        };
    }
}
