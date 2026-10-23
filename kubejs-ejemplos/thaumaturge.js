//hace que se registre el objeto kubejs:luminous_blade, usado como resultado de la infusion de mas abajo
//StartupEvents.registry('item', event)
StartupEvents.registry('item', event => {
    //hace que se cree el objeto kubejs:luminous_blade
    //event.create(id)
    event.create('luminous_blade')
})

//hace que se registren remates de varita nuevos
//ThaumaturgeStartup.wandCaps(event)
ThaumaturgeStartup.wandCaps(event => {
    //hace que se cree el remate kubejs:emerald
    //event.create(id)
    event.create('kubejs:emerald')
        //hace que el remate multiplique el coste de vis por 0.95
        //.baseCost(multiplicador)
        .baseCost(0.95)
        //hace que el coste baje a 0.9 para los aspectos aqua y terra
        //.specialCost(multiplicador, aspectos...)
        .specialCost(0.9, 'aqua', 'terra')
        //hace que fabricar el remate cueste 5
        //.craftCost(coste)
        .craftCost(5)
        //hace que el remate use esta textura
        //.texture(ruta de la textura)
        .texture('kubejs:textures/models/wand_cap_emerald.png')

    //hace que se cree el remate kubejs:prism
    //event.create(id)
    event.create('kubejs:prism')
        //hace que el remate multiplique el coste de vis por 1.0
        //.baseCost(multiplicador)
        .baseCost(1.0)
        //hace que el coste del aspecto ignis sea 0.8
        //.aspectCost(aspecto, multiplicador)
        .aspectCost('ignis', 0.8)
        //hace que el coste del aspecto aer sea 0.85
        //.aspectCost(aspecto, multiplicador)
        .aspectCost('aer', 0.85)
        //hace que fabricar el remate cueste 4
        //.craftCost(coste)
        .craftCost(4)
})

//hace que se registren varas de varita nuevas
//ThaumaturgeStartup.wandRods(event)
ThaumaturgeStartup.wandRods(event => {
    //hace que se cree la vara kubejs:crystal
    //event.create(id)
    event.create('kubejs:crystal')
        //hace que la vara guarde 90 de vis
        //.capacity(reserva de vis)
        .capacity(90)
        //hace que fabricar la vara cueste 7
        //.craftCost(coste)
        .craftCost(7)
        //hace que la vara brille
        //.glow(brilla)
        .glow(true)
        //hace que la vara use esta textura
        //.texture(ruta de la textura)
        .texture('kubejs:textures/models/wand_rod_crystal.png')
        //hace que cada 200 ticks con la varita en el inventario se de velocidad al jugador
        //.onUpdate((stack, player) => codigo)
        .onUpdate((stack, player) => {
            if (player.tickCount % 200 == 0) {
                player.potionEffects.add('minecraft:speed', 100, 0)
            }
        })
})

//hace que se registren rasgos de golem nuevos (solo necesitan id e icono)
//ThaumaturgeStartup.golemTraits(event)
ThaumaturgeStartup.golemTraits(event => {
    //hace que se cree el rasgo de golem kubejs:fragile
    //event.create(id)
    event.create('kubejs:fragile')
        //hace que el rasgo use este icono
        //.icon(ruta de la textura)
        .icon('kubejs:textures/misc/golem/tag_fragile.png')
})

//hace que se registren rasgos de mob nuevos
//ThaumaturgeStartup.mobTraits(event)
ThaumaturgeStartup.mobTraits(event => {
    //hace que se cree el rasgo de mob kubejs:vampiric
    //event.create(id)
    event.create('kubejs:vampiric')
        //hace que el rasgo cuente como campeon
        //.champion(esCampeon)
        .champion(true)
        //hace que cada 100 ticks el mob herido recupere 1 de vida
        //.tick(mob => codigo)
        .tick(mob => {
            if (mob.tickCount % 100 == 0 && mob.health < mob.maxHealth) {
                mob.heal(1)
            }
        })
        //hace que el dano que hace el mob se multiplique por 1.25
        //.onAttack(ctx => nuevo dano)
        .onAttack(ctx => ctx.amount * 1.25)
        //hace que el dano que recibe el mob se duplique si es por fuego
        //.onHurt(ctx => nuevo dano)
        .onHurt(ctx => (ctx.source.getMsgId() == 'inFire' || ctx.source.getMsgId() == 'onFire') ? ctx.amount * 2 : ctx.amount)
})

