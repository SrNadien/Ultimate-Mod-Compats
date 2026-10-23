package nadiendev.umc.crafting.event;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.crafting.config.CraftingConfig;
import nadiendev.umc.crafting.config.SpeciesConfig;
import nadiendev.umc.crafting.familiar.FamiliarTypes;
import nadiendev.umc.crafting.util.EffectSpec;
import net.favouriteless.enchanted.api.familiars.FamiliarEntry;
import net.favouriteless.enchanted.common.enchanted.familiars.FamiliarSavedData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = UltimateModCompats.MODID)
public final class FamiliarBonusEvents {

    private FamiliarBonusEvents() {
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        if (!CraftingConfig.ENABLED.get() || !CraftingConfig.FAMILIARS_ENABLED.get()) {
            return;
        }
        int interval = CraftingConfig.FAMILIAR_BONUS_INTERVAL.get();
        if ((player.tickCount + player.getId()) % interval != 0) {
            return;
        }

        ServerLevel level = player.serverLevel();
        FamiliarEntry entry = FamiliarSavedData.get(level).getEntry(player.getUUID());
        if (entry == null || entry.isDismissed()) {
            return;
        }

        SpeciesConfig species = FamiliarTypes.all().get(entry.getType().getId());
        if (species == null || !species.enabled.get()) {
            return;
        }

        int range = CraftingConfig.FAMILIAR_BONUS_RANGE.get();
        if (range > 0) {
            Entity familiar = level.getEntity(entry.getUUID());
            if (familiar == null || familiar.distanceToSqr(player) > (double) range * range) {
                return;
            }
        }

        EffectSpec.applyAll(player, species.bonusEffects.get(), Math.max(interval + 120, 260));
    }
}
