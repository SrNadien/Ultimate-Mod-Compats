package nadiendev.umc.foregoing;

import nadiendev.umc.core.material.UmcMaterial;

/**
 * The tier of the addons of one material. Range and the others scale with the tier the same way the addons of
 * Industrial Foregoing itself do: range adds the tier, speed and processing are one plus the tier, and efficiency is
 * the fraction of energy still used.
 */
public record ForegoingStats(int tier, float efficiency) {

    public static ForegoingStats of(UmcMaterial material) {
        return switch (material) {
            case MUSHASHITE -> new ForegoingStats(18, 0.14f);
            case JOANFOITE -> new ForegoingStats(19, 0.13f);
            case NADIENITE -> new ForegoingStats(20, 0.12f);
            case ALLTHEMODIUM -> new ForegoingStats(21, 0.11f);
            case VIBRANIUM -> new ForegoingStats(22, 0.10f);
            case UNOBTAINIUM -> new ForegoingStats(25, 0.07f);
            case PIGLICH -> new ForegoingStats(30, 0.05f);
        };
    }

    public float range() {
        return tier;
    }

    public float speed() {
        return 1 + tier;
    }

    public float processing() {
        return 1 + tier;
    }
}
