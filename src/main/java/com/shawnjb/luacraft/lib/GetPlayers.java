package com.shawnjb.luacraft.lib;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import com.shawnjb.luacraft.LuaCraftPlayer;

/**
 * The GetPlayers class allows Lua scripts to get a table of all online LuaCraftPlayer
 * objects on the server.
 */
public class GetPlayers extends VarArgFunction {
    /**
     * Gets a table of all online LuaCraftPlayer objects.
     * Lua Usage: getPlayers()
     *
     * @return LuaValue A Lua table containing LuaCraftPlayer objects.
     */
    @Override
    public Varargs invoke(Varargs args) {
        LuaValue playersTable = LuaValue.tableOf();
        int index = 1;

        for (Player player : Bukkit.getOnlinePlayers()) {
            LuaCraftPlayer luaCraftPlayer = new LuaCraftPlayer(player);
            playersTable.set(index++, luaCraftPlayer.toLuaValue());
        }

        return playersTable;
    }
}