//hace que se cree el aspecto kubejs:lux y se repartan aspectos a objetos, mobs y biomas
//ThaumaturgeEvents.aspects(event)
ThaumaturgeEvents.aspects(event => {
    //hace que se cree el aspecto kubejs:lux
    //event.create(id)
    event.create('kubejs:lux')
        //hace que el aspecto tenga este color
        //.color(color hexadecimal)
        .color(0xFFEE55)
        //hace que el aspecto use este codigo de color de chat
        //.chatColor(codigo de color)
        .chatColor('e')
        //hace que el aspecto se componga de ignis y aer
        //.components(aspecto1, aspecto2)
        .components('ignis', 'aer')
        //hace que el aspecto use esta textura
        //.texture(ruta de la textura)
        .texture('kubejs:textures/aspects/lux.png')

    //hace que minecraft:glowstone tenga 10x kubejs:lux y 2x thaumaturge:ignis
    //event.item(objeto o #etiqueta, aspectos...)
    event.item('minecraft:glowstone', '10x kubejs:lux', '2x thaumaturge:ignis')
    //hace que los lingotes de cobre (#c:ingots/copper) tengan 3x thaumaturge:metallum y 1x thaumaturge:potentia
    //event.item(objeto o #etiqueta, aspectos...)
    event.item('#c:ingots/copper', '3x thaumaturge:metallum', '1x thaumaturge:potentia')
    //hace que minecraft:dirt pierda sus aspectos
    //event.removeItem(objeto o #etiqueta)
    event.removeItem('minecraft:dirt')
    //hace que minecraft:bee tenga 5x thaumaturge:volatus y 3x kubejs:lux
    //event.entity(mob, aspectos...)
    event.entity('minecraft:bee', '5x thaumaturge:volatus', '3x kubejs:lux')
    //hace que el bioma minecraft:desert tenga thaumaturge:ignis y kubejs:lux
    //event.biome(bioma, aspectos...)
    event.biome('minecraft:desert', 'thaumaturge:ignis', 'kubejs:lux')
})

//hace que escanear glowstone con el escaner marque la clave kubejs:scanned/glowstone, que luego se pide como requisito en una investigacion
//ThaumaturgeEvents.scans(event)
ThaumaturgeEvents.scans(event => {
    //hace que se cree el escaneo kubejs:scan_glowstone
    //event.create(id)
    event.create('kubejs:scan_glowstone')
        //hace que al escanear se marque esta clave
        //.key(clave)
        .key('kubejs:scanned/glowstone')
        //hace que se pueda escanear glowstone y polvo de glowstone como objetos
        //.items(objetos...)
        .items('minecraft:glowstone', 'minecraft:glowstone_dust')
        //hace que se pueda escanear glowstone como bloque
        //.blocks(bloques...)
        .blocks('minecraft:glowstone')
})

//hace que se cree el plano de multibloque kubejs:lux_altar (no es objeto ni bloque, es la forma a construir; se activa con la receta dust_trigger_multiblock de abajo)
//ThaumaturgeEvents.blueprints(event)
//nota: se construye la estructura y se hace clic derecho con Salis Mundus (thaumaturge:salis_mundus) sobre cualquier bloque de ella; la primera layer() es la capa de ARRIBA y la ultima la de ABAJO
ThaumaturgeEvents.blueprints(event => {
    //hace que se cree el plano kubejs:lux_altar
    //event.create(id)
    event.create('kubejs:lux_altar')
        //hace que la capa de arriba sea un anillo de G con el centro vacio (el espacio es "no importa")
        //.layer(fila1, fila2, ...)
        .layer('GGG', 'G G', 'GGG')
        //hace que la capa de abajo sea una cruz de G con L en el centro
        //.layer(fila1, fila2, ...)
        .layer(' G ', 'GLG', ' G ')
        //hace que G se construya con minecraft:glowstone y se convierta en minecraft:sea_lantern al activarse
        //.blockKey(simbolo, bloque que se construye, bloque en el que se convierte)
        .blockKey('G', 'minecraft:glowstone', 'minecraft:sea_lantern')
        //hace que L sea minecraft:lodestone y se conserve al activarse
        //.keepKey(simbolo, bloque)
        .keepKey('L', 'minecraft:lodestone')
})

