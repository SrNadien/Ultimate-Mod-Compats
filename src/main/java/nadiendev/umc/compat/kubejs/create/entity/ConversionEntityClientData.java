package nadiendev.umc.compat.kubejs.create.entity;

import java.util.List;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public final class ConversionEntityClientData {

    private static volatile List<ConversionEntityRegistry.Entry> entries = List.of();

    private ConversionEntityClientData() {
    }

    public static void set(List<ConversionEntityRegistry.Entry> received) {
        entries = List.copyOf(received);
    }

    public static Optional<ResourceLocation> entityFor(List<Ingredient> inputs, ItemStack result) {
        for (ConversionEntityRegistry.Entry entry : entries) {
            if (sameInput(entry.input(), inputs) && (result.isEmpty() || ItemStack.isSameItem(entry.result(), result))) {
                return Optional.of(entry.entity());
            }
        }
        return Optional.empty();
    }

    private static boolean sameInput(Ingredient entryInput, List<Ingredient> inputs) {
        if (inputs.isEmpty()) {
            return false;
        }
        ItemStack[] expected = entryInput.getItems();
        ItemStack[] actual = inputs.get(0).getItems();
        if (expected.length == 0 || expected.length != actual.length) {
            return false;
        }
        for (int i = 0; i < expected.length; i++) {
            if (!ItemStack.isSameItem(expected[i], actual[i])) {
                return false;
            }
        }
        return true;
    }
}
