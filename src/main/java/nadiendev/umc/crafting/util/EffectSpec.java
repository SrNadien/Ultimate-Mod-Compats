package nadiendev.umc.crafting.util;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public record EffectSpec(ResourceLocation id, int amplifier) {

    public static EffectSpec parse(String value) {
        if (value == null) {
            return null;
        }
        int comma = value.lastIndexOf(',');
        String idPart = comma < 0 ? value : value.substring(0, comma);
        int amplifier = 0;
        if (comma >= 0) {
            try {
                amplifier = Integer.parseInt(value.substring(comma + 1).trim());
            } catch (NumberFormatException exception) {
                return null;
            }
        }
        ResourceLocation id = ResourceLocation.tryParse(idPart.trim());
        if (id == null || amplifier < 0 || amplifier > 255) {
            return null;
        }
        return new EffectSpec(id, amplifier);
    }

    public static List<EffectSpec> parseAll(List<? extends String> values) {
        List<EffectSpec> specs = new ArrayList<>();
        for (String value : values) {
            EffectSpec spec = parse(value);
            if (spec != null) {
                specs.add(spec);
            }
        }
        return specs;
    }

    public static void applyAll(LivingEntity entity, List<? extends String> values, int duration) {
        for (EffectSpec spec : parseAll(values)) {
            spec.apply(entity, duration);
        }
    }

    public void apply(LivingEntity entity, int duration) {
        BuiltInRegistries.MOB_EFFECT.getHolder(id).ifPresent(holder -> grant(entity, holder, duration));
    }

    private void grant(LivingEntity entity, Holder<MobEffect> holder, int duration) {
        MobEffectInstance current = entity.getEffect(holder);
        if (current != null && current.getAmplifier() > amplifier) {
            return;
        }
        if (current != null && current.getAmplifier() == amplifier && current.getDuration() > duration) {
            return;
        }
        entity.addEffect(new MobEffectInstance(holder, duration, amplifier, true, false, true));
    }
}
