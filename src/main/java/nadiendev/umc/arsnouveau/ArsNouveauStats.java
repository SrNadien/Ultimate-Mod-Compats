package nadiendev.umc.arsnouveau;

import nadiendev.umc.core.material.UmcMaterial;

/** How much source one jar of each material holds. The jar of Ars Nouveau itself holds 10,000. */
public final class ArsNouveauStats {

    /** The source the enchanting apparatus takes to turn a source jar into the jar of a material. */
    public static final int CRAFT_SOURCE = 10_000;

    private ArsNouveauStats() {
    }

    public static int capacity(UmcMaterial material) {
        return switch (material) {
            case MUSHASHITE -> 100_000;
            case JOANFOITE -> 200_000;
            case NADIENITE -> 1_000_000;
            case ALLTHEMODIUM -> 2_000_000;
            case VIBRANIUM -> 5_000_000;
            case UNOBTAINIUM -> 10_000_000;
            case PIGLICH -> 1_000_000_000;
        };
    }
}
