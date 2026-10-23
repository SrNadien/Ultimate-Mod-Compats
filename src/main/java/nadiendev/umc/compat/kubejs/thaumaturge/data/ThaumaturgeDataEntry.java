package nadiendev.umc.compat.kubejs.thaumaturge.data;

import com.google.gson.JsonElement;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;

public interface ThaumaturgeDataEntry {

    @HideFromJS
    ResourceLocation dataPath();

    @HideFromJS
    JsonElement toJson();
}
