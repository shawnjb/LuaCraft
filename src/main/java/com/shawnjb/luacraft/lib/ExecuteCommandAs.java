package com.shawnjb.luacraft.lib;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;

/**
 * The ExecuteCommandAs class allows Lua scripts to execute a Minecraft command
 * as a specified player or console.
 */
public class ExecuteCommandAs extends VarArgFunction {
    private final LuaCraft plugin;

    public ExecuteCommandAs(LuaCraft plugin) {
        this.plugin = plugin;
    }

    /**
     * Executes a Minecraft command as the specified player or console.
     * Lua Usage: executeCommandAs(player, command)
     *
     * @param player an optional LuaCraftPlayer table (if not provided, uses the console)
     * @param command the command string to execute
     * @return boolean @true if the command was successfully executed.
     */
    @Override
    public Varargs invoke(Varargs args) {
        LuaValue playerValue = args.optvalue(1, LuaValue.NIL);
        String command = args.checkjstring(2);

        CommandSender sender;

        Player player = getPlayerFromLuaValue(playerValue);
        if (player != null) {
            sender = player;
        } else {
            sender = Bukkit.getConsoleSender();
        }

        boolean success = Bukkit.dispatchCommand(sender, command);

        if (success) {
            return LuaValue.TRUE;
        } else {
            plugin.getLogger().warning("Command failed: " + command);
            return LuaValue.FALSE;
        }
    }

    private Player getPlayerFromLuaValue(LuaValue value) {
        if (value != null && value.istable()) {
            LuaValue playerName = value.get("getName");
            if (!playerName.isnil()) {
                return plugin.getServer().getPlayer(playerName.tojstring());
            }
        }
        return null;
    }
}
