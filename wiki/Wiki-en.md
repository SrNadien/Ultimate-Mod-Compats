# Ultimate Mod Compats: Wiki

Guide for players and modpack creators. Mod `umc` (NeoForge 1.21.1), by NadienDev.

## Index

1. [What It Is and Dependencies](#1-what-it-is-and-dependencies)
2. [Built-in Content](#2-built-in-content)
3. [Configuration](#3-configuration)
4. [KubeJS Compatibility](#4-kubejs-compatibility)
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
5. [Mystical Agriculture Crops](#5-mystical-agriculture-crops)
6. [FAQ and Known Issues](#6-faq-and-known-issues)

---

## 1. What It Is and Dependencies

**Ultimate Mod Compats** (id `umc`) is an addon for **Enchanted** that does two things:

- Adds **its own content**: creatures (werewolf, vampire, hunter and new mobs), a spirit world and witch crafting (familiars, clothing, plants).
- Adds **KubeJS compatibility** for other mods (recipes and events those mods do not provide), and **54 extra crops** for Mystical Agriculture.

### Dependencies

| Mod | Type | What for |
| --- | --- | --- |
| NeoForge 21.1.240 or higher (Minecraft 1.21.1) | Required | Mod loader |
| Enchanted 4.2.7 or higher (`enchanted`) | Required | The mod this addon is built on |
| GeckoLib 4.7 or higher (`geckolib`) | Required | Creature models and animations |
| KubeJS 2101.7.2 or higher (`kubejs`) | Optional | All of section 4 |
| Thaumaturge (`thaumaturge`) | Optional | KubeJS module and 14 Mystical Agriculture crops |
| Applied Energistics 2 (`ae2`) | Optional | KubeJS module |
| Advanced AE (`advanced_ae`) | Optional | KubeJS module |
| Extended AE (`extendedae`) | Optional | KubeJS module |
| Create (`create`) | Optional | KubeJS module |
| Generator Galore (`generatorgalore`) | Optional | KubeJS module |
| Excessive Utilities (`excessive_utilities`) | Optional | KubeJS module |
| Functional Storage (`functionalstorage`) | Optional | KubeJS module |
| Mystical Agriculture (`mysticalagriculture`) | Optional | KubeJS module and extra crops |

Each KubeJS module is only enabled if the mod it supports is installed. If it is not, that mod's event or recipe **does not exist** and the script throws an error.

The mod's texts are available in English and Spanish (es_es, es_ar and es_mx).

---

## 2. Built-in Content

A short summary of what the mod adds by itself. Everything can be turned off from the configuration (section 3).

### Creatures

- **Werewolf and vampire curses.** A bite can curse you (30% for the werewolf and 25% for the vampire by default). You transform with a key or with the **Lunar Talisman** (werewolf) and the **Blood Sigil** (vampire).
- **Abilities.** The werewolf leaps onto its prey; the vampire drains blood.
- **Weaknesses.** Silver (silver sword and silver ingot) and wolfsbane (`enchanted:wolfsbane_flower`) for the werewolf; sunlight, the wooden stake and garlic for the vampire.
- **You keep your skin.** While transformed you keep your skin and gain glowing eyes: red (vampire) and green (werewolf). The color and the on/off switch are configurable.
- **Cure.** The **Purifying Tonic** or the purification rite.
- **Hunter.** A third state: you hit werewolves and vampires harder, take less damage from them and cannot be cursed by them. It is only activated with the command `/umc form hunter`.
- **New mobs.** Werewolf, vampire, ghost, imp and ent. Also the summonable **servants**: Risen Servant (zombie) and Bone Servant (skeleton).
- **Necromancy.** Magic circle rites to raise servants, ghosts, imps and ents; the **Necromancer's Scepter**, the **Summoning Horn** (consumes grave dust) and the **Demonomicon** (binds imps by paying with life and an imp horn). Also the *Pact of the Moon* and the *Pact of Blood*.

### Spirit World

- A dimension of its own. You enter through the **Spirit Portal** (charged with spirit dust) or with the *Spirit Journey* rite. To return, use the return key.
- **Dream Catcher and Dream Loom:** they change the chance of good dreams and nightmares when sleeping. A good dream can give rewards, experience or a trip to the spirit world.
- **Infusions:** Spirit Leap, Wall Walking and Spirit Sight. They are charged with essences and switched with a key.
- **Covens:** found one at a Coven Altar, invite witches and share bonuses (altar recharge and Coven Bond).

### Witch Crafting

- **Familiars:** toad, owl, raven and hound, each with its own bonuses and its own binding rite.
- **Witch clothing:** hat, robe, cloak and boots, with bonuses per piece and for the full set (faster altars, curse resistance, longer beneficial potions, a cloak that softens falls).
- **Plants:** wormwood, vervain, moonbloom and mugwort, for Enchanted's cauldron, spinning wheel and distillery.
- **In-game guide:** the *Hedge Witch's Almanac* (a Modopedia book with the chapters Rituals, Familiars, Witch Clothing, Plants, Creatures and Spirit World).

### Commands

| Command | Who | What it does |
| --- | --- | --- |
| `/umc form <vampire\|werewolf\|hunter\|human> [players]` | Operators (level 2) | Puts a player into that form. `human` cures them and leaves them human. If the form is disabled in the config, it warns you and does nothing |
| `/umc coven create <name>` | Player | Founds a coven |
| `/umc coven invite <player>` | Player | Invites a player |
| `/umc coven join <name>` | Player | Joins a coven you have been invited to |
| `/umc coven leave` | Player | Leaves your coven |
| `/umc coven kick <player>` | Leader | Expels a member |
| `/umc coven disband` | Leader | Disbands the coven |
| `/umc coven info [name]` | Player | Shows coven information |

### Keys (can be changed in Controls)

| Default key | Action |
| --- | --- |
| G | Transform |
| H | Creature ability |
| V | Cycle infusion |
| B | Return from the spirit world |

### Creative Tab

A tab of its own called **Ultimate Mod Compats** (id `umc:main`) with the mod's items. The icon is the item `umc:tab_icon` (named "Space Cat" in the game).

---

## 3. Configuration

There is **a single file**: `config/umc.toml` (COMMON type, generated the first time the game starts). Every feature has its own `enabled` switch. The comments in the file are in English.

### Sections

| Section | What it controls |
| --- | --- |
| `creatures` | Werewolf, vampire, infection, cure, hunter, mobs, necromancy, demonology and rites |
| `spirit` | Spirit world, dreams, infusions and covens |
| `crafting` | Familiars, witch clothing and plants |
| `compat.kubejs` | Master switch and one switch per mod for KubeJS compatibility |
| `compat.mysticalagriculture` | Extra Mystical Agriculture crops and their recipes |

The keys in `creatures` and `crafting` use camelCase (`healthBonus`); those in `spirit` and `compat` use snake_case (`portal_active_seconds`).

### `creatures`

| Key | Default | What it is |
| --- | --- | --- |
| `creatures.enabled` | `true` | Turns off the whole creatures module |
| `creatures.werewolf.enabled` | `true` | Lycanthropy and werewolf form |
| `creatures.werewolf.healthBonus` / `damageBonus` / `speedBonus` | `10.0` / `4.0` / `0.25` | Bonuses while transformed |
| `creatures.werewolf.forceOnFullMoon` | `true` | Cursed players are forcibly transformed on a full moon |
| `creatures.werewolf.revertAtDawn` | `true` | The forced transformation ends at dawn |
| `creatures.werewolf.requireNightToTransform` | `false` | You can only transform voluntarily at night |
| `creatures.werewolf.silverDamageMultiplier` | `2.0` | Damage multiplier for silver weapons |
| `creatures.werewolf.repellentItem` | `"enchanted:wolfsbane_flower"` | Item that weakens werewolves if you carry it nearby |
| `creatures.werewolf.spawnOnlyOnFullMoon` | `false` | Wild werewolves only spawn on a full moon |
| `creatures.werewolf.useModel` | `false` | Uses the custom GeckoLib model instead of your skin (needs a resource pack with the model) |
| `creatures.werewolf.glowingEyes` / `eyeColor` | `true` / `"33FF33"` | Glowing eyes and their color (hexadecimal RRGGBB) |
| `creatures.vampire.enabled` | `true` | Vampirism and vampire form |
| `creatures.vampire.healthBonus` / `damageBonus` / `speedBonus` | `4.0` / `3.0` / `0.15` | Bonuses while transformed |
| `creatures.vampire.sunlightBurns` | `true` | Vampire form burns in direct sunlight |
| `creatures.vampire.helmetProtects` / `rainProtects` | `true` / `true` | A helmet or rain prevents burning |
| `creatures.vampire.stakeBonusDamage` | `10.0` | Extra damage from the wooden stake |
| `creatures.vampire.repellentItem` | `"enchanted:garlic"` | Item that weakens vampires |
| `creatures.vampire.drainRange` / `drainDamage` | `3.5` / `5.0` | Range and damage of the blood drain |
| `creatures.vampire.glowingEyes` / `eyeColor` | `true` / `"FF1A1A"` | Glowing eyes and their color |
| `creatures.infection.werewolfBiteChance` / `vampireBiteChance` | `0.3` / `0.25` | Chance of cursing on a bite |
| `creatures.infection.fromPlayers` | `false` | Transformed players also curse |
| `creatures.infection.loseCurseOnDeath` | `false` | Dying lifts the curse |
| `creatures.cure.tonic` / `rite` | `true` / `true` | The tonic and the purification rite cure |
| `creatures.hunter.damageMultiplier` / `damageTakenMultiplier` | `1.5` / `0.7` | Damage the hunter deals and takes against werewolves and vampires |
| `creatures.mobs.<mob>.enabled` | `true` | Enables the mob and its natural spawning. `<mob>`: `werewolf`, `vampire`, `ghost`, `imp`, `ent`, `zombie_servant`, `skeleton_servant` |
| `creatures.mobs.<mob>.health` / `damage` / `speed` | depends on the mob | Health, damage and speed |
| `creatures.necromancy.maxServantsPerOwner` | `8` | Maximum servants per player |
| `creatures.necromancy.servantLifetimeSeconds` | `600` | Servants fade away after this time (0 = never) |
| `creatures.necromancy.horn.graveDustCost` | `1` | Grave dust consumed by each blow of the horn |
| `creatures.demonology.grimoire.healthCost` | `6.0` | Health paid when binding an imp |
| `creatures.demonology.grimoire.requiresImpHorn` | `true` | Binding consumes an imp horn |
| `creatures.rites.enabled` | `true` | Enables the circle rites added by the module |

### `spirit`

| Key | Default | What it is |
| --- | --- | --- |
| `spirit.enabled` | `true` | Turns off the whole spirit world module |
| `spirit.world.enabled` | `true` | The spirit world and its portals |
| `spirit.world.portal_active_seconds` | `30` | Seconds the portal stays open after being charged |
| `spirit.world.portal_dust_cost` | `1` | Spirit dust consumed to charge the portal |
| `spirit.world.return_delay_seconds` | `5` | Seconds the return key takes |
| `spirit.world.rite_enabled` / `rite_duration_seconds` | `true` / `300` | Enchanted rite that sends you to the spirit world and how long it lasts (0 = unlimited) |
| `spirit.world.nightmare_spawns_enabled` | `true` | Nightmares spawn on their own in the spirit world |
| `spirit.dreams.enabled` | `true` | Dream weaving |
| `spirit.dreams.nightmare_chance` / `good_dream_chance` | `0.15` / `0.1` | Base chance of a nightmare and of a good dream when sleeping |
| `spirit.dreams.max_catchers` | `4` | Maximum number of dream catchers that count |
| `spirit.dreams.good_dream_rewards` | list | Rewards for good dreams in the format `item;amount;weight` |
| `spirit.infusions.enabled` | `true` | Dimensional infusions |
| `spirit.infusions.leap_enabled` / `climb_enabled` / `sight_enabled` | `true` | Enables each infusion separately |
| `spirit.infusions.essence_seconds` / `max_energy_seconds` | `120` / `900` | Energy per essence and maximum storable energy |
| `spirit.infusions.hud_enabled` | `true` | Shows the infusions HUD |
| `spirit.covens.enabled` | `true` | Covens |
| `spirit.covens.max_members` | `8` | Maximum number of members |
| `spirit.covens.found_cost_item` / `found_cost_count` | `"umc:spirit_dust"` / `3` | Item consumed when founding a coven (empty = none) |
| `spirit.covens.open_join` | `false` | Anyone can join at the altar without an invitation |

### `crafting`

| Key | Default | What it is |
| --- | --- | --- |
| `crafting.enabled` | `true` | Turns off the whole crafting module |
| `crafting.familiars.enabled` / `ritesEnabled` | `true` / `true` | Extra familiars and their binding rites |
| `crafting.familiars.<species>.enabled` | `true` | `<species>`: `toad`, `owl`, `raven`, `hound` |
| `crafting.familiars.<species>.bonusEffects` | depends on the species | Effects the owner receives, in the format `"effect,amplifier"` |
| `crafting.familiars.<species>.tameChance` / `tameFoods` | `0.33` / list | Chance and food for taming (toad, owl and raven) |
| `crafting.familiars.<species>.spawnEnabled` / `spawnWeight` | `true` / depends on the species | Natural spawning (toad, owl and raven) |
| `crafting.clothing.enabled` | `true` | Witch clothing bonuses |
| `crafting.clothing.setEffects` | `["minecraft:luck,0"]` | Effects of the full set |
| `crafting.clothing.altar.power` / `radius` | `30.0` / `16` | Power added to nearby altars and radius |
| `crafting.clothing.curses.chance` | `0.15` | Chance per check of weakening a curse while wearing the full set |
| `crafting.clothing.potions.durationMultiplier` | `1.25` | Extra duration of beneficial potions |
| `crafting.clothing.cloak.reduction` | `0.5` | Fraction of fall damage the cloak removes |
| `crafting.plants.enabled` | `true` | Witch plants |
| `crafting.plants.wildPlants` | `true` | Generates moonbloom and mugwort in the world |
| `crafting.plants.seedDropChance` | `0.08` | Chance of dropping seeds when breaking grass |
| `crafting.plants.seedDropHoeOnly` | `false` | Only drops seeds if broken with a hoe |

### `compat.kubejs`: turning off compatibilities

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

| Key | Mod it controls |
| --- | --- |
| `compat.kubejs.enabled` | Master switch: when `false`, none of this mod's KubeJS modules are registered |
| `compat.kubejs.modules.enchanted` | Enchanted |
| `compat.kubejs.modules.thaumaturge` | Thaumaturge |
| `compat.kubejs.modules.ae2` | Applied Energistics 2 |
| `compat.kubejs.modules.advanced_ae` | Advanced AE |
| `compat.kubejs.modules.extendedae` | Extended AE |
| `compat.kubejs.modules.create` | Create (includes the right-click action on entities and the JEI help) |
| `compat.kubejs.modules.generatorgalore` | Generator Galore |
| `compat.kubejs.modules.excessive_utilities` | Excessive Utilities |
| `compat.kubejs.modules.functionalstorage` | Functional Storage |
| `compat.kubejs.modules.mysticalagriculture` | Mystical Agriculture (recipes and the `MysticalEvents` event) |

To disable a single compatibility, set its key to `false`. It takes effect when the game starts.

### `compat.mysticalagriculture`: extra crops

| Key | Default | What it is |
| --- | --- | --- |
| `compat.mysticalagriculture.enabled` | `true` | Master switch: when `false`, no crop is registered |
| `compat.mysticalagriculture.crop_namespace` | `"umc"` | Namespace of the crop ids (the items are still `mysticalagriculture:`) |
| `compat.mysticalagriculture.recipes.seed_crafting` | `true` | Crafting recipe for each seed |
| `compat.mysticalagriculture.recipes.seed_infusion` | `true` | Recipe for each seed in the Infusion Altar |
| `compat.mysticalagriculture.recipes.seed_reprocessor` | `true` | Recipe for each seed in the Seed Reprocessor |
| `compat.mysticalagriculture.recipes.essence_crafting` | `true` | Recipe that turns the essence into the crop's item |
| `compat.mysticalagriculture.crops.<id>` | `true` | Registers or not each crop (see section 5 for the ids) |

Crops are registered at startup, so changing these keys requires restarting the game.

---

## 4. KubeJS Compatibility

This whole section needs **KubeJS** (2101.7.2 or higher for 1.21.1) and the mod that each subsection supports.

### Where Each Script Goes

| Folder | When it loads | What it is used for here |
| --- | --- | --- |
| `kubejs/startup_scripts/` | When the game starts (you must restart to see changes) | `EnchantedStartup.*`, `ThaumaturgeStartup.*`, `StartupEvents.registry('item', ...)` |
| `kubejs/server_scripts/` | When entering the world and on `/reload` | `ServerEvents.recipes`, `EnchantedEvents.*`, `ThaumaturgeEvents.*`, `MysticalEvents.recipes`, `ServerEvents.generateData` |
| `kubejs/client_scripts/` | When resources load on the client | `ClientEvents.lang` |

### Common Conventions

- Items are written as `'namespace:item'`. With an amount: `'3x namespace:item'`. Tags: `'#namespace:tag'`.
- All recipes accept `.id('namespace:name')` at the end, like any KubeJS recipe.
- If a custom id has no namespace, `kubejs:` is used.
- Fluids are written as `Fluid.of('minecraft:water', 1000)` (amount in mB).
- The complete example scripts are in the `kubejs-ejemplos/` folder of the repository (one file per mod).

---

### 4.1. Enchanted

Module `compat.kubejs.modules.enchanted`. Required mod: `enchanted` (always present, because it is mandatory).

What it adds:

| What | Where it is written | What for |
| --- | --- | --- |
| `event.recipes.enchanted.kettle / wheel / distilling / byproduct` | `ServerEvents.recipes` | Recipes for the kettle, the spinning wheel, the distillery and the furnace byproduct |
| `EnchantedEvents.rites` | server | New magic circle rites and disabling rites |
| `EnchantedEvents.shapes` | server | New circle shapes |
| `EnchantedEvents.altar` | server | Altar power sources and upgrades |
| `EnchantedEvents.mutagens` | server | Plant mutations (mutandis) |
| `EnchantedStartup.riteFactories` | startup | Rites with your own JS code |
| `EnchantedStartup.curses` | startup | New curses |
| `EnchantedStartup.familiars` | startup | New familiars |
| `StartupEvents.registry('item', ...)` with `enchanted:*` types | startup | Poppets, brews and bottles |

Rites, shapes, mutations and altar upgrades are generated as data when the server loads.

#### Recipes

Available aliases: `enchantedKettle`, `enchantedWheel` (or `enchantedSpinning`), `enchantedDistilling`, `enchantedByproduct`.

| Recipe | Call | Arguments |
| --- | --- | --- |
| Kettle | `event.recipes.enchanted.kettle(result, [ingredients])` | `result` (item with amount), `ingredients` (list of items) |
| Spinning wheel | `event.recipes.enchanted.wheel(result, [3 ingredients])` | `result`, exactly 3 ingredients |
| Distillery | `event.recipes.enchanted.distilling([results], [ingredients])` | 1 to 4 results and 1 to 3 ingredients |
| Byproduct | `event.recipes.enchanted.byproduct(result, ingredient)` | `result`, `ingredient` (item or tag) |

Chainable options:

| Recipe | Method | Type | What it is | Default |
| --- | --- | --- | --- | --- |
| kettle | `.requiresBottle(bool)` | boolean | Needs a bottle | `false` |
| kettle | `.power(int)` | integer | Altar power it consumes | `0` |
| kettle | `.cookColour(color)` | color | Color of the liquid while it cooks (`'#B046A5'`, `'B046A5'` or `0xB046A5`; also `cookColor`) | `#2D155E` |
| kettle | `.finalColour(color)` | color | Color of the liquid when finished (also `finalColor`) | `#4A1AAD` |
| wheel | `.power(int)` | integer | Altar power it consumes | `0` |
| wheel | `.duration(ticks)` | integer | Duration in ticks | `300` |
| distilling | `.duration(ticks)` | integer | Duration in ticks | `300` |
| distilling | `.power(int)` | integer | Altar power it consumes | `750` |

| Recipe | Input | Output | Where |
| --- | --- | --- | --- |
| `kettle` example | `minecraft:poppy`, `golden_carrot`, `lily_pad`, `cocoa_beans`, `enchanted:whiff_of_magic`, `enchanted:water_artichoke` and a bottle | 3x `enchanted:brew_of_love` | Kettle (the liquid goes from `#B046A5` to `F78FEB`) |
| `wheel` example | `enchanted:poppet` + `enchanted:demonic_blood` + `minecraft:magma_block`, 500 power, 300 ticks | `kubejs:lava_poppet` | Spinning wheel |
| `distilling` example | 3x `enchanted:clay_jar` + `enchanted:breath_of_the_goddess` + `minecraft:lapis_lazuli`, 300 ticks, 750 power | `enchanted:tear_of_the_goddess`, `enchanted:whiff_of_magic`, `minecraft:slime_ball` | Distillery |
| `byproduct` example | Any `#minecraft:logs_that_burn` | `enchanted:foul_fume` as a byproduct | Witch's oven with a `clay_jar` |

```js
ServerEvents.recipes(event => {
    //gives 3x enchanted:brew_of_love by mixing minecraft:poppy, golden_carrot, lily_pad, cocoa_beans, enchanted:whiff_of_magic and water_artichoke in the kettle with a bottle
    event.recipes.enchanted.kettle('3x enchanted:brew_of_love', [
        'minecraft:poppy',
        'minecraft:golden_carrot',
        'minecraft:lily_pad',
        'minecraft:cocoa_beans',
        'enchanted:whiff_of_magic',
        'enchanted:water_artichoke'
    ]).requiresBottle(true).cookColour('#B046A5').finalColour('F78FEB')

    //gives kubejs:lava_poppet by weaving enchanted:poppet + enchanted:demonic_blood + minecraft:magma_block on the spinning wheel with 500 altar power for 300 ticks
    event.recipes.enchanted.wheel('kubejs:lava_poppet', ['enchanted:poppet', 'enchanted:demonic_blood', 'minecraft:magma_block'])
        .power(500)
        .duration(300)

    //gives enchanted:tear_of_the_goddess, enchanted:whiff_of_magic and minecraft:slime_ball by distilling 3x enchanted:clay_jar + breath_of_the_goddess + minecraft:lapis_lazuli
    event.recipes.enchanted.distilling(
        ['enchanted:tear_of_the_goddess', 'enchanted:whiff_of_magic', 'minecraft:slime_ball'],
        ['3x enchanted:clay_jar', 'enchanted:breath_of_the_goddess', 'minecraft:lapis_lazuli']
    ).duration(300).power(750)

    //gives enchanted:foul_fume as a byproduct when burning any flammable log in the witch's oven with a clay_jar
    event.recipes.enchanted.byproduct('enchanted:foul_fume', '#minecraft:logs_that_burn')
})
```

#### Circle Rites (`EnchantedEvents.rites`)

`event.create(id)` creates a rite and requirements and a final effect (the "factory") are chained onto it. **Every rite needs a final effect**; if it is missing, the event throws an error.

Requirements and conditions:

| Method | Arguments | What it does |
| --- | --- | --- |
| `.item(stack)` / `.items(...stacks)` | items with amount (`'2x minecraft:redstone'`) | Objects offered in the circle |
| `.smallCircle(chalk)` / `.mediumCircle(chalk)` / `.largeCircle(chalk)` | chalk (`'ritual_chalk'`, `'nether_chalk'`) | Small, medium or large circle with that chalk. Without a namespace, `enchanted:` is used |
| `.circle(shape, chalk)` | shape and chalk | Circle shape by id (for example a custom `kubejs:cross_circle`) |
| `.entity(...types)` | entity ids | Required entities |
| `.dimension(...ids)` | dimension ids | Only in those dimensions |
| `.power(int)` | integer | Altar power it consumes at the start |
| `.tickPower(int)` | integer | Extra power per tick |
| `.weather(weather)` | `none`, `clear`, `raining`, `thundering` | Required weather |
| `.night()` / `.day()` | no arguments | Only at night (ticks 13000 to 23000) or during the day (0 to 12000) |
| `.time(from, to)` | ticks | Custom time window |

Final effect (use one per rite):

| Method | Arguments | What it does |
| --- | --- | --- |
| `.factory(id)` / `.factory(id, {params})` | factory id and parameter object | Activates an Enchanted factory or one of your own (`EnchantedStartup.riteFactories`) |
| `.createItem(...stacks)` | items | Creates those items |
| `.command(...commands)` | commands | Runs commands |
| `.applyCurse(id)` | curse id | Applies a curse |
| `.protection(radius, duration, blocksPlayers)` | radius, ticks, boolean | Protected zone (also `.protection(radius)`) |
| `.entityBoundCreateItem(...)`, `.locationBoundCreateItem(...)` | items | Creates items bound to an entity or a location |
| `.duplicateItem(item, amount)` | item and integer | Duplicates an item |
| `.removeCurse(id)` | curse id | Removes a curse |
| `.commandSequence(delay, steps)` | ticks and list of command lists | Commands in steps |
| `.fertility(radius, chance)`, `.forest(radius, spacing)`, `.blight(radius, decayChance, zombieChance)`, `.broiling(burnChance)` | numbers | Enchanted terrain effects |
| `.transposeBlocks(blockTag, tool)` | tag and item | Block transposition |
| `.skyWrath()`, `.sanctity()`, `.imprisonment()`, `.totalEclipse()`, `.summonEntity()`, `.bindFamiliar()`, `.summonFamiliar()`, `.bindTalisman()`, `.transposeCaster()` | no arguments | Standalone Enchanted factories |

To override an Enchanted rite: `event.disable('enchanted:blight')`. The original rite is replaced by one that is impossible to fulfill.

| Example | Input | Output | Where |
| --- | --- | --- | --- |
| `kubejs:diamond_gift` | 1 `minecraft:coal_block`, 800 power | 1 `minecraft:diamond` and 2 `minecraft:emerald` | Small ritual chalk circle |
| `kubejs:blessing_rite` | `enchanted:tear_of_the_goddess` + `minecraft:golden_apple`, 1500 power (+2 per tick), at night with a clear sky | Activates the factory `kubejs:blessing` | Medium ritual chalk circle |
| `kubejs:thunder_rite` | `minecraft:iron_ingot` + 2x `minecraft:redstone`, 2500 power, thunderstorm, Overworld only | Activates the factory `kubejs:thunder_call` | Shape `kubejs:cross_circle` with Nether chalk |
| `kubejs:curse_heavy_feet` | `enchanted:taglock` + `minecraft:anvil`, 1800 power | Applies the curse `kubejs:heavy_feet` | Medium Nether chalk circle |
| `kubejs:quick_day` | `minecraft:clock`, 600 power | Runs `time set day` and `weather clear` | Small ritual chalk circle |
| `kubejs:big_protection` | `minecraft:obsidian` + `minecraft:diamond`, 2000 power (+3 per tick) | Protected zone of radius 12 for 12000 ticks (10 min) that also blocks players | Medium ritual chalk circle |

```js
EnchantedEvents.rites(event => {
    //makes a night rite with a clear sky: Tear of the Goddess + golden apple in a medium ritual chalk circle, costs 1500 power (+2 per tick) and activates the factory kubejs:blessing
    event.create('kubejs:blessing_rite')
        .item('enchanted:tear_of_the_goddess')
        .item('minecraft:golden_apple')
        .mediumCircle('ritual_chalk')
        .power(1500)
        .tickPower(2)
        .night()
        .weather('clear')
        .factory('kubejs:blessing', { message: 'Que la luna te proteja', level: 1 })

    //makes a thunderstorm rite in the Overworld: iron ingot + 2 redstone in the kubejs:cross_circle shape with Nether chalk, costs 2500 and activates kubejs:thunder_call
    event.create('kubejs:thunder_rite')
        .items('minecraft:iron_ingot', '2x minecraft:redstone')
        .circle('kubejs:cross_circle', 'enchanted:nether_chalk')
        .power(2500)
        .weather('thundering')
        .dimension('minecraft:overworld')
        .factory('kubejs:thunder_call')

    //gives 1 minecraft:diamond and 2 minecraft:emerald by sacrificing a minecraft:coal_block in a small ritual chalk circle, costs 800
    event.create('kubejs:diamond_gift')
        .item('minecraft:coal_block')
        .smallCircle('ritual_chalk')
        .power(800)
        .createItem('minecraft:diamond', '2x minecraft:emerald')

    //makes the curse kubejs:heavy_feet apply with taglock + anvil in a medium Nether chalk circle, costs 1800
    event.create('kubejs:curse_heavy_feet')
        .items('enchanted:taglock', 'minecraft:anvil')
        .mediumCircle('nether_chalk')
        .power(1800)
        .applyCurse('kubejs:heavy_feet')

    //makes it day with a clear sky using a clock in a small ritual chalk circle, costs 600
    event.create('kubejs:quick_day')
        .item('minecraft:clock')
        .smallCircle('ritual_chalk')
        .power(600)
        .command('time set day', 'weather clear')

    //makes a protected zone of radius 12 for 12000 ticks (10 min) that also blocks players, with obsidian + diamond in a medium circle, costs 2000 (+3 per tick)
    event.create('kubejs:big_protection')
        .items('minecraft:obsidian', 'minecraft:diamond')
        .mediumCircle('ritual_chalk')
        .power(2000)
        .tickPower(3)
        .protection(12, 12000, true)

    //makes the enchanted:blight rite unusable (replaces it with one that is impossible to fulfill)
    event.disable('enchanted:blight')
})
```

#### Circle Shapes (`EnchantedEvents.shapes`)

`event.create(id).rows(...rows)`

| Argument | Type | What it is |
| --- | --- | --- |
| `id` | text | Shape id (for example `'kubejs:cross_circle'`) |
| `rows` | texts | An **odd** number of rows, all as long as the number of rows (square), with at least one `X`. `X` is a chalk tile and a space is empty |

Input: the rows. Output: a shape that is used with `.circle('kubejs:cross_circle', chalk)` in a rite.

```js
EnchantedEvents.shapes(event => {
    //makes the shape kubejs:cross_circle, a 7x7 square (X = chalk tile, O = interior without chalk, space = empty)
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

| Call | Arguments | What it does |
| --- | --- | --- |
| `event.block(block, power, limit)` | block, integer, integer | Each nearby block gives the altar that much power, counting up to the limit |
| `event.tag(tag, power, limit)` | block tag (without `#`), integer, integer | Same, for a tag |
| `event.upgrade(id).type(type).block(block).power(decimal).recharge(decimal).priority(int)` | see example | Altar upgrade |

```js
EnchantedEvents.altar(event => {
    //gives the altar 6 power for each nearby minecraft:amethyst_block, counting up to 16 blocks
    event.block('minecraft:amethyst_block', 6, 16)
    //gives the altar 3 power for each nearby block of the tag minecraft:flowers, counting up to 40
    event.tag('minecraft:flowers', 3, 40)
    //makes an altar upgrade with minecraft:soul_lantern of type torch: power 0, recharge x1.5 and priority 1
    event.upgrade('kubejs:soul_lantern').type('torch').block('minecraft:soul_lantern').power(0).recharge(1.5).priority(1)
})
```

#### Mutations (`EnchantedEvents.mutagens`)

`event.create(id).mutation(result, weight, ...mutagenBlocks).extremisMutation(result, weight, ...mutagenBlocks)`

| Argument | Type | What it is |
| --- | --- | --- |
| `id` | text | Mutation id |
| `mutation` | result, weight, blocks | Mutation with *mutandis* |
| `extremisMutation` | result, weight, blocks | Mutation with *mutandis extremis* |

| Input | Output |
| --- | --- |
| `minecraft:stone` or `minecraft:deepslate` around, with mutandis | `minecraft:amethyst_block` (weight 10) |
| `minecraft:amethyst_block` around, with mutandis extremis | `minecraft:budding_amethyst` (weight 2) |

```js
EnchantedEvents.mutagens(event => {
    event.create('kubejs:crystals')
        .mutation('minecraft:amethyst_block', 10, 'minecraft:stone', 'minecraft:deepslate')
        .extremisMutation('minecraft:budding_amethyst', 2, 'minecraft:amethyst_block')
})
```

#### Rites with Code (`EnchantedStartup.riteFactories`)

`event.create(id).duration(ticks).output(...items).onStart(ctx => bool).onTick(ctx => bool).onStop(ctx => ...)`

| Method | What it is |
| --- | --- |
| `.duration(ticks)` | Duration of the rite |
| `.output(...items)` | Items declared as output |
| `.onStart(ctx => bool)` | On start. Returns `true` to continue or `false` to end immediately (`ctx.cancel()` cancels) |
| `.onTick(ctx => bool)` | Every tick |
| `.onStop(ctx => ...)` | When it ends |

Context `ctx`: `caster`, `level`, `pos`, `ticks`, `data` (the rite's own NBT), `getString(key, default)`, `getInt(key, default)` (also `getDouble` and `getBoolean`), `particles(particle, amount, spread)`, `drop(item)`, `runCommand(command)`, `cancel()`. The parameters come from the second argument of the rite's `.factory(id, {params})`.

```js
EnchantedStartup.riteFactories(event => {
    //makes a 200-tick rite that gives the caster Regeneration every second, notifies them in chat and declares minecraft:golden_apple as output
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

    //makes an instant rite that summons lightning and drops a minecraft:lightning_rod over the circle (onStart returns false and ends immediately)
    event.create('kubejs:thunder_call')
        .onStart(ctx => {
            ctx.level.getServer().runCommandSilent('summon minecraft:lightning_bolt ' + ctx.pos.x + ' ' + ctx.pos.y + ' ' + ctx.pos.z)
            ctx.drop(Item.of('minecraft:lightning_rod'))
            return false
        })
})
```

#### Curses (`EnchantedStartup.curses`)

`event.create(id).maxStrength(int).onInitialise(ctx => ...).onRemove(ctx => ...).tick(ctx => ...)`

| Method | What it is |
| --- | --- |
| `.maxStrength(int)` | Maximum strength of the curse |
| `.onInitialise(ctx => ...)` | When it is applied |
| `.onRemove(ctx => ...)` | When it is removed |
| `.tick(ctx => ...)` | Every tick |

Context `ctx`: `target` (the player), `strength` (current strength), `age` (ticks since it was applied).

```js
EnchantedStartup.curses(event => {
    //makes a curse of maximum strength 3 that notifies when applied and removed, and gives Slowness (level = strength - 1) every 40 ticks
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

#### Familiars (`EnchantedStartup.familiars`)

`event.create(id, sourceEntity, familiarEntity).copy((from, familiar) => ...)`

| Argument | Type | What it is |
| --- | --- | --- |
| `id` | text | Familiar id |
| `sourceEntity` | entity id | Tameable animal that gets converted |
| `familiarEntity` | entity id | Entity it turns into |
| `copy` | function | Copies data from the original to the familiar |

Used with a `bindFamiliar` rite.

```js
EnchantedStartup.familiars(event => {
    //makes a familiar that turns a minecraft:parrot into another minecraft:parrot named Familiar
    event.create('kubejs:parrot_familiar', 'minecraft:parrot', 'minecraft:parrot')
        .copy((from, familiar) => {
            familiar.setCustomName(Text.of('Familiar'))
        })
})
```

#### Enchanted Items (poppets, brews and flasks)

They are created with `StartupEvents.registry('item', ...)` by giving the type as the second argument. Objects created this way go to the mod's tab with a default texture; put your own at `kubejs/assets/kubejs/textures/item/<id>.png`.

| Type | Methods |
| --- | --- |
| `enchanted:player_poppet` | `.poppetColour(primaryColor, secondaryColor)`, `.maxDamage(uses)`, `.protectsAgainst(...damageTypes or #tags)`, `.protectsWhen(source => bool)`, `.effect(effect, ticks, amplifier)`, `.onProtect(player => ...)` |
| `enchanted:item_poppet` | `.poppetColour(c1, c2)`, `.protects(stack => bool)`, `.restoreMultiplier(fraction)`, `.onProtect(...)` |
| `enchanted:brew` | `.effect(effect, ticks, amplifier)` (repeatable), `.onDrink(entity => ...)` |
| `enchanted:throwable_brew` | `.effect(...)` (repeatable), `.radius(blocks)`, `.brewColour(rgbColor)`, `.onSplash(ctx => ...)` with `ctx = {owner, level, pos}` |

| Example | What it gives |
| --- | --- |
| `lava_poppet` (`player_poppet`) | A 2-use poppet that protects against fire and lava, gives Fire Resistance for 30 s and notifies the player |
| `anvil_poppet` (`player_poppet`) | A poppet that protects against anvil damage; without `.effect` it gives Regeneration II for 5 s by default |
| `iron_poppet` (`item_poppet`) | An item poppet that protects `minecraft:iron_*` items and restores half of their durability |
| `moon_brew` (`brew`) | Drinkable potion: Night Vision for 5 min and Slow Falling for 1 min |
| `storm_flask` (`throwable_brew`) | Throwable flask: Glowing for 20 s on creatures within radius 5 and a lightning bolt where it breaks |

```js
StartupEvents.registry('item', event => {
    //gives a 2-use poppet that protects against fire and lava, gives Fire Resistance for 30 s and notifies the player
    event.create('lava_poppet', 'enchanted:player_poppet')
        .poppetColour(0xFFFF6A00, 0xFFB22200)
        .maxDamage(2)
        .protectsAgainst('#minecraft:is_fire', 'minecraft:lava')
        .effect('minecraft:fire_resistance', 600, 0)
        .onProtect(player => player.tell('El poppet absorbe el fuego'))

    //gives a poppet that protects against anvil damage (without .effect it gives Regeneration II for 5 s by default)
    event.create('anvil_poppet', 'enchanted:player_poppet')
        .protectsWhen(source => source.type().msgId() == 'anvil')

    //gives an item poppet that protects iron items (minecraft:iron_*) and restores half of their durability
    event.create('iron_poppet', 'enchanted:item_poppet')
        .poppetColour(0xFFD8D8D8, 0xFF9A9A9A)
        .protects(stack => stack.id.startsWith('minecraft:iron_'))
        .restoreMultiplier(0.5)

    //gives a drinkable potion that gives Night Vision for 5 min and Slow Falling for 1 min and notifies the drinker
    event.create('moon_brew', 'enchanted:brew')
        .effect('minecraft:night_vision', 6000, 0)
        .effect('minecraft:slow_falling', 1200, 0)
        .onDrink(entity => entity.tell('Sabe a luz de luna'))

    //gives a throwable flask that gives Glowing for 20 s to creatures within radius 5 and strikes lightning where it breaks
    event.create('storm_flask', 'enchanted:throwable_brew')
        .effect('minecraft:glowing', 400, 0)
        .radius(5)
        .brewColour(0x55AAFF)
        .onSplash(ctx => ctx.level.spawnLightning(ctx.pos.x(), ctx.pos.y(), ctx.pos.z(), true))
})
```

Usage notes:

- Custom rites (`kubejs:...`) are saved as data in `data/<namespace>/enchanted/circle_magic/rite/<name>.json`. The rites added by the UMC mod itself (creatures, familiars, spirit journey) use the `enchanted` namespace.
- Chalks and shapes without a namespace are read as `enchanted:` (`'ritual_chalk'` is `enchanted:ritual_chalk`).
- A custom factory or curse is registered in `startup_scripts`; the rites that use them go in `server_scripts`.

---

### 4.2. Thaumaturge

Module `compat.kubejs.modules.thaumaturge`. Required mod: `thaumaturge`.

| What | Where it is written | What for |
| --- | --- | --- |
| `ThaumaturgeStartup.wandCaps` | startup | New wand caps |
| `ThaumaturgeStartup.wandRods` | startup | New wand rods |
| `ThaumaturgeStartup.golemTraits` | startup | Golem traits |
| `ThaumaturgeStartup.mobTraits` | startup | Mob traits (champions) |
| `ThaumaturgeEvents.aspects` | server | New aspects and assigning aspects to items, mobs and biomes |
| `ThaumaturgeEvents.scans` | server | Scans |
| `ThaumaturgeEvents.blueprints` | server | Multiblock blueprints |
| `ThaumaturgeEvents.research` | server | Research categories and entries |
| `ThaumaturgeEvents.data` | server | Hand-written JSON for any data registry without its own event |
| `event.recipes.thaumaturge.<type>` | `ServerEvents.recipes` | Recipes (table below) |

Aspects are written with or without a namespace: `'ignis'` is `thaumaturge:ignis`. With an amount: `'30x ignis'`, `'10x kubejs:lux'`.

#### Wand Caps (`ThaumaturgeStartup.wandCaps`)

`event.create(id)` and chain:

| Method | Type | What it is |
| --- | --- | --- |
| `.baseCost(multiplier)` | decimal | Vis cost multiplier |
| `.specialCost(multiplier, ...aspects)` | decimal and aspects | Different cost for those aspects |
| `.aspectCost(aspect, multiplier)` | aspect and decimal | Cost for a single aspect |
| `.craftCost(cost)` | integer | Cost of crafting the cap |
| `.texture(path)` | text | Cap texture |

```js
ThaumaturgeStartup.wandCaps(event => {
    //makes the cap kubejs:emerald get created
    event.create('kubejs:emerald')
        .baseCost(0.95)                      //the cap multiplies the vis cost by 0.95
        .specialCost(0.9, 'aqua', 'terra')   //the cost drops to 0.9 for the aspects aqua and terra
        .craftCost(5)                        //crafting the cap costs 5
        .texture('kubejs:textures/models/wand_cap_emerald.png')

    //makes the cap kubejs:prism get created
    event.create('kubejs:prism')
        .baseCost(1.0)
        .aspectCost('ignis', 0.8)
        .aspectCost('aer', 0.85)
        .craftCost(4)
})
```

#### Wand Rods (`ThaumaturgeStartup.wandRods`)

| Method | Type | What it is |
| --- | --- | --- |
| `.capacity(vis)` | integer | The rod's vis reserve |
| `.craftCost(cost)` | integer | Cost of crafting the rod |
| `.glow(bool)` | boolean | The rod glows |
| `.texture(path)` | text | Rod texture |
| `.onUpdate((stack, player) => ...)` | function | Runs while the wand is in the inventory |
| `.staff(bool)`, `.runes(bool)`, `.assemblyResearch(research)` | | Other options |

```js
ThaumaturgeStartup.wandRods(event => {
    //makes the rod kubejs:crystal get created
    event.create('kubejs:crystal')
        .capacity(90)
        .craftCost(7)
        .glow(true)
        .texture('kubejs:textures/models/wand_rod_crystal.png')
        //every 200 ticks while the wand is in the inventory the player gets speed
        .onUpdate((stack, player) => {
            if (player.tickCount % 200 == 0) {
                player.potionEffects.add('minecraft:speed', 100, 0)
            }
        })
})
```

#### Golem and Mob Traits

`golemTraits`: `event.create(id).icon(texturePath)` (only needs an id and an icon).

`mobTraits`: `event.create(id)` with:

| Method | What it is |
| --- | --- |
| `.champion(bool)` | The trait counts as a champion |
| `.tick(mob => ...)` | Every tick |
| `.onAttack(ctx => newDamage)` | Returns the damage the mob deals (`ctx.amount`) |
| `.onHurt(ctx => newDamage)` | Returns the damage it receives (`ctx.amount`, `ctx.source`) |

```js
ThaumaturgeStartup.golemTraits(event => {
    event.create('kubejs:fragile')
        .icon('kubejs:textures/misc/golem/tag_fragile.png')
})

ThaumaturgeStartup.mobTraits(event => {
    //makes the mob trait kubejs:vampiric get created
    event.create('kubejs:vampiric')
        .champion(true)
        //every 100 ticks the injured mob recovers 1 health
        .tick(mob => {
            if (mob.tickCount % 100 == 0 && mob.health < mob.maxHealth) {
                mob.heal(1)
            }
        })
        //the damage the mob deals is multiplied by 1.25
        .onAttack(ctx => ctx.amount * 1.25)
        //the damage the mob receives is doubled if it comes from fire
        .onHurt(ctx => (ctx.source.getMsgId() == 'inFire' || ctx.source.getMsgId() == 'onFire') ? ctx.amount * 2 : ctx.amount)
})
```

#### Aspects (`ThaumaturgeEvents.aspects`)

| Call | Arguments | What it does |
| --- | --- | --- |
| `event.create(id)` | id | Creates a new aspect. Chain `.color(hex)`, `.chatColor(code)`, `.components(aspect1, aspect2)` and `.texture(path)` |
| `event.item(item or #tag, ...aspects)` | item or tag and aspects with amount | Aspects of an item |
| `event.removeItem(item or #tag)` | item or tag | Removes an item's aspects |
| `event.entity(mob, ...aspects)` | entity id and aspects | Aspects of a mob |
| `event.biome(biome, ...aspects)` | biome id and aspects | Aspects of a biome |

```js
ThaumaturgeEvents.aspects(event => {
    //makes the aspect kubejs:lux get created, composed of ignis and aer
    event.create('kubejs:lux')
        .color(0xFFEE55)
        .chatColor('e')
        .components('ignis', 'aer')
        .texture('kubejs:textures/aspects/lux.png')

    //makes minecraft:glowstone have 10x kubejs:lux and 2x thaumaturge:ignis
    event.item('minecraft:glowstone', '10x kubejs:lux', '2x thaumaturge:ignis')
    //makes copper ingots (#c:ingots/copper) have 3x thaumaturge:metallum and 1x thaumaturge:potentia
    event.item('#c:ingots/copper', '3x thaumaturge:metallum', '1x thaumaturge:potentia')
    //makes minecraft:dirt lose its aspects
    event.removeItem('minecraft:dirt')
    //makes minecraft:bee have 5x thaumaturge:volatus and 3x kubejs:lux
    event.entity('minecraft:bee', '5x thaumaturge:volatus', '3x kubejs:lux')
    //makes the biome minecraft:desert have thaumaturge:ignis and kubejs:lux
    event.biome('minecraft:desert', 'thaumaturge:ignis', 'kubejs:lux')
})
```

#### Scans (`ThaumaturgeEvents.scans`)

`event.create(id).key(key).items(...items).blocks(...blocks)`

| Method | What it is |
| --- | --- |
| `.key(key)` | Key that is set when scanning (it can later be required in a research) |
| `.items(...)` | Objects that can be scanned as an item |
| `.blocks(...)` | Blocks that can be scanned as a block |

```js
ThaumaturgeEvents.scans(event => {
    //makes scanning glowstone with the scanner set the key kubejs:scanned/glowstone
    event.create('kubejs:scan_glowstone')
        .key('kubejs:scanned/glowstone')
        .items('minecraft:glowstone', 'minecraft:glowstone_dust')
        .blocks('minecraft:glowstone')
})
```

#### Multiblock Blueprints (`ThaumaturgeEvents.blueprints`)

`event.create(id).layer(...rows).blockKey(symbol, block, resultBlock).keepKey(symbol, block)`

| Method | What it is |
| --- | --- |
| `.layer(row1, row2, ...)` | One layer of the structure. A space means "anything goes". **The first `layer()` is the TOP one and the last is the BOTTOM one** |
| `.blockKey(symbol, block, resultBlock)` | The symbol is built with `block` and turns into `resultBlock` when activated |
| `.keepKey(symbol, block)` | The symbol is `block` and is kept when activated |

The blueprint is neither an item nor a block, it is the shape to build. It is activated with a `dust_trigger_multiblock` recipe (below).

```js
ThaumaturgeEvents.blueprints(event => {
    event.create('kubejs:lux_altar')
        //top layer: ring of G with an empty center
        .layer('GGG', 'G G', 'GGG')
        //bottom layer: cross of G with L in the center
        .layer(' G ', 'GLG', ' G ')
        //G is built with glowstone and turns into a sea lantern when activated
        .blockKey('G', 'minecraft:glowstone', 'minecraft:sea_lantern')
        //L is lodestone and is kept
        .keepKey('L', 'minecraft:lodestone')
})
```

#### Research (`ThaumaturgeEvents.research`)

Categories: `event.createCategory(id)`

| Method | What it is |
| --- | --- |
| `.icon(texture)` | Icon |
| `.background(texture)` | Background |
| `.overlayBackground(texture)` | Overlay layer for the background |
| `.index(position)` | Position in the category list |
| `.formula(...aspects)` | Aspects of the formula, for example `'30x ignis', '10x kubejs:lux'` |
| `.requiredResearch(entry)` | Research needed to see the category |

Entries: `event.create(id)`

| Method | What it is |
| --- | --- |
| `.category(id)` | Category it belongs to |
| `.name(langKey)` | Language key of the title |
| `.position(x, y)` | Position on the map |
| `.parent('entry@stage')` | Entry it depends on, with the stage (`'kubejs:luminous_basics@1'`) |
| `.icon(item or texture)` | Icon (also `.clearIcons()`) |
| `.meta(shape)` | Shape of the entry, for example `'round'` |
| `.complexity(n)` | Complexity |
| `.noteAspect(...aspects)` | Aspects of the notes |
| `.stage(stage => ...)` | A stage (you can add several) |
| `.addendum(addendum => ...)` | An addendum |

Stage (`stage`): `.text(langKey)`, `.obtain(item, amount)`, `.craft(item, amount)`, `.recipe(recipe)`, `.requiredKnowledge(type, category, amount)`, `.knowledge(type, category, amount)`, `.requiredResearch(...entries)`, `.warp(amount)`.
Addendum (`addendum`): `.text(langKey)`, `.recipe(...)`, `.requiredResearch(entry)`.

To modify an entry that already exists: `event.modify(id, entry => entry.complexity(1))`.

The texts come from language keys; they are added with `ClientEvents.lang('en_us', ...)` (or `es_es`) in `client_scripts`.

```js
ThaumaturgeEvents.research(event => {
    //makes the research category kubejs:radiance get created
    event.createCategory('kubejs:radiance')
        .icon('kubejs:textures/research/cat_radiance.png')
        .background('thaumaturge:textures/gui/gui_research_back_3.png')
        .overlayBackground('thaumaturge:textures/gui/gui_research_back_over.png')
        .index(9)
        .formula('30x ignis', '10x kubejs:lux')

    //makes the entry kubejs:luminous_basics get created with two stages
    event.create('kubejs:luminous_basics')
        .category('kubejs:radiance')
        .name('research.kubejs.luminous_basics.title')
        .position(0, 0)
        .icon('minecraft:glowstone')
        .meta('round')
        .complexity(1)
        .noteAspect('4x kubejs:lux', '2x ignis')
        //stage 0: obtain 8 glowstone and require 1 observation knowledge of basics
        .stage(stage => stage
            .text('research.kubejs.luminous_basics.stage_0')
            .obtain('minecraft:glowstone', 8)
            .requiredKnowledge('observation', 'basics', 1))
        //stage 1: require the recipe of kubejs:luminous_blade and give 1 theory knowledge of basics
        .stage(stage => stage
            .text('research.kubejs.luminous_basics.stage_1')
            .recipe('kubejs:luminous_blade')
            .knowledge('theory', 'basics', 1))

    //makes the entry kubejs:luminous_blades get created, which depends on stage 1 of the previous one
    event.create('kubejs:luminous_blades')
        .category('kubejs:radiance')
        .name('research.kubejs.luminous_blades.title')
        .position(2, 1)
        .parent('kubejs:luminous_basics@1')
        .icon('minecraft:golden_sword')
        .complexity(2)
        //requires crafting a golden sword and adds 1 warp
        .stage(stage => stage
            .text('research.kubejs.luminous_blades.stage_0')
            .craft('minecraft:golden_sword', 1)
            .warp(1))
        //addendum that requires having completed kubejs:luminous_basics
        .addendum(addendum => addendum
            .text('research.kubejs.luminous_blades.addendum_0')
            .requiredResearch('kubejs:luminous_basics'))

    //makes the entry thaumaturge:alumentum change to complexity 1
    event.modify('thaumaturge:alumentum', entry => entry.complexity(1))
})

//client_scripts: English texts for the research
ClientEvents.lang('en_us', event => {
    event.add('research.kubejs.luminous_basics.title', 'Luminous Basics')
    event.add('research.kubejs.luminous_basics.stage_0', 'Gather glowstone and observe how it holds light.')
    event.add('research.kubejs.luminous_basics.stage_1', 'Glowstone can be infused to hold a blade of radiance.')
    event.add('research.kubejs.luminous_blades.title', 'Luminous Blades')
    event.add('research.kubejs.luminous_blades.stage_0', 'Forge a golden sword to carry the radiance.')
    event.add('research.kubejs.luminous_blades.addendum_0', 'The blade glows brighter near glowstone.')
})
```

#### Hand-written Data (`ThaumaturgeEvents.data`)

`event.add(registry, id, json)` adds a data file as-is to any Thaumaturge registry that has no event of its own.

```js
ThaumaturgeEvents.data(event => {
    //makes an instability outcome get added that gives a zap of 4 to 7 damage with weight 2
    event.add('instability_outcome', 'kubejs:shocking_failure', {
        effect: {
            type: 'thaumaturge:zap',
            damage: { type: 'minecraft:uniform', min_inclusive: 4, max_inclusive: 7 }
        },
        weight: 2
    })
})
```

#### Thaumaturge Recipes

Common options: `.research(research)` (the recipe is only visible with that research), `.crystals([extra crystals])` (up to 6 primary aspects), `.group(group)`.

A research is written `'id'`, `'id@stage'` (for example `'thaumaturge:unlock_alchemy@0'`) or `'!id'` to negate it. Without a namespace, `thaumaturge:` is used.

Aliases: `thaumaturgeArcaneShaped`, `thaumaturgeArcaneShapeless`, `thaumaturgeCrucible`, `thaumaturgeInfusion`, `thaumaturgeInfusionEnchantment`, `thaumaturgeRunicAugment`, `thaumaturgeDustTrigger`, `thaumaturgeDustTriggerTag`, `thaumaturgeDustTriggerMultiblock`.

| Recipe | Call | Arguments |
| --- | --- | --- |
| Shaped arcane workbench | `arcane_workbench_shaped(result, rows, keys, vis)` | result; rows (list of texts); keys (object mapping letter to ingredient); vis (integer greater than 0) |
| Shapeless arcane workbench | `arcane_workbench_shapeless(result, [ingredients], vis)` | 1 to 9 ingredients |
| Crucible | `crucible(result, catalyst, [aspects])` | catalyst (ingredient); aspects with amount |
| Infusion | `infusion(result, catalyst, [components], [aspects])` | 1 to 64 components. Options `.instability(0 to 100)` and `.catalystPatch({components})` |
| Infusion (the catalyst stays) | `infusion(catalyst, [components], [aspects])` | no result: the catalyst receives the `.catalystPatch` patch |
| Infusion enchantment | `infusion_enchantment(enchantment, [components], [aspects], exampleItem)` | the example item is only displayed |
| Runic augment | `runic_augment([components], ingredientPerLevel, [aspects], exampleItem)` | consumes one ingredient per level |
| Dust on block | `dust_trigger_simple(result, targetBlock)` | right-click with Salis Mundus |
| Dust on tag | `dust_trigger_tag(result, blockTag)` | tag without `#` |
| Dust on multiblock | `dust_trigger_multiblock(result, blueprintId)` | the blueprint is defined in `ThaumaturgeEvents.blueprints` |

| Recipe | Input | Output | Where |
| --- | --- | --- | --- |
| `arcane_workbench_shaped` | `minecraft:charcoal` above `minecraft:stick`, 15 vis, `2x ignis` crystals, research `thaumaturge:first_steps` | 4x `minecraft:torch` | Arcane workbench |
| `arcane_workbench_shapeless` | `minecraft:torch` + `minecraft:iron_nugget`, 20 vis, `ignis` and `ordo` crystals | `minecraft:lantern` | Arcane workbench |
| `crucible` | `minecraft:coal` + `5x ignis` + `5x perditio` | 3x `minecraft:gunpowder` | Crucible (research `thaumaturge:alumentum`) |
| `infusion` | `minecraft:golden_sword` in the center, `minecraft:glowstone` x2 and `glowstone_dust` on the pedestals, `50x kubejs:lux` + `30x ignis`, instability 3 | `kubejs:luminous_blade` | Infusion altar |
| `infusion` (patch) | `minecraft:iron_pickaxe` in the center, `minecraft:redstone_block` + `minecraft:diamond`, `20x fabrico` + `20x terra` | The same pickaxe, now unbreakable (`minecraft:unbreakable`); the catalyst stays | Infusion altar |
| `infusion_enchantment` | `minecraft:enchanted_book` + `#c:storage_blocks/redstone`, `40x potentia` + `60x aer` | Enchantment `arcing` | Infusion altar |
| `runic_augment` | `thaumaturge:salis_mundus` + `#c:gems/amber`, `40x praemunio` + `20x vitreus`, one amber per level | Upgraded piece (example `minecraft:iron_chestplate`) | Infusion altar |
| `dust_trigger_simple` | `minecraft:cauldron` + Salis Mundus (after `thaumaturge:unlock_alchemy@0`) | `thaumaturge:crucible` | Right-click on the cauldron |
| `dust_trigger_tag` | Any `c:bookshelves` + Salis Mundus | `thaumaturge:thaumonomicon` | Right-click on a bookshelf |
| `dust_trigger_multiblock` | Built blueprint `kubejs:lux_altar` + Salis Mundus | `minecraft:beacon` (only displayed and counted as crafted) | Right-click on any block of the structure |

```js
ServerEvents.recipes(event => {
    //gives 4x minecraft:torch by placing minecraft:charcoal above minecraft:stick in the shaped arcane workbench, spending 15 vis
    event.recipes.thaumaturge.arcane_workbench_shaped(
        '4x minecraft:torch',
        [' C', ' S'],
        { C: 'minecraft:charcoal', S: 'minecraft:stick' },
        15
    ).crystals(['2x ignis']).research('thaumaturge:first_steps')

    //gives minecraft:lantern by mixing minecraft:torch and minecraft:iron_nugget in any arrangement in the shapeless arcane workbench, spending 20 vis
    event.recipes.thaumaturge.arcane_workbench_shapeless(
        'minecraft:lantern',
        ['minecraft:torch', 'minecraft:iron_nugget'],
        20
    ).crystals(['ignis', 'ordo']).group('lanterns')

    //gives 3x minecraft:gunpowder by dissolving minecraft:coal in the crucible with 5x ignis and 5x perditio
    event.recipes.thaumaturge.crucible(
        '3x minecraft:gunpowder',
        'minecraft:coal',
        ['5x ignis', '5x perditio']
    ).research('thaumaturge:alumentum')

    //gives kubejs:luminous_blade by infusing minecraft:golden_sword (altar center) surrounded by glowstone and glowstone dust on the pedestals with 50x kubejs:lux and 30x ignis
    event.recipes.thaumaturge.infusion(
        'kubejs:luminous_blade',
        'minecraft:golden_sword',
        ['minecraft:glowstone', 'minecraft:glowstone', 'minecraft:glowstone_dust'],
        ['50x kubejs:lux', '30x ignis']
    ).instability(3).research('kubejs:luminous_blades')

    //gives an unbreakable minecraft:iron_pickaxe by infusing with minecraft:redstone_block and minecraft:diamond, 20x fabrico and 20x terra (the catalyst stays and only receives the patch)
    event.recipes.thaumaturge.infusion(
        'minecraft:iron_pickaxe',
        ['minecraft:redstone_block', 'minecraft:diamond'],
        ['20x fabrico', '20x terra']
    ).catalystPatch({ 'minecraft:unbreakable': {} })

    //gives the arcing enchantment by infusing with minecraft:enchanted_book and #c:storage_blocks/redstone, 40x potentia and 60x aer (the wooden sword is only the example that is displayed)
    event.recipes.thaumaturge.infusion_enchantment(
        'arcing',
        ['minecraft:enchanted_book', '#c:storage_blocks/redstone'],
        ['40x potentia', '60x aer'],
        'minecraft:wooden_sword'
    ).research('thaumaturge:infusion_enchantment')

    //gives an upgraded minecraft:iron_chestplate by applying thaumaturge:salis_mundus and amber, 40x praemunio and 20x vitreus, consuming one amber (#c:gems/amber) per level
    event.recipes.thaumaturge.runic_augment(
        ['thaumaturge:salis_mundus', '#c:gems/amber'],
        '#c:gems/amber',
        ['40x praemunio', '20x vitreus'],
        'minecraft:iron_chestplate'
    )

    //gives thaumaturge:crucible by applying thaumaturge:salis_mundus with a right-click on minecraft:cauldron (after researching alchemy)
    event.recipes.thaumaturge.dust_trigger_simple('thaumaturge:crucible', 'minecraft:cauldron').research('thaumaturge:unlock_alchemy@0')

    //gives thaumaturge:thaumonomicon by applying thaumaturge:salis_mundus with a right-click on any bookshelf (c:bookshelves)
    event.recipes.thaumaturge.dust_trigger_tag('thaumaturge:thaumonomicon', 'c:bookshelves')

    //gives minecraft:beacon (only the displayed item, counted as crafted) by applying thaumaturge:salis_mundus on the built blueprint kubejs:lux_altar
    event.recipes.thaumaturge.dust_trigger_multiblock('minecraft:beacon', 'kubejs:lux_altar')

    //normal crafting table recipe: thaumaturge:wand with cap kubejs:emerald and rod thaumaturge:wood
    event.shaped(
        'thaumaturge:wand[thaumaturge:wand_parts={cap:"kubejs:emerald",rod:"thaumaturge:wood"}]',
        [' C', ' S', 'C '],
        { C: 'minecraft:emerald', S: 'minecraft:stick' }
    )
})
```

For the blueprint and infusion examples to work, the item `kubejs:luminous_blade` must be registered in `startup_scripts`:

```js
StartupEvents.registry('item', event => {
    //makes the item kubejs:luminous_blade get created
    event.create('luminous_blade')
})
```

Usage notes in the game:

- **Blueprints:** build the structure and right-click any block of it with Salis Mundus (`thaumaturge:salis_mundus`). The first `layer()` is the TOP one and the last is the BOTTOM one.
- With the `dust_trigger_multiblock` recipe from the examples, activating the blueprint consumes 1 dust and, after 2.5 seconds, every glowstone turns into a sea lantern (the lodestone stays).
- Recipes with `.research(...)` only appear and can only be used when the player has that research.
- The `arcane_key_*` warnings on load come from Thaumaturge and are harmless (see the FAQ).

---

### 4.3. Mystical Agriculture

Module `compat.kubejs.modules.mysticalagriculture`. Required mod: `mysticalagriculture`.

| What | Where it is written | What for |
| --- | --- | --- |
| `event.recipes.mysticalagriculture.<type>(...)` | `ServerEvents.recipes` | Mystical Agriculture's own recipes (altars, reprocessor, soul extractor, etc.) |
| `MysticalEvents.recipes` | server | Seed recipes (crafting, infusion and reprocessing) with the mod conditions set automatically |

#### Recipes (`event.recipes.mysticalagriculture`)

| Type | Call | Arguments |
| --- | --- | --- |
| `infusion` | `infusion(result, input, [ingredients]).transferComponents(bool)` | 1 to 8 ingredients; `transferComponents` defaults to `false` |
| `awakening` | `awakening(result, input, [ingredients], [essences]).transferComponents(bool)` | 1 to 4 ingredients and 1 to 4 essences with amount |
| `enchanter` | `enchanter(enchantment, [{item, count}])` | 1 to 2 ingredients with amount |
| `reprocessor` | `reprocessor(result, input)` | |
| `soul_extraction` | `soul_extraction({type, souls}, input)` | soul type and decimal amount |
| `farmland_till` | `farmland_till(result, [ingredients], group)` | 1 to 9 ingredients; the group is optional |
| `soulium_spawner` | `soulium_spawner({item, count}, [{entity, weight}])` | item it consumes and list of entities with weight |
| `soul_jar_empty` | `soul_jar_empty()` | no arguments |

| Recipe | Input | Output | Where |
| --- | --- | --- | --- |
| `infusion` | `minecraft:iron_block` + 4x `minecraft:diamond` and 4x `mysticalagriculture:diamond_essence` alternating | `minecraft:diamond_block` | Infusion Altar |
| `infusion` without components | `minecraft:gold_ingot` + 4x `minecraft:netherite_scrap` | `minecraft:netherite_ingot` | Infusion Altar |
| `awakening` | `mysticalagriculture:supremium_block` + 4x `cognizant_dust` + 10x air, earth, water and fire essence | `mysticalagriculture:awakened_supremium_block` | Awakening Altar |
| `enchanter` | 16x `minecraft:redstone` + 64x `mysticalagriculture:experience_essence` | Enchantment `minecraft:efficiency` | Mystical Enchanter |
| `reprocessor` | `mysticalagriculture:iron_seeds` | 2x `mysticalagriculture:iron_essence` | Seed Reprocessor |
| `soul_extraction` | `minecraft:blaze_rod` | 0.5 `mysticalagriculture:blaze` soul | Soul Extractor |
| `farmland_till` | Any hoe (`mysticalagriculture:all_hoes`) + `minecraft:dirt` | `minecraft:farmland` | Crafting table |
| `soulium_spawner` | 12x `mysticalagriculture:cow_essence` | Spawns a `minecraft:cow` (weight 1) | Soulium Spawner |
| `soul_jar_empty` | Full Soul Jar | Empty Soul Jar | Crafting table (special recipe) |

```js
ServerEvents.recipes(event => {
    //gives minecraft:diamond_block by infusing minecraft:iron_block with 4 minecraft:diamond and 4 mysticalagriculture:diamond_essence alternating in the Infusion Altar
    event.recipes.mysticalagriculture.infusion('minecraft:diamond_block', 'minecraft:iron_block', [
        'minecraft:diamond', 'mysticalagriculture:diamond_essence',
        'minecraft:diamond', 'mysticalagriculture:diamond_essence',
        'minecraft:diamond', 'mysticalagriculture:diamond_essence',
        'minecraft:diamond', 'mysticalagriculture:diamond_essence'
    ])

    //gives minecraft:netherite_ingot by infusing minecraft:gold_ingot with 4 minecraft:netherite_scrap in the Infusion Altar, without copying the item's components
    event.recipes.mysticalagriculture.infusion('minecraft:netherite_ingot', 'minecraft:gold_ingot', [
        'minecraft:netherite_scrap', 'minecraft:netherite_scrap',
        'minecraft:netherite_scrap', 'minecraft:netherite_scrap'
    ]).transferComponents(false)

    //gives mysticalagriculture:awakened_supremium_block by awakening supremium_block with 4 cognizant_dust and 10 of each essence (air, earth, water, fire) in the Awakening Altar
    event.recipes.mysticalagriculture.awakening(
        'mysticalagriculture:awakened_supremium_block',
        'mysticalagriculture:supremium_block',
        ['mysticalagriculture:cognizant_dust', 'mysticalagriculture:cognizant_dust', 'mysticalagriculture:cognizant_dust', 'mysticalagriculture:cognizant_dust'],
        ['10x mysticalagriculture:air_essence', '10x mysticalagriculture:earth_essence', '10x mysticalagriculture:water_essence', '10x mysticalagriculture:fire_essence']
    )

    //gives the enchantment minecraft:efficiency by consuming 16 minecraft:redstone and 64 mysticalagriculture:experience_essence in the Mystical Enchanter
    event.recipes.mysticalagriculture.enchanter('minecraft:efficiency', [
        { item: 'minecraft:redstone', count: 16 },
        { item: 'mysticalagriculture:experience_essence', count: 64 }
    ])

    //gives 2x mysticalagriculture:iron_essence by reprocessing mysticalagriculture:iron_seeds in the Seed Reprocessor
    event.recipes.mysticalagriculture.reprocessor('2x mysticalagriculture:iron_essence', 'mysticalagriculture:iron_seeds')

    //gives 0.5 of a soul of type mysticalagriculture:blaze by extracting it from minecraft:blaze_rod in the Soul Extractor
    event.recipes.mysticalagriculture.soul_extraction({ type: 'mysticalagriculture:blaze', souls: 0.5 }, 'minecraft:blaze_rod')

    //gives minecraft:farmland by combining any hoe (mysticalagriculture:all_hoes) with minecraft:dirt in the crafting table
    event.recipes.mysticalagriculture.farmland_till('minecraft:farmland', [{ type: 'mysticalagriculture:all_hoes' }, 'minecraft:dirt'])

    //spawns a minecraft:cow (weight 1) by spending 12 mysticalagriculture:cow_essence in the Soulium Spawner
    event.recipes.mysticalagriculture.soulium_spawner({ item: 'mysticalagriculture:cow_essence', count: 12 }, [
        { entity: 'minecraft:cow', weight: 1 }
    ])

    //turns a full Soul Jar into an empty one (special recipe with no arguments)
    event.recipes.mysticalagriculture.soul_jar_empty()
})
```

#### Seed Recipes (`MysticalEvents.recipes`)

| Method | Arguments | What it does |
| --- | --- | --- |
| `event.seed(seeds, seedBase, essence, material)` | `seeds` accepts `'Nx namespace:item'` | Creates the seeds' crafting recipe **and** infusion recipe |
| `event.seedCrafting(seeds, seedBase, essence, material)` | | Crafting only, in a shaped table recipe `MEM / ESE / MEM` (`M` material, `E` essence, `S` seed base) |
| `event.seedInfusion(seeds, seedBase, essence, material)` | | Infusion only: the base in the center of the Infusion Altar and 4 alternating pairs of material and essence |
| `event.seedReprocessing(seeds, essence)` | `essence` accepts `'Nx namespace:item'` | Seed Reprocessor: from seeds to essence |

The recipes are created with the necessary `neoforge:mod_loaded` conditions automatically: one is added for each mod of the items that is not `minecraft`, `neoforge`, `c` or `mysticalagriculture`, and for the seeds of a crop in the UMC catalog (section 5) also the mods that crop requires. The generated ids are `umc:mystical/<seed_crafting|seed_infusion|seed_reprocessing>/<namespace>/<item>`.

| Recipe | Input | Output | Where |
| --- | --- | --- | --- |
| `seed` | `prosperity_seed_base` + `inferium_essence` + `minecraft:iron_ingot` | `mysticalagriculture:thaumaturge_thaumium_seeds` | Crafting table and Infusion Altar |
| `seedCrafting` | `prosperity_seed_base` + `inferium_essence` + `#c:ingots/copper` | `mysticalagriculture:copper_seeds` | Crafting table |
| `seedInfusion` | `prosperity_seed_base` + 4 pairs of `#c:ingots/copper` and `inferium_essence` | `mysticalagriculture:copper_seeds` | Infusion Altar |
| `seedReprocessing` | `mysticalagriculture:copper_seeds` | 2x `mysticalagriculture:copper_essence` | Seed Reprocessor |

```js
MysticalEvents.recipes(event => {
    //gives mysticalagriculture:thaumaturge_thaumium_seeds (by crafting and infusion) by mixing prosperity_seed_base with inferium_essence and minecraft:iron_ingot; carries mod_loaded thaumaturge if the catalog requires it
    event.seed(
        'mysticalagriculture:thaumaturge_thaumium_seeds',
        'mysticalagriculture:prosperity_seed_base',
        'mysticalagriculture:inferium_essence',
        'minecraft:iron_ingot'
    )

    //gives mysticalagriculture:copper_seeds in the crafting table (shape MEM/ESE/MEM) with prosperity_seed_base, inferium_essence and copper ingots #c:ingots/copper
    event.seedCrafting(
        'mysticalagriculture:copper_seeds',
        'mysticalagriculture:prosperity_seed_base',
        'mysticalagriculture:inferium_essence',
        '#c:ingots/copper'
    )

    //gives mysticalagriculture:copper_seeds by infusing prosperity_seed_base with 4 alternating pairs of #c:ingots/copper and inferium_essence in the Infusion Altar
    event.seedInfusion(
        'mysticalagriculture:copper_seeds',
        'mysticalagriculture:prosperity_seed_base',
        'mysticalagriculture:inferium_essence',
        '#c:ingots/copper'
    )

    //gives 2x mysticalagriculture:copper_essence by reprocessing mysticalagriculture:copper_seeds in the Seed Reprocessor
    event.seedReprocessing('mysticalagriculture:copper_seeds', '2x mysticalagriculture:copper_essence')
})
```

---

### 4.4. Applied Energistics 2 (AE2)

Module `compat.kubejs.modules.ae2`. Required mod: `ae2`. All recipes go in `ServerEvents.recipes` with `event.recipes.ae2.<type>(...)`.

| Type | Call | Arguments |
| --- | --- | --- |
| `inscriber` | `inscriber(result, middle, top, bottom)` | `top` and `bottom` are optional (`inscriber(result, middle)` and `inscriber(result, middle, top)` are also valid). Finish with `.press()` or `.inscribe()` |
| `transform` | `transform(result, [ingredients])` | By default, thrown into water. Options: `.explosion()`, `.fluid(fluidTag)` (with or without `#`), `.water()`, `.lava()` |
| `charger` | `charger(result, ingredient)` | |
| `matter_cannon` | `matter_cannon(ammo, weight)` | `ammo` is an item or tag; `weight` is a decimal (atomic weight) |
| `entropy` | `entropy(mode)` | `mode`: `'heat'` or `'cool'`. Chain `.inputBlock(block)` or `.inputFluid(fluid)`, and `.outputBlock(block)`, `.outputFluid(fluid)` or `.drop(item)` |
| `storage_cell_upgrade` | `storage_cell_upgrade(inputCell, inputComponent, outputCell, outputComponent)` | Four item ids |
| `storage_cell_disassembly` | `storage_cell_disassembly(cell, [returnedItems])` | |
| `crafting_unit_transform` | `crafting_unit_transform(usedItem, resultBlock)` | |
| `quartz_cutting` | `quartz_cutting(result, [ingredients])` | 1 to 9 ingredients |

In addition, `add_item_upgrade`, `remove_item_upgrade` and `facade` are registered with no arguments, so that KubeJS does not throw errors on the recipes AE2 already ships of those types.

| Recipe | Input | Output | Where |
| --- | --- | --- | --- |
| `inscriber` + `.press()` | `minecraft:redstone` in the middle, `ae2:printed_calculation_processor` on top and `ae2:printed_silicon` on the bottom | `ae2:calculation_processor` | Inscriber |
| `inscriber` + `.inscribe()` | `minecraft:iron_block` (consumed) with `ae2:logic_processor_press` on top (kept) | `ae2:logic_processor_press` | Inscriber |
| `transform` (water) | `ae2:charged_certus_quartz_crystal` + `ae2:flawed_budding_quartz` | `ae2:flawless_budding_quartz` | Thrown into water for about 3 seconds without drifting away |
| `transform` + `.explosion()` | `ae2:charged_certus_quartz_crystal` + `minecraft:coal_block` | 2x `minecraft:diamond` | Thrown on the ground with a TNT explosion next to them (it does not destroy them) |
| `transform` + `.fluid('minecraft:lava')` | `minecraft:netherite_ingot` + `minecraft:netherite_scrap` | `minecraft:netherite_block` | Thrown into lava for about 3 seconds (fireproof items only) |
| `charger` | `minecraft:book` | `ae2:guide` | Powered AE2 Charger |
| `matter_cannon` | `#c:nuggets/iron` | Matter cannon ammo with weight 55.845 (the higher the weight, the more damage) | Matter Cannon |
| `entropy('cool')` | `minecraft:stone` | `minecraft:cobblestone` | Entropy Manipulator (cool) |
| `entropy('heat')` | `minecraft:ice` | `minecraft:water` | Entropy Manipulator (heat) |
| `entropy('cool')` + `.inputFluid().drop()` | `minecraft:flowing_water` | `minecraft:snowball` (drops as an item) | Entropy Manipulator (cool) |
| `storage_cell_upgrade` | `ae2:item_storage_cell_4k` + `ae2:cell_component_64k` | `ae2:item_storage_cell_64k` and returns `ae2:cell_component_4k` | Crafting table |
| `storage_cell_disassembly` | Empty `ae2:item_storage_cell_4k` | `ae2:item_cell_housing` + `ae2:cell_component_4k` | Used in hand while sneaking |
| `crafting_unit_transform` | `ae2:cell_component_16k` | `ae2:16k_crafting_storage` | Used on a crafting unit |
| `quartz_cutting` | `#ae2:knife` (wears down, not consumed) + `#ae2:metal_ingots` | 4x `ae2:cable_anchor` | Crafting grid |

```js
ServerEvents.recipes(event => {
    //gives ae2:calculation_processor by pressing minecraft:redstone with ae2:printed_calculation_processor on top and ae2:printed_silicon on the bottom in the inscriber
    event.recipes.ae2.inscriber('ae2:calculation_processor', 'minecraft:redstone', 'ae2:printed_calculation_processor', 'ae2:printed_silicon')
        .press()
        .id('umc_ejemplos:ae2/inscriber_procesador')

    //gives ae2:logic_processor_press by inscribing minecraft:iron_block (consumed) with ae2:logic_processor_press on top (kept) in the inscriber
    event.recipes.ae2.inscriber('ae2:logic_processor_press', 'minecraft:iron_block', 'ae2:logic_processor_press')
        .inscribe()
        .id('umc_ejemplos:ae2/inscriber_molde')

    //gives ae2:flawless_budding_quartz by mixing ae2:charged_certus_quartz_crystal and ae2:flawed_budding_quartz thrown into water for about 3 seconds without drifting away
    event.recipes.ae2.transform('ae2:flawless_budding_quartz', ['ae2:charged_certus_quartz_crystal', 'ae2:flawed_budding_quartz'])
        .id('umc_ejemplos:ae2/transform_agua')

    //gives 2x minecraft:diamond by mixing ae2:charged_certus_quartz_crystal and minecraft:coal_block thrown on the ground with a TNT explosion next to them (it does not destroy them)
    event.recipes.ae2.transform('2x minecraft:diamond', ['ae2:charged_certus_quartz_crystal', 'minecraft:coal_block'])
        .explosion()
        .id('umc_ejemplos:ae2/transform_explosion')

    //gives minecraft:netherite_block by mixing minecraft:netherite_ingot and minecraft:netherite_scrap thrown into lava for about 3 seconds (fireproof items only)
    event.recipes.ae2.transform('minecraft:netherite_block', ['minecraft:netherite_ingot', 'minecraft:netherite_scrap'])
        .fluid('minecraft:lava')
        .id('umc_ejemplos:ae2/transform_lava')

    //gives ae2:guide by charging minecraft:book in the powered AE2 charger
    event.recipes.ae2.charger('ae2:guide', 'minecraft:book')
        .id('umc_ejemplos:ae2/charger_guia')

    //makes iron nuggets (#c:nuggets/iron) work as matter cannon ammo with atomic weight 55.845 (the higher the weight, the more damage)
    event.recipes.ae2.matter_cannon('#c:nuggets/iron', 55.845)
        .id('umc_ejemplos:ae2/municion_hierro')

    //turns minecraft:stone into minecraft:cobblestone by cooling with the entropy manipulator
    event.recipes.ae2.entropy('cool')
        .inputBlock('minecraft:stone')
        .outputBlock('minecraft:cobblestone')
        .id('umc_ejemplos:ae2/entropia_piedra')

    //turns minecraft:ice into minecraft:water by heating with the entropy manipulator
    event.recipes.ae2.entropy('heat')
        .inputBlock('minecraft:ice')
        .outputFluid('minecraft:water')
        .id('umc_ejemplos:ae2/entropia_hielo')

    //turns minecraft:flowing_water into minecraft:snowball (drops as an item) by cooling with the entropy manipulator
    event.recipes.ae2.entropy('cool')
        .inputFluid('minecraft:flowing_water')
        .drop('minecraft:snowball')
        .id('umc_ejemplos:ae2/entropia_nieve')

    //gives ae2:item_storage_cell_64k and returns ae2:cell_component_4k by combining ae2:item_storage_cell_4k with ae2:cell_component_64k in a crafting table
    event.recipes.ae2.storage_cell_upgrade('ae2:item_storage_cell_4k', 'ae2:cell_component_64k', 'ae2:item_storage_cell_64k', 'ae2:cell_component_4k')
        .id('umc_ejemplos:ae2/mejora_celda')

    //gives ae2:item_cell_housing and ae2:cell_component_4k by disassembling an empty ae2:item_storage_cell_4k used in hand while sneaking
    event.recipes.ae2.storage_cell_disassembly('ae2:item_storage_cell_4k', ['ae2:item_cell_housing', 'ae2:cell_component_4k'])
        .id('umc_ejemplos:ae2/desmontar_celda')

    //gives ae2:16k_crafting_storage by using ae2:cell_component_16k on a crafting unit
    event.recipes.ae2.crafting_unit_transform('ae2:cell_component_16k', 'ae2:16k_crafting_storage')
        .id('umc_ejemplos:ae2/unidad_crafteo')

    //gives 4x ae2:cable_anchor by cutting with the AE2 knife (#ae2:knife, wears down but is not consumed) and a metal ingot (#ae2:metal_ingots) in the crafting grid
    event.recipes.ae2.quartz_cutting('4x ae2:cable_anchor', ['#ae2:knife', '#ae2:metal_ingots'])
        .id('umc_ejemplos:ae2/corte_cuarzo')
})
```

Usage notes in the game:

- **Transform in lava:** needs **fireproof** items (netherite) and about **3 seconds** of waiting inside the lava; items that are not fireproof burn first. When the script loads, UMC warns in the KubeJS log if you use `.fluid('minecraft:lava')` or `.lava()`.
- **Transform with an explosion:** the items must be thrown on the ground; they are not destroyed by the explosion.
- **Entropy:** `'cool'` and `'heat'` are the two modes of the Entropy Manipulator (cooling and heating).
- **`storage_cell_disassembly`:** you have to use the empty cell in hand while sneaking.

---

### 4.5. Advanced AE

Module `compat.kubejs.modules.advanced_ae` (with an underscore). Required mod: `advanced_ae`.

| Type | Call | Arguments |
| --- | --- | --- |
| `reaction` | `event.recipes.advanced_ae.reaction(result, [ingredients], fluid, energy)` | `result`: item with amount (`'64x ae2:...'`); `ingredients`: list of items with amount; `fluid`: `Fluid.of(id, mB)`; `energy`: integer in FE. Method aliases: `.inputItems()`/`.items()`, `.inputFluid()`/`.fluid()`, `.inputEnergy()`/`.energy()` |

| Input | Output | Where |
| --- | --- | --- |
| 64x `ae2:certus_quartz_crystal` + 1000 mB of water + 1,300,000 FE | 64x `ae2:charged_certus_quartz_crystal` | Reaction Chamber |
| 4x `minecraft:copper_ingot` + 4x `advanced_ae:shattered_singularity` + 4x `ae2:singularity` + 1000 mB of `advanced_ae:quantum_infusion_source` + 200,000 FE | 1x `advanced_ae:quantum_alloy` | Reaction Chamber |
| `advanced_ae:quantum_infused_dust` + 4000 mB of water + 20,000 FE | 1000x `advanced_ae:quantum_infusion_source` | Reaction Chamber |

```js
ServerEvents.recipes(event => {
    //gives 64x ae2:charged_certus_quartz_crystal by mixing 64x ae2:certus_quartz_crystal with 1000 mB of water and 1300000 energy (FE), in the Reaction Chamber
    event.recipes.advanced_ae.reaction(
        '64x ae2:charged_certus_quartz_crystal',
        ['64x ae2:certus_quartz_crystal'],
        Fluid.of('minecraft:water', 1000),
        1300000
    ).id('umc_ejemplos:advancedae/cuarzo_cargado')

    //gives 1x advanced_ae:quantum_alloy by mixing 4x copper, 4x shattered_singularity and 4x singularity with 1000 mB of quantum_infusion and 200000 energy (FE), in the Reaction Chamber
    event.recipes.advanced_ae.reaction(
        'advanced_ae:quantum_alloy',
        ['4x minecraft:copper_ingot', '4x advanced_ae:shattered_singularity', '4x ae2:singularity'],
        Fluid.of('advanced_ae:quantum_infusion_source', 1000),
        200000
    ).id('umc_ejemplos:advancedae/aleacion_cuantica')

    //gives 1000x advanced_ae:quantum_infusion_source by mixing advanced_ae:quantum_infused_dust with 4000 mB of water and 20000 energy (FE), in the Reaction Chamber
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

Module `compat.kubejs.modules.extendedae`. Required mod: `extendedae`.

| Type | Call | Arguments |
| --- | --- | --- |
| `crystal_assembler` | `crystal_assembler(result, [ingredients], fluid)` | `ingredients`: items or tags with amount (`'4x #c:dusts/entro'`); `fluid`: `Fluid.of(id, mB)`, **optional** |
| `circuit_cutter` | `circuit_cutter(result, ingredient)` | `ingredient` with amount |
| `crystal_fixer` | `crystal_fixer(result, input, fuel, chance)` | `chance`: integer from 1 to 10000 (8000 is 80%) |

| Recipe | Input | Output | Where |
| --- | --- | --- | --- |
| `crystal_assembler` with fluid | 4x `#c:dusts/entro` + 4x `#c:ingots/gold` + 4x `minecraft:lapis_lazuli` + 100 mB of water | 4x `extendedae:entro_ingot` | Crystal Assembler |
| `crystal_assembler` without fluid | `extendedae:machine_frame` + 8x `ae2:engineering_processor` + `minecraft:stonecutter` | `extendedae:circuit_cutter` | Crystal Assembler |
| `circuit_cutter` | 1x `ae2:fluix_block` | 9x `ae2:printed_silicon` | Circuit Slicer |
| `crystal_fixer` | `ae2:chipped_budding_quartz` with `ae2:charged_certus_quartz_crystal` as fuel, 80% chance | `ae2:flawed_budding_quartz` | Crystal Fixer |

```js
ServerEvents.recipes(event => {
    //gives 4x extendedae:entro_ingot by mixing 4x entro dust, 4x gold ingot and 4x lapis lazuli with 100 mB of water, in the Crystal Assembler
    event.recipes.extendedae.crystal_assembler(
        '4x extendedae:entro_ingot',
        ['4x #c:dusts/entro', '4x #c:ingots/gold', '4x minecraft:lapis_lazuli'],
        Fluid.of('minecraft:water', 100)
    ).id('umc_ejemplos:extendedae/lingote_entro')

    //gives extendedae:circuit_cutter by mixing extendedae:machine_frame, 8x ae2:engineering_processor and minecraft:stonecutter without fluid, in the Crystal Assembler
    event.recipes.extendedae.crystal_assembler(
        'extendedae:circuit_cutter',
        ['extendedae:machine_frame', '8x ae2:engineering_processor', 'minecraft:stonecutter']
    ).id('umc_ejemplos:extendedae/cortador_circuitos')

    //gives 9x ae2:printed_silicon by cutting 1x ae2:fluix_block, in the Circuit Slicer
    event.recipes.extendedae.circuit_cutter('9x ae2:printed_silicon', 'ae2:fluix_block')
        .id('umc_ejemplos:extendedae/corte_acumulacion')

    //turns ae2:chipped_budding_quartz into ae2:flawed_budding_quartz by spending ae2:charged_certus_quartz_crystal as fuel, with an 8000 out of 10000 (80%) chance, in the Crystal Fixer
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

Module `compat.kubejs.modules.create`. Required mod: `create`.

| Type | Call | Arguments |
| --- | --- | --- |
| `item_application` (alias `itemApplication`) | `item_application(result, [blockInWorld, itemInHand])` | Exactly 2 ingredients: first the block (or tag) that is clicked, then the item in hand. Option `.keepHeldItem()`: does not consume the item in hand |
| `conversion` (alias `mysteriousConversion`) | `conversion(result, input)` | One result and one input. Options `.haunting()` and `.entity(entity)` |
| `haunting` | `haunting(result, input)` | Haunting with a fan. Up to 12 results |

`conversion` and `haunting` are registered by UMC **only if the KubeJS Create addon (`kubejs_create`) is not installed**; if it is, that addon provides them. `item_application` and UMC's aliases are always registered.

| Recipe | Input | Output | In the game |
| --- | --- | --- | --- |
| `item_application` | `#c:stripped_logs` + `create:andesite_alloy` in hand | `create:andesite_casing` | Alloy in hand and right-click on the stripped log |
| `item_application` + `.keepHeldItem()` | `#c:stripped_logs` + `create:brass_ingot` in hand | `create:brass_casing` | Right-click with the ingot; the ingot is not consumed |
| `itemApplication` (alias) | `#c:stripped_logs` + `minecraft:copper_ingot` | `create:copper_casing` | Right-click with the ingot |
| `conversion` + `.haunting()` | `minecraft:emerald` | `minecraft:amethyst_shard` | Shown in JEI and works in the game with a fan (Encased Fan) behind soul fire |
| `mysteriousConversion` + `.haunting()` | `minecraft:grass_block` | `minecraft:glowstone` | Throw the block on the ground and blow a fan's air through soul fire; nothing is clicked |
| `haunting` | `minecraft:emerald` | `minecraft:amethyst_shard` | Fan (Encased Fan) behind soul fire |
| `conversion` + `.entity('minecraft:pig')` | `minecraft:coal` | `minecraft:diamond` | Right-click a pig with the coal in hand |

What each `conversion` option does:

| Chain | What it does |
| --- | --- |
| Nothing | The conversion is **only shown in JEI** (Create's "Mysterious Conversion" category). Create does not run generic conversions, so nothing happens in the game |
| `.haunting()` | Also creates a `create:haunting` recipe with the same content (id with the suffix `_haunting`). It now works in the game with the fan behind soul fire |
| `.entity('minecraft:pig')` | Works like the blaze burner (item + entity): right-click that entity with the input in hand (main hand), it consumes 1 item (not in creative) and gives the result. JEI shows the text "Right-click on: Pig" |

```js
ServerEvents.recipes(event => {
    //gives create:andesite_casing by applying create:andesite_alloy on a stripped log; in the game: alloy in hand and right-click on the log
    event.recipes.create.item_application('create:andesite_casing', ['#c:stripped_logs', 'create:andesite_alloy'])
        .id('umc_ejemplos:create/casing_andamio')

    //gives create:brass_casing by applying create:brass_ingot on a stripped log without consuming the ingot (keepHeldItem); in the game: right-click with the ingot
    event.recipes.create.item_application('create:brass_casing', ['#c:stripped_logs', 'create:brass_ingot'])
        .keepHeldItem()
        .id('umc_ejemplos:create/casing_laton_conserva')

    //gives create:copper_casing by applying minecraft:copper_ingot on a stripped log (camelCase alias); in the game: right-click with the ingot
    event.recipes.itemApplication('create:copper_casing', ['#c:stripped_logs', 'minecraft:copper_ingot'])
        .id('umc_ejemplos:create/casing_cobre_alias')

    //turns minecraft:emerald into minecraft:amethyst_shard: it shows in JEI (Mysterious Conversion) and with haunting() it also works in the game (fan behind soul fire)
    //note: without haunting() it is only shown in JEI, Create does not run generic conversions
    event.recipes.create.conversion('minecraft:amethyst_shard', 'minecraft:emerald')
        .id('umc_ejemplos:create/conversion_esmeralda_amatista')
        .haunting()

    //turns minecraft:grass_block into minecraft:glowstone (camelCase alias of conversion): it shows in JEI and with haunting() it works in the game
    //note: in the game you throw the grass block on the ground and blow the air of an Encased Fan (fan) through soul fire; nothing is clicked
    event.recipes.mysteriousConversion('minecraft:glowstone', 'minecraft:grass_block')
        .id('umc_ejemplos:create/conversion_hierba_piedra_luminosa')
        .haunting()

    //gives minecraft:amethyst_shard by haunting minecraft:emerald with a fan (Encased Fan) behind soul fire
    event.recipes.create.haunting('minecraft:amethyst_shard', 'minecraft:emerald')
        .id('umc_ejemplos:create/haunting_esmeralda_amatista')

    //gives minecraft:diamond by converting minecraft:coal when you right-click a pig with the coal in hand, like the blaze burner (item + entity); it also shows in JEI (Mysterious Conversion)
    event.recipes.create.conversion('minecraft:diamond', 'minecraft:coal')
        .id('umc_ejemplos:create/conversion_carbon_diamante')
        .entity('minecraft:pig')
})
```

Usage notes in the game:

- **The mysterious conversion is only shown in JEI** unless `.haunting()` or `.entity('minecraft:pig')` is chained. Without them it is purely informational.
- With `.entity(...)`, JEI shows the text **"Right-click on: Pig"** in that recipe (the name of the entity you set). The list of conversions with entities is synced from the server to the client when joining or reloading data.
- Entity conversion only works with the **main hand**.
- If you disable `compat.kubejs.modules.create`, right-clicking on entities is disabled too.

---

### 4.8. Generator Galore

Module `compat.kubejs.modules.generatorgalore`. Required mod: `generatorgalore`.

Adds the global object `GeneratorGalore`, which is used inside `ServerEvents.generateData('after_mods', ...)`. It generates Generator Galore's fuel *data maps*: `solid_fuel_map` and `fluid_fuel_map`. They are written with `replace = false`, so they are **added** to the mod's own instead of replacing them.

| Call | Arguments | What it does |
| --- | --- | --- |
| `GeneratorGalore.fuels()` | | Starts the definition. Nothing is written until `.write(event)` is called |
| `.solid(generatorId)` | generator id | Selects a solid fuel generator |
| `.fuel(item or #tag, burnTicks, FEPerTick, consumptionSpeed)` | after `.solid` | Solid fuel. The consumption speed is optional (1 by default) |
| `.fluid(generatorId)` | generator id | Selects a liquid fuel generator |
| `.fuel(fluid or #tag, FEPerTick, mBPerTickConsumed)` | after `.fluid` | Liquid fuel. The last one is optional (1 by default) |
| `.done()` | | Closes that generator's list so you can select another |
| `.write(event)` | | Writes the two data maps |

| Generator | Fuel (input) | Output |
| --- | --- | --- |
| `generatorgalore:iron_generator` (solid) | 1x `minecraft:coal` | 40 FE/t for 1600 ticks |
| `generatorgalore:iron_generator` (solid) | Any `#minecraft:logs` | 20 FE/t for 300 ticks |
| `generatorgalore:ender_generator` (solid) | 1x `minecraft:ender_pearl` | 96 FE/t for 1600 ticks |
| `generatorgalore:ender_generator` (solid) | 1x `minecraft:ender_eye` | 80 FE/t for 3200 ticks |
| `generatorgalore:magmatic_generator` (liquid) | Any fluid in `#c:lava`, 0.4 mB/t | 40 FE/t |

```js
ServerEvents.generateData('after_mods', event => {
    GeneratorGalore.fuels()
        //the given generator accepts solid fuels
        .solid('generatorgalore:iron_generator')
        //40 FE/t for 1600 ticks burning 1 minecraft:coal, consumption speed 1
        .fuel('minecraft:coal', 1600, 40, 1)
        //20 FE/t for 300 ticks burning any #minecraft:logs (consumption speed 1 by default)
        .fuel('#minecraft:logs', 300, 20)
        .done()
        .solid('generatorgalore:ender_generator')
        .fuel('minecraft:ender_pearl', 1600, 96, 1)
        .fuel('minecraft:ender_eye', 3200, 80, 1)
        .done()
        //liquid fuels
        .fluid('generatorgalore:magmatic_generator')
        //40 FE/t consuming 0.4 mB/t of any fluid in the tag #c:lava
        .fuel('#c:lava', 40, 0.4)
        .done()
        //writes the two data maps (solid and liquid) to the generated data pack
        .write(event)
})
```

---

### 4.9. Excessive Utilities

Module `compat.kubejs.modules.excessive_utilities`. Required mod: `excessive_utilities`. Recipes with `event.recipes.excessive_utilities.<type>(...)` in `ServerEvents.recipes`.

#### Machines

| Type | Call | Arguments |
| --- | --- | --- |
| `crusher` | `crusher(result, ingredient[, secondaryResult, chance])` | `chance` from 0 to 1. Options `.ticks(n)` (200 by default) and `.fePerTick(n)` (20 by default) |
| `enchanter` | `enchanter(result, left, right, fePerTick, ticks)` | `left` and `right` with amount. Option `.enchantingPower(power)` (15 by default) |
| `qed` | `qed(result, [pattern], {letter: ingredient}, crystalTicks)` | Vertical pattern of letters |
| `resonator` | `resonator(result, ingredient, GPCost)` | `GPCost`: Grid Power |
| `world_interaction_item` | `world_interaction_item(result, blockBelow, [adjacentBlocks])` | Option `.behind(block)` |
| `world_interaction_fluid` | `world_interaction_fluid(resultFluid, blockBelow, [adjacentBlocks])` | Option `.behind(block)` |

#### Generator Fuels

| Type | Call | Arguments |
| --- | --- | --- |
| `single_item_fuel` | `single_item_fuel(generatorType, ingredient, fePerTick, durationTicks)` | `generatorType`: `ender`, `explosive`, `pink`, `nether_star`, `frosty`, `halitosis` or `death` |
| `item_and_fluid_fuel` | `item_and_fluid_fuel(generatorType, ingredient, fluid, fePerTick, durationTicks)` | `generatorType`: `slimy` or `heated_redstone` |
| `magmatic_fuel` | `magmatic_fuel(fluid, fePerTick, durationTicks)` | |

#### Special Crafting Recipes

| Type | Call | Arguments |
| --- | --- | --- |
| `shaped_division` | `shaped_division(result, [pattern], {letter: ingredient}).inversion(mode)` | `mode`: `inverted`, `uninverted` or `either` (default) depending on the state of the Division Sigil |
| `shaped_unstable` | `shaped_unstable(result, [pattern], {letter: ingredient}).stability(mode)` | `mode`: `stable`, `unstable` or `either` (default) |
| `keep_paintbrush` | `keep_paintbrush(result, [pattern], {letter: ingredient})` | The Paintbrush is not consumed |
| `damage_glass_cutter` | `damage_glass_cutter(result, [pattern], {letter: ingredient})` | The Glass Cutter loses durability |
| `snow_globe` | `snow_globe(result, [pattern], {letter: ingredient})` | Needs an already activated Magical Snow Globe |

| Recipe | Input | Output | Where |
| --- | --- | --- | --- |
| `crusher` (simple) | `minecraft:allium` | 2x `minecraft:magenta_dye` | Crusher (200 ticks and 20 FE/t) |
| `crusher` (full) | `minecraft:cobblestone` | `minecraft:gravel` and `minecraft:flint` at 25% | Crusher (100 ticks, 40 FE/t) |
| `enchanter` | 1x `#c:storage_blocks/gold` + 9x `#c:gems/lapis`, 40 FE/t, 600 ticks | `excessive_utilities:block_of_enchanted_metal` | Enchanter |
| `qed` | Ender pearl (`E`) on top of 2x `excessive_utilities:ender_infused_obsidian` (`O`) vertically, 300 crystal ticks | `excessive_utilities:ender_marker` | QED |
| `resonator` | `minecraft:iron_bars`, 16 Grid Power | `excessive_utilities:heating_coil` | Resonator |
| `world_interaction_item` | `minecraft:soul_soil` below, `minecraft:blue_ice` and `minecraft:lava` on the sides | `minecraft:basalt` | World Interaction Upgrade |
| `world_interaction_item` + `.behind` | Any `#c:stones` below, `minecraft:water` and `minecraft:lava` on the sides and `minecraft:water` behind | `minecraft:stone` | World Interaction Upgrade |
| `world_interaction_fluid` | `minecraft:water` below with two `minecraft:water` on the sides | 1 mB of `minecraft:water` | World Interaction Upgrade |
| `single_item_fuel` | 1x `#c:bones` | 40 FE/t for 400 ticks | Death Generator |
| `item_and_fluid_fuel` | `#c:dusts/redstone` + 250 mB of lava | 3200 FE/t for 125 ticks | Heated Redstone Generator |
| `magmatic_fuel` | 1 mB of `minecraft:lava` | 100 FE/t for 1 tick | Magmatic Generator |
| `shaped_division` | Iron nugget (`N`), `excessive_utilities:division_sigil` (`S`) and diamond (`D`), vertically N/S/D, only with the Division Sigil inverted | `excessive_utilities:semi_unstable_nugget` | Crafting table |
| `shaped_unstable` | 8x `#c:ingots/iron` and 1x `#c:gems/diamond` in the center, only in the stable state | `excessive_utilities:unstable_ingot` | Crafting table (3x3) |
| `keep_paintbrush` | 7x `#c:brick_blocks` (the pattern `III/IDI/IPI` takes 7), `#c:dyes/black` and the Paintbrush (not consumed) | 7x `excessive_utilities:colored_bricks_black` | Crafting table (3x3) |
| `damage_glass_cutter` | `#c:ender_pearls` and the Glass Cutter (loses durability) | 8x `excessive_utilities:ender_shard` | Crafting table (vertical P/C) |
| `snow_globe` | `#c:end_stones`, `excessive_utilities:stoneburnt` and an activated Magical Snow Globe | `excessive_utilities:quantum_quarry` | Crafting table (3x3) |

```js
ServerEvents.recipes(event => {
    //gives 2x minecraft:magenta_dye by crushing minecraft:allium, in the Crusher (200 ticks and 20 FE/t by default)
    event.recipes.excessive_utilities.crusher('2x minecraft:magenta_dye', 'minecraft:allium')
        .id('umc_ejemplos:excessive_utilities/triturador_simple')

    //gives minecraft:gravel by crushing minecraft:cobblestone, plus minecraft:flint with a 25% chance, in 100 ticks spending 40 FE/t, in the Crusher
    event.recipes.excessive_utilities.crusher('minecraft:gravel', 'minecraft:cobblestone', 'minecraft:flint', 0.25)
        .ticks(100)
        .fePerTick(40)
        .id('umc_ejemplos:excessive_utilities/triturador_completo')

    //gives excessive_utilities:block_of_enchanted_metal by mixing 1x gold block (#c:storage_blocks/gold) with 9x lapis lazuli (#c:gems/lapis), spending 40 FE/t for 600 ticks, in the Enchanter
    event.recipes.excessive_utilities.enchanter('excessive_utilities:block_of_enchanted_metal', '#c:storage_blocks/gold', '9x #c:gems/lapis', 40, 600)
        .enchantingPower(15)
        .id('umc_ejemplos:excessive_utilities/encantador')

    //gives excessive_utilities:ender_marker by placing 1 ender pearl (E) on top of 2 ender_infused_obsidian (O) vertically, with 300 crystal ticks, in the QED
    event.recipes.excessive_utilities.qed('excessive_utilities:ender_marker', ['E', 'O', 'O'], {
        E: '#c:ender_pearls',
        O: 'excessive_utilities:ender_infused_obsidian'
    }, 300).id('umc_ejemplos:excessive_utilities/qed')

    //gives excessive_utilities:heating_coil by resonating minecraft:iron_bars at a cost of 16 Grid Power, in the Resonator
    event.recipes.excessive_utilities.resonator('excessive_utilities:heating_coil', 'minecraft:iron_bars', 16)
        .id('umc_ejemplos:excessive_utilities/resonador')

    //gives minecraft:basalt when the World Interaction Upgrade acts on minecraft:soul_soil with minecraft:blue_ice and minecraft:lava on the sides
    event.recipes.excessive_utilities.world_interaction_item('minecraft:basalt', 'minecraft:soul_soil', ['minecraft:blue_ice', 'minecraft:lava'])
        .id('umc_ejemplos:excessive_utilities/interaccion_objeto')

    //gives minecraft:stone when the World Interaction Upgrade acts on any #c:stones with minecraft:water and minecraft:lava on the sides and minecraft:water behind
    event.recipes.excessive_utilities.world_interaction_item('minecraft:stone', '#c:stones', ['minecraft:water', 'minecraft:lava'])
        .behind('minecraft:water')
        .id('umc_ejemplos:excessive_utilities/interaccion_objeto_detras')

    //gives 1 mB of minecraft:water when the World Interaction Upgrade acts on minecraft:water with two minecraft:water on the sides
    event.recipes.excessive_utilities.world_interaction_fluid(Fluid.of('minecraft:water', 1), 'minecraft:water', ['minecraft:water', 'minecraft:water'])
        .id('umc_ejemplos:excessive_utilities/interaccion_fluido')

    //gives 40 FE/t for 400 ticks by burning 1 bone (#c:bones), in the Death Generator
    event.recipes.excessive_utilities.single_item_fuel('death', '#c:bones', 40, 400)
        .id('umc_ejemplos:excessive_utilities/combustible_muerte')

    //gives 3200 FE/t for 125 ticks by burning redstone dust (#c:dusts/redstone) with 250 mB of lava, in the Heated Redstone Generator
    event.recipes.excessive_utilities.item_and_fluid_fuel('heated_redstone', '#c:dusts/redstone', Fluid.of('minecraft:lava', 250), 3200, 125)
        .id('umc_ejemplos:excessive_utilities/combustible_redstone')

    //gives 100 FE/t for 1 tick by consuming 1 mB of minecraft:lava, in the Magmatic Generator
    event.recipes.excessive_utilities.magmatic_fuel(Fluid.of('minecraft:lava', 1), 100, 1)
        .id('umc_ejemplos:excessive_utilities/combustible_magmatico')

    //gives excessive_utilities:semi_unstable_nugget in the crafting table (shaped, vertical N/S/D) with iron nugget, Division Sigil and diamond, only if the Division Sigil is inverted
    event.recipes.excessive_utilities.shaped_division('excessive_utilities:semi_unstable_nugget', ['N', 'S', 'D'], {
        N: '#c:nuggets/iron',
        S: 'excessive_utilities:division_sigil',
        D: '#c:gems/diamond'
    }).inversion('inverted').id('umc_ejemplos:excessive_utilities/division')

    //gives excessive_utilities:unstable_ingot in the crafting table (shaped, 3x3) with 8 iron ingots and 1 diamond in the center, only in the stable state
    event.recipes.excessive_utilities.shaped_unstable('excessive_utilities:unstable_ingot', ['III', 'IDI', 'III'], {
        I: '#c:ingots/iron',
        D: '#c:gems/diamond'
    }).stability('stable').id('umc_ejemplos:excessive_utilities/inestable')

    //gives 7x excessive_utilities:colored_bricks_black in the crafting table (shaped, 3x3) with 8 brick blocks, black dye and the Paintbrush, which is NOT consumed
    event.recipes.excessive_utilities.keep_paintbrush('7x excessive_utilities:colored_bricks_black', ['III', 'IDI', 'IPI'], {
        D: '#c:dyes/black',
        I: '#c:brick_blocks',
        P: 'excessive_utilities:paintbrush'
    }).id('umc_ejemplos:excessive_utilities/pincel')

    //gives 8x excessive_utilities:ender_shard in the crafting table (shaped, vertical P/C) with an ender pearl and the Glass Cutter, which loses durability
    event.recipes.excessive_utilities.damage_glass_cutter('8x excessive_utilities:ender_shard', ['P', 'C'], {
        P: '#c:ender_pearls',
        C: 'excessive_utilities:glass_cutter'
    }).id('umc_ejemplos:excessive_utilities/cortavidrios')

    //gives excessive_utilities:quantum_quarry in the crafting table (shaped, 3x3) with end stones, stoneburnt and an already activated Magical Snow Globe (G)
    event.recipes.excessive_utilities.snow_globe('excessive_utilities:quantum_quarry', ['ESE', 'SGS', 'ESE'], {
        E: '#c:end_stones',
        G: 'excessive_utilities:magical_snow_globe',
        S: 'excessive_utilities:stoneburnt'
    }).id('umc_ejemplos:excessive_utilities/bola_nieve')
})
```

---

### 4.10. Functional Storage

Module `compat.kubejs.modules.functionalstorage`. Required mod: `functionalstorage`.

| Type | Call | Arguments |
| --- | --- | --- |
| `custom_compacting` | `custom_compacting(lowerItem, higherItem)` | Two items with amount: `'4x minecraft:amethyst_shard'` and `'minecraft:amethyst_block'` |
| `copy_components` | `copy_components(result, [pattern], {letter: ingredient}, [componentsToCopy], sourceSlotIndex)` | Shaped crafting table recipe that copies components from one slot to the result |

In addition, `framed_recipe` is registered with no arguments, so that KubeJS does not throw errors on the framed drawer recipes the mod already ships.

| Recipe | Input | Output | Where |
| --- | --- | --- | --- |
| `custom_compacting` | 4x `minecraft:amethyst_shard` | 1x `minecraft:amethyst_block` (and the reverse when decompacting) | Compacting Drawer |
| `custom_compacting` | 9x `minecraft:iron_nugget` | 1x `minecraft:iron_ingot` (and the reverse) | Compacting Drawer |
| `copy_components` | `functionalstorage:compacting_drawer` (`D`) surrounded by `#c:nuggets/iron` (`S`), copying the component `functionalstorage:tile` | `functionalstorage:compacting_framed_drawer` | Crafting table. Index 4 is the center slot of the 3x3 |

```js
ServerEvents.recipes(event => {
    //makes the Compacting Drawer compact 4x minecraft:amethyst_shard into 1x minecraft:amethyst_block (and decompact it back)
    event.recipes.functionalstorage.custom_compacting('4x minecraft:amethyst_shard', 'minecraft:amethyst_block')
        .id('umc_ejemplos:functionalstorage/compactar_amatista')

    //makes the Compacting Drawer compact 9x minecraft:iron_nugget into 1x minecraft:iron_ingot (and decompact it back)
    event.recipes.functionalstorage.custom_compacting('9x minecraft:iron_nugget', 'minecraft:iron_ingot')
        .id('umc_ejemplos:functionalstorage/compactar_hierro')

    //gives 1x functionalstorage:compacting_framed_drawer in the crafting table (shaped) by surrounding a Compacting Drawer (D) with iron nuggets (S) and copying the functionalstorage:tile component to the result (index 4 = center slot of the 3x3)
    event.recipes.functionalstorage.copy_components('functionalstorage:compacting_framed_drawer', ['S S', 'SDS', ' S '], {
        D: 'functionalstorage:compacting_drawer',
        S: '#c:nuggets/iron'
    }, ['functionalstorage:tile'], 4).id('umc_ejemplos:functionalstorage/cajon_marco_compactador')
})
```

---

## 5. Mystical Agriculture Crops

If **Mystical Agriculture** is installed, UMC adds **54 extra crops** natively (without KubeJS). Each crop is only registered when the mods it needs are loaded (column "Required mods").

### How the Ids Work

| What | Id |
| --- | --- |
| Crop | `umc:<id>` (the namespace is changed with `compat.mysticalagriculture.crop_namespace`) |
| Seeds | `mysticalagriculture:<id>_seeds` |
| Essence | `mysticalagriculture:<id>_essence` |
| Display name | Language key `umc.mysticalagriculture.crop.<id>` (in English and Spanish) |

Example: the crop `thaumaturge_thaumium` gives `mysticalagriculture:thaumaturge_thaumium_seeds` and `mysticalagriculture:thaumaturge_thaumium_essence`.

Each crop can be turned off separately with `compat.mysticalagriculture.crops.<id> = false`, and the whole system with `compat.mysticalagriculture.enabled = false`.

### The 54 Crops

- **Tier:** the tier level. 1 to 5 are from Mystical Agriculture; 6 is from Mystical Agradditions (which is why it requires that mod).
- **Material:** ingredient the seed is crafted with. With `#` it is a tag.
- **Essence gives:** what you get when crafting the essence in a ring (amount and item). "no recipe" means there is no essence recipe.

| Id | Name | Tier | Material | Required mods | Essence gives |
| --- | --- | --- | --- | --- | --- |
| `allthemodium` | Allthemodium | 6 (Agradditions) | allthecompressed:allthemodium_block_4x | allthecompressed, mysticalagradditions | 2x allthemodium:allthemodium_ingot |
| `andesite_alloy` | Andesite Alloy | 5 | create:andesite_alloy_block | create | 2x create:andesite_alloy |
| `arcane_gold_ingot` | Arcane Gold | 5 | eidolon:arcane_gold_block | eidolon | 2x eidolon:arcane_gold_ingot |
| `black_quartz` | Black Quartz | 3 | #c:gems/black_quartz | one of: actuallyadditions / occultism | (no recipe) |
| `blaze_gold` | Blaze Gold | 6 (Agradditions) | justdirethings:blazegold_block | justdirethings, mysticalagradditions | 2x justdirethings:blazegold_ingot |
| `cajita_feliz` | Happy Box | 1 | ultimatefoods:cajita_feliz | ultimatefoods | 12x ultimatefoods:cajita_feliz |
| `celestigem` | Celestigem | 6 (Agradditions) | justdirethings:celestigem_block | justdirethings, mysticalagradditions | 2x justdirethings:celestigem |
| `chaos_shard` | Chaos Shard | 6 (Agradditions) | draconicevolution:chaos_shard | draconicevolution, mysticalagradditions | 2x draconicevolution:chaos_shard |
| `chromium` | Chromium | 6 (Agradditions) | mysticalexpansion:etherium_ingot_block | mysticalexpansion, mysticalagradditions | 2x mysticalexpansion:etherium_ingot |
| `cognition` | Cognitive Alloy | 5 | experienceobelisk:cognitive_alloy_block | experienceobelisk | 2x experienceobelisk:cognitive_alloy |
| `crystal_matrix` | Crystal Matrix | 5 | avaritia:crystal_matrix | avaritia | 2x avaritia:crystal_matrix_ingot |
| `cuarzorosa` | Rose Quartz | 6 (Agradditions) | create:polished_rose_quartz | create, mysticalagradditions | 2x create:polished_rose_quartz |
| `dark_gem` | Dark Gem | 3 | evilcraft:dark_block | evilcraft | 4x evilcraft:dark_gem |
| `deorum_ingot` | Deorum | 4 | forbidden_arcanus:deorum_block | forbidden_arcanus | 3x forbidden_arcanus:deorum_ingot |
| `eclipse_alloy` | Eclipse Alloy | 6 (Agradditions) | justdirethings:eclipsealloy_block | justdirethings, mysticalagradditions | 2x justdirethings:eclipsealloy_ingot |
| `elevatium_ingot` | Elevatium | 4 | kubejs:ingot_of_elevation | kubejs | 3x kubejs:ingot_of_elevation |
| `ender_ingot` | Ender Ingot | 5 | extendedcrafting:ender_ingot_block | extendedcrafting | 2x extendedcrafting:ender_ingot |
| `entro` | Entro | 5 | allthecompressed:entro_block_1x | allthecompressed | 2x allthecompressed:entro_block_1x |
| `ferricore` | FerriCore | 6 (Agradditions) | justdirethings:ferricore_block | justdirethings, mysticalagradditions | 2x justdirethings:ferricore_ingot |
| `flux_dust` | Flux | 1 | fluxnetworks:flux_block | fluxnetworks | 12x fluxnetworks:flux_dust |
| `imperium_essence` | Imperium Essence | 4 | mysticalagriculture:imperium_essence | (none) | 3x mysticalagriculture:imperium_essence |
| `insanium_essence` | Insanium Essence | 6 (Agradditions) | mysticalagriculture:supremium_essence | mysticalagradditions | 2x mysticalagriculture:supremium_essence |
| `kivi` | Kivi | 2 | xycraft_world:kivi | xycraft_world | 6x xycraft_world:kivi |
| `nadienite_ingot` | Nadienite | 5 | ultimatearmoury:nadienite_block | ultimatearmoury | 2x ultimatearmoury:nadienite_ingot |
| `naquadah` | Naquadah | 6 (Agradditions) | mekanism_extras:block_naquadah | mekanism_extras, mysticalagradditions | 2x mekanism_extras:ingot_naquadah |
| `pink_slime` | Pink Slime | 4 | industrialforegoing:pink_slime_ingot | industrialforegoing | 3x industrialforegoing:pink_slime_ingot |
| `predicciondematrix` | Prediction Matrix | 3 | hostilenetworks:prediction_matrix | hostilenetworks | 4x hostilenetworks:prediction_matrix |
| `prometheum` | Prometheum | 4 | oritech:prometheum_ingot | oritech | 3x oritech:prometheum_ingot |
| `prosperity_shard` | Prosperity Shard | 5 | mysticalagriculture:prosperity_block | (none) | 2x mysticalagriculture:prosperity_shard |
| `prudentium_essence` | Prudentium Essence | 2 | mysticalagriculture:prudentium_essence | (none) | 6x mysticalagriculture:prudentium_essence |
| `redstoneingot` | Redstone Ingot | 2 | extendedcrafting:redstone_ingot_block | extendedcrafting | 6x extendedcrafting:redstone_ingot |
| `sky_steel_ingot` | Sky Steel | 5 | allthecompressed:sky_steel_block_1x | allthecompressed | 2x megacells:sky_steel_ingot |
| `source_gem` | Source Gem | 2 | ars_nouveau:source_gem_block | ars_nouveau | 6x ars_nouveau:source_gem |
| `spirit_gem` | Spirit Gem | 3 | occultism:spirit_campfire | occultism | 4x occultism:spirit_campfire |
| `super_cacotas` | Super Cacotas | 5 | ultimate_utilities:super_caca | ultimate_utilities | 2x ultimate_utilities:super_caca |
| `supremium_essence_esc` | Supremium Essence | 5 | mysticalagriculture:supremium_essence | (none) | 2x mysticalagriculture:supremium_essence |
| `tertium_essence` | Tertium Essence | 3 | mysticalagriculture:tertium_essence | (none) | 4x mysticalagriculture:tertium_essence |
| `thaumaturge_alumentum` | Alumentum | 4 | thaumaturge:alumentum | thaumaturge | 3x thaumaturge:alumentum |
| `thaumaturge_amber` | Amber | 3 | thaumaturge:amber | thaumaturge | 4x thaumaturge:amber |
| `thaumaturge_brass` | Thaumaturge Brass | 2 | thaumaturge:ingot_brass | thaumaturge | 6x thaumaturge:ingot_brass |
| `thaumaturge_crystal_aer` | Aer Crystal | 3 | thaumaturge:crystal_aer | thaumaturge | 4x thaumaturge:crystal_aer |
| `thaumaturge_crystal_aqua` | Aqua Crystal | 3 | thaumaturge:crystal_aqua | thaumaturge | 4x thaumaturge:crystal_aqua |
| `thaumaturge_crystal_ignis` | Ignis Crystal | 3 | thaumaturge:crystal_ignis | thaumaturge | 4x thaumaturge:crystal_ignis |
| `thaumaturge_crystal_ordo` | Ordo Crystal | 3 | thaumaturge:crystal_ordo | thaumaturge | 4x thaumaturge:crystal_ordo |
| `thaumaturge_crystal_perditio` | Perditio Crystal | 4 | thaumaturge:crystal_perditio | thaumaturge | 3x thaumaturge:crystal_perditio |
| `thaumaturge_crystal_terra` | Terra Crystal | 3 | thaumaturge:crystal_terra | thaumaturge | 4x thaumaturge:crystal_terra |
| `thaumaturge_crystal_vitium` | Vitium Crystal | 4 | thaumaturge:crystal_vitium | thaumaturge | 3x thaumaturge:crystal_vitium |
| `thaumaturge_quicksilver` | Quicksilver | 2 | thaumaturge:quicksilver | thaumaturge | 6x thaumaturge:quicksilver |
| `thaumaturge_salis_mundus` | Salis Mundus | 2 | thaumaturge:salis_mundus | thaumaturge | 6x thaumaturge:salis_mundus |
| `thaumaturge_thaumium` | Thaumium | 3 | thaumaturge:ingot_thaumium | thaumaturge | 4x thaumaturge:ingot_thaumium |
| `thaumaturge_void_metal` | Void Metal | 4 | thaumaturge:ingot_void | thaumaturge | 3x thaumaturge:ingot_void |
| `unobtainium` | Unobtainium | 6 (Agradditions) | #c:storage_blocks/unobtainium | allthemodium, mysticalagradditions | 2x allthemodium:unobtainium_ingot |
| `vibranium` | Vibranium | 6 (Agradditions) | #c:storage_blocks/vibranium | allthemodium, mysticalagradditions | 2x allthemodium:vibranium_ingot |
| `xychorium_gem` | Xychorium Gem | 3 | #c:gems/xychorium | xycraft_world | (no recipe) |

Summary by mod: 14 crops need `thaumaturge`; tier 6 crops need `mysticalagradditions`; `imperium_essence`, `prosperity_shard`, `prudentium_essence`, `supremium_essence_esc` and `tertium_essence` need no mod other than Mystical Agriculture.

### Seed Recipes

Mystical Agriculture generates the recipes for each seed with the material from the table. UMC turns them on or off with these keys:

| Recipe | Key | Where it is made |
| --- | --- | --- |
| Crafting | `compat.mysticalagriculture.recipes.seed_crafting` | Crafting table |
| Infusion | `compat.mysticalagriculture.recipes.seed_infusion` | Infusion Altar |
| Reprocessor | `compat.mysticalagriculture.recipes.seed_reprocessor` | Seed Reprocessor (from seed to essence) |

Each one is enabled by default. For seed recipes different from the catalog's, use `MysticalEvents.recipes` (section 4.3), which adds only the necessary `neoforge:mod_loaded` conditions.

### Essence Recipes

Crafting the essence returns the item the crop comes from. It is a shaped recipe in a ring of 8 essences:

```
EEE
E E
EEE
```

- `E` is `mysticalagriculture:<id>_essence`.
- The amount you get is **12 divided by the tier** (minimum 1), using integer division:

| Tier | Amount you get |
| --- | --- |
| 1 | 12 |
| 2 | 6 |
| 3 | 4 |
| 4 | 3 |
| 5 | 2 |
| 6 | 2 |

- They are generated by datagen in `data/umc/recipe/mystical/essence/<id>.json`: there are **52 recipes**. The crops `black_quartz` and `xychorium_gem` have no essence recipe because their material is a tag (there is no single result item).
- They are enabled with `compat.mysticalagriculture.recipes.essence_crafting`.
- They carry `neoforge:mod_loaded` conditions (one for each mod the crop requires, apart from Mystical Agriculture) and the custom condition `umc:mystical_crop`.

Real example (`andesite_alloy`, tier 5, gives 2 andesite alloys):

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

### Condition `umc:mystical_crop`

A NeoForge data condition added by the mod. It can be used in any datapack recipe.

```json
{ "type": "umc:mystical_crop", "crop": "andesite_alloy" }
```

| Field | Type | What it is |
| --- | --- | --- |
| `crop` | text | Id of the crop in the catalog (without namespace), from the "Id" column of the table |

It is true only if **all** of these are met:

1. `compat.mysticalagriculture.enabled` is `true`.
2. `compat.mysticalagriculture.recipes.essence_crafting` is `true`.
3. `compat.mysticalagriculture.crops.<crop>` is `true`.
4. The mods that crop requires are loaded (column "Required mods").

---

## 6. FAQ and Known Issues

### I used an event and KubeJS says it does not exist

Each compatibility module is only registered if the corresponding mod is installed **and** its switch is on (`compat.kubejs.enabled` and `compat.kubejs.modules.<mod>`). If the mod is missing, objects such as `EnchantedEvents`, `ThaumaturgeEvents`, `GeneratorGalore` or `event.recipes.ae2` do not exist. You also get errors of the type `Serializer for type X is not found` when a recipe points to a mod that is not installed.

### The `elevatium_ingot` crop cannot find its item

That crop uses `kubejs:ingot_of_elevation`, an item **from the modpack** (from KubeJS), not from the mod. The crop is registered when KubeJS is loaded, but you need to define the item yourself in `startup_scripts`, for example:

```js
StartupEvents.registry('item', event => {
    event.create('ingot_of_elevation')
})
```

If you do not want it, turn the crop off with `compat.mysticalagriculture.crops.elevatium_ingot = false`.

### Thaumaturge warns about `arcane_key_*` on load

Warnings like this appear in the KubeJS log:

```
Failed to parse recipe 'thaumaturge:arcane_workbench/arcane_key_iron[thaumaturge:arcane_workbench_shaped]'! Falling back to vanilla: Failed to read required component 'result: item_stack' - java.lang.IllegalStateException: Item stack with stack size of 2 was larger than maximum: 1
```

The same happens for `arcane_key_gold`. These are Thaumaturge's own recipes, whose result has an amount larger than the maximum stack size. KubeJS lets them through as normal recipes ("Falling back to vanilla"), so they are **harmless** and there is nothing to do.

### Which namespace do the rites go in?

The rites added by the UMC mod itself (familiars, spirit journey, necromancy, purification) are in the `enchanted` namespace. The ones you create yourself with `EnchantedEvents.rites` use the namespace you give (`kubejs:` if you write none), and they are saved in `data/<namespace>/enchanted/circle_magic/rite/`. Chalks and shapes without a namespace are read as `enchanted:`.

### The Create conversion does nothing in the game

The mysterious conversion (`conversion` / `mysteriousConversion`) is **only shown in JEI** unless you chain `.haunting()` (fan behind soul fire) or `.entity('minecraft:pig')` (right-click on that entity). See section 4.7.

### The AE2 transformation in lava does not work

It needs **fireproof** items (netherite) and about **3 seconds** inside the lava. The others burn first.

### A Thaumaturge blueprint comes out upside down

The first `layer()` is the **top** layer and the last is the **bottom** one. It is activated by right-clicking any block of the structure with Salis Mundus.

### I changed the config and nothing happens

The switches in `compat.kubejs` and `compat.mysticalagriculture` apply when the game starts (crops and KubeJS modules are registered at that moment): restart the game after changing them.
