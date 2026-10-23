package nadiendev.umc.spirit.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public final class SpiritKeys {

    public static final String CATEGORY = "key.categories.umc_spirit";

    public static final KeyMapping CYCLE_INFUSION = new KeyMapping("key.umc.cycle_infusion", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, CATEGORY);
    public static final KeyMapping SPIRIT_RETURN = new KeyMapping("key.umc.spirit_return", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_B, CATEGORY);

    private SpiritKeys() {
    }
}
