package nadiendev.umc.compat.kubejs.witchery.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.List;
import nadiendev.umc.compat.kubejs.witchery.util.WitcheryIds;
import net.minecraft.resources.ResourceLocation;

public class ShapeBuilder implements WitcheryDataEntry {

    private final ResourceLocation id;
    private List<String> rows = List.of();

    ShapeBuilder(ResourceLocation id) {
        this.id = id;
    }

    public ShapeBuilder rows(String... rows) {
        int width = rows.length;
        if (width == 0 || width % 2 == 0) {
            throw new IllegalArgumentException("La forma " + id + " debe tener un numero impar de filas");
        }
        boolean hasPoint = false;
        for (String row : rows) {
            if (row.length() != width) {
                throw new IllegalArgumentException("La forma " + id + " debe ser cuadrada: cada fila mide " + width + " caracteres");
            }
            hasPoint |= row.indexOf('X') >= 0;
        }
        if (!hasPoint) {
            throw new IllegalArgumentException("La forma " + id + " necesita al menos una X");
        }
        this.rows = List.of(rows);
        return this;
    }

    @Override
    @HideFromJS
    public ResourceLocation dataPath() {
        return WitcheryIds.registryEntry(id, "circle_magic/shape");
    }

    @Override
    @HideFromJS
    public JsonElement toJson() {
        if (rows.isEmpty()) {
            throw new IllegalStateException("La forma " + id + " no tiene filas: usa rows(...)");
        }
        JsonArray array = new JsonArray();
        rows.forEach(array::add);
        return array;
    }
}
