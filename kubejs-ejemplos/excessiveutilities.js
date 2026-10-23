//registra recetas de Excessive Utilities (maquinas, combustibles de generadores y crafteos especiales) al cargar los datos del servidor
//ServerEvents.recipes(event => { ... })
ServerEvents.recipes(event => {
    //da 2x minecraft:magenta_dye triturando minecraft:allium, en el Crusher (200 ticks y 20 FE/t por defecto)
    //event.recipes.excessive_utilities.crusher(resultado, ingrediente)
    event.recipes.excessive_utilities.crusher('2x minecraft:magenta_dye', 'minecraft:allium')
        .id('umc_ejemplos:excessive_utilities/triturador_simple')

    //da minecraft:gravel triturando minecraft:cobblestone, mas minecraft:flint con 25% de probabilidad, en 100 ticks gastando 40 FE/t, en el Crusher
    //event.recipes.excessive_utilities.crusher(resultado, ingrediente, resultado_secundario, probabilidad_0_a_1).ticks(ticks).fePerTick(fe_por_tick)
    event.recipes.excessive_utilities.crusher('minecraft:gravel', 'minecraft:cobblestone', 'minecraft:flint', 0.25)
        .ticks(100)
        .fePerTick(40)
        .id('umc_ejemplos:excessive_utilities/triturador_completo')

    //da excessive_utilities:block_of_enchanted_metal mezclando 1x bloque de oro (#c:storage_blocks/gold) con 9x lapislazuli (#c:gems/lapis), gastando 40 FE/t durante 600 ticks, en el Enchanter
    //event.recipes.excessive_utilities.enchanter(resultado, izquierda, derecha, fe_por_tick, ticks).enchantingPower(poder)   (poder opcional, 15 por defecto; su efecto exacto: sin confirmar)
    event.recipes.excessive_utilities.enchanter('excessive_utilities:block_of_enchanted_metal', '#c:storage_blocks/gold', '9x #c:gems/lapis', 40, 600)
        .enchantingPower(15)
        .id('umc_ejemplos:excessive_utilities/encantador')

    //da excessive_utilities:ender_marker colocando 1 perla de ender (E) sobre 2 ender_infused_obsidian (O) en vertical, con 300 ticks de cristal, en el QED
    //event.recipes.excessive_utilities.qed(resultado, [patron], {letra: ingrediente}, ticks_de_cristal)
    event.recipes.excessive_utilities.qed('excessive_utilities:ender_marker', ['E', 'O', 'O'], {
        E: '#c:ender_pearls',
        O: 'excessive_utilities:ender_infused_obsidian'
    }, 300).id('umc_ejemplos:excessive_utilities/qed')

    //da excessive_utilities:heating_coil resonando minecraft:iron_bars con un coste de 16 de Grid Power, en el Resonator
    //event.recipes.excessive_utilities.resonator(resultado, ingrediente, coste_gp)
    event.recipes.excessive_utilities.resonator('excessive_utilities:heating_coil', 'minecraft:iron_bars', 16)
        .id('umc_ejemplos:excessive_utilities/resonador')

    //da minecraft:basalt cuando el World Interaction Upgrade actua sobre minecraft:soul_soil con minecraft:blue_ice y minecraft:lava en los lados
    //event.recipes.excessive_utilities.world_interaction_item(resultado, bloque_debajo, [bloques_adyacentes])
    event.recipes.excessive_utilities.world_interaction_item('minecraft:basalt', 'minecraft:soul_soil', ['minecraft:blue_ice', 'minecraft:lava'])
        .id('umc_ejemplos:excessive_utilities/interaccion_objeto')

    //da minecraft:stone cuando el World Interaction Upgrade actua sobre cualquier #c:stones con minecraft:water y minecraft:lava en los lados y minecraft:water detras
    //event.recipes.excessive_utilities.world_interaction_item(resultado, bloque_debajo, [bloques_adyacentes]).behind(bloque_detras)
    event.recipes.excessive_utilities.world_interaction_item('minecraft:stone', '#c:stones', ['minecraft:water', 'minecraft:lava'])
        .behind('minecraft:water')
        .id('umc_ejemplos:excessive_utilities/interaccion_objeto_detras')

    //da 1 mB de minecraft:water cuando el World Interaction Upgrade actua sobre minecraft:water con dos minecraft:water en los lados
    //event.recipes.excessive_utilities.world_interaction_fluid(fluido_resultado, bloque_debajo, [bloques_adyacentes])
    event.recipes.excessive_utilities.world_interaction_fluid(Fluid.of('minecraft:water', 1), 'minecraft:water', ['minecraft:water', 'minecraft:water'])
        .id('umc_ejemplos:excessive_utilities/interaccion_fluido')

    //da 40 FE/t durante 400 ticks quemando 1 hueso (#c:bones), en el Death Generator
    //event.recipes.excessive_utilities.single_item_fuel(tipo_de_generador, ingrediente, fe_por_tick, duracion_ticks)
    event.recipes.excessive_utilities.single_item_fuel('death', '#c:bones', 40, 400)
        .id('umc_ejemplos:excessive_utilities/combustible_muerte')

    //da 3200 FE/t durante 125 ticks quemando polvo de redstone (#c:dusts/redstone) con 250 mB de lava, en el Heated Redstone Generator
    //event.recipes.excessive_utilities.item_and_fluid_fuel(tipo_de_generador, ingrediente, fluido, fe_por_tick, duracion_ticks)
    event.recipes.excessive_utilities.item_and_fluid_fuel('heated_redstone', '#c:dusts/redstone', Fluid.of('minecraft:lava', 250), 3200, 125)
        .id('umc_ejemplos:excessive_utilities/combustible_redstone')

    //da 100 FE/t durante 1 tick consumiendo 1 mB de minecraft:lava, en el Magmatic Generator
    //event.recipes.excessive_utilities.magmatic_fuel(fluido, fe_por_tick, duracion_ticks)
    event.recipes.excessive_utilities.magmatic_fuel(Fluid.of('minecraft:lava', 1), 100, 1)
        .id('umc_ejemplos:excessive_utilities/combustible_magmatico')

    //da excessive_utilities:semi_unstable_nugget en la mesa de crafteo (con forma, vertical N/S/D) con pepita de hierro, Division Sigil y diamante, solo si el Division Sigil esta invertido
    //event.recipes.excessive_utilities.shaped_division(resultado, [patron], {letra: ingrediente}).inversion(inverted|uninverted|either)   (inversion opcional, either por defecto)
    event.recipes.excessive_utilities.shaped_division('excessive_utilities:semi_unstable_nugget', ['N', 'S', 'D'], {
        N: '#c:nuggets/iron',
        S: 'excessive_utilities:division_sigil',
        D: '#c:gems/diamond'
    }).inversion('inverted').id('umc_ejemplos:excessive_utilities/division')

    //da excessive_utilities:unstable_ingot en la mesa de crafteo (con forma, 3x3) con 8 lingotes de hierro y 1 diamante en el centro, solo en estado estable
    //event.recipes.excessive_utilities.shaped_unstable(resultado, [patron], {letra: ingrediente}).stability(stable|unstable|either)   (stability opcional, either por defecto)
    event.recipes.excessive_utilities.shaped_unstable('excessive_utilities:unstable_ingot', ['III', 'IDI', 'III'], {
        I: '#c:ingots/iron',
        D: '#c:gems/diamond'
    }).stability('stable').id('umc_ejemplos:excessive_utilities/inestable')

    //da 7x excessive_utilities:colored_bricks_black en la mesa de crafteo (con forma, 3x3) con 8 bloques de ladrillo, tinte negro y el Paintbrush, que NO se consume
    //event.recipes.excessive_utilities.keep_paintbrush(resultado, [patron], {letra: ingrediente})
    event.recipes.excessive_utilities.keep_paintbrush('7x excessive_utilities:colored_bricks_black', ['III', 'IDI', 'IPI'], {
        D: '#c:dyes/black',
        I: '#c:brick_blocks',
        P: 'excessive_utilities:paintbrush'
    }).id('umc_ejemplos:excessive_utilities/pincel')

    //da 8x excessive_utilities:ender_shard en la mesa de crafteo (con forma, vertical P/C) con una perla de ender y el Glass Cutter, que pierde durabilidad
    //event.recipes.excessive_utilities.damage_glass_cutter(resultado, [patron], {letra: ingrediente})
    event.recipes.excessive_utilities.damage_glass_cutter('8x excessive_utilities:ender_shard', ['P', 'C'], {
        P: '#c:ender_pearls',
        C: 'excessive_utilities:glass_cutter'
    }).id('umc_ejemplos:excessive_utilities/cortavidrios')

    //da excessive_utilities:quantum_quarry en la mesa de crafteo (con forma, 3x3) con piedras del End, stoneburnt y un Magical Snow Globe (G) ya activado (sin confirmar el detalle)
    //event.recipes.excessive_utilities.snow_globe(resultado, [patron], {letra: ingrediente})
    event.recipes.excessive_utilities.snow_globe('excessive_utilities:quantum_quarry', ['ESE', 'SGS', 'ESE'], {
        E: '#c:end_stones',
        G: 'excessive_utilities:magical_snow_globe',
        S: 'excessive_utilities:stoneburnt'
    }).id('umc_ejemplos:excessive_utilities/bola_nieve')
})
