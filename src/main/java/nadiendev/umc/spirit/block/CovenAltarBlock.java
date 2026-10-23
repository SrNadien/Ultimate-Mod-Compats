package nadiendev.umc.spirit.block;

import com.mojang.serialization.MapCodec;
import java.util.Optional;
import nadiendev.umc.spirit.block.entity.CovenAltarBlockEntity;
import nadiendev.umc.spirit.config.SpiritConfig;
import nadiendev.umc.spirit.coven.Coven;
import nadiendev.umc.spirit.coven.CovenData;
import nadiendev.umc.spirit.coven.CovenService;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CovenAltarBlock extends BaseEntityBlock {

    public static final MapCodec<CovenAltarBlock> CODEC = simpleCodec(CovenAltarBlock::new);

    private static final VoxelShape SHAPE = box(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D);

    public CovenAltarBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CovenAltarBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if (!(player instanceof ServerPlayer serverPlayer) || !(level.getBlockEntity(pos) instanceof CovenAltarBlockEntity altar)) {
            return InteractionResult.CONSUME;
        }
        if (!SpiritConfig.ENABLED.get() || !SpiritConfig.COVENS_ENABLED.get()) {
            serverPlayer.sendSystemMessage(Component.translatable("message.umc.coven.disabled"));
            return InteractionResult.CONSUME;
        }
        CovenData data = CovenData.get(serverPlayer.server);
        Optional<Coven> own = data.ofPlayer(serverPlayer.getUUID());
        Optional<Coven> bound = altar.covenId() == null ? Optional.empty() : data.byId(altar.covenId());
        if (bound.isEmpty()) {
            bindOrFound(serverPlayer, data, own, altar, level, pos);
        } else if (own.isPresent() && own.get().id().equals(bound.get().id())) {
            CovenService.info(serverPlayer, bound.get());
        } else if (own.isPresent()) {
            serverPlayer.sendSystemMessage(Component.translatable("message.umc.coven.already_member"));
        } else if (CovenService.joinCoven(serverPlayer, data, bound.get(), false)) {
            level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.0F, 0.8F);
        }
        return InteractionResult.CONSUME;
    }

    private void bindOrFound(ServerPlayer player, CovenData data, Optional<Coven> own, CovenAltarBlockEntity altar, Level level, BlockPos pos) {
        if (own.isPresent()) {
            Coven coven = own.get();
            if (!coven.leader().equals(player.getUUID())) {
                player.sendSystemMessage(Component.translatable("message.umc.coven.not_leader"));
                return;
            }
            altar.bind(coven.id());
            player.sendSystemMessage(Component.translatable("message.umc.coven.altar_bound", coven.name()));
            level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS, 1.0F, 0.7F);
            return;
        }
        Item costItem = costItem();
        int count = SpiritConfig.COVEN_FOUND_COST_COUNT.get();
        boolean free = costItem == null || count <= 0 || player.getAbilities().instabuild;
        if (!free && countItem(player, costItem) < count) {
            player.sendSystemMessage(Component.translatable("message.umc.coven.needs_item", count, costItem.getDescription()));
            return;
        }
        String base = Component.translatable("message.umc.coven.default_name", player.getGameProfile().getName()).getString();
        String name = base;
        int suffix = 2;
        while (data.byName(name).isPresent()) {
            name = base + " " + suffix++;
        }
        if (CovenService.create(player, name)) {
            if (!free) {
                consumeItem(player, costItem, count);
            }
            data.ofPlayer(player.getUUID()).ifPresent(coven -> altar.bind(coven.id()));
            level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS, 1.0F, 0.5F);
        }
    }

    private static Item costItem() {
        String itemId = SpiritConfig.COVEN_FOUND_COST_ITEM.get();
        if (itemId.isBlank()) {
            return null;
        }
        ResourceLocation id = ResourceLocation.tryParse(itemId);
        return id == null ? null : BuiltInRegistries.ITEM.getOptional(id).orElse(null);
    }

    private static int countItem(ServerPlayer player, Item item) {
        int have = 0;
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(item)) {
                have += stack.getCount();
            }
        }
        return have;
    }

    private static void consumeItem(ServerPlayer player, Item item, int count) {
        int remaining = count;
        for (ItemStack stack : player.getInventory().items) {
            if (remaining <= 0) {
                return;
            }
            if (stack.is(item)) {
                int taken = Math.min(remaining, stack.getCount());
                stack.shrink(taken);
                remaining -= taken;
            }
        }
    }
}
