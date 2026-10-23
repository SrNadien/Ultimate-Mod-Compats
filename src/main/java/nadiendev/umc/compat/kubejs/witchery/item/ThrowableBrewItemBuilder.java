package nadiendev.umc.compat.kubejs.witchery.item;

import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.favouriteless.enchanted.common.items.brews.ThrowableBrewItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ThrowableBrewItemBuilder extends ItemBuilder {

    private static final double DEFAULT_RADIUS = 4.0;
    private static final int DEFAULT_COLOUR = 0xFFFFFF;

    private final transient List<Supplier<MobEffectInstance>> effects = new ArrayList<>();
    private transient Consumer<BrewSplashContext> splashCallback;
    private transient double radius = DEFAULT_RADIUS;
    private transient int brewColour = DEFAULT_COLOUR;

    public ThrowableBrewItemBuilder(ResourceLocation id) {
        super(id);
        texture("enchanted:item/brew_of_love");
        this.maxStackSize = 16;
    }

    public ThrowableBrewItemBuilder effect(String effect, int ticks, int amplifier) {
        effects.add(WitcheryEffects.effect(effect, ticks, amplifier));
        return this;
    }

    public ThrowableBrewItemBuilder radius(double radius) {
        this.radius = radius;
        return this;
    }

    public ThrowableBrewItemBuilder brewColour(long colour) {
        this.brewColour = (int) colour;
        return this;
    }

    public ThrowableBrewItemBuilder onSplash(Consumer<BrewSplashContext> callback) {
        this.splashCallback = callback;
        return this;
    }

    @Override
    @HideFromJS
    public Item createObject() {
        return new ScriptedThrowableBrewItem(createItemProperties(), List.copyOf(effects), splashCallback, radius, brewColour);
    }

    private static class ScriptedThrowableBrewItem extends ThrowableBrewItem {

        private final List<Supplier<MobEffectInstance>> effects;
        private final Consumer<BrewSplashContext> callback;
        private final double radius;
        private final int colour;

        ScriptedThrowableBrewItem(Properties properties, List<Supplier<MobEffectInstance>> effects, Consumer<BrewSplashContext> callback, double radius, int colour) {
            super(properties);
            this.effects = effects;
            this.callback = callback;
            this.radius = radius;
            this.colour = colour;
        }

        @Override
        public void applyEffect(Entity owner, Level level, Vec3 pos) {
            if (level.isClientSide) {
                return;
            }
            AABB area = new AABB(pos, pos).inflate(radius);
            for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, area)) {
                effects.forEach(effect -> target.addEffect(effect.get()));
            }
            if (callback != null) {
                callback.accept(new BrewSplashContext(owner, level, pos));
            }
        }

        @Override
        public int getColour() {
            return colour;
        }
    }
}
