package nadiendev.umc.compat.kubejs.witchery.startup;

import dev.latvian.mods.kubejs.script.ConsoleJS;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class ScriptedRite extends Rite {

    private static final String DATA_KEY = "KubeData";
    private static final Object FAILED = new Object();

    private final ScriptedRiteFactory factory;
    private CompoundTag data = new CompoundTag();

    public ScriptedRite(BaseRiteParams baseParams, RiteParams params, ScriptedRiteFactory factory) {
        super(baseParams, params);
        this.factory = factory;
    }

    @Override
    protected boolean onStart(RiteParams params) {
        RiteDefinition definition = factory.definition();
        Function<RiteContext, Object> callback = definition.start();
        if (callback == null) {
            return definition.tick() != null || definition.durationTicks() > 0;
        }
        Object result = invoke(callback, params);
        if (result == FAILED) {
            return cancel();
        }
        return result == null ? definition.tick() != null || definition.durationTicks() > 0 : truthy(result);
    }

    @Override
    protected boolean onTick(RiteParams params) {
        RiteDefinition definition = factory.definition();
        boolean keep = definition.durationTicks() > 0;
        Function<RiteContext, Object> callback = definition.tick();
        if (callback != null) {
            Object result = invoke(callback, params);
            if (result == FAILED) {
                return false;
            }
            keep = result == null ? keep : truthy(result);
        }
        if (definition.durationTicks() > 0 && params.ticks() >= definition.durationTicks()) {
            keep = false;
        }
        return keep;
    }

    @Override
    protected void onStop(RiteParams params) {
        Consumer<RiteContext> callback = factory.definition().stop();
        if (callback == null) {
            return;
        }
        try {
            callback.accept(new RiteContext(this, params));
        } catch (Exception ex) {
            ConsoleJS.STARTUP.error("Error en onStop del rito " + factory.id(), ex);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, ServerLevel level) {
        tag.put(DATA_KEY, data.copy());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, ServerLevel level) {
        data = tag.getCompound(DATA_KEY);
    }

    private Object invoke(Function<RiteContext, Object> callback, RiteParams params) {
        try {
            return callback.apply(new RiteContext(this, params));
        } catch (Exception ex) {
            ConsoleJS.STARTUP.error("Error en el rito " + factory.id(), ex);
            return FAILED;
        }
    }

    private static boolean truthy(Object value) {
        if (value instanceof Boolean flag) {
            return flag;
        }
        if (value instanceof Number number) {
            return number.doubleValue() != 0;
        }
        return true;
    }

    ScriptedRiteFactory factory() {
        return factory;
    }

    CompoundTag data() {
        return data;
    }

    boolean cancelRite() {
        return cancel();
    }

    @Nullable
    Entity lookup(@Nullable UUID uuid) {
        return uuid == null ? null : findEntity(uuid);
    }

    void particles(ParticleOptions options) {
        randomParticles(options);
    }

    ServerLevel serverLevel() {
        return level;
    }

    BlockPos blockPos() {
        return pos;
    }
}
