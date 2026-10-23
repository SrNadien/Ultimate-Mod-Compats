//hace que se registren las recetas de AE2 de abajo al cargar o recargar los datos del servidor
//ServerEvents.recipes(event)
ServerEvents.recipes(event => {
    //da ae2:calculation_processor prensando minecraft:redstone con ae2:printed_calculation_processor arriba y ae2:printed_silicon abajo en el inscriptor
    //event.recipes.ae2.inscriber(resultado, centro, arriba, abajo).press()
    event.recipes.ae2.inscriber('ae2:calculation_processor', 'minecraft:redstone', 'ae2:printed_calculation_processor', 'ae2:printed_silicon')
        .press()
        .id('umc_ejemplos:ae2/inscriber_procesador')

    //da ae2:logic_processor_press inscribiendo minecraft:iron_block (se consume) con ae2:logic_processor_press arriba (se conserva) en el inscriptor
    //event.recipes.ae2.inscriber(resultado, centro, arriba).inscribe()
    event.recipes.ae2.inscriber('ae2:logic_processor_press', 'minecraft:iron_block', 'ae2:logic_processor_press')
        .inscribe()
        .id('umc_ejemplos:ae2/inscriber_molde')

    //da ae2:flawless_budding_quartz mezclando ae2:charged_certus_quartz_crystal y ae2:flawed_budding_quartz tirados al agua durante unos 3 segundos sin que se alejen
    //event.recipes.ae2.transform(resultado, [ingredientes])
    event.recipes.ae2.transform('ae2:flawless_budding_quartz', ['ae2:charged_certus_quartz_crystal', 'ae2:flawed_budding_quartz'])
        .id('umc_ejemplos:ae2/transform_agua')

    //da 2x minecraft:diamond mezclando ae2:charged_certus_quartz_crystal y minecraft:coal_block tirados al suelo con una explosion de TNT al lado (no los destruye)
    //event.recipes.ae2.transform(resultado, [ingredientes]).explosion()
    event.recipes.ae2.transform('2x minecraft:diamond', ['ae2:charged_certus_quartz_crystal', 'minecraft:coal_block'])
        .explosion()
        .id('umc_ejemplos:ae2/transform_explosion')

    //da minecraft:netherite_block mezclando minecraft:netherite_ingot y minecraft:netherite_scrap tirados a la lava durante unos 3 segundos (solo objetos a prueba de fuego)
    //event.recipes.ae2.transform(resultado, [ingredientes]).fluid(etiqueta de fluido con o sin #)
    event.recipes.ae2.transform('minecraft:netherite_block', ['minecraft:netherite_ingot', 'minecraft:netherite_scrap'])
        .fluid('minecraft:lava')
        .id('umc_ejemplos:ae2/transform_lava')

    //da ae2:guide cargando minecraft:book en el cargador de AE2 con energia
    //event.recipes.ae2.charger(resultado, ingrediente)
    event.recipes.ae2.charger('ae2:guide', 'minecraft:book')
        .id('umc_ejemplos:ae2/charger_guia')

    //hace que las pepitas de hierro (#c:nuggets/iron) sirvan de municion del canon de materia con peso atomico 55.845 (a mas peso, mas dano)
    //event.recipes.ae2.matter_cannon(municion, peso)
    event.recipes.ae2.matter_cannon('#c:nuggets/iron', 55.845)
        .id('umc_ejemplos:ae2/municion_hierro')

    //convierte minecraft:stone en minecraft:cobblestone enfriando con el manipulador de entropia
    //event.recipes.ae2.entropy(modo).inputBlock(bloque de entrada).outputBlock(bloque de salida)
    event.recipes.ae2.entropy('cool')
        .inputBlock('minecraft:stone')
        .outputBlock('minecraft:cobblestone')
        .id('umc_ejemplos:ae2/entropia_piedra')

    //convierte minecraft:ice en minecraft:water calentando con el manipulador de entropia
    //event.recipes.ae2.entropy(modo).inputBlock(bloque de entrada).outputFluid(fluido de salida)
    event.recipes.ae2.entropy('heat')
        .inputBlock('minecraft:ice')
        .outputFluid('minecraft:water')
        .id('umc_ejemplos:ae2/entropia_hielo')

    //convierte minecraft:flowing_water en minecraft:snowball (cae como objeto) enfriando con el manipulador de entropia
    //event.recipes.ae2.entropy(modo).inputFluid(fluido de entrada).drop(objeto que cae)
    event.recipes.ae2.entropy('cool')
        .inputFluid('minecraft:flowing_water')
        .drop('minecraft:snowball')
        .id('umc_ejemplos:ae2/entropia_nieve')

    //da ae2:item_storage_cell_64k y devuelve ae2:cell_component_4k juntando ae2:item_storage_cell_4k con ae2:cell_component_64k en una mesa de crafteo
    //event.recipes.ae2.storage_cell_upgrade(celda que entra, componente que entra, celda que sale, componente que sale)
    event.recipes.ae2.storage_cell_upgrade('ae2:item_storage_cell_4k', 'ae2:cell_component_64k', 'ae2:item_storage_cell_64k', 'ae2:cell_component_4k')
        .id('umc_ejemplos:ae2/mejora_celda')

    //da ae2:item_cell_housing y ae2:cell_component_4k desmontando una ae2:item_storage_cell_4k vacia usada en la mano agachado
    //event.recipes.ae2.storage_cell_disassembly(celda, [objetos que se devuelven])
    event.recipes.ae2.storage_cell_disassembly('ae2:item_storage_cell_4k', ['ae2:item_cell_housing', 'ae2:cell_component_4k'])
        .id('umc_ejemplos:ae2/desmontar_celda')

    //da ae2:16k_crafting_storage usando ae2:cell_component_16k sobre una unidad de crafteo
    //event.recipes.ae2.crafting_unit_transform(objeto que se usa, bloque resultante)
    event.recipes.ae2.crafting_unit_transform('ae2:cell_component_16k', 'ae2:16k_crafting_storage')
        .id('umc_ejemplos:ae2/unidad_crafteo')

    //da 4x ae2:cable_anchor cortando con el cuchillo de AE2 (#ae2:knife, se desgasta pero no se consume) y un lingote de metal (#ae2:metal_ingots) en la cuadricula de crafteo
    //event.recipes.ae2.quartz_cutting(resultado, [ingredientes])
    event.recipes.ae2.quartz_cutting('4x ae2:cable_anchor', ['#ae2:knife', '#ae2:metal_ingots'])
        .id('umc_ejemplos:ae2/corte_cuarzo')
})
