# Ultimate Mod Compats: Wiki

Guía para jugadores y creadores de modpacks. Mod `umc` (NeoForge 1.21.1), autor NadienDev.

## Índice

1. [Qué es y dependencias](#1-qué-es-y-dependencias)
2. [Contenido propio](#2-contenido-propio)
3. [Configuración](#3-configuración)
4. [Compatibilidad con KubeJS](#4-compatibilidad-con-kubejs)
   - [Enchanted](#41-enchanted)
   - [Thaumaturge](#42-thaumaturge)
   - [Mystical Agriculture](#43-mystical-agriculture)
   - [Applied Energistics 2](#44-applied-energistics-2-ae2)
   - [Advanced AE](#45-advanced-ae)
   - [Extended AE](#46-extended-ae)
   - [Create](#47-create)
   - [Generator Galore](#48-generator-galore)
   - [Excessive Utilities](#49-excessive-utilities)
   - [Functional Storage](#410-functional-storage)
5. [Cultivos de Mystical Agriculture](#5-cultivos-de-mystical-agriculture)
6. [Preguntas frecuentes y problemas conocidos](#6-preguntas-frecuentes-y-problemas-conocidos)

---

## 1. Qué es y dependencias

**Ultimate Mod Compats** (id `umc`) es un addon para **Enchanted** que hace dos cosas:

- Añade **contenido propio**: criaturas (hombre lobo, vampiro, cazador y mobs nuevos), un mundo espiritual y artesanía de brujas (familiares, ropa, plantas).
- Añade **compatibilidad con KubeJS** para otros mods (recetas y eventos que esos mods no traen), y **54 cultivos extra** para Mystical Agriculture.

### Dependencias

| Mod | Tipo | Para qué |
| --- | --- | --- |
| NeoForge 21.1.240 o superior (Minecraft 1.21.1) | Obligatorio | Cargador |
| Enchanted 4.2.7 o superior (`enchanted`) | Obligatorio | Es el mod sobre el que se construye |
| GeckoLib 4.7 o superior (`geckolib`) | Obligatorio | Modelos y animaciones de las criaturas |
| KubeJS 2101.7.2 o superior (`kubejs`) | Opcional | Todo el apartado 4 |
| Thaumaturge (`thaumaturge`) | Opcional | Módulo KubeJS y 14 cultivos de Mystical Agriculture |
| Applied Energistics 2 (`ae2`) | Opcional | Módulo KubeJS |
| Advanced AE (`advanced_ae`) | Opcional | Módulo KubeJS |
| Extended AE (`extendedae`) | Opcional | Módulo KubeJS |
| Create (`create`) | Opcional | Módulo KubeJS |
| Generator Galore (`generatorgalore`) | Opcional | Módulo KubeJS |
| Excessive Utilities (`excessive_utilities`) | Opcional | Módulo KubeJS |
| Functional Storage (`functionalstorage`) | Opcional | Módulo KubeJS |
| Mystical Agriculture (`mysticalagriculture`) | Opcional | Módulo KubeJS y cultivos extra |

Cada módulo de KubeJS solo se activa si el mod al que apoya está instalado. Si no lo está, el evento o la receta de ese mod **no existe** y el script da error.

Los textos del mod están en inglés y en español (es_es, es_ar y es_mx).

---

## 2. Contenido propio

Resumen corto de lo que añade el mod por sí mismo. Todo se puede apagar desde la configuración (apartado 3).

### Criaturas

- **Maldiciones de hombre lobo y vampiro.** Un mordisco puede maldecirte (30 % el hombre lobo y 25 % el vampiro por defecto). Te transformas con una tecla o con el **talismán lunar** (hombre lobo) y el **sello de sangre** (vampiro).
- **Habilidades.** El hombre lobo salta sobre su presa; el vampiro drena sangre.
- **Debilidades.** Plata (espada y lingote de plata), acónito (`enchanted:wolfsbane_flower`) para el hombre lobo, luz del sol, estaca de madera y ajo para el vampiro.
- **Conservas tu skin.** Transformado mantienes tu skin y se te ponen ojos con brillo: rojos (vampiro) y verdes (hombre lobo). Color e interruptor son configurables.
- **Cura.** **Tónico purificador** o el rito de purificación.
- **Cazador.** Un tercer estado: golpeas más fuerte a hombres lobo y vampiros, recibes menos daño de ellos y no te pueden maldecir. Solo se activa con el comando `/umc form hunter`.
- **Mobs nuevos.** Hombre lobo, vampiro, fantasma, diablillo (imp) y ent. Además los **siervos** invocables: siervo resucitado (zombi) y siervo óseo (esqueleto).
- **Nigromancia.** Ritos de círculo mágico para levantar siervos, fantasmas, diablillos y ents; **cetro del nigromante**, **cuerno de invocación** (gasta polvo de tumba) y **demonomicón** (ata diablillos pagando vida y un cuerno de diablillo). También el *Pacto de la luna* y el *Pacto de sangre*.

### Mundo espiritual

- Una dimensión propia. Se entra por el **portal espiritual** (se carga con polvo espiritual) o con el rito *Viaje espiritual*. Para volver, tecla de regreso.
- **Atrapasueños y telar de sueños:** cambian la probabilidad de buenos sueños y pesadillas al dormir. Un buen sueño puede dar recompensas, experiencia o un viaje al mundo espiritual.
- **Infusiones:** salto espiritual, caminar por paredes y visión espiritual. Se cargan con esencias y se cambian con una tecla.
- **Aquelarres:** funda uno en un altar de aquelarre, invita brujas y compartid bonus (recarga de altares y vínculo de aquelarre).

### Artesanía de brujas

- **Familiares:** sapo, lechuza, cuervo y sabueso, cada uno con sus bonus y su rito de vinculación.
- **Ropa de bruja:** sombrero, túnica, capa y botas, con bonus por pieza y por conjunto completo (altares más rápidos, resistencia a maldiciones, pociones beneficiosas más largas, capa que amortigua caídas).
- **Plantas:** ajenjo, verbena, flor de luna y artemisa, para el caldero, la rueca y la destilería de Enchanted.
- **Guía dentro del juego:** el *Almanaque de la bruja rural* (libro de Modopedia con capítulos Rituales, Familiares, Ropa de bruja, Plantas, Criaturas y Mundo espiritual).

### Comandos

| Comando | Quién | Qué hace |
| --- | --- | --- |
| `/umc form <vampire\|werewolf\|hunter\|human> [jugadores]` | Operadores (nivel 2) | Pone a un jugador en esa forma. `human` lo cura y lo deja humano. Si la forma está desactivada en la config, avisa y no hace nada |
| `/umc coven create <nombre>` | Jugador | Funda un aquelarre |
| `/umc coven invite <jugador>` | Jugador | Invita a un jugador |
| `/umc coven join <nombre>` | Jugador | Se une a un aquelarre al que te han invitado |
| `/umc coven leave` | Jugador | Abandona tu aquelarre |
| `/umc coven kick <jugador>` | Líder | Expulsa a un miembro |
| `/umc coven disband` | Líder | Disuelve el aquelarre |
| `/umc coven info [nombre]` | Jugador | Muestra datos del aquelarre |

### Teclas (se pueden cambiar en Controles)

| Tecla por defecto | Acción |
| --- | --- |
| G | Transformarse |
| H | Habilidad de la criatura |
| V | Cambiar de infusión |
| B | Volver del mundo espiritual |

### Pestaña creativa

Una pestaña propia llamada **Ultimate Mod Compats** (id `umc:main`) con los objetos del mod. El icono es el objeto `umc:tab_icon` (en español, "Gato Espacial").

---

## 3. Configuración

Hay **un solo archivo**: `config/umc.toml` (tipo COMMON, se genera la primera vez que arranca el juego). Cada función tiene su interruptor `enabled`. Los comentarios del archivo están en inglés.

### Secciones

| Sección | Qué controla |
| --- | --- |
| `creatures` | Hombre lobo, vampiro, infección, cura, cazador, mobs, nigromancia, demonología y ritos |
| `spirit` | Mundo espiritual, sueños, infusiones y aquelarres |
| `crafting` | Familiares, ropa de bruja y plantas |
| `compat.kubejs` | Interruptor general y uno por mod para la compatibilidad con KubeJS |
| `compat.mysticalagriculture` | Cultivos extra de Mystical Agriculture y sus recetas |

Las claves de `creatures` y `crafting` usan camelCase (`healthBonus`); las de `spirit` y `compat` usan snake_case (`portal_active_seconds`).

### `creatures`

| Clave | Por defecto | Qué es |
| --- | --- | --- |
| `creatures.enabled` | `true` | Apaga todo el módulo de criaturas |
| `creatures.werewolf.enabled` | `true` | Licantropía y forma de hombre lobo |
| `creatures.werewolf.healthBonus` / `damageBonus` / `speedBonus` | `10.0` / `4.0` / `0.25` | Bonus mientras estás transformado |
| `creatures.werewolf.forceOnFullMoon` | `true` | Los maldecidos se transforman a la fuerza en luna llena |
| `creatures.werewolf.revertAtDawn` | `true` | La transformación forzada termina al amanecer |
| `creatures.werewolf.requireNightToTransform` | `false` | Solo se puede transformar voluntariamente de noche |
| `creatures.werewolf.silverDamageMultiplier` | `2.0` | Multiplicador de daño de las armas de plata |
| `creatures.werewolf.repellentItem` | `"enchanted:wolfsbane_flower"` | Objeto que debilita a los hombres lobo si lo llevas cerca |
| `creatures.werewolf.spawnOnlyOnFullMoon` | `false` | Los hombres lobo salvajes solo aparecen en luna llena |
| `creatures.werewolf.useModel` | `false` | Usa el modelo GeckoLib propio en lugar de tu skin (necesita un resource pack con el modelo) |
| `creatures.werewolf.glowingEyes` / `eyeColor` | `true` / `"33FF33"` | Ojos brillantes y su color (hexadecimal RRGGBB) |
| `creatures.vampire.enabled` | `true` | Vampirismo y forma de vampiro |
| `creatures.vampire.healthBonus` / `damageBonus` / `speedBonus` | `4.0` / `3.0` / `0.15` | Bonus mientras estás transformado |
| `creatures.vampire.sunlightBurns` | `true` | La forma de vampiro arde con el sol directo |
| `creatures.vampire.helmetProtects` / `rainProtects` | `true` / `true` | Casco o lluvia evitan quemarse |
| `creatures.vampire.stakeBonusDamage` | `10.0` | Daño extra de la estaca de madera |
| `creatures.vampire.repellentItem` | `"enchanted:garlic"` | Objeto que debilita a los vampiros |
| `creatures.vampire.drainRange` / `drainDamage` | `3.5` / `5.0` | Alcance y daño del drenaje de sangre |
| `creatures.vampire.glowingEyes` / `eyeColor` | `true` / `"FF1A1A"` | Ojos brillantes y su color |
| `creatures.infection.werewolfBiteChance` / `vampireBiteChance` | `0.3` / `0.25` | Probabilidad de maldecir al morder |
| `creatures.infection.fromPlayers` | `false` | Los jugadores transformados también maldicen |
| `creatures.infection.loseCurseOnDeath` | `false` | Morir levanta la maldición |
| `creatures.cure.tonic` / `rite` | `true` / `true` | El tónico y el rito de purificación curan |
| `creatures.hunter.damageMultiplier` / `damageTakenMultiplier` | `1.5` / `0.7` | Daño que hace y que recibe el cazador frente a lobos y vampiros |
| `creatures.mobs.<mob>.enabled` | `true` | Activa el mob y su aparición natural. `<mob>`: `werewolf`, `vampire`, `ghost`, `imp`, `ent`, `zombie_servant`, `skeleton_servant` |
| `creatures.mobs.<mob>.health` / `damage` / `speed` | según mob | Vida, daño y velocidad |
| `creatures.necromancy.maxServantsPerOwner` | `8` | Máximo de siervos por jugador |
| `creatures.necromancy.servantLifetimeSeconds` | `600` | Los siervos se desvanecen pasado este tiempo (0 = nunca) |
| `creatures.necromancy.horn.graveDustCost` | `1` | Polvo de tumba que gasta cada soplido |
| `creatures.demonology.grimoire.healthCost` | `6.0` | Vida que se paga al atar un diablillo |
| `creatures.demonology.grimoire.requiresImpHorn` | `true` | Atar gasta un cuerno de diablillo |
| `creatures.rites.enabled` | `true` | Activa los ritos de círculo que añade el módulo |

### `spirit`

| Clave | Por defecto | Qué es |
| --- | --- | --- |
| `spirit.enabled` | `true` | Apaga todo el módulo del mundo espiritual |
| `spirit.world.enabled` | `true` | El mundo espiritual y sus portales |
| `spirit.world.portal_active_seconds` | `30` | Segundos que el portal queda abierto tras cargarlo |
| `spirit.world.portal_dust_cost` | `1` | Polvo espiritual que gasta cargar el portal |
| `spirit.world.return_delay_seconds` | `5` | Segundos que tarda la tecla de regreso |
| `spirit.world.rite_enabled` / `rite_duration_seconds` | `true` / `300` | Rito de Enchanted que manda al mundo espiritual y cuánto dura (0 = ilimitado) |
| `spirit.world.nightmare_spawns_enabled` | `true` | Las pesadillas aparecen solas en el mundo espiritual |
| `spirit.dreams.enabled` | `true` | Tejido de sueños |
| `spirit.dreams.nightmare_chance` / `good_dream_chance` | `0.15` / `0.1` | Probabilidad base de pesadilla y de buen sueño al dormir |
| `spirit.dreams.max_catchers` | `4` | Máximo de atrapasueños que cuentan |
| `spirit.dreams.good_dream_rewards` | lista | Premios de los buenos sueños con formato `objeto;cantidad;peso` |
| `spirit.infusions.enabled` | `true` | Infusiones dimensionales |
| `spirit.infusions.leap_enabled` / `climb_enabled` / `sight_enabled` | `true` | Activa cada infusión por separado |
| `spirit.infusions.essence_seconds` / `max_energy_seconds` | `120` / `900` | Energía por esencia y máximo almacenable |
| `spirit.infusions.hud_enabled` | `true` | Muestra el HUD de infusiones |
| `spirit.covens.enabled` | `true` | Aquelarres |
| `spirit.covens.max_members` | `8` | Máximo de miembros |
| `spirit.covens.found_cost_item` / `found_cost_count` | `"umc:spirit_dust"` / `3` | Objeto que se gasta al fundar un aquelarre (vacío = ninguno) |
| `spirit.covens.open_join` | `false` | Cualquiera puede unirse en el altar sin invitación |

### `crafting`

| Clave | Por defecto | Qué es |
| --- | --- | --- |
| `crafting.enabled` | `true` | Apaga todo el módulo de artesanía |
| `crafting.familiars.enabled` / `ritesEnabled` | `true` / `true` | Familiares extra y sus ritos de vinculación |
| `crafting.familiars.<especie>.enabled` | `true` | `<especie>`: `toad`, `owl`, `raven`, `hound` |
| `crafting.familiars.<especie>.bonusEffects` | según especie | Efectos que recibe el dueño, formato `"efecto,amplificador"` |
| `crafting.familiars.<especie>.tameChance` / `tameFoods` | `0.33` / lista | Probabilidad y comida para domar (sapo, lechuza y cuervo) |
| `crafting.familiars.<especie>.spawnEnabled` / `spawnWeight` | `true` / según especie | Aparición natural (sapo, lechuza y cuervo) |
| `crafting.clothing.enabled` | `true` | Bonus de la ropa de bruja |
| `crafting.clothing.setEffects` | `["minecraft:luck,0"]` | Efectos del conjunto completo |
| `crafting.clothing.altar.power` / `radius` | `30.0` / `16` | Poder que se suma a los altares cercanos y radio |
| `crafting.clothing.curses.chance` | `0.15` | Probabilidad por comprobación de debilitar una maldición con el conjunto completo |
| `crafting.clothing.potions.durationMultiplier` | `1.25` | Duración extra de las pociones beneficiosas |
| `crafting.clothing.cloak.reduction` | `0.5` | Fracción de daño por caída que quita la capa |
| `crafting.plants.enabled` | `true` | Plantas de bruja |
| `crafting.plants.wildPlants` | `true` | Genera flor de luna y artemisa en el mundo |
| `crafting.plants.seedDropChance` | `0.08` | Probabilidad de soltar semillas al romper hierba |
| `crafting.plants.seedDropHoeOnly` | `false` | Solo suelta semillas si se rompe con azada |

### `compat.kubejs`: apagar compatibilidades

```toml
[compat.kubejs]
	enabled = true

	[compat.kubejs.modules]
		enchanted = true
		thaumaturge = true
		ae2 = true
		advanced_ae = true
		extendedae = true
		create = true
		generatorgalore = true
		excessive_utilities = true
		functionalstorage = true
		mysticalagriculture = true
```

| Clave | Mod que controla |
| --- | --- |
| `compat.kubejs.enabled` | Interruptor general: en `false` no se registra ningún módulo de KubeJS de este mod |
| `compat.kubejs.modules.enchanted` | Enchanted |
| `compat.kubejs.modules.thaumaturge` | Thaumaturge |
| `compat.kubejs.modules.ae2` | Applied Energistics 2 |
| `compat.kubejs.modules.advanced_ae` | Advanced AE |
| `compat.kubejs.modules.extendedae` | Extended AE |
| `compat.kubejs.modules.create` | Create (incluye la acción por clic derecho sobre entidades y la ayuda de JEI) |
| `compat.kubejs.modules.generatorgalore` | Generator Galore |
| `compat.kubejs.modules.excessive_utilities` | Excessive Utilities |
| `compat.kubejs.modules.functionalstorage` | Functional Storage |
| `compat.kubejs.modules.mysticalagriculture` | Mystical Agriculture (recetas y evento `MysticalEvents`) |

Para desactivar una sola compatibilidad pon su clave en `false`. Se aplica al arrancar el juego.

### `compat.mysticalagriculture`: cultivos extra

| Clave | Por defecto | Qué es |
| --- | --- | --- |
| `compat.mysticalagriculture.enabled` | `true` | Interruptor general: en `false` no se registra ningún cultivo |
| `compat.mysticalagriculture.crop_namespace` | `"umc"` | Namespace de los ids de cultivo (los objetos siguen siendo `mysticalagriculture:`) |
| `compat.mysticalagriculture.recipes.seed_crafting` | `true` | Receta de crafteo de cada semilla |
| `compat.mysticalagriculture.recipes.seed_infusion` | `true` | Receta de cada semilla en el Altar de Infusión |
| `compat.mysticalagriculture.recipes.seed_reprocessor` | `true` | Receta de cada semilla en el Reprocesador de Semillas |
| `compat.mysticalagriculture.recipes.essence_crafting` | `true` | Receta que convierte la esencia en el objeto del cultivo |
| `compat.mysticalagriculture.crops.<id>` | `true` | Registra o no cada cultivo (ver el apartado 5 para los ids) |

Los cultivos se registran al arrancar, así que cambiar estas claves requiere reiniciar el juego.

---

## 4. Compatibilidad con KubeJS

Todo este apartado necesita **KubeJS** (2101.7.2 o superior para 1.21.1) y el mod al que apoya cada subsección.

### Dónde va cada script

| Carpeta | Se carga | Para qué se usa aquí |
| --- | --- | --- |
| `kubejs/startup_scripts/` | Al arrancar el juego (hay que reiniciar para ver cambios) | `EnchantedStartup.*`, `ThaumaturgeStartup.*`, `StartupEvents.registry('item', ...)` |
| `kubejs/server_scripts/` | Al entrar al mundo y con `/reload` | `ServerEvents.recipes`, `EnchantedEvents.*`, `ThaumaturgeEvents.*`, `MysticalEvents.recipes`, `ServerEvents.generateData` |
| `kubejs/client_scripts/` | Al cargar recursos en el cliente | `ClientEvents.lang` |

### Convenciones comunes

- Los ítems se escriben `'namespace:item'`. Con cantidad: `'3x namespace:item'`. Etiquetas: `'#namespace:etiqueta'`.
- Todas las recetas aceptan `.id('namespace:nombre')` al final, como cualquier receta de KubeJS.
- Si un id propio no lleva namespace, se usa `kubejs:`.
- Los fluidos se escriben con `Fluid.of('minecraft:water', 1000)` (cantidad en mB).
- Los scripts de ejemplo completos están en la carpeta `kubejs-ejemplos/` del repositorio (un archivo por mod).

---

### 4.1. Enchanted

Módulo `compat.kubejs.modules.enchanted`. Mod necesario: `enchanted` (siempre presente, porque es obligatorio).

Qué añade:

| Qué | Dónde se escribe | Para qué |
| --- | --- | --- |
| `event.recipes.enchanted.kettle / wheel / distilling / byproduct` | `ServerEvents.recipes` | Recetas del caldero, la rueca, el destilador y el subproducto del horno |
| `EnchantedEvents.rites` | server | Ritos de círculo mágico nuevos y desactivar ritos |
| `EnchantedEvents.shapes` | server | Formas de círculo nuevas |
| `EnchantedEvents.altar` | server | Fuentes de poder y mejoras del altar |
| `EnchantedEvents.mutagens` | server | Mutaciones de plantas (mutandis) |
| `EnchantedStartup.riteFactories` | startup | Ritos con código JS propio |
| `EnchantedStartup.curses` | startup | Maldiciones nuevas |
| `EnchantedStartup.familiars` | startup | Familiares nuevos |
| `StartupEvents.registry('item', ...)` con tipos `enchanted:*` | startup | Poppets, brebajes y frascos |

Los ritos, formas, mutaciones y mejoras de altar se generan como datos al cargar el servidor.

#### Recetas

Alias disponibles: `enchantedKettle`, `enchantedWheel` (o `enchantedSpinning`), `enchantedDistilling`, `enchantedByproduct`.

| Receta | Llamada | Argumentos |
| --- | --- | --- |
| Caldero | `event.recipes.enchanted.kettle(resultado, [ingredientes])` | `resultado` (ítem con cantidad), `ingredientes` (lista de ítems) |
| Rueca | `event.recipes.enchanted.wheel(resultado, [3 ingredientes])` | `resultado`, exactamente 3 ingredientes |
| Destilador | `event.recipes.enchanted.distilling([resultados], [ingredientes])` | de 1 a 4 resultados y de 1 a 3 ingredientes |
| Subproducto | `event.recipes.enchanted.byproduct(resultado, ingrediente)` | `resultado`, `ingrediente` (ítem o etiqueta) |

Opciones encadenables:

| Receta | Método | Tipo | Qué es | Por defecto |
| --- | --- | --- | --- | --- |
| kettle | `.requiresBottle(bool)` | booleano | Necesita un frasco | `false` |
| kettle | `.power(int)` | entero | Poder de altar que gasta | `0` |
| kettle | `.cookColour(color)` | color | Color del líquido mientras se cuece (`'#B046A5'`, `'B046A5'` o `0xB046A5`; también `cookColor`) | `#2D155E` |
| kettle | `.finalColour(color)` | color | Color del líquido al terminar (también `finalColor`) | `#4A1AAD` |
| wheel | `.power(int)` | entero | Poder de altar que gasta | `0` |
| wheel | `.duration(ticks)` | entero | Duración en ticks | `300` |
| distilling | `.duration(ticks)` | entero | Duración en ticks | `300` |
| distilling | `.power(int)` | entero | Poder de altar que gasta | `750` |

| Receta | Entra | Sale | Dónde |
| --- | --- | --- | --- |
| Ejemplo `kettle` | `minecraft:poppy`, `golden_carrot`, `lily_pad`, `cocoa_beans`, `enchanted:whiff_of_magic`, `enchanted:water_artichoke` y un frasco | 3x `enchanted:brew_of_love` | Caldero (el líquido pasa de `#B046A5` a `F78FEB`) |
| Ejemplo `wheel` | `enchanted:poppet` + `enchanted:demonic_blood` + `minecraft:magma_block`, 500 de poder, 300 ticks | `kubejs:lava_poppet` | Rueca de hilar |
| Ejemplo `distilling` | 3x `enchanted:clay_jar` + `enchanted:breath_of_the_goddess` + `minecraft:lapis_lazuli`, 300 ticks, 750 de poder | `enchanted:tear_of_the_goddess`, `enchanted:whiff_of_magic`, `minecraft:slime_ball` | Destilador |
| Ejemplo `byproduct` | Cualquier `#minecraft:logs_that_burn` | `enchanted:foul_fume` como subproducto | Horno de la bruja con un `clay_jar` |

```js
ServerEvents.recipes(event => {
    //da 3x enchanted:brew_of_love mezclando minecraft:poppy, golden_carrot, lily_pad, cocoa_beans, enchanted:whiff_of_magic y water_artichoke en el caldero (kettle) con frasco
    event.recipes.enchanted.kettle('3x enchanted:brew_of_love', [
        'minecraft:poppy',
        'minecraft:golden_carrot',
        'minecraft:lily_pad',
        'minecraft:cocoa_beans',
        'enchanted:whiff_of_magic',
        'enchanted:water_artichoke'
    ]).requiresBottle(true).cookColour('#B046A5').finalColour('F78FEB')

    //da kubejs:lava_poppet tejiendo enchanted:poppet + enchanted:demonic_blood + minecraft:magma_block en la rueda de hilar con 500 de poder de altar durante 300 ticks
    event.recipes.enchanted.wheel('kubejs:lava_poppet', ['enchanted:poppet', 'enchanted:demonic_blood', 'minecraft:magma_block'])
        .power(500)
        .duration(300)

    //da enchanted:tear_of_the_goddess, enchanted:whiff_of_magic y minecraft:slime_ball destilando 3x enchanted:clay_jar + breath_of_the_goddess + minecraft:lapis_lazuli
    event.recipes.enchanted.distilling(
        ['enchanted:tear_of_the_goddess', 'enchanted:whiff_of_magic', 'minecraft:slime_ball'],
        ['3x enchanted:clay_jar', 'enchanted:breath_of_the_goddess', 'minecraft:lapis_lazuli']
    ).duration(300).power(750)

    //da enchanted:foul_fume como subproducto al quemar cualquier tronco inflamable en el horno de la bruja con un clay_jar
    event.recipes.enchanted.byproduct('enchanted:foul_fume', '#minecraft:logs_that_burn')
})
```

#### Ritos de círculo (`EnchantedEvents.rites`)

`event.create(id)` crea un rito y se le encadenan requisitos y un efecto final (la "factory"). **Todo rito necesita un efecto final**; si falta, el evento da error.

Requisitos y condiciones:

| Método | Argumentos | Qué hace |
| --- | --- | --- |
| `.item(stack)` / `.items(...stacks)` | ítems con cantidad (`'2x minecraft:redstone'`) | Objetos que se ofrecen en el círculo |
| `.smallCircle(tiza)` / `.mediumCircle(tiza)` / `.largeCircle(tiza)` | tiza (`'ritual_chalk'`, `'nether_chalk'`) | Círculo pequeño, mediano o grande con esa tiza. Sin namespace se usa `enchanted:` |
| `.circle(forma, tiza)` | forma y tiza | Forma de círculo por id (por ejemplo una propia `kubejs:cross_circle`) |
| `.entity(...tipos)` | ids de entidad | Entidades necesarias |
| `.dimension(...ids)` | ids de dimensión | Solo en esas dimensiones |
| `.power(int)` | entero | Poder de altar que gasta al empezar |
| `.tickPower(int)` | entero | Poder extra por tick |
| `.weather(clima)` | `none`, `clear`, `raining`, `thundering` | Clima necesario |
| `.night()` / `.day()` | sin argumentos | Solo de noche (ticks 13000 a 23000) o de día (0 a 12000) |
| `.time(desde, hasta)` | ticks | Franja horaria propia |

Efecto final (se usa uno por rito):

| Método | Argumentos | Qué hace |
| --- | --- | --- |
| `.factory(id)` / `.factory(id, {parametros})` | id de factory y objeto de parámetros | Activa una factory de Enchanted o una tuya (`EnchantedStartup.riteFactories`) |
| `.createItem(...stacks)` | ítems | Crea esos ítems |
| `.command(...comandos)` | comandos | Ejecuta comandos |
| `.applyCurse(id)` | id de maldición | Aplica una maldición |
| `.protection(radio, duración, bloqueaJugadores)` | radio, ticks, booleano | Zona protegida (también `.protection(radio)`) |
| `.entityBoundCreateItem(...)`, `.locationBoundCreateItem(...)` | ítems | Crea ítems ligados a una entidad o un lugar |
| `.duplicateItem(item, cantidad)` | ítem y entero | Duplica un ítem |
| `.removeCurse(id)` | id de maldición | Quita una maldición |
| `.commandSequence(retraso, pasos)` | ticks y lista de listas de comandos | Comandos por pasos |
| `.fertility(radio, probabilidad)`, `.forest(radio, separación)`, `.blight(radio, probDecaimiento, probZombi)`, `.broiling(probQuemado)` | números | Efectos de terreno de Enchanted |
| `.transposeBlocks(etiquetaBloques, herramienta)` | etiqueta e ítem | Transposición de bloques |
| `.skyWrath()`, `.sanctity()`, `.imprisonment()`, `.totalEclipse()`, `.summonEntity()`, `.bindFamiliar()`, `.summonFamiliar()`, `.bindTalisman()`, `.transposeCaster()` | sin argumentos | Factories sueltas de Enchanted |

Para anular un rito de Enchanted: `event.disable('enchanted:blight')`. El rito original se cambia por uno imposible de cumplir.

| Ejemplo | Entra | Sale | Dónde |
| --- | --- | --- | --- |
| `kubejs:diamond_gift` | 1 `minecraft:coal_block`, 800 de poder | 1 `minecraft:diamond` y 2 `minecraft:emerald` | Círculo pequeño de tiza ritual |
| `kubejs:blessing_rite` | `enchanted:tear_of_the_goddess` + `minecraft:golden_apple`, 1500 de poder (+2 por tick), de noche con cielo despejado | Activa la factory `kubejs:blessing` | Círculo mediano de tiza ritual |
| `kubejs:thunder_rite` | `minecraft:iron_ingot` + 2x `minecraft:redstone`, 2500 de poder, tormenta, solo Overworld | Activa la factory `kubejs:thunder_call` | Forma `kubejs:cross_circle` con tiza del Nether |
| `kubejs:curse_heavy_feet` | `enchanted:taglock` + `minecraft:anvil`, 1800 de poder | Aplica la maldición `kubejs:heavy_feet` | Círculo mediano de tiza del Nether |
| `kubejs:quick_day` | `minecraft:clock`, 600 de poder | Ejecuta `time set day` y `weather clear` | Círculo pequeño de tiza ritual |
| `kubejs:big_protection` | `minecraft:obsidian` + `minecraft:diamond`, 2000 de poder (+3 por tick) | Zona protegida de radio 12 durante 12000 ticks (10 min) que también bloquea jugadores | Círculo mediano de tiza ritual |

```js
EnchantedEvents.rites(event => {
    //hace un rito nocturno con cielo despejado: Tear of the Goddess + manzana dorada en circulo mediano de tiza ritual, gasta 1500 de poder (+2 por tick) y activa la factory kubejs:blessing
    event.create('kubejs:blessing_rite')
        .item('enchanted:tear_of_the_goddess')
        .item('minecraft:golden_apple')
        .mediumCircle('ritual_chalk')
        .power(1500)
        .tickPower(2)
        .night()
        .weather('clear')
        .factory('kubejs:blessing', { message: 'Que la luna te proteja', level: 1 })

    //hace un rito con tormenta en el Overworld: lingote de hierro + 2 redstone en el circulo kubejs:cross_circle de tiza del Nether, gasta 2500 y activa kubejs:thunder_call
    event.create('kubejs:thunder_rite')
        .items('minecraft:iron_ingot', '2x minecraft:redstone')
        .circle('kubejs:cross_circle', 'enchanted:nether_chalk')
        .power(2500)
        .weather('thundering')
        .dimension('minecraft:overworld')
        .factory('kubejs:thunder_call')

    //da 1 minecraft:diamond y 2 minecraft:emerald sacrificando un minecraft:coal_block en circulo pequeno de tiza ritual, gasta 800
    event.create('kubejs:diamond_gift')
        .item('minecraft:coal_block')
        .smallCircle('ritual_chalk')
        .power(800)
        .createItem('minecraft:diamond', '2x minecraft:emerald')

    //hace aplicar la maldicion kubejs:heavy_feet con taglock + yunque en circulo mediano de tiza del Nether, gasta 1800
    event.create('kubejs:curse_heavy_feet')
        .items('enchanted:taglock', 'minecraft:anvil')
        .mediumCircle('nether_chalk')
        .power(1800)
        .applyCurse('kubejs:heavy_feet')

    //hace poner dia y cielo despejado con un reloj en circulo pequeno de tiza ritual, gasta 600
    event.create('kubejs:quick_day')
        .item('minecraft:clock')
        .smallCircle('ritual_chalk')
        .power(600)
        .command('time set day', 'weather clear')

    //hace una zona protegida de radio 12 durante 12000 ticks (10 min) que tambien bloquea a jugadores, con obsidiana + diamante en circulo mediano, gasta 2000 (+3 por tick)
    event.create('kubejs:big_protection')
        .items('minecraft:obsidian', 'minecraft:diamond')
        .mediumCircle('ritual_chalk')
        .power(2000)
        .tickPower(3)
        .protection(12, 12000, true)

    //hace inutilizable el rito enchanted:blight (lo sustituye por uno imposible de cumplir)
    event.disable('enchanted:blight')
})
```

#### Formas de círculo (`EnchantedEvents.shapes`)

`event.create(id).rows(...filas)`

| Argumento | Tipo | Qué es |
| --- | --- | --- |
| `id` | texto | Id de la forma (por ejemplo `'kubejs:cross_circle'`) |
| `filas` | textos | Número **impar** de filas, todas del mismo largo que el número de filas (cuadrada), con al menos una `X`. `X` es una casilla de tiza y el espacio es vacío |

Entra: las filas. Sale: una forma que se usa con `.circle('kubejs:cross_circle', tiza)` en un rito.

```js
EnchantedEvents.shapes(event => {
    //hace la forma kubejs:cross_circle, cuadrada de 7x7 (X = casilla de tiza, O = interior sin tiza, espacio = vacio)
    event.create('kubejs:cross_circle').rows(
        '  XXX  ',
        '  XOX  ',
        'XXXOXXX',
        'XOOOOOX',
        'XXXOXXX',
        '  XOX  ',
        '  XXX  '
    )
})
```

#### Altar (`EnchantedEvents.altar`)

| Llamada | Argumentos | Qué hace |
| --- | --- | --- |
| `event.block(bloque, poder, límite)` | bloque, entero, entero | Cada bloque cercano da ese poder al altar, contando hasta el límite |
| `event.tag(etiqueta, poder, límite)` | etiqueta de bloques (sin `#`), entero, entero | Igual, para una etiqueta |
| `event.upgrade(id).type(tipo).block(bloque).power(decimal).recharge(decimal).priority(int)` | ver ejemplo | Mejora de altar |

```js
EnchantedEvents.altar(event => {
    //da 6 de poder al altar por cada minecraft:amethyst_block cercano, contando hasta 16 bloques
    event.block('minecraft:amethyst_block', 6, 16)
    //da 3 de poder al altar por cada bloque del tag minecraft:flowers cercano, contando hasta 40
    event.tag('minecraft:flowers', 3, 40)
    //hace una mejora de altar con minecraft:soul_lantern de tipo torch: poder 0, recarga x1.5 y prioridad 1
    event.upgrade('kubejs:soul_lantern').type('torch').block('minecraft:soul_lantern').power(0).recharge(1.5).priority(1)
})
```

#### Mutaciones (`EnchantedEvents.mutagens`)

`event.create(id).mutation(resultado, peso, ...bloquesMutagenos).extremisMutation(resultado, peso, ...bloquesMutagenos)`

| Argumento | Tipo | Qué es |
| --- | --- | --- |
| `id` | texto | Id de la mutación |
| `mutation` | resultado, peso, bloques | Mutación con *mutandis* |
| `extremisMutation` | resultado, peso, bloques | Mutación con *mutandis extremis* |

| Entra | Sale |
| --- | --- |
| `minecraft:stone` o `minecraft:deepslate` alrededor, con mutandis | `minecraft:amethyst_block` (peso 10) |
| `minecraft:amethyst_block` alrededor, con mutandis extremis | `minecraft:budding_amethyst` (peso 2) |

```js
EnchantedEvents.mutagens(event => {
    event.create('kubejs:crystals')
        .mutation('minecraft:amethyst_block', 10, 'minecraft:stone', 'minecraft:deepslate')
        .extremisMutation('minecraft:budding_amethyst', 2, 'minecraft:amethyst_block')
})
```

#### Ritos con código (`EnchantedStartup.riteFactories`)

`event.create(id).duration(ticks).output(...items).onStart(ctx => bool).onTick(ctx => bool).onStop(ctx => ...)`

| Método | Qué es |
| --- | --- |
| `.duration(ticks)` | Duración del rito |
| `.output(...items)` | Ítems que se declaran como salida |
| `.onStart(ctx => bool)` | Al empezar. Devuelve `true` para seguir o `false` para terminar al momento (`ctx.cancel()` cancela) |
| `.onTick(ctx => bool)` | Cada tick |
| `.onStop(ctx => ...)` | Al terminar |

Contexto `ctx`: `caster`, `level`, `pos`, `ticks`, `data` (NBT propio del rito), `getString(clave, defecto)`, `getInt(clave, defecto)` (también `getDouble` y `getBoolean`), `particles(partícula, cantidad, dispersión)`, `drop(ítem)`, `runCommand(comando)`, `cancel()`. Los parámetros salen del segundo argumento de `.factory(id, {parametros})` del rito.

```js
EnchantedStartup.riteFactories(event => {
    //hace un rito de 200 ticks que da Regeneracion al lanzador cada segundo, avisa por chat y declara como salida minecraft:golden_apple
    event.create('kubejs:blessing')
        .duration(200)
        .output('minecraft:golden_apple')
        .onStart(ctx => {
            const caster = ctx.caster
            if (caster == null) {
                return ctx.cancel()
            }
            caster.tell(ctx.getString('message', 'La bendicion comienza'))
            ctx.data.putInt('pulses', 0)
            return true
        })
        .onTick(ctx => {
            if (ctx.ticks % 20 == 0) {
                ctx.particles('minecraft:happy_villager', 12, 1.5)
                const caster = ctx.caster
                if (caster != null) {
                    caster.potionEffects.add('minecraft:regeneration', 60, ctx.getInt('level', 0))
                }
                ctx.data.putInt('pulses', ctx.data.getInt('pulses') + 1)
            }
            return true
        })
        .onStop(ctx => {
            ctx.runCommand('say La bendicion termino tras ' + ctx.data.getInt('pulses') + ' pulsos')
        })

    //hace un rito instantaneo que invoca un rayo y suelta un minecraft:lightning_rod sobre el circulo (onStart devuelve false y termina al momento)
    event.create('kubejs:thunder_call')
        .onStart(ctx => {
            ctx.level.getServer().runCommandSilent('summon minecraft:lightning_bolt ' + ctx.pos.x + ' ' + ctx.pos.y + ' ' + ctx.pos.z)
            ctx.drop(Item.of('minecraft:lightning_rod'))
            return false
        })
})
```

#### Maldiciones (`EnchantedStartup.curses`)

`event.create(id).maxStrength(int).onInitialise(ctx => ...).onRemove(ctx => ...).tick(ctx => ...)`

| Método | Qué es |
| --- | --- |
| `.maxStrength(int)` | Fuerza máxima de la maldición |
| `.onInitialise(ctx => ...)` | Al aplicarse |
| `.onRemove(ctx => ...)` | Al quitarse |
| `.tick(ctx => ...)` | Cada tick |

Contexto `ctx`: `target` (el jugador), `strength` (fuerza actual), `age` (ticks desde que se aplicó).

```js
EnchantedStartup.curses(event => {
    //hace una maldicion de fuerza maxima 3 que avisa al aplicarse y al quitarse, y da Lentitud (nivel = fuerza - 1) cada 40 ticks
    event.create('kubejs:heavy_feet')
        .maxStrength(3)
        .onInitialise(ctx => ctx.target.tell('Tus pies pesan como el plomo'))
        .onRemove(ctx => ctx.target.tell('Vuelves a sentirte ligero'))
        .tick(ctx => {
            if (ctx.age % 40 == 0) {
                ctx.target.potionEffects.add('minecraft:slowness', 60, ctx.strength - 1)
            }
        })
})
```

#### Familiares (`EnchantedStartup.familiars`)

`event.create(id, entidadOrigen, entidadFamiliar).copy((from, familiar) => ...)`

| Argumento | Tipo | Qué es |
| --- | --- | --- |
| `id` | texto | Id del familiar |
| `entidadOrigen` | id de entidad | Animal domable que se convierte |
| `entidadFamiliar` | id de entidad | Entidad en la que se convierte |
| `copy` | función | Copia datos del original al familiar |

Se usa con un rito `bindFamiliar`.

```js
EnchantedStartup.familiars(event => {
    //hace un familiar que convierte un minecraft:parrot en otro minecraft:parrot con el nombre Familiar
    event.create('kubejs:parrot_familiar', 'minecraft:parrot', 'minecraft:parrot')
        .copy((from, familiar) => {
            familiar.setCustomName(Text.of('Familiar'))
        })
})
```

#### Ítems de Enchanted (poppets, brebajes y frascos)

Se crean con `StartupEvents.registry('item', ...)` indicando el tipo como segundo argumento. Los objetos creados así van a la pestaña del mod con una textura por defecto; pon la tuya en `kubejs/assets/kubejs/textures/item/<id>.png`.

| Tipo | Métodos |
| --- | --- |
| `enchanted:player_poppet` | `.poppetColour(colorPrimario, colorSecundario)`, `.maxDamage(usos)`, `.protectsAgainst(...tiposDeDaño o #etiquetas)`, `.protectsWhen(source => bool)`, `.effect(efecto, ticks, amplificador)`, `.onProtect(player => ...)` |
| `enchanted:item_poppet` | `.poppetColour(c1, c2)`, `.protects(stack => bool)`, `.restoreMultiplier(fracción)`, `.onProtect(...)` |
| `enchanted:brew` | `.effect(efecto, ticks, amplificador)` (repetible), `.onDrink(entity => ...)` |
| `enchanted:throwable_brew` | `.effect(...)` (repetible), `.radius(bloques)`, `.brewColour(colorRGB)`, `.onSplash(ctx => ...)` con `ctx = {owner, level, pos}` |

| Ejemplo | Qué da |
| --- | --- |
| `lava_poppet` (`player_poppet`) | Poppet de 2 usos que protege del fuego y la lava, da Resistencia al fuego 30 s y avisa al jugador |
| `anvil_poppet` (`player_poppet`) | Poppet que protege del daño de yunque; sin `.effect` da Regeneración II 5 s por defecto |
| `iron_poppet` (`item_poppet`) | Poppet de objeto que protege ítems `minecraft:iron_*` y les restaura la mitad de la durabilidad |
| `moon_brew` (`brew`) | Poción bebible: Visión nocturna 5 min y Caída lenta 1 min |
| `storm_flask` (`throwable_brew`) | Frasco arrojable: Resplandor 20 s a las criaturas en radio 5 y un rayo donde se rompe |

```js
StartupEvents.registry('item', event => {
    //da un poppet de 2 usos que protege del fuego y la lava, da Resistencia al fuego 30 s y avisa al jugador
    event.create('lava_poppet', 'enchanted:player_poppet')
        .poppetColour(0xFFFF6A00, 0xFFB22200)
        .maxDamage(2)
        .protectsAgainst('#minecraft:is_fire', 'minecraft:lava')
        .effect('minecraft:fire_resistance', 600, 0)
        .onProtect(player => player.tell('El poppet absorbe el fuego'))

    //da un poppet que protege del dano de yunque (sin .effect da Regeneracion II 5 s por defecto)
    event.create('anvil_poppet', 'enchanted:player_poppet')
        .protectsWhen(source => source.type().msgId() == 'anvil')

    //da un poppet de objeto que protege items de hierro (minecraft:iron_*) y les restaura la mitad de la durabilidad
    event.create('iron_poppet', 'enchanted:item_poppet')
        .poppetColour(0xFFD8D8D8, 0xFF9A9A9A)
        .protects(stack => stack.id.startsWith('minecraft:iron_'))
        .restoreMultiplier(0.5)

    //da una pocion bebible que da Vision nocturna 5 min y Caida lenta 1 min y avisa al bebedor
    event.create('moon_brew', 'enchanted:brew')
        .effect('minecraft:night_vision', 6000, 0)
        .effect('minecraft:slow_falling', 1200, 0)
        .onDrink(entity => entity.tell('Sabe a luz de luna'))

    //da un frasco arrojable que da Resplandor 20 s a las criaturas en radio 5 y hace caer un rayo donde se rompe
    event.create('storm_flask', 'enchanted:throwable_brew')
        .effect('minecraft:glowing', 400, 0)
        .radius(5)
        .brewColour(0x55AAFF)
        .onSplash(ctx => ctx.level.spawnLightning(ctx.pos.x(), ctx.pos.y(), ctx.pos.z(), true))
})
```

Notas de uso:

- Los ritos propios (`kubejs:...`) se guardan como datos en `data/<namespace>/enchanted/circle_magic/rite/<nombre>.json`. Los ritos que añade el propio mod UMC (criaturas, familiares, viaje espiritual) usan el namespace `enchanted`.
- Las tizas y formas sin namespace se leen como `enchanted:` (`'ritual_chalk'` es `enchanted:ritual_chalk`).
- Una factory o una maldición propia se registran en `startup_scripts`; los ritos que las usan van en `server_scripts`.

---

### 4.2. Thaumaturge

Módulo `compat.kubejs.modules.thaumaturge`. Mod necesario: `thaumaturge`.

| Qué | Dónde se escribe | Para qué |
| --- | --- | --- |
| `ThaumaturgeStartup.wandCaps` | startup | Remates de varita nuevos |
| `ThaumaturgeStartup.wandRods` | startup | Varas de varita nuevas |
| `ThaumaturgeStartup.golemTraits` | startup | Rasgos de golem |
| `ThaumaturgeStartup.mobTraits` | startup | Rasgos de mob (campeones) |
| `ThaumaturgeEvents.aspects` | server | Aspectos nuevos y repartir aspectos a objetos, mobs y biomas |
| `ThaumaturgeEvents.scans` | server | Escaneos |
| `ThaumaturgeEvents.blueprints` | server | Planos de multibloque |
| `ThaumaturgeEvents.research` | server | Categorías y entradas de investigación |
| `ThaumaturgeEvents.data` | server | JSON a mano para cualquier registro de datos sin evento propio |
| `event.recipes.thaumaturge.<tipo>` | `ServerEvents.recipes` | Recetas (tabla más abajo) |

Los aspectos se escriben con o sin namespace: `'ignis'` es `thaumaturge:ignis`. Con cantidad: `'30x ignis'`, `'10x kubejs:lux'`.

#### Remates de varita (`ThaumaturgeStartup.wandCaps`)

`event.create(id)` y encadenar:

| Método | Tipo | Qué es |
| --- | --- | --- |
| `.baseCost(multiplicador)` | decimal | Multiplicador del coste de vis |
| `.specialCost(multiplicador, ...aspectos)` | decimal y aspectos | Coste distinto para esos aspectos |
| `.aspectCost(aspecto, multiplicador)` | aspecto y decimal | Coste de un solo aspecto |
| `.craftCost(coste)` | entero | Coste de fabricar el remate |
| `.texture(ruta)` | texto | Textura del remate |

```js
ThaumaturgeStartup.wandCaps(event => {
    //hace que se cree el remate kubejs:emerald
    event.create('kubejs:emerald')
        .baseCost(0.95)                      //el remate multiplica el coste de vis por 0.95
        .specialCost(0.9, 'aqua', 'terra')   //el coste baja a 0.9 para los aspectos aqua y terra
        .craftCost(5)                        //fabricar el remate cuesta 5
        .texture('kubejs:textures/models/wand_cap_emerald.png')

    //hace que se cree el remate kubejs:prism
    event.create('kubejs:prism')
        .baseCost(1.0)
        .aspectCost('ignis', 0.8)
        .aspectCost('aer', 0.85)
        .craftCost(4)
})
```

#### Varas de varita (`ThaumaturgeStartup.wandRods`)

| Método | Tipo | Qué es |
| --- | --- | --- |
| `.capacity(vis)` | entero | Reserva de vis de la vara |
| `.craftCost(coste)` | entero | Coste de fabricar la vara |
| `.glow(bool)` | booleano | La vara brilla |
| `.texture(ruta)` | texto | Textura de la vara |
| `.onUpdate((stack, player) => ...)` | función | Se ejecuta con la varita en el inventario |
| `.staff(bool)`, `.runes(bool)`, `.assemblyResearch(investigación)` | | Otras opciones |

```js
ThaumaturgeStartup.wandRods(event => {
    //hace que se cree la vara kubejs:crystal
    event.create('kubejs:crystal')
        .capacity(90)
        .craftCost(7)
        .glow(true)
        .texture('kubejs:textures/models/wand_rod_crystal.png')
        //cada 200 ticks con la varita en el inventario se da velocidad al jugador
        .onUpdate((stack, player) => {
            if (player.tickCount % 200 == 0) {
                player.potionEffects.add('minecraft:speed', 100, 0)
            }
        })
})
```

#### Rasgos de golem y de mob

`golemTraits`: `event.create(id).icon(rutaTextura)` (solo necesita id e icono).

`mobTraits`: `event.create(id)` con:

| Método | Qué es |
| --- | --- |
| `.champion(bool)` | El rasgo cuenta como campeón |
| `.tick(mob => ...)` | Cada tick |
| `.onAttack(ctx => nuevoDaño)` | Devuelve el daño que hace el mob (`ctx.amount`) |
| `.onHurt(ctx => nuevoDaño)` | Devuelve el daño que recibe (`ctx.amount`, `ctx.source`) |

```js
ThaumaturgeStartup.golemTraits(event => {
    event.create('kubejs:fragile')
        .icon('kubejs:textures/misc/golem/tag_fragile.png')
})

ThaumaturgeStartup.mobTraits(event => {
    //hace que se cree el rasgo de mob kubejs:vampiric
    event.create('kubejs:vampiric')
        .champion(true)
        //cada 100 ticks el mob herido recupera 1 de vida
        .tick(mob => {
            if (mob.tickCount % 100 == 0 && mob.health < mob.maxHealth) {
                mob.heal(1)
            }
        })
        //el dano que hace el mob se multiplica por 1.25
        .onAttack(ctx => ctx.amount * 1.25)
        //el dano que recibe el mob se duplica si es por fuego
        .onHurt(ctx => (ctx.source.getMsgId() == 'inFire' || ctx.source.getMsgId() == 'onFire') ? ctx.amount * 2 : ctx.amount)
})
```

#### Aspectos (`ThaumaturgeEvents.aspects`)

| Llamada | Argumentos | Qué hace |
| --- | --- | --- |
| `event.create(id)` | id | Crea un aspecto nuevo. Se encadena `.color(hex)`, `.chatColor(código)`, `.components(aspecto1, aspecto2)` y `.texture(ruta)` |
| `event.item(objeto o #etiqueta, ...aspectos)` | ítem o etiqueta y aspectos con cantidad | Aspectos de un objeto |
| `event.removeItem(objeto o #etiqueta)` | ítem o etiqueta | Quita los aspectos de un objeto |
| `event.entity(mob, ...aspectos)` | id de entidad y aspectos | Aspectos de un mob |
| `event.biome(bioma, ...aspectos)` | id de bioma y aspectos | Aspectos de un bioma |

```js
ThaumaturgeEvents.aspects(event => {
    //hace que se cree el aspecto kubejs:lux, compuesto de ignis y aer
    event.create('kubejs:lux')
        .color(0xFFEE55)
        .chatColor('e')
        .components('ignis', 'aer')
        .texture('kubejs:textures/aspects/lux.png')

    //hace que minecraft:glowstone tenga 10x kubejs:lux y 2x thaumaturge:ignis
    event.item('minecraft:glowstone', '10x kubejs:lux', '2x thaumaturge:ignis')
    //hace que los lingotes de cobre (#c:ingots/copper) tengan 3x thaumaturge:metallum y 1x thaumaturge:potentia
    event.item('#c:ingots/copper', '3x thaumaturge:metallum', '1x thaumaturge:potentia')
    //hace que minecraft:dirt pierda sus aspectos
    event.removeItem('minecraft:dirt')
    //hace que minecraft:bee tenga 5x thaumaturge:volatus y 3x kubejs:lux
    event.entity('minecraft:bee', '5x thaumaturge:volatus', '3x kubejs:lux')
    //hace que el bioma minecraft:desert tenga thaumaturge:ignis y kubejs:lux
    event.biome('minecraft:desert', 'thaumaturge:ignis', 'kubejs:lux')
})
```

#### Escaneos (`ThaumaturgeEvents.scans`)

`event.create(id).key(clave).items(...objetos).blocks(...bloques)`

| Método | Qué es |
| --- | --- |
| `.key(clave)` | Clave que se marca al escanear (después se puede pedir como requisito en una investigación) |
| `.items(...)` | Objetos que se pueden escanear como objeto |
| `.blocks(...)` | Bloques que se pueden escanear como bloque |

```js
ThaumaturgeEvents.scans(event => {
    //hace que escanear glowstone con el escaner marque la clave kubejs:scanned/glowstone
    event.create('kubejs:scan_glowstone')
        .key('kubejs:scanned/glowstone')
        .items('minecraft:glowstone', 'minecraft:glowstone_dust')
        .blocks('minecraft:glowstone')
})
```

#### Planos de multibloque (`ThaumaturgeEvents.blueprints`)

`event.create(id).layer(...filas).blockKey(símbolo, bloque, bloqueResultante).keepKey(símbolo, bloque)`

| Método | Qué es |
| --- | --- |
| `.layer(fila1, fila2, ...)` | Una capa de la estructura. El espacio significa "no importa". **La primera `layer()` es la de ARRIBA y la última la de ABAJO** |
| `.blockKey(símbolo, bloque, bloqueResultante)` | El símbolo se construye con `bloque` y se convierte en `bloqueResultante` al activarse |
| `.keepKey(símbolo, bloque)` | El símbolo es `bloque` y se conserva al activarse |

El plano no es un objeto ni un bloque, es la forma a construir. Se activa con una receta `dust_trigger_multiblock` (más abajo).

```js
ThaumaturgeEvents.blueprints(event => {
    event.create('kubejs:lux_altar')
        //capa de arriba: anillo de G con el centro vacio
        .layer('GGG', 'G G', 'GGG')
        //capa de abajo: cruz de G con L en el centro
        .layer(' G ', 'GLG', ' G ')
        //G se construye con glowstone y se convierte en linterna marina al activarse
        .blockKey('G', 'minecraft:glowstone', 'minecraft:sea_lantern')
        //L es piedra iman y se conserva
        .keepKey('L', 'minecraft:lodestone')
})
```

#### Investigación (`ThaumaturgeEvents.research`)

Categorías: `event.createCategory(id)`

| Método | Qué es |
| --- | --- |
| `.icon(textura)` | Icono |
| `.background(textura)` | Fondo |
| `.overlayBackground(textura)` | Capa superpuesta de fondo |
| `.index(posición)` | Posición en la lista de categorías |
| `.formula(...aspectos)` | Aspectos de la fórmula, por ejemplo `'30x ignis', '10x kubejs:lux'` |
| `.requiredResearch(entrada)` | Investigación necesaria para ver la categoría |

Entradas: `event.create(id)`

| Método | Qué es |
| --- | --- |
| `.category(id)` | Categoría a la que pertenece |
| `.name(claveDeIdioma)` | Clave de idioma del título |
| `.position(x, y)` | Posición en el mapa |
| `.parent('entrada@etapa')` | Entrada de la que depende, con la etapa (`'kubejs:luminous_basics@1'`) |
| `.icon(objeto o textura)` | Icono (también `.clearIcons()`) |
| `.meta(forma)` | Forma de la entrada, por ejemplo `'round'` |
| `.complexity(n)` | Complejidad |
| `.noteAspect(...aspectos)` | Aspectos de los apuntes |
| `.stage(stage => ...)` | Una etapa (se pueden poner varias) |
| `.addendum(addendum => ...)` | Un apéndice |

Etapa (`stage`): `.text(claveDeIdioma)`, `.obtain(objeto, cantidad)`, `.craft(objeto, cantidad)`, `.recipe(receta)`, `.requiredKnowledge(tipo, categoría, cantidad)`, `.knowledge(tipo, categoría, cantidad)`, `.requiredResearch(...entradas)`, `.warp(cantidad)`.
Apéndice (`addendum`): `.text(claveDeIdioma)`, `.recipe(...)`, `.requiredResearch(entrada)`.

Para modificar una entrada que ya existe: `event.modify(id, entry => entry.complexity(1))`.

Los textos salen de claves de idioma; se añaden con `ClientEvents.lang('en_us', ...)` (o `es_es`) en `client_scripts`.

```js
ThaumaturgeEvents.research(event => {
    //hace que se cree la categoria de investigacion kubejs:radiance
    event.createCategory('kubejs:radiance')
        .icon('kubejs:textures/research/cat_radiance.png')
        .background('thaumaturge:textures/gui/gui_research_back_3.png')
        .overlayBackground('thaumaturge:textures/gui/gui_research_back_over.png')
        .index(9)
        .formula('30x ignis', '10x kubejs:lux')

    //hace que se cree la entrada kubejs:luminous_basics con dos etapas
    event.create('kubejs:luminous_basics')
        .category('kubejs:radiance')
        .name('research.kubejs.luminous_basics.title')
        .position(0, 0)
        .icon('minecraft:glowstone')
        .meta('round')
        .complexity(1)
        .noteAspect('4x kubejs:lux', '2x ignis')
        //etapa 0: obtener 8 de glowstone y pedir 1 de conocimiento observation de basics
        .stage(stage => stage
            .text('research.kubejs.luminous_basics.stage_0')
            .obtain('minecraft:glowstone', 8)
            .requiredKnowledge('observation', 'basics', 1))
        //etapa 1: pedir la receta de kubejs:luminous_blade y dar 1 de conocimiento theory de basics
        .stage(stage => stage
            .text('research.kubejs.luminous_basics.stage_1')
            .recipe('kubejs:luminous_blade')
            .knowledge('theory', 'basics', 1))

    //hace que se cree la entrada kubejs:luminous_blades, que depende de la etapa 1 de la anterior
    event.create('kubejs:luminous_blades')
        .category('kubejs:radiance')
        .name('research.kubejs.luminous_blades.title')
        .position(2, 1)
        .parent('kubejs:luminous_basics@1')
        .icon('minecraft:golden_sword')
        .complexity(2)
        //pide fabricar una espada de oro y suma 1 de disonancia
        .stage(stage => stage
            .text('research.kubejs.luminous_blades.stage_0')
            .craft('minecraft:golden_sword', 1)
            .warp(1))
        //apendice que exige haber completado kubejs:luminous_basics
        .addendum(addendum => addendum
            .text('research.kubejs.luminous_blades.addendum_0')
            .requiredResearch('kubejs:luminous_basics'))

    //hace que la entrada thaumaturge:alumentum pase a complejidad 1
    event.modify('thaumaturge:alumentum', entry => entry.complexity(1))
})

//client_scripts: textos en ingles de la investigacion
ClientEvents.lang('en_us', event => {
    event.add('research.kubejs.luminous_basics.title', 'Luminous Basics')
    event.add('research.kubejs.luminous_basics.stage_0', 'Gather glowstone and observe how it holds light.')
    event.add('research.kubejs.luminous_basics.stage_1', 'Glowstone can be infused to hold a blade of radiance.')
    event.add('research.kubejs.luminous_blades.title', 'Luminous Blades')
    event.add('research.kubejs.luminous_blades.stage_0', 'Forge a golden sword to carry the radiance.')
    event.add('research.kubejs.luminous_blades.addendum_0', 'The blade glows brighter near glowstone.')
})
```

#### Datos a mano (`ThaumaturgeEvents.data`)

`event.add(registro, id, json)` añade un archivo de datos tal cual a cualquier registro de Thaumaturge que no tenga evento propio.

```js
ThaumaturgeEvents.data(event => {
    //hace que se anada un resultado de inestabilidad que da zap de 4 a 7 de dano con peso 2
    event.add('instability_outcome', 'kubejs:shocking_failure', {
        effect: {
            type: 'thaumaturge:zap',
            damage: { type: 'minecraft:uniform', min_inclusive: 4, max_inclusive: 7 }
        },
        weight: 2
    })
})
```

#### Recetas de Thaumaturge

Opciones comunes: `.research(investigación)` (la receta solo se ve con esa investigación), `.crystals([cristales extra])` (hasta 6 aspectos primarios), `.group(grupo)`.

Una investigación se escribe `'id'`, `'id@etapa'` (por ejemplo `'thaumaturge:unlock_alchemy@0'`) o `'!id'` para negarla. Sin namespace se usa `thaumaturge:`.

Alias: `thaumaturgeArcaneShaped`, `thaumaturgeArcaneShapeless`, `thaumaturgeCrucible`, `thaumaturgeInfusion`, `thaumaturgeInfusionEnchantment`, `thaumaturgeRunicAugment`, `thaumaturgeDustTrigger`, `thaumaturgeDustTriggerTag`, `thaumaturgeDustTriggerMultiblock`.

| Receta | Llamada | Argumentos |
| --- | --- | --- |
| Mesa arcana con forma | `arcane_workbench_shaped(resultado, filas, claves, vis)` | resultado; filas (lista de textos); claves (objeto letra a ingrediente); vis (entero mayor que 0) |
| Mesa arcana sin forma | `arcane_workbench_shapeless(resultado, [ingredientes], vis)` | de 1 a 9 ingredientes |
| Crisol | `crucible(resultado, catalizador, [aspectos])` | catalizador (ingrediente); aspectos con cantidad |
| Infusión | `infusion(resultado, catalizador, [componentes], [aspectos])` | de 1 a 64 componentes. Opciones `.instability(0 a 100)` y `.catalystPatch({componentes})` |
| Infusión (el catalizador se queda) | `infusion(catalizador, [componentes], [aspectos])` | sin resultado: el catalizador recibe el parche de `.catalystPatch` |
| Encantamiento por infusión | `infusion_enchantment(encantamiento, [componentes], [aspectos], objetoDeEjemplo)` | el objeto de ejemplo solo se muestra |
| Aumento rúnico | `runic_augment([componentes], ingredientePorNivel, [aspectos], objetoDeEjemplo)` | gasta un ingrediente por nivel |
| Polvo sobre bloque | `dust_trigger_simple(resultado, bloqueObjetivo)` | clic derecho con Salis Mundus |
| Polvo sobre etiqueta | `dust_trigger_tag(resultado, etiquetaDeBloques)` | etiqueta sin `#` |
| Polvo sobre multibloque | `dust_trigger_multiblock(resultado, idDelPlano)` | el plano se define en `ThaumaturgeEvents.blueprints` |

| Receta | Entra | Sale | Dónde |
| --- | --- | --- | --- |
| `arcane_workbench_shaped` | `minecraft:charcoal` sobre `minecraft:stick`, 15 de vis, cristales `2x ignis`, investigación `thaumaturge:first_steps` | 4x `minecraft:torch` | Mesa arcana |
| `arcane_workbench_shapeless` | `minecraft:torch` + `minecraft:iron_nugget`, 20 de vis, cristales `ignis` y `ordo` | `minecraft:lantern` | Mesa arcana |
| `crucible` | `minecraft:coal` + `5x ignis` + `5x perditio` | 3x `minecraft:gunpowder` | Crisol (investigación `thaumaturge:alumentum`) |
| `infusion` | `minecraft:golden_sword` en el centro, `minecraft:glowstone` x2 y `glowstone_dust` en los pedestales, `50x kubejs:lux` + `30x ignis`, inestabilidad 3 | `kubejs:luminous_blade` | Altar de infusión |
| `infusion` (parche) | `minecraft:iron_pickaxe` en el centro, `minecraft:redstone_block` + `minecraft:diamond`, `20x fabrico` + `20x terra` | El mismo pico, ahora irrompible (`minecraft:unbreakable`); el catalizador se queda | Altar de infusión |
| `infusion_enchantment` | `minecraft:enchanted_book` + `#c:storage_blocks/redstone`, `40x potentia` + `60x aer` | Encantamiento `arcing` | Altar de infusión |
| `runic_augment` | `thaumaturge:salis_mundus` + `#c:gems/amber`, `40x praemunio` + `20x vitreus`, una ámbar por nivel | Pieza (ejemplo `minecraft:iron_chestplate`) mejorada | Altar de infusión |
| `dust_trigger_simple` | `minecraft:cauldron` + Salis Mundus (tras `thaumaturge:unlock_alchemy@0`) | `thaumaturge:crucible` | Clic derecho sobre el caldero |
| `dust_trigger_tag` | Cualquier `c:bookshelves` + Salis Mundus | `thaumaturge:thaumonomicon` | Clic derecho sobre una estantería |
| `dust_trigger_multiblock` | Plano `kubejs:lux_altar` construido + Salis Mundus | `minecraft:beacon` (solo se muestra y cuenta como fabricado) | Clic derecho sobre cualquier bloque de la estructura |

```js
ServerEvents.recipes(event => {
    //da 4x minecraft:torch poniendo minecraft:charcoal sobre minecraft:stick en la mesa arcana con forma, gastando 15 de vis
    event.recipes.thaumaturge.arcane_workbench_shaped(
        '4x minecraft:torch',
        [' C', ' S'],
        { C: 'minecraft:charcoal', S: 'minecraft:stick' },
        15
    ).crystals(['2x ignis']).research('thaumaturge:first_steps')

    //da minecraft:lantern mezclando minecraft:torch y minecraft:iron_nugget en cualquier disposicion en la mesa arcana sin forma, gastando 20 de vis
    event.recipes.thaumaturge.arcane_workbench_shapeless(
        'minecraft:lantern',
        ['minecraft:torch', 'minecraft:iron_nugget'],
        20
    ).crystals(['ignis', 'ordo']).group('lanterns')

    //da 3x minecraft:gunpowder disolviendo minecraft:coal en el crisol con 5x ignis y 5x perditio
    event.recipes.thaumaturge.crucible(
        '3x minecraft:gunpowder',
        'minecraft:coal',
        ['5x ignis', '5x perditio']
    ).research('thaumaturge:alumentum')

    //da kubejs:luminous_blade infundiendo minecraft:golden_sword (centro del altar) rodeada de glowstone y polvo de glowstone en los pedestales con 50x kubejs:lux y 30x ignis
    event.recipes.thaumaturge.infusion(
        'kubejs:luminous_blade',
        'minecraft:golden_sword',
        ['minecraft:glowstone', 'minecraft:glowstone', 'minecraft:glowstone_dust'],
        ['50x kubejs:lux', '30x ignis']
    ).instability(3).research('kubejs:luminous_blades')

    //da minecraft:iron_pickaxe irrompible infundiendo con minecraft:redstone_block y minecraft:diamond, 20x fabrico y 20x terra (el catalizador se queda y solo recibe el parche)
    event.recipes.thaumaturge.infusion(
        'minecraft:iron_pickaxe',
        ['minecraft:redstone_block', 'minecraft:diamond'],
        ['20x fabrico', '20x terra']
    ).catalystPatch({ 'minecraft:unbreakable': {} })

    //da el encantamiento arcing infundiendo con minecraft:enchanted_book y #c:storage_blocks/redstone, 40x potentia y 60x aer (la espada de madera es solo el ejemplo que se muestra)
    event.recipes.thaumaturge.infusion_enchantment(
        'arcing',
        ['minecraft:enchanted_book', '#c:storage_blocks/redstone'],
        ['40x potentia', '60x aer'],
        'minecraft:wooden_sword'
    ).research('thaumaturge:infusion_enchantment')

    //da una minecraft:iron_chestplate mejorada aplicando thaumaturge:salis_mundus y ambar, 40x praemunio y 20x vitreus, gastando una ambar (#c:gems/amber) por nivel
    event.recipes.thaumaturge.runic_augment(
        ['thaumaturge:salis_mundus', '#c:gems/amber'],
        '#c:gems/amber',
        ['40x praemunio', '20x vitreus'],
        'minecraft:iron_chestplate'
    )

    //da thaumaturge:crucible aplicando thaumaturge:salis_mundus con clic derecho sobre minecraft:cauldron (tras investigar alquimia)
    event.recipes.thaumaturge.dust_trigger_simple('thaumaturge:crucible', 'minecraft:cauldron').research('thaumaturge:unlock_alchemy@0')

    //da thaumaturge:thaumonomicon aplicando thaumaturge:salis_mundus con clic derecho sobre cualquier estanteria (c:bookshelves)
    event.recipes.thaumaturge.dust_trigger_tag('thaumaturge:thaumonomicon', 'c:bookshelves')

    //da minecraft:beacon (solo el objeto que se muestra y cuenta como fabricado) aplicando thaumaturge:salis_mundus sobre el plano kubejs:lux_altar construido
    event.recipes.thaumaturge.dust_trigger_multiblock('minecraft:beacon', 'kubejs:lux_altar')

    //receta normal de mesa de crafteo: thaumaturge:wand con remate kubejs:emerald y vara thaumaturge:wood
    event.shaped(
        'thaumaturge:wand[thaumaturge:wand_parts={cap:"kubejs:emerald",rod:"thaumaturge:wood"}]',
        [' C', ' S', 'C '],
        { C: 'minecraft:emerald', S: 'minecraft:stick' }
    )
})
```

Para que el ejemplo del plano y de la infusión funcione hace falta registrar el objeto `kubejs:luminous_blade` en `startup_scripts`:

```js
StartupEvents.registry('item', event => {
    //hace que se cree el objeto kubejs:luminous_blade
    event.create('luminous_blade')
})
```

Notas de uso en el juego:

- **Blueprints:** se construye la estructura y se hace clic derecho con Salis Mundus (`thaumaturge:salis_mundus`) sobre cualquier bloque de ella. La primera `layer()` es la de ARRIBA y la última la de ABAJO.
- Con la receta `dust_trigger_multiblock` de los ejemplos, activar el plano gasta 1 polvo y, a los 2,5 segundos, cada glowstone pasa a linterna marina (la piedra imán se queda).
- Las recetas con `.research(...)` solo aparecen y se pueden usar cuando el jugador tiene esa investigación.
- Los avisos `arcane_key_*` al cargar son de Thaumaturge y son inofensivos (ver las preguntas frecuentes).

---

### 4.3. Mystical Agriculture

Módulo `compat.kubejs.modules.mysticalagriculture`. Mod necesario: `mysticalagriculture`.

| Qué | Dónde se escribe | Para qué |
| --- | --- | --- |
| `event.recipes.mysticalagriculture.<tipo>(...)` | `ServerEvents.recipes` | Recetas propias de Mystical Agriculture (altares, reprocesador, extractor de almas, etc.) |
| `MysticalEvents.recipes` | server | Recetas de semillas (crafteo, infusión y reprocesado) con las condiciones de mods puestas solas |

#### Recetas (`event.recipes.mysticalagriculture`)

| Tipo | Llamada | Argumentos |
| --- | --- | --- |
| `infusion` | `infusion(resultado, entrada, [ingredientes]).transferComponents(bool)` | de 1 a 8 ingredientes; `transferComponents` por defecto `false` |
| `awakening` | `awakening(resultado, entrada, [ingredientes], [esencias]).transferComponents(bool)` | de 1 a 4 ingredientes y de 1 a 4 esencias con cantidad |
| `enchanter` | `enchanter(encantamiento, [{item, count}])` | de 1 a 2 ingredientes con cantidad |
| `reprocessor` | `reprocessor(resultado, entrada)` | |
| `soul_extraction` | `soul_extraction({type, souls}, entrada)` | tipo de alma y cantidad decimal |
| `farmland_till` | `farmland_till(resultado, [ingredientes], grupo)` | de 1 a 9 ingredientes; el grupo es opcional |
| `soulium_spawner` | `soulium_spawner({item, count}, [{entity, weight}])` | objeto que gasta y lista de entidades con peso |
| `soul_jar_empty` | `soul_jar_empty()` | sin argumentos |

| Receta | Entra | Sale | Dónde |
| --- | --- | --- | --- |
| `infusion` | `minecraft:iron_block` + 4x `minecraft:diamond` y 4x `mysticalagriculture:diamond_essence` alternados | `minecraft:diamond_block` | Altar de Infusión |
| `infusion` sin componentes | `minecraft:gold_ingot` + 4x `minecraft:netherite_scrap` | `minecraft:netherite_ingot` | Altar de Infusión |
| `awakening` | `mysticalagriculture:supremium_block` + 4x `cognizant_dust` + 10x esencia de aire, tierra, agua y fuego | `mysticalagriculture:awakened_supremium_block` | Altar del Despertar |
| `enchanter` | 16x `minecraft:redstone` + 64x `mysticalagriculture:experience_essence` | Encantamiento `minecraft:efficiency` | Encantador de Mystical |
| `reprocessor` | `mysticalagriculture:iron_seeds` | 2x `mysticalagriculture:iron_essence` | Reprocesador de Semillas |
| `soul_extraction` | `minecraft:blaze_rod` | 0.5 de alma `mysticalagriculture:blaze` | Extractor de Almas |
| `farmland_till` | Cualquier azada (`mysticalagriculture:all_hoes`) + `minecraft:dirt` | `minecraft:farmland` | Mesa de crafteo |
| `soulium_spawner` | 12x `mysticalagriculture:cow_essence` | Hace aparecer una `minecraft:cow` (peso 1) | Spawner de Soulium |
| `soul_jar_empty` | Soul Jar lleno | Soul Jar vacío | Mesa de crafteo (receta especial) |

```js
ServerEvents.recipes(event => {
    //da minecraft:diamond_block infundiendo minecraft:iron_block con 4 minecraft:diamond y 4 mysticalagriculture:diamond_essence alternados en el Altar de Infusion
    event.recipes.mysticalagriculture.infusion('minecraft:diamond_block', 'minecraft:iron_block', [
        'minecraft:diamond', 'mysticalagriculture:diamond_essence',
        'minecraft:diamond', 'mysticalagriculture:diamond_essence',
        'minecraft:diamond', 'mysticalagriculture:diamond_essence',
        'minecraft:diamond', 'mysticalagriculture:diamond_essence'
    ])

    //da minecraft:netherite_ingot infundiendo minecraft:gold_ingot con 4 minecraft:netherite_scrap en el Altar de Infusion, sin copiar componentes del item
    event.recipes.mysticalagriculture.infusion('minecraft:netherite_ingot', 'minecraft:gold_ingot', [
        'minecraft:netherite_scrap', 'minecraft:netherite_scrap',
        'minecraft:netherite_scrap', 'minecraft:netherite_scrap'
    ]).transferComponents(false)

    //da mysticalagriculture:awakened_supremium_block despertando supremium_block con 4 cognizant_dust y 10 de cada esencia (aire, tierra, agua, fuego) en el Altar del Despertar
    event.recipes.mysticalagriculture.awakening(
        'mysticalagriculture:awakened_supremium_block',
        'mysticalagriculture:supremium_block',
        ['mysticalagriculture:cognizant_dust', 'mysticalagriculture:cognizant_dust', 'mysticalagriculture:cognizant_dust', 'mysticalagriculture:cognizant_dust'],
        ['10x mysticalagriculture:air_essence', '10x mysticalagriculture:earth_essence', '10x mysticalagriculture:water_essence', '10x mysticalagriculture:fire_essence']
    )

    //da el encantamiento minecraft:efficiency consumiendo 16 minecraft:redstone y 64 mysticalagriculture:experience_essence en el Encantador de Mystical
    event.recipes.mysticalagriculture.enchanter('minecraft:efficiency', [
        { item: 'minecraft:redstone', count: 16 },
        { item: 'mysticalagriculture:experience_essence', count: 64 }
    ])

    //da 2x mysticalagriculture:iron_essence reprocesando mysticalagriculture:iron_seeds en el Reprocesador de Semillas
    event.recipes.mysticalagriculture.reprocessor('2x mysticalagriculture:iron_essence', 'mysticalagriculture:iron_seeds')

    //da 0.5 de alma de tipo mysticalagriculture:blaze extrayendola de minecraft:blaze_rod en el Extractor de Almas
    event.recipes.mysticalagriculture.soul_extraction({ type: 'mysticalagriculture:blaze', souls: 0.5 }, 'minecraft:blaze_rod')

    //da minecraft:farmland combinando cualquier azada (mysticalagriculture:all_hoes) con minecraft:dirt en la mesa de crafteo
    event.recipes.mysticalagriculture.farmland_till('minecraft:farmland', [{ type: 'mysticalagriculture:all_hoes' }, 'minecraft:dirt'])

    //hace aparecer una minecraft:cow (peso 1) gastando 12 mysticalagriculture:cow_essence en el Spawner de Soulium
    event.recipes.mysticalagriculture.soulium_spawner({ item: 'mysticalagriculture:cow_essence', count: 12 }, [
        { entity: 'minecraft:cow', weight: 1 }
    ])

    //convierte un Soul Jar lleno en uno vacio (receta especial sin argumentos)
    event.recipes.mysticalagriculture.soul_jar_empty()
})
```

#### Recetas de semillas (`MysticalEvents.recipes`)

| Método | Argumentos | Qué hace |
| --- | --- | --- |
| `event.seed(semillas, baseSemilla, esencia, material)` | `semillas` admite `'Nx namespace:item'` | Crea la receta de crafteo **y** la de infusión de las semillas |
| `event.seedCrafting(semillas, baseSemilla, esencia, material)` | | Solo crafteo, en mesa con forma `MEM / ESE / MEM` (`M` material, `E` esencia, `S` base de semilla) |
| `event.seedInfusion(semillas, baseSemilla, esencia, material)` | | Solo infusión: la base en el centro del Altar de Infusión y 4 pares alternados de material y esencia |
| `event.seedReprocessing(semillas, esencia)` | `esencia` admite `'Nx namespace:item'` | Reprocesador de Semillas: de semillas a esencia |

Las recetas se crean solas con las condiciones `neoforge:mod_loaded` necesarias: se añade una por cada mod de los objetos que no sea `minecraft`, `neoforge`, `c` ni `mysticalagriculture`, y para las semillas de un cultivo del catálogo de UMC (apartado 5) también los mods que ese cultivo exige. Los ids que se generan son `umc:mystical/<seed_crafting|seed_infusion|seed_reprocessing>/<namespace>/<objeto>`.

| Receta | Entra | Sale | Dónde |
| --- | --- | --- | --- |
| `seed` | `prosperity_seed_base` + `inferium_essence` + `minecraft:iron_ingot` | `mysticalagriculture:thaumaturge_thaumium_seeds` | Mesa de crafteo y Altar de Infusión |
| `seedCrafting` | `prosperity_seed_base` + `inferium_essence` + `#c:ingots/copper` | `mysticalagriculture:copper_seeds` | Mesa de crafteo |
| `seedInfusion` | `prosperity_seed_base` + 4 pares de `#c:ingots/copper` e `inferium_essence` | `mysticalagriculture:copper_seeds` | Altar de Infusión |
| `seedReprocessing` | `mysticalagriculture:copper_seeds` | 2x `mysticalagriculture:copper_essence` | Reprocesador de Semillas |

```js
MysticalEvents.recipes(event => {
    //da mysticalagriculture:thaumaturge_thaumium_seeds (por crafteo e infusion) mezclando prosperity_seed_base con inferium_essence y minecraft:iron_ingot; lleva mod_loaded thaumaturge si el catalogo lo exige
    event.seed(
        'mysticalagriculture:thaumaturge_thaumium_seeds',
        'mysticalagriculture:prosperity_seed_base',
        'mysticalagriculture:inferium_essence',
        'minecraft:iron_ingot'
    )

    //da mysticalagriculture:copper_seeds en la mesa de crafteo (forma MEM/ESE/MEM) con prosperity_seed_base, inferium_essence y lingotes de cobre #c:ingots/copper
    event.seedCrafting(
        'mysticalagriculture:copper_seeds',
        'mysticalagriculture:prosperity_seed_base',
        'mysticalagriculture:inferium_essence',
        '#c:ingots/copper'
    )

    //da mysticalagriculture:copper_seeds infundiendo prosperity_seed_base con 4 pares alternados de #c:ingots/copper e inferium_essence en el Altar de Infusion
    event.seedInfusion(
        'mysticalagriculture:copper_seeds',
        'mysticalagriculture:prosperity_seed_base',
        'mysticalagriculture:inferium_essence',
        '#c:ingots/copper'
    )

    //da 2x mysticalagriculture:copper_essence reprocesando mysticalagriculture:copper_seeds en el Reprocesador de Semillas
    event.seedReprocessing('mysticalagriculture:copper_seeds', '2x mysticalagriculture:copper_essence')
})
```

---

### 4.4. Applied Energistics 2 (AE2)

Módulo `compat.kubejs.modules.ae2`. Mod necesario: `ae2`. Todas las recetas van en `ServerEvents.recipes` con `event.recipes.ae2.<tipo>(...)`.

| Tipo | Llamada | Argumentos |
| --- | --- | --- |
| `inscriber` | `inscriber(resultado, centro, arriba, abajo)` | `arriba` y `abajo` son opcionales (`inscriber(resultado, centro)` y `inscriber(resultado, centro, arriba)` también valen). Se termina con `.press()` o `.inscribe()` |
| `transform` | `transform(resultado, [ingredientes])` | Por defecto, tirados al agua. Opciones: `.explosion()`, `.fluid(etiquetaDeFluido)` (con o sin `#`), `.water()`, `.lava()` |
| `charger` | `charger(resultado, ingrediente)` | |
| `matter_cannon` | `matter_cannon(munición, peso)` | `munición` es un ítem o etiqueta; `peso` es decimal (peso atómico) |
| `entropy` | `entropy(modo)` | `modo`: `'heat'` o `'cool'`. Se encadena `.inputBlock(bloque)` o `.inputFluid(fluido)`, y `.outputBlock(bloque)`, `.outputFluid(fluido)` o `.drop(objeto)` |
| `storage_cell_upgrade` | `storage_cell_upgrade(celdaQueEntra, componenteQueEntra, celdaQueSale, componenteQueSale)` | Cuatro ids de ítem |
| `storage_cell_disassembly` | `storage_cell_disassembly(celda, [objetosQueSeDevuelven])` | |
| `crafting_unit_transform` | `crafting_unit_transform(objetoQueSeUsa, bloqueResultante)` | |
| `quartz_cutting` | `quartz_cutting(resultado, [ingredientes])` | de 1 a 9 ingredientes |

Además se registran sin argumentos `add_item_upgrade`, `remove_item_upgrade` y `facade`, para que KubeJS no dé error con las recetas que AE2 ya trae de esos tipos.

| Receta | Entra | Sale | Dónde |
| --- | --- | --- | --- |
| `inscriber` + `.press()` | `minecraft:redstone` en el centro, `ae2:printed_calculation_processor` arriba y `ae2:printed_silicon` abajo | `ae2:calculation_processor` | Inscriptor |
| `inscriber` + `.inscribe()` | `minecraft:iron_block` (se consume) con `ae2:logic_processor_press` arriba (se conserva) | `ae2:logic_processor_press` | Inscriptor |
| `transform` (agua) | `ae2:charged_certus_quartz_crystal` + `ae2:flawed_budding_quartz` | `ae2:flawless_budding_quartz` | Tirados al agua unos 3 segundos sin que se alejen |
| `transform` + `.explosion()` | `ae2:charged_certus_quartz_crystal` + `minecraft:coal_block` | 2x `minecraft:diamond` | Tirados al suelo con una explosión de TNT al lado (no los destruye) |
| `transform` + `.fluid('minecraft:lava')` | `minecraft:netherite_ingot` + `minecraft:netherite_scrap` | `minecraft:netherite_block` | Tirados a la lava unos 3 segundos (solo objetos a prueba de fuego) |
| `charger` | `minecraft:book` | `ae2:guide` | Cargador de AE2 con energía |
| `matter_cannon` | `#c:nuggets/iron` | Munición del cañón de materia con peso 55.845 (a más peso, más daño) | Cañón de materia |
| `entropy('cool')` | `minecraft:stone` | `minecraft:cobblestone` | Manipulador de entropía (enfriar) |
| `entropy('heat')` | `minecraft:ice` | `minecraft:water` | Manipulador de entropía (calentar) |
| `entropy('cool')` + `.inputFluid().drop()` | `minecraft:flowing_water` | `minecraft:snowball` (cae como objeto) | Manipulador de entropía (enfriar) |
| `storage_cell_upgrade` | `ae2:item_storage_cell_4k` + `ae2:cell_component_64k` | `ae2:item_storage_cell_64k` y devuelve `ae2:cell_component_4k` | Mesa de crafteo |
| `storage_cell_disassembly` | `ae2:item_storage_cell_4k` vacía | `ae2:item_cell_housing` + `ae2:cell_component_4k` | Usada en la mano agachado |
| `crafting_unit_transform` | `ae2:cell_component_16k` | `ae2:16k_crafting_storage` | Usada sobre una unidad de crafteo |
| `quartz_cutting` | `#ae2:knife` (se desgasta, no se consume) + `#ae2:metal_ingots` | 4x `ae2:cable_anchor` | Cuadrícula de crafteo |

```js
ServerEvents.recipes(event => {
    //da ae2:calculation_processor prensando minecraft:redstone con ae2:printed_calculation_processor arriba y ae2:printed_silicon abajo en el inscriptor
    event.recipes.ae2.inscriber('ae2:calculation_processor', 'minecraft:redstone', 'ae2:printed_calculation_processor', 'ae2:printed_silicon')
        .press()
        .id('umc_ejemplos:ae2/inscriber_procesador')

    //da ae2:logic_processor_press inscribiendo minecraft:iron_block (se consume) con ae2:logic_processor_press arriba (se conserva) en el inscriptor
    event.recipes.ae2.inscriber('ae2:logic_processor_press', 'minecraft:iron_block', 'ae2:logic_processor_press')
        .inscribe()
        .id('umc_ejemplos:ae2/inscriber_molde')

    //da ae2:flawless_budding_quartz mezclando ae2:charged_certus_quartz_crystal y ae2:flawed_budding_quartz tirados al agua durante unos 3 segundos sin que se alejen
    event.recipes.ae2.transform('ae2:flawless_budding_quartz', ['ae2:charged_certus_quartz_crystal', 'ae2:flawed_budding_quartz'])
        .id('umc_ejemplos:ae2/transform_agua')

    //da 2x minecraft:diamond mezclando ae2:charged_certus_quartz_crystal y minecraft:coal_block tirados al suelo con una explosion de TNT al lado (no los destruye)
    event.recipes.ae2.transform('2x minecraft:diamond', ['ae2:charged_certus_quartz_crystal', 'minecraft:coal_block'])
        .explosion()
        .id('umc_ejemplos:ae2/transform_explosion')

    //da minecraft:netherite_block mezclando minecraft:netherite_ingot y minecraft:netherite_scrap tirados a la lava durante unos 3 segundos (solo objetos a prueba de fuego)
    event.recipes.ae2.transform('minecraft:netherite_block', ['minecraft:netherite_ingot', 'minecraft:netherite_scrap'])
        .fluid('minecraft:lava')
        .id('umc_ejemplos:ae2/transform_lava')

    //da ae2:guide cargando minecraft:book en el cargador de AE2 con energia
    event.recipes.ae2.charger('ae2:guide', 'minecraft:book')
        .id('umc_ejemplos:ae2/charger_guia')

    //hace que las pepitas de hierro (#c:nuggets/iron) sirvan de municion del canon de materia con peso atomico 55.845 (a mas peso, mas dano)
    event.recipes.ae2.matter_cannon('#c:nuggets/iron', 55.845)
        .id('umc_ejemplos:ae2/municion_hierro')

    //convierte minecraft:stone en minecraft:cobblestone enfriando con el manipulador de entropia
    event.recipes.ae2.entropy('cool')
        .inputBlock('minecraft:stone')
        .outputBlock('minecraft:cobblestone')
        .id('umc_ejemplos:ae2/entropia_piedra')

    //convierte minecraft:ice en minecraft:water calentando con el manipulador de entropia
    event.recipes.ae2.entropy('heat')
        .inputBlock('minecraft:ice')
        .outputFluid('minecraft:water')
        .id('umc_ejemplos:ae2/entropia_hielo')

    //convierte minecraft:flowing_water en minecraft:snowball (cae como objeto) enfriando con el manipulador de entropia
    event.recipes.ae2.entropy('cool')
        .inputFluid('minecraft:flowing_water')
        .drop('minecraft:snowball')
        .id('umc_ejemplos:ae2/entropia_nieve')

    //da ae2:item_storage_cell_64k y devuelve ae2:cell_component_4k juntando ae2:item_storage_cell_4k con ae2:cell_component_64k en una mesa de crafteo
    event.recipes.ae2.storage_cell_upgrade('ae2:item_storage_cell_4k', 'ae2:cell_component_64k', 'ae2:item_storage_cell_64k', 'ae2:cell_component_4k')
        .id('umc_ejemplos:ae2/mejora_celda')

    //da ae2:item_cell_housing y ae2:cell_component_4k desmontando una ae2:item_storage_cell_4k vacia usada en la mano agachado
    event.recipes.ae2.storage_cell_disassembly('ae2:item_storage_cell_4k', ['ae2:item_cell_housing', 'ae2:cell_component_4k'])
        .id('umc_ejemplos:ae2/desmontar_celda')

    //da ae2:16k_crafting_storage usando ae2:cell_component_16k sobre una unidad de crafteo
    event.recipes.ae2.crafting_unit_transform('ae2:cell_component_16k', 'ae2:16k_crafting_storage')
        .id('umc_ejemplos:ae2/unidad_crafteo')

    //da 4x ae2:cable_anchor cortando con el cuchillo de AE2 (#ae2:knife, se desgasta pero no se consume) y un lingote de metal (#ae2:metal_ingots) en la cuadricula de crafteo
    event.recipes.ae2.quartz_cutting('4x ae2:cable_anchor', ['#ae2:knife', '#ae2:metal_ingots'])
        .id('umc_ejemplos:ae2/corte_cuarzo')
})
```

Notas de uso en el juego:

- **Transform en lava:** necesita objetos **a prueba de fuego** (netherita) y unos **3 segundos** de espera dentro de la lava; los que no lo son se queman antes. Al cargar el script, UMC avisa en el log de KubeJS si usas `.fluid('minecraft:lava')` o `.lava()`.
- **Transform con explosión:** los objetos tienen que estar tirados al suelo, no se destruyen con la explosión.
- **Entropy:** `'cool'` y `'heat'` son los dos modos del manipulador de entropía (enfriar y calentar).
- **`storage_cell_disassembly`:** hay que usar la celda vacía en la mano agachado.

---

### 4.5. Advanced AE

Módulo `compat.kubejs.modules.advanced_ae` (con guion bajo). Mod necesario: `advanced_ae`.

| Tipo | Llamada | Argumentos |
| --- | --- | --- |
| `reaction` | `event.recipes.advanced_ae.reaction(resultado, [ingredientes], fluido, energía)` | `resultado`: ítem con cantidad (`'64x ae2:...'`); `ingredientes`: lista de ítems con cantidad; `fluido`: `Fluid.of(id, mB)`; `energía`: entero en FE. Alias de los métodos: `.inputItems()`/`.items()`, `.inputFluid()`/`.fluid()`, `.inputEnergy()`/`.energy()` |

| Entra | Sale | Dónde |
| --- | --- | --- |
| 64x `ae2:certus_quartz_crystal` + 1000 mB de agua + 1 300 000 FE | 64x `ae2:charged_certus_quartz_crystal` | Reaction Chamber |
| 4x `minecraft:copper_ingot` + 4x `advanced_ae:shattered_singularity` + 4x `ae2:singularity` + 1000 mB de `advanced_ae:quantum_infusion_source` + 200 000 FE | 1x `advanced_ae:quantum_alloy` | Reaction Chamber |
| `advanced_ae:quantum_infused_dust` + 4000 mB de agua + 20 000 FE | 1000x `advanced_ae:quantum_infusion_source` | Reaction Chamber |

```js
ServerEvents.recipes(event => {
    //da 64x ae2:charged_certus_quartz_crystal mezclando 64x ae2:certus_quartz_crystal con 1000 mB de agua y 1300000 de energia (FE), en la Reaction Chamber
    event.recipes.advanced_ae.reaction(
        '64x ae2:charged_certus_quartz_crystal',
        ['64x ae2:certus_quartz_crystal'],
        Fluid.of('minecraft:water', 1000),
        1300000
    ).id('umc_ejemplos:advancedae/cuarzo_cargado')

    //da 1x advanced_ae:quantum_alloy mezclando 4x cobre, 4x shattered_singularity y 4x singularity con 1000 mB de quantum_infusion y 200000 de energia (FE), en la Reaction Chamber
    event.recipes.advanced_ae.reaction(
        'advanced_ae:quantum_alloy',
        ['4x minecraft:copper_ingot', '4x advanced_ae:shattered_singularity', '4x ae2:singularity'],
        Fluid.of('advanced_ae:quantum_infusion_source', 1000),
        200000
    ).id('umc_ejemplos:advancedae/aleacion_cuantica')

    //da 1000x advanced_ae:quantum_infusion_source mezclando advanced_ae:quantum_infused_dust con 4000 mB de agua y 20000 de energia (FE), en la Reaction Chamber
    event.recipes.advanced_ae.reaction(
        '1000x advanced_ae:quantum_infusion_source',
        ['advanced_ae:quantum_infused_dust'],
        Fluid.of('minecraft:water', 4000),
        20000
    ).id('umc_ejemplos:advancedae/fluido_cuantico')
})
```

---

### 4.6. Extended AE

Módulo `compat.kubejs.modules.extendedae`. Mod necesario: `extendedae`.

| Tipo | Llamada | Argumentos |
| --- | --- | --- |
| `crystal_assembler` | `crystal_assembler(resultado, [ingredientes], fluido)` | `ingredientes`: ítems o etiquetas con cantidad (`'4x #c:dusts/entro'`); `fluido`: `Fluid.of(id, mB)`, **opcional** |
| `circuit_cutter` | `circuit_cutter(resultado, ingrediente)` | `ingrediente` con cantidad |
| `crystal_fixer` | `crystal_fixer(resultado, entrada, combustible, probabilidad)` | `probabilidad`: entero de 1 a 10000 (8000 es el 80 %) |

| Receta | Entra | Sale | Dónde |
| --- | --- | --- | --- |
| `crystal_assembler` con fluido | 4x `#c:dusts/entro` + 4x `#c:ingots/gold` + 4x `minecraft:lapis_lazuli` + 100 mB de agua | 4x `extendedae:entro_ingot` | Crystal Assembler |
| `crystal_assembler` sin fluido | `extendedae:machine_frame` + 8x `ae2:engineering_processor` + `minecraft:stonecutter` | `extendedae:circuit_cutter` | Crystal Assembler |
| `circuit_cutter` | 1x `ae2:fluix_block` | 9x `ae2:printed_silicon` | Circuit Slicer |
| `crystal_fixer` | `ae2:chipped_budding_quartz` con `ae2:charged_certus_quartz_crystal` de combustible, 80 % de probabilidad | `ae2:flawed_budding_quartz` | Crystal Fixer |

```js
ServerEvents.recipes(event => {
    //da 4x extendedae:entro_ingot mezclando 4x polvo de entro, 4x lingote de oro y 4x lapislazuli con 100 mB de agua, en el Crystal Assembler
    event.recipes.extendedae.crystal_assembler(
        '4x extendedae:entro_ingot',
        ['4x #c:dusts/entro', '4x #c:ingots/gold', '4x minecraft:lapis_lazuli'],
        Fluid.of('minecraft:water', 100)
    ).id('umc_ejemplos:extendedae/lingote_entro')

    //da extendedae:circuit_cutter mezclando extendedae:machine_frame, 8x ae2:engineering_processor y minecraft:stonecutter sin fluido, en el Crystal Assembler
    event.recipes.extendedae.crystal_assembler(
        'extendedae:circuit_cutter',
        ['extendedae:machine_frame', '8x ae2:engineering_processor', 'minecraft:stonecutter']
    ).id('umc_ejemplos:extendedae/cortador_circuitos')

    //da 9x ae2:printed_silicon cortando 1x ae2:fluix_block, en el Circuit Slicer
    event.recipes.extendedae.circuit_cutter('9x ae2:printed_silicon', 'ae2:fluix_block')
        .id('umc_ejemplos:extendedae/corte_acumulacion')

    //convierte ae2:chipped_budding_quartz en ae2:flawed_budding_quartz gastando ae2:charged_certus_quartz_crystal como combustible, con 8000 de 10000 (80%) de probabilidad, en el Crystal Fixer
    event.recipes.extendedae.crystal_fixer(
        'ae2:flawed_budding_quartz',
        'ae2:chipped_budding_quartz',
        'ae2:charged_certus_quartz_crystal',
        8000
    ).id('umc_ejemplos:extendedae/arreglo_cristal')
})
```

---

### 4.7. Create

Módulo `compat.kubejs.modules.create`. Mod necesario: `create`.

| Tipo | Llamada | Argumentos |
| --- | --- | --- |
| `item_application` (alias `itemApplication`) | `item_application(resultado, [bloqueEnElMundo, objetoEnLaMano])` | Exactamente 2 ingredientes: primero el bloque (o etiqueta) sobre el que se hace clic, después el objeto de la mano. Opción `.keepHeldItem()`: no gasta el objeto de la mano |
| `conversion` (alias `mysteriousConversion`) | `conversion(resultado, entrada)` | Un resultado y una entrada. Opciones `.haunting()` y `.entity(entidad)` |
| `haunting` | `haunting(resultado, entrada)` | Hechizo con ventilador. Hasta 12 resultados |

`conversion` y `haunting` los registra UMC **solo si el addon KubeJS Create (`kubejs_create`) no está instalado**; si lo está, las aporta ese addon. `item_application` y los alias de UMC se registran siempre.

| Receta | Entra | Sale | En el juego |
| --- | --- | --- | --- |
| `item_application` | `#c:stripped_logs` + `create:andesite_alloy` en la mano | `create:andesite_casing` | Aleación en la mano y clic derecho sobre el tronco sin corteza |
| `item_application` + `.keepHeldItem()` | `#c:stripped_logs` + `create:brass_ingot` en la mano | `create:brass_casing` | Clic derecho con el lingote; el lingote no se gasta |
| `itemApplication` (alias) | `#c:stripped_logs` + `minecraft:copper_ingot` | `create:copper_casing` | Clic derecho con el lingote |
| `conversion` + `.haunting()` | `minecraft:emerald` | `minecraft:amethyst_shard` | Se muestra en JEI y funciona en el juego con un ventilador (Encased Fan) tras fuego de almas |
| `mysteriousConversion` + `.haunting()` | `minecraft:grass_block` | `minecraft:glowstone` | Tirar el bloque al suelo y pasar el aire de un ventilador a través de fuego de almas; no se hace clic en nada |
| `haunting` | `minecraft:emerald` | `minecraft:amethyst_shard` | Ventilador (Encased Fan) detrás de fuego de almas |
| `conversion` + `.entity('minecraft:pig')` | `minecraft:coal` | `minecraft:diamond` | Clic derecho con el carbón en la mano sobre un cerdo |

Qué hace cada opción de `conversion`:

| Cadena | Qué hace |
| --- | --- |
| Sin nada | La conversión **solo se muestra en JEI** (categoría "Conversión misteriosa" de Create). Create no ejecuta conversiones genéricas, así que no pasa nada en el juego |
| `.haunting()` | Además crea una receta `create:haunting` con el mismo contenido (id con el sufijo `_haunting`). Ahora funciona en el juego con el ventilador tras fuego de almas |
| `.entity('minecraft:pig')` | Funciona como el blaze burner (objeto + entidad): clic derecho con la entrada en la mano (mano principal) sobre esa entidad, gasta 1 objeto (en creativo no) y da el resultado. JEI muestra el texto "Clic derecho sobre: Cerdo" |

```js
ServerEvents.recipes(event => {
    //da create:andesite_casing aplicando create:andesite_alloy sobre un tronco sin corteza; en el juego: aleacion en la mano y clic derecho sobre el tronco
    event.recipes.create.item_application('create:andesite_casing', ['#c:stripped_logs', 'create:andesite_alloy'])
        .id('umc_ejemplos:create/casing_andamio')

    //da create:brass_casing aplicando create:brass_ingot sobre un tronco sin corteza sin gastar el lingote (keepHeldItem); en el juego: clic derecho con el lingote
    event.recipes.create.item_application('create:brass_casing', ['#c:stripped_logs', 'create:brass_ingot'])
        .keepHeldItem()
        .id('umc_ejemplos:create/casing_laton_conserva')

    //da create:copper_casing aplicando minecraft:copper_ingot sobre un tronco sin corteza (alias camelCase); en el juego: clic derecho con el lingote
    event.recipes.itemApplication('create:copper_casing', ['#c:stripped_logs', 'minecraft:copper_ingot'])
        .id('umc_ejemplos:create/casing_cobre_alias')

    //convierte minecraft:emerald en minecraft:amethyst_shard: sale en JEI (Conversion misteriosa) y con haunting() tambien funciona en el juego (ventilador tras fuego de almas)
    //nota: sin haunting() solo se muestra en JEI, Create no ejecuta conversiones genericas
    event.recipes.create.conversion('minecraft:amethyst_shard', 'minecraft:emerald')
        .id('umc_ejemplos:create/conversion_esmeralda_amatista')
        .haunting()

    //convierte minecraft:grass_block en minecraft:glowstone (alias camelCase de conversion): sale en JEI y con haunting() funciona en el juego
    //nota: en el juego se tira el bloque de hierba al suelo y se le pasa el aire de un Encased Fan (ventilador) a traves de fuego de almas; no se hace clic en nada
    event.recipes.mysteriousConversion('minecraft:glowstone', 'minecraft:grass_block')
        .id('umc_ejemplos:create/conversion_hierba_piedra_luminosa')
        .haunting()

    //da minecraft:amethyst_shard hechizando minecraft:emerald con un ventilador (Encased Fan) detras de fuego de almas
    event.recipes.create.haunting('minecraft:amethyst_shard', 'minecraft:emerald')
        .id('umc_ejemplos:create/haunting_esmeralda_amatista')

    //da minecraft:diamond convirtiendo minecraft:coal cuando se hace clic derecho con el carbon en la mano sobre un cerdo, como el blaze burner (item + entidad); tambien sale en JEI (Conversion misteriosa)
    event.recipes.create.conversion('minecraft:diamond', 'minecraft:coal')
        .id('umc_ejemplos:create/conversion_carbon_diamante')
        .entity('minecraft:pig')
})
```

Notas de uso en el juego:

- **La conversión misteriosa solo se muestra en JEI** salvo que se encadene `.haunting()` o `.entity('minecraft:pig')`. Sin ellas es solo informativa.
- Con `.entity(...)`, JEI muestra en esa receta el texto **"Clic derecho sobre: Cerdo"** (el nombre de la entidad que pongas). La lista de conversiones con entidad se sincroniza del servidor al cliente al unirse o recargar los datos.
- La conversión por entidad solo vale con la **mano principal**.
- Si desactivas `compat.kubejs.modules.create`, también se desactiva el clic derecho sobre entidades.

---

### 4.8. Generator Galore

Módulo `compat.kubejs.modules.generatorgalore`. Mod necesario: `generatorgalore`.

Añade el objeto global `GeneratorGalore`, que se usa dentro de `ServerEvents.generateData('after_mods', ...)`. Genera los *data maps* de combustibles de Generator Galore: `solid_fuel_map` y `fluid_fuel_map`. Se escriben con `replace = false`, así que **se suman** a los del mod en lugar de sustituirlos.

| Llamada | Argumentos | Qué hace |
| --- | --- | --- |
| `GeneratorGalore.fuels()` | | Empieza la definición. No se escribe nada hasta llamar a `.write(event)` |
| `.solid(idGenerador)` | id del generador | Elige un generador de combustible sólido |
| `.fuel(objeto o #etiqueta, ticksDeQuemado, FEPorTick, velocidadDeConsumo)` | tras `.solid` | Combustible sólido. La velocidad de consumo es opcional (1 por defecto) |
| `.fluid(idGenerador)` | id del generador | Elige un generador de combustible líquido |
| `.fuel(fluido o #etiqueta, FEPorTick, mBPorTickConsumidos)` | tras `.fluid` | Combustible líquido. El último es opcional (1 por defecto) |
| `.done()` | | Cierra la lista de ese generador para elegir otro |
| `.write(event)` | | Escribe los dos data maps |

| Generador | Combustible (entra) | Sale |
| --- | --- | --- |
| `generatorgalore:iron_generator` (sólido) | 1x `minecraft:coal` | 40 FE/t durante 1600 ticks |
| `generatorgalore:iron_generator` (sólido) | Cualquier `#minecraft:logs` | 20 FE/t durante 300 ticks |
| `generatorgalore:ender_generator` (sólido) | 1x `minecraft:ender_pearl` | 96 FE/t durante 1600 ticks |
| `generatorgalore:ender_generator` (sólido) | 1x `minecraft:ender_eye` | 80 FE/t durante 3200 ticks |
| `generatorgalore:magmatic_generator` (líquido) | Cualquier fluido de `#c:lava`, 0.4 mB/t | 40 FE/t |

```js
ServerEvents.generateData('after_mods', event => {
    GeneratorGalore.fuels()
        //el generador indicado acepta combustibles solidos
        .solid('generatorgalore:iron_generator')
        //40 FE/t durante 1600 ticks quemando 1 minecraft:coal, velocidad de consumo 1
        .fuel('minecraft:coal', 1600, 40, 1)
        //20 FE/t durante 300 ticks quemando cualquier #minecraft:logs (velocidad de consumo 1 por omision)
        .fuel('#minecraft:logs', 300, 20)
        .done()
        .solid('generatorgalore:ender_generator')
        .fuel('minecraft:ender_pearl', 1600, 96, 1)
        .fuel('minecraft:ender_eye', 3200, 80, 1)
        .done()
        //combustibles liquidos
        .fluid('generatorgalore:magmatic_generator')
        //40 FE/t consumiendo 0.4 mB/t de cualquier fluido de la tag #c:lava
        .fuel('#c:lava', 40, 0.4)
        .done()
        //escribe los dos data maps (solidos y liquidos) en el paquete de datos generado
        .write(event)
})
```

---

### 4.9. Excessive Utilities

Módulo `compat.kubejs.modules.excessive_utilities`. Mod necesario: `excessive_utilities`. Recetas con `event.recipes.excessive_utilities.<tipo>(...)` en `ServerEvents.recipes`.

#### Máquinas

| Tipo | Llamada | Argumentos |
| --- | --- | --- |
| `crusher` | `crusher(resultado, ingrediente[, resultadoSecundario, probabilidad])` | `probabilidad` de 0 a 1. Opciones `.ticks(n)` (200 por defecto) y `.fePerTick(n)` (20 por defecto) |
| `enchanter` | `enchanter(resultado, izquierda, derecha, fePorTick, ticks)` | `izquierda` y `derecha` con cantidad. Opción `.enchantingPower(poder)` (15 por defecto) |
| `qed` | `qed(resultado, [patrón], {letra: ingrediente}, ticksDeCristal)` | Patrón vertical de letras |
| `resonator` | `resonator(resultado, ingrediente, costeGP)` | `costeGP`: Grid Power |
| `world_interaction_item` | `world_interaction_item(resultado, bloqueDebajo, [bloquesAdyacentes])` | Opción `.behind(bloque)` |
| `world_interaction_fluid` | `world_interaction_fluid(fluidoResultado, bloqueDebajo, [bloquesAdyacentes])` | Opción `.behind(bloque)` |

#### Combustibles de generadores

| Tipo | Llamada | Argumentos |
| --- | --- | --- |
| `single_item_fuel` | `single_item_fuel(tipoGenerador, ingrediente, fePorTick, duraciónTicks)` | `tipoGenerador`: `ender`, `explosive`, `pink`, `nether_star`, `frosty`, `halitosis` o `death` |
| `item_and_fluid_fuel` | `item_and_fluid_fuel(tipoGenerador, ingrediente, fluido, fePorTick, duraciónTicks)` | `tipoGenerador`: `slimy` o `heated_redstone` |
| `magmatic_fuel` | `magmatic_fuel(fluido, fePorTick, duraciónTicks)` | |

#### Recetas de crafteo especiales

| Tipo | Llamada | Argumentos |
| --- | --- | --- |
| `shaped_division` | `shaped_division(resultado, [patrón], {letra: ingrediente}).inversion(modo)` | `modo`: `inverted`, `uninverted` o `either` (por defecto) según el estado del Division Sigil |
| `shaped_unstable` | `shaped_unstable(resultado, [patrón], {letra: ingrediente}).stability(modo)` | `modo`: `stable`, `unstable` o `either` (por defecto) |
| `keep_paintbrush` | `keep_paintbrush(resultado, [patrón], {letra: ingrediente})` | El Paintbrush no se consume |
| `damage_glass_cutter` | `damage_glass_cutter(resultado, [patrón], {letra: ingrediente})` | El Glass Cutter pierde durabilidad |
| `snow_globe` | `snow_globe(resultado, [patrón], {letra: ingrediente})` | Necesita un Magical Snow Globe ya activado |

| Receta | Entra | Sale | Dónde |
| --- | --- | --- | --- |
| `crusher` (simple) | `minecraft:allium` | 2x `minecraft:magenta_dye` | Crusher (200 ticks y 20 FE/t) |
| `crusher` (completo) | `minecraft:cobblestone` | `minecraft:gravel` y `minecraft:flint` con 25 % | Crusher (100 ticks, 40 FE/t) |
| `enchanter` | 1x `#c:storage_blocks/gold` + 9x `#c:gems/lapis`, 40 FE/t, 600 ticks | `excessive_utilities:block_of_enchanted_metal` | Enchanter |
| `qed` | Perla de ender (`E`) sobre 2x `excessive_utilities:ender_infused_obsidian` (`O`) en vertical, 300 ticks de cristal | `excessive_utilities:ender_marker` | QED |
| `resonator` | `minecraft:iron_bars`, 16 de Grid Power | `excessive_utilities:heating_coil` | Resonator |
| `world_interaction_item` | `minecraft:soul_soil` debajo, `minecraft:blue_ice` y `minecraft:lava` en los lados | `minecraft:basalt` | World Interaction Upgrade |
| `world_interaction_item` + `.behind` | Cualquier `#c:stones` debajo, `minecraft:water` y `minecraft:lava` en los lados y `minecraft:water` detrás | `minecraft:stone` | World Interaction Upgrade |
| `world_interaction_fluid` | `minecraft:water` debajo con dos `minecraft:water` en los lados | 1 mB de `minecraft:water` | World Interaction Upgrade |
| `single_item_fuel` | 1x `#c:bones` | 40 FE/t durante 400 ticks | Death Generator |
| `item_and_fluid_fuel` | `#c:dusts/redstone` + 250 mB de lava | 3200 FE/t durante 125 ticks | Heated Redstone Generator |
| `magmatic_fuel` | 1 mB de `minecraft:lava` | 100 FE/t durante 1 tick | Magmatic Generator |
| `shaped_division` | Pepita de hierro (`N`), `excessive_utilities:division_sigil` (`S`) y diamante (`D`), en vertical N/S/D, solo con el Division Sigil invertido | `excessive_utilities:semi_unstable_nugget` | Mesa de crafteo |
| `shaped_unstable` | 8x `#c:ingots/iron` y 1x `#c:gems/diamond` en el centro, solo en estado estable | `excessive_utilities:unstable_ingot` | Mesa de crafteo (3x3) |
| `keep_paintbrush` | 7x `#c:brick_blocks` (el patrón `III/IDI/IPI` lleva 7), `#c:dyes/black` y el Paintbrush (no se consume) | 7x `excessive_utilities:colored_bricks_black` | Mesa de crafteo (3x3) |
| `damage_glass_cutter` | `#c:ender_pearls` y el Glass Cutter (pierde durabilidad) | 8x `excessive_utilities:ender_shard` | Mesa de crafteo (vertical P/C) |
| `snow_globe` | `#c:end_stones`, `excessive_utilities:stoneburnt` y un Magical Snow Globe activado | `excessive_utilities:quantum_quarry` | Mesa de crafteo (3x3) |

```js
ServerEvents.recipes(event => {
    //da 2x minecraft:magenta_dye triturando minecraft:allium, en el Crusher (200 ticks y 20 FE/t por defecto)
    event.recipes.excessive_utilities.crusher('2x minecraft:magenta_dye', 'minecraft:allium')
        .id('umc_ejemplos:excessive_utilities/triturador_simple')

    //da minecraft:gravel triturando minecraft:cobblestone, mas minecraft:flint con 25% de probabilidad, en 100 ticks gastando 40 FE/t, en el Crusher
    event.recipes.excessive_utilities.crusher('minecraft:gravel', 'minecraft:cobblestone', 'minecraft:flint', 0.25)
        .ticks(100)
        .fePerTick(40)
        .id('umc_ejemplos:excessive_utilities/triturador_completo')

    //da excessive_utilities:block_of_enchanted_metal mezclando 1x bloque de oro (#c:storage_blocks/gold) con 9x lapislazuli (#c:gems/lapis), gastando 40 FE/t durante 600 ticks, en el Enchanter
    event.recipes.excessive_utilities.enchanter('excessive_utilities:block_of_enchanted_metal', '#c:storage_blocks/gold', '9x #c:gems/lapis', 40, 600)
        .enchantingPower(15)
        .id('umc_ejemplos:excessive_utilities/encantador')

    //da excessive_utilities:ender_marker colocando 1 perla de ender (E) sobre 2 ender_infused_obsidian (O) en vertical, con 300 ticks de cristal, en el QED
    event.recipes.excessive_utilities.qed('excessive_utilities:ender_marker', ['E', 'O', 'O'], {
        E: '#c:ender_pearls',
        O: 'excessive_utilities:ender_infused_obsidian'
    }, 300).id('umc_ejemplos:excessive_utilities/qed')

    //da excessive_utilities:heating_coil resonando minecraft:iron_bars con un coste de 16 de Grid Power, en el Resonator
    event.recipes.excessive_utilities.resonator('excessive_utilities:heating_coil', 'minecraft:iron_bars', 16)
        .id('umc_ejemplos:excessive_utilities/resonador')

    //da minecraft:basalt cuando el World Interaction Upgrade actua sobre minecraft:soul_soil con minecraft:blue_ice y minecraft:lava en los lados
    event.recipes.excessive_utilities.world_interaction_item('minecraft:basalt', 'minecraft:soul_soil', ['minecraft:blue_ice', 'minecraft:lava'])
        .id('umc_ejemplos:excessive_utilities/interaccion_objeto')

    //da minecraft:stone cuando el World Interaction Upgrade actua sobre cualquier #c:stones con minecraft:water y minecraft:lava en los lados y minecraft:water detras
    event.recipes.excessive_utilities.world_interaction_item('minecraft:stone', '#c:stones', ['minecraft:water', 'minecraft:lava'])
        .behind('minecraft:water')
        .id('umc_ejemplos:excessive_utilities/interaccion_objeto_detras')

    //da 1 mB de minecraft:water cuando el World Interaction Upgrade actua sobre minecraft:water con dos minecraft:water en los lados
    event.recipes.excessive_utilities.world_interaction_fluid(Fluid.of('minecraft:water', 1), 'minecraft:water', ['minecraft:water', 'minecraft:water'])
        .id('umc_ejemplos:excessive_utilities/interaccion_fluido')

    //da 40 FE/t durante 400 ticks quemando 1 hueso (#c:bones), en el Death Generator
    event.recipes.excessive_utilities.single_item_fuel('death', '#c:bones', 40, 400)
        .id('umc_ejemplos:excessive_utilities/combustible_muerte')

    //da 3200 FE/t durante 125 ticks quemando polvo de redstone (#c:dusts/redstone) con 250 mB de lava, en el Heated Redstone Generator
    event.recipes.excessive_utilities.item_and_fluid_fuel('heated_redstone', '#c:dusts/redstone', Fluid.of('minecraft:lava', 250), 3200, 125)
        .id('umc_ejemplos:excessive_utilities/combustible_redstone')

    //da 100 FE/t durante 1 tick consumiendo 1 mB de minecraft:lava, en el Magmatic Generator
    event.recipes.excessive_utilities.magmatic_fuel(Fluid.of('minecraft:lava', 1), 100, 1)
        .id('umc_ejemplos:excessive_utilities/combustible_magmatico')

    //da excessive_utilities:semi_unstable_nugget en la mesa de crafteo (con forma, vertical N/S/D) con pepita de hierro, Division Sigil y diamante, solo si el Division Sigil esta invertido
    event.recipes.excessive_utilities.shaped_division('excessive_utilities:semi_unstable_nugget', ['N', 'S', 'D'], {
        N: '#c:nuggets/iron',
        S: 'excessive_utilities:division_sigil',
        D: '#c:gems/diamond'
    }).inversion('inverted').id('umc_ejemplos:excessive_utilities/division')

    //da excessive_utilities:unstable_ingot en la mesa de crafteo (con forma, 3x3) con 8 lingotes de hierro y 1 diamante en el centro, solo en estado estable
    event.recipes.excessive_utilities.shaped_unstable('excessive_utilities:unstable_ingot', ['III', 'IDI', 'III'], {
        I: '#c:ingots/iron',
        D: '#c:gems/diamond'
    }).stability('stable').id('umc_ejemplos:excessive_utilities/inestable')

    //da 7x excessive_utilities:colored_bricks_black en la mesa de crafteo (con forma, 3x3) con 8 bloques de ladrillo, tinte negro y el Paintbrush, que NO se consume
    event.recipes.excessive_utilities.keep_paintbrush('7x excessive_utilities:colored_bricks_black', ['III', 'IDI', 'IPI'], {
        D: '#c:dyes/black',
        I: '#c:brick_blocks',
        P: 'excessive_utilities:paintbrush'
    }).id('umc_ejemplos:excessive_utilities/pincel')

    //da 8x excessive_utilities:ender_shard en la mesa de crafteo (con forma, vertical P/C) con una perla de ender y el Glass Cutter, que pierde durabilidad
    event.recipes.excessive_utilities.damage_glass_cutter('8x excessive_utilities:ender_shard', ['P', 'C'], {
        P: '#c:ender_pearls',
        C: 'excessive_utilities:glass_cutter'
    }).id('umc_ejemplos:excessive_utilities/cortavidrios')

    //da excessive_utilities:quantum_quarry en la mesa de crafteo (con forma, 3x3) con piedras del End, stoneburnt y un Magical Snow Globe (G) ya activado
    event.recipes.excessive_utilities.snow_globe('excessive_utilities:quantum_quarry', ['ESE', 'SGS', 'ESE'], {
        E: '#c:end_stones',
        G: 'excessive_utilities:magical_snow_globe',
        S: 'excessive_utilities:stoneburnt'
    }).id('umc_ejemplos:excessive_utilities/bola_nieve')
})
```

---

### 4.10. Functional Storage

Módulo `compat.kubejs.modules.functionalstorage`. Mod necesario: `functionalstorage`.

| Tipo | Llamada | Argumentos |
| --- | --- | --- |
| `custom_compacting` | `custom_compacting(itemInferior, itemSuperior)` | Dos ítems con cantidad: `'4x minecraft:amethyst_shard'` y `'minecraft:amethyst_block'` |
| `copy_components` | `copy_components(resultado, [patrón], {letra: ingrediente}, [componentesACopiar], índiceCasillaOrigen)` | Receta de mesa de crafteo con forma que copia componentes de una casilla al resultado |

Además se registra `framed_recipe` sin argumentos, para que KubeJS no dé error con las recetas de cajones con marco que el mod ya trae.

| Receta | Entra | Sale | Dónde |
| --- | --- | --- | --- |
| `custom_compacting` | 4x `minecraft:amethyst_shard` | 1x `minecraft:amethyst_block` (y al revés al descompactar) | Compacting Drawer |
| `custom_compacting` | 9x `minecraft:iron_nugget` | 1x `minecraft:iron_ingot` (y al revés) | Compacting Drawer |
| `copy_components` | `functionalstorage:compacting_drawer` (`D`) rodeado de `#c:nuggets/iron` (`S`), copiando el componente `functionalstorage:tile` | `functionalstorage:compacting_framed_drawer` | Mesa de crafteo. El índice 4 es la casilla central del 3x3 |

```js
ServerEvents.recipes(event => {
    //hace que el Compacting Drawer compacte 4x minecraft:amethyst_shard en 1x minecraft:amethyst_block (y lo descompacte al reves)
    event.recipes.functionalstorage.custom_compacting('4x minecraft:amethyst_shard', 'minecraft:amethyst_block')
        .id('umc_ejemplos:functionalstorage/compactar_amatista')

    //hace que el Compacting Drawer compacte 9x minecraft:iron_nugget en 1x minecraft:iron_ingot (y lo descompacte al reves)
    event.recipes.functionalstorage.custom_compacting('9x minecraft:iron_nugget', 'minecraft:iron_ingot')
        .id('umc_ejemplos:functionalstorage/compactar_hierro')

    //da 1x functionalstorage:compacting_framed_drawer en la mesa de crafteo (con forma) rodeando un Compacting Drawer (D) con pepitas de hierro (S) y copiandole al resultado el componente functionalstorage:tile (indice 4 = casilla central 3x3)
    event.recipes.functionalstorage.copy_components('functionalstorage:compacting_framed_drawer', ['S S', 'SDS', ' S '], {
        D: 'functionalstorage:compacting_drawer',
        S: '#c:nuggets/iron'
    }, ['functionalstorage:tile'], 4).id('umc_ejemplos:functionalstorage/cajon_marco_compactador')
})
```

---

## 5. Cultivos de Mystical Agriculture

Si **Mystical Agriculture** está instalado, UMC añade **54 cultivos** extra de forma nativa (sin KubeJS). Cada cultivo solo se registra cuando están cargados los mods que necesita (columna "Mods requeridos").

### Cómo funcionan los ids

| Qué | Id |
| --- | --- |
| Cultivo (crop) | `umc:<id>` (el namespace se cambia con `compat.mysticalagriculture.crop_namespace`) |
| Semillas | `mysticalagriculture:<id>_seeds` |
| Esencia | `mysticalagriculture:<id>_essence` |
| Nombre en pantalla | Clave de idioma `umc.mysticalagriculture.crop.<id>` (en inglés y español) |

Ejemplo: el cultivo `thaumaturge_thaumium` da `mysticalagriculture:thaumaturge_thaumium_seeds` y `mysticalagriculture:thaumaturge_thaumium_essence`.

Cada cultivo se puede apagar por separado con `compat.mysticalagriculture.crops.<id> = false`, y todo el sistema con `compat.mysticalagriculture.enabled = false`.

### Los 54 cultivos

- **Nivel:** el nivel de tier. De 1 a 5 son de Mystical Agriculture; el 6 es de Mystical Agradditions (por eso exige ese mod).
- **Material:** ingrediente con el que se crea la semilla. Con `#` es una etiqueta.
- **Esencia da:** lo que sale al craftear la esencia en anillo (cantidad y objeto). "sin receta" significa que no hay receta de esencia.

| Id | Nombre | Nivel | Material | Mods requeridos | Esencia da |
| --- | --- | --- | --- | --- | --- |
| `allthemodium` | Allthemodium | 6 (Agradditions) | allthecompressed:allthemodium_block_4x | allthecompressed, mysticalagradditions | 2x allthemodium:allthemodium_ingot |
| `andesite_alloy` | Aleación de andesita | 5 | create:andesite_alloy_block | create | 2x create:andesite_alloy |
| `arcane_gold_ingot` | Oro arcano | 5 | eidolon:arcane_gold_block | eidolon | 2x eidolon:arcane_gold_ingot |
| `black_quartz` | Cuarzo negro | 3 | #c:gems/black_quartz | uno de: actuallyadditions / occultism | (sin receta) |
| `blaze_gold` | Oro de blaze | 6 (Agradditions) | justdirethings:blazegold_block | justdirethings, mysticalagradditions | 2x justdirethings:blazegold_ingot |
| `cajita_feliz` | Cajita feliz | 1 | ultimatefoods:cajita_feliz | ultimatefoods | 12x ultimatefoods:cajita_feliz |
| `celestigem` | Gema celestial | 6 (Agradditions) | justdirethings:celestigem_block | justdirethings, mysticalagradditions | 2x justdirethings:celestigem |
| `chaos_shard` | Fragmento del caos | 6 (Agradditions) | draconicevolution:chaos_shard | draconicevolution, mysticalagradditions | 2x draconicevolution:chaos_shard |
| `chromium` | Cromo | 6 (Agradditions) | mysticalexpansion:etherium_ingot_block | mysticalexpansion, mysticalagradditions | 2x mysticalexpansion:etherium_ingot |
| `cognition` | Aleación cognitiva | 5 | experienceobelisk:cognitive_alloy_block | experienceobelisk | 2x experienceobelisk:cognitive_alloy |
| `crystal_matrix` | Matriz de cristal | 5 | avaritia:crystal_matrix | avaritia | 2x avaritia:crystal_matrix_ingot |
| `cuarzorosa` | Cuarzo rosa | 6 (Agradditions) | create:polished_rose_quartz | create, mysticalagradditions | 2x create:polished_rose_quartz |
| `dark_gem` | Gema oscura | 3 | evilcraft:dark_block | evilcraft | 4x evilcraft:dark_gem |
| `deorum_ingot` | Deorum | 4 | forbidden_arcanus:deorum_block | forbidden_arcanus | 3x forbidden_arcanus:deorum_ingot |
| `eclipse_alloy` | Aleación de eclipse | 6 (Agradditions) | justdirethings:eclipsealloy_block | justdirethings, mysticalagradditions | 2x justdirethings:eclipsealloy_ingot |
| `elevatium_ingot` | Elevatium | 4 | kubejs:ingot_of_elevation | kubejs | 3x kubejs:ingot_of_elevation |
| `ender_ingot` | Lingote de ender | 5 | extendedcrafting:ender_ingot_block | extendedcrafting | 2x extendedcrafting:ender_ingot |
| `entro` | Entro | 5 | allthecompressed:entro_block_1x | allthecompressed | 2x allthecompressed:entro_block_1x |
| `ferricore` | FerriCore | 6 (Agradditions) | justdirethings:ferricore_block | justdirethings, mysticalagradditions | 2x justdirethings:ferricore_ingot |
| `flux_dust` | Flux | 1 | fluxnetworks:flux_block | fluxnetworks | 12x fluxnetworks:flux_dust |
| `imperium_essence` | Esencia de imperium | 4 | mysticalagriculture:imperium_essence | (ninguno) | 3x mysticalagriculture:imperium_essence |
| `insanium_essence` | Esencia de insanium | 6 (Agradditions) | mysticalagriculture:supremium_essence | mysticalagradditions | 2x mysticalagriculture:supremium_essence |
| `kivi` | Kivi | 2 | xycraft_world:kivi | xycraft_world | 6x xycraft_world:kivi |
| `nadienite_ingot` | Nadienita | 5 | ultimatearmoury:nadienite_block | ultimatearmoury | 2x ultimatearmoury:nadienite_ingot |
| `naquadah` | Naquadah | 6 (Agradditions) | mekanism_extras:block_naquadah | mekanism_extras, mysticalagradditions | 2x mekanism_extras:ingot_naquadah |
| `pink_slime` | Slime rosa | 4 | industrialforegoing:pink_slime_ingot | industrialforegoing | 3x industrialforegoing:pink_slime_ingot |
| `predicciondematrix` | Matriz de predicción | 3 | hostilenetworks:prediction_matrix | hostilenetworks | 4x hostilenetworks:prediction_matrix |
| `prometheum` | Prometeo | 4 | oritech:prometheum_ingot | oritech | 3x oritech:prometheum_ingot |
| `prosperity_shard` | Fragmento de prosperidad | 5 | mysticalagriculture:prosperity_block | (ninguno) | 2x mysticalagriculture:prosperity_shard |
| `prudentium_essence` | Esencia de prudentium | 2 | mysticalagriculture:prudentium_essence | (ninguno) | 6x mysticalagriculture:prudentium_essence |
| `redstoneingot` | Lingote de redstone | 2 | extendedcrafting:redstone_ingot_block | extendedcrafting | 6x extendedcrafting:redstone_ingot |
| `sky_steel_ingot` | Acero celeste | 5 | allthecompressed:sky_steel_block_1x | allthecompressed | 2x megacells:sky_steel_ingot |
| `source_gem` | Gema de fuente | 2 | ars_nouveau:source_gem_block | ars_nouveau | 6x ars_nouveau:source_gem |
| `spirit_gem` | Gema espiritual | 3 | occultism:spirit_campfire | occultism | 4x occultism:spirit_campfire |
| `super_cacotas` | Súper cacotas | 5 | ultimate_utilities:super_caca | ultimate_utilities | 2x ultimate_utilities:super_caca |
| `supremium_essence_esc` | Esencia de supremium | 5 | mysticalagriculture:supremium_essence | (ninguno) | 2x mysticalagriculture:supremium_essence |
| `tertium_essence` | Esencia de tertium | 3 | mysticalagriculture:tertium_essence | (ninguno) | 4x mysticalagriculture:tertium_essence |
| `thaumaturge_alumentum` | Alumentum | 4 | thaumaturge:alumentum | thaumaturge | 3x thaumaturge:alumentum |
| `thaumaturge_amber` | Ámbar | 3 | thaumaturge:amber | thaumaturge | 4x thaumaturge:amber |
| `thaumaturge_brass` | Latón taumatúrgico | 2 | thaumaturge:ingot_brass | thaumaturge | 6x thaumaturge:ingot_brass |
| `thaumaturge_crystal_aer` | Cristal de Aer | 3 | thaumaturge:crystal_aer | thaumaturge | 4x thaumaturge:crystal_aer |
| `thaumaturge_crystal_aqua` | Cristal de Aqua | 3 | thaumaturge:crystal_aqua | thaumaturge | 4x thaumaturge:crystal_aqua |
| `thaumaturge_crystal_ignis` | Cristal de Ignis | 3 | thaumaturge:crystal_ignis | thaumaturge | 4x thaumaturge:crystal_ignis |
| `thaumaturge_crystal_ordo` | Cristal de Ordo | 3 | thaumaturge:crystal_ordo | thaumaturge | 4x thaumaturge:crystal_ordo |
| `thaumaturge_crystal_perditio` | Cristal de Perditio | 4 | thaumaturge:crystal_perditio | thaumaturge | 3x thaumaturge:crystal_perditio |
| `thaumaturge_crystal_terra` | Cristal de Terra | 3 | thaumaturge:crystal_terra | thaumaturge | 4x thaumaturge:crystal_terra |
| `thaumaturge_crystal_vitium` | Cristal de Vitium | 4 | thaumaturge:crystal_vitium | thaumaturge | 3x thaumaturge:crystal_vitium |
| `thaumaturge_quicksilver` | Azogue | 2 | thaumaturge:quicksilver | thaumaturge | 6x thaumaturge:quicksilver |
| `thaumaturge_salis_mundus` | Salis Mundus | 2 | thaumaturge:salis_mundus | thaumaturge | 6x thaumaturge:salis_mundus |
| `thaumaturge_thaumium` | Taumio | 3 | thaumaturge:ingot_thaumium | thaumaturge | 4x thaumaturge:ingot_thaumium |
| `thaumaturge_void_metal` | Metal del vacío | 4 | thaumaturge:ingot_void | thaumaturge | 3x thaumaturge:ingot_void |
| `unobtainium` | Unobtainium | 6 (Agradditions) | #c:storage_blocks/unobtainium | allthemodium, mysticalagradditions | 2x allthemodium:unobtainium_ingot |
| `vibranium` | Vibranio | 6 (Agradditions) | #c:storage_blocks/vibranium | allthemodium, mysticalagradditions | 2x allthemodium:vibranium_ingot |
| `xychorium_gem` | Gema de xychorium | 3 | #c:gems/xychorium | xycraft_world | (sin receta) |

Resumen por mods: 14 cultivos necesitan `thaumaturge`; los de nivel 6 necesitan `mysticalagradditions`; `imperium_essence`, `prosperity_shard`, `prudentium_essence`, `supremium_essence_esc` y `tertium_essence` no necesitan ningún mod aparte de Mystical Agriculture.

### Recetas de semilla

Mystical Agriculture genera las recetas de cada semilla con el material de la tabla. UMC las activa o apaga con estas claves:

| Receta | Clave | Dónde se hace |
| --- | --- | --- |
| Crafteo | `compat.mysticalagriculture.recipes.seed_crafting` | Mesa de crafteo |
| Infusión | `compat.mysticalagriculture.recipes.seed_infusion` | Altar de Infusión |
| Reprocesador | `compat.mysticalagriculture.recipes.seed_reprocessor` | Reprocesador de Semillas (de semilla a esencia) |

Cada una está activada por defecto. Para recetas de semilla distintas a las del catálogo usa `MysticalEvents.recipes` (apartado 4.3), que añade solo las condiciones `neoforge:mod_loaded` necesarias.

### Recetas de esencia

Craftear la esencia devuelve el objeto del que sale el cultivo. Es una receta con forma de anillo de 8 esencias:

```
EEE
E E
EEE
```

- `E` es `mysticalagriculture:<id>_essence`.
- La cantidad que sale es **12 dividido entre el nivel** (mínimo 1), con división entera:

| Nivel | Cantidad que sale |
| --- | --- |
| 1 | 12 |
| 2 | 6 |
| 3 | 4 |
| 4 | 3 |
| 5 | 2 |
| 6 | 2 |

- Se generan por datagen en `data/umc/recipe/mystical/essence/<id>.json`: hay **52 recetas**. Los cultivos `black_quartz` y `xychorium_gem` no tienen receta de esencia porque su material es una etiqueta (no hay un único objeto de resultado).
- Se activan con `compat.mysticalagriculture.recipes.essence_crafting`.
- Llevan condiciones `neoforge:mod_loaded` (una por cada mod que exige el cultivo, aparte de Mystical Agriculture) y la condición propia `umc:mystical_crop`.

Ejemplo real (`andesite_alloy`, nivel 5, da 2 aleaciones de andesita):

```json
{
  "neoforge:conditions": [
    { "type": "neoforge:mod_loaded", "modid": "create" },
    { "type": "umc:mystical_crop", "crop": "andesite_alloy" }
  ],
  "type": "minecraft:crafting_shaped",
  "category": "misc",
  "key": {
    "E": { "item": "mysticalagriculture:andesite_alloy_essence" }
  },
  "pattern": ["EEE", "E E", "EEE"],
  "result": { "count": 2, "id": "create:andesite_alloy" }
}
```

### Condición `umc:mystical_crop`

Condición de datos de NeoForge que añade el mod. Se puede usar en cualquier receta de un datapack.

```json
{ "type": "umc:mystical_crop", "crop": "andesite_alloy" }
```

| Campo | Tipo | Qué es |
| --- | --- | --- |
| `crop` | texto | Id del cultivo del catálogo (sin namespace), de la columna "Id" de la tabla |

Es verdadera solo si se cumplen **todas** estas cosas:

1. `compat.mysticalagriculture.enabled` está en `true`.
2. `compat.mysticalagriculture.recipes.essence_crafting` está en `true`.
3. `compat.mysticalagriculture.crops.<crop>` está en `true`.
4. Están cargados los mods que exige ese cultivo (columna "Mods requeridos").

---

## 6. Preguntas frecuentes y problemas conocidos

### Usé un evento y KubeJS dice que no existe

Cada módulo de compatibilidad solo se registra si el mod correspondiente está instalado **y** su interruptor está activo (`compat.kubejs.enabled` y `compat.kubejs.modules.<mod>`). Si falta el mod, objetos como `EnchantedEvents`, `ThaumaturgeEvents`, `GeneratorGalore` o `event.recipes.ae2` no existen. También salen errores del tipo `Serializer for type X is not found` cuando una receta apunta a un mod que no está instalado.

### El cultivo `elevatium_ingot` no encuentra su objeto

Ese cultivo usa `kubejs:ingot_of_elevation`, un ítem **del modpack** (de KubeJS), no del mod. El cultivo se registra cuando KubeJS está cargado, pero necesitas definir el objeto tú mismo en `startup_scripts`, por ejemplo:

```js
StartupEvents.registry('item', event => {
    event.create('ingot_of_elevation')
})
```

Si no lo quieres, apaga el cultivo con `compat.mysticalagriculture.crops.elevatium_ingot = false`.

### Thaumaturge avisa de `arcane_key_*` al cargar

En el log de KubeJS aparecen avisos así:

```
Failed to parse recipe 'thaumaturge:arcane_workbench/arcane_key_iron[thaumaturge:arcane_workbench_shaped]'! Falling back to vanilla: Failed to read required component 'result: item_stack' - java.lang.IllegalStateException: Item stack with stack size of 2 was larger than maximum: 1
```

También para `arcane_key_gold`. Son recetas del propio Thaumaturge, cuyo resultado tiene una cantidad mayor que el máximo de pila. KubeJS las deja pasar como recetas normales ("Falling back to vanilla"), así que **son inofensivos** y no hay que hacer nada.

### ¿En qué namespace van los ritos?

Los ritos que añade el propio mod UMC (familiares, viaje espiritual, nigromancia, purificación) están en el namespace `enchanted`. Los que creas tú con `EnchantedEvents.rites` usan el namespace que pongas (`kubejs:` si no escribes ninguno), y se guardan en `data/<namespace>/enchanted/circle_magic/rite/`. Las tizas y formas sin namespace se leen como `enchanted:`.

### La conversión de Create no hace nada en el juego

La conversión misteriosa (`conversion` / `mysteriousConversion`) **solo se muestra en JEI** salvo que le encadenes `.haunting()` (ventilador tras fuego de almas) o `.entity('minecraft:pig')` (clic derecho sobre esa entidad). Mira el apartado 4.7.

### La transformación de AE2 en lava no funciona

Necesita objetos **a prueba de fuego** (netherita) y unos **3 segundos** dentro de la lava. Los demás se queman antes.

### Un blueprint de Thaumaturge sale al revés

La primera `layer()` es la capa de **arriba** y la última la de **abajo**. Se activa con clic derecho con Salis Mundus sobre cualquier bloque de la estructura.

### Cambié la config y no pasa nada

Los interruptores de `compat.kubejs` y de `compat.mysticalagriculture` se aplican al arrancar el juego (los cultivos y los módulos de KubeJS se registran en ese momento): reinicia el juego tras cambiarlos.
