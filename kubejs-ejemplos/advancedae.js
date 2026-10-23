//registra recetas de AdvancedAE (Reaction Chamber) al cargar los datos del servidor
//ServerEvents.recipes(event => { ... })
ServerEvents.recipes(event => {
    //da 64x ae2:charged_certus_quartz_crystal mezclando 64x ae2:certus_quartz_crystal con 1000 mB de agua y 1300000 de energia (FE), en la Reaction Chamber
    //event.recipes.advanced_ae.reaction(resultado, [ingredientes], fluido, energia)
    event.recipes.advanced_ae.reaction(
        '64x ae2:charged_certus_quartz_crystal',
        ['64x ae2:certus_quartz_crystal'],
        Fluid.of('minecraft:water', 1000),
        1300000
    ).id('umc_ejemplos:advancedae/cuarzo_cargado')

    //da 1x advanced_ae:quantum_alloy mezclando 4x cobre, 4x shattered_singularity y 4x singularity con 1000 mB de quantum_infusion y 200000 de energia (FE), en la Reaction Chamber
    //event.recipes.advanced_ae.reaction(resultado, [ingredientes], fluido, energia)
    event.recipes.advanced_ae.reaction(
        'advanced_ae:quantum_alloy',
        ['4x minecraft:copper_ingot', '4x advanced_ae:shattered_singularity', '4x ae2:singularity'],
        Fluid.of('advanced_ae:quantum_infusion_source', 1000),
        200000
    ).id('umc_ejemplos:advancedae/aleacion_cuantica')

    //da 1000x advanced_ae:quantum_infusion_source mezclando advanced_ae:quantum_infused_dust con 4000 mB de agua y 20000 de energia (FE), en la Reaction Chamber
    //event.recipes.advanced_ae.reaction(resultado, [ingredientes], fluido, energia)
    event.recipes.advanced_ae.reaction(
        '1000x advanced_ae:quantum_infusion_source',
        ['advanced_ae:quantum_infused_dust'],
        Fluid.of('minecraft:water', 4000),
        20000
    ).id('umc_ejemplos:advancedae/fluido_cuantico')
})
