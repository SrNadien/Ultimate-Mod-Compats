package nadiendev.umc.creatures.registry;

import nadiendev.umc.UltimateModCompats;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public final class CreaturesTags {

    public static final TagKey<Item> SILVER_WEAPONS = TagKey.create(Registries.ITEM, id("silver_weapons"));
    public static final TagKey<Item> STAKES = TagKey.create(Registries.ITEM, id("stakes"));
    public static final TagKey<EntityType<?>> WEREWOLVES = TagKey.create(Registries.ENTITY_TYPE, id("werewolves"));
    public static final TagKey<EntityType<?>> VAMPIRES = TagKey.create(Registries.ENTITY_TYPE, id("vampires"));
    public static final TagKey<EntityType<?>> SERVANTS = TagKey.create(Registries.ENTITY_TYPE, id("servants"));

    private CreaturesTags() {
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, path);
    }
}
