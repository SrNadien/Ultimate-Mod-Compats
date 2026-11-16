package nadiendev.umc.arsnouveau;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.common.items.data.BlockFillContents;
import java.util.List;
import nadiendev.umc.core.material.UmcMaterial;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Item;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

/** Makes the item of each jar show how full it is, like the jar of Ars Nouveau does. Only loaded on the client. */
final class ArsJarsClient {

    private ArsJarsClient() {
    }

    static void setup(FMLClientSetupEvent event, List<Item> items) {
        event.enqueueWork(() -> {
            List<UmcMaterial> materials = UmcMaterial.available();
            for (int index = 0; index < materials.size(); index++) {
                float capacity = ArsNouveauStats.capacity(materials.get(index));
                ItemProperties.register(items.get(index), ArsNouveau.prefix("source"),
                        (stack, level, entity, seed) -> BlockFillContents.get(stack) / capacity);
            }
        });
    }
}
