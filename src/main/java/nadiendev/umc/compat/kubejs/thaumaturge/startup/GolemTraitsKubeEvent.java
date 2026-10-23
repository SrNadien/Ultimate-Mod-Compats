package nadiendev.umc.compat.kubejs.thaumaturge.startup;

import dev.latvian.mods.rhino.util.HideFromJS;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeIds;
import net.neoforged.neoforge.registries.RegisterEvent;

public class GolemTraitsKubeEvent extends StartupRegistration {

    @HideFromJS
    public GolemTraitsKubeEvent(RegisterEvent event) {
        super(event, ThaumaturgeReflect.registryKey("golem_trait"));
    }

    public GolemTraitBuilder create(String id) {
        GolemTraitBuilder builder = new GolemTraitBuilder(ThaumaturgeIds.owned(id));
        queue(builder.id(), builder::build);
        return builder;
    }
}
