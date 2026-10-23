package nadiendev.umc.spirit.client.render;

import nadiendev.umc.spirit.entity.Nightmare;
import nadiendev.umc.spirit.registry.SpiritWorld;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class NightmareRenderer extends MobRenderer<Nightmare, NightmareModel> {

    private static final ResourceLocation TEXTURE = SpiritWorld.id("textures/entity/nightmare.png");

    public NightmareRenderer(EntityRendererProvider.Context context) {
        super(context, new NightmareModel(context.bakeLayer(NightmareModel.LAYER)), 0.45F);
        addLayer(new NightmareEyesLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Nightmare entity) {
        return TEXTURE;
    }
}
