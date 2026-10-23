package nadiendev.umc.datagen.providers;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.spirit.block.DreamLoomBlock;
import nadiendev.umc.spirit.block.SpiritPortalBlock;
import nadiendev.umc.spirit.registry.SpiritBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class SpiritBlockStates extends BlockStateProvider {

    public SpiritBlockStates(PackOutput output, ExistingFileHelper helper) {
        super(output, UltimateModCompats.MODID, helper);
    }

    @Override
    public String getName() {
        return "Spirit Block States";
    }

    private ResourceLocation blockTexture(String name) {
        return modLoc("block/" + name);
    }

    @Override
    protected void registerStatesAndModels() {
        Block stone = SpiritBlocks.SPIRIT_STONE.get();
        simpleBlockWithItem(stone, cubeAll(stone));

        Block soil = SpiritBlocks.SPIRIT_SOIL.get();
        simpleBlockWithItem(soil, models().cubeBottomTop("spirit_soil", blockTexture("spirit_soil_side"), blockTexture("spirit_soil_bottom"), blockTexture("spirit_soil_top")));

        Block log = SpiritBlocks.SPIRIT_LOG.get();
        axisBlock(SpiritBlocks.SPIRIT_LOG.get(), blockTexture("spirit_log"), blockTexture("spirit_log_top"));
        simpleBlockItem(log, models().getExistingFile(modLoc("block/spirit_log")));

        Block leaves = SpiritBlocks.SPIRIT_LEAVES.get();
        BlockModelBuilder leavesModel = models().singleTexture("spirit_leaves", mcLoc("block/leaves"), "all", blockTexture("spirit_leaves")).renderType("cutout_mipped");
        simpleBlockWithItem(leaves, leavesModel);

        Block planks = SpiritBlocks.SPIRIT_PLANKS.get();
        simpleBlockWithItem(planks, cubeAll(planks));

        plant(SpiritBlocks.SPIRIT_BLOOM.get(), "spirit_bloom");
        plant(SpiritBlocks.SPIRIT_GRASS.get(), "spirit_grass");

        portal();
        dreamCatcher();
        dreamLoom();
        covenAltar();
    }

    private void plant(Block block, String name) {
        ModelFile model = models().cross(name, blockTexture(name)).renderType("cutout");
        simpleBlock(block, model);
        itemModels().withExistingParent(name, mcLoc("item/generated")).texture("layer0", blockTexture(name));
    }

    private BlockModelBuilder flatBox(String name, int height, String side, String bottom, String top) {
        BlockModelBuilder builder = models().getBuilder(name).parent(new ModelFile.UncheckedModelFile(mcLoc("block/block")))
                .texture("particle", blockTexture(side))
                .texture("side", blockTexture(side))
                .texture("bottom", blockTexture(bottom))
                .texture("top", blockTexture(top));
        builder.element().from(0, 0, 0).to(16, height, 16)
                .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#bottom").cullface(Direction.DOWN).end()
                .face(Direction.UP).uvs(0, 0, 16, 16).texture("#top").end()
                .face(Direction.NORTH).uvs(0, 16 - height, 16, 16).texture("#side").cullface(Direction.NORTH).end()
                .face(Direction.SOUTH).uvs(0, 16 - height, 16, 16).texture("#side").cullface(Direction.SOUTH).end()
                .face(Direction.WEST).uvs(0, 16 - height, 16, 16).texture("#side").cullface(Direction.WEST).end()
                .face(Direction.EAST).uvs(0, 16 - height, 16, 16).texture("#side").cullface(Direction.EAST).end()
                .end();
        return builder;
    }

    private void portal() {
        Block block = SpiritBlocks.SPIRIT_PORTAL.get();
        BlockModelBuilder inactive = flatBox("spirit_portal", 3, "spirit_portal_side", "spirit_portal_side", "spirit_portal_top");
        BlockModelBuilder active = flatBox("spirit_portal_active", 3, "spirit_portal_side", "spirit_portal_side", "spirit_portal_top_active");
        getVariantBuilder(block)
                .partialState().with(SpiritPortalBlock.ACTIVE, false).modelForState().modelFile(inactive).addModel()
                .partialState().with(SpiritPortalBlock.ACTIVE, true).modelForState().modelFile(active).addModel();
        simpleBlockItem(block, inactive);
    }

    private int rotation(Direction direction) {
        return switch (direction) {
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
            default -> 0;
        };
    }

    private void dreamCatcher() {
        Block block = SpiritBlocks.DREAM_CATCHER.get();
        ResourceLocation texture = blockTexture("dream_catcher");
        BlockModelBuilder model = models().getBuilder("dream_catcher").parent(new ModelFile.UncheckedModelFile(mcLoc("block/block")))
                .texture("particle", texture)
                .texture("main", texture)
                .renderType("cutout");
        model.element().from(0, 0, 14).to(16, 16, 16)
                .face(Direction.NORTH).uvs(0, 0, 16, 16).texture("#main").end()
                .face(Direction.SOUTH).uvs(16, 0, 0, 16).texture("#main").end()
                .end();
        getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(model)
                .rotationY(rotation(state.getValue(HorizontalDirectionalBlock.FACING)))
                .build());
        itemModels().withExistingParent("dream_catcher", mcLoc("item/generated")).texture("layer0", texture);
    }

    private void box(BlockModelBuilder builder, float x1, float y1, float z1, float x2, float y2, float z2, String texture) {
        BlockModelBuilder.ElementBuilder element = builder.element().from(x1, y1, z1).to(x2, y2, z2);
        for (Direction direction : Direction.values()) {
            element.face(direction).uvs(0, 0, 16, 16).texture("#" + texture).end();
        }
        element.end();
    }

    private void dreamLoom() {
        Block block = SpiritBlocks.DREAM_LOOM.get();
        ModelFile[] models = new ModelFile[DreamLoomBlock.MAX_THREADS + 1];
        for (int threads = 0; threads <= DreamLoomBlock.MAX_THREADS; threads++) {
            BlockModelBuilder builder = models().getBuilder("dream_loom_" + threads).parent(new ModelFile.UncheckedModelFile(mcLoc("block/block")))
                    .texture("particle", blockTexture("dream_loom_wood"))
                    .texture("wood", blockTexture("dream_loom_wood"))
                    .texture("thread", blockTexture("dream_loom_thread"))
                    .renderType("cutout");
            box(builder, 0, 0, 0, 16, 3, 16, "wood");
            box(builder, 0, 3, 2, 2, 16, 14, "wood");
            box(builder, 14, 3, 2, 16, 16, 14, "wood");
            box(builder, 2, 13, 4, 14, 16, 12, "wood");
            box(builder, 2, 3, 3, 14, 5, 5, "wood");
            for (int sheet = 0; sheet < threads; sheet++) {
                float z = 5.5F + sheet * 2.5F;
                BlockModelBuilder.ElementBuilder element = builder.element().from(2, 5, z).to(14, 13, z + 0.5F);
                element.face(Direction.NORTH).uvs(0, 0, 16, 16).texture("#thread").end();
                element.face(Direction.SOUTH).uvs(16, 0, 0, 16).texture("#thread").end();
                element.end();
            }
            models[threads] = builder;
        }
        getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(models[state.getValue(DreamLoomBlock.THREADS)])
                .rotationY(rotation(state.getValue(HorizontalDirectionalBlock.FACING)))
                .build());
        simpleBlockItem(block, models[0]);
    }

    private void covenAltar() {
        Block block = SpiritBlocks.COVEN_ALTAR.get();
        BlockModelBuilder model = models().getBuilder("coven_altar").parent(new ModelFile.UncheckedModelFile(mcLoc("block/block")))
                .texture("particle", blockTexture("coven_altar_side"))
                .texture("side", blockTexture("coven_altar_side"))
                .texture("top", blockTexture("coven_altar_top"));
        BlockModelBuilder.ElementBuilder base = model.element().from(0, 0, 0).to(16, 3, 16);
        for (Direction direction : Direction.values()) {
            base.face(direction).uvs(0, 0, 16, direction.getAxis() == Direction.Axis.Y ? 16 : 3).texture(direction == Direction.UP ? "#top" : "#side").end();
        }
        base.end();
        BlockModelBuilder.ElementBuilder column = model.element().from(3, 3, 3).to(13, 10, 13);
        for (Direction direction : Direction.values()) {
            column.face(direction).uvs(3, 3, 13, 10).texture("#side").end();
        }
        column.end();
        BlockModelBuilder.ElementBuilder plate = model.element().from(0, 10, 0).to(16, 13, 16);
        for (Direction direction : Direction.values()) {
            plate.face(direction).uvs(0, 0, 16, direction.getAxis() == Direction.Axis.Y ? 16 : 3).texture(direction == Direction.UP ? "#top" : "#side").end();
        }
        plate.end();
        simpleBlockWithItem(block, model);
    }
}
