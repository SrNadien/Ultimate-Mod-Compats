package nadiendev.umc.compat.kubejs.generatorgalore.fuel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.latvian.mods.rhino.Context;

public class FluidFuelList {

    private final GeneratorFuelMaps parent;
    private final JsonArray fuels = new JsonArray();

    FluidFuelList(GeneratorFuelMaps parent) {
        this.parent = parent;
    }

    public FluidFuelList fuel(Context cx, Object fluid, double generationRate, double consumptionRate) {
        JsonObject fuel = new JsonObject();
        fuel.add("fluid", FuelJson.fluid(cx, fluid));
        fuel.addProperty("generationRate", generationRate);
        fuel.addProperty("consumptionRate", consumptionRate);
        fuels.add(fuel);
        return this;
    }

    public FluidFuelList fuel(Context cx, Object fluid, double generationRate) {
        return fuel(cx, fluid, generationRate, 1.0D);
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
