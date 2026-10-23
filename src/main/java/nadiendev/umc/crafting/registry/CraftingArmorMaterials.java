package nadiendev.umc.crafting.registry;

import java.util.EnumMap;
import java.util.List;
import nadiendev.umc.UltimateModCompats;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public final class CraftingArmorMaterials {

    public static final Holder<ArmorMaterial> WITCH_HAT = register("witch_hat", ArmorItem.Type.HELMET, 2);
    public static final Holder<ArmorMaterial> WITCH_ROBE = register("witch_robe", ArmorItem.Type.CHESTPLATE, 4);
    public static final Holder<ArmorMaterial> WITCH_CLOAK = register("witch_cloak", ArmorItem.Type.CHESTPLATE, 3);
    public static final Holder<ArmorMaterial> WITCH_BOOTS = register("witch_boots", ArmorItem.Type.BOOTS, 2);

    private CraftingArmorMaterials() {
    }

    private static Holder<ArmorMaterial> register(String name, ArmorItem.Type type, int defense) {
        return CraftingRegistry.ARMOR_MATERIALS.register(name, () -> new ArmorMaterial(
                Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                    for (ArmorItem.Type value : ArmorItem.Type.values()) {
                        map.put(value, value == type ? defense : 0);
                    }
                }),
                20,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                () -> Ingredient.of(CraftingItems.WITCH_CLOTH.get()),
                List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, name))),
                0.0F,
                0.0F));
    }

    public static void init() {
    }
}
