package com.dankfmemes.luacraft.utils;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

public class Entities {
    /**
     * Retrieves an entity by its UUID.
     * 
     * @param uuid The UUID of the entity as a string.
     * @return The Bukkit Entity corresponding to the UUID, or null if not found.
     */
    public static Entity getEntityByUUID(String uuid) {
        UUID entityUUID;
        try {
            entityUUID = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            return null;
        }

        for (Entity entity : Bukkit.getWorlds().stream()
                .flatMap(world -> world.getEntities().stream())
                .toList()) {
            if (entity.getUniqueId().equals(entityUUID)) {
                return entity;
            }
        }
        
        return null;
    }
}
