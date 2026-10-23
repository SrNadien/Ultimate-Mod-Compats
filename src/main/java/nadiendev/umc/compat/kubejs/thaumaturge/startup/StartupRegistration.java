package nadiendev.umc.compat.kubejs.thaumaturge.startup;

import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegisterEvent;

public abstract class StartupRegistration implements KubeEvent {

    private final RegisterEvent event;
    private final ResourceKey<Registry<Object>> key;
    private final List<Pending> pending = new ArrayList<>();

    protected StartupRegistration(RegisterEvent event, ResourceKey<Registry<Object>> key) {
        this.event = event;
        this.key = key;
    }

    @HideFromJS
    protected void queue(ResourceLocation id, Supplier<Object> factory) {
        pending.add(new Pending(id, factory));
    }

    @HideFromJS
    public void flush() {
        for (Pending entry : pending) {
            event.register(key, entry.id(), entry.factory());
        }
        pending.clear();
    }

    private record Pending(ResourceLocation id, Supplier<Object> factory) {
    }
}
