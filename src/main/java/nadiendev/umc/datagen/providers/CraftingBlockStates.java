package nadiendev.umc.datagen.providers;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.crafting.block.WitchCropBlock;
import nadiendev.umc.crafting.registry.CraftingBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class CraftingBlockStates extends BlockStateProvider {

    public CraftingBlockStates(PackOutput output, ExistingFileHelper helper) {
        super(output, UltimateModCompats.MODID, helper);
    }

    @Override
    public String getName() {
        return "Crafting Block States";
    }

    @Override
    protected void registerStatesAndModels() {
        crop(CraftingBlocks.WORMWOOD, "wormwood");
        crop(CraftingBlocks.VERVAIN, "vervain");
        flower(CraftingBlocks.MOONBLOOM, "moonbloom");
        flower(CraftingBlocks.MUGWORT, "mugwort");
    }

    private void crop(DeferredBlock<WitchCropBlock> block, String name) {
        ModelFile[] stages = new ModelFile[WitchCropBlock.MAX_AGE + 1];
        for (int stage = 0; stage < stages.length; stage++) {
            String modelName = name + "_stage" + stage;
            stages[stage] = models().crop(modelName, modLoc("block/" + modelName)).renderType("cutout");
        }
        getVariantBuilder(block.get()).forAllStates(state -> ConfiguredModel.builder().modelFile(stages[state.getValue(WitchCropBlock.AGE)]).build());
    }

    private void flower(DeferredBlock<Block> block, String name) {
        simpleBlock(block.get(), models().cross(name, modLoc("block/" + name)).renderType("cutout"));
    }
}
