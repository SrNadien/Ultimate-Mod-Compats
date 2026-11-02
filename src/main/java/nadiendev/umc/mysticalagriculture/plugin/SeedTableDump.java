package nadiendev.umc.mysticalagriculture.plugin;

import com.blakebr0.mysticalagriculture.api.crop.Crop;
import com.blakebr0.mysticalagriculture.api.registry.ICropRegistry;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Development helper: when the environment variable {@code UMC_DUMP_SEEDS} holds a file path, the
 * seeds of every registered crop are written there as a Markdown table grouped by tier.
 */
final class SeedTableDump {

    private static final Logger LOGGER = LoggerFactory.getLogger(SeedTableDump.class);
    private static final String ENVIRONMENT_VARIABLE = "UMC_DUMP_SEEDS";

    private SeedTableDump() {
    }

    static void writeIfRequested(ICropRegistry registry) {
        String target = System.getenv(ENVIRONMENT_VARIABLE);
        if (target == null || target.isBlank()) {
            return;
        }
        Map<Integer, List<Crop>> byTier = new TreeMap<>();
        for (Crop crop : registry.getCrops()) {
            byTier.computeIfAbsent(crop.getTier().getValue(), tier -> new java.util.ArrayList<>()).add(crop);
        }
        StringBuilder text = new StringBuilder();
        byTier.forEach((tier, crops) -> {
            text.append("## Tier ").append(tier).append(" (").append(crops.size()).append(")\n\n");
            text.append("| Seed | Crop | Mod |\n| --- | --- | --- |\n");
            crops.stream()
                    .sorted(Comparator.comparing((Crop crop) -> crop.getId().toString()))
                    .forEach(crop -> text.append("| `").append(crop.getId().getPath()).append("_seeds` | `")
                            .append(crop.getId()).append("` | ").append(crop.getModId()).append(" |\n"));
            text.append('\n');
        });
        text.append("Total: ").append(byTier.values().stream().mapToInt(List::size).sum()).append(" crops in ")
                .append(byTier.keySet().stream().map(String::valueOf).collect(Collectors.joining(", "))).append('\n');
        try {
            Files.writeString(Path.of(target), text.toString(), StandardCharsets.UTF_8);
            LOGGER.info("Seed table written to {}", target);
        } catch (IOException exception) {
            LOGGER.warn("Could not write the seed table to {}", target, exception);
        }
    }
}
