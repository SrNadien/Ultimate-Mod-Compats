package nadiendev.umc.compat.kubejs.witchery.item;

import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class BrewSplashContext {

    @Nullable
    public final Entity owner;
    public final Level level;
    public final Vec3 pos;

    @HideFromJS
    public BrewSplashContext(@Nullable Entity owner, Level level, Vec3 pos) {
        this.owner = owner;
        this.level = level;
        this.pos = pos;
    }
}
