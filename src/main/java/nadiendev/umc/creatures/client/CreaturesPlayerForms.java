package nadiendev.umc.creatures.client;

import java.util.EnumMap;
import java.util.Map;
import nadiendev.umc.creatures.client.geo.CreatureGeoModel;
import nadiendev.umc.creatures.client.geo.FormAnimatable;
import nadiendev.umc.creatures.form.CreatureKind;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoReplacedEntityRenderer;

public final class CreaturesPlayerForms {

    private static final Map<CreatureKind, GeoReplacedEntityRenderer<AbstractClientPlayer, FormAnimatable>> GEO_RENDERERS = new EnumMap<>(CreatureKind.class);

    private CreaturesPlayerForms() {
    }

    public static void create(EntityRendererProvider.Context context) {
        GEO_RENDERERS.clear();
        GEO_RENDERERS.put(CreatureKind.WEREWOLF, geo(context, LocalModelAvailability.WEREWOLF));
        GEO_RENDERERS.put(CreatureKind.VAMPIRE, geo(context, LocalModelAvailability.VAMPIRE));
    }

    private static GeoReplacedEntityRenderer<AbstractClientPlayer, FormAnimatable> geo(EntityRendererProvider.Context context, String name) {
        return new GeoReplacedEntityRenderer<>(context, new CreatureGeoModel<FormAnimatable>(name), new FormAnimatable(name));
    }

    public static GeoReplacedEntityRenderer<AbstractClientPlayer, FormAnimatable> getGeo(CreatureKind kind) {
        if (!LocalModelAvailability.isAvailable(kind.getSerializedName())) {
            return null;
        }
        return GEO_RENDERERS.get(kind);
    }
}
