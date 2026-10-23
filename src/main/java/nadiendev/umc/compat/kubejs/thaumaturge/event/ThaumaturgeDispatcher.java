package nadiendev.umc.compat.kubejs.thaumaturge.event;

import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.generator.KubeDataGenerator;
import dev.latvian.mods.kubejs.script.ConsoleJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import java.util.function.Function;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.compat.kubejs.thaumaturge.data.AspectsKubeEvent;
import nadiendev.umc.compat.kubejs.thaumaturge.data.BlueprintsKubeEvent;
import nadiendev.umc.compat.kubejs.thaumaturge.data.RawDataKubeEvent;
import nadiendev.umc.compat.kubejs.thaumaturge.data.ResearchKubeEvent;
import nadiendev.umc.compat.kubejs.thaumaturge.data.ScansKubeEvent;
import nadiendev.umc.compat.kubejs.thaumaturge.data.ThaumaturgeDataEvent;
import nadiendev.umc.compat.kubejs.thaumaturge.startup.GolemTraitsKubeEvent;
import nadiendev.umc.compat.kubejs.thaumaturge.startup.MobTraitsKubeEvent;
import nadiendev.umc.compat.kubejs.thaumaturge.startup.StartupRegistration;
import nadiendev.umc.compat.kubejs.thaumaturge.startup.WandCapsKubeEvent;
import nadiendev.umc.compat.kubejs.thaumaturge.startup.WandRodsKubeEvent;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.RegisterEvent;

public final class ThaumaturgeDispatcher {

    private static boolean installed;

    private ThaumaturgeDispatcher() {
    }

    public static synchronized void installStartupBridge() {
        if (installed) {
            return;
        }
        installed = true;
        IEventBus bus = ModList.get().getModContainerById(UltimateModCompats.MODID).map(ModContainer::getEventBus).orElse(null);
        if (bus == null) {
            ConsoleJS.STARTUP.error("No se encontro el bus de eventos de " + UltimateModCompats.MODID + ", ThaumaturgeStartup no se activara");
            return;
        }
        bus.addListener(ThaumaturgeDispatcher::onRegister);
    }

    private static void onRegister(RegisterEvent event) {
        ResourceLocation key = event.getRegistryKey().location();
        if (!"thaumaturge".equals(key.getNamespace())) {
            return;
        }
        switch (key.getPath()) {
            case "wand_cap" -> dispatch(event, ThaumaturgeStartup.WAND_CAPS, WandCapsKubeEvent::new);
            case "wand_rod" -> dispatch(event, ThaumaturgeStartup.WAND_RODS, WandRodsKubeEvent::new);
            case "golem_trait" -> dispatch(event, ThaumaturgeStartup.GOLEM_TRAITS, GolemTraitsKubeEvent::new);
            case "mob_trait" -> dispatch(event, ThaumaturgeStartup.MOB_TRAITS, MobTraitsKubeEvent::new);
            default -> { }
        }
    }

    private static <E extends StartupRegistration> void dispatch(RegisterEvent event, EventHandler handler, Function<RegisterEvent, E> factory) {
        if (!handler.hasListeners()) {
            return;
        }
        try {
            E kubeEvent = factory.apply(event);
            handler.post(ScriptType.STARTUP, kubeEvent);
            kubeEvent.flush();
        } catch (RuntimeException ex) {
            ConsoleJS.STARTUP.error("Fallo al ejecutar ThaumaturgeStartup." + handler.name, ex);
        }
    }

    public static void postData(KubeDataGenerator generator) {
        postData(ThaumaturgeEvents.RESEARCH, new ResearchKubeEvent(generator));
        postData(ThaumaturgeEvents.ASPECTS, new AspectsKubeEvent(generator));
        postData(ThaumaturgeEvents.SCANS, new ScansKubeEvent(generator));
        postData(ThaumaturgeEvents.BLUEPRINTS, new BlueprintsKubeEvent(generator));
        postData(ThaumaturgeEvents.DATA, new RawDataKubeEvent(generator));
    }

    private static void postData(EventHandler handler, ThaumaturgeDataEvent event) {
        if (!handler.hasListeners()) {
            return;
        }
        try {
            handler.post(ScriptType.SERVER, event);
            event.flush();
        } catch (RuntimeException ex) {
            ConsoleJS.SERVER.error("Fallo al generar datos de ThaumaturgeEvents." + handler.name, ex);
        }
    }
}
