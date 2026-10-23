package nadiendev.umc.core.registry;

import nadiendev.umc.UltimateModCompats;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class UmcTabs {

    private static final String KUBEJS_NAMESPACE = "kubejs";
    private static final String ENCHANTED_PACKAGE = "net.favouriteless.enchanted.";

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, UltimateModCompats.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + UltimateModCompats.MODID + ".main"))
            .icon(() -> new ItemStack(UmcItems.TAB_ICON.get()))
            .withLabelColor(0xFFE6DCFF)
            .backgroundTexture(ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "textures/gui/container/creative_inventory/tab_space_cat.png"))
            .displayItems((parameters, output) -> BuiltInRegistries.ITEM.entrySet().stream()
                    .filter(entry -> entry.getKey().location().getNamespace().equals(UltimateModCompats.MODID) || isScriptedWitcheryItem(entry.getKey().location().getNamespace(), entry.getValue()))
                    .map(entry -> entry.getValue())
                    .filter(item -> item != UmcItems.TAB_ICON.get())
                    .forEach(output::accept))
            .build());

    private static boolean isScriptedWitcheryItem(String namespace, Item item) {
        if (!namespace.equals(KUBEJS_NAMESPACE)) {
            return false;
        }
        for (Class<?> type = item.getClass(); type != null; type = type.getSuperclass()) {
            if (type.getName().startsWith(ENCHANTED_PACKAGE)) {
                return true;
            }
        }
        return false;
    }

    private UmcTabs() {
    }

    public static void register(IEventBus modBus) {
        TABS.register(modBus);
    }
}
