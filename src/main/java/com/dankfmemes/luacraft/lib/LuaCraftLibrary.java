package com.dankfmemes.luacraft.lib;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import java.util.ArrayList;
import java.util.List;

import com.dankfmemes.luacraft.LuaCraft;
import com.dankfmemes.luacraft.utils.Vec3;
import com.dankfmemes.luacraft.utils.TextFormatter;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

/**
 * The LuaCraftLibrary class provides a collection of functions that can be
 * used in Lua scripts for interacting with Minecraft players, entities,
 * and the game world. It allows Lua scripts to execute commands, manipulate
 * player inventories, and access player and entity data.
 */
public class LuaCraftLibrary {
    private final LuaCraft plugin;

    /**
     * Constructs a LuaCraftLibrary instance with the specified LuaCraft plugin.
     *
     * @param plugin the instance of the LuaCraft plugin used for interaction
     *               with the Minecraft server and its functionalities.
     */
    public LuaCraftLibrary(LuaCraft plugin) {
        this.plugin = plugin;
    }

    public Player getPlayerFromLuaValue(LuaValue value) {
        if (value instanceof LuaValue && value.istable()) {
            LuaValue playerTable = value;
            LuaValue playerName = playerTable.get("getName");
            if (!playerName.isnil()) {
                return Bukkit.getPlayer(playerName.tojstring());
            }
        }
        return null;
    }

