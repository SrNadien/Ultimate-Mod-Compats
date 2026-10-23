package nadiendev.umc.creatures.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public final class CreaturesKeys {

    public static final String CATEGORY = "key.categories.umc.creatures";

    public static final KeyMapping TRANSFORM = new KeyMapping("key.umc.transform", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, CATEGORY);
    public static final KeyMapping ABILITY = new KeyMapping("key.umc.ability", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H, CATEGORY);

    private CreaturesKeys() {
    }
}
