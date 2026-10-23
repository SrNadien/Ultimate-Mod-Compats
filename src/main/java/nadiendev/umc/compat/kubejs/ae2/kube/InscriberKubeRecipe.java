package nadiendev.umc.compat.kubejs.ae2.kube;

import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import nadiendev.umc.compat.kubejs.ae2.schema.Ae2Schemas;
import nadiendev.umc.compat.kubejs.ae2.type.InscriberMode;

public class InscriberKubeRecipe extends Ae2KubeRecipe {

    public KubeRecipe press() {
        return setValue(Ae2Schemas.INSCRIBER_MODE, InscriberMode.PRESS);
    }

    public KubeRecipe inscribe() {
        return setValue(Ae2Schemas.INSCRIBER_MODE, InscriberMode.INSCRIBE);
    }
}
