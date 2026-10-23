package nadiendev.umc.compat.kubejs.witchery.startup;

import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.util.JsonUtils;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.List;
import java.util.UUID;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class RiteContext {

    private static final int COMMAND_PERMISSION_LEVEL = 2;

    public final ServerLevel level;
    public final BlockPos pos;
    public final int ticks;

    private final ScriptedRite rite;
    private final Rite.RiteParams params;

    @HideFromJS
    public RiteContext(ScriptedRite rite, Rite.RiteParams params) {
        this.rite = rite;
        this.params = params;
        this.level = rite.serverLevel();
        this.pos = rite.blockPos();
        this.ticks = params.ticks();
    }

    @Nullable
    public ServerPlayer getCaster() {
        return params.caster == null ? null : level.getServer().getPlayerList().getPlayer(params.caster);
    }

    @Nullable
    public UUID getCasterId() {
        return params.caster;
    }

    @Nullable
    public Entity getTarget() {
        return rite.lookup(params.target);
    }

    @Nullable
    public UUID getTargetId() {
        return params.target;
    }

    public List<ItemStack> getConsumedItems() {
        return params.consumedItems;
    }

    public CompoundTag getData() {
        return rite.data();
    }

    public ResourceLocation getRiteType() {
        return rite.factory().id();
    }

    public Object getParam(String key) {
        JsonElement value = rite.factory().params().get(key);
        return value == null ? null : JsonUtils.toObject(value);
    }

    public boolean hasParam(String key) {
        return rite.factory().params().containsKey(key);
    }

    public String getString(String key, String fallback) {
        JsonElement value = rite.factory().params().get(key);
        return value == null || !value.isJsonPrimitive() ? fallback : value.getAsString();
    }

    public int getInt(String key, int fallback) {
        JsonElement value = rite.factory().params().get(key);
        return value == null || !value.isJsonPrimitive() ? fallback : value.getAsInt();
    }

    public double getDouble(String key, double fallback) {
        JsonElement value = rite.factory().params().get(key);
        return value == null || !value.isJsonPrimitive() ? fallback : value.getAsDouble();
    }

    public boolean getBoolean(String key, boolean fallback) {
        JsonElement value = rite.factory().params().get(key);
        return value == null || !value.isJsonPrimitive() ? fallback : value.getAsBoolean();
    }

    public boolean cancel() {
        return rite.cancelRite();
    }

    public void drop(ItemStack stack) {
        level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, stack.copy()));
    }

    public void ambientParticles(String particle) {
        if (BuiltInRegistries.PARTICLE_TYPE.get(ResourceLocation.parse(particle)) instanceof SimpleParticleType type) {
            rite.particles(type);
        }
    }

    public void particles(String particle, int count, double spread) {
        if (BuiltInRegistries.PARTICLE_TYPE.get(ResourceLocation.parse(particle)) instanceof SimpleParticleType type) {
            level.sendParticles(type, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, count, spread, spread, spread, 0.0);
        }
    }

    public void runCommand(String command) {
        CommandSourceStack source = new CommandSourceStack(CommandSource.NULL, Vec3.atCenterOf(pos), Vec2.ZERO, level,
                COMMAND_PERMISSION_LEVEL, "Rite", Component.literal("Rite"), level.getServer(), null);
        level.getServer().getCommands().performPrefixedCommand(source, command);
    }
}
