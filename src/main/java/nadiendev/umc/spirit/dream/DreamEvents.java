package nadiendev.umc.spirit.dream;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.spirit.block.DreamLoomBlock;
import nadiendev.umc.spirit.config.SpiritConfig;
import nadiendev.umc.spirit.entity.Nightmare;
import nadiendev.umc.spirit.registry.SpiritBlocks;
import nadiendev.umc.spirit.registry.SpiritEffects;
import nadiendev.umc.spirit.registry.SpiritEntities;
import nadiendev.umc.spirit.registry.SpiritWorld;
import nadiendev.umc.spirit.world.SpiritScheduler;
import nadiendev.umc.spirit.world.SpiritTravel;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerWakeUpEvent;

@EventBusSubscriber(modid = UltimateModCompats.MODID)
public final class DreamEvents {

    private DreamEvents() {
    }

    private record Surroundings(int catchers, BlockPos loom) {
    }

    @SubscribeEvent
    public static void onWake(PlayerWakeUpEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || !player.isSleepingLongEnough()) {
            return;
        }
        if (!SpiritConfig.ENABLED.get() || !SpiritConfig.DREAMS_ENABLED.get() || player.level().dimension() == SpiritWorld.LEVEL) {
            return;
        }
        ServerLevel level = player.serverLevel();
        BlockPos bed = player.getSleepingPos().orElse(player.blockPosition());
        Surroundings around = scan(level, bed);
        boolean woven = around.loom() != null;
        RandomSource random = player.getRandom();

        double nightmareChance = woven ? 0.0D : SpiritConfig.NIGHTMARE_CHANCE.get() * Math.pow(SpiritConfig.CATCHER_NIGHTMARE_MULTIPLIER.get(), around.catchers());
        double goodChance = SpiritConfig.GOOD_DREAM_CHANCE.get() + around.catchers() * SpiritConfig.CATCHER_GOOD_DREAM_BONUS.get() + (woven ? SpiritConfig.LOOM_GOOD_DREAM_BONUS.get() : 0.0D);

        if (woven) {
            consumeThread(level, around.loom());
        }
        if (random.nextDouble() < nightmareChance) {
            nightmare(level, player, bed);
        } else if (random.nextDouble() < Math.min(1.0D, goodChance)) {
            goodDream(level, player, bed);
        }
    }

    private static Surroundings scan(ServerLevel level, BlockPos bed) {
        int radius = SpiritConfig.DREAM_SCAN_RADIUS.get();
        int max = SpiritConfig.MAX_CATCHERS.get();
        int catchers = 0;
        BlockPos loom = null;
        double loomDistance = Double.MAX_VALUE;
        for (BlockPos pos : BlockPos.betweenClosed(bed.offset(-radius, -radius, -radius), bed.offset(radius, radius, radius))) {
            BlockState state = level.getBlockState(pos);
            if (state.is(SpiritBlocks.DREAM_CATCHER.get())) {
                if (catchers < max) {
                    catchers++;
                }
            } else if (SpiritConfig.LOOM_ENABLED.get() && state.is(SpiritBlocks.DREAM_LOOM.get()) && state.getValue(DreamLoomBlock.THREADS) > 0) {
                double distance = pos.distSqr(bed);
                if (distance < loomDistance) {
                    loomDistance = distance;
                    loom = pos.immutable();
                }
            }
        }
        return new Surroundings(catchers, loom);
    }

    private static void consumeThread(ServerLevel level, BlockPos loom) {
        BlockState state = level.getBlockState(loom);
        if (state.is(SpiritBlocks.DREAM_LOOM.get())) {
            level.setBlock(loom, state.setValue(DreamLoomBlock.THREADS, Math.max(0, state.getValue(DreamLoomBlock.THREADS) - 1)), 3);
        }
    }

    private static void goodDream(ServerLevel level, ServerPlayer player, BlockPos bed) {
        player.sendSystemMessage(Component.translatable("message.umc.good_dream").withStyle(ChatFormatting.AQUA));
        int seconds = SpiritConfig.RESTFUL_EFFECT_SECONDS.get();
        if (seconds > 0) {
            player.addEffect(new MobEffectInstance(SpiritEffects.RESTFUL_DREAM, seconds * 20, 0));
        }
        int xp = SpiritConfig.GOOD_DREAM_XP.get();
        if (xp > 0) {
            player.giveExperiencePoints(xp);
        }
        ItemStack reward = DreamRewards.roll(player.getRandom());
        if (!reward.isEmpty() && !player.addItem(reward)) {
            player.drop(reward, false);
        }
        level.playSound(null, bed, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.0F, 1.0F);
        if (SpiritConfig.WORLD_ENABLED.get() && player.getRandom().nextDouble() < SpiritConfig.JOURNEY_CHANCE.get()) {
            long duration = SpiritConfig.JOURNEY_SECONDS.get() * 20L;
            player.sendSystemMessage(Component.translatable("message.umc.dream_journey").withStyle(ChatFormatting.LIGHT_PURPLE));
            SpiritScheduler.next(() -> SpiritTravel.enter(player, bed, duration));
        }
    }

    private static void nightmare(ServerLevel level, ServerPlayer player, BlockPos bed) {
        player.sendSystemMessage(Component.translatable("message.umc.nightmare").withStyle(ChatFormatting.DARK_PURPLE));
        int seconds = SpiritConfig.NIGHTMARE_EFFECT_SECONDS.get();
        if (seconds > 0) {
            player.addEffect(new MobEffectInstance(SpiritEffects.NIGHTMARE, seconds * 20, 0));
        }
        RandomSource random = player.getRandom();
        int lifetime = SpiritConfig.NIGHTMARE_LIFETIME_SECONDS.get() * 20;
        for (int i = 0; i < SpiritConfig.NIGHTMARE_COUNT.get(); i++) {
            for (int attempt = 0; attempt < 12; attempt++) {
                int dx = random.nextInt(9) - 4;
                int dz = random.nextInt(9) - 4;
                if (Math.abs(dx) < 2 && Math.abs(dz) < 2) {
                    continue;
                }
                double x = bed.getX() + dx + 0.5D;
                double z = bed.getZ() + dz + 0.5D;
                double y = bed.getY();
                Nightmare nightmare = SpiritEntities.NIGHTMARE.get().create(level);
                if (nightmare == null) {
                    break;
                }
                nightmare.moveTo(x, y, z, random.nextFloat() * 360.0F, 0.0F);
                if (!level.noCollision(nightmare)) {
                    nightmare.discard();
                    continue;
                }
                nightmare.setLifetime(lifetime > 0 ? lifetime : -1);
                nightmare.finalizeSpawn(level, level.getCurrentDifficultyAt(bed), MobSpawnType.EVENT, null);
                nightmare.setTarget(player);
                level.addFreshEntity(nightmare);
                break;
            }
        }
        level.playSound(null, bed, SoundEvents.PHANTOM_SWOOP, SoundSource.HOSTILE, 1.0F, 0.6F);
    }
}
