package nadiendev.umc.compat.kubejs.generatorgalore.fuel;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.generator.KubeDataGenerator;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;

public class GeneratorFuelMaps {

    public static final ResourceLocation SOLID_FUEL_MAP = ResourceLocation.fromNamespaceAndPath("generatorgalore", "data_maps/block/solid_fuel_map");
    public static final ResourceLocation FLUID_FUEL_MAP = ResourceLocation.fromNamespaceAndPath("generatorgalore", "data_maps/block/fluid_fuel_map");

    private final Map<String, SolidFuelList> solid = new LinkedHashMap<>();
    private final Map<String, FluidFuelList> fluid = new LinkedHashMap<>();

    public SolidFuelList solid(String generator) {
        return solid.computeIfAbsent(generator, key -> new SolidFuelList(this));
    }

    public FluidFuelList fluid(String generator) {
        return fluid.computeIfAbsent(generator, key -> new FluidFuelList(this));
    }

    public GeneratorFuelMaps write(KubeDataGenerator generator) {
        if (!solid.isEmpty()) {
            JsonObject values = new JsonObject();
            solid.forEach((key, list) -> values.add(key, list.toJson()));
            generator.json(SOLID_FUEL_MAP, file(values));
        }
        if (!fluid.isEmpty()) {
            JsonObject values = new JsonObject();
            fluid.forEach((key, list) -> values.add(key, list.toJson()));
            generator.json(FLUID_FUEL_MAP, file(values));
        }
        return this;
    }

    private static JsonObject file(JsonObject values) {
        JsonObject file = new JsonObject();
        file.addProperty("replace", false);
        file.add("values", values);
        return file;
    }
}
