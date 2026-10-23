package nadiendev.umc.compat.kubejs.create.kube;

import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import nadiendev.umc.compat.kubejs.create.schema.CreateSchemas;

public class ItemApplicationKubeRecipe extends KubeRecipe {

    public KubeRecipe keepHeldItem() {
        return setValue(CreateSchemas.KEEP_HELD_ITEM, true);
    }

    public KubeRecipe keepHeldItem(boolean keep) {
        return setValue(CreateSchemas.KEEP_HELD_ITEM, keep);
    }
}
