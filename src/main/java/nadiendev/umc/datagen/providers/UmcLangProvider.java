package nadiendev.umc.datagen.providers;

import java.util.List;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.datagen.DataModule;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class UmcLangProvider extends LanguageProvider {

    private final List<DataModule> modules;
    private final boolean spanish;

    public UmcLangProvider(PackOutput output, String locale, List<DataModule> modules) {
        super(output, UltimateModCompats.MODID, locale);
        this.modules = modules;
        this.spanish = locale.startsWith("es_");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + UltimateModCompats.MODID + ".main", "Ultimate Mod Compats");
        modules.forEach(module -> {
            if (spanish) {
                module.addSpanish(this);
            } else {
                module.addEnglish(this);
            }
        });
    }
}
