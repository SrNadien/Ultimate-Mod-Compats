//hace generar los data maps de combustibles de GeneratorGalore (solid_fuel_map y fluid_fuel_map) al generar los datos
//ServerEvents.generateData(fase, event => { ... })
ServerEvents.generateData('after_mods', event => {
    //hace empezar la definicion de combustibles; no se escribe nada hasta llamar a .write(event) (el data map se crea con replace=false, suma a los del mod)
    //GeneratorGalore.fuels()
    GeneratorGalore.fuels()
        //hace que el generador indicado acepte combustibles solidos (sin confirmar si el Iron Generator lee este mapa)
        //.solid(id_del_generador)
        .solid('generatorgalore:iron_generator')
        //da 40 FE/t durante 1600 ticks quemando 1 minecraft:coal, a velocidad de consumo 1 (por defecto 1 si se omite)
        //.fuel(item_o_#tag, ticks_de_quemado, FE_por_tick, velocidad_de_consumo)
        .fuel('minecraft:coal', 1600, 40, 1)
        //da 20 FE/t durante 300 ticks quemando cualquier #minecraft:logs, a velocidad de consumo 1 (por omision)
        //.fuel(item_o_#tag, ticks_de_quemado, FE_por_tick)
        .fuel('#minecraft:logs', 300, 20)
        //hace cerrar la lista de combustibles de este generador y volver a elegir otro
        //.done()
        .done()
        //hace que el generador indicado acepte combustibles solidos
        //.solid(id_del_generador)
        .solid('generatorgalore:ender_generator')
        //da 96 FE/t durante 1600 ticks quemando 1 minecraft:ender_pearl, a velocidad de consumo 1
        //.fuel(item_o_#tag, ticks_de_quemado, FE_por_tick, velocidad_de_consumo)
        .fuel('minecraft:ender_pearl', 1600, 96, 1)
        //da 80 FE/t durante 3200 ticks quemando 1 minecraft:ender_eye, a velocidad de consumo 1
        //.fuel(item_o_#tag, ticks_de_quemado, FE_por_tick, velocidad_de_consumo)
        .fuel('minecraft:ender_eye', 3200, 80, 1)
        //hace cerrar la lista de combustibles de este generador
        //.done()
        .done()
        //hace que el generador indicado acepte combustibles liquidos
        //.fluid(id_del_generador)
        .fluid('generatorgalore:magmatic_generator')
        //da 40 FE/t consumiendo 0.4 mB/t de cualquier fluido de la tag #c:lava
        //.fuel(fluido_o_#tag, FE_por_tick, mB_por_tick_consumidos)   (el ultimo es opcional, 1 por defecto)
        .fuel('#c:lava', 40, 0.4)
        //hace cerrar la lista de combustibles de este generador
        //.done()
        .done()
        //hace escribir los dos data maps (solidos y liquidos) en el paquete de datos generado
        //.write(event)
        .write(event)
})
