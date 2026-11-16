# Changelog

## 1.0.2 - 2026-11-15

- 12 new Mystical Agriculture crops for the Ultimate Armoury materials that had no seeds yet: amberite, lustrous dust, tenebrite, gorium, luminite, viridite, osmirite, umbrite, ionite, pyrestone crystal, aurelite crystal and infernium They only load when Ultimate Armoury is installed.
- 9 more crops for materials of Advanced AE, Extended AE, Excessive Utilities and vanilla glass: quantum infused dust, quantum alloy, entro ingot, unstable ingot, enchanted ingot, demon ingot, evil infused iron, bedrockium ingot and glass (85 crops in total). The tier 6 ones are made from blocks, bedrockium is tier 10 and glass tier 3.
- The essence of the Thaumaturge crystal crops now gives the vis crystal (essentia crystal) of its aspect instead of the raw crystal.
- Every tinted crop has its own seed, essence and flower textures, colored like the material it makes.
- The Elevatium crop now uses Ultimate Armoury's ascendium ingot (`ultimatearmoury:ascendium_ingot`) instead of a KubeJS item.
- A single creative tab built with Fractal, with the sub tabs Seeds, Altars, Pedestals, Witchcraft, Essences, Crystals, Machines and Tables, and only the name of the mod at the top. The Example sub tab only shows with no compatibility installed, and always in development.
- Every compatibility loads only when its mod is installed, and none is required.
- Dependencies: official Maven where the mod has one (Mystical Agriculture and its addons, Applied Energistics 2, GuideME, Create, Kotlin for Forge, Fractal) and CurseMaven at the end for the rest, including Thaumaturge. `neoforge.mods.toml` now uses `[${mod_version},)` for every dependency.
- Compatibilities for the Ultimate Armoury materials (mushashite, joanfoite, nadienite) and the Allthemodium ones (allthemodium, vibranium, unobtainium, piglich), each one with its own creative sub tab and its own switch in `umc.toml` (`compat.items.<name>.enabled`):
  - EnderIO: grinding balls, capacitors and energy, item and fluid conduits, with two recipes for the conduits.
  - Industrial Foregoing: range, speed, efficiency and processing addons.
  - Thaumaturge: wand caps and rods.
  - Ars Nouveau: source jars.
  - Solar Flux Reborn: solar panels, mirrors and coatings.
  - Functional Storage: seven storage upgrades from x64 to x30000 that add up with each other instead of multiplying, and normal drawers of the woods of vanilla and of other mods with the planks on the front and the log on the rest.
- Crops: the entro crystal is only made from the ring of entro essence, the essence of the entro ingot makes dust and ingots, nadienite uses its 1x block, the AllTheCompressed blocks are no longer used, the fat entro seed and the Mercurio, quicksilver and salis mundus colors are fixed.
- A key (J by default, in Controls) shows and hides the box of the spirit infusions in the corner of the screen.
- New optional dependencies: Allthemodium, AllTheCompressed and XyCraft, Solar Flux Reborn and HammerLib.

## 1.0.2 (Español)

- 12 cultivos nuevos de Mystical Agriculture para los materiales de Ultimate Armoury que aún no tenían semillas: amberita, polvo lustroso, tenebrita, gorio, luminita, viridita, osmirita, umbrita, ionita, cristal de pirestona, cristal de aurelita e infernium Solo se cargan si Ultimate Armoury está instalado.
- 9 cultivos más para materiales de Advanced AE, Extended AE, Excessive Utilities y el vidrio vanilla: polvo infundido cuántico, aleación cuántica, lingote de entro, lingote inestable, lingote encantado, lingote demoníaco, hierro infundido con maldad, lingote de bedrockium y vidrio (85 cultivos en total). Los de nivel 6 se hacen con bloques, el bedrockium es de nivel 10 y el vidrio de nivel 3.
- La esencia de los cultivos de cristales de Thaumaturge ahora da el cristal de vis de su aspecto en vez del cristal bruto.
- Todos los cultivos teñidos tienen sus propias texturas de semilla, esencia y flor, con el color del material que fabrican.
- El cultivo Elevatium ahora usa el lingote de ascendium de Ultimate Armoury (`ultimatearmoury:ascendium_ingot`) en vez de un ítem de KubeJS.
- Una sola pestaña creativa hecha con Fractal, con las subpestañas Semillas, Altares, Pedestales, Brujeria, Esencias, Cristales, Maquinas y Mesas, y solo el nombre del mod arriba. La subpestaña Ejemplo solo sale sin ninguna compatibilidad instalada, y siempre en desarrollo.
- Cada compatibilidad se carga solo si su mod está instalado, y ninguna es obligatoria.
- Dependencias: Maven oficial cuando el mod lo tiene (Mystical Agriculture y sus addons, Applied Energistics 2, GuideME, Create, Kotlin for Forge, Fractal) y CurseMaven al final para el resto, incluido Thaumaturge. `neoforge.mods.toml` usa `[${mod_version},)` en todas las dependencias.
- Compatibilidades para los materiales de Ultimate Armoury (mushashite, joanfoite, nadienite) y los de Allthemodium (allthemodium, vibranium, unobtainium, piglich), cada una con su subpestaña creativa y su interruptor en `umc.toml` (`compat.items.<nombre>.enabled`):
  - EnderIO: bolas de molienda, capacitores y conductos de energía, ítems y fluidos, con dos recetas para los conductos.
  - Industrial Foregoing: mejoras de rango, velocidad, eficiencia y procesamiento.
  - Thaumaturge: puntas y varas de varita.
  - Ars Nouveau: jarras de fuente.
  - Solar Flux Reborn: paneles solares, espejos y revestimientos.
  - Functional Storage: siete mejoras de almacenamiento de x64 a x30000 que se suman entre sí en vez de multiplicarse, y cajones normales de las maderas de vanilla y de otros mods con los tablones al frente y el tronco en el resto.
