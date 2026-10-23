package nadiendev.umc.compat.kubejs.generatorgalore.fuel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.latvian.mods.rhino.Context;

public class SolidFuelList {

    private final GeneratorFuelMaps parent;
    private final JsonArray fuels = new JsonArray();

    SolidFuelList(GeneratorFuelMaps parent) {
        this.parent = parent;
    }

    public SolidFuelList fuel(Context cx, Object item, int burnTime, int generationRate, float consumptionRate) {
        JsonObject fuel = new JsonObject();
        fuel.add("item", FuelJson.ingredient(cx, item));
        fuel.addProperty("burnTime", burnTime);
        fuel.addProperty("generationRate", generationRate);
        fuel.addProperty("consumptionRate", consumptionRate);
        fuels.add(fuel);
        return this;
    }

    public SolidFuelList fuel(Context cx, Object item, int burnTime, int generationRate) {
        return fuel(cx, item, burnTime, generationRate, 1.0F);
    }

    public GeneratorFuelMaps done() {
        return parent;
    }

    JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.add("fuels", fuels);
        return object;
    }
}
