package com.dankfmemes.luacraft;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import com.dankfmemes.luacraft.utils.Undumper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LuaCraft extends JavaPlugin {
    public CommandSender lastSender;
    private Globals globals;

    @Override
    public void onEnable() {
        super.onEnable();

        this.globals = JsePlatform.standardGlobals();
        this.globals.undumper = new Undumper(this.globals);

        File luaDir = new File(getDataFolder(), "lua");
        if (!luaDir.exists()) {
            luaDir.mkdirs();
            getLogger().info("Created lua directory at " + luaDir.getAbsolutePath());
        }

        File luaNotLiveDir = new File(getDataFolder(), "lua-notlive");
        if (!luaNotLiveDir.exists()) {
            luaNotLiveDir.mkdirs();
            getLogger().info("Created lua-notlive directory at " + luaNotLiveDir.getAbsolutePath());
        }

        File initFile = new File(luaDir, "init.lua");
        if (!initFile.exists()) {
            try {
                initFile.createNewFile();
                getLogger().info("Created init.lua at " + initFile.getAbsolutePath());
            } catch (IOException e) {
                getLogger().severe("Failed to create init.lua: " + e.getMessage());
            }
        }

        try (FileInputStream fileInputStream = new FileInputStream(initFile)) {
            LuaValue chunk = globals.load(fileInputStream, "init.lua", "bt", globals);
            chunk.call();
            getLogger().info("Successfully loaded init.lua");
        } catch (FileNotFoundException e) {
            getLogger().severe("init.lua file not found: " + e.getMessage());
        } catch (IOException e) {
            getLogger().severe("I/O error when loading init.lua: " + e.getMessage());
        } catch (LuaError e) {
            getLogger().severe("Lua error while loading init.lua: " + e.getMessage());
        }

        registerNativeFunctions();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.globals = null;
    }

    private void registerNativeFunctions() {
        globals.set("print", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                StringBuilder message = new StringBuilder();
                for (int i = 1; i <= args.narg(); i++) {
                    message.append(args.checkjstring(i)).append(" ");
                }
                Component chatMessage = translateColorCodes(message.toString().trim());
                if (lastSender != null) {
                    lastSender.sendMessage(chatMessage);
                }
                return NIL;
            }
        });

        globals.set("colorize", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                String color = args.checkjstring(1);
                String text = args.checkjstring(2);
                return CoerceJavaToLua.coerce("&" + color + text);
            }
        });

        globals.set("wait", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                int seconds = args.checkint(1);
                try {
                    Thread.sleep(seconds * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return NIL;
            }
        });

        globals.set("runcommand", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                String command = args.checkjstring(1);
                if (lastSender instanceof Player) {
                    Player player = (Player) lastSender;
                    Bukkit.dispatchCommand(player, command);
                } else {
                    lastSender.sendMessage(translateColorCodes("You must be a player to run commands."));
                }
                return NIL;
            }
        });
    }

    private Component translateColorCodes(String message) {
        message = message.replace("&0", "§0")
                .replace("&1", "§1")
                .replace("&2", "§2")
                .replace("&3", "§3")
                .replace("&4", "§4")
                .replace("&5", "§5")
                .replace("&6", "§6")
                .replace("&7", "§7")
                .replace("&8", "§8")
                .replace("&9", "§9")
                .replace("&a", "§a")
                .replace("&b", "§b")
                .replace("&c", "§c")
                .replace("&d", "§d")
                .replace("&e", "§e")
                .replace("&f", "§f")
                .replace("&k", "§k")
                .replace("&l", "§l")
                .replace("&m", "§m")
                .replace("&n", "§n")
                .replace("&o", "§o")
                .replace("&r", "§r");
        return Component.text(message);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        lastSender = sender;

        if (cmd.getName().equalsIgnoreCase("loadscript")) {
            if (args.length == 0) {
                sender.sendMessage(translateColorCodes("Usage: /loadscript <filename>"));
                return true;
            }

            File scriptFile = new File(getServer().getWorldContainer(), "lua-notlive/" + args[0] + ".lua");
            if (scriptFile.exists()) {
                try {
                    LuaValue chunk = globals.loadfile(scriptFile.getAbsolutePath());
                    chunk.call();
                    sender.sendMessage(translateColorCodes("Script executed successfully."));
                } catch (LuaError e) {
                    sender.sendMessage(translateColorCodes("Error executing script: " + e.getMessage()));
                }
            } else {
                sender.sendMessage(translateColorCodes("Script not found: " + args[0]));
            }

            getServer().broadcast(translateColorCodes(sender.getName() + " executed the script: " + args[0]));
            return true;
        }

        // New command for listing scripts
        if (cmd.getName().equalsIgnoreCase("listscripts")) {
            listScripts(sender);
            return true;
        }

        return false;
    }

    private void listScripts(CommandSender sender) {
        File folder = new File(getServer().getWorldContainer(), "lua-notlive");
        if (!folder.exists() || !folder.isDirectory()) {
            sender.sendMessage(translateColorCodes("Lua scripts directory does not exist."));
            return;
        }

        String[] luaFiles = folder.list((dir, name) -> name.endsWith(".lua"));
        if (luaFiles == null || luaFiles.length == 0) {
            sender.sendMessage(translateColorCodes("No Lua scripts found."));
            return;
        }

        StringBuilder scriptsList = new StringBuilder("Available scripts: ");
        for (String fileName : luaFiles) {
            scriptsList.append(fileName.substring(0, fileName.lastIndexOf('.'))).append(", ");
        }

        if (scriptsList.length() > 2) {
            scriptsList.setLength(scriptsList.length() - 2);
        }

        sender.sendMessage(translateColorCodes(scriptsList.toString()));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("loadscript") && args.length == 1) {
            File folder = new File(getServer().getWorldContainer(), "lua-notlive");

            if (!folder.exists() || !folder.isDirectory()) {
                return null;
            }
            String[] luaFiles = folder.list((dir, name) -> name.endsWith(".lua"));
            List<String> completions = new ArrayList<>();

            if (luaFiles != null) {
                for (String fileName : luaFiles) {
                    completions.add(fileName.substring(0, fileName.lastIndexOf('.')));
                }
            }

            return completions;
        }

        if (command.getName().equalsIgnoreCase("listscripts")) {
            return List.of();
        }

        return null;
    }

}
