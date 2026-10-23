package nadiendev.umc.compat.kubejs.ae2.kube;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.script.ConsoleJS;
import nadiendev.umc.compat.kubejs.ae2.schema.Ae2Schemas;
import net.minecraft.resources.ResourceLocation;

public class TransformKubeRecipe extends Ae2KubeRecipe {

    private static final ResourceLocation LAVA = ResourceLocation.withDefaultNamespace("lava");
    private static final ResourceLocation WATER = ResourceLocation.withDefaultNamespace("water");

    public KubeRecipe explosion() {
        JsonObject circumstance = new JsonObject();
        circumstance.addProperty("type", "explosion");
        return setValue(Ae2Schemas.TRANSFORM_CIRCUMSTANCE, circumstance);
    }

    public KubeRecipe fluid(String tag) {
        ResourceLocation id = parseTag(tag);
        if (LAVA.equals(id)) {
            ConsoleJS.SERVER.warn("ae2.transform en lava: los objetos que no son a prueba de fuego (netherita) se queman antes de los 3 segundos que AE2 necesita para transformarlos");
        }
        JsonObject circumstance = new JsonObject();
        circumstance.addProperty("type", "fluid");
        circumstance.addProperty("tag", id.toString());
        return setValue(Ae2Schemas.TRANSFORM_CIRCUMSTANCE, circumstance);
    }

    public KubeRecipe water() {
        return fluid(WATER.toString());
    }

    public KubeRecipe lava() {
        return fluid(LAVA.toString());
    }

    private static ResourceLocation parseTag(String tag) {
        String text = tag == null ? "" : tag.trim();
        if (text.startsWith("#")) {
            text = text.substring(1);
        }
        ResourceLocation id = ResourceLocation.tryParse(text);
        if (id == null) {
            throw new IllegalArgumentException("ae2.transform: la etiqueta de fluido '" + tag + "' no es un id valido, usa por ejemplo 'minecraft:water' o '#minecraft:lava'");
        }
        return id;
    }
}
