package nadiendev.umc.crafting.rite;

import java.util.List;
import java.util.UUID;
import nadiendev.umc.crafting.config.CraftingConfig;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.BindFamiliarRite;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite.BaseRiteParams;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite.RiteParams;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;

public class BindSpeciesRite extends BindFamiliarRite {

    private final EntityType<?> species;

    public BindSpeciesRite(BaseRiteParams baseParams, RiteParams params, EntityType<?> species) {
        super(baseParams, params);
        this.species = species;
    }

    @Override
    protected UUID findTargetUUID(ServerLevel level, BlockPos pos, RiteParams params) {
        if (!CraftingConfig.ENABLED.get() || !CraftingConfig.FAMILIARS_ENABLED.get() || !CraftingConfig.RITES_ENABLED.get()) {
            return null;
        }
        List<TamableAnimal> candidates = level.getEntitiesOfClass(TamableAnimal.class, type.getBounds(pos), animal -> animal.getType() == species);
        for (TamableAnimal animal : candidates) {
            if (animal.getOwnerUUID() != null && animal.getOwnerUUID().equals(params.caster)) {
                return animal.getUUID();
            }
        }
        return null;
    }
}
