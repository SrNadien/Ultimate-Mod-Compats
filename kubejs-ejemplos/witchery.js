//hace registrar factories de rito con codigo JS (comportamiento propio al iniciar, en cada tick y al terminar)
//EnchantedStartup.riteFactories(event => { event.create(id) ... })
EnchantedStartup.riteFactories(event => {
    //hace un rito de 200 ticks que da Regeneracion al lanzador cada segundo, avisa por chat y declara como salida minecraft:golden_apple
    //event.create(id).duration(ticks).output(...items).onStart(ctx => bool).onTick(ctx => bool).onStop(ctx => ...)   ctx = {caster, level, pos, ticks, data, getString/getInt(clave, defecto), particles, drop, runCommand, cancel}
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
    //event.create(id).onStart(ctx => bool)
    event.create('kubejs:thunder_call')
        .onStart(ctx => {
            ctx.level.getServer().runCommandSilent('summon minecraft:lightning_bolt ' + ctx.pos.x + ' ' + ctx.pos.y + ' ' + ctx.pos.z)
            ctx.drop(Item.of('minecraft:lightning_rod'))
            return false
        })
})

//hace registrar maldiciones nuevas con codigo JS
//EnchantedStartup.curses(event => { event.create(id) ... })
EnchantedStartup.curses(event => {
    //hace una maldicion de fuerza maxima 3 que avisa al aplicarse y al quitarse, y da Lentitud (nivel = fuerza - 1) cada 40 ticks
    //event.create(id).maxStrength(int).onInitialise(ctx => ...).onRemove(ctx => ...).tick(ctx => ...)   ctx = {target, strength, age}
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

//hace registrar familiares nuevos con codigo JS
//EnchantedStartup.familiars(event => { event.create(id, entidadOrigen, entidadFamiliar) ... })
EnchantedStartup.familiars(event => {
    //hace un familiar que convierte un minecraft:parrot en otro minecraft:parrot con el nombre Familiar (se usa con un rito bindFamiliar; sin confirmar)
    //event.create(id, entidadOrigen, entidadFamiliar).copy((from, familiar) => ...)
    event.create('kubejs:parrot_familiar', 'minecraft:parrot', 'minecraft:parrot')
        .copy((from, familiar) => {
            familiar.setCustomName(Text.of('Familiar'))
        })
})

//hace registrar items nuevos con los builders de Enchanted (poppets, pociones y frascos)
//StartupEvents.registry('item', event => { event.create(id, tipo) ... })   tipo = enchanted:player_poppet | enchanted:item_poppet | enchanted:brew | enchanted:throwable_brew
StartupEvents.registry('item', event => {
    //da un poppet de 2 usos que protege del fuego y la lava, da Resistencia al fuego 30 s y avisa al jugador (como se equipa: sin confirmar)
    //event.create(id, 'enchanted:player_poppet').poppetColour(colorPrimario, colorSecundario).maxDamage(usos).protectsAgainst(...tiposDano o #tags).effect(efecto, ticks, amplificador).onProtect(player => ...)
    event.create('lava_poppet', 'enchanted:player_poppet')
        .poppetColour(0xFFFF6A00, 0xFFB22200)
        .maxDamage(2)
        .protectsAgainst('#minecraft:is_fire', 'minecraft:lava')
        .effect('minecraft:fire_resistance', 600, 0)
        .onProtect(player => player.tell('El poppet absorbe el fuego'))

    //da un poppet que protege del dano de yunque (sin .effect da Regeneracion II 5 s por defecto)
    //event.create(id, 'enchanted:player_poppet').protectsWhen(source => bool)
    event.create('anvil_poppet', 'enchanted:player_poppet')
        .protectsWhen(source => source.type().msgId() == 'anvil')

    //da un poppet de objeto que protege items de hierro (minecraft:iron_*) y les restaura la mitad de la durabilidad (sin confirmar)
    //event.create(id, 'enchanted:item_poppet').poppetColour(colorPrimario, colorSecundario).protects(stack => bool).restoreMultiplier(fraccion)
    event.create('iron_poppet', 'enchanted:item_poppet')
        .poppetColour(0xFFD8D8D8, 0xFF9A9A9A)
        .protects(stack => stack.id.startsWith('minecraft:iron_'))
        .restoreMultiplier(0.5)

    //da una pocion bebible que da Vision nocturna 5 min y Caida lenta 1 min y avisa al bebedor
    //event.create(id, 'enchanted:brew').effect(efecto, ticks, amplificador).onDrink(entity => ...)
    event.create('moon_brew', 'enchanted:brew')
        .effect('minecraft:night_vision', 6000, 0)
        .effect('minecraft:slow_falling', 1200, 0)
        .onDrink(entity => entity.tell('Sabe a luz de luna'))

    //da un frasco arrojable que da Resplandor 20 s a las criaturas en radio 5 y hace caer un rayo donde se rompe
    //event.create(id, 'enchanted:throwable_brew').effect(efecto, ticks, amplificador).radius(bloques).brewColour(colorRGB).onSplash(ctx => ...)   ctx = {owner, level, pos}
    event.create('storm_flask', 'enchanted:throwable_brew')
        .effect('minecraft:glowing', 400, 0)
        .radius(5)
        .brewColour(0x55AAFF)
        .onSplash(ctx => ctx.level.spawnLightning(ctx.pos.x(), ctx.pos.y(), ctx.pos.z(), true))
})

//hace registrar formas de circulo para ritos
//EnchantedEvents.shapes(event => { event.create(id).rows(...) })
EnchantedEvents.shapes(event => {
    //hace la forma kubejs:cross_circle, cuadrada de 7x7 (X = casilla de tiza, O = interior sin tiza, espacio = vacio; el significado de O: sin confirmar)
    //event.create(id).rows(...filas)   filas en numero impar, todas del mismo largo que el numero de filas, al menos una X
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

//hace registrar ritos de circulo y desactivar ritos de Enchanted
//EnchantedEvents.rites(event => { event.create(id)... ; event.disable(...ids) })
EnchantedEvents.rites(event => {
    //hace un rito nocturno con cielo despejado: Tear of the Goddess + manzana dorada en circulo mediano de tiza ritual, gasta 1500 de poder (+2 por tick) y activa la factory kubejs:blessing
    //event.create(id).item(stack).item(stack).mediumCircle(tiza).power(int).tickPower(int).night().weather(clima).factory(idFactory, {parametros})
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
    //event.create(id).items(...stacks).circle(forma, tiza).power(int).weather(clima).dimension(...dimensiones).factory(idFactory)
    event.create('kubejs:thunder_rite')
        .items('minecraft:iron_ingot', '2x minecraft:redstone')
        .circle('kubejs:cross_circle', 'enchanted:nether_chalk')
        .power(2500)
        .weather('thundering')
        .dimension('minecraft:overworld')
        .factory('kubejs:thunder_call')

    //da 1 minecraft:diamond y 2 minecraft:emerald sacrificando un minecraft:coal_block en circulo pequeno de tiza ritual, gasta 800
    //event.create(id).item(stack).smallCircle(tiza).power(int).createItem(...stacksResultado)
    event.create('kubejs:diamond_gift')
        .item('minecraft:coal_block')
        .smallCircle('ritual_chalk')
        .power(800)
        .createItem('minecraft:diamond', '2x minecraft:emerald')

    //hace aplicar la maldicion kubejs:heavy_feet (al duenyo del taglock; sin confirmar) con taglock + yunque en circulo mediano de tiza del Nether, gasta 1800
    //event.create(id).items(...stacks).mediumCircle(tiza).power(int).applyCurse(idMaldicion)
    event.create('kubejs:curse_heavy_feet')
        .items('enchanted:taglock', 'minecraft:anvil')
        .mediumCircle('nether_chalk')
        .power(1800)
        .applyCurse('kubejs:heavy_feet')

    //hace poner dia y cielo despejado con un reloj en circulo pequeno de tiza ritual, gasta 600
    //event.create(id).item(stack).smallCircle(tiza).power(int).command(...comandos)
    event.create('kubejs:quick_day')
        .item('minecraft:clock')
        .smallCircle('ritual_chalk')
        .power(600)
        .command('time set day', 'weather clear')

    //hace una zona protegida de radio 12 durante 12000 ticks (10 min) que tambien bloquea a jugadores, con obsidiana + diamante en circulo mediano, gasta 2000 (+3 por tick)
    //event.create(id).items(...stacks).mediumCircle(tiza).power(int).tickPower(int).protection(radio, ticksDuracion, bloqueaJugadores)
    event.create('kubejs:big_protection')
        .items('minecraft:obsidian', 'minecraft:diamond')
        .mediumCircle('ritual_chalk')
        .power(2000)
        .tickPower(3)
        .protection(12, 12000, true)

    //hace inutilizable el rito enchanted:blight (lo sustituye por uno imposible de cumplir)
    //event.disable(...idsRito)
    event.disable('enchanted:blight')
})

//hace registrar fuentes de poder y mejoras del altar
//EnchantedEvents.altar(event => { event.block(...) ; event.tag(...) ; event.upgrade(id)... })
EnchantedEvents.altar(event => {
    //da 6 de poder al altar por cada minecraft:amethyst_block cercano, contando hasta 16 bloques
    //event.block(bloque, poder, limite)
    event.block('minecraft:amethyst_block', 6, 16)
    //da 3 de poder al altar por cada bloque del tag minecraft:flowers cercano, contando hasta 40
    //event.tag(tagBloques, poder, limite)
    event.tag('minecraft:flowers', 3, 40)
    //hace una mejora de altar con minecraft:soul_lantern de tipo torch: poder 0, recarga x1.5 (sin confirmar) y prioridad 1
    //event.upgrade(id).type(tipo).block(bloque).power(decimal).recharge(decimal).priority(int)
    event.upgrade('kubejs:soul_lantern').type('torch').block('minecraft:soul_lantern').power(0).recharge(1.5).priority(1)
})

//hace registrar mutaciones de plantas (mutandis y mutandis extremis)
//EnchantedEvents.mutagens(event => { event.create(id) ... })
EnchantedEvents.mutagens(event => {
    //hace mutar a minecraft:amethyst_block (peso 10) con mutandis si hay stone/deepslate alrededor, o a minecraft:budding_amethyst (peso 2) con mutandis extremis rodeado de amethyst_block; el id deberia ser el de la planta que muta (sin confirmar)
    //event.create(id).mutation(resultado, peso, ...bloquesMutagenos).extremisMutation(resultado, peso, ...bloquesMutagenos)
    event.create('kubejs:crystals')
        .mutation('minecraft:amethyst_block', 10, 'minecraft:stone', 'minecraft:deepslate')
        .extremisMutation('minecraft:budding_amethyst', 2, 'minecraft:amethyst_block')
})

//hace registrar recetas de Enchanted (kettle, wheel, distilling, byproduct) con KubeJS
//ServerEvents.recipes(event => { event.recipes.enchanted.<tipo>(...) })
ServerEvents.recipes(event => {
    //da 3x enchanted:brew_of_love mezclando minecraft:poppy, golden_carrot, lily_pad, cocoa_beans, enchanted:whiff_of_magic y water_artichoke en el caldero (kettle) con frasco; el liquido pasa de #B046A5 a F78FEB
    //event.recipes.enchanted.kettle(resultado, [ingredientes]).requiresBottle(bool).power(int).cookColour(color).finalColour(color)
    event.recipes.enchanted.kettle('3x enchanted:brew_of_love', [
        'minecraft:poppy',
        'minecraft:golden_carrot',
        'minecraft:lily_pad',
        'minecraft:cocoa_beans',
        'enchanted:whiff_of_magic',
        'enchanted:water_artichoke'
    ]).requiresBottle(true).cookColour('#B046A5').finalColour('F78FEB')

    //da kubejs:moon_brew mezclando minecraft:glow_berries, amethyst_shard y enchanted:hint_of_rebirth en el caldero (kettle) con frasco, poder 200 (efecto exacto: sin confirmar)
    //event.recipes.enchanted.kettle(resultado, [ingredientes]).requiresBottle(bool).power(int).cookColour(color).finalColour(color)
    event.recipes.enchanted.kettle('kubejs:moon_brew', ['minecraft:glow_berries', 'minecraft:amethyst_shard', 'enchanted:hint_of_rebirth'])
        .requiresBottle(true)
        .power(200)
        .cookColour(0x223366)
        .finalColour(0x88AAFF)

    //da kubejs:lava_poppet tejiendo enchanted:poppet + enchanted:demonic_blood + minecraft:magma_block en la rueda de hilar con 500 de poder de altar durante 300 ticks
    //event.recipes.enchanted.wheel(resultado, [3 ingredientes]).power(int).duration(ticks)
    event.recipes.enchanted.wheel('kubejs:lava_poppet', ['enchanted:poppet', 'enchanted:demonic_blood', 'minecraft:magma_block'])
        .power(500)
        .duration(300)

    //da enchanted:tear_of_the_goddess, enchanted:whiff_of_magic y minecraft:slime_ball destilando 3x enchanted:clay_jar + breath_of_the_goddess + minecraft:lapis_lazuli en el destilador, 300 ticks y 750 de poder
    //event.recipes.enchanted.distilling([resultados (1 a 4)], [ingredientes (1 a 3)]).duration(ticks).power(int)
    event.recipes.enchanted.distilling(
        ['enchanted:tear_of_the_goddess', 'enchanted:whiff_of_magic', 'minecraft:slime_ball'],
        ['3x enchanted:clay_jar', 'enchanted:breath_of_the_goddess', 'minecraft:lapis_lazuli']
    ).duration(300).power(750)

    //da enchanted:foul_fume como subproducto al quemar cualquier tronco inflamable (#minecraft:logs_that_burn) en el horno de la bruja con un clay_jar (detalle: sin confirmar)
    //event.recipes.enchanted.byproduct(resultado, ingrediente)
    event.recipes.enchanted.byproduct('enchanted:foul_fume', '#minecraft:logs_that_burn')
})
