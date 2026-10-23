package nadiendev.umc.compat.kubejs.witchery.item;

import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.favouriteless.enchanted.common.items.brews.ConsumableBrewItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BrewItemBuilder extends ItemBuilder {

    private final transient List<Supplier<MobEffectInstance>> effects = new ArrayList<>();
    private transient Consumer<LivingEntity> drinkCallback;

    public BrewItemBuilder(ResourceLocation id) {
        super(id);
        texture("enchanted:item/brew_of_love");
        this.maxStackSize = 16;
    }

    public BrewItemBuilder effect(String effect, int ticks, int amplifier) {
        effects.add(WitcheryEffects.effect(effect, ticks, amplifier));
        return this;
    }

    public BrewItemBuilder onDrink(Consumer<LivingEntity> callback) {
        this.drinkCallback = callback;
        return this;
    }

    @Override
    @HideFromJS
    public Item createObject() {
        return new ScriptedBrewItem(createItemProperties(), List.copyOf(effects), drinkCallback);
    }

    private static class ScriptedBrewItem extends ConsumableBrewItem {

        private final List<Supplier<MobEffectInstance>> effects;
        private final Consumer<LivingEntity> callback;

        ScriptedBrewItem(Properties properties, List<Supplier<MobEffectInstance>> effects, Consumer<LivingEntity> callback) {
            super(properties);
            this.effects = effects;
            this.callback = callback;
        }

        @Override
        public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
            stack.shrink(1);
            if (!level.isClientSide) {
                effects.forEach(effect -> entity.addEffect(effect.get()));
                if (callback != null) {
                    callback.accept(entity);
                }
            }
            return super.finishUsingItem(stack, level, entity);
        }
    }
}
