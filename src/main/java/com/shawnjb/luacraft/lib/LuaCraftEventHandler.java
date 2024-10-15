package com.shawnjb.luacraft.lib;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;

import java.util.HashMap;
import java.util.Map;

public class LuaCraftEventHandler implements Listener {
    private final LuaCraft plugin;
    private final Map<String, Map<String, LuaValue>> eventListeners = new HashMap<>();

    public LuaCraftEventHandler(LuaCraft plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
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
            String listenerName = args.checkjstring(2);
            LuaValue function = args.checkfunction(3);

            eventListeners.computeIfAbsent(eventType, k -> new HashMap<>());

            eventListeners.get(eventType).put(listenerName, function);
            plugin.getLogger().info("Listener '" + listenerName + "' bound to event '" + eventType + "'.");

            return LuaValue.TRUE;
        }
    }

    class UnbindEvent extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs args) {
            String eventType = args.checkjstring(1);
            String listenerName = args.checkjstring(2);

            Map<String, LuaValue> listeners = eventListeners.get(eventType);
            if (listeners != null && listeners.remove(listenerName) != null) {
                plugin.getLogger().info("Listener '" + listenerName + "' unbound from event '" + eventType + "'.");
                return LuaValue.TRUE;
            } else {
                plugin.getLogger().warning("Listener '" + listenerName + "' not found for event '" + eventType + "'.");
                return LuaValue.FALSE;
            }
        }
    }

    class GetRegisteredEvents extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs args) {
            LuaTable resultTable = LuaValue.tableOf();

            for (Map.Entry<String, Map<String, LuaValue>> eventEntry : eventListeners.entrySet()) {
                String eventType = eventEntry.getKey();
                LuaTable listenersTable = LuaValue.tableOf();

                for (String listenerName : eventEntry.getValue().keySet()) {
                    listenersTable.set(listenersTable.length() + 1, LuaValue.valueOf(listenerName));
                }

                resultTable.set(eventType, listenersTable);
            }

            return resultTable;
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Map<String, LuaValue> listeners = eventListeners.get("BlockPlaceEvent");
        if (listeners != null) {
            listeners.values().forEach(listener -> {
                listener.call(LuaValue.valueOf(event.getPlayer().getName()), LuaValue.valueOf(event.getBlock().getType().toString()));
            });
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Map<String, LuaValue> listeners = eventListeners.get("PlayerJoinEvent");
        if (listeners != null) {
            listeners.values().forEach(listener -> {
                listener.call(LuaValue.valueOf(event.getPlayer().getName()));
            });
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Map<String, LuaValue> listeners = eventListeners.get("PlayerQuitEvent");
        if (listeners != null) {
            listeners.values().forEach(listener -> {
                listener.call(LuaValue.valueOf(event.getPlayer().getName()));
            });
        }
    }
}
