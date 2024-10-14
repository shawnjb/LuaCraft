package com.shawnjb.luacraft.lib;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;
import com.shawnjb.luacraft.LuaCraftEntity;
import com.shawnjb.luacraft.utils.TextFormatter;

public class GetEntitiesByType extends VarArgFunction {
    private final LuaCraft plugin;

    public GetEntitiesByType(LuaCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public Varargs invoke(Varargs args) {
        String entityTypeStr = args.checkjstring(1);
        EntityType entityType;

        try {
            entityType = EntityType.valueOf(entityTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            plugin.getLastSender().sendMessage(TextFormatter.toComponent("Invalid entity type: " + entityTypeStr));
            return LuaValue.NIL;
        }

        LuaValue entitiesTable = LuaValue.tableOf();
        int index = 1;

        if (plugin.getLastSender() instanceof Player) {
            Player player = (Player) plugin.getLastSender();
            for (Entity entity : player.getWorld().getEntitiesByClass(entityType.getEntityClass())) {
                LuaCraftEntity luaCraftEntity = new LuaCraftEntity(entity);
                entitiesTable.set(index++, luaCraftEntity.toLuaValue());
            }
            return entitiesTable;
        } else {
            plugin.getLastSender()
                  .sendMessage(TextFormatter.toComponent("You must be a player to get entities by type."));
        }

        return LuaValue.NIL;
    }
}
