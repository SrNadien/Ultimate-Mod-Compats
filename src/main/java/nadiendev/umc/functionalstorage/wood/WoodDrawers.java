package nadiendev.umc.functionalstorage.wood;

import com.buuz135.functionalstorage.FunctionalStorage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The drawers of Functional Storage for the woods of the other mods. The woods are found in the files of the installed
 * mods and given to Functional Storage before it registers its drawers, which then makes the blocks, the items and
 * everything that goes with them. The blockstates, models, textures, names, recipes and tags are made in memory when the
 * game starts, from the cherry drawer. Needs to be called before Functional Storage is set up, so this mod loads first.
 */
public final class WoodDrawers {

    private static final Logger LOGGER = LoggerFactory.getLogger(WoodDrawers.class);
    private static List<WoodDiscovery.Wood> woods = new ArrayList<>();

    private WoodDrawers() {
    }

    public static void init(IEventBus modBus) {
        woods = new ArrayList<>(WoodDiscovery.vanilla());
        woods.addAll(WoodDiscovery.discover());
        if (woods.isEmpty()) {
            return;
        }
        for (WoodDiscovery.Wood wood : woods) {
            FunctionalStorage.WOOD_TYPES.add(new ModdedWoodType(wood));
        }
        modBus.addListener(WoodDrawers::addPacks);
    }

    private static void addPacks(AddPackFindersEvent event) {
        PackType type = event.getPackType();
        if (type != PackType.CLIENT_RESOURCES && type != PackType.SERVER_DATA) {
            return;
        }
        Map<String, byte[]> files = WoodDrawerContent.build(woods, type);
        String id = "umc_wood_drawers_" + (type == PackType.CLIENT_RESOURCES ? "assets" : "data");
        PackLocationInfo info = new PackLocationInfo(id, Component.literal("Ultimate Mod Compats: wood drawers"), PackSource.BUILT_IN, Optional.empty());
        Pack.ResourcesSupplier supplier = new Pack.ResourcesSupplier() {
            @Override
            public PackResources openPrimary(PackLocationInfo location) {
                return new WoodDrawerPack(location, type, files);
            }

            @Override
            public PackResources openFull(PackLocationInfo location, Pack.Metadata metadata) {
                return new WoodDrawerPack(location, type, files);
            }
        };
        Pack pack = Pack.readMetaAndCreate(info, supplier, type, new PackSelectionConfig(true, Pack.Position.TOP, false));
        if (pack == null) {
            LOGGER.error("Could not make the pack of the wood drawers ({})", id);
            return;
        }
        event.addRepositorySource(consumer -> consumer.accept(pack));
    }

    /** The drawer items of the woods for the creative tab: the woods of vanilla first and then the ones of the other mods, each in its three sizes. */
    public static List<Item> items() {
        List<Item> items = new ArrayList<>();
        for (WoodDiscovery.Wood wood : woods) {
            for (int slots : WoodDrawerContent.SLOTS) {
                BuiltInRegistries.ITEM.getOptional(ResourceLocation.fromNamespaceAndPath("functionalstorage", wood.name() + "_" + slots))
                        .ifPresent(items::add);
            }
        }
        return items;
    }
}
