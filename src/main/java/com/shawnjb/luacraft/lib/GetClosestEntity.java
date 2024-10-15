package com.shawnjb.luacraft.lib;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.EntityType;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;
import com.shawnjb.luacraft.LuaCraftPlayer;

/**
 * The GetClosestEntity class allows Lua scripts to get the closest entity
 * to the provided player or the local player.
 */
public class GetClosestEntity extends VarArgFunction {
    private final LuaCraft plugin;

    public GetClosestEntity(LuaCraft plugin) {
        this.plugin = plugin;
    }

    /**
     * Gets the closest entity to the provided player or local player.
     * Lua Usage: getClosestEntity(player, entityType)
     *
     * @param player an optional LuaCraftPlayer table (if not provided, uses local player)
     * @param entityType the type of entity to look for (e.g., "ZOMBIE"), optional
     * @return the closest entity's unique ID, or "Unavailable" if not applicable
     */
    @Override
    public Varargs invoke(Varargs args) {
        LuaValue playerValue = args.optvalue(1, LuaValue.NIL);
        Player player = LuaCraftPlayer.fromLuaValue(playerValue) != null
                ? LuaCraftPlayer.fromLuaValue(playerValue).getPlayer()
                : null;

        if (player == null) {
            player = getLocalPlayer();
            if (player == null) {
                plugin.getLogger().info("No player available.");
                return LuaValue.valueOf("Unavailable");
            }
        }

        String entityTypeStr = args.optjstring(2, "ALL");
        EntityType entityTypeFilter = null;

        if (!entityTypeStr.equalsIgnoreCase("ALL")) {
            try {
                entityTypeFilter = EntityType.valueOf(entityTypeStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Invalid entity type: " + entityTypeStr);
                return LuaValue.valueOf("Invalid entity type");
            }
        }

        Entity closestEntity = null;
        double closestDistance = Double.MAX_VALUE;
        Location playerLocation = player.getLocation();

        for (Entity entity : player.getWorld().getEntities()) {
            if (entity.equals(player)) {
                continue;
            }
            if (entityTypeFilter != null && entity.getType() != entityTypeFilter) {
                continue;
            }
            double distance = playerLocation.distance(entity.getLocation());
            if (distance < closestDistance) {
                closestDistance = distance;
                closestEntity = entity;
            }
        }
        if (closestEntity != null) {
            return LuaValue.valueOf(closestEntity.getUniqueId().toString());
        } else {
            return LuaValue.valueOf("No entity found.");
        }
    }

    private Player getLocalPlayer() {
        if (plugin.getLastSender() instanceof Player) {
            return (Player) plugin.getLastSender();
        }
        return null;
    }
}
