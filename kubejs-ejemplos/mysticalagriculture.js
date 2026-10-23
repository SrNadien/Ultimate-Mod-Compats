//hace registrar recetas de Mystical Agriculture con KubeJS
//ServerEvents.recipes(event => { event.recipes.mysticalagriculture.<tipo>(...) })
ServerEvents.recipes(event => {
    //da minecraft:diamond_block infundiendo minecraft:iron_block con 4 minecraft:diamond y 4 mysticalagriculture:diamond_essence alternados en el Altar de Infusion
    //event.recipes.mysticalagriculture.infusion(resultado, entrada, [ingredientes (1 a 8)]).transferComponents(bool)
    event.recipes.mysticalagriculture.infusion('minecraft:diamond_block', 'minecraft:iron_block', [
        'minecraft:diamond', 'mysticalagriculture:diamond_essence',
        'minecraft:diamond', 'mysticalagriculture:diamond_essence',
        'minecraft:diamond', 'mysticalagriculture:diamond_essence',
        'minecraft:diamond', 'mysticalagriculture:diamond_essence'
    ])

    //da minecraft:netherite_ingot infundiendo minecraft:gold_ingot con 4 minecraft:netherite_scrap en el Altar de Infusion, sin copiar componentes del item
    //event.recipes.mysticalagriculture.infusion(resultado, entrada, [ingredientes (1 a 8)]).transferComponents(bool)
    event.recipes.mysticalagriculture.infusion('minecraft:netherite_ingot', 'minecraft:gold_ingot', [
        'minecraft:netherite_scrap', 'minecraft:netherite_scrap',
        'minecraft:netherite_scrap', 'minecraft:netherite_scrap'
    ]).transferComponents(false)

    //da mysticalagriculture:awakened_supremium_block despertando supremium_block con 4 cognizant_dust y 10 de cada esencia (aire, tierra, agua, fuego) en el Altar del Despertar
    //event.recipes.mysticalagriculture.awakening(resultado, entrada, [ingredientes (1 a 4)], [esencias con cantidad (1 a 4)]).transferComponents(bool)
    event.recipes.mysticalagriculture.awakening(
        'mysticalagriculture:awakened_supremium_block',
        'mysticalagriculture:supremium_block',
        ['mysticalagriculture:cognizant_dust', 'mysticalagriculture:cognizant_dust', 'mysticalagriculture:cognizant_dust', 'mysticalagriculture:cognizant_dust'],
        ['10x mysticalagriculture:air_essence', '10x mysticalagriculture:earth_essence', '10x mysticalagriculture:water_essence', '10x mysticalagriculture:fire_essence']
    )

    //da el encantamiento minecraft:efficiency consumiendo 16 minecraft:redstone y 64 mysticalagriculture:experience_essence en el Encantador de Mystical (como se aplica: sin confirmar)
    //event.recipes.mysticalagriculture.enchanter(encantamiento, [{item, count} (1 a 2 ingredientes)])
    event.recipes.mysticalagriculture.enchanter('minecraft:efficiency', [
        { item: 'minecraft:redstone', count: 16 },
        { item: 'mysticalagriculture:experience_essence', count: 64 }
    ])

    //da 2x mysticalagriculture:iron_essence reprocesando mysticalagriculture:iron_seeds en el Reprocesador de Semillas
    //event.recipes.mysticalagriculture.reprocessor(resultado, entrada)
    event.recipes.mysticalagriculture.reprocessor('2x mysticalagriculture:iron_essence', 'mysticalagriculture:iron_seeds')

    //da 0.5 de alma de tipo mysticalagriculture:blaze extrayendola de minecraft:blaze_rod en el Extractor de Almas
    //event.recipes.mysticalagriculture.soul_extraction({type: tipoAlma, souls: decimal}, entrada)
    event.recipes.mysticalagriculture.soul_extraction({ type: 'mysticalagriculture:blaze', souls: 0.5 }, 'minecraft:blaze_rod')

    //da minecraft:farmland combinando cualquier azada (mysticalagriculture:all_hoes) con minecraft:dirt en la mesa de crafteo
    //event.recipes.mysticalagriculture.farmland_till(resultado, [ingredientes (1 a 9)], grupo opcional)
    event.recipes.mysticalagriculture.farmland_till('minecraft:farmland', [{ type: 'mysticalagriculture:all_hoes' }, 'minecraft:dirt'])

    //hace aparecer una minecraft:cow (peso 1) gastando 12 mysticalagriculture:cow_essence en el Spawner de Soulium
    //event.recipes.mysticalagriculture.soulium_spawner({item, count}, [{entity, weight}, ...])
    event.recipes.mysticalagriculture.soulium_spawner({ item: 'mysticalagriculture:cow_essence', count: 12 }, [
        { entity: 'minecraft:cow', weight: 1 }
    ])

    //convierte un Soul Jar lleno en uno vacio (receta especial sin argumentos)
    //event.recipes.mysticalagriculture.soul_jar_empty()
    event.recipes.mysticalagriculture.soul_jar_empty()
})

//hace registrar recetas de semillas de Mystical Agriculture (crafteo, infusion, reprocesado); si el item es de otro mod el JSON lleva neoforge:conditions mod_loaded
//MysticalEvents.recipes(event => { event.seed(...) ; event.seedCrafting(...) ; event.seedInfusion(...) ; event.seedReprocessing(...) })
MysticalEvents.recipes(event => {
    //da mysticalagriculture:thaumaturge_thaumium_seeds (por crafteo e infusion) mezclando prosperity_seed_base con inferium_essence y minecraft:iron_ingot; lleva mod_loaded thaumaturge si el catalogo lo exige
    //event.seed(semillas, baseSemilla, esencia, material)   semillas admite 'Nx namespace:item'
    event.seed(
        'mysticalagriculture:thaumaturge_thaumium_seeds',
        'mysticalagriculture:prosperity_seed_base',
        'mysticalagriculture:inferium_essence',
        'minecraft:iron_ingot'
    )

    //da mysticalagriculture:copper_seeds en la mesa de crafteo (forma MEM/ESE/MEM: M = material, E = esencia, S = base) con prosperity_seed_base, inferium_essence y lingotes de cobre #c:ingots/copper
    //event.seedCrafting(semillas, baseSemilla, esencia, material)
    event.seedCrafting(
        'mysticalagriculture:copper_seeds',
        'mysticalagriculture:prosperity_seed_base',
        'mysticalagriculture:inferium_essence',
        '#c:ingots/copper'
    )

    //da mysticalagriculture:copper_seeds infundiendo prosperity_seed_base con 4 pares alternados de #c:ingots/copper e inferium_essence en el Altar de Infusion
    //event.seedInfusion(semillas, baseSemilla, esencia, material)
    event.seedInfusion(
        'mysticalagriculture:copper_seeds',
        'mysticalagriculture:prosperity_seed_base',
        'mysticalagriculture:inferium_essence',
        '#c:ingots/copper'
    )

    //da 2x mysticalagriculture:copper_essence reprocesando mysticalagriculture:copper_seeds en el Reprocesador de Semillas
    //event.seedReprocessing(semillas, esencia)   esencia admite 'Nx namespace:item'
    event.seedReprocessing('mysticalagriculture:copper_seeds', '2x mysticalagriculture:copper_essence')
})
