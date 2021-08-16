package me.quick.feather.api.event.bus;

import net.minecraft.server.management.LowerStringMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventBus {
    private final Map<Class, Method> REGISTRY_MAP = new HashMap<Class, Method>();

    public void register(Object o) {
        if (o != null) {
            for (Method m : o.getClass().getDeclaredMethods()) {
                if (isValidMethod(m)) {
                    REGISTRY_MAP.put(o.getClass(), m);
                }
            }
        }
    }

    public void unregister(Object o) {
        if (o != null) {
            for (Method m : o.getClass().getDeclaredMethods()) {
                REGISTRY_MAP.remove(o.getClass(), m);
            }
        }
    }

    public void shutdown() {
        REGISTRY_MAP.clear();
    }

    public void callEvent(Event e) {
        if (e != null) {
            for (Method m : REGISTRY_MAP.values()) {
                if (m.getParameterTypes()[0].isInstance(e) && REGISTRY_MAP.get(m.getDeclaringClass()) != null) {
                    try {
                        m.invoke(REGISTRY_MAP.get(m.getDeclaringClass()).getDeclaringClass().newInstance(), e);
                    } catch (IllegalAccessException | InvocationTargetException | InstantiationException illegalAccessException) {
                        illegalAccessException.printStackTrace();
                    }
                }
            }
        }
    }

    private boolean isValidMethod(Method m) {
        return m != null ? (m.getParameterCount() == 1 && m.isAnnotationPresent(SubscribeEvent.class)) : false;
    }
}
