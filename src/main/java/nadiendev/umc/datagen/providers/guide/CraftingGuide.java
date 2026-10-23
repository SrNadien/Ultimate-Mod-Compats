package nadiendev.umc.datagen.providers.guide;

import static nadiendev.umc.datagen.providers.guide.GuideJson.array;
import static nadiendev.umc.datagen.providers.guide.GuideJson.icon;
import static nadiendev.umc.datagen.providers.guide.GuideJson.object;
import static nadiendev.umc.datagen.providers.guide.GuideJson.with;

import com.google.gson.JsonObject;
import java.util.List;
import nadiendev.umc.datagen.providers.UmcJsonSink;

public final class CraftingGuide {

    private static final List<String> LOCALES = List.of("en_us", "es_ar", "es_mx", "es_es");

    private final UmcJsonSink sink;
    private final String locale;
    private final boolean spanish;

    private CraftingGuide(UmcJsonSink sink, String locale) {
        this.sink = sink;
        this.locale = locale;
        this.spanish = locale.startsWith("es_");
    }

    public static void generate(UmcJsonSink sink) {
        JsonObject book = object();
        with(book, "type", object("id", "enchanted:classic"));
        with(book, "creative_tab", "umc:main");
        with(book, "landing_text", "book.landing_text.umc.guide");
        with(book, "subtitle", "book.subtitle.umc.guide");
        with(book, "texture", "enchanted:art_of_witchcraft");
        with(book, "title", "book.title.umc.guide");
        sink.data("modopedia/books/guide", book);
        sink.data("recipe/guide_book", bookRecipe());
        for (String locale : LOCALES) {
            new CraftingGuide(sink, locale).write();
        }
    }

    private static JsonObject bookRecipe() {
        JsonObject recipe = object();
        with(recipe, "type", "minecraft:crafting_shaped");
        with(recipe, "category", "equipment");
        with(recipe, "pattern", array("IB", "FS"));
        JsonObject key = object();
        with(key, "I", object("item", "minecraft:ink_sac"));
        with(key, "B", object("item", "minecraft:book"));
        with(key, "F", object("item", "minecraft:feather"));
        with(key, "S", object("item", "umc:wormwood_sprig"));
        with(recipe, "key", key);
        JsonObject result = object();
        with(result, "count", 1);
        with(result, "id", "modopedia:book");
        with(result, "components", object("modopedia:book", "umc:guide"));
        with(recipe, "result", result);
        return recipe;
    }

    private String t(String english, String spanishText) {
        return spanish ? spanishText : english;
    }

    private void category(String id, String title, String landing, String iconId, int sort, String... entries) {
        JsonObject category = object();
        with(category, "entries", array((Object[]) entries));
        with(category, "icon", icon(iconId));
        with(category, "landing_text", landing);
        with(category, "sort_num", sort);
        with(category, "title", title);
        sink.raw("assets/umc/modopedia/books/guide/" + locale + "/categories/" + id + ".json", category);
    }

    private void entry(String id, String iconId, String[] assigned, String title, JsonObject... pages) {
        JsonObject entry = object();
        with(entry, "assigned_items", array((Object[]) assigned));
        with(entry, "icon", icon(iconId));
        with(entry, "pages", array((Object[]) pages));
        with(entry, "title", title);
        sink.raw("assets/umc/modopedia/books/guide/" + locale + "/entries/" + id + ".json", entry);
    }

    private static JsonObject page(JsonObject... components) {
        return object("components", array((Object[]) components));
    }

    private static JsonObject text(String header, String body) {
        JsonObject component = object();
        with(component, "header", header);
        with(component, "template", "modopedia:page/headered_text");
        with(component, "text", body);
        return component;
    }

    private static JsonObject crafting(String recipe, int y) {
        JsonObject component = object();
        with(component, "recipe", recipe);
        with(component, "template", "modopedia:recipe/crafting");
        with(component, "y", y);
        return component;
    }

    private static JsonObject nested(JsonObject... inner) {
        return object("components", array((Object[]) inner));
    }

    private static JsonObject template(String template, String recipe) {
        JsonObject component = object();
        with(component, "recipe", recipe);
        with(component, "template", template);
        return component;
    }

    private static JsonObject rite(String rite) {
        JsonObject component = object();
        with(component, "rite", rite);
        with(component, "template", "enchanted:page/rite");
        return component;
    }

