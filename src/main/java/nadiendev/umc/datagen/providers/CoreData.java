package nadiendev.umc.datagen.providers;

import nadiendev.umc.core.registry.UmcFractalTabs;
import nadiendev.umc.datagen.DataModule;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class CoreData implements DataModule {

    @Override
    public void gather(GatherDataEvent event) {
        event.getGenerator().addProvider(event.includeClient(), new CoreItemModels(event.getGenerator().getPackOutput(), event.getExistingFileHelper()));
    }

    @Override
    public void addEnglish(LanguageProvider lang) {
        lang.add("item.umc.tab_icon", "Space Cat");
        lang.add("item.umc.example_item", "Example Item");
        lang.add(UmcFractalTabs.sectionKey(UmcFractalTabs.WITCHCRAFT), "Witchcraft");
        lang.add(UmcFractalTabs.sectionKey(UmcFractalTabs.EXAMPLE), "Example");
        lang.add("umc.jei.conversion_entity", "Right-click on: %s");
    }

    @Override
    public void addSpanish(LanguageProvider lang) {
        lang.add("item.umc.tab_icon", "Gato Espacial");
        lang.add("item.umc.example_item", "Objeto de ejemplo");
        lang.add(UmcFractalTabs.sectionKey(UmcFractalTabs.WITCHCRAFT), "Brujeria");
        lang.add(UmcFractalTabs.sectionKey(UmcFractalTabs.EXAMPLE), "Ejemplo");
        lang.add("umc.jei.conversion_entity", "Clic derecho sobre: %s");
    }
}