//hace que se cree la categoria kubejs:radiance con dos entradas encadenadas (basics -> blades) y se modifique alumentum; los textos salen del bloque de idioma de mas abajo
//ThaumaturgeEvents.research(event)
ThaumaturgeEvents.research(event => {
    //hace que se cree la categoria de investigacion kubejs:radiance
    //event.createCategory(id)
    event.createCategory('kubejs:radiance')
        //hace que la categoria use este icono
        //.icon(ruta de la textura)
        .icon('kubejs:textures/research/cat_radiance.png')
        //hace que la categoria use este fondo
        //.background(ruta de la textura)
        .background('thaumaturge:textures/gui/gui_research_back_3.png')
        //hace que la categoria use esta capa superpuesta de fondo
        //.overlayBackground(ruta de la textura)
        .overlayBackground('thaumaturge:textures/gui/gui_research_back_over.png')
        //hace que la categoria aparezca en la posicion 9 de la lista
        //.index(posicion)
        .index(9)
        //hace que la categoria pida 30x ignis y 10x kubejs:lux de formula
        //.formula(aspectos...)
        .formula('30x ignis', '10x kubejs:lux')

    //hace que se cree la entrada de investigacion kubejs:luminous_basics
    //event.create(id)
    event.create('kubejs:luminous_basics')
        //hace que la entrada pertenezca a la categoria kubejs:radiance
        //.category(id de categoria)
        .category('kubejs:radiance')
        //hace que el titulo de la entrada salga de esta clave de idioma
        //.name(clave de idioma)
        .name('research.kubejs.luminous_basics.title')
        //hace que la entrada se coloque en la posicion 0,0 del mapa
        //.position(x, y)
        .position(0, 0)
        //hace que la entrada use minecraft:glowstone de icono
        //.icon(objeto o textura)
        .icon('minecraft:glowstone')
        //hace que la entrada tenga forma redonda
        //.meta(forma)
        .meta('round')
        //hace que la entrada tenga complejidad 1
        //.complexity(complejidad)
        .complexity(1)
        //hace que la entrada pida 4x kubejs:lux y 2x ignis de apuntes
        //.noteAspect(aspectos...)
        .noteAspect('4x kubejs:lux', '2x ignis')
        //hace que la primera etapa pida obtener 8 de glowstone y conocimiento de observacion
        //.stage(stage => stage.text(clave).obtain(objeto, cantidad).requiredKnowledge(tipo, categoria, cantidad))
        .stage(stage => stage
            //hace que la etapa muestre este texto
            //.text(clave de idioma)
            .text('research.kubejs.luminous_basics.stage_0')
            //hace que la etapa pida obtener 8 de minecraft:glowstone
            //.obtain(objeto, cantidad)
            .obtain('minecraft:glowstone', 8)
            //hace que la etapa pida 1 de conocimiento observation de basics
            //.requiredKnowledge(tipo, categoria, cantidad)
            .requiredKnowledge('observation', 'basics', 1))
        //hace que la segunda etapa pida la receta de kubejs:luminous_blade y de conocimiento de teoria
        //.stage(stage => stage.text(clave).recipe(receta).knowledge(tipo, categoria, cantidad))
        .stage(stage => stage
            //hace que la etapa muestre este texto
            //.text(clave de idioma)
            .text('research.kubejs.luminous_basics.stage_1')
            //hace que la etapa pida la receta de kubejs:luminous_blade
            //.recipe(receta)
            .recipe('kubejs:luminous_blade')
            //hace que la etapa de 1 de conocimiento theory de basics
            //.knowledge(tipo, categoria, cantidad)
            .knowledge('theory', 'basics', 1))

    //hace que se cree la entrada de investigacion kubejs:luminous_blades
    //event.create(id)
    event.create('kubejs:luminous_blades')
        //hace que la entrada pertenezca a la categoria kubejs:radiance
        //.category(id de categoria)
        .category('kubejs:radiance')
        //hace que el titulo de la entrada salga de esta clave de idioma
        //.name(clave de idioma)
        .name('research.kubejs.luminous_blades.title')
        //hace que la entrada se coloque en la posicion 2,1 del mapa
        //.position(x, y)
        .position(2, 1)
        //hace que la entrada dependa de kubejs:luminous_basics en su etapa 1
        //.parent(id@etapa)
        .parent('kubejs:luminous_basics@1')
        //hace que la entrada use minecraft:golden_sword de icono
        //.icon(objeto o textura)
        .icon('minecraft:golden_sword')
        //hace que la entrada tenga complejidad 2
        //.complexity(complejidad)
        .complexity(2)
        //hace que la etapa pida fabricar una espada de oro y sume 1 de disonancia
        //.stage(stage => stage.text(clave).craft(objeto, cantidad).warp(cantidad))
        .stage(stage => stage
            //hace que la etapa muestre este texto
            //.text(clave de idioma)
            .text('research.kubejs.luminous_blades.stage_0')
            //hace que la etapa pida fabricar 1 de minecraft:golden_sword
            //.craft(objeto, cantidad)
            .craft('minecraft:golden_sword', 1)
            //hace que la etapa sume 1 de disonancia
            //.warp(cantidad)
            .warp(1))
        //hace que la entrada tenga un apendice que exige haber completado kubejs:luminous_basics
        //.addendum(addendum => addendum.text(clave).requiredResearch(id))
        .addendum(addendum => addendum
            //hace que el apendice muestre este texto
            //.text(clave de idioma)
            .text('research.kubejs.luminous_blades.addendum_0')
            //hace que el apendice exija tener la investigacion kubejs:luminous_basics
            //.requiredResearch(id de investigacion)
            .requiredResearch('kubejs:luminous_basics'))

    //hace que la entrada thaumaturge:alumentum pase a complejidad 1
    //event.modify(id de entrada, entry => entry.complexity(complejidad))
    event.modify('thaumaturge:alumentum', entry => entry.complexity(1))
})

