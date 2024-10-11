package com.shawnjb.luacraft.lib;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.LivingEntity;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import net.kyori.adventure.text.Component;

import java.util.UUID;

public class LuaCraftEntity {
    private final Entity entity;

    public LuaCraftEntity(Entity entity) {
        this.entity = entity;
    }

    public LuaCraftEntity(String entityUUID) {
        this.entity = getEntityByUUID(entityUUID);
    }

    private Entity getEntityByUUID(String uuid) {
        try {
            UUID entityUUID = UUID.fromString(uuid);
            return Bukkit.getWorlds().stream()
                    .flatMap(world -> world.getEntities().stream())
                    .filter(e -> e.getUniqueId().equals(entityUUID))
                    .findFirst()
                    .orElse(null);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    public boolean isValid() {
        return entity != null && !entity.isDead();
    }

    public void setPosition(double x, double y, double z) {
        if (entity != null)
            entity.teleport(new Location(entity.getWorld(), x, y, z));
    }

    public void setCustomName(String name) {
        if (entity != null)
            entity.customName(Component.text(name));
    }

    public void setHealth(double health) {
        if (entity instanceof LivingEntity)
            ((LivingEntity) entity).setHealth(health);
    }

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

    public void setCharged(boolean charged) {
        if (entity instanceof Creeper)
            ((Creeper) entity).setPowered(charged);
    }

    public LuaTable getPosition() {
        Location loc = entity.getLocation();
        LuaTable positionTable = LuaValue.tableOf();
        positionTable.set("x", LuaValue.valueOf(loc.getX()));
        positionTable.set("y", LuaValue.valueOf(loc.getY()));
        positionTable.set("z", LuaValue.valueOf(loc.getZ()));
        return positionTable;
    }

    public String getUUID() {
        return entity != null ? entity.getUniqueId().toString() : null;
    }

    public LuaValue getCustomName() {
        return entity != null && entity.customName() != null ? LuaValue.valueOf(entity.customName().toString())
                : LuaValue.NIL;
    }

    public void setAI(boolean enabled) {
        if (entity instanceof LivingEntity)
            ((LivingEntity) entity).setAI(enabled);
    }

    public LuaValue toLuaValue() {
        LuaTable entityTable = LuaValue.tableOf();

        entityTable.set("setPosition", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                setPosition(args.checkdouble(1), args.checkdouble(2), args.checkdouble(3));
                return LuaValue.NIL;
            }
        });

        entityTable.set("setCustomName", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                setCustomName(args.checkjstring(1));
                return LuaValue.NIL;
            }
        });

        entityTable.set("getPosition", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                return getPosition();
            }
        });

        entityTable.set("getUUID", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                return LuaValue.valueOf(getUUID());
            }
        });

        entityTable.set("getType", LuaValue.valueOf(entity.getType().toString()));

        entityTable.set("getCustomName", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                return getCustomName();
            }
        });

        entityTable.set("setAI", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                setAI(args.checkboolean(1));
                return LuaValue.NIL;
            }
        });

        entityTable.set("setHealth", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                setHealth(args.checkdouble(1));
                return LuaValue.NIL;
            }
        });

        entityTable.set("setBaby", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                setBaby(args.checkboolean(1));
                return LuaValue.NIL;
            }
        });

        entityTable.set("setCharged", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                setCharged(args.checkboolean(1));
                return LuaValue.NIL;
            }
        });

        return entityTable;
    }
}
