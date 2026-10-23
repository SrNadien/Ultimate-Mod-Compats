package nadiendev.umc.compat.kubejs.witchery.data;

import com.google.gson.JsonElement;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;

public interface WitcheryDataEntry {

    @HideFromJS
    ResourceLocation dataPath();

    @HideFromJS
    JsonElement toJson();
}
