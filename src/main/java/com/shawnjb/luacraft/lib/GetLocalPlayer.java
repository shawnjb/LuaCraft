package com.shawnjb.luacraft.lib;

import org.bukkit.entity.Player;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.VarArgFunction;
import com.shawnjb.luacraft.LuaCraft;
import com.shawnjb.luacraft.LuaCraftPlayer;

public class GetLocalPlayer extends VarArgFunction {
    private final LuaCraft plugin;

    public GetLocalPlayer(LuaCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public Varargs invoke(Varargs args) {
        if (plugin.getLastSender() instanceof Player) {
            Player player = (Player) plugin.getLastSender();
            return new LuaCraftPlayer(player).toLuaValue();
        }
        return LuaValue.NIL;
    }
}
