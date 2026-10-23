package nadiendev.umc.crafting.client.render;

import nadiendev.umc.UltimateModCompats;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;

public class HoundRenderer extends WolfRenderer {

    private static final ResourceLocation EYES = ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, "textures/entity/hound_eyes.png");

    public HoundRenderer(EntityRendererProvider.Context context) {
        super(context);
        addLayer(new FamiliarEyesLayer<Wolf, WolfModel<Wolf>>(this, EYES));
    }
}
