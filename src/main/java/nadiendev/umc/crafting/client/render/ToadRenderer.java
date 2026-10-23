package nadiendev.umc.crafting.client.render;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.crafting.client.model.ToadModel;
import nadiendev.umc.crafting.entity.ToadEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ToadRenderer extends MobRenderer<ToadEntity, ToadModel<ToadEntity>> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "textures/entity/toad.png");
    private static final ResourceLocation EYES = ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "textures/entity/toad_eyes.png");

    public ToadRenderer(EntityRendererProvider.Context context, boolean familiar) {
        super(context, new ToadModel<>(context.bakeLayer(ToadModel.LAYER)), 0.35F);
        if (familiar) {
            addLayer(new FamiliarEyesLayer<>(this, EYES));
        }
    }

    @Override
    public ResourceLocation getTextureLocation(ToadEntity entity) {
        return TEXTURE;
    }
}
