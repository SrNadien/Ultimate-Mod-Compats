package nadiendev.umc.compat.kubejs.thaumaturge.startup;

import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.List;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeIds;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class GolemTraitBuilder {

    private final ResourceLocation id;
    private ResourceLocation icon;
    private ResourceKey<Object> opposite;

    GolemTraitBuilder(ResourceLocation id) {
        this.id = id;
    }

    public GolemTraitBuilder icon(String texture) {
        this.icon = ThaumaturgeIds.withDefault(texture, ThaumaturgeIds.KUBEJS);
        return this;
    }

    public GolemTraitBuilder opposite(String trait) {
        this.opposite = ThaumaturgeReflect.elementKey("golem_trait", ThaumaturgeIds.thaumaturge(trait));
        return this;
    }

    @HideFromJS
    ResourceLocation id() {
        return id;
    }

    @HideFromJS
    Object build() {
        Class<?> type = ThaumaturgeReflect.type("golems.GolemTrait");
        ResourceLocation texture = icon != null ? icon : ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "textures/misc/golem/tag_" + id.getPath() + ".png");
        return ThaumaturgeReflect.newInstance(type, List.of(ResourceLocation.class, ResourceKey.class), texture, opposite);
    }
}
