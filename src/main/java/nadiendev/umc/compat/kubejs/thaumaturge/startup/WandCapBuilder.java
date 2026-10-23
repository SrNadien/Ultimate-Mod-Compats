package nadiendev.umc.compat.kubejs.thaumaturge.startup;

import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeIds;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class WandCapBuilder {

    private final ResourceLocation id;
    private float baseCost = 1.0F;
    private float specialCost = 1.0F;
    private int craftCost = 1;
    private ResourceLocation texture;
    private final List<ResourceKey<Object>> specialAspects = new ArrayList<>();
    private final Map<ResourceKey<Object>, Float> aspectCosts = new LinkedHashMap<>();

    WandCapBuilder(ResourceLocation id) {
        this.id = id;
        this.texture = ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "textures/models/wand_cap_" + id.getPath() + ".png");
    }

    public WandCapBuilder baseCost(float modifier) {
        this.baseCost = modifier;
        return this;
    }

    public WandCapBuilder specialCost(float modifier, String... aspects) {
        this.specialCost = modifier;
        for (String aspect : aspects) {
            specialAspects.add(ThaumaturgeReflect.elementKey("aspect", ThaumaturgeIds.thaumaturge(aspect)));
        }
        return this;
    }

    public WandCapBuilder aspectCost(String aspect, float modifier) {
        aspectCosts.put(ThaumaturgeReflect.elementKey("aspect", ThaumaturgeIds.thaumaturge(aspect)), modifier);
        return this;
    }

    public WandCapBuilder craftCost(int craftCost) {
        this.craftCost = craftCost;
        return this;
    }

    public WandCapBuilder texture(String texture) {
        this.texture = ThaumaturgeIds.withDefault(texture, ThaumaturgeIds.KUBEJS);
        return this;
    }

    @HideFromJS
    ResourceLocation id() {
        return id;
    }

    @HideFromJS
    Object build() {
        Class<?> type = ThaumaturgeReflect.type("wands.WandCap");
        if (!aspectCosts.isEmpty()) {
            return ThaumaturgeReflect.newInstance(type, List.of(float.class, Map.class, int.class, ResourceLocation.class),
                    baseCost, Map.copyOf(aspectCosts), craftCost, texture);
        }
        return ThaumaturgeReflect.newInstance(type, List.of(float.class, List.class, float.class, int.class, ResourceLocation.class),
                baseCost, List.copyOf(specialAspects), specialCost, craftCost, texture);
    }
}
