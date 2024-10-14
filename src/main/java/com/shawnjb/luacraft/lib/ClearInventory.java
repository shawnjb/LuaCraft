package com.shawnjb.luacraft.lib;

import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;
import com.shawnjb.luacraft.utils.TextFormatter;

public class ClearInventory extends VarArgFunction {
    private final LuaCraft plugin;

    public ClearInventory(LuaCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public Varargs invoke(Varargs args) {
        LuaValue playerValue = args.optvalue(1, LuaValue.NIL);
        Player player = getPlayerFromLuaValue(playerValue);

        if (player == null) {
            player = getLocalPlayer();
            if (player == null) {
                plugin.getLastSender().sendMessage(TextFormatter.toComponent("No player applicable."));
                return LuaValue.NIL;
            }
        }

        player.getInventory().clear();
        return LuaValue.TRUE;
    }

    private Player getPlayerFromLuaValue(LuaValue value) {
        if (value != null && value.istable()) {
            LuaValue playerName = value.get("name");
            if (!playerName.isnil()) {
                return plugin.getServer().getPlayer(playerName.tojstring());
            }
        }
        return null;
    }

    private Player getLocalPlayer() {
        if (plugin.getLastSender() instanceof Player) {
            return (Player) plugin.getLastSender();
        }
        return null;
    }
}