- Cultivos: el cristal de entro solo se hace con el anillo de esencia de entro, la esencia del lingote de entro da polvo y lingotes, la nadienite usa su bloque 1x, ya no se usan los bloques de AllTheCompressed, y se arreglan la semilla gorda de entro y los colores de Mercurio, quicksilver y salis mundus.
- Una tecla (J por defecto, en Controles) muestra y oculta el cuadro de las infusiones espirituales en la esquina de la pantalla.
- Dependencias opcionales nuevas: Allthemodium, AllTheCompressed y XyCraft, Solar Flux Reborn y HammerLib.

## 1.0.1 - 2026-11-02

- Mystical Agriculture tier system: infusion and awakening altars, pedestals, essence vessels, seed reprocessors and furnaces from level 1 to 10, upgraded in the altar of the previous level.
- Tiered infusion crystals (the Master Infusion Crystal also works in their place) and a new Master Infusion Crystal recipe. The original crystal and essence recipes of Mystical Agriculture, Agradditions and Extended Tier are replaced.
- Altar rules by level for seeds, essences, augments, essence gear and awakened items. The base altar only makes air, earth, fire and water seeds, and the crafting table seed recipes are removed.
- JEI: one category per level with its altar and pedestals as catalysts, and the tiered furnaces and reprocessors as catalysts.
- A creative tab for Mystical Agriculture, sorted by kind and level.
- 64 extra Mystical Agriculture crops (new Extended Tier essences, soul dust and soulstone cobblestone). Seed and reprocessor recipes are generated by datagen, and every seed has the same thickness.
- AgriCraft ReReloaded plants for every crop.
- CurseForge workflow: the file name is the jar name without `.jar` and the metadata is sent as a plain form string.

## 1.0.1 (Español)

- Sistema de tiers de Mystical Agriculture: altares de infusión y del despertar, pedestales, recipientes de esencia, reprocesadores de semillas y hornos del nivel 1 al 10, mejorados en el altar del nivel anterior.
- Cristales de infusión por nivel (el Master Infusion Crystal también sirve en su lugar) y una receta nueva del Master Infusion Crystal. Se reemplazan las recetas originales de cristales y esencias de Mystical Agriculture, Agradditions y Extended Tier.
- Reglas de los altares por nivel para semillas, esencias, aumentos, equipo de esencia y objetos del despertar. El altar base solo hace las semillas de aire, tierra, fuego y agua, y se quitan las recetas de semillas en mesa de crafteo.
- JEI: una categoría por nivel con su altar y sus pedestales como catalizadores, y los hornos y reprocesadores por nivel como catalizadores.
- Una pestaña creativa de Mystical Agriculture, ordenada por tipo y nivel.
- 64 cultivos extra de Mystical Agriculture (esencias nuevas de Extended Tier, polvo de alma y adoquín de piedra de alma). Las recetas de semilla y de reprocesado se generan por datagen y todas las semillas tienen el mismo grosor.
- Plantas de AgriCraft ReReloaded para cada cultivo.
- Workflow de CurseForge: el nombre del archivo es el del jar sin `.jar` y el metadata se envía como texto plano del formulario.

## 1.0.0 - 2026-10-22

First release.

- Werewolves, vampires and hunters for Enchanted: curses, transformations that keep your skin with glowing eyes, new abilities and weaknesses, cures and new mobs.
- Necromancy and demonology rites, a spirit world with dreams, infusions and covens, and witch crafting (familiars, clothing and plants) with an in-game guide.
- KubeJS support for Enchanted (`EnchantedEvents` / `EnchantedStartup`), Thaumaturge, Applied Energistics 2, Advanced AE, Extended AE, Create, Generator Galore, Excessive Utilities and Functional Storage.
- Create: `item_application` recipes and mysterious conversions that can run in the world with `.haunting()` or `.entity(...)`, shown in JEI.
- 54 extra Mystical Agriculture crops with their seed and essence recipes.
- Everything configurable in a single `config/umc.toml`, with a switch for each compatibility.
- Texts in English and Spanish.

## 1.0.0 (Español)

Primera versión.

- Hombres lobo, vampiros y cazadores para Enchanted: maldiciones, transformaciones que conservan tu skin con ojos brillantes, habilidades y debilidades nuevas, curas y mobs nuevos.
- Ritos de nigromancia y demonología, un mundo espiritual con sueños, infusiones y aquelarres, y artesanía de brujas (familiares, ropa y plantas) con una guía dentro del juego.
- Soporte de KubeJS para Enchanted (`EnchantedEvents` / `EnchantedStartup`), Thaumaturge, Applied Energistics 2, Advanced AE, Extended AE, Create, Generator Galore, Excessive Utilities y Functional Storage.
- Create: recetas `item_application` y conversiones misteriosas que pueden ocurrir en el mundo con `.haunting()` o `.entity(...)`, visibles en JEI.
- 54 cultivos extra de Mystical Agriculture con sus recetas de semilla y esencia.
- Todo configurable en un solo `config/umc.toml`, con un interruptor por cada compatibilidad.
- Textos en inglés y español.
