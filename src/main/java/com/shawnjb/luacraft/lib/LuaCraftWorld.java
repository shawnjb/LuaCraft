package com.shawnjb.luacraft.lib;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;

import java.util.HashMap;
import java.util.Map;

public class LuaCraftWorld implements Listener {
    private final Map<String, LuaFunction> eventListeners = new HashMap<>();

    public LuaCraftWorld(LuaCraft plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public LuaValue registerEvent(String eventName, LuaFunction listener) {
        eventListeners.put(eventName, listener);

        LuaTable eventBinding = new LuaTable();
        eventBinding.set("unbindEvent", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                eventListeners.remove(eventName);
                return LuaValue.NIL;
            }
        });
        return eventBinding;
    }
	
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (eventListeners.containsKey("PlayerJoin")) {
            LuaFunction listener = eventListeners.get("PlayerJoin");
            LuaCraftPlayer luaPlayer = new LuaCraftPlayer(event.getPlayer());
            listener.call(luaPlayer.toLuaValue());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (eventListeners.containsKey("PlayerQuit")) {
            LuaFunction listener = eventListeners.get("PlayerQuit");
            LuaCraftPlayer luaPlayer = new LuaCraftPlayer(event.getPlayer());
            listener.call(luaPlayer.toLuaValue());
        }
    }

    public void registerLuaFunctions(Globals globals) {
        LuaValue table = LuaValue.tableOf();

        table.set("bindEvent", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                String eventName = args.checkjstring(1);
                LuaFunction callback = args.checkfunction(2);
                return registerEvent(eventName, callback);
            }
        });

        globals.set("LuaCraftWorld", table);
    }
}
