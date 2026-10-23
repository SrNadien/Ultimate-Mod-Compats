package nadiendev.umc.compat.kubejs.ae2.component;

import com.mojang.serialization.Codec;
import dev.latvian.mods.kubejs.recipe.RecipeScriptContext;
import dev.latvian.mods.kubejs.recipe.component.ItemStackComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.rhino.type.TypeInfo;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nadiendev.umc.compat.kubejs.ae2.type.AeStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;

public record AeStackComponent(RecipeComponentType<?> type) implements RecipeComponent<AeStack> {

    private static final Pattern AMOUNT = Pattern.compile("^(\\d+)\\s*x\\s+(.+)$");

    @Override
    public Codec<AeStack> codec() {
        return AeStack.CODEC;
    }

    @Override
    public TypeInfo typeInfo() {
        return TypeInfo.STRING.or(TypeInfo.of(ItemStack.class)).or(TypeInfo.of(FluidStack.class)).or(TypeInfo.RAW_MAP);
    }

    @Override
    public AeStack wrap(RecipeScriptContext cx, Object from) {
        if (from instanceof AeStack stack) {
            return stack;
        }
        if (from instanceof FluidStack fluid) {
            return AeStack.fluid(fluid);
        }
        if (from instanceof ItemStack item) {
            return AeStack.item(item);
        }
        if (from instanceof CharSequence text) {
            return parse(text.toString().trim());
        }
        return AeStack.item(ItemStackComponent.ITEM_STACK.instance().wrap(cx, from));
    }

    private static AeStack parse(String text) {
        long amount = 1;
        String name = text;
        Matcher matcher = AMOUNT.matcher(text);
        if (matcher.matches()) {
            amount = Long.parseLong(matcher.group(1));
            name = matcher.group(2).trim();
        }
        ResourceLocation id = ResourceLocation.parse(name);
        if (BuiltInRegistries.ITEM.containsKey(id) && BuiltInRegistries.ITEM.get(id) != Items.AIR) {
            return new AeStack(AeStack.ITEM_KIND, id, amount);
        }
        if (BuiltInRegistries.FLUID.containsKey(id) && BuiltInRegistries.FLUID.get(id) != Fluids.EMPTY) {
            return new AeStack(AeStack.FLUID_KIND, id, amount);
        }
        throw new IllegalArgumentException("'" + id + "' no es un objeto ni un fluido registrado");
    }

    @Override
    public boolean isEmpty(AeStack value) {
        return value == null;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
