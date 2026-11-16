package nadiendev.umc.core.witchcraft;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.IModBusEvent;
import net.neoforged.neoforge.common.NeoForge;

/**
 * Registers the static {@link SubscribeEvent} methods of a class by hand, on the mod bus when the event is a mod bus
 * event and on the game bus when it is not. It does what {@code @EventBusSubscriber} does, but only when it is called,
 * which lets a class that needs another mod stay unloaded when that mod is not installed.
 */
final class ManualSubscribers {

    private ManualSubscribers() {
    }

    @SuppressWarnings("unchecked")
    static void register(IEventBus modBus, Class<?> type) {
        for (Method method : type.getDeclaredMethods()) {
            SubscribeEvent annotation = method.getAnnotation(SubscribeEvent.class);
            if (annotation == null || !Modifier.isStatic(method.getModifiers()) || method.getParameterCount() != 1
                    || !Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                continue;
            }
            method.setAccessible(true);
            Class<? extends Event> eventType = (Class<? extends Event>) method.getParameterTypes()[0];
            IEventBus bus = IModBusEvent.class.isAssignableFrom(eventType) ? modBus : NeoForge.EVENT_BUS;
            addListener(bus, annotation, eventType, method);
        }
    }

    private static <T extends Event> void addListener(IEventBus bus, SubscribeEvent annotation, Class<T> eventType, Method method) {
        bus.addListener(annotation.priority(), annotation.receiveCanceled(), eventType, event -> {
            try {
                method.invoke(null, event);
            } catch (InvocationTargetException exception) {
                throw exception.getCause() instanceof RuntimeException runtime ? runtime : new IllegalStateException(exception.getCause());
            } catch (IllegalAccessException exception) {
                throw new IllegalStateException(exception);
            }
        });
    }
}
