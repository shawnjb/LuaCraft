package com.shawnjb.luacraft.lib;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class LuaCraftPlayer {
    private final Player player;

    public LuaCraftPlayer(Player player) {
        this.player = player;
    }

    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    public LuaValue getPosition() {
        Location loc = player.getLocation();
        LuaValue positionTable = LuaValue.tableOf();
        positionTable.set("x", loc.getX());
        positionTable.set("y", loc.getY());
        positionTable.set("z", loc.getZ());
        return positionTable;
    }

    public void setPosition(double x, double y, double z) {
        player.teleport(new Location(player.getWorld(), x, y, z));
    }

    public String getName() {
        return player.getName();
    }

    public boolean isOnline() {
        return player.isOnline();
    }

    public VarArgFunction giveItem = new VarArgFunction() {
        @Override
        public Varargs invoke(Varargs args) {
            String itemName = args.checkjstring(1);
            int amount = args.checkint(2);
            giveItem(itemName, amount);
            return LuaValue.NIL;
        }
    };

    public void giveItem(String itemName, int amount) {
        Material material = Material.getMaterial(itemName.toUpperCase());
        if (material != null) {
            ItemStack itemStack = new ItemStack(material, amount);
            player.getInventory().addItem(itemStack);
            player.sendMessage("You have received " + amount + " " + itemName + "(s).");
        } else {
            player.sendMessage("Invalid item name: " + itemName);
        }
    }

    public void runCommand(String command) {
        Bukkit.dispatchCommand(player, command);
    }

    public LuaValue toLuaValue() {
        LuaValue playerTable = LuaValue.tableOf();
        
        playerTable.set("giveItem", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                String itemName = args.checkjstring(1);
                int amount = args.checkint(2);
                giveItem(itemName, amount);
                return LuaValue.NIL;
            }
        });

        playerTable.set("sendMessage", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                String message = args.checkjstring(1);
                sendMessage(message);
                return LuaValue.NIL;
            }
        });

        playerTable.set("getPosition", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                return getPosition();
            }
        });

        playerTable.set("setPosition", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                double x = args.checkdouble(1);
                double y = args.checkdouble(2);
                double z = args.checkdouble(3);
                setPosition(x, y, z);
                return LuaValue.NIL;
            }
        });

        playerTable.set("getName", LuaValue.valueOf(getName()));
        playerTable.set("isOnline", LuaValue.valueOf(isOnline()));

        return playerTable;
    }
}
