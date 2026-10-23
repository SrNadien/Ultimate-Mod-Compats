package nadiendev.umc.compat.kubejs.create.entity;

import com.mojang.serialization.JsonOps;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public final class ConversionEntityRegistry {

    private static final List<Entry> ENTRIES = new ArrayList<>();

    private ConversionEntityRegistry() {
    }

    public static synchronized void add(ResourceLocation entity, Ingredient input, ItemStack result) {
        Entry entry = new Entry(entity, input, result);
        ENTRIES.removeIf(existing -> existing.sameAs(entry));
        ENTRIES.add(entry);
    }

    public static synchronized List<Entry> entries() {
        return List.copyOf(ENTRIES);
    }

    public static synchronized void clear() {
        ENTRIES.clear();
    }

    public record Entry(ResourceLocation entity, Ingredient input, ItemStack result) {

        public static final StreamCodec<RegistryFriendlyByteBuf, Entry> STREAM_CODEC = StreamCodec.composite(
                ResourceLocation.STREAM_CODEC, Entry::entity,
                Ingredient.CONTENTS_STREAM_CODEC, Entry::input,
                ItemStack.STREAM_CODEC, Entry::result,
                Entry::new
        );

        boolean sameAs(Entry other) {
            return entity.equals(other.entity)
                    && encoded(input).equals(encoded(other.input))
                    && ItemStack.matches(result, other.result);
        }

        private static String encoded(Ingredient ingredient) {
            return Ingredient.CODEC_NONEMPTY.encodeStart(JsonOps.INSTANCE, ingredient).getOrThrow().toString();
        }
    }
}
