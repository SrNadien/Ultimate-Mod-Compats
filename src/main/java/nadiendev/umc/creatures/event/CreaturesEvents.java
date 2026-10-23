package nadiendev.umc.creatures.event;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.creatures.config.CreaturesConfig;
import nadiendev.umc.creatures.entity.VampireEntity;
import nadiendev.umc.creatures.entity.WerewolfEntity;
import nadiendev.umc.creatures.form.CreatureCurse;
import nadiendev.umc.creatures.form.CreatureForms;
import nadiendev.umc.creatures.form.CreatureKind;
import nadiendev.umc.creatures.registry.CreaturesTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = UltimateModCompats.MODID)
public final class CreaturesEvents {

    private CreaturesEvents() {
    }

    @SubscribeEvent
    public static void playerTick(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CreaturesConfig.active()) {
            CreatureForms.tick(player);
        }
    }

    @SubscribeEvent
    public static void loggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CreaturesConfig.active()) {
            CreatureForms.refresh(player);
        }
    }

    @SubscribeEvent
    public static void respawned(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CreaturesConfig.active()) {
            CreatureForms.refresh(player);
        }
    }

    @SubscribeEvent
    public static void changedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CreaturesConfig.active()) {
            CreatureForms.refresh(player);
        }
    }

    @SubscribeEvent
    public static void clone(PlayerEvent.Clone event) {
        if (!event.isWasDeath() || !CreaturesConfig.active()) {
            return;
        }
        Player player = event.getEntity();
        CreatureCurse curse = CreatureForms.get(player);
        if (CreaturesConfig.LOSE_CURSE_ON_DEATH.get()) {
            CreatureForms.set(player, CreatureCurse.NONE);
        } else if (curse.transformed()) {
            CreatureForms.set(player, curse.withTransformed(false, false));
        }
    }

    @SubscribeEvent
    public static void incomingDamage(LivingIncomingDamageEvent event) {
        if (!CreaturesConfig.active()) {
            return;
        }
        Entity attacker = event.getSource().getEntity();
        if (!(attacker instanceof LivingEntity living) || event.getSource().getDirectEntity() != attacker) {
            return;
        }
        LivingEntity target = event.getEntity();
        if (living instanceof Player hunter && CreatureForms.isHunter(hunter) && CreatureForms.isHostileForm(target)) {
            event.setAmount(event.getAmount() * CreaturesConfig.HUNTER_DAMAGE_MULTIPLIER.get().floatValue());
        }
        if (target instanceof Player hunted && CreatureForms.isHunter(hunted) && CreatureForms.isHostileForm(living)) {
            event.setAmount(event.getAmount() * CreaturesConfig.HUNTER_DAMAGE_TAKEN_MULTIPLIER.get().floatValue());
        }
        ItemStack weapon = living.getMainHandItem();
        if (weapon.is(CreaturesTags.SILVER_WEAPONS) && CreatureForms.isKind(target, CreatureKind.WEREWOLF)) {
            event.setAmount(event.getAmount() * CreaturesConfig.WEREWOLF_SILVER_MULTIPLIER.get().floatValue());
            int seconds = CreaturesConfig.WEREWOLF_SILVER_WEAKNESS_SECONDS.get();
            if (seconds > 0) {
                target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, seconds * 20, 1), living);
            }
        }
        if (weapon.is(CreaturesTags.STAKES) && CreatureForms.isKind(target, CreatureKind.VAMPIRE)) {
            event.setAmount(event.getAmount() + CreaturesConfig.VAMPIRE_STAKE_BONUS_DAMAGE.get().floatValue());
            int seconds = CreaturesConfig.VAMPIRE_STAKE_SLOW_SECONDS.get();
            if (seconds > 0) {
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, seconds * 20, 2), living);
            }
        }
    }

    @SubscribeEvent
    public static void damaged(LivingDamageEvent.Post event) {
        if (!CreaturesConfig.active() || !CreaturesConfig.INFECTION_ENABLED.get() || event.getNewDamage() <= 0.0F) {
            return;
        }
        if (!(event.getEntity() instanceof ServerPlayer victim)) {
            return;
        }
        if (CreaturesConfig.INFECTION_SKIP_CREATIVE.get() && victim.isCreative()) {
            return;
        }
        Entity attacker = event.getSource().getEntity();
        CreatureKind kind = CreatureKind.NONE;
        double chance = 0.0;
        if (attacker instanceof WerewolfEntity werewolf && !werewolf.isServant()) {
            kind = CreatureKind.WEREWOLF;
            chance = CreaturesConfig.INFECTION_WEREWOLF_CHANCE.get();
        } else if (attacker instanceof VampireEntity vampire && !vampire.isServant()) {
            kind = CreatureKind.VAMPIRE;
            chance = CreaturesConfig.INFECTION_VAMPIRE_CHANCE.get();
        } else if (attacker instanceof Player player && CreaturesConfig.INFECTION_FROM_PLAYERS.get() && player != victim) {
            CreatureCurse source = CreatureForms.get(player);
            if (source.transformed()) {
                kind = source.kind();
                chance = kind == CreatureKind.WEREWOLF ? CreaturesConfig.INFECTION_WEREWOLF_CHANCE.get() : CreaturesConfig.INFECTION_VAMPIRE_CHANCE.get();
            }
        }
        if (kind != CreatureKind.NONE && victim.getRandom().nextDouble() < chance) {
            CreatureForms.infect(victim, kind);
        }
    }
}
