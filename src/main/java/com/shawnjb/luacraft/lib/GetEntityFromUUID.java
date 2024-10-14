package com.shawnjb.luacraft.lib;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;

/**
 * The GetEntityFromUUID class allows Lua scripts to find an entity by its UUID.
 */
public class GetEntityFromUUID extends VarArgFunction {
    private final LuaCraft plugin;

    public GetEntityFromUUID(LuaCraft plugin) {
        this.plugin = plugin;
    }

    /**
     * Gets an entity by its UUID.
     * Lua Usage: getEntityFromUUID(uuid)
     *
     * @param uuid the UUID of the entity
     * @return the entity's UUID, or "Entity not found" if the entity is not present
     */
    @Override
    public Varargs invoke(Varargs args) {
        String uuidStr = args.checkjstring(1);

        try {
            UUID uuid = UUID.fromString(uuidStr);
            Entity entity = Bukkit.getEntity(uuid);

            if (entity != null) {
                return LuaValue.valueOf(entity.getUniqueId().toString());
            } else {
                return LuaValue.valueOf("Entity not found");
            }

        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Invalid UUID format: " + uuidStr);
            return LuaValue.valueOf("Invalid UUID");
        }
    }
}
