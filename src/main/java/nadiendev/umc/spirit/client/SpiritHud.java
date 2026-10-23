package nadiendev.umc.spirit.client;

import java.util.Optional;
import nadiendev.umc.spirit.config.SpiritConfig;
import nadiendev.umc.spirit.infusion.InfusionType;
import nadiendev.umc.spirit.infusion.SpiritInfusions;
import nadiendev.umc.spirit.registry.SpiritAttachments;
import nadiendev.umc.spirit.registry.SpiritWorld;
import nadiendev.umc.spirit.world.SpiritVisit;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class SpiritHud implements LayeredDraw.Layer {

    private static final int BAR_WIDTH = 44;
    private static final int ROW_HEIGHT = 11;

    @Override
    public void render(GuiGraphics graphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null || minecraft.options.hideGui || minecraft.getDebugOverlay().showDebugScreen()) {
            return;
        }
        if (!SpiritConfig.ENABLED.get() || !SpiritConfig.HUD_ENABLED.get()) {
            return;
        }
        Font font = minecraft.font;
        int x = SpiritConfig.HUD_X.get();
        int y = SpiritConfig.HUD_Y.get();
        if (SpiritConfig.INFUSIONS_ENABLED.get()) {
            SpiritInfusions data = player.getData(SpiritAttachments.INFUSIONS);
            int max = SpiritConfig.MAX_ENERGY_SECONDS.get() * 20;
            for (InfusionType type : InfusionType.all()) {
                int energy = data.energy(type);
                boolean active = data.active().isPresent() && data.active().get() == type;
                if (!type.enabled() || (energy <= 0 && !active)) {
                    continue;
                }
                drawRow(graphics, font, x, y, type, energy, max, active);
                y += ROW_HEIGHT;
            }
        }
        if (player.level().dimension() == SpiritWorld.LEVEL) {
            Optional<SpiritVisit> visit = player.getExistingData(SpiritAttachments.VISIT);
            if (visit.isPresent() && visit.get().returnAt() >= 0) {
                long seconds = Math.max(0L, (visit.get().returnAt() - player.level().getGameTime()) / 20L);
                graphics.drawString(font, Component.translatable("hud.umc.spirit_time", seconds), x, y, 0xCFA8FF, true);
            }
        }
    }

    private void drawRow(GuiGraphics graphics, Font font, int x, int y, InfusionType type, int energy, int max, boolean active) {
        int color = 0xFF000000 | type.color();
        graphics.fill(x, y + 1, x + 3, y + 8, active ? color : 0xFF555566);
        Component label = Component.translatable(type.translationKey());
        graphics.drawString(font, label, x + 6, y, active ? color : 0xAAAAAA, true);
        int barX = x + 6 + font.width(label) + 4;
        graphics.fill(barX - 1, y + 1, barX + BAR_WIDTH + 1, y + 8, 0xAA000000);
        int filled = max <= 0 ? 0 : Math.min(BAR_WIDTH, energy * BAR_WIDTH / max);
        graphics.fill(barX, y + 2, barX + filled, y + 7, active ? color : 0xFF6A6A88);
    }
}
