package com.dankfmemes.luacraft.lib;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;

import com.dankfmemes.luacraft.LuaCraft;

import net.kyori.adventure.text.Component;

public class LuaCraftLibrary {
    private final LuaCraft plugin;

    public LuaCraftLibrary(LuaCraft plugin) {
        this.plugin = plugin;
    }

    public void registerFunctions(Globals globals) {
        LuaValue table = LuaValue.tableOf();

        // Add the print function
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
                return LuaValue.NIL; // Use LuaValue.NIL instead of just NIL
            }
        });

        // Add the colorize function
        table.set("colorize", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                String color = args.checkjstring(1);
                String text = args.checkjstring(2);
                return LuaValue.valueOf("&" + color + text); // Return as LuaValue
            }
        });

        // Add the wait function
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

        // Add the runcommand function
        table.set("runcommand", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                String command = args.checkjstring(1);
                if (plugin.getLastSender() instanceof Player) {
                    Player player = (Player) plugin.getLastSender();
                    Bukkit.dispatchCommand(player, command);
                } else {
                    plugin.getLastSender()
                            .sendMessage(plugin.translateColorCodes("You must be a player to run commands."));
                }
                return LuaValue.NIL;
            }
        });

        // Register the table under the name 'luacraft'
        globals.set("luacraft", table);
    }
}
