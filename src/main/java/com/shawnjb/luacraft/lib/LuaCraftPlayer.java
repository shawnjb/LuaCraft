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

    public LuaValue toLuaValue() {
        LuaValue playerTable = LuaValue.tableOf();

        playerTable.set("giveItem", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                giveItem(args.checkjstring(1), args.checkint(2));
                return LuaValue.NIL;
            }
        });

        playerTable.set("sendMessage", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                sendMessage(args.checkjstring(1));
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
                setPosition(args.checkdouble(1), args.checkdouble(2), args.checkdouble(3));
                return LuaValue.NIL;
            }
        });

        playerTable.set("getName", LuaValue.valueOf(player.getName()));
        playerTable.set("isOnline", LuaValue.valueOf(player.isOnline()));

        return playerTable;
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

    public void giveItem(String itemName, int amount) {
        Material material = Material.getMaterial(itemName.toUpperCase());
        if (material != null) {
            player.getInventory().addItem(new ItemStack(material, amount));
            player.sendMessage("You received " + amount + " " + itemName + "(s).");
        } else {
            player.sendMessage("Invalid item: " + itemName);
        }
    }

    public void runCommand(String command) {
        Bukkit.dispatchCommand(player, command);
    }
}