//hace que se anada kubejs:shocking_failure al registro instability_outcome con el JSON tal cual (sirve para cualquier registro de datos de Thaumaturge sin evento propio)
//ThaumaturgeEvents.data(event)
ThaumaturgeEvents.data(event => {
    //hace que se anada un resultado de inestabilidad que da zap de 4 a 7 de dano con peso 2
    //event.add(registro, id, json)
    event.add('instability_outcome', 'kubejs:shocking_failure', {
        effect: {
            type: 'thaumaturge:zap',
            damage: { type: 'minecraft:uniform', min_inclusive: 4, max_inclusive: 7 }
        },
        weight: 2
    })
})

//hace que se anadan los textos en ingles de la investigacion de arriba
//ClientEvents.lang('en_us', event)
ClientEvents.lang('en_us', event => {
    //hace que el titulo de luminous_basics sea Luminous Basics
    //event.add(clave de idioma, texto)
    event.add('research.kubejs.luminous_basics.title', 'Luminous Basics')
    //hace que la etapa 0 de luminous_basics muestre su texto
    //event.add(clave de idioma, texto)
    event.add('research.kubejs.luminous_basics.stage_0', 'Gather glowstone and observe how it holds light.')
    //hace que la etapa 1 de luminous_basics muestre su texto
    //event.add(clave de idioma, texto)
    event.add('research.kubejs.luminous_basics.stage_1', 'Glowstone can be infused to hold a blade of radiance.')
    //hace que el titulo de luminous_blades sea Luminous Blades
    //event.add(clave de idioma, texto)
    event.add('research.kubejs.luminous_blades.title', 'Luminous Blades')
    //hace que la etapa 0 de luminous_blades muestre su texto
    //event.add(clave de idioma, texto)
    event.add('research.kubejs.luminous_blades.stage_0', 'Forge a golden sword to carry the radiance.')
    //hace que el apendice de luminous_blades muestre su texto
    //event.add(clave de idioma, texto)
    event.add('research.kubejs.luminous_blades.addendum_0', 'The blade glows brighter near glowstone.')
})

