package nadiendev.umc.compat.kubejs.witchery.startup;

import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.server.level.ServerPlayer;

public class CurseContext {

    public final ServerPlayer target;
    public final int strength;
    public final long age;

    @HideFromJS
    public CurseContext(ServerPlayer target, int strength, long age) {
        this.target = target;
        this.strength = strength;
        this.age = age;
    }
}