    public void registerFunctions(Globals globals) {
        LuaValue table = LuaValue.tableOf();

        table.set("getPlayer", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                String playerName = args.checkjstring(1);
                Player targetPlayer = Bukkit.getPlayer(playerName);
                if (targetPlayer != null) {
                    return new LuaCraftPlayer(targetPlayer).toLuaValue();
                }
                return LuaValue.NIL;
            }
        });

        table.set("getLocalPlayer", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                if (plugin.getLastSender() instanceof Player) {
                    Player player = (Player) plugin.getLastSender();
                    return new LuaCraftPlayer(player).toLuaValue();
                }
                return LuaValue.NIL;
            }
        });

        table.set("getPlayers", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                LuaValue playersTable = LuaValue.tableOf();
                int index = 1;

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    LuaCraftPlayer luaCraftPlayer = new LuaCraftPlayer(onlinePlayer);
                    playersTable.set(index++, luaCraftPlayer.toLuaValue());
                }

                return playersTable;
            }
        });

        table.set("print", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                StringBuilder message = new StringBuilder();
                for (int i = 1; i <= args.narg(); i++) {
                    message.append(args.checkjstring(i)).append(" ");
                }

                Component chatMessage = plugin.toHexColors(message.toString().trim());

                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(chatMessage);
                }

                return LuaValue.NIL;
            }
        });

        table.set("broadcast", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                StringBuilder message = new StringBuilder();
                for (int i = 1; i <= args.narg(); i++) {
                    message.append(args.checkjstring(i)).append(" ");
                }

                Component chatMessage = plugin.toHexColors(message.toString().trim());
                Bukkit.getLogger().info("[LuaCraft Broadcast] " + message.toString().trim());

                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(chatMessage);
                }

                return LuaValue.NIL;
            }
        });

        table.set("toSections", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                String inputString = args.checkjstring(1);
                String resultString = inputString.replace("&", "§");
                return LuaValue.valueOf(resultString);
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
                LuaValue entitiesTable = LuaValue.tableOf();
                int index = 1;

                if (plugin.getLastSender() instanceof Player) {
                    Player player = (Player) plugin.getLastSender();
                    for (Entity entity : player.getWorld().getEntities()) {
                        LuaCraftEntity luaCraftEntity = new LuaCraftEntity(entity);
                        entitiesTable.set(index++, luaCraftEntity.toLuaValue());
                    }
                    return entitiesTable;
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
                    String entityUUID = args.checkjstring(1);
                    LuaValue dataTable = args.checktable(2);

                    LuaCraftEntity luaCraftEntity = new LuaCraftEntity(entityUUID);
                    if (!luaCraftEntity.isValid()) {
                        plugin.getLastSender().sendMessage("Entity with the given UUID not found.");
                        return LuaValue.NIL;
                    }

                    LuaValue customName = dataTable.get("customName");
                    if (!customName.isnil()) {
                        luaCraftEntity.setCustomName(customName.tojstring());
                    }

                    LuaValue health = dataTable.get("health");
                    if (!health.isnil()) {
                        luaCraftEntity.setHealth(health.todouble());
                    }

                    LuaValue position = dataTable.get("position");
                    if (!position.isnil() && position.istable()) {
                        double x = position.get("x").todouble();
                        double y = position.get("y").todouble();
                        double z = position.get("z").todouble();
                        luaCraftEntity.setPosition(x, y, z);
                    }

                    LuaValue charged = dataTable.get("charged");
                    if (!charged.isnil()) {
                        luaCraftEntity.setCharged(charged.toboolean());
                    }

                    LuaValue isBaby = dataTable.get("isBaby");
                    if (!isBaby.isnil()) {
                        luaCraftEntity.setBaby(isBaby.toboolean());
                    }
                } else {
                    plugin.getLastSender().sendMessage("You must provide a valid entity UUID and a Lua table.");
                }
                return LuaValue.NIL;
            }
        });

        table.set("consoleMessage", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                String message = args.checkjstring(1);
                Bukkit.getLogger().info("[LuaCraft] " + message);
                return LuaValue.NIL;
            }
        });

        table.set("createItem", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                plugin.getLogger().info("createItem called");

                String materialName = args.checkjstring(1);
                LuaValue itemData = args.checktable(2);
                LuaValue playerLua = itemData.get("player");
                Player player = getPlayerFromLuaValue(playerLua);

                if (player != null) {
                    plugin.getLogger().info("Player found: " + player.getName());

                    Material material = Material.getMaterial(materialName.toUpperCase());
                    if (material != null) {
                        plugin.getLogger().info("Material found: " + materialName);

                        ItemStack itemStack = new ItemStack(material, 1);
                        ItemMeta meta = itemStack.getItemMeta();

                        // name
                        LuaValue customName = itemData.get("name");
                        if (customName.isstring()) {
                            plugin.getLogger().info("Setting custom name: " + customName.tojstring());
                            Component coloredName = plugin.toHexColors(customName.tojstring());
                            meta.displayName(coloredName);
                        }

                        // lore
                        LuaValue loreTable = itemData.get("lore");
                        if (loreTable.istable()) {
                            plugin.getLogger().info("Setting lore");
                            List<Component> loreList = new ArrayList<>();
                            LuaValue loreKey = LuaValue.NIL;
                            while ((loreKey = loreTable.next(loreKey).arg1()).isnil() == false) {
                                String loreLine = loreTable.get(loreKey).tojstring();
                                Component coloredLoreLine = plugin.toHexColors(loreLine);
                                loreList.add(coloredLoreLine);
                                plugin.getLogger().info("Lore line added: " + loreLine);
                            }
                            meta.lore(loreList);
                        }

                        // enchantments
                        LuaValue enchantmentsTable = itemData.get("enchantments");
                        if (enchantmentsTable.istable()) {
                            plugin.getLogger().info("Applying enchantments");
                            LuaValue enchantKey = LuaValue.NIL;
                            while ((enchantKey = enchantmentsTable.next(enchantKey).arg1()).isnil() == false) {
                                LuaValue enchantmentData = enchantmentsTable.get(enchantKey);
                                String enchantmentName = enchantmentData.get(1).tojstring();
                                int level = enchantmentData.get(2).toint();

                                plugin.getLogger()
                                        .info("Processing enchantment: " + enchantmentName + " with level " + level);

                                Registry<Enchantment> enchantmentRegistry = RegistryAccess.registryAccess()
                                        .getRegistry(RegistryKey.ENCHANTMENT);

                                Enchantment enchantment = enchantmentRegistry
                                        .getOrThrow(NamespacedKey.minecraft(enchantmentName.toLowerCase()));

                                if (enchantment != null) {
                                    meta.addEnchant(enchantment, level, true);
                                    plugin.getLogger().info("Enchantment applied: " + enchantmentName);
                                } else {
                                    plugin.getLastSender().sendMessage("Invalid enchantment: " + enchantmentName);
                                    plugin.getLogger().warning("Invalid enchantment: " + enchantmentName);
                                }
                            }
                        } else {
                            plugin.getLogger().info("No enchantments provided.");
                        }

                        itemStack.setItemMeta(meta);
                        player.getInventory().addItem(itemStack);
                        plugin.getLogger().info("Item added to player: " + player.getName());
                        plugin.getLogger().info("Item Meta: " + itemStack.getItemMeta().toString());
                        return LuaValue.userdataOf(itemStack);
                    } else {
                        plugin.getLastSender()
                                .sendMessage(plugin.translateColorCodes("Invalid material: " + materialName));
                        plugin.getLogger().warning("Invalid material: " + materialName);
                    }
                } else {
                    plugin.getLastSender().sendMessage("Invalid player.");
                    plugin.getLogger().warning("Invalid player");
                }

                return LuaValue.NIL;
            }
        });

        globals.set("LuaCraft", table);
    }
}
