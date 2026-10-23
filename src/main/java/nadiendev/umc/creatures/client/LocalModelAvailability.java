package nadiendev.umc.creatures.client;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import nadiendev.umc.creatures.client.geo.CreatureGeoModel;
import net.minecraft.server.packs.resources.ResourceManager;

public final class LocalModelAvailability {

    public static final String WEREWOLF = "werewolf";
    public static final String VAMPIRE = "vampire";

    private static final Set<String> NAMES = Set.of(WEREWOLF, VAMPIRE);
    private static final Map<String, Boolean> AVAILABLE = new ConcurrentHashMap<>();

    private LocalModelAvailability() {
    }

    public static void reload(ResourceManager manager) {
        for (String name : NAMES) {
            boolean present = manager.getResource(CreatureGeoModel.modelPath(name)).isPresent()
                    && manager.getResource(CreatureGeoModel.animationPath(name)).isPresent();
            AVAILABLE.put(name, present);
        }
    }

    public static boolean isAvailable(String name) {
        return AVAILABLE.getOrDefault(name, false) && FormVisuals.useModel(name);
    }
}
