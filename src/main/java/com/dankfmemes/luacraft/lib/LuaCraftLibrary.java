package com.dankfmemes.luacraft.lib;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;

import com.dankfmemes.luacraft.LuaCraft;
import com.dankfmemes.luacraft.utils.Vec3;
import com.dankfmemes.luacraft.utils.Entities;

import net.kyori.adventure.text.Component;

public class LuaCraftLibrary {
    private final LuaCraft plugin;

    public LuaCraftLibrary(LuaCraft plugin) {
        this.plugin = plugin;
    }

    public void registerFunctions(Globals globals) {
        LuaValue table = LuaValue.tableOf();

        table.set("print", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                StringBuilder message = new StringBuilder();
                for (int i = 1; i <= args.narg(); i++) {
                    message.append(args.checkjstring(i)).append(" ");
                }
                Component chatMessage = plugin.translateColorCodes(message.toString().trim());
                if (plugin.getLastSender() != null) {
                    plugin.getLastSender().sendMessage(chatMessage);
                }
                return LuaValue.NIL;
            }
        });

        table.set("colorize", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                String color = args.checkjstring(1);
                String text = args.checkjstring(2);
                return LuaValue.valueOf("&" + color + text);
            }
        });

        table.set("sleep", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                double seconds = args.checkdouble(1);
                try {
                    Thread.sleep((long) (seconds * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return LuaValue.NIL;
            }
        });

        table.set("wait", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                double seconds = args.checkdouble(1);
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                }, (long) (seconds * 20));
                return LuaValue.NIL;
            }
        });

        table.set("runCommand", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                String command = args.checkjstring(1);
                if (plugin.getLastSender() instanceof Player) {
                    Player player = (Player) plugin.getLastSender();
                    command = command.replace("@p", player.getName());
                    Bukkit.dispatchCommand(player, command);
                } else {
                    plugin.getLastSender()
                            .sendMessage(plugin.translateColorCodes("You must be a player to run commands."));
                }
                return LuaValue.NIL;
            }
        });

        table.set("runcommand", table.get("runCommand"));

        table.set("getPlayerPosition", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                if (plugin.getLastSender() instanceof Player) {
                    Player player = (Player) plugin.getLastSender();
                    Vec3 position = new Vec3(player.getLocation().getX(), player.getLocation().getY(),
                            player.getLocation().getZ());

                    LuaValue positionTable = LuaValue.tableOf();
                    positionTable.set("x", position.x);
                    positionTable.set("y", position.y);
                    positionTable.set("z", position.z);

                    return positionTable;
                } else {
                    plugin.getLastSender()
                            .sendMessage(plugin.translateColorCodes("You must be a player to get position."));
                }
                return LuaValue.NIL;
            }
        });

        table.set("setPlayerPosition", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                if (plugin.getLastSender() instanceof Player) {
                    Player player = (Player) plugin.getLastSender();

                    double x = args.checkdouble(1);
                    double y = args.checkdouble(2);
                    double z = args.checkdouble(3);

                    player.teleport(new Location(player.getWorld(), x, y, z));
                } else {
                    plugin.getLastSender()
                            .sendMessage(plugin.translateColorCodes("You must be a player to set position."));
                }
                return LuaValue.NIL;
            }
        });

        table.set("summonAtPosition", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                String entityName = args.checkjstring(1);
                double x = args.checkdouble(2);
                double y = args.checkdouble(3);
                double z = args.checkdouble(4);

                EntityType entityType;
                try {
                    entityType = EntityType.valueOf(entityName.toUpperCase());
                } catch (IllegalArgumentException e) {
                    plugin.getLastSender()
                            .sendMessage(plugin.translateColorCodes("Invalid entity type: " + entityName));
                    return LuaValue.NIL;
                }

                if (plugin.getLastSender() instanceof Player) {
                    Player player = (Player) plugin.getLastSender();
                    Location location = new Location(player.getWorld(), x, y, z);
                    Entity entity = player.getWorld().spawnEntity(location, entityType);
                    plugin.getLastSender().sendMessage(plugin
                            .translateColorCodes("Summoned " + entityName + " at (" + x + ", " + y + ", " + z + ")."));
                    return LuaValue.valueOf(entity.getUniqueId().toString());
                } else {
                    plugin.getLastSender()
                            .sendMessage(plugin.translateColorCodes("You must be a player to summon entities."));
                }

                return LuaValue.NIL;
            }
        });

        table.set("getAllEntities", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                JSONArray entityArray = new JSONArray();

                if (plugin.getLastSender() instanceof Player) {
                    Player player = (Player) plugin.getLastSender();
                    for (Entity entity : player.getWorld().getEntities()) {
                        JSONObject entityInfo = new JSONObject();
                        entityInfo.put("id", entity.getUniqueId().toString());
                        entityInfo.put("type", entity.getType().toString());
                        entityInfo.put("position", new JSONObject() {
                            {
                                put("x", entity.getLocation().getX());
                                put("y", entity.getLocation().getY());
                                put("z", entity.getLocation().getZ());
                            }
                        });
                        entityArray.add(entityInfo);
                    }
                    return LuaValue.valueOf(entityArray.toJSONString());
                } else {
                    plugin.getLastSender()
                            .sendMessage(plugin.translateColorCodes("You must be a player to get entity data."));
                }

                return LuaValue.NIL;
            }
        });

        table.set("modifyEntityData", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                if (args.isstring(1) && args.istable(2)) {
                    String entityUUID = args.checkjstring(1); // Get UUID as a string
                    LuaValue dataTable = args.checktable(2);

                    // Retrieve the entity using the UUID
                    Entity entity = Entities.getEntityByUUID(entityUUID);
                    if (entity == null) {
                        plugin.getLastSender().sendMessage("Entity with the given UUID not found.");
                        return LuaValue.NIL;
                    }

                    // Modify custom name
                    LuaValue customName = dataTable.get("customName");
                    if (!customName.isnil()) {
                        entity.customName(Component.text(customName.tojstring()));
                    }

                    // Modify health
                    LuaValue health = dataTable.get("health");
                    if (!health.isnil() && entity instanceof LivingEntity) {
                        ((LivingEntity) entity).setHealth(health.todouble());
                    }

                    // Modify position
                    LuaValue position = dataTable.get("position");
                    if (!position.isnil() && position.istable()) {
                        double x = position.get("x").todouble();
                        double y = position.get("y").todouble();
                        double z = position.get("z").todouble();
                        Location newLocation = new Location(entity.getWorld(), x, y, z);
                        entity.teleport(newLocation);
                    }

                    // Modify charged state (for Creepers)
                    if (entity instanceof Creeper) {
                        LuaValue charged = dataTable.get("charged");
                        if (!charged.isnil()) {
                            ((Creeper) entity).setPowered(charged.toboolean());
                        }
                    }

                    // Modify isBaby state (for Ageable entities)
                    LuaValue isBaby = dataTable.get("isBaby");
                    if (!isBaby.isnil() && entity instanceof Ageable) {
                        if (isBaby.toboolean()) {
                            ((Ageable) entity).setBaby();
                        } else {
                            ((Ageable) entity).setAdult();
                        }
                    }
                } else {
                    plugin.getLastSender().sendMessage("You must provide a valid entity UUID and a Lua table.");
                }
                return LuaValue.NIL;
            }
        });

        globals.set("luacraft", table);
    }
}
