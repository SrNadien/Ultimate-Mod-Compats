package nadiendev.umc.datagen.providers;

import nadiendev.umc.crafting.registry.CraftingBlocks;
import nadiendev.umc.crafting.registry.CraftingEntities;
import nadiendev.umc.crafting.registry.CraftingItems;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class CraftingLang {

    private CraftingLang() {
    }

    public static void add(LanguageProvider lang, boolean spanish) {
        lang.addItem(CraftingItems.WORMWOOD_SEEDS, spanish ? "Semillas de ajenjo" : "Wormwood Seeds");
        lang.addItem(CraftingItems.VERVAIN_SEEDS, spanish ? "Semillas de verbena" : "Vervain Seeds");
        lang.addItem(CraftingItems.WORMWOOD_SPRIG, spanish ? "Ramita de ajenjo" : "Wormwood Sprig");
        lang.addItem(CraftingItems.VERVAIN_FLOWER, spanish ? "Flor de verbena" : "Vervain Flower");
        lang.addItem(CraftingItems.MOONTHREAD, spanish ? "Hilo de luna" : "Moonthread");
        lang.addItem(CraftingItems.WITCH_CLOTH, spanish ? "Tela de bruja" : "Witch Cloth");
        lang.addItem(CraftingItems.WORMWOOD_TINCTURE, spanish ? "Tintura de ajenjo" : "Wormwood Tincture");
        lang.addItem(CraftingItems.VERVAIN_TONIC, spanish ? "Tónico de verbena" : "Vervain Tonic");
        lang.addItem(CraftingItems.MOONDEW, spanish ? "Rocío lunar" : "Moondew");
        lang.addItem(CraftingItems.WITCH_HAT, spanish ? "Sombrero de bruja" : "Witch's Hat");
        lang.addItem(CraftingItems.WITCH_ROBE, spanish ? "Túnica de bruja" : "Witch's Robe");
        lang.addItem(CraftingItems.WITCH_CLOAK, spanish ? "Capa de bruja" : "Witch's Cloak");
        lang.addItem(CraftingItems.WITCH_BOOTS, spanish ? "Botas de bruja" : "Witch's Boots");
        lang.addItem(CraftingItems.TOAD_SPAWN_EGG, spanish ? "Huevo generador de sapo" : "Toad Spawn Egg");
        lang.addItem(CraftingItems.OWL_SPAWN_EGG, spanish ? "Huevo generador de lechuza" : "Owl Spawn Egg");
        lang.addItem(CraftingItems.RAVEN_SPAWN_EGG, spanish ? "Huevo generador de cuervo" : "Raven Spawn Egg");

        lang.addBlock(CraftingBlocks.WORMWOOD, spanish ? "Ajenjo" : "Wormwood");
        lang.addBlock(CraftingBlocks.VERVAIN, spanish ? "Verbena" : "Vervain");
        lang.addBlock(CraftingBlocks.MOONBLOOM, spanish ? "Flor de luna" : "Moonbloom");
        lang.addBlock(CraftingBlocks.MUGWORT, spanish ? "Artemisa" : "Mugwort");

        lang.addEntityType(CraftingEntities.TOAD, spanish ? "Sapo" : "Toad");
        lang.addEntityType(CraftingEntities.OWL, spanish ? "Lechuza" : "Owl");
        lang.addEntityType(CraftingEntities.RAVEN, spanish ? "Cuervo" : "Raven");
        lang.addEntityType(CraftingEntities.FAMILIAR_TOAD, spanish ? "Sapo (Familiar)" : "Toad (Familiar)");
        lang.addEntityType(CraftingEntities.FAMILIAR_OWL, spanish ? "Lechuza (Familiar)" : "Owl (Familiar)");
        lang.addEntityType(CraftingEntities.FAMILIAR_RAVEN, spanish ? "Cuervo (Familiar)" : "Raven (Familiar)");
        lang.addEntityType(CraftingEntities.FAMILIAR_HOUND, spanish ? "Sabueso (Familiar)" : "Hound (Familiar)");

        lang.add("tooltip.umc.witch_clothing.hat", spanish ? "Alarga la duración de las pociones beneficiosas" : "Extends the duration of beneficial potions");
        lang.add("tooltip.umc.witch_clothing.robe", spanish ? "Ayuda a librarse de las maldiciones" : "Helps shake off curses");
        lang.add("tooltip.umc.witch_clothing.cloak", spanish ? "Amortigua las caídas y ayuda a librarse de maldiciones" : "Softens falls and helps shake off curses");
        lang.add("tooltip.umc.witch_clothing.boots", spanish ? "Pasos ligeros de bruja" : "The light steps of a witch");
        lang.add("tooltip.umc.witch_clothing.set", spanish ? "Sombrero, túnica o capa y botas: bonus de conjunto y altares más rápidos" : "Hat, robe or cloak and boots: set bonus and faster altars");

        lang.add("book.title.umc.guide", spanish ? "Almanaque de la bruja rural" : "Hedge Witch's Almanac");
        lang.add("book.subtitle.umc.guide", spanish ? "Familiares, ropajes y hierbas" : "Familiars, garb and herbs");
        lang.add("book.landing_text.umc.guide", spanish
                ? "Este almanaque recoge lo que las brujas del campo han aprendido de sus familiares, de sus ropajes y de las hierbas que crecen lejos de los caminos.\n\nCada capítulo explica una parte del oficio."
                : "This almanac gathers what hedge witches have learned from their familiars, their garb and the herbs that grow far from the roads.\n\nEach chapter explains one part of the craft.");
    }
}
