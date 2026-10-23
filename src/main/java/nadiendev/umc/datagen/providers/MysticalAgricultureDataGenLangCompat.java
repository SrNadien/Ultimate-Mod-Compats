package nadiendev.umc.datagen.providers;

import nadiendev.umc.datagen.DataModule;
import nadiendev.umc.mysticalagriculture.catalog.MysticalCropCatalog;
import nadiendev.umc.mysticalagriculture.catalog.MysticalCropDefinition;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class MysticalAgricultureDataGenLangCompat implements DataModule {

    @Override
    public void addEnglish(LanguageProvider lang) {
        for (MysticalCropDefinition definition : MysticalCropCatalog.all()) {
            lang.add(definition.translationKey(), definition.names().get("en_us"));
        }
    }

    @Override
    public void addSpanish(LanguageProvider lang) {
        for (MysticalCropDefinition definition : MysticalCropCatalog.all()) {
            lang.add(definition.translationKey(), definition.names().get("es_es"));
        }
    }
}
