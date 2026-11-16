package nadiendev.umc.mixin.client;

import nadiendev.umc.core.registry.UmcFractalTabs;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fractal writes the name of the tab in a fixed dark gray, which cannot be read over the background of the tab of this
 * mod. The name is drawn again in white, with a shadow, at the same place and after Fractal, so it covers the gray one.
 */
@Mixin(value = CreativeModeInventoryScreen.class, priority = 1500)
public abstract class CreativeInventoryScreenTitleMixin extends EffectRenderingInventoryScreen<CreativeModeInventoryScreen.ItemPickerMenu> {

    private static final int TITLE_COLOR = 0xFFFFFFFF;

    @Shadow
    private static CreativeModeTab selectedTab;

    private CreativeInventoryScreenTitleMixin(CreativeModeInventoryScreen.ItemPickerMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Inject(method = "render", at = @At("TAIL"), require = 0)
    private void umc$readableTabTitle(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (selectedTab == null) {
            return;
        }
        Component name = selectedTab.getDisplayName();
        if (name.getContents() instanceof TranslatableContents contents && contents.getKey().equals(UmcFractalTabs.TITLE_KEY)) {
            graphics.drawString(this.font, name, this.leftPos + 8, this.topPos + 6, TITLE_COLOR, true);
        }
    }
}
