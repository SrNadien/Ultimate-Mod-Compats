package nadiendev.umc.compat.kubejs.witchery.startup;

import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.kubejs.script.ConsoleJS;
import nadiendev.umc.compat.kubejs.witchery.util.WitcheryIds;
import net.favouriteless.enchanted.api.curses.CurseManager;
import net.minecraft.resources.ResourceLocation;

public class CursesKubeEvent implements KubeEvent {

    public CurseDefinition create(String id) {
        ResourceLocation location = WitcheryIds.owned(id);
        CurseDefinition definition = new CurseDefinition(location);
        try {
            CurseManager.get().register(definition.type());
        } catch (RuntimeException ex) {
            ConsoleJS.STARTUP.error("No se pudo registrar la maldicion " + location, ex);
        }
        return definition;
    }
}
