package nadiendev.umc.datagen.providers;

import com.google.gson.JsonObject;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.material.UmcMaterial;
import nadiendev.umc.core.registry.UmcFractalTabs;
import nadiendev.umc.datagen.DataModule;
import nadiendev.umc.solarflux.SolarFluxFractalTabs;
import nadiendev.umc.solarflux.SolarFluxRegistry;
import net.neoforged.neoforge.common.data.LanguageProvider;

/** Lang and item models of the mirrors, the coatings and the panels of Solar Flux Reborn. */
public class SolarFluxDataGenCompat implements DataModule {

    @Override
    public void addEnglish(LanguageProvider lang) {
        lang.add(UmcFractalTabs.sectionKey(SolarFluxFractalTabs.ID), "Solar Flux");
        for (UmcMaterial material : UmcMaterial.values()) {
            lang.add(panelKey(material), material.english() + " Solar Panel");
            lang.add(itemKey(SolarFluxRegistry.mirrorId(material)), material.english() + " Mirror");
            lang.add(itemKey(SolarFluxRegistry.coatingId(material)), material.english() + " Coating");
        }
    }

    @Override
    public void addSpanish(LanguageProvider lang) {
        lang.add(UmcFractalTabs.sectionKey(SolarFluxFractalTabs.ID), "Solar Flux");
        for (UmcMaterial material : UmcMaterial.values()) {
            lang.add(panelKey(material), "Panel solar de " + material.spanish());
            lang.add(itemKey(SolarFluxRegistry.mirrorId(material)), "Espejo de " + material.spanish());
            lang.add(itemKey(SolarFluxRegistry.coatingId(material)), "Recubrimiento de " + material.spanish());
        }
    }

    @Override
    public void json(UmcJsonSink sink) {
        for (UmcMaterial material : UmcMaterial.values()) {
            for (String id : new String[]{SolarFluxRegistry.mirrorId(material), SolarFluxRegistry.coatingId(material)}) {
                JsonObject model = new JsonObject();
                model.addProperty("parent", "minecraft:item/generated");
                JsonObject layers = new JsonObject();
                layers.addProperty("layer0", UltimateModCompats.MODID + ":item/" + id);
                model.add("textures", layers);
                sink.assets("models/item/" + id, model);
            }
        }
    }

    /** The name of the panel block, which Solar Flux Reborn registers as {@code solarflux:sp_umc.<id>}. */
    private static String panelKey(UmcMaterial material) {
        return "block.solarflux.sp_" + SolarFluxRegistry.panelName(material);
    }

    private static String itemKey(String id) {
        return "item." + UltimateModCompats.MODID + "." + id;
    }
}
