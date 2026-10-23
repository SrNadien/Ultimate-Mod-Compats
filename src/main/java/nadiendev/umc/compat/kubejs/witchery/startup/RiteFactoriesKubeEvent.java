package nadiendev.umc.compat.kubejs.witchery.startup;

import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.kubejs.script.ConsoleJS;
import nadiendev.umc.compat.kubejs.witchery.util.WitcheryIds;
import net.favouriteless.enchanted.api.circle_magic.RiteFactoryRegistry;
import net.minecraft.resources.ResourceLocation;

public class RiteFactoriesKubeEvent implements KubeEvent {

    public RiteDefinition create(String id) {
        ResourceLocation location = WitcheryIds.owned(id);
        RiteDefinition definition = new RiteDefinition(location);
        try {
            RiteFactoryRegistry.get().register(location, ScriptedRiteFactory.codec(definition));
        } catch (RuntimeException ex) {
            ConsoleJS.STARTUP.error("No se pudo registrar la factory de rito " + location, ex);
        }
        return definition;
    }
}
