package com.shawnjb.luacraft.lib;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;
import com.shawnjb.luacraft.LuaCraftEntity;
import com.shawnjb.luacraft.utils.TextFormatter;
import com.shawnjb.luacraft.utils.Vec3;

public class NewEntity extends VarArgFunction {
    private final LuaCraft plugin;

    public NewEntity(LuaCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public Varargs invoke(Varargs args) {
        String entityTypeStr = args.checkjstring(1);
        LuaValue positionTable = args.checktable(2);

        EntityType entityType;
        try {
            entityType = EntityType.valueOf(entityTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            plugin.getLastSender().sendMessage(TextFormatter.toSections("&4Invalid entity type: " + entityTypeStr));
            return LuaValue.NIL;
        }

        Player player = getLocalPlayer();
        if (player == null) {
            plugin.getLastSender().sendMessage(TextFormatter.toSections("&4You must be a player to spawn an entity."));
            return LuaValue.NIL;
        }

        Vec3 position = Vec3.fromLua(positionTable);
        World world = player.getWorld();
        Location location = new Location(world, position.x, position.y, position.z);

        Entity entity = world.spawnEntity(location, entityType);
        return new LuaCraftEntity(entity).toLuaValue();
    }

    private Player getLocalPlayer() {
        if (plugin.getLastSender() instanceof Player) {
            return (Player) plugin.getLastSender();
        }
        return null;
    }
}
