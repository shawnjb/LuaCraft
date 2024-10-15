package com.shawnjb.luacraft.lib;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.bukkit.Location;

import com.shawnjb.luacraft.utils.Vec3;

public class SetDestinationPath extends VarArgFunction {
    private static final Set<Mob> activeMobs = new HashSet<>();

    @Override
    public Varargs invoke(Varargs args) {
        String entityUUID = args.checkjstring(1);
        Entity entity = null;

        try {
            entity = Bukkit.getEntity(UUID.fromString(entityUUID));
        } catch (IllegalArgumentException e) {
            return LuaValue.NIL;
        }

        if (entity == null || !(entity instanceof Mob)) {
            return LuaValue.NIL;
        }

        Mob mob = (Mob) entity;

        LuaValue vec3Table = args.checktable(2);
        Vec3 destination = Vec3.fromLua(vec3Table);
        Location targetLocation = new Location(mob.getWorld(), destination.x, destination.y, destination.z);
        boolean success = mob.getPathfinder().moveTo(targetLocation);

        if (success) {
            activeMobs.add(mob);
        }

        return LuaValue.valueOf(success);
    }

    public static void stopAllPathRoutes() {
        for (Mob mob : activeMobs) {
            mob.getPathfinder().stopPathfinding();
        }
        activeMobs.clear();
    }
}
