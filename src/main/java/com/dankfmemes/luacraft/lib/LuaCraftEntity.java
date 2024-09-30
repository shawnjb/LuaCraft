package com.dankfmemes.luacraft.lib;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.LivingEntity;
import org.json.simple.JSONObject;
import org.luaj.vm2.LuaValue;

import net.kyori.adventure.text.Component;

import java.util.UUID;

public class LuaCraftEntity {
    private Entity entity;

    public LuaCraftEntity(Entity entity) {
        this.entity = entity;
    }

    public LuaCraftEntity(String entityUUID) {
        this.entity = getEntityByUUID(entityUUID);
    }

    public LuaCraftEntity(UUID entityUUID) {
        this.entity = getEntityByUUID(entityUUID.toString());
    }

    public JSONObject getEntityDataAsJson() {
        JSONObject entityInfo = new JSONObject();
        entityInfo.put("id", entity.getUniqueId().toString());
        entityInfo.put("type", entity.getType().toString());

        Location location = entity.getLocation();
        JSONObject position = new JSONObject();
        position.put("x", location.getX());
        position.put("y", location.getY());
        position.put("z", location.getZ());
        entityInfo.put("position", position);

        return entityInfo;
    }

    // Fetch entity by UUID (Utility method)
    public Entity getEntityByUUID(String uuid) {
        try {
            UUID entityUUID = UUID.fromString(uuid);
            for (org.bukkit.World world : Bukkit.getWorlds()) {
                for (Entity e : world.getEntities()) {
                    if (e.getUniqueId().equals(entityUUID)) {
                        return e;
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            // Handle invalid UUID format
        }
        return null;
    }

    // Method to check if the entity is valid
    public boolean isValid() {
        return entity != null;
    }

    // Set the custom name of the entity
    public void setCustomName(String name) {
        if (entity != null) {
            entity.customName(Component.text(name));
        }
    }

    // Set the health of the entity (if it's a LivingEntity)
    public void setHealth(double health) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).setHealth(health);
        }
    }

    // Teleport entity to a new position
    public void teleport(double x, double y, double z) {
        if (entity != null) {
            Location newLocation = new Location(entity.getWorld(), x, y, z);
            entity.teleport(newLocation);
        }
    }

    // Set whether the entity is a baby (for Ageable entities)
    public void setBaby(boolean isBaby) {
        if (entity instanceof Ageable) {
            Ageable ageable = (Ageable) entity;
            if (isBaby) {
                ageable.setBaby();
            } else {
                ageable.setAdult();
            }
        }
    }

    // Set whether a Creeper is charged
    public void setCharged(boolean charged) {
        if (entity instanceof Creeper) {
            ((Creeper) entity).setPowered(charged);
        }
    }

    // Get the entity's UUID as a string
    public String getUUID() {
        if (entity != null) {
            return entity.getUniqueId().toString();
        }
        return null;
    }

    // Lua interface for accessing entity's custom name
    public LuaValue getCustomName() {
        if (entity != null && entity.customName() != null) {
            return LuaValue.valueOf(entity.customName().toString());
        }
        return LuaValue.NIL;
    }

    // Add more methods as needed for additional properties
}
