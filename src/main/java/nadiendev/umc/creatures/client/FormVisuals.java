package nadiendev.umc.creatures.client;

import javax.annotation.Nullable;
import nadiendev.umc.core.config.UmcConfig;
import nadiendev.umc.creatures.config.CreaturesConfig;
import nadiendev.umc.creatures.form.CreatureKind;

public final class FormVisuals {

    private static final int OPAQUE = 0xFF000000;

    private FormVisuals() {
    }

    public static boolean useModel(CreatureKind kind) {
        if (!UmcConfig.SPEC.isLoaded()) {
            return false;
        }
        return switch (kind) {
            case WEREWOLF -> CreaturesConfig.WEREWOLF_USE_MODEL.get();
            case VAMPIRE -> CreaturesConfig.VAMPIRE_USE_MODEL.get();
            case NONE -> false;
        };
    }

    public static boolean useModel(String name) {
        return useModel(CreatureKind.byName(name));
    }

    @Nullable
    public static Integer eyeColor(CreatureKind kind) {
        if (!UmcConfig.SPEC.isLoaded() || useModel(kind)) {
            return null;
        }
        return switch (kind) {
            case WEREWOLF -> CreaturesConfig.WEREWOLF_GLOWING_EYES.get() ? parse(CreaturesConfig.WEREWOLF_EYE_COLOR.get(), 0x33FF33) : null;
            case VAMPIRE -> CreaturesConfig.VAMPIRE_GLOWING_EYES.get() ? parse(CreaturesConfig.VAMPIRE_EYE_COLOR.get(), 0xFF1A1A) : null;
            case NONE -> null;
        };
    }

    private static int parse(String hex, int fallback) {
        try {
            return OPAQUE | (Integer.parseInt(hex.replace("#", "").trim(), 16) & 0xFFFFFF);
        } catch (NumberFormatException exception) {
            return OPAQUE | fallback;
        }
    }
}
