package nadiendev.umc.spirit.block;

import com.mojang.serialization.MapCodec;
import nadiendev.umc.spirit.config.SpiritConfig;
import nadiendev.umc.spirit.registry.SpiritItems;
import nadiendev.umc.spirit.registry.SpiritWorld;
import nadiendev.umc.spirit.world.SpiritTravel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SpiritPortalBlock extends Block {

    public static final MapCodec<SpiritPortalBlock> CODEC = simpleCodec(SpiritPortalBlock::new);
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    private static final VoxelShape SHAPE = box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);

    public SpiritPortalBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(ACTIVE, false));
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    private static boolean isOpen(BlockState state, Level level) {
        return state.getValue(ACTIVE) || level.dimension() == SpiritWorld.LEVEL;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!stack.is(SpiritItems.SPIRIT_DUST.get()) || isOpen(state, level)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (!SpiritConfig.ENABLED.get() || !SpiritConfig.WORLD_ENABLED.get()) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        int cost = SpiritConfig.PORTAL_DUST_COST.get();
        if (stack.getCount() < cost && !player.getAbilities().instabuild) {
            if (!level.isClientSide) {
                player.displayClientMessage(Component.translatable("message.umc.portal_needs_dust", cost), true);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        if (!level.isClientSide) {
            if (!player.getAbilities().instabuild) {
                stack.shrink(cost);
            }
            level.setBlock(pos, state.setValue(ACTIVE, true), UPDATE_ALL);
            level.scheduleTick(pos, this, SpiritConfig.PORTAL_ACTIVE_SECONDS.get() * 20);
            level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS, 1.0F, 0.6F);
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide && !isOpen(state, level)) {
            player.displayClientMessage(Component.translatable("message.umc.portal_inert"), true);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(ACTIVE) && level.dimension() != SpiritWorld.LEVEL) {
            level.setBlock(pos, state.setValue(ACTIVE, false), UPDATE_ALL);
            level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.BLOCKS, 1.0F, 0.5F);
        }
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (level.isClientSide || !isOpen(state, level) || !(entity instanceof ServerPlayer player)) {
            return;
        }
        if (!SpiritConfig.ENABLED.get() || !SpiritConfig.WORLD_ENABLED.get()) {
            return;
        }
        if (player.isPassenger() || player.isVehicle()) {
            return;
        }
        if (player.isOnPortalCooldown()) {
            player.setPortalCooldown();
            return;
        }
        if (level.dimension() == SpiritWorld.LEVEL) {
            SpiritTravel.leave(player);
        } else {
            SpiritTravel.enter(player, pos, -1);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!isOpen(state, level)) {
            return;
        }
        for (int i = 0; i < 2; i++) {
            double x = pos.getX() + random.nextDouble();
            double z = pos.getZ() + random.nextDouble();
            level.addParticle(ParticleTypes.SOUL, x, pos.getY() + 0.25D, z, 0.0D, 0.04D + random.nextDouble() * 0.04D, 0.0D);
        }
        if (random.nextInt(6) == 0) {
            level.addParticle(ParticleTypes.END_ROD, pos.getX() + random.nextDouble(), pos.getY() + 0.3D, pos.getZ() + random.nextDouble(), 0.0D, 0.03D, 0.0D);
        }
    }
}
