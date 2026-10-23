package nadiendev.umc.spirit.dream;

import java.util.ArrayList;
import java.util.List;
import nadiendev.umc.spirit.config.SpiritConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class DreamRewards {

    private record Entry(Item item, int count, int weight) {
    }

    private DreamRewards() {
    }

    private static List<Entry> entries() {
        List<Entry> entries = new ArrayList<>();
        for (String raw : SpiritConfig.GOOD_DREAM_REWARDS.get()) {
            String[] parts = raw.split(";");
            ResourceLocation id = ResourceLocation.tryParse(parts[0].trim());
            if (id == null) {
                continue;
            }
            Item item = BuiltInRegistries.ITEM.getOptional(id).orElse(null);
            if (item == null) {
                continue;
            }
            int count = parts.length > 1 ? parse(parts[1], 1) : 1;
            int weight = parts.length > 2 ? parse(parts[2], 1) : 1;
            if (count > 0 && weight > 0) {
                entries.add(new Entry(item, count, weight));
            }
        }
        return entries;
    }

    private static int parse(String value, int fallback) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException exception) {
            return fallback;
        }
    }

    public static ItemStack roll(RandomSource random) {
        List<Entry> entries = entries();
        int total = entries.stream().mapToInt(Entry::weight).sum();
        if (total <= 0) {
            return ItemStack.EMPTY;
        }
        int pick = random.nextInt(total);
        for (Entry entry : entries) {
            pick -= entry.weight();
            if (pick < 0) {
                return new ItemStack(entry.item(), entry.count());
            }
        }
        return ItemStack.EMPTY;
    }
}
