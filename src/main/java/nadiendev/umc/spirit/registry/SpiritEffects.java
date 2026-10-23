package nadiendev.umc.spirit.registry;

import nadiendev.umc.spirit.effect.SpiritEffect;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class SpiritEffects {

    public static final DeferredHolder<MobEffect, MobEffect> NIGHTMARE = SpiritRegistry.MOB_EFFECTS.register("nightmare", () -> new SpiritEffect(MobEffectCategory.HARMFUL, 0x2A1A4A, 0)
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, SpiritWorld.id("effect.nightmare.speed"), -0.1D, Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.LUCK, SpiritWorld.id("effect.nightmare.luck"), -1.0D, Operation.ADD_VALUE));

    public static final DeferredHolder<MobEffect, MobEffect> RESTFUL_DREAM = SpiritRegistry.MOB_EFFECTS.register("restful_dream", () -> new SpiritEffect(MobEffectCategory.BENEFICIAL, 0x8FD8FF, 80)
            .addAttributeModifier(Attributes.MAX_HEALTH, SpiritWorld.id("effect.restful_dream.health"), 2.0D, Operation.ADD_VALUE)
            .addAttributeModifier(Attributes.LUCK, SpiritWorld.id("effect.restful_dream.luck"), 1.0D, Operation.ADD_VALUE));

    public static final Holder<MobEffect> COVEN_BOND = SpiritRegistry.MOB_EFFECTS.register("coven_bond", () -> new SpiritEffect(MobEffectCategory.BENEFICIAL, 0xB05CFF, 0)
            .addAttributeModifier(Attributes.LUCK, SpiritWorld.id("effect.coven_bond.luck"), 1.0D, Operation.ADD_VALUE)
            .addAttributeModifier(Attributes.ARMOR, SpiritWorld.id("effect.coven_bond.armor"), 2.0D, Operation.ADD_VALUE));

    private SpiritEffects() {
    }

    public static void load() {
    }
}
