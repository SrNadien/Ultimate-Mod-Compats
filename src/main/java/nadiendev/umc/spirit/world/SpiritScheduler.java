package nadiendev.umc.spirit.world;

import java.util.ArrayList;
import java.util.List;
import nadiendev.umc.UltimateModCompats;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber(modid = UltimateModCompats.MODID)
public final class SpiritScheduler {

    private static final List<Runnable> QUEUE = new ArrayList<>();

    private SpiritScheduler() {
    }

    public static void next(Runnable task) {
        QUEUE.add(task);
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        if (QUEUE.isEmpty()) {
            return;
        }
        List<Runnable> tasks = new ArrayList<>(QUEUE);
        QUEUE.clear();
        tasks.forEach(Runnable::run);
    }
}