    private static JsonObject entity(String entity, double scale, String body) {
        JsonObject component = object();
        with(component, "entity", entity);
        with(component, "scale", scale);
        with(component, "template", "modopedia:page/entity");
        with(component, "text", body);
        return component;
    }

    private static JsonObject block(String block, String body) {
        JsonObject component = object();
        with(component, "block", block);
        with(component, "template", "modopedia:page/block");
        with(component, "text", body);
        return component;
    }

    private void write() {
        familiars();
        clothing();
        plants();
        rituals();
        generic();
    }

    private void rituals() {
        category("rituals", t("Rituals", "Rituales"),
                t("Some work is too great for a kettle.\n\nThese rites are cast inside a chalk circle, with the offerings laid out and enough altar power to feed them.",
                        "Hay obras demasiado grandes para un caldero.\n\nEstos ritos se realizan dentro de un círculo de tiza, con las ofrendas dispuestas y poder de altar suficiente para alimentarlos."),
                "umc:grave_dust", 5, "rituals/overview", "rituals/zombies", "rituals/skeletons", "rituals/ghost", "rituals/imps", "rituals/ent",
                "rituals/scepter", "rituals/cleanse", "rituals/moon", "rituals/blood", "rituals/spirit");

        entry("rituals/overview", "umc:grave_dust", new String[0], t("Casting Rites", "Realizar ritos"),
                page(text(t("Rites", "Ritos"),
                        t("Draw the circle shown in the rite, set the offerings on it and make sure an altar nearby holds enough power.\n\nEvery rite page lists its offerings, its circle and the power it costs.",
                                "Dibuja el círculo que indica el rito, coloca las ofrendas sobre él y asegúrate de que un altar cercano tenga poder suficiente.\n\nCada página de rito muestra sus ofrendas, su círculo y el poder que cuesta."))),
                page(text(t("Familiar Rites", "Ritos de familiares"),
                        t("The rites that bind familiars are described in the Familiars chapter.",
                                "Los ritos que ligan familiares se describen en el capítulo de Familiares."))));

        entry("rituals/zombies", "minecraft:rotten_flesh", new String[0], t("Zombie Servants", "Siervos zombi"),
                page(text(t("Zombie Servants", "Siervos zombi"),
                        t("Raise a few zombies that obey you and fight at your side until they fade away.",
                                "Levanta unos cuantos zombis que te obedecen y luchan a tu lado hasta que se desvanecen."))),
                page(rite("enchanted:summon_zombie_servants")));

        entry("rituals/skeletons", "minecraft:bone", new String[0], t("Skeleton Servants", "Siervos esqueleto"),
                page(text(t("Skeleton Servants", "Siervos esqueleto"),
                        t("Call skeletons from the grave to serve you for a while.",
                                "Llama a esqueletos de la tumba para que te sirvan durante un tiempo."))),
                page(rite("enchanted:summon_skeleton_servants")));

        entry("rituals/ghost", "umc:ectoplasm", new String[]{"umc:ectoplasm"}, t("Ghost", "Fantasma"),
                page(text(t("Ghost", "Fantasma"),
                        t("Summon a ghost. It takes little harm from blows and its touch can blind.",
                                "Invoca a un fantasma. Los golpes le hacen poco daño y su toque puede cegar."))),
                page(rite("enchanted:summon_ghost")));

        entry("rituals/imps", "umc:imp_horn", new String[]{"umc:imp_horn"}, t("Imps", "Imps"),
                page(text(t("Imps", "Imps"),
                        t("Call imps to your side. They throw small fireballs and set their target alight.",
                                "Llama a imps a tu lado. Lanzan pequeñas bolas de fuego y prenden a su objetivo."))),
                page(rite("enchanted:summon_imps")));

        entry("rituals/ent", "enchanted:ent_twig", new String[0], t("Ent", "Ent"),
                page(text(t("Ent", "Ent"),
                        t("Awaken a walking tree. It fears fire and axes.",
                                "Despierta a un árbol andante. Teme al fuego y a las hachas."))),
                page(rite("enchanted:summon_ent")));

        entry("rituals/scepter", "umc:necromancer_scepter", new String[]{"umc:necromancer_scepter"}, t("Necromancer's Scepter", "Cetro del nigromante"),
                page(text(t("Necromancer's Scepter", "Cetro del nigromante"),
                        t("This rite forges the scepter used to command your servants: recall them, make them stay or follow, or order an attack.",
                                "Este rito forja el cetro con el que se manda a los siervos: llamarlos, hacer que se queden o te sigan, u ordenar un ataque."))),
                page(rite("enchanted:forge_necromancer_scepter")));

        entry("rituals/cleanse", "umc:purifying_tonic", new String[]{"umc:purifying_tonic"}, t("Cleansing", "Purificación"),
                page(text(t("Cleansing", "Purificación"),
                        t("Lifts the curse of lycanthropy or vampirism from the one who casts it.",
                                "Levanta la maldición de la licantropía o del vampirismo de quien lo realiza."))),
                page(rite("enchanted:cleanse_curse")));

        entry("rituals/moon", "umc:lunar_talisman", new String[]{"umc:lunar_talisman"}, t("Pact of the Moon", "Pacto de la luna"),
                page(text(t("Pact of the Moon", "Pacto de la luna"),
                        t("A pact that takes the werewolf curse on the caster of its own free will. It can only be sealed at night.",
                                "Un pacto que carga voluntariamente la maldición del hombre lobo sobre quien lo realiza. Solo puede sellarse de noche."))),
                page(rite("enchanted:pact_of_the_moon")));

        entry("rituals/blood", "umc:blood_sigil", new String[]{"umc:blood_sigil"}, t("Pact of Blood", "Pacto de sangre"),
                page(text(t("Pact of Blood", "Pacto de sangre"),
                        t("A pact that takes the vampire curse on the caster of its own free will. It can only be sealed at night.",
                                "Un pacto que carga voluntariamente la maldición del vampiro sobre quien lo realiza. Solo puede sellarse de noche."))),
                page(rite("enchanted:pact_of_blood")));

        entry("rituals/spirit", "umc:spirit_dust", new String[]{"umc:spirit_dust"}, t("Spirit Journey", "Viaje espiritual"),
                page(text(t("Spirit Journey", "Viaje espiritual"),
                        t("Sends the caster to the spirit world for a short while, then brings them back.",
                                "Envía a quien lo realiza al mundo espiritual durante un rato y después lo trae de vuelta."))),
                page(rite("enchanted:spirit_journey")));
    }

