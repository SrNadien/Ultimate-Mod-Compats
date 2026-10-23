//hace que se registren las recetas de Create de abajo al cargar o recargar los datos del servidor
//ServerEvents.recipes(event)
ServerEvents.recipes(event => {
    //da create:andesite_casing aplicando create:andesite_alloy sobre un tronco sin corteza; en el juego: aleacion en la mano y clic derecho sobre el tronco
    //event.recipes.create.item_application(resultado, [bloque en el mundo, objeto en la mano])
    event.recipes.create.item_application('create:andesite_casing', ['#c:stripped_logs', 'create:andesite_alloy'])
        .id('umc_ejemplos:create/casing_andamio')

    //da create:brass_casing aplicando create:brass_ingot sobre un tronco sin corteza sin gastar el lingote (keepHeldItem); en el juego: clic derecho con el lingote
    //event.recipes.create.item_application(resultado, [bloque en el mundo, objeto en la mano]).keepHeldItem()
    event.recipes.create.item_application('create:brass_casing', ['#c:stripped_logs', 'create:brass_ingot'])
        .keepHeldItem()
        .id('umc_ejemplos:create/casing_laton_conserva')

    //da create:copper_casing aplicando minecraft:copper_ingot sobre un tronco sin corteza (alias camelCase); en el juego: clic derecho con el lingote
    //event.recipes.itemApplication(resultado, [bloque en el mundo, objeto en la mano])
    event.recipes.itemApplication('create:copper_casing', ['#c:stripped_logs', 'minecraft:copper_ingot'])
        .id('umc_ejemplos:create/casing_cobre_alias')

    //convierte minecraft:emerald en minecraft:amethyst_shard: sale en JEI (Conversion misteriosa) y con haunting() tambien funciona en el juego (ventilador tras fuego de almas)
    //event.recipes.create.conversion(resultado, entrada).id(id).haunting()
    //nota: sin haunting() solo se muestra en JEI, Create no ejecuta conversiones genericas
    event.recipes.create.conversion('minecraft:amethyst_shard', 'minecraft:emerald')
        .id('umc_ejemplos:create/conversion_esmeralda_amatista')
        .haunting()

    //convierte minecraft:grass_block en minecraft:glowstone (alias camelCase de conversion): sale en JEI y con haunting() funciona en el juego
    //event.recipes.mysteriousConversion(resultado, entrada).id(id).haunting()
    //nota: en el juego se tira el bloque de hierba al suelo y se le pasa el aire de un Encased Fan (ventilador) a traves de fuego de almas; no se hace clic en nada
    event.recipes.mysteriousConversion('minecraft:glowstone', 'minecraft:grass_block')
        .id('umc_ejemplos:create/conversion_hierba_piedra_luminosa')
        .haunting()

    //da minecraft:amethyst_shard hechizando minecraft:emerald con un ventilador (Encased Fan) detras de fuego de almas; hace real la conversion de arriba (requiere addon KubeJS Create)
    //event.recipes.create.haunting(resultado, entrada)
    event.recipes.create.haunting('minecraft:amethyst_shard', 'minecraft:emerald')
        .id('umc_ejemplos:create/haunting_esmeralda_amatista')

    //da minecraft:diamond convirtiendo minecraft:coal cuando se hace clic derecho con el carbon en la mano sobre un cerdo, como el blaze burner (item + entidad); tambien sale en JEI (Conversion misteriosa)
    //event.recipes.create.conversion(resultado, entrada).entity(entidad)
    event.recipes.create.conversion('minecraft:diamond', 'minecraft:coal')
        .id('umc_ejemplos:create/conversion_carbon_diamante')
        .entity('minecraft:pig')
})
