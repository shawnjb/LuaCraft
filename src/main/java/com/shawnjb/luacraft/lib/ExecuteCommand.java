package com.shawnjb.luacraft.lib;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;

/**
 * The ExecuteCommand class allows Lua scripts to execute a Minecraft command
 * from the console.
 */
public class ExecuteCommand extends VarArgFunction {
    private final LuaCraft plugin;

    public ExecuteCommand(LuaCraft plugin) {
        this.plugin = plugin;
    }

    /**
     * Executes a Minecraft command from the console.
     * Lua Usage: executeCommand(command)
     *
     * @param command the command string to execute
     * @return boolean @true if the command was successfully executed.
     */
    @Override
    public Varargs invoke(Varargs args) {
        String command = args.checkjstring(1);

        CommandSender console = Bukkit.getConsoleSender();
        boolean success = Bukkit.dispatchCommand(console, command);

        if (success) {
            return LuaValue.TRUE;
        } else {
            plugin.getLogger().warning("Command failed: " + command);
            return LuaValue.FALSE;
        }
    }
}