    private void familiars() {
        category("familiars", t("Familiars", "Familiares"),
                t("A cat is not the only companion a witch can keep.\n\nThis chapter tells how to tame creatures and bind them to yourself as familiars.",
                        "El gato no es el único compañero que puede tener una bruja.\n\nEste capítulo explica cómo domar criaturas y ligarlas a ti como familiares."),
                "umc:toad_spawn_egg", 6, "familiars/overview", "familiars/toad", "familiars/owl", "familiars/raven", "familiars/hound");

        entry("familiars/overview", "umc:toad_spawn_egg", new String[0], t("Binding Familiars", "Ligar familiares"),
                page(text(t("Familiars", "Familiares"),
                        t("Tame the animal first, then place it inside a medium chalk circle and cast its binding rite. While it is bound, the familiar grants its owner a bonus.\n\nOnly one familiar can be bound to a witch at a time.",
                                "Doma primero al animal, colócalo dentro de un círculo de tiza mediano y realiza su rito de vinculación. Mientras esté ligado, el familiar otorga un bonus a su dueño.\n\nUna bruja solo puede tener un familiar ligado a la vez."))),
                page(text(t("Care", "Cuidados"),
                        t("Crouch and use your familiar to dismiss it. The $(b)summon familiar$(/b) rite calls it back.\n\nFamiliars shrug off fire, falls and drowning, and a fallen familiar is only dismissed, never lost.",
                                "Agáchate y usa a tu familiar para despedirlo. El rito de $(b)invocar familiar$(/b) lo trae de vuelta.\n\nLos familiares ignoran el fuego, las caídas y el ahogamiento, y un familiar derrotado solo es despedido, nunca se pierde."))));

        entry("familiars/toad", "umc:toad_spawn_egg", new String[]{"umc:toad_spawn_egg", "umc:wormwood_tincture"}, t("Toad", "Sapo"),
                page(text(t("Toad", "Sapo"),
                        t("Toads croak in the shallows of swamps. Feed a wild toad spider eyes to win its trust.\n\nA bound toad lends its owner a frog's spring and lets them breathe under water.",
                                "Los sapos croan en las aguas poco profundas de los pantanos. Dale ojos de araña a un sapo salvaje para ganarte su confianza.\n\nUn sapo ligado presta a su dueño el salto de una rana y le deja respirar bajo el agua."))),
                page(entity("umc:toad", 1.2D, t("$(b)Found in:$(/b)\nSwamps", "$(b)Se encuentra en:$(/b)\nPantanos"))),
                page(rite("enchanted:bind_toad_familiar")));

        entry("familiars/owl", "umc:owl_spawn_egg", new String[]{"umc:owl_spawn_egg", "umc:moondew"}, t("Owl", "Lechuza"),
                page(text(t("Owl", "Lechuza"),
                        t("Owls watch the forest from the branches. Offer a wild owl raw rabbit or chicken to tame it.\n\nA bound owl lets its owner see in the dark.",
                                "Las lechuzas vigilan el bosque desde las ramas. Ofrece conejo o pollo crudo a una lechuza salvaje para domarla.\n\nUna lechuza ligada permite a su dueño ver en la oscuridad."))),
                page(entity("umc:owl", 1.1D, t("$(b)Found in:$(/b)\nForests", "$(b)Se encuentra en:$(/b)\nBosques"))),
                page(rite("enchanted:bind_owl_familiar")));

        entry("familiars/raven", "umc:raven_spawn_egg", new String[]{"umc:raven_spawn_egg", "umc:vervain_tonic"}, t("Raven", "Cuervo"),
                page(text(t("Raven", "Cuervo"),
                        t("Ravens haunt taigas and open plains. Rotten flesh or seeds will tame a wild raven.\n\nA bound raven brings its owner fortune and a swift step.",
                                "Los cuervos rondan las taigas y las llanuras. La carne podrida o las semillas domarán a un cuervo salvaje.\n\nUn cuervo ligado trae a su dueño fortuna y un paso veloz."))),
                page(entity("umc:raven", 1.2D, t("$(b)Found in:$(/b)\nTaigas and plains", "$(b)Se encuentra en:$(/b)\nTaigas y llanuras"))),
                page(rite("enchanted:bind_raven_familiar")));

        entry("familiars/hound", "minecraft:bone", new String[]{"enchanted:tongue_of_dog"}, t("Hound", "Sabueso"),
                page(text(t("Hound", "Sabueso"),
                        t("A tamed wolf can be bound as a hound. Its coat darkens and its eyes smoulder like embers.\n\nA bound hound makes its owner stronger and harder to hurt.",
                                "Un lobo domado puede ligarse como sabueso. Su pelaje se oscurece y sus ojos arden como brasas.\n\nUn sabueso ligado hace a su dueño más fuerte y más difícil de herir."))),
                page(entity("umc:familiar_hound", 0.9D, t("$(b)Made from:$(/b)\nA tamed wolf", "$(b)Se obtiene de:$(/b)\nUn lobo domado"))),
                page(rite("enchanted:bind_hound_familiar")));
    }

