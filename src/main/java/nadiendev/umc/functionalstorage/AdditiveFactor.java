package nadiendev.umc.functionalstorage;

import com.buuz135.functionalstorage.item.component.SizeProvider;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.text.DecimalFormat;
import net.minecraft.network.chat.Component;

/**
 * A storage factor that adds up instead of multiplying. The factors of Functional Storage multiply each other, which is
 * fine for x8 to x32 but goes past the largest number a drawer can hold with a few upgrades of x1000 and more. Alone it
 * is the same as a factor of {@code factor}, and each one that comes after adds {@code factor - 1} to the total.
 */
public record AdditiveFactor(float factor) implements SizeProvider {

    /** The name it is registered under in {@link SizeProvider#REGISTRY}. */
    public static final String NAME = "umc_additive_factor";
    public static final MapCodec<AdditiveFactor> CODEC = Codec.floatRange(0, Float.MAX_VALUE)
            .fieldOf("factor")
            .xmap(AdditiveFactor::new, AdditiveFactor::factor);
    private static final DecimalFormat TWO_DECIMALS = new DecimalFormat("#0.00");

    /** Has to be called before any stack with this factor is read or written. */
    static void register() {
        SizeProvider.REGISTRY.putIfAbsent(NAME, CODEC);
    }

    @Override
    public float applyFactorModifier(float source) {
        return source + (factor - 1);
    }

    @Override
    public Component getTooltip(Component object) {
        if (factor < 1) {
            return Component.translatable("storageupgrade.desc.modify_factor_div", object, format(1 / factor));
        }
        return Component.translatable("storageupgrade.desc.modify_factor_mult", object, format(factor));
    }

    private static String format(float value) {
        if (Integer.valueOf((int) value).floatValue() == value) {
            return String.valueOf((int) value);
        }
        return TWO_DECIMALS.format(value);
    }

    @Override
    public MapCodec<? extends SizeProvider> codec() {
        return CODEC;
    }
}