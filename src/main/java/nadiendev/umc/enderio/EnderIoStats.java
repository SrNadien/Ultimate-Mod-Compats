package nadiendev.umc.enderio;

import nadiendev.umc.core.material.UmcMaterial;

/**
 * Statistics of the EnderIO items of one material. The grinding ball has its own output and bonus multipliers, the
 * capacitor value is its base (the energy multiplier), and the conduits have their own rates.
 */
public record EnderIoStats(
        float outputMultiplier,
        float bonusMultiplier,
        int ballDurability,
        float ballPowerUse,
        float capacitorBase,
        int itemConduitRate,
        int itemConduitTicksPerCycle,
        int energyConduitRate,
        int fluidConduitRate) {

    public static EnderIoStats of(UmcMaterial material) {
        return switch (material) {
            case JOANFOITE -> new EnderIoStats(6f, 6f, 7500, 0.5f, 6f, 96, 5, 1_200_000, 20_000);
            case MUSHASHITE -> new EnderIoStats(5f, 5f, 7500, 0.5f, 5f, 64, 10, 950_000, 10_000);
            case NADIENITE -> new EnderIoStats(70f, 70f, 7500, 0.5f, 20f, 128, 1, 799_000_000, 50_000);
            // The three of Allthemodium keep the grinding balls of the ATM: Compat mod. The capacitors and the energy
            // conduits of every material are the ones asked for, the other conduit rates are free values.
            case ALLTHEMODIUM -> new EnderIoStats(1.85f, 1.75f, 2500, 0.7f, 30f, 3000, 1, 20_000_000, 60_000);
            case VIBRANIUM -> new EnderIoStats(2.0f, 1.9f, 5000, 0.6f, 40f, 4000, 1, 90_000_000, 70_000);
            case UNOBTAINIUM -> new EnderIoStats(2.25f, 2.1f, 7500, 0.5f, 50f, 5000, 1, 899_000_000, 80_000);
            // The energy conduits of the piglich do not have a limit.
            case PIGLICH -> new EnderIoStats(90f, 90f, 7500, 0.5f, 70f, 6000, 1, Integer.MAX_VALUE, 90_000);
        };
    }
}