    private void clothing() {
        category("clothing", t("Witch Clothing", "Ropa de bruja"),
                t("A witch is known by her garb.\n\nThe hat, robe, cloak and boots described here are woven from moonthread and bring more than good looks.",
                        "A una bruja se la reconoce por sus ropajes.\n\nEl sombrero, la túnica, la capa y las botas que se describen aquí se tejen con hilo de luna y aportan algo más que buen aspecto."),
                "umc:witch_hat", 7, "clothing/hat", "clothing/robe", "clothing/cloak", "clothing/boots", "clothing/set");

        entry("clothing/hat", "umc:witch_hat", new String[]{"umc:witch_hat"}, t("Witch's Hat", "Sombrero de bruja"),
                page(text(t("Witch's Hat", "Sombrero de bruja"),
                        t("A tall pointed hat with a wide brim.\n\nWorn together with the rest of the set it lengthens the beneficial potions you drink.",
                                "Un sombrero alto y puntiagudo de ala ancha.\n\nLlevado junto con el resto del conjunto alarga las pociones beneficiosas que bebas.")),
                        crafting("umc:witch_hat", 75)));

        entry("clothing/robe", "umc:witch_robe", new String[]{"umc:witch_robe"}, t("Witch's Robe", "Túnica de bruja"),
                page(text(t("Witch's Robe", "Túnica de bruja"),
                        t("A long dress with wide sleeves.\n\nIt helps its wearer shake off curses over time.",
                                "Un vestido largo de mangas anchas.\n\nAyuda a quien lo viste a librarse de las maldiciones con el tiempo.")),
                        crafting("umc:witch_robe", 75)));

        entry("clothing/cloak", "umc:witch_cloak", new String[]{"umc:witch_cloak"}, t("Witch's Cloak", "Capa de bruja"),
                page(text(t("Witch's Cloak", "Capa de bruja"),
                        t("A cloak worn over the shoulders in place of the robe.\n\nIt softens falls and also helps shake off curses.",
                                "Una capa que se lleva sobre los hombros en lugar de la túnica.\n\nAmortigua las caídas y también ayuda a librarse de maldiciones.")),
                        crafting("umc:witch_cloak", 75)));

        entry("clothing/boots", "umc:witch_boots", new String[]{"umc:witch_boots"}, t("Witch's Boots", "Botas de bruja"),
                page(text(t("Witch's Boots", "Botas de bruja"),
                        t("Pointed boots with a gentle curl at the toe.\n\nThey give a little spring to every step.",
                                "Botas puntiagudas con la punta ligeramente curvada.\n\nDan un poco de impulso a cada paso.")),
                        crafting("umc:witch_boots", 75)));

        entry("clothing/set", "umc:witch_cloth", new String[]{"umc:witch_cloth", "umc:moonthread"}, t("Set Bonuses", "Bonus del conjunto"),
                page(text(t("The Full Set", "El conjunto completo"),
                        t("Wear a hat, a robe or cloak, and boots together to awaken the full set bonus.\n\nEach piece also makes altars within reach recharge faster, and curse shaking grows with the pieces worn.",
                                "Viste sombrero, túnica o capa y botas a la vez para despertar el bonus completo.\n\nCada pieza también hace que los altares cercanos se recarguen más rápido, y el librarse de maldiciones crece con las piezas puestas."))),
                page(text(t("Materials", "Materiales"),
                        t("Moonthread is spun at a spinning wheel and then crafted into witch cloth.",
                                "El hilo de luna se hila en una rueca y luego se convierte en tela de bruja.")),
                        crafting("umc:witch_cloth", 65)),
                page(nested(template("enchanted:page/spinning", "umc:spinning/moonthread"))));
    }

