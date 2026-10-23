package nadiendev.umc.crafting.familiar;

import java.util.LinkedHashMap;
import java.util.Map;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.crafting.config.CraftingConfig;
import nadiendev.umc.crafting.config.SpeciesConfig;
import nadiendev.umc.crafting.entity.OwlEntity;
import nadiendev.umc.crafting.entity.RavenEntity;
import nadiendev.umc.crafting.entity.ToadEntity;
import nadiendev.umc.crafting.entity.familiar.FamiliarHound;
import nadiendev.umc.crafting.entity.familiar.FamiliarOwl;
import nadiendev.umc.crafting.entity.familiar.FamiliarRaven;
import nadiendev.umc.crafting.entity.familiar.FamiliarToad;
import nadiendev.umc.crafting.registry.CraftingEntities;
import net.favouriteless.enchanted.api.familiars.FamiliarType;
import net.favouriteless.enchanted.common.enchanted.familiars.EFamiliarTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Wolf;

public final class FamiliarTypes {

    public static final ResourceLocation TOAD_ID = id("toad");
    public static final ResourceLocation OWL_ID = id("owl");
    public static final ResourceLocation RAVEN_ID = id("raven");
    public static final ResourceLocation HOUND_ID = id("hound");

    private static final Map<ResourceLocation, SpeciesConfig> SPECIES = new LinkedHashMap<>();

    private static boolean registered;

    private FamiliarTypes() {
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, path);
    }

    public static synchronized void register() {
        if (registered) {
            return;
        }
        registered = true;

        EFamiliarTypes.register(TOAD_ID, new UmcFamiliarType<ToadEntity, FamiliarToad>(TOAD_ID, CraftingEntities.TOAD, CraftingEntities.FAMILIAR_TOAD, (from, familiar) -> { }));
        EFamiliarTypes.register(OWL_ID, new UmcFamiliarType<OwlEntity, FamiliarOwl>(OWL_ID, CraftingEntities.OWL, CraftingEntities.FAMILIAR_OWL, (from, familiar) -> { }));
        EFamiliarTypes.register(RAVEN_ID, new UmcFamiliarType<RavenEntity, FamiliarRaven>(RAVEN_ID, CraftingEntities.RAVEN, CraftingEntities.FAMILIAR_RAVEN, (from, familiar) -> { }));
        EFamiliarTypes.register(HOUND_ID, new UmcFamiliarType<Wolf, FamiliarHound>(HOUND_ID, () -> EntityType.WOLF, CraftingEntities.FAMILIAR_HOUND, FamiliarTypes::copyWolf));

        SPECIES.put(TOAD_ID, CraftingConfig.TOAD);
        SPECIES.put(OWL_ID, CraftingConfig.OWL);
        SPECIES.put(RAVEN_ID, CraftingConfig.RAVEN);
        SPECIES.put(HOUND_ID, CraftingConfig.HOUND);
    }

    public static SpeciesConfig species(FamiliarType<?, ?> type) {
        return SPECIES.get(type.getId());
    }

    public static Map<ResourceLocation, SpeciesConfig> all() {
        return SPECIES;
    }

    private static void copyWolf(Wolf from, FamiliarHound familiar) {
        CompoundTag tag = new CompoundTag();
        tag.putByte("CollarColor", (byte) from.getCollarColor().getId());
        tag.putString("variant", "minecraft:black");
        familiar.readAdditionalSaveData(tag);
    }
}
