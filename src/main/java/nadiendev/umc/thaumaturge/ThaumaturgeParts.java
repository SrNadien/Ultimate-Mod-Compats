package nadiendev.umc.thaumaturge;

import com.leclowndu93150.thaumaturge.api.wands.WandCap;
import com.leclowndu93150.thaumaturge.api.wands.WandRod;
import com.leclowndu93150.thaumaturge.content.wands.ItemWandCap;
import com.leclowndu93150.thaumaturge.content.wands.ItemWandRod;
import java.util.List;
import java.util.function.Supplier;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.material.UmcMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/** The caps and rods themselves and their items. Only loaded when Thaumaturge is installed. */
final class ThaumaturgeParts {

    private static final ResourceLocation ASSEMBLY_RESEARCH = ResourceLocation.fromNamespaceAndPath("thaumaturge", "unlock_auromancy");

    private static final DeferredRegister<WandCap> CAPS = DeferredRegister.create(WandCap.REGISTRY_KEY, UltimateModCompats.MODID);
    private static final DeferredRegister<WandRod> RODS = DeferredRegister.create(WandRod.REGISTRY_KEY, UltimateModCompats.MODID);

    private ThaumaturgeParts() {
    }

    static void register(IEventBus modBus, DeferredRegister.Items items, List<DeferredItem<Item>> out) {
        for (UmcMaterial material : UmcMaterial.wandMaterials()) {
            ThaumaturgeStats stats = ThaumaturgeStats.of(material);
            ResourceLocation capTexture = texture("wand_cap_" + material.id());
            ResourceLocation rodTexture = texture("wand_rod_" + material.id());
            DeferredHolder<WandCap, WandCap> cap = CAPS.register(material.id(),
                    () -> new WandCap(stats.visModifier(), List.of(), 0f, stats.capCost(), capTexture));
            DeferredHolder<WandRod, WandRod> rod = RODS.register(material.id(),
                    () -> new WandRod(stats.rodCapacity(),stats.rodCost(), rodTexture, null, true, false, false, ASSEMBLY_RESEARCH));
            out.add(items.register(ThaumaturgeRegistry.capId(material), () -> capItem(cap)));
            out.add(items.register(ThaumaturgeRegistry.rodId(material), () -> rodItem(rod)));
        }
        CAPS.register(modBus);
        RODS.register(modBus);
    }

    private static Item capItem(Supplier<WandCap> cap) {
        return new ItemWandCap(new Item.Properties().stacksTo(64), cap);
    }

    private static Item rodItem(Supplier<WandRod> rod) {
        return new ItemWandRod(new Item.Properties().stacksTo(64), rod);
    }

    private static ResourceLocation texture(String name) {
        return ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "textures/models/" + name + ".png");
    }
}
