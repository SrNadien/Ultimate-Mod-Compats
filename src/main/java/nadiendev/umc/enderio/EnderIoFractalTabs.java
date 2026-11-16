package nadiendev.umc.enderio;

import com.enderio.enderio.api.EnderIORegistries;
import com.enderio.enderio.content.conduits.ConduitBlockItem;
import de.dafuqs.fractal.api.CreativeSubTab;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.registry.UmcFractalTabs;
import nadiendev.umc.core.material.UmcMaterial;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

/** The EnderIO sub tab of the creative tab. Only touched when EnderIO is installed. */
public final class EnderIoFractalTabs {

    public static final String ID = "enderio";
    /** The conduits go first, then the grinding balls and last the capacitors. */
    private static final String[] CONDUIT_ORDER = {"energy", "item", "fluid"};

    private EnderIoFractalTabs() {
    }

    public static CreativeSubTab build(CreativeModeTab parent) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, ID);
        return new CreativeSubTab.Builder(parent, id, Component.translatable(UmcFractalTabs.sectionKey(ID)))
                .entries((parameters, output) -> {
                    var conduits = parameters.holders().lookupOrThrow(EnderIORegistries.Keys.CONDUIT);
                    for (String kind : CONDUIT_ORDER) {
                        for (UmcMaterial material : UmcMaterial.available()) {
                            ResourceLocation conduit = ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, EnderIoRegistry.conduitId(material, kind));
                            conduits.get(ResourceKey.create(EnderIORegistries.Keys.CONDUIT, conduit)).ifPresent(holder ->
                                    output.accept(ConduitBlockItem.getStackFor(Holder.<com.enderio.enderio.api.conduits.Conduit<?, ?>>direct(holder.value()), 1)));
                        }
                    }
                    for (Item item : EnderIoRegistry.balls()) {
                        output.accept(item);
                    }
                    for (Item item : EnderIoRegistry.capacitors()) {
                        output.accept(item);
                    }
                })
                .build();
    }
}
