package nadiendev.umc.compat.kubejs.witchery.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.rhino.util.HideFromJS;
import nadiendev.umc.compat.kubejs.witchery.util.WitcheryIds;
import net.minecraft.resources.ResourceLocation;

public class AltarUpgradeBuilder implements WitcheryDataEntry {

    private final ResourceLocation id;
    private String type = WitcheryIds.ENCHANTED + ":torch";
    private String block;
    private double power;
    private double recharge;
    private int priority;

    AltarUpgradeBuilder(ResourceLocation id) {
        this.id = id;
    }

    public AltarUpgradeBuilder type(String type) {
        this.type = WitcheryIds.enchantedString(type);
        return this;
    }

    public AltarUpgradeBuilder block(String block) {
        this.block = WitcheryIds.withDefault(block, "minecraft").toString();
        return this;
    }

    public AltarUpgradeBuilder power(double power) {
        this.power = power;
        return this;
    }

    public AltarUpgradeBuilder recharge(double recharge) {
        this.recharge = recharge;
        return this;
    }

    public AltarUpgradeBuilder priority(int priority) {
        this.priority = priority;
        return this;
    }

    @Override
    @HideFromJS
    public ResourceLocation dataPath() {
        return WitcheryIds.registryEntry(id, "altar/upgrade");
    }

    @Override
    @HideFromJS
    public JsonElement toJson() {
        if (block == null) {
            throw new IllegalStateException("La mejora de altar " + id + " no tiene bloque: usa block(...)");
        }
        JsonObject json = new JsonObject();
        json.addProperty("type", type);
        json.addProperty("block", block);
        json.addProperty("power", power);
        json.addProperty("recharge", recharge);
        json.addProperty("priority", priority);
        return json;
    }
}