    private void plants() {
        category("plants", t("Plants", "Plantas"),
                t("The hedgerows hold more than the common herbs.\n\nThese plants feed altars and fill the kettle and the distillery.",
                        "Los setos guardan más que las hierbas comunes.\n\nEstas plantas alimentan los altares y llenan el caldero y la destilería."),
                "umc:wormwood_sprig", 8, "plants/wormwood", "plants/vervain", "plants/moonbloom", "plants/mugwort", "plants/tincture", "plants/tonic", "plants/moondew");

        entry("plants/wormwood", "umc:wormwood_sprig", new String[]{"umc:wormwood_sprig", "umc:wormwood_seeds"}, t("Wormwood", "Ajenjo"),
                page(text(t("Wormwood", "Ajenjo"),
                        t("A silvery, bitter herb sown on farmland.\n\nMature plants give sprigs, which are the base of wormwood tincture. Wormwood also feeds nearby altars.",
                                "Una hierba plateada y amarga que se siembra en tierra de cultivo.\n\nLas plantas maduras dan ramitas, base de la tintura de ajenjo. El ajenjo también alimenta a los altares cercanos."))),
                page(block("umc:wormwood[age=3]", t("$(b)Procurement:$(/b)\nSeeds drop from grass", "$(b)Obtención:$(/b)\nLas semillas caen de la hierba"))));

        entry("plants/vervain", "umc:vervain_flower", new String[]{"umc:vervain_flower", "umc:vervain_seeds"}, t("Vervain", "Verbena"),
                page(text(t("Vervain", "Verbena"),
                        t("A slender herb with spikes of violet flowers, grown on farmland.\n\nIts flowers go into vervain tonic. Vervain also feeds nearby altars.",
                                "Una hierba esbelta con espigas de flores violetas, cultivada en tierra de cultivo.\n\nSus flores se usan en el tónico de verbena. La verbena también alimenta a los altares cercanos."))),
                page(block("umc:vervain[age=3]", t("$(b)Procurement:$(/b)\nSeeds drop from grass", "$(b)Obtención:$(/b)\nLas semillas caen de la hierba"))));

        entry("plants/moonbloom", "umc:moonbloom", new String[]{"umc:moonbloom"}, t("Moonbloom", "Flor de luna"),
                page(text(t("Moonbloom", "Flor de luna"),
                        t("A pale flower that glows softly in the dark.\n\nIt grows wild in forests and taigas. Spun into moonthread or distilled into moondew, it is the heart of witch clothing.",
                                "Una flor pálida que brilla suavemente en la oscuridad.\n\nCrece salvaje en bosques y taigas. Hilada como hilo de luna o destilada como rocío lunar, es el corazón de la ropa de bruja."))),
                page(block("umc:moonbloom", t("$(b)Found in:$(/b)\nForests and taigas", "$(b)Se encuentra en:$(/b)\nBosques y taigas"))));

        entry("plants/mugwort", "umc:mugwort", new String[]{"umc:mugwort"}, t("Mugwort", "Artemisa"),
                page(text(t("Mugwort", "Artemisa"),
                        t("A grey-green herb that witches burn and brew to open the mind.\n\nIt grows wild on plains and in swamps and is distilled together with moonbloom.",
                                "Una hierba gris verdosa que las brujas queman e infusionan para abrir la mente.\n\nCrece salvaje en llanuras y pantanos y se destila junto con la flor de luna."))),
                page(block("umc:mugwort", t("$(b)Found in:$(/b)\nPlains and swamps", "$(b)Se encuentra en:$(/b)\nLlanuras y pantanos"))));

        entry("plants/tincture", "umc:wormwood_tincture", new String[]{"umc:wormwood_tincture"}, t("Wormwood Tincture", "Tintura de ajenjo"),
                page(text(t("Wormwood Tincture", "Tintura de ajenjo"),
                        t("A bitter infusion brewed in the kettle.\n\nIt is the key ingredient of the rite that binds a toad familiar.",
                                "Una infusión amarga preparada en el caldero.\n\nEs el ingrediente clave del rito que liga a un sapo como familiar."))),
                page(template("enchanted:recipe/kettle", "umc:kettle/wormwood_tincture")));

        entry("plants/tonic", "umc:vervain_tonic", new String[]{"umc:vervain_tonic"}, t("Vervain Tonic", "Tónico de verbena"),
                page(text(t("Vervain Tonic", "Tónico de verbena"),
                        t("A violet tonic brewed in the kettle.\n\nIt is used in the rites that bind raven and hound familiars.",
                                "Un tónico violeta preparado en el caldero.\n\nSe usa en los ritos que ligan a cuervos y sabuesos como familiares."))),
                page(template("enchanted:recipe/kettle", "umc:kettle/vervain_tonic")));

        entry("plants/moondew", "umc:moondew", new String[]{"umc:moondew"}, t("Moondew", "Rocío lunar"),
                page(text(t("Moondew", "Rocío lunar"),
                        t("Moonbloom and mugwort give up their essence in the distillery.\n\nMoondew is used in the rites that bind owl and hound familiars.",
                                "La flor de luna y la artemisa ceden su esencia en la destilería.\n\nEl rocío lunar se usa en los ritos que ligan a lechuzas y sabuesos como familiares."))),
                page(nested(template("enchanted:page/distillery", "umc:distilling/moondew"))));
    }

