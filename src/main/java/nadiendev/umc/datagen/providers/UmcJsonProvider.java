package nadiendev.umc.datagen.providers;

import com.google.gson.JsonElement;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import nadiendev.umc.datagen.DataModule;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

public class UmcJsonProvider implements DataProvider {

    private final PackOutput output;
    private final List<DataModule> modules;

    public UmcJsonProvider(PackOutput output, List<DataModule> modules) {
        this.output = output;
        this.modules = modules;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        UmcJsonSink sink = new UmcJsonSink();
        modules.forEach(module -> module.json(sink));
        Path root = output.getOutputFolder();
        CompletableFuture<?>[] writes = sink.files().entrySet().stream()
                .map((Map.Entry<String, JsonElement> entry) -> DataProvider.saveStable(cache, entry.getValue(), root.resolve(entry.getKey())))
                .toArray(CompletableFuture[]::new);
        return CompletableFuture.allOf(writes);
    }

    @Override
    public String getName() {
        return "Umc Json";
    }
}
