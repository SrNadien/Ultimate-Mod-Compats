package nadiendev.umc.arsnouveau;

import java.util.ArrayList;
import java.util.List;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.config.CompatSwitches;
import nadiendev.umc.core.material.UmcMaterial;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/** The Ars Nouveau source jars of the Ultimate Armoury materials, registered in the {@code umc} namespace. */
public final class ArsNouveauRegistry {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(UltimateModCompats.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(UltimateModCompats.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, UltimateModCompats.MODID);

    private static final List<DeferredItem<Item>> REGISTERED = new ArrayList<>();

    private ArsNouveauRegistry() {
    }

    public static String jarId(UmcMaterial material) {
        return material.id() + "_source_jar";
    }

    public static void register(IEventBus modBus) {
        if (!CompatSwitches.enabled(CompatSwitches.ARS_NOUVEAU)) {
            return;
        }
        ArsJars.register(BLOCKS, ITEMS, BLOCK_ENTITIES, REGISTERED);
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        BLOCK_ENTITIES.register(modBus);
        modBus.addListener(ArsNouveauRegistry::capabilities);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modBus.addListener((net.neoforged.fml.event.lifecycle.FMLClientSetupEvent event) -> ArsJarsClient.setup(event, items()));
        }
    }

    private static void capabilities(RegisterCapabilitiesEvent event) {
        ArsJars.capabilities(event);
    }

    public static List<net.minecraft.world.level.block.Block> blocks() {
        return BLOCKS.getEntries().stream().<net.minecraft.world.level.block.Block>map(entry -> entry.get()).toList();
    }

    public static List<Item> items() {
        return REGISTERED.stream().<Item>map(DeferredItem::get).toList();
    }
}
