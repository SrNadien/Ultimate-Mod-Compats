package nadiendev.umc.mysticalagriculture.tier;

public final class MachineStats {

    public static final int BASE_OPERATION_TIME = 200;
    public static final int BASE_FUEL_USAGE = 20;
    public static final int BASE_FUEL_CAPACITY = 80000;

    private static final int[][] REPROCESSORS = {
            {100, 40, 120000},
            {80, 60, 180000},
            {55, 100, 300000},
            {20, 320, 420000},
            {5, 1440, 640000},
            {3, 2880, 900000},
            {2, 5760, 1200000},
            {1, 11520, 1600000},
            {1, 23040, 2400000},
            {1, 46080, 3200000}
    };

    private static final double[][] FURNACES = {
            {0.84, 0.84},
            {0.625, 0.84},
            {0.4, 0.68},
            {0.145, 0.5},
            {0.025, 0.2},
            {0.015, 0.15},
            {0.01, 0.12},
            {0.006, 0.1},
            {0.004, 0.08},
            {0.002, 0.05}
    };

    private MachineStats() {
    }

    public static TierMachine reprocessor(int tier) {
        int[] stats = REPROCESSORS[tier - 1];
        return new Stats(
                (double) stats[0] / BASE_OPERATION_TIME,
                (double) stats[1] / BASE_FUEL_USAGE,
                (double) stats[2] / BASE_FUEL_CAPACITY);
    }

    public static TierMachine furnace(int tier) {
        double[] stats = FURNACES[tier - 1];
        double reprocessorCapacity = (double) REPROCESSORS[tier - 1][2] / BASE_FUEL_CAPACITY;
        return new Stats(stats[0], stats[1] / stats[0], reprocessorCapacity);
    }

    public static int scale(int value, double multiplier) {
        return Math.max(1, (int) Math.round(value * multiplier));
    }

    private record Stats(double operationMultiplier, double fuelUsageMultiplier, double capacityMultiplier) implements TierMachine {
    }
}
