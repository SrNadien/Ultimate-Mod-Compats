package nadiendev.umc.core.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import java.nio.file.Files;
import java.nio.file.Path;
import net.neoforged.fml.loading.FMLPaths;

public final class EarlyConfig {

    private static CommentedFileConfig snapshot;

    private EarlyConfig() {
    }

    public static boolean bool(String key, boolean fallback) {
        return snapshot().get(key) instanceof Boolean value ? value : fallback;
    }

    public static String text(String key, String fallback) {
        return snapshot().get(key) instanceof String value && !value.isBlank() ? value : fallback;
    }

    private static synchronized CommentedFileConfig snapshot() {
        if (snapshot == null) {
            Path path = FMLPaths.CONFIGDIR.get().resolve(UmcConfig.FILE);
            snapshot = CommentedFileConfig.builder(path).build();
            if (Files.exists(path)) {
                snapshot.load();
            }
        }
        return snapshot;
    }
}
