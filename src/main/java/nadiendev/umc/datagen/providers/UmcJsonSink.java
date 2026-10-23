package nadiendev.umc.datagen.providers;

import com.google.gson.JsonElement;
import java.util.LinkedHashMap;
import java.util.Map;
import nadiendev.umc.UltimateModCompats;

public final class UmcJsonSink {

    private final Map<String, JsonElement> files = new LinkedHashMap<>();

    public void data(String path, JsonElement json) {
        raw("data/" + UltimateModCompats.MODID + "/" + path + ".json", json);
    }

    public void data(String namespace, String path, JsonElement json) {
        raw("data/" + namespace + "/" + path + ".json", json);
    }

    public void assets(String path, JsonElement json) {
        raw("assets/" + UltimateModCompats.MODID + "/" + path + ".json", json);
    }

    public void raw(String pathFromRoot, JsonElement json) {
        if (files.putIfAbsent(pathFromRoot, json) != null) {
            throw new IllegalStateException("Duplicate generated file: " + pathFromRoot);
        }
    }

    Map<String, JsonElement> files() {
        return files;
    }
}
