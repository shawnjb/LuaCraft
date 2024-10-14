package com.shawnjb.luacraft.lib;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;
import com.shawnjb.luacraft.LuaCraftEntity;
import com.shawnjb.luacraft.utils.TextFormatter;

public class GetEntities extends VarArgFunction {
    private final LuaCraft plugin;

    public GetEntities(LuaCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public Varargs invoke(Varargs args) {
        LuaValue entitiesTable = LuaValue.tableOf();
        int index = 1;

        if (plugin.getLastSender() instanceof Player) {
            Player player = (Player) plugin.getLastSender();
            for (Entity entity : player.getWorld().getEntities()) {
                LuaCraftEntity luaCraftEntity = new LuaCraftEntity(entity);
                entitiesTable.set(index++, luaCraftEntity.toLuaValue());
            }
            return entitiesTable;
        } else {
            plugin.getLastSender().sendMessage(TextFormatter.toComponent("You must be a player to get entity data."));
        }

        return LuaValue.NIL;
    }
}
