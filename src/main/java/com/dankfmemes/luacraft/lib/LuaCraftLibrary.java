package com.dankfmemes.luacraft.lib;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;

import com.dankfmemes.luacraft.LuaCraft;
import com.dankfmemes.luacraft.utils.Vec3;

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

        table.set("wait", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                int seconds = args.checkint(1);
                try {
                    Thread.sleep(seconds * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return LuaValue.NIL;
            }
        });

        table.set("runcommand", new VarArgFunction() {
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

        table.set("runCommand", table.get("runcommand"));

        table.set("getPlayerPosition", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                if (plugin.getLastSender() instanceof Player) {
                    Player player = (Player) plugin.getLastSender();
                    Vec3 position = new Vec3(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
                    
                    LuaValue positionTable = LuaValue.tableOf();
                    positionTable.set("x", position.x);
                    positionTable.set("y", position.y);
                    positionTable.set("z", position.z);

                    return positionTable;
                } else {
                    plugin.getLastSender().sendMessage(plugin.translateColorCodes("You must be a player to get position."));
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
                    plugin.getLastSender().sendMessage(plugin.translateColorCodes("You must be a player to set position."));
                }
                return LuaValue.NIL;
            }
        });

        globals.set("luacraft", table);
    }
}
