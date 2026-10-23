package nadiendev.umc.datagen.providers;

import java.util.ArrayList;
import java.util.List;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class CreaturesLang {

    private record Entry(String key, String english, String spanish) {
    }

    private static final List<Entry> ENTRIES = new ArrayList<>();

    static {
        item("silver_ingot", "Silver Ingot", "Lingote de plata");
        item("silver_sword", "Silver Sword", "Espada de plata");
        item("wooden_stake", "Wooden Stake", "Estaca de madera");
        item("grave_dust", "Grave Dust", "Polvo de tumba");
        item("ectoplasm", "Ectoplasm", "Ectoplasma");
        item("imp_horn", "Imp Horn", "Cuerno de diablillo");
        item("werewolf_fang", "Werewolf Fang", "Colmillo de hombre lobo");
        item("vampire_fang", "Vampire Fang", "Colmillo de vampiro");
        item("purifying_tonic", "Purifying Tonic", "Tónico purificador");
        item("lunar_talisman", "Lunar Talisman", "Talismán lunar");
        item("blood_sigil", "Blood Sigil", "Sello de sangre");
        item("necromancer_scepter", "Necromancer's Scepter", "Cetro del nigromante");
        item("summoning_horn", "Summoning Horn", "Cuerno de invocación");
        item("demonomicon", "Demonomicon", "Demonomicón");
        item("werewolf_spawn_egg", "Werewolf Spawn Egg", "Huevo generador de hombre lobo");
        item("vampire_spawn_egg", "Vampire Spawn Egg", "Huevo generador de vampiro");
        item("ghost_spawn_egg", "Ghost Spawn Egg", "Huevo generador de fantasma");
        item("imp_spawn_egg", "Imp Spawn Egg", "Huevo generador de diablillo");
        item("ent_spawn_egg", "Ent Spawn Egg", "Huevo generador de ent");

        tooltip("silver_sword", "Deals extra damage to werewolves.", "Hace más daño a los hombres lobo.");
        tooltip("wooden_stake", "Strike a vampire to deal heavy damage.", "Golpea a un vampiro para hacerle mucho daño.");
        tooltip("purifying_tonic", "Lifts the curse of lycanthropy or vampirism.", "Levanta la maldición de la licantropía o el vampirismo.");
        tooltip("lunar_talisman", "Use it to change shape if you carry the werewolf curse.", "Úsalo para cambiar de forma si portas la maldición del hombre lobo.");
        tooltip("blood_sigil", "Use it to change shape if you carry the vampire curse.", "Úsalo para cambiar de forma si portas la maldición del vampiro.");
        tooltip("necromancer_scepter", "Use it to recall your servants. Use it on a servant to make it stay or follow, or on another creature to order an attack.", "Úsalo para llamar a tus siervos. Úsalo sobre un siervo para que se quede o te siga, o sobre otra criatura para ordenar un ataque.");
        add("item.umc.necromancer_scepter.tooltip.sneak", "Sneak and use it to dismiss every servant.", "Agáchate y úsalo para despedir a todos los siervos.");
        tooltip("summoning_horn", "Consumes grave dust to raise undead servants.", "Consume polvo de tumba para alzar siervos no muertos.");
        tooltip("demonomicon", "Pay with your blood and an imp horn to bind an imp.", "Paga con tu sangre y un cuerno de diablillo para atar a un diablillo.");

        entity("werewolf", "Werewolf", "Hombre lobo");
        entity("vampire", "Vampire", "Vampiro");
        entity("ghost", "Ghost", "Fantasma");
        entity("imp", "Imp", "Diablillo");
        entity("ent", "Ent", "Ent");
        entity("zombie_servant", "Risen Servant", "Siervo resucitado");
        entity("skeleton_servant", "Bone Servant", "Siervo óseo");

        add("key.umc.transform", "Transform", "Transformarse");
        add("key.umc.ability", "Creature Ability", "Habilidad de la criatura");
        add("key.categories.umc.creatures", "Ultimate Mod Compats: Creatures", "Ultimate Mod Compats: Criaturas");

        subtitle("entity.werewolf.ambient", "Werewolf growls", "Hombre lobo gruñe");
        subtitle("entity.werewolf.hurt", "Werewolf hurts", "Hombre lobo es herido");
        subtitle("entity.werewolf.death", "Werewolf dies", "Hombre lobo muere");
        subtitle("entity.werewolf.howl", "Werewolf howls", "Hombre lobo aúlla");
        subtitle("entity.vampire.ambient", "Vampire hisses", "Vampiro sisea");
        subtitle("entity.vampire.hurt", "Vampire hurts", "Vampiro es herido");
        subtitle("entity.vampire.death", "Vampire dies", "Vampiro muere");
        subtitle("entity.vampire.transform", "Vampire transforms", "Vampiro se transforma");
        subtitle("entity.vampire.bite", "Vampire bites", "Vampiro muerde");
        subtitle("entity.ghost.ambient", "Ghost wails", "Fantasma gime");
        subtitle("entity.ghost.hurt", "Ghost hurts", "Fantasma es herido");
        subtitle("entity.ghost.death", "Ghost vanishes", "Fantasma se desvanece");
        subtitle("entity.imp.ambient", "Imp cackles", "Diablillo se ríe");
        subtitle("entity.imp.hurt", "Imp hurts", "Diablillo es herido");
        subtitle("entity.imp.death", "Imp dies", "Diablillo muere");
        subtitle("entity.imp.fireball", "Imp throws a fireball", "Diablillo lanza una bola de fuego");
        subtitle("entity.ent.ambient", "Ent groans", "Ent cruje");
        subtitle("entity.ent.hurt", "Ent hurts", "Ent es herido");
        subtitle("entity.ent.death", "Ent falls", "Ent cae");
        subtitle("entity.ent.step", "Ent stomps", "Ent pisa fuerte");
        subtitle("item.summon", "Something is summoned", "Algo es invocado");
        subtitle("item.banish", "Servants are banished", "Los siervos son desterrados");

        add("message.umc.cursed.werewolf", "You have been bitten. The curse of the werewolf runs in your blood.", "Te han mordido. La maldición del hombre lobo corre por tu sangre.");
        add("message.umc.cursed.vampire", "You have been bitten. The curse of the vampire runs in your blood.", "Te han mordido. La maldición del vampiro corre por tu sangre.");
        add("message.umc.cured.werewolf", "The curse of the werewolf leaves your body.", "La maldición del hombre lobo abandona tu cuerpo.");
        add("message.umc.cured.vampire", "The curse of the vampire leaves your body.", "La maldición del vampiro abandona tu cuerpo.");
        add("message.umc.hunter.become", "You are now a hunter: you hit werewolves and vampires harder, take less from them and cannot be cursed.", "Ahora eres cazador: golpeas más fuerte a hombres lobo y vampiros, recibes menos daño de ellos y no puedes ser maldecido.");
        add("commands.umc.form.success", "%1$s is now a %2$s", "%1$s ahora es %2$s");
        add("commands.umc.form.disabled", "The %s form is disabled in the config", "La forma de %s está desactivada en la config");
        add("form.umc.vampire", "Vampire", "vampiro");
        add("form.umc.werewolf", "Werewolf", "hombre lobo");
        add("form.umc.hunter", "Hunter", "cazador");
        add("form.umc.human", "Human", "humano");
        add("message.umc.transform.werewolf", "The beast takes over!", "¡La bestia toma el control!");
        add("message.umc.transform.vampire", "You embrace the night.", "Abrazas la noche.");
        add("message.umc.transform.revert", "You return to your human form.", "Vuelves a tu forma humana.");
        add("message.umc.transform.cooldown", "You cannot change shape for %s more seconds.", "No puedes cambiar de forma durante %s segundos más.");
        add("message.umc.transform.needs_night", "You can only transform at night.", "Solo puedes transformarte de noche.");
        add("message.umc.ability.cooldown", "Your ability recharges in %s seconds.", "Tu habilidad se recarga en %s segundos.");
        add("message.umc.ability.no_target", "There is nothing to bite.", "No hay nada que morder.");
        add("message.umc.charm.wrong_curse", "The charm does not answer to you.", "El amuleto no te responde.");
        add("message.umc.tonic.not_cursed", "You are not cursed.", "No estás maldito.");
        add("message.umc.tonic.disabled", "The tonic has no effect.", "El tónico no tiene efecto.");
        add("message.umc.creatures.disabled", "Its power is sealed.", "Su poder está sellado.");
        add("message.umc.servant.stay", "The servant will stay here.", "El siervo se quedará aquí.");
        add("message.umc.servant.follow", "The servant will follow you.", "El siervo te seguirá.");
        add("message.umc.servants.limit", "You cannot control more servants.", "No puedes controlar más siervos.");
        add("message.umc.scepter.none", "You have no servants nearby.", "No tienes siervos cerca.");
        add("message.umc.scepter.dismiss", "%s servants are dismissed.", "%s siervos han sido despedidos.");
        add("message.umc.scepter.recall", "%s servants answer your call.", "%s siervos acuden a tu llamada.");
        add("message.umc.scepter.attack", "%s servants attack your target.", "%s siervos atacan a tu objetivo.");
        add("message.umc.horn.no_dust", "You need %s grave dust.", "Necesitas %s de polvo de tumba.");
        add("message.umc.horn.summoned", "%s servants rise from the earth.", "%s siervos surgen de la tierra.");
        add("message.umc.grimoire.too_weak", "You are too weak to pay the price.", "Estás demasiado débil para pagar el precio.");
        add("message.umc.grimoire.no_horn", "The book demands an imp horn.", "El libro exige un cuerno de diablillo.");
        add("message.umc.grimoire.bound", "%s imps are bound to your will.", "%s diablillos quedan atados a tu voluntad.");
        add("message.umc.creatures.rite.summoned", "%s servants answer the rite.", "%s siervos responden al rito.");
        add("message.umc.creatures.rite.already_cursed", "You already carry a curse.", "Ya cargas con una maldición.");
        add("message.umc.creatures.rite.not_cursed", "There is no curse to lift.", "No hay ninguna maldición que levantar.");
        add("message.umc.creatures.rite.cured_other", "%s is freed from the curse.", "%s queda libre de la maldición.");
    }

    private CreaturesLang() {
    }

    private static void add(String key, String english, String spanish) {
        ENTRIES.add(new Entry(key, english, spanish));
    }

    private static void item(String name, String english, String spanish) {
        add("item.umc." + name, english, spanish);
    }

    private static void tooltip(String name, String english, String spanish) {
        add("item.umc." + name + ".tooltip", english, spanish);
    }

    private static void entity(String name, String english, String spanish) {
        add("entity.umc." + name, english, spanish);
    }

    private static void subtitle(String name, String english, String spanish) {
        add("subtitles.umc." + name, english, spanish);
    }

    public static void english(LanguageProvider lang) {
        ENTRIES.forEach(entry -> lang.add(entry.key(), entry.english()));
    }

    public static void spanish(LanguageProvider lang) {
        ENTRIES.forEach(entry -> lang.add(entry.key(), entry.spanish()));
    }
}
