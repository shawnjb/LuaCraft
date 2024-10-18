package com.shawnjb.luacraft.lib;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class LuaCraftEventHandler {
    private final LuaCraft plugin;
    private final Map<String, Triple<Listener, List<LuaValue>, Event>> eventListeners = new HashMap<>();
    private final Map<String, Pair<Class<? extends Event>, Function<? super Event, LuaValue[]>>> eventMap = new HashMap<>();

    public LuaCraftEventHandler(LuaCraft plugin) {
        this.plugin = plugin;
        registerEvents();
    }

    private void registerEvents() {
        registerLuaEvent(BlockPlaceEvent.class, (event) -> new LuaValue[]{
            LuaValue.valueOf(event.getPlayer().getName()),
            LuaValue.valueOf(event.getBlock().getType().toString())
        });

        registerLuaEvent(PlayerJoinEvent.class, (event) -> new LuaValue[]{
            LuaValue.valueOf(event.getPlayer().getName())
        });

        registerLuaEvent(PlayerQuitEvent.class, (event) -> new LuaValue[]{
            LuaValue.valueOf(event.getPlayer().getName())
        });
    }

    public LuaValue create() {
        LuaValue eventTable = LuaValue.tableOf();

        eventTable.set("bindEvent", new BindEvent());
        eventTable.set("unbindEvent", new UnbindEvent());
        eventTable.set("getRegisteredEvents", new GetRegisteredEvents());

        return eventTable;
    }

    class BindEvent extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs args) {
            String eventType = args.checkjstring(1);
            LuaValue function = args.checkfunction(2);
            if (eventMap.containsKey(eventType)) {
                eventListeners.computeIfAbsent(eventType, key -> {
                    EventListener newListener = new EventListener(key);
                    registerEvent(eventType, newListener);
                    return Triple.of(newListener, new ArrayList<>(), null);
                }).getMiddle().add(function);

                plugin.getLogger().info("Event '" + eventType + "' bound.");
                return LuaValue.TRUE;
            } else {
                plugin.getLogger().warning("Event '" + eventType + "' not found.");
                return LuaValue.FALSE;
            }
        }
    }

    class UnbindEvent extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs args) {
            String eventType = args.checkjstring(1);
            if (eventListeners.containsKey(eventType)) {
                unregisterEvent(eventType);
                plugin.getLogger().info("Event '" + eventType + "' unbound.");
                return LuaValue.TRUE;
            } else {
                plugin.getLogger().warning("Event '" + eventType + "' not found.");
                return LuaValue.FALSE;
            }
        }
    }

    class GetRegisteredEvents extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs args) {
            LuaTable resultTable = LuaValue.tableOf();
            int i = 1;
            for (Map.Entry<String, Triple<Listener, List<LuaValue>, Event>> eventEntry : eventListeners.entrySet()) {
                Triple<Listener, List<LuaValue>, Event> value = eventEntry.getValue();
                resultTable.set(i, value.getRight().getEventName());
                i++;
            }
            return resultTable;
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Event> void registerLuaEvent(Class<T> c, Function<? super T, LuaValue[]> function) {
        eventMap.put(c.getSimpleName(), Pair.of(c, (Function<? super Event, LuaValue[]>) function));
    }

    private void registerEvent(String clazz, Listener listener) {
        if (eventMap.containsKey(clazz)) {
            Pair<Class<? extends Event>, Function<? super Event, LuaValue[]>> pair = eventMap.get(clazz);
            Class<? extends Event> c = pair.getLeft();
            Bukkit.getPluginManager().registerEvent(c, listener, EventPriority.NORMAL,
                (newListener, event) -> {
                    Triple<Listener, List<LuaValue>, Event> listenerTriple = eventListeners.get(clazz);
                    if (listenerTriple != null) {
                        for (LuaValue func : listenerTriple.getMiddle()) {
                            LuaValue[] luaParams = pair.getRight().apply(event);
                            func.invoke(luaParams);
                        }
                    }
                }, plugin);
        }
    }

    private void unregisterEvent(String clazz) {
        if (eventListeners.containsKey(clazz)) {
            Triple<Listener, List<LuaValue>, Event> triple = eventListeners.get(clazz);
            triple.getRight().getHandlers().unregister(triple.getLeft());
            eventListeners.remove(clazz);
        }
    }

        private record EventListener(String eventType) implements Listener {
    }
}
