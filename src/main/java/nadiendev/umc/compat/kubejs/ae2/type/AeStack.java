package nadiendev.umc.compat.kubejs.ae2.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public record AeStack(String kind, ResourceLocation id, long amount) {

    public static final String ITEM_KIND = "ae2:i";
    public static final String FLUID_KIND = "ae2:f";

    public static final Codec<AeStack> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("#t").forGetter(AeStack::kind),
            ResourceLocation.CODEC.fieldOf("id").forGetter(AeStack::id),
            Codec.LONG.fieldOf("#").forGetter(AeStack::amount)
    ).apply(instance, AeStack::new));

    public static AeStack item(ItemStack stack) {
        return new AeStack(ITEM_KIND, BuiltInRegistries.ITEM.getKey(stack.getItem()), stack.getCount());
    }

    public static AeStack fluid(FluidStack stack) {
        return new AeStack(FLUID_KIND, BuiltInRegistries.FLUID.getKey(stack.getFluid()), stack.getAmount());
    }

    public boolean isFluid() {
        return FLUID_KIND.equals(kind);
    }
}
