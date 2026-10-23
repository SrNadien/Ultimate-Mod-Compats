//registra recetas de ExtendedAE (Crystal Assembler, Circuit Slicer, Crystal Fixer) al cargar los datos del servidor
//ServerEvents.recipes(event => { ... })
ServerEvents.recipes(event => {
    //da 4x extendedae:entro_ingot mezclando 4x polvo de entro, 4x lingote de oro y 4x lapislazuli con 100 mB de agua, en el Crystal Assembler
    //event.recipes.extendedae.crystal_assembler(resultado, [ingredientes], fluido)
    event.recipes.extendedae.crystal_assembler(
        '4x extendedae:entro_ingot',
        ['4x #c:dusts/entro', '4x #c:ingots/gold', '4x minecraft:lapis_lazuli'],
        Fluid.of('minecraft:water', 100)
    ).id('umc_ejemplos:extendedae/lingote_entro')

    //da extendedae:circuit_cutter mezclando extendedae:machine_frame, 8x ae2:engineering_processor y minecraft:stonecutter sin fluido, en el Crystal Assembler
    //event.recipes.extendedae.crystal_assembler(resultado, [ingredientes])   (el fluido es opcional)
    event.recipes.extendedae.crystal_assembler(
        'extendedae:circuit_cutter',
        ['extendedae:machine_frame', '8x ae2:engineering_processor', 'minecraft:stonecutter']
    ).id('umc_ejemplos:extendedae/cortador_circuitos')

    //da 9x ae2:printed_silicon cortando 1x ae2:fluix_block, en el Circuit Slicer
    //event.recipes.extendedae.circuit_cutter(resultado, ingrediente)
    event.recipes.extendedae.circuit_cutter('9x ae2:printed_silicon', 'ae2:fluix_block')
        .id('umc_ejemplos:extendedae/corte_acumulacion')

    //convierte ae2:chipped_budding_quartz en ae2:flawed_budding_quartz gastando ae2:charged_certus_quartz_crystal como combustible, con 8000 de 10000 (80%) de probabilidad, en el Crystal Fixer (cuanto combustible gasta por intento: sin confirmar)
    //event.recipes.extendedae.crystal_fixer(resultado, entrada, combustible, probabilidad_1_a_10000)
    event.recipes.extendedae.crystal_fixer(
        'ae2:flawed_budding_quartz',
        'ae2:chipped_budding_quartz',
        'ae2:charged_certus_quartz_crystal',
        8000
    ).id('umc_ejemplos:extendedae/arreglo_cristal')
})
