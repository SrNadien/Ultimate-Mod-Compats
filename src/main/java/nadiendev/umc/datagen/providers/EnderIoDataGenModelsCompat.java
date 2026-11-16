package nadiendev.umc.datagen.providers;

import com.google.gson.JsonObject;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.material.UmcMaterial;
import nadiendev.umc.datagen.DataModule;
import nadiendev.umc.enderio.EnderIoRegistry;

public class EnderIoDataGenModelsCompat implements DataModule {

    @Override
    public void json(UmcJsonSink sink) {
        for (UmcMaterial material : UmcMaterial.values()) {
            sink.assets("models/item/" + material.id() + EnderIoRegistry.GRINDING_BALL, itemModel(material.id() + EnderIoRegistry.GRINDING_BALL));
            sink.assets("models/item/" + material.id() + EnderIoRegistry.CAPACITOR, itemModel(material.id() + EnderIoRegistry.CAPACITOR));
        }
    }

    private static JsonObject itemModel(String texture) {
        JsonObject model = new JsonObject();
        model.addProperty("parent", "minecraft:item/generated");
        JsonObject layers = new JsonObject();
        layers.addProperty("layer0", UltimateModCompats.MODID + ":item/" + texture);
        model.add("textures", layers);
        return model;
    }
}
