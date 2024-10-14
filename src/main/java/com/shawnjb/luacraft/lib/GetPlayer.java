package com.shawnjb.luacraft.lib;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraftPlayer;

public class GetPlayer extends VarArgFunction {
    @Override
    public Varargs invoke(Varargs args) {
        String playerName = args.checkjstring(1);
        Player targetPlayer = Bukkit.getPlayer(playerName);
        if (targetPlayer != null) {
            return new LuaCraftPlayer(targetPlayer).toLuaValue();
        }
        return LuaValue.NIL;
    }
}
