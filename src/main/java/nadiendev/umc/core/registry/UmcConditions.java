package nadiendev.umc.core.registry;

import com.mojang.serialization.MapCodec;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.mysticalagriculture.condition.MysticalCropCondition;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class UmcConditions {

    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITIONS =
            DeferredRegister.create(NeoForgeRegistries.CONDITION_SERIALIZERS, UltimateModCompats.MODID);

    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<MysticalCropCondition>> MYSTICAL_CROP =
            CONDITIONS.register("mystical_crop", () -> MysticalCropCondition.CODEC);

    private UmcConditions() {
    }

    public static void register(IEventBus modBus) {
        CONDITIONS.register(modBus);
    }
}
