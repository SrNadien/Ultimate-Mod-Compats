package nadiendev.umc.compat.kubejs.witchery.startup;

import com.mojang.serialization.MapCodec;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.favouriteless.enchanted.api.curses.Curse;
import net.minecraft.resources.ResourceLocation;

public class CurseDefinition {

    private static final int DEFAULT_MAX_STRENGTH = 2;

    private final ResourceLocation id;
    private final Curse.Type<ScriptedCurse> type;
    private int maxStrength = DEFAULT_MAX_STRENGTH;
    private Consumer<CurseContext> onInitialise;
    private Consumer<CurseContext> onRemove;
    private Consumer<CurseContext> onTick;

    CurseDefinition(ResourceLocation id) {
        this.id = id;
        Supplier<ScriptedCurse> supplier = () -> new ScriptedCurse(this);
        this.type = new Curse.Type<>(id, supplier, MapCodec.unit(supplier));
    }

    public CurseDefinition maxStrength(int maxStrength) {
        this.maxStrength = maxStrength;
        return this;
    }

    public CurseDefinition onInitialise(Consumer<CurseContext> callback) {
        this.onInitialise = callback;
        return this;
    }

    public CurseDefinition onRemove(Consumer<CurseContext> callback) {
        this.onRemove = callback;
        return this;
    }

    public CurseDefinition tick(Consumer<CurseContext> callback) {
        this.onTick = callback;
        return this;
    }

    @HideFromJS
    public ResourceLocation id() {
        return id;
    }

    @HideFromJS
    public Curse.Type<ScriptedCurse> type() {
        return type;
    }

    @HideFromJS
    public int strengthLimit() {
        return maxStrength;
    }

    @HideFromJS
    public Consumer<CurseContext> initialiseCallback() {
        return onInitialise;
    }

    @HideFromJS
    public Consumer<CurseContext> removeCallback() {
        return onRemove;
    }

    @HideFromJS
    public Consumer<CurseContext> tickCallback() {
        return onTick;
    }

    @HideFromJS
    public void disableTick() {
        this.onTick = null;
    }
}
