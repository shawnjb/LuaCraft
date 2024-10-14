package com.shawnjb.luacraft.lib;

import org.bukkit.Bukkit;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;

/**
 * The Wait class allows Lua scripts to pause execution for a specified
 * amount of time using Minecraft's scheduler.
 */
public class Wait extends VarArgFunction {
    private final LuaCraft plugin;

    /**
     * Constructs the Wait class.
     *
     * @param plugin the instance of LuaCraft plugin used for scheduling
     */
    public Wait(LuaCraft plugin) {
        this.plugin = plugin;
    }

    /**
     * Pauses execution for a specified number of seconds.
     * Lua Usage: wait(seconds)
     *
     * @param seconds the number of seconds to wait
     * @return nil when the wait is over
     */
    @Override
    public Varargs invoke(Varargs args) {
        double seconds = args.checkdouble(1);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
        }, (long) (seconds * 20));
        return LuaValue.NIL;
    }
}
