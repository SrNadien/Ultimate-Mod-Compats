package nadiendev.umc.creatures.registry;

import nadiendev.umc.creatures.form.CreatureKind;
import nadiendev.umc.creatures.item.GrimoireItem;
import nadiendev.umc.creatures.item.NecromancerScepterItem;
import nadiendev.umc.creatures.item.PurifyingTonicItem;
import nadiendev.umc.creatures.item.SilverTier;
import nadiendev.umc.creatures.item.SummoningHornItem;
import nadiendev.umc.creatures.item.TooltipItem;
import nadiendev.umc.creatures.item.TooltipSwordItem;
import nadiendev.umc.creatures.item.TransformCharmItem;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;

public final class CreaturesItems {

    public static final DeferredItem<Item> SILVER_INGOT = CreaturesRegistry.ITEMS.registerSimpleItem("silver_ingot");
    public static final DeferredItem<SwordItem> SILVER_SWORD = CreaturesRegistry.ITEMS.register("silver_sword",
            () -> new TooltipSwordItem(SilverTier.INSTANCE, new Item.Properties().attributes(SwordItem.createAttributes(SilverTier.INSTANCE, 3, -2.4F))));
    public static final DeferredItem<TooltipItem> WOODEN_STAKE = CreaturesRegistry.ITEMS.register("wooden_stake",
            () -> new TooltipItem(new Item.Properties().durability(60).attributes(ItemAttributeModifiers.builder()
                    .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, 3.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                    .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, -2.2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                    .build())));

    public static final DeferredItem<Item> GRAVE_DUST = CreaturesRegistry.ITEMS.registerSimpleItem("grave_dust");
    public static final DeferredItem<Item> ECTOPLASM = CreaturesRegistry.ITEMS.registerSimpleItem("ectoplasm");
    public static final DeferredItem<Item> IMP_HORN = CreaturesRegistry.ITEMS.registerSimpleItem("imp_horn");
    public static final DeferredItem<Item> WEREWOLF_FANG = CreaturesRegistry.ITEMS.registerSimpleItem("werewolf_fang");
    public static final DeferredItem<Item> VAMPIRE_FANG = CreaturesRegistry.ITEMS.registerSimpleItem("vampire_fang");

    public static final DeferredItem<PurifyingTonicItem> PURIFYING_TONIC = CreaturesRegistry.ITEMS.register("purifying_tonic",
            () -> new PurifyingTonicItem(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<TransformCharmItem> LUNAR_TALISMAN = CreaturesRegistry.ITEMS.register("lunar_talisman",
            () -> new TransformCharmItem(CreatureKind.WEREWOLF, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<TransformCharmItem> BLOOD_SIGIL = CreaturesRegistry.ITEMS.register("blood_sigil",
            () -> new TransformCharmItem(CreatureKind.VAMPIRE, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<NecromancerScepterItem> NECROMANCER_SCEPTER = CreaturesRegistry.ITEMS.register("necromancer_scepter",
            () -> new NecromancerScepterItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<SummoningHornItem> SUMMONING_HORN = CreaturesRegistry.ITEMS.register("summoning_horn",
            () -> new SummoningHornItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<GrimoireItem> DEMONOMICON = CreaturesRegistry.ITEMS.register("demonomicon",
            () -> new GrimoireItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<DeferredSpawnEggItem> WEREWOLF_SPAWN_EGG = CreaturesRegistry.ITEMS.register("werewolf_spawn_egg",
            () -> new DeferredSpawnEggItem(CreaturesEntities.WEREWOLF, 0x4B3F36, 0xFFD23A, new Item.Properties()));
    public static final DeferredItem<DeferredSpawnEggItem> VAMPIRE_SPAWN_EGG = CreaturesRegistry.ITEMS.register("vampire_spawn_egg",
            () -> new DeferredSpawnEggItem(CreaturesEntities.VAMPIRE, 0x1B1722, 0x9B1B30, new Item.Properties()));
    public static final DeferredItem<DeferredSpawnEggItem> GHOST_SPAWN_EGG = CreaturesRegistry.ITEMS.register("ghost_spawn_egg",
            () -> new DeferredSpawnEggItem(CreaturesEntities.GHOST, 0xB8D8F0, 0x5EE8FF, new Item.Properties()));
    public static final DeferredItem<DeferredSpawnEggItem> IMP_SPAWN_EGG = CreaturesRegistry.ITEMS.register("imp_spawn_egg",
            () -> new DeferredSpawnEggItem(CreaturesEntities.IMP, 0xB3321F, 0xFFC23A, new Item.Properties()));
    public static final DeferredItem<DeferredSpawnEggItem> ENT_SPAWN_EGG = CreaturesRegistry.ITEMS.register("ent_spawn_egg",
            () -> new DeferredSpawnEggItem(CreaturesEntities.ENT, 0x5A3F28, 0x3E7A2B, new Item.Properties()));

    private CreaturesItems() {
    }

    static void init() {
    }
}
