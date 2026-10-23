package nadiendev.umc.datagen.providers;

import java.util.List;
import java.util.function.BiConsumer;
import nadiendev.umc.datagen.DataModule;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class UmcEntityLoot implements LootTableSubProvider {

    private final List<DataModule> modules;

    public UmcEntityLoot(List<DataModule> modules) {
        this.modules = modules;
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        modules.forEach(module -> module.entityLoot(output));
    }
}
