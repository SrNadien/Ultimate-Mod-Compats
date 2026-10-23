package nadiendev.umc.compat.kubejs.mysticalagriculture;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface MysticalEvents {

    EventGroup GROUP = EventGroup.of("MysticalEvents");

    EventHandler RECIPES = GROUP.server("recipes", () -> MysticalRecipesKubeEvent.class);
}