    private void generic() {
        category("creatures", t("Creatures", "Criaturas"),
                t("Not everything that moves in the night is an animal.\n\nThis chapter will grow as the strange creatures of this land are catalogued.",
                        "No todo lo que se mueve de noche es un animal.\n\nEste capítulo crecerá a medida que se catalogue a las extrañas criaturas de estas tierras."),
                "minecraft:bone", 9, "creatures/overview");
        entry("creatures/overview", "minecraft:bone", new String[0], t("Strange Creatures", "Criaturas extrañas"),
                page(text(t("Strange Creatures", "Criaturas extrañas"),
                        t("Witches have always told of beasts that walk between the world of the living and the world of the shadows.\n\nBe careful when you wander far from your circle after dark.",
                                "Las brujas siempre han hablado de bestias que caminan entre el mundo de los vivos y el de las sombras.\n\nTen cuidado al alejarte de tu círculo después del anochecer."))));

        category("spirit", t("Spirit World", "Mundo espiritual"),
                t("Beyond the veil lies another realm.\n\nWhat is known of it will be written down in this chapter.",
                        "Más allá del velo hay otro reino.\n\nLo que se sabe de él se anotará en este capítulo."),
                "minecraft:soul_lantern", 10, "spirit/overview");
        entry("spirit/overview", "minecraft:soul_lantern", new String[0], t("Beyond the Veil", "Más allá del velo"),
                page(text(t("Beyond the Veil", "Más allá del velo"),
                        t("The spirits of the dead do not always rest. Witches say a second world lies just behind this one, quiet and cold.\n\nWalk softly if you ever find the way in.",
                                "Los espíritus de los muertos no siempre descansan. Las brujas dicen que un segundo mundo se esconde tras este, callado y frío.\n\nPisa con cuidado si alguna vez encuentras la entrada."))));
    }
}
