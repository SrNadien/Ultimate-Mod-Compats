package nadiendev.umc.crafting.event;

import java.util.ArrayList;
import java.util.List;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.crafting.config.CraftingConfig;
import nadiendev.umc.crafting.util.AltarBoost;
import nadiendev.umc.crafting.util.EffectSpec;
import nadiendev.umc.crafting.util.WitchSet;
import net.favouriteless.enchanted.api.curses.CurseInstance;
import net.favouriteless.enchanted.api.curses.CurseManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = UltimateModCompats.MODID)
public final class ClothingEvents {

    private static final ThreadLocal<Boolean> EXTENDING = ThreadLocal.withInitial(() -> false);

    private ClothingEvents() {
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        if (!CraftingConfig.ENABLED.get() || !CraftingConfig.CLOTHING_ENABLED.get()) {
            return;
        }

        int time = player.tickCount + player.getId();
        int effectInterval = CraftingConfig.CLOTHING_EFFECT_INTERVAL.get();
        int altarInterval = CraftingConfig.ALTAR_BONUS_INTERVAL.get();
        int curseInterval = CraftingConfig.CURSE_CHECK_INTERVAL.get();
        if (time % effectInterval != 0 && time % altarInterval != 0 && time % curseInterval != 0) {
            return;
        }

        WitchSet set = WitchSet.of(player);
        if (set.pieces() == 0) {
            return;
        }

        if (time % effectInterval == 0) {
            applyEffects(player, set, effectInterval);
        }
        if (time % altarInterval == 0) {
            boostAltars(player, set);
        }
        if (time % curseInterval == 0) {
            shakeCurses(player, set);
        }
    }

    private static void applyEffects(ServerPlayer player, WitchSet set, int interval) {
        int duration = Math.max(interval + 120, 260);
        if (set.hat()) {
            EffectSpec.applyAll(player, CraftingConfig.HAT_EFFECTS.get(), duration);
        }
        if (set.robe()) {
            EffectSpec.applyAll(player, CraftingConfig.ROBE_EFFECTS.get(), duration);
        }
        if (set.cloak()) {
            EffectSpec.applyAll(player, CraftingConfig.CLOAK_EFFECTS.get(), duration);
        }
        if (set.boots()) {
            EffectSpec.applyAll(player, CraftingConfig.BOOTS_EFFECTS.get(), duration);
        }
        if (set.complete()) {
            EffectSpec.applyAll(player, CraftingConfig.SET_EFFECTS.get(), duration);
        }
    }

    private static void boostAltars(ServerPlayer player, WitchSet set) {
        if (!CraftingConfig.ALTAR_BONUS_ENABLED.get() || set.pieces() < CraftingConfig.ALTAR_BONUS_MIN_PIECES.get()) {
            return;
        }
        double amount = CraftingConfig.ALTAR_BONUS_POWER.get() * set.fraction();
        AltarBoost.boost(player.serverLevel(), player.blockPosition(), CraftingConfig.ALTAR_BONUS_RADIUS.get(), amount);
    }

    private static void shakeCurses(ServerPlayer player, WitchSet set) {
        if (!CraftingConfig.CURSE_RESISTANCE_ENABLED.get() || set.pieces() < CraftingConfig.CURSE_MIN_PIECES.get()) {
            return;
        }
        ServerLevel level = player.serverLevel();
        List<CurseInstance> curses = new ArrayList<>(CurseManager.get().getCurses(player.getUUID(), level));
        double chance = CraftingConfig.CURSE_REMOVE_CHANCE.get() * set.fraction();
        for (CurseInstance curse : curses) {
            if (curse.isRemoved() || player.getRandom().nextDouble() >= chance) {
                continue;
            }
            if (curse.getStrength() > 1) {
                curse.setStrength(curse.getStrength() - 1);
            } else {
                CurseManager.get().removeCurse(curse.getCurse().type(), player.getUUID(), level);
            }
        }
    }

    @SubscribeEvent
    public static void onFall(LivingFallEvent event) {
        if (!CraftingConfig.ENABLED.get() || !CraftingConfig.CLOTHING_ENABLED.get() || !CraftingConfig.CLOAK_FALL_ENABLED.get()) {
            return;
        }
        LivingEntity entity = event.getEntity();
        if (WitchSet.of(entity).cloak()) {
            event.setDamageMultiplier((float) (event.getDamageMultiplier() * (1.0D - CraftingConfig.CLOAK_FALL_REDUCTION.get())));
        }
    }

    @SubscribeEvent
    public static void onEffectAdded(MobEffectEvent.Added event) {
        if (EXTENDING.get() || event.getEntity().level().isClientSide) {
            return;
        }
        if (!CraftingConfig.ENABLED.get() || !CraftingConfig.CLOTHING_ENABLED.get() || !CraftingConfig.POTION_BONUS_ENABLED.get()) {
            return;
        }
        MobEffectInstance added = event.getEffectInstance();
        if (added.isAmbient() || added.isInfiniteDuration() || added.getDuration() > CraftingConfig.POTION_MAX_DURATION.get()) {
            return;
        }
        if (added.getEffect().value().getCategory() != MobEffectCategory.BENEFICIAL) {
            return;
        }
        LivingEntity entity = event.getEntity();
        WitchSet set = WitchSet.of(entity);
        if (set.pieces() < CraftingConfig.POTION_MIN_PIECES.get()) {
            return;
        }
        int extended = (int) Math.min((double) CraftingConfig.POTION_MAX_DURATION.get(), added.getDuration() * CraftingConfig.POTION_DURATION_MULTIPLIER.get());
        if (extended <= added.getDuration()) {
            return;
        }
        EXTENDING.set(true);
        try {
            entity.addEffect(new MobEffectInstance(added.getEffect(), extended, added.getAmplifier(), added.isAmbient(), added.isVisible(), added.showIcon()));
        } finally {
            EXTENDING.set(false);
        }
    }
}