//hace que se registren las recetas de Thaumaturge de abajo al cargar o recargar los datos del servidor
//ServerEvents.recipes(event)
ServerEvents.recipes(event => {
    //da 4x minecraft:torch poniendo minecraft:charcoal sobre minecraft:stick en la mesa arcana con forma, gastando 15 de vis
    //event.recipes.thaumaturge.arcane_workbench_shaped(resultado, filas, claves, vis).crystals([cristales extra]).research(investigacion)
    event.recipes.thaumaturge.arcane_workbench_shaped(
        '4x minecraft:torch',
        [' C', ' S'],
        { C: 'minecraft:charcoal', S: 'minecraft:stick' },
        15
    ).crystals(['2x ignis']).research('thaumaturge:first_steps')

    //da minecraft:lantern mezclando minecraft:torch y minecraft:iron_nugget en cualquier disposicion en la mesa arcana sin forma, gastando 20 de vis
    //event.recipes.thaumaturge.arcane_workbench_shapeless(resultado, [ingredientes], vis).crystals([cristales extra]).group(grupo)
    event.recipes.thaumaturge.arcane_workbench_shapeless(
        'minecraft:lantern',
        ['minecraft:torch', 'minecraft:iron_nugget'],
        20
    ).crystals(['ignis', 'ordo']).group('lanterns')

    //da 3x minecraft:gunpowder disolviendo minecraft:coal en el crisol con 5x ignis y 5x perditio
    //event.recipes.thaumaturge.crucible(resultado, catalizador, [aspectos]).research(investigacion)
    event.recipes.thaumaturge.crucible(
        '3x minecraft:gunpowder',
        'minecraft:coal',
        ['5x ignis', '5x perditio']
    ).research('thaumaturge:alumentum')

    //da kubejs:luminous_blade infundiendo minecraft:golden_sword (centro del altar) rodeada de glowstone y polvo de glowstone en los pedestales con 50x kubejs:lux y 30x ignis
    //event.recipes.thaumaturge.infusion(resultado, catalizador, [componentes], [aspectos]).instability(inestabilidad).research(investigacion)
    event.recipes.thaumaturge.infusion(
        'kubejs:luminous_blade',
        'minecraft:golden_sword',
        ['minecraft:glowstone', 'minecraft:glowstone', 'minecraft:glowstone_dust'],
        ['50x kubejs:lux', '30x ignis']
    ).instability(3).research('kubejs:luminous_blades')

    //da minecraft:iron_pickaxe irrompible infundiendo con minecraft:redstone_block y minecraft:diamond, 20x fabrico y 20x terra (el catalizador se queda y solo recibe el parche)
    //event.recipes.thaumaturge.infusion([catalizador], [componentes], [aspectos]).catalystPatch(componentes del parche)
    event.recipes.thaumaturge.infusion(
        'minecraft:iron_pickaxe',
        ['minecraft:redstone_block', 'minecraft:diamond'],
        ['20x fabrico', '20x terra']
    ).catalystPatch({ 'minecraft:unbreakable': {} })

    //da el encantamiento arcing infundiendo con minecraft:enchanted_book y #c:storage_blocks/redstone, 40x potentia y 60x aer (la espada de madera es solo el ejemplo que se muestra)
    //event.recipes.thaumaturge.infusion_enchantment(encantamiento, [componentes], [aspectos], objeto de ejemplo).research(investigacion)
    event.recipes.thaumaturge.infusion_enchantment(
        'arcing',
        ['minecraft:enchanted_book', '#c:storage_blocks/redstone'],
        ['40x potentia', '60x aer'],
        'minecraft:wooden_sword'
    ).research('thaumaturge:infusion_enchantment')

    //da una minecraft:iron_chestplate mejorada aplicando thaumaturge:salis_mundus y ambar, 40x praemunio y 20x vitreus, gastando una ambar (#c:gems/amber) por nivel
    //event.recipes.thaumaturge.runic_augment([componentes], ingrediente por nivel, [aspectos], objeto de ejemplo)
    event.recipes.thaumaturge.runic_augment(
        ['thaumaturge:salis_mundus', '#c:gems/amber'],
        '#c:gems/amber',
        ['40x praemunio', '20x vitreus'],
        'minecraft:iron_chestplate'
    )

    //da thaumaturge:crucible aplicando thaumaturge:salis_mundus con clic derecho sobre minecraft:cauldron (tras investigar alquimia)
    //event.recipes.thaumaturge.dust_trigger_simple(resultado, bloque objetivo).research(investigacion)
    event.recipes.thaumaturge.dust_trigger_simple('thaumaturge:crucible', 'minecraft:cauldron').research('thaumaturge:unlock_alchemy@0')

    //da thaumaturge:thaumonomicon aplicando thaumaturge:salis_mundus con clic derecho sobre cualquier estanteria (c:bookshelves)
    //event.recipes.thaumaturge.dust_trigger_tag(resultado, etiqueta de bloque sin #)
    event.recipes.thaumaturge.dust_trigger_tag('thaumaturge:thaumonomicon', 'c:bookshelves')

    //da minecraft:beacon (solo el objeto que se muestra y cuenta como fabricado) aplicando thaumaturge:salis_mundus sobre el plano kubejs:lux_altar construido
    //event.recipes.thaumaturge.dust_trigger_multiblock(resultado, id del plano)
    //nota: gasta 1 polvo y a los 2,5 segundos cada glowstone pasa a linterna marina (la piedra iman se queda); clic derecho sobre cualquier bloque de la estructura
    event.recipes.thaumaturge.dust_trigger_multiblock('minecraft:beacon', 'kubejs:lux_altar')

    //da thaumaturge:wand con remate kubejs:emerald y vara thaumaturge:wood poniendo minecraft:emerald y minecraft:stick en cualquier mesa de crafteo
    //event.shaped(resultado, filas, claves)
    event.shaped(
        'thaumaturge:wand[thaumaturge:wand_parts={cap:"kubejs:emerald",rod:"thaumaturge:wood"}]',
        [' C', ' S', 'C '],
        { C: 'minecraft:emerald', S: 'minecraft:stick' }
    )
})
