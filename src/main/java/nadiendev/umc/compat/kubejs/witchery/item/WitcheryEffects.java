package nadiendev.umc.compat.kubejs.witchery.item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

public final class WitcheryEffects {

    private WitcheryEffects() {
    }

    public static Supplier<MobEffectInstance> effect(String id, int ticks, int amplifier) {
        ResourceLocation location = ResourceLocation.parse(id.indexOf(':') >= 0 ? id : "minecraft:" + id);
        return () -> {
            Holder<MobEffect> holder = BuiltInRegistries.MOB_EFFECT.getHolder(location)
                    .orElseThrow(() -> new IllegalStateException("El efecto " + location + " no existe"));
            return new MobEffectInstance(holder, ticks, amplifier);
        };
    }

    public static List<DamageMatcher> damageMatchers(String[] raw) {
        List<DamageMatcher> matchers = new ArrayList<>();
        for (String entry : raw) {
            if (entry.startsWith("#")) {
                matchers.add(new DamageMatcher(TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse(entry.substring(1))), null));
            } else {
                ResourceLocation location = ResourceLocation.parse(entry.indexOf(':') >= 0 ? entry : "minecraft:" + entry);
                matchers.add(new DamageMatcher(null, ResourceKey.create(Registries.DAMAGE_TYPE, location)));
            }
        }
        return matchers;
    }

    public record DamageMatcher(TagKey<DamageType> tag, ResourceKey<DamageType> key) {

        public boolean matches(DamageSource source) {
            return tag != null ? source.is(tag) : source.is(key);
        }
    }
}
