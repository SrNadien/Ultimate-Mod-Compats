//registra recetas de FunctionalStorage (Compacting Drawer y cajones con marco) al cargar los datos del servidor
//ServerEvents.recipes(event => { ... })
ServerEvents.recipes(event => {
    //hace que el Compacting Drawer compacte 4x minecraft:amethyst_shard en 1x minecraft:amethyst_block (y lo descompacte al reves)
    //event.recipes.functionalstorage.custom_compacting(item_inferior, item_superior)
    event.recipes.functionalstorage.custom_compacting('4x minecraft:amethyst_shard', 'minecraft:amethyst_block')
        .id('umc_ejemplos:functionalstorage/compactar_amatista')

    //hace que el Compacting Drawer compacte 9x minecraft:iron_nugget en 1x minecraft:iron_ingot (y lo descompacte al reves)
    //event.recipes.functionalstorage.custom_compacting(item_inferior, item_superior)
    event.recipes.functionalstorage.custom_compacting('9x minecraft:iron_nugget', 'minecraft:iron_ingot')
        .id('umc_ejemplos:functionalstorage/compactar_hierro')

    //da 1x functionalstorage:compacting_framed_drawer en la mesa de crafteo (con forma) rodeando un Compacting Drawer (D) con pepitas de hierro (S) y copiandole al resultado el componente functionalstorage:tile (indice 4 = casilla central 3x3, sin confirmar)
    //event.recipes.functionalstorage.copy_components(resultado, [patron], {letra: ingrediente}, [componentes_a_copiar], indice_casilla_origen)
    event.recipes.functionalstorage.copy_components('functionalstorage:compacting_framed_drawer', ['S S', 'SDS', ' S '], {
        D: 'functionalstorage:compacting_drawer',
        S: '#c:nuggets/iron'
    }, ['functionalstorage:tile'], 4).id('umc_ejemplos:functionalstorage/cajon_marco_compactador')
})
