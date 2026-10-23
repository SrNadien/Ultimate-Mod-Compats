package nadiendev.umc.compat.kubejs.witchery.startup;

import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class RiteDefinition {

    private final ResourceLocation id;
    private Function<RiteContext, Object> onStart;
    private Function<RiteContext, Object> onTick;
    private Consumer<RiteContext> onStop;
    private int duration;
    private final List<ItemStack> outputs = new ArrayList<>();

    RiteDefinition(ResourceLocation id) {
        this.id = id;
    }

    public RiteDefinition onStart(Function<RiteContext, Object> callback) {
        this.onStart = callback;
        return this;
    }

    public RiteDefinition onTick(Function<RiteContext, Object> callback) {
        this.onTick = callback;
        return this;
    }

    public RiteDefinition onStop(Consumer<RiteContext> callback) {
        this.onStop = callback;
        return this;
    }

    public RiteDefinition duration(int ticks) {
        this.duration = ticks;
        return this;
    }

    public RiteDefinition output(ItemStack... stacks) {
        outputs.addAll(List.of(stacks));
        return this;
    }

    @HideFromJS
    public ResourceLocation id() {
        return id;
    }

    @HideFromJS
    public Function<RiteContext, Object> start() {
        return onStart;
    }

    @HideFromJS
    public Function<RiteContext, Object> tick() {
        return onTick;
    }

    @HideFromJS
    public Consumer<RiteContext> stop() {
        return onStop;
    }

    @HideFromJS
    public int durationTicks() {
        return duration;
    }

    @HideFromJS
    public List<ItemStack> outputStacks() {
        return outputs.isEmpty() ? null : List.copyOf(outputs);
    }
}
