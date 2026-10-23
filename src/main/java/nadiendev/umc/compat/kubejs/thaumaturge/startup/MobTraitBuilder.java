package nadiendev.umc.compat.kubejs.thaumaturge.startup;

import dev.latvian.mods.kubejs.script.ConsoleJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class MobTraitBuilder {

    private final ResourceLocation id;
    private boolean champion;
    private boolean taint;
    private Consumer<LivingEntity> onAdded;
    private Consumer<LivingEntity> onRemoved;
    private Consumer<LivingEntity> onTick;
    private Function<MobTraitDamageContext, Object> onAttack;
    private Function<MobTraitDamageContext, Object> onHurt;

    MobTraitBuilder(ResourceLocation id) {
        this.id = id;
    }

    public MobTraitBuilder champion(boolean champion) {
        this.champion = champion;
        return this;
    }

    public MobTraitBuilder taint(boolean taint) {
        this.taint = taint;
        return this;
    }

    public MobTraitBuilder onAdded(Consumer<LivingEntity> callback) {
        this.onAdded = callback;
        return this;
    }

    public MobTraitBuilder onRemoved(Consumer<LivingEntity> callback) {
        this.onRemoved = callback;
        return this;
    }

    public MobTraitBuilder tick(Consumer<LivingEntity> callback) {
        this.onTick = callback;
        return this;
    }

    public MobTraitBuilder onAttack(Function<MobTraitDamageContext, Object> callback) {
        this.onAttack = callback;
        return this;
    }

    public MobTraitBuilder onHurt(Function<MobTraitDamageContext, Object> callback) {
        this.onHurt = callback;
        return this;
    }

    @HideFromJS
    ResourceLocation id() {
        return id;
    }

    @HideFromJS
    Object build() {
        Class<?> type = ThaumaturgeReflect.type("entity.trait.MobTrait");
        return Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] {type}, this::handle);
    }

    private Object handle(Object proxy, Method method, Object[] args) throws Throwable {
        switch (method.getName()) {
            case "isChampion" -> {
                return champion;
            }
            case "isTaint" -> {
                return taint;
            }
            case "onAdded" -> {
                run(onAdded, (LivingEntity) args[0]);
                return null;
            }
            case "onRemoved" -> {
                run(onRemoved, (LivingEntity) args[0]);
                return null;
            }
            case "tick" -> {
                run(onTick, (LivingEntity) args[0]);
                return null;
            }
            case "onAttack" -> {
                return damage(onAttack, (LivingEntity) args[0], (LivingEntity) args[1], (DamageSource) args[2], (Float) args[3]);
            }
            case "onHurt" -> {
                return damage(onHurt, (LivingEntity) args[0], (LivingEntity) args[1], (DamageSource) args[2], (Float) args[3]);
            }
            case "hashCode" -> {
                return System.identityHashCode(proxy);
            }
            case "equals" -> {
                return proxy == args[0];
            }
            case "toString" -> {
                return "MobTrait[" + id + "]";
            }
            default -> {
                return method.isDefault() ? InvocationHandler.invokeDefault(proxy, method, args) : null;
            }
        }
    }

    private void run(Consumer<LivingEntity> callback, LivingEntity mob) {
        if (callback == null) {
            return;
        }
        try {
            callback.accept(mob);
        } catch (RuntimeException ex) {
            ConsoleJS.STARTUP.error("Error en el rasgo de mob " + id, ex);
        }
    }

    private Float damage(Function<MobTraitDamageContext, Object> callback, LivingEntity mob, LivingEntity other, DamageSource source, float amount) {
        if (callback == null) {
            return amount;
        }
        try {
            Object result = callback.apply(new MobTraitDamageContext(mob, other, source, amount));
            return result instanceof Number number ? number.floatValue() : amount;
        } catch (RuntimeException ex) {
            ConsoleJS.STARTUP.error("Error en el rasgo de mob " + id, ex);
            return amount;
        }
    }
}
