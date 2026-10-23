package nadiendev.umc.compat.kubejs.thaumaturge.data;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.generator.KubeDataGenerator;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.function.Consumer;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeIds;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeJson;
import net.minecraft.resources.ResourceLocation;

public class ResearchKubeEvent extends ThaumaturgeDataEvent {

    private static final String ENTRY_DIRECTORY = "thaumaturge/" + ThaumaturgeRegistries.RESEARCH_ENTRY;
    private static final String CATEGORY_DIRECTORY = "thaumaturge/" + ThaumaturgeRegistries.RESEARCH_CATEGORY;

    @HideFromJS
    public ResearchKubeEvent(KubeDataGenerator generator) {
        super(generator);
    }

    public ResearchEntryBuilder create(String id) {
        return track(new ResearchEntryBuilder(ThaumaturgeIds.owned(id), new JsonObject()));
    }

    public ResearchEntryBuilder modify(String id) {
        ResourceLocation location = ThaumaturgeIds.thaumaturge(id);
        JsonObject existing = ThaumaturgeJson.readResource(location, ENTRY_DIRECTORY);
        if (existing == null) {
            throw new IllegalArgumentException("No existe la entrada de investigacion " + location + " dentro de los recursos de Thaumaturge");
        }
        return track(new ResearchEntryBuilder(location, existing));
    }

    public ResearchKubeEvent modify(String id, Consumer<ResearchEntryBuilder> edit) {
        edit.accept(modify(id));
        return this;
    }

    public ResearchCategoryBuilder createCategory(String id) {
        return track(new ResearchCategoryBuilder(ThaumaturgeIds.owned(id), new JsonObject()));
    }

    public ResearchCategoryBuilder modifyCategory(String id) {
        ResourceLocation location = ThaumaturgeIds.thaumaturge(id);
        JsonObject existing = ThaumaturgeJson.readResource(location, CATEGORY_DIRECTORY);
        if (existing == null) {
            throw new IllegalArgumentException("No existe la categoria de investigacion " + location + " dentro de los recursos de Thaumaturge");
        }
        return track(new ResearchCategoryBuilder(location, existing));
    }
}
