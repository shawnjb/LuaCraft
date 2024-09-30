package com.dankfmemes.luacraft.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class EntityUtils {
    public static void summonEntity(String entityName, Vec3 position, Player player) {
        EntityType entityType = EntityType.fromName(entityName);
        if (entityType == null) {
            Bukkit.getLogger().warning("Invalid entity name: " + entityName);
            return;
        }
        Location location = new Location(player.getWorld(), position.getX(), position.getY(), position.getZ());
        Entity entity = player.getWorld().spawnEntity(location, entityType);
    }
}
