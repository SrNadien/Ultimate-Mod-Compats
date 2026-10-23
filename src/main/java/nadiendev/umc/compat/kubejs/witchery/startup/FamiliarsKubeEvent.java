package nadiendev.umc.compat.kubejs.witchery.startup;

import dev.latvian.mods.kubejs.event.KubeEvent;
import nadiendev.umc.compat.kubejs.witchery.util.WitcheryIds;
import net.favouriteless.enchanted.common.enchanted.familiars.EFamiliarTypes;
import net.minecraft.resources.ResourceLocation;

public class FamiliarsKubeEvent implements KubeEvent {

    public FamiliarDefinition create(String id, String from, String to) {
        ResourceLocation location = WitcheryIds.owned(id);
        FamiliarDefinition definition = new FamiliarDefinition(location, WitcheryIds.withDefault(from, "minecraft"), WitcheryIds.withDefault(to, "minecraft"));
        EFamiliarTypes.register(location, definition);
        return definition;
    }
}
