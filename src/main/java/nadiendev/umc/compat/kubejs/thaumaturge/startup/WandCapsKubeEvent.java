package nadiendev.umc.compat.kubejs.thaumaturge.startup;

import dev.latvian.mods.rhino.util.HideFromJS;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeIds;
import net.neoforged.neoforge.registries.RegisterEvent;

public class WandCapsKubeEvent extends StartupRegistration {

    @HideFromJS
    public WandCapsKubeEvent(RegisterEvent event) {
        super(event, ThaumaturgeReflect.registryKey("wand_cap"));
    }

    public WandCapBuilder create(String id) {
        WandCapBuilder builder = new WandCapBuilder(ThaumaturgeIds.owned(id));
        queue(builder.id(), builder::build);
        return builder;
    }
}
