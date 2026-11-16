package nadiendev.umc.thaumaturge;

import nadiendev.umc.core.material.UmcMaterial;

/**
 * Statistics of the wand cap and the wand rod of one material.
 *
 * @param capCost        what the cap adds to the craft cost of the wand, Thaumaturge's void cap has 9 and its thaumium cap 6
 * @param visModifier    the multiplier of the vis cost of everything cast with the cap, below 1 is cheaper
 * @param rodCapacity    the capacity of the rod, in the same unit as the rods of Thaumaturge (its silverwood wand rod has 100)
 * @param rodCost        what the rod adds to the craft cost of the wand
 * @param partVis        the vis the arcane workbench takes to craft the cap and the rod
 */
public record ThaumaturgeStats(int capCost, float visModifier, int rodCapacity, int rodCost, int partVis) {

    public static ThaumaturgeStats of(UmcMaterial material) {
        return switch (material) {
            case MUSHASHITE -> new ThaumaturgeStats(10, 0.75f, 100, 20, 30);
            case JOANFOITE -> new ThaumaturgeStats(12, 0.7f, 200, 28, 45);
            case NADIENITE -> new ThaumaturgeStats(15, 0.6f, 1000, 40, 60);
            case ALLTHEMODIUM -> new ThaumaturgeStats(18, 0.55f, 2000, 55, 80);
            case VIBRANIUM -> new ThaumaturgeStats(22, 0.5f, 4000, 75, 110);
            case UNOBTAINIUM -> new ThaumaturgeStats(28, 0.45f, 8000, 100, 150);
            case PIGLICH -> new ThaumaturgeStats(35, 0.4f, 16000, 130, 200);
        };
    }
}
