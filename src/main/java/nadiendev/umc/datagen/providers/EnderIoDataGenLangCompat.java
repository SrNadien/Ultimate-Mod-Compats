package nadiendev.umc.datagen.providers;

import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.core.material.UmcMaterial;
import nadiendev.umc.core.registry.UmcFractalTabs;
import nadiendev.umc.datagen.DataModule;
import nadiendev.umc.enderio.EnderIoFractalTabs;
import nadiendev.umc.enderio.EnderIoRegistry;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class EnderIoDataGenLangCompat implements DataModule {

    private static final String[] KINDS_EN = {"Item", "Energy", "Fluid"};
    private static final String[] KINDS_ES = {"de objetos", "de energia", "de fluidos"};

    @Override
    public void addEnglish(LanguageProvider lang) {
        lang.add(UmcFractalTabs.sectionKey(EnderIoFractalTabs.ID), "EnderIO");
        for (UmcMaterial material : UmcMaterial.values()) {
            lang.add(item(material, EnderIoRegistry.GRINDING_BALL), material.english() + " Grinding Ball");
            lang.add(item(material, EnderIoRegistry.CAPACITOR), material.english() + " Capacitor");
            for (int kind = 0; kind < EnderIoRegistry.CONDUIT_KINDS.length; kind++) {
                lang.add(conduit(material, kind), material.english() + " " + KINDS_EN[kind] + " Conduit");
            }
        }
    }

    @Override
    public void addSpanish(LanguageProvider lang) {
        lang.add(UmcFractalTabs.sectionKey(EnderIoFractalTabs.ID), "EnderIO");
        for (UmcMaterial material : UmcMaterial.values()) {
            lang.add(item(material, EnderIoRegistry.GRINDING_BALL), "Bola de molienda de " + material.spanish());
            lang.add(item(material, EnderIoRegistry.CAPACITOR), "Capacitor de " + material.spanish());
            for (int kind = 0; kind < EnderIoRegistry.CONDUIT_KINDS.length; kind++) {
                lang.add(conduit(material, kind), "Conducto " + KINDS_ES[kind] + " de " + material.spanish());
            }
        }
    }

    private static String item(UmcMaterial material, String suffix) {
        return "item." + UltimateModCompats.MODID + "." + material.id() + suffix;
    }

    public static String conduit(UmcMaterial material, int kind) {
        return "conduit." + UltimateModCompats.MODID + "." + EnderIoRegistry.conduitId(material, EnderIoRegistry.CONDUIT_KINDS[kind]);
    }
}
