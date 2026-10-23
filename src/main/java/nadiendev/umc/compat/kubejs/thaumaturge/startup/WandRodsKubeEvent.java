package nadiendev.umc.compat.kubejs.thaumaturge.startup;

import dev.latvian.mods.rhino.util.HideFromJS;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeIds;
import net.neoforged.neoforge.registries.RegisterEvent;

public class WandRodsKubeEvent extends StartupRegistration {

    @HideFromJS
    public WandRodsKubeEvent(RegisterEvent event) {
        super(event, ThaumaturgeReflect.registryKey("wand_rod"));
    }

    public WandRodBuilder create(String id) {
        WandRodBuilder builder = new WandRodBuilder(ThaumaturgeIds.owned(id));
        queue(builder.id(), builder::build);
        return builder;
    }
}
