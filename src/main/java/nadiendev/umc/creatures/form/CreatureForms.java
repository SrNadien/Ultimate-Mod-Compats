package nadiendev.umc.creatures.form;

import java.util.List;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.creatures.config.CreaturesConfig;
import nadiendev.umc.creatures.registry.CreaturesAttachments;
import nadiendev.umc.creatures.registry.CreaturesSounds;
import nadiendev.umc.creatures.registry.CreaturesTags;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class CreatureForms {

    private static final String POUNCE_KEY = "umc_pounce";
    private static final int NIGHT_VISION_TICKS = 260;
    private static final String[] MODIFIER_NAMES = {"health", "damage", "speed", "jump", "step", "knockback", "fall"};
    private static final List<Holder<Attribute>> MODIFIED = List.of(Attributes.MAX_HEALTH, Attributes.ATTACK_DAMAGE, Attributes.MOVEMENT_SPEED, Attributes.JUMP_STRENGTH, Attributes.STEP_HEIGHT, Attributes.KNOCKBACK_RESISTANCE, Attributes.SAFE_FALL_DISTANCE);

    private CreatureForms() {
    }

    public static CreatureCurse get(Player player) {
        return player.getData(CreaturesAttachments.CURSE.get());
    }

    public static void set(Player player, CreatureCurse curse) {
        player.setData(CreaturesAttachments.CURSE.get(), curse);
    }

    public static boolean isHunter(Player player) {
        return CreaturesConfig.active() && CreaturesConfig.HUNTER_ENABLED.get() && player.getData(CreaturesAttachments.HUNTER.get());
    }

    public static void setHunter(Player player, boolean hunter) {
        player.setData(CreaturesAttachments.HUNTER.get(), hunter);
    }

    public static boolean isHostileForm(Entity entity) {
        return isKind(entity, CreatureKind.WEREWOLF) || isKind(entity, CreatureKind.VAMPIRE);
    }

    public static boolean isKindEnabled(CreatureKind kind) {
        if (!CreaturesConfig.active()) {
            return false;
        }
        return switch (kind) {
            case WEREWOLF -> CreaturesConfig.WEREWOLF_ENABLED.get();
            case VAMPIRE -> CreaturesConfig.VAMPIRE_ENABLED.get();
            case NONE -> false;
        };
    }

    public static boolean isKind(Entity entity, CreatureKind kind) {
        if (entity instanceof Player player) {
            return get(player).isTransformed(kind);
        }
        return switch (kind) {
            case WEREWOLF -> entity.getType().is(CreaturesTags.WEREWOLVES);
            case VAMPIRE -> entity.getType().is(CreaturesTags.VAMPIRES);
            case NONE -> false;
        };
    }

    public static boolean infect(ServerPlayer player, CreatureKind kind) {
        if (!isKindEnabled(kind)) {
            return false;
        }
        if (CreaturesConfig.HUNTER_INFECTION_IMMUNE.get() && isHunter(player)) {
            return false;
        }
        CreatureCurse curse = get(player);
        if (curse.isCursed()) {
            return false;
        }
        set(player, curse.withKind(kind));
        player.displayClientMessage(Component.translatable("message.umc.cursed." + kind.getSerializedName()), false);
        ServerLevel level = player.serverLevel();
        SoundEvent sound = kind == CreatureKind.WEREWOLF ? CreaturesSounds.WEREWOLF_HOWL.get() : CreaturesSounds.VAMPIRE_BITE.get();
        level.playSound(null, player.blockPosition(), sound, SoundSource.PLAYERS, 1.0F, 1.0F);
        level.sendParticles(ParticleTypes.SOUL, player.getX(), player.getY() + 1.0, player.getZ(), 12, 0.4, 0.6, 0.4, 0.02);
        return true;
    }

    public static boolean cure(ServerPlayer player) {
        CreatureCurse curse = get(player);
        if (!curse.isCursed()) {
            return false;
        }
        CreatureKind kind = curse.kind();
        if (curse.transformed()) {
            revert(player);
        }
        clearModifiers(player);
        set(player, CreatureCurse.NONE);
        player.displayClientMessage(Component.translatable("message.umc.cured." + kind.getSerializedName()), false);
        ServerLevel level = player.serverLevel();
        level.playSound(null, player.blockPosition(), SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 1.0F, 1.2F);
        level.sendParticles(ParticleTypes.END_ROD, player.getX(), player.getY() + 1.0, player.getZ(), 20, 0.4, 0.7, 0.4, 0.05);
        return true;
    }

    public static boolean toggle(ServerPlayer player) {
        CreatureCurse curse = get(player);
        if (!curse.isCursed() || !isKindEnabled(curse.kind())) {
            return false;
        }
        long now = player.level().getGameTime();
        if (now < curse.transformReadyAt()) {
            player.displayClientMessage(Component.translatable("message.umc.transform.cooldown", (curse.transformReadyAt() - now + 19) / 20), true);
            return false;
        }
        if (curse.transformed()) {
            revert(player);
            return true;
        }
        if (curse.kind() == CreatureKind.WEREWOLF && CreaturesConfig.WEREWOLF_REQUIRE_NIGHT.get() && !player.level().isNight()) {
            player.displayClientMessage(Component.translatable("message.umc.transform.needs_night"), true);
            return false;
        }
        transform(player, false);
        return true;
    }

    private static int transformCooldown(CreatureKind kind) {
        return kind == CreatureKind.WEREWOLF ? CreaturesConfig.WEREWOLF_TRANSFORM_COOLDOWN_SECONDS.get() : CreaturesConfig.VAMPIRE_TRANSFORM_COOLDOWN_SECONDS.get();
    }

    public static void transform(ServerPlayer player, boolean forced) {
        CreatureCurse curse = get(player);
        if (!curse.isCursed() || curse.transformed()) {
            return;
        }
        long now = player.level().getGameTime();
        set(player, curse.withTransformed(true, forced).withTransformReadyAt(now + transformCooldown(curse.kind()) * 20L));
        refresh(player);
        ServerLevel level = player.serverLevel();
        SoundEvent sound = curse.kind() == CreatureKind.WEREWOLF ? CreaturesSounds.WEREWOLF_HOWL.get() : CreaturesSounds.VAMPIRE_TRANSFORM.get();
        level.playSound(null, player.blockPosition(), sound, SoundSource.PLAYERS, 1.4F, 1.0F);
        level.sendParticles(ParticleTypes.LARGE_SMOKE, player.getX(), player.getY() + 1.0, player.getZ(), 24, 0.4, 0.8, 0.4, 0.04);
        player.displayClientMessage(Component.translatable("message.umc.transform." + curse.kind().getSerializedName()), true);
    }

    public static void revert(ServerPlayer player) {
        CreatureCurse curse = get(player);
        if (!curse.transformed()) {
            return;
        }
        long now = player.level().getGameTime();
        set(player, curse.withTransformed(false, false).withTransformReadyAt(now + transformCooldown(curse.kind()) * 20L));
        clearModifiers(player);
        MobEffectInstance vision = player.getEffect(MobEffects.NIGHT_VISION);
        if (vision != null && vision.isAmbient() && vision.getDuration() <= NIGHT_VISION_TICKS) {
            player.removeEffect(MobEffects.NIGHT_VISION);
        }
        ServerLevel level = player.serverLevel();
        level.sendParticles(ParticleTypes.POOF, player.getX(), player.getY() + 1.0, player.getZ(), 16, 0.4, 0.8, 0.4, 0.03);
        player.displayClientMessage(Component.translatable("message.umc.transform.revert"), true);
    }

    public static void refresh(ServerPlayer player) {
        clearModifiers(player);
        CreatureCurse curse = get(player);
        if (!curse.transformed() || !isKindEnabled(curse.kind())) {
            return;
        }
        if (curse.kind() == CreatureKind.WEREWOLF) {
            put(player, Attributes.MAX_HEALTH, curse.kind(), "health", CreaturesConfig.WEREWOLF_HEALTH_BONUS.get(), AttributeModifier.Operation.ADD_VALUE);
            put(player, Attributes.ATTACK_DAMAGE, curse.kind(), "damage", CreaturesConfig.WEREWOLF_DAMAGE_BONUS.get(), AttributeModifier.Operation.ADD_VALUE);
            put(player, Attributes.MOVEMENT_SPEED, curse.kind(), "speed", CreaturesConfig.WEREWOLF_SPEED_BONUS.get(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            put(player, Attributes.JUMP_STRENGTH, curse.kind(), "jump", CreaturesConfig.WEREWOLF_JUMP_BONUS.get(), AttributeModifier.Operation.ADD_VALUE);
            put(player, Attributes.STEP_HEIGHT, curse.kind(), "step", CreaturesConfig.WEREWOLF_STEP_BONUS.get(), AttributeModifier.Operation.ADD_VALUE);
            put(player, Attributes.KNOCKBACK_RESISTANCE, curse.kind(), "knockback", CreaturesConfig.WEREWOLF_KNOCKBACK_RESISTANCE.get(), AttributeModifier.Operation.ADD_VALUE);
        } else if (curse.kind() == CreatureKind.VAMPIRE) {
            put(player, Attributes.MAX_HEALTH, curse.kind(), "health", CreaturesConfig.VAMPIRE_HEALTH_BONUS.get(), AttributeModifier.Operation.ADD_VALUE);
            put(player, Attributes.ATTACK_DAMAGE, curse.kind(), "damage", CreaturesConfig.VAMPIRE_DAMAGE_BONUS.get(), AttributeModifier.Operation.ADD_VALUE);
            put(player, Attributes.MOVEMENT_SPEED, curse.kind(), "speed", CreaturesConfig.VAMPIRE_SPEED_BONUS.get(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            put(player, Attributes.JUMP_STRENGTH, curse.kind(), "jump", CreaturesConfig.VAMPIRE_JUMP_BONUS.get(), AttributeModifier.Operation.ADD_VALUE);
            put(player, Attributes.SAFE_FALL_DISTANCE, curse.kind(), "fall", CreaturesConfig.VAMPIRE_SAFE_FALL_BONUS.get(), AttributeModifier.Operation.ADD_VALUE);
        }
    }

    private static ResourceLocation modifierId(CreatureKind kind, String name) {
        return ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, kind.getSerializedName() + "_" + name);
    }

    private static void put(Player player, Holder<Attribute> attribute, CreatureKind kind, String name, double amount, AttributeModifier.Operation operation) {
        AttributeInstance instance = player.getAttribute(attribute);
        if (instance == null || amount == 0.0) {
            return;
        }
        ResourceLocation id = modifierId(kind, name);
        instance.removeModifier(id);
        instance.addTransientModifier(new AttributeModifier(id, amount, operation));
    }

    private static void clearModifiers(Player player) {
        for (CreatureKind kind : CreatureKind.values()) {
            if (kind == CreatureKind.NONE) {
                continue;
            }
            for (int i = 0; i < MODIFIED.size(); i++) {
                AttributeInstance instance = player.getAttribute(MODIFIED.get(i));
                if (instance != null) {
                    instance.removeModifier(modifierId(kind, MODIFIER_NAMES[i]));
                }
            }
        }
    }

    private static boolean hasModifiers(Player player, CreatureKind kind) {
        double bonus = kind == CreatureKind.WEREWOLF ? CreaturesConfig.WEREWOLF_HEALTH_BONUS.get() : CreaturesConfig.VAMPIRE_HEALTH_BONUS.get();
        if (bonus == 0.0) {
            return true;
        }
        AttributeInstance instance = player.getAttribute(Attributes.MAX_HEALTH);
        return instance != null && instance.hasModifier(modifierId(kind, "health"));
    }

    public static void tick(ServerPlayer player) {
        CreatureCurse curse = get(player);
        if (!curse.isCursed()) {
            return;
        }
        if (!isKindEnabled(curse.kind())) {
            if (curse.transformed()) {
                revert(player);
            }
            return;
        }
        ServerLevel level = player.serverLevel();
        tickPounce(player, level);
        if (player.tickCount % 20 != 0) {
            return;
        }
        if (curse.transformed()) {
            tickTransformed(player, level, curse);
        } else if (curse.kind() == CreatureKind.WEREWOLF) {
            tickMoon(player, level, curse);
        }
    }

    private static void tickTransformed(ServerPlayer player, ServerLevel level, CreatureCurse curse) {
        if (!hasModifiers(player, curse.kind())) {
            refresh(player);
        }
        if (curse.kind() == CreatureKind.WEREWOLF) {
            if (CreaturesConfig.WEREWOLF_NIGHT_VISION.get()) {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, NIGHT_VISION_TICKS, 0, true, false, false));
            }
            player.causeFoodExhaustion(CreaturesConfig.WEREWOLF_EXHAUSTION.get().floatValue());
            if (curse.forced() && CreaturesConfig.WEREWOLF_REVERT_AT_DAWN.get() && level.isDay()) {
                revert(player);
                return;
            }
            applyRepellent(player, level, CreaturesConfig.WEREWOLF_REPELLENT_ITEM.get(), CreaturesConfig.WEREWOLF_REPELLENT_RADIUS.get(), CreaturesConfig.WEREWOLF_REPELLENT_WEAKNESS_SECONDS.get());
        } else {
            if (CreaturesConfig.VAMPIRE_NIGHT_VISION.get()) {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, NIGHT_VISION_TICKS, 0, true, false, false));
            }
            player.causeFoodExhaustion(CreaturesConfig.VAMPIRE_EXHAUSTION.get().floatValue());
            if (CreaturesConfig.VAMPIRE_SUNLIGHT_BURNS.get() && isBurningInSun(player, level, CreaturesConfig.VAMPIRE_HELMET_PROTECTS.get(), CreaturesConfig.VAMPIRE_RAIN_PROTECTS.get())) {
                player.igniteForSeconds(CreaturesConfig.VAMPIRE_SUNLIGHT_FIRE_SECONDS.get());
            }
            applyRepellent(player, level, CreaturesConfig.VAMPIRE_REPELLENT_ITEM.get(), CreaturesConfig.VAMPIRE_REPELLENT_RADIUS.get(), CreaturesConfig.VAMPIRE_REPELLENT_WEAKNESS_SECONDS.get());
        }
    }

    private static void tickMoon(ServerPlayer player, ServerLevel level, CreatureCurse curse) {
        if (!CreaturesConfig.WEREWOLF_FORCE_FULL_MOON.get() || level.dimension() != Level.OVERWORLD) {
            return;
        }
        if (!level.isNight() || level.getMoonPhase() != 0) {
            return;
        }
        long day = level.getDayTime() / 24000L;
        if (curse.forcedDay() == day) {
            return;
        }
        set(player, curse.withForcedDay(day));
        transform(player, true);
    }

    public static boolean isBurningInSun(LivingEntity entity, Level level, boolean helmetProtects, boolean rainProtects) {
        if (level.isClientSide || !level.isDay()) {
            return false;
        }
        if (entity.isInWater() || (rainProtects && entity.isInWaterRainOrBubble())) {
            return false;
        }
        if (helmetProtects && !entity.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
            return false;
        }
        if (rainProtects && level.isRaining()) {
            return false;
        }
        return level.canSeeSky(entity.blockPosition().above((int) Math.floor(entity.getBbHeight())));
    }

    public static boolean holdsItem(LivingEntity entity, String id) {
        return isItem(entity.getMainHandItem(), id) || isItem(entity.getOffhandItem(), id);
    }

    private static boolean isItem(ItemStack stack, String id) {
        return !stack.isEmpty() && BuiltInRegistries.ITEM.getKey(stack.getItem()).toString().equals(id);
    }

    public static boolean isNearRepellent(LivingEntity entity, String itemId, double radius) {
        if (radius <= 0.0) {
            return false;
        }
        AABB area = entity.getBoundingBox().inflate(radius);
        for (Player other : entity.level().getEntitiesOfClass(Player.class, area)) {
            if (holdsItem(other, itemId) && other.distanceToSqr(entity) <= radius * radius) {
                return true;
            }
        }
        return false;
    }

    public static void applyRepellent(LivingEntity entity, ServerLevel level, String itemId, double radius, int seconds) {
        if (seconds <= 0 || !isNearRepellent(entity, itemId, radius)) {
            return;
        }
        entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, seconds * 20, 1));
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, seconds * 20, 0));
        level.sendParticles(ParticleTypes.SMOKE, entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ(), 6, 0.3, 0.5, 0.3, 0.01);
    }

    public static boolean useAbility(ServerPlayer player) {
        CreatureCurse curse = get(player);
        if (!curse.transformed() || !isKindEnabled(curse.kind())) {
            return false;
        }
        long now = player.level().getGameTime();
        if (now < curse.abilityReadyAt()) {
            player.displayClientMessage(Component.translatable("message.umc.ability.cooldown", (curse.abilityReadyAt() - now + 19) / 20), true);
            return false;
        }
        boolean used = curse.kind() == CreatureKind.WEREWOLF ? pounce(player) : drain(player);
        if (used) {
            int cooldown = curse.kind() == CreatureKind.WEREWOLF ? CreaturesConfig.WEREWOLF_ABILITY_COOLDOWN_SECONDS.get() : CreaturesConfig.VAMPIRE_ABILITY_COOLDOWN_SECONDS.get();
            set(player, get(player).withAbilityReadyAt(now + cooldown * 20L));
        }
        return used;
    }

    private static boolean pounce(ServerPlayer player) {
        Vec3 look = player.getLookAngle();
        Vec3 push = new Vec3(look.x, Math.max(0.3, look.y * 0.5 + 0.3), look.z).normalize().scale(CreaturesConfig.WEREWOLF_POUNCE_STRENGTH.get());
        player.setDeltaMovement(push);
        player.hurtMarked = true;
        player.fallDistance = 0.0F;
        CompoundTag data = player.getPersistentData();
        data.putLong(POUNCE_KEY, player.level().getGameTime());
        player.serverLevel().playSound(null, player.blockPosition(), CreaturesSounds.WEREWOLF_HOWL.get(), SoundSource.PLAYERS, 1.2F, 1.2F);
        return true;
    }

    private static void tickPounce(ServerPlayer player, ServerLevel level) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(POUNCE_KEY)) {
            return;
        }
        long started = data.getLong(POUNCE_KEY);
        long now = level.getGameTime();
        if (now - started > 120L) {
            data.remove(POUNCE_KEY);
            return;
        }
        if (now - started < 4L || !(player.onGround() || player.isInWater())) {
            return;
        }
        data.remove(POUNCE_KEY);
        double radius = CreaturesConfig.WEREWOLF_POUNCE_RADIUS.get();
        float damage = CreaturesConfig.WEREWOLF_POUNCE_DAMAGE.get().floatValue();
        DamageSource source = player.damageSources().playerAttack(player);
        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(radius), entity -> entity != player && entity.isAlive() && !player.isAlliedTo(entity))) {
            target.hurt(source, damage);
            Vec3 away = target.position().subtract(player.position()).normalize();
            target.push(away.x * 0.6, 0.35, away.z * 0.6);
            target.hurtMarked = true;
        }
        level.sendParticles(ParticleTypes.CRIT, player.getX(), player.getY() + 0.2, player.getZ(), 30, radius * 0.5, 0.1, radius * 0.5, 0.2);
        level.playSound(null, player.blockPosition(), SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 0.8F, 1.4F);
    }

    private static boolean drain(ServerPlayer player) {
        double range = CreaturesConfig.VAMPIRE_DRAIN_RANGE.get();
        Vec3 eye = player.getEyePosition();
        Vec3 look = player.getLookAngle();
        LivingEntity best = null;
        double bestDistance = Double.MAX_VALUE;
        for (LivingEntity candidate : player.serverLevel().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(range), entity -> entity != player && entity.isAlive() && !player.isAlliedTo(entity))) {
            Vec3 to = candidate.getBoundingBox().getCenter().subtract(eye);
            double distance = to.length();
            if (distance > range || to.normalize().dot(look) < 0.75) {
                continue;
            }
            if (distance < bestDistance) {
                bestDistance = distance;
                best = candidate;
            }
        }
        if (best == null) {
            player.displayClientMessage(Component.translatable("message.umc.ability.no_target"), true);
            return false;
        }
        float amount = CreaturesConfig.VAMPIRE_DRAIN_DAMAGE.get().floatValue();
        float before = best.getHealth();
        best.hurt(player.damageSources().playerAttack(player), amount);
        float dealt = Math.max(0.0F, before - best.getHealth());
        player.heal((float) (dealt * CreaturesConfig.VAMPIRE_DRAIN_HEAL_FACTOR.get()));
        int food = CreaturesConfig.VAMPIRE_DRAIN_FOOD.get();
        if (food > 0) {
            player.getFoodData().eat(food, 0.4F);
        }
        ServerLevel level = player.serverLevel();
        level.playSound(null, player.blockPosition(), CreaturesSounds.VAMPIRE_BITE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        level.sendParticles(ParticleTypes.DAMAGE_INDICATOR, best.getX(), best.getY() + best.getBbHeight() * 0.6, best.getZ(), 8, 0.2, 0.3, 0.2, 0.1);
        level.sendParticles(ParticleTypes.CRIMSON_SPORE, player.getX(), player.getY() + 1.2, player.getZ(), 10, 0.3, 0.4, 0.3, 0.02);
        return true;
    }
}
