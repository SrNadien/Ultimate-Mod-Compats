package nadiendev.umc.compat.kubejs.thaumaturge.startup;

import dev.latvian.mods.rhino.util.HideFromJS;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeIds;
import net.neoforged.neoforge.registries.RegisterEvent;

public class MobTraitsKubeEvent extends StartupRegistration {

    @HideFromJS
    public MobTraitsKubeEvent(RegisterEvent event) {
        super(event, ThaumaturgeReflect.registryKey("mob_trait"));
    }

    public MobTraitBuilder create(String id) {
        MobTraitBuilder builder = new MobTraitBuilder(ThaumaturgeIds.owned(id));
        queue(builder.id(), builder::build);
        return builder;
    }
}
