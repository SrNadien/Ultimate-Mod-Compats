package nadiendev.umc.spirit.client.render;

import nadiendev.umc.spirit.entity.Nightmare;
import nadiendev.umc.spirit.registry.SpiritWorld;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class NightmareEyesLayer extends EyesLayer<Nightmare, NightmareModel> {

    private static final RenderType EYES = RenderType.eyes(SpiritWorld.id("textures/entity/nightmare_eyes.png"));

    public NightmareEyesLayer(RenderLayerParent<Nightmare, NightmareModel> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return EYES;
    }
}
