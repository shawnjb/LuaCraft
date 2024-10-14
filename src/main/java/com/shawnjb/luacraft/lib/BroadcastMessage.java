package com.shawnjb.luacraft.lib;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;

/**
 * The BroadcastMessage class allows Lua scripts to broadcast a message
 * to both the server console and all players on the server.
 */
public class BroadcastMessage extends VarArgFunction {
    private final LuaCraft plugin;

    public BroadcastMessage(LuaCraft plugin) {
        this.plugin = plugin;
    }

    /**
     * Broadcasts a message to both the server console and all players.
     * Lua Usage: broadcastMessage(message)
     *
     * @param message the message to broadcast
     * @return nil
     */
    @Override
    public Varargs invoke(Varargs args) {
        String message = args.checkjstring(1);
        plugin.getLogger().info(message);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
        return LuaValue.NIL;
    }
}
