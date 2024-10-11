package com.shawnjb.luacraft;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
import com.shawnjb.luacraft.lib.LuaCraftLibrary;
import com.shawnjb.luacraft.utils.Undumper;
import com.shawnjb.luacraft.utils.Vec3;
import java.io.*;
import java.util.*;

public class LuaCraft extends JavaPlugin {
    private Globals globals;
    private CommandSender lastSender;

    public CommandSender getLastSender() {
        return lastSender;
    }

    @Override
    public void onEnable() {
        globals = JsePlatform.standardGlobals();
        globals.undumper = new Undumper(globals);
        LuaCraftLibrary luaCraftLibrary = new LuaCraftLibrary(this);
        registerAutorunScripts(new File(getServer().getWorldContainer(), "lua"));
        luaCraftLibrary.registerFunctions(globals);
        Vec3.registerVec3(globals);
        globals.get("print").call(LuaValue.valueOf("Vec3 module registered."));
    }

    private void registerAutorunScripts(File luaDir) {
        copyResourceDirectoryToServer("lua", luaDir);
        File autorunDir = new File(luaDir, "autorun");
        if (autorunDir.exists()) {
            for (File file : autorunDir.listFiles((dir, name) -> name.endsWith(".lua"))) {
                try {
                    globals.set("script", LuaValue.tableOf(new LuaValue[] {
                            LuaValue.valueOf("name"), LuaValue.valueOf(file.getName())
                    }));

                    LuaValue chunk = globals.loadfile(file.getAbsolutePath());
                    chunk.call();
                } catch (LuaError e) {
                    getLogger().severe("Error executing " + file.getName() + ": " + e.getMessage());
                }
            }
        } else {
            getLogger().info("No lua/autorun directory found, skipping autorun scripts.");
        }
    }

    private void copyResourceDirectoryToServer(String resourcePath, File targetDir) {
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        try {
            File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
            if (jarFile.isFile()) {
                try (java.util.jar.JarFile jar = new java.util.jar.JarFile(jarFile)) {
                    java.util.Enumeration<java.util.jar.JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        java.util.jar.JarEntry entry = entries.nextElement();
                        String entryName = entry.getName();

                        if (entryName.startsWith(resourcePath) && !entry.isDirectory() && entryName.endsWith(".lua")) {
                            String fileName = entryName.substring(resourcePath.length() + 1);
                            File targetFile = new File(targetDir, fileName);

                            targetFile.getParentFile().mkdirs();

                            try (InputStream in = getResource(entryName);
                                 OutputStream out = new FileOutputStream(targetFile)) {
                                byte[] buffer = new byte[1024];
                                int length;
                                while ((length = in.read(buffer)) > 0) {
                                    out.write(buffer, 0, length);
                                }
                                getLogger().info("Copied " + entryName + " to " + targetFile.getAbsolutePath());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            getLogger().severe("Failed to copy resource directory: " + e.getMessage());
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        lastSender = sender;

        Component prefix = Component.text("[LuaCraft] ").color(NamedTextColor.LIGHT_PURPLE);

        if (cmd.getName().equalsIgnoreCase("loadscript")) {
            if (args.length == 0) {
                sender.sendMessage(prefix.append(Component.text("Usage: /loadscript <filename>").color(NamedTextColor.RED)));
                return true;
            }

            File scriptFile = new File(getServer().getWorldContainer(), "lua/" + args[0] + ".lua");

            if (scriptFile.exists()) {
                try {
                    globals.set("script", LuaValue.tableOf(new LuaValue[] {
                            LuaValue.valueOf("name"), LuaValue.valueOf(scriptFile.getName())
                    }));

                    LuaValue chunk = globals.loadfile(scriptFile.getAbsolutePath());
                    chunk.call();
                } catch (LuaError e) {
                    sender.sendMessage(prefix.append(Component.text("Error executing script: " + e.getMessage())
                            .color(NamedTextColor.RED)));
                }
            } else {
                sender.sendMessage(prefix.append(Component.text("Script not found: ").color(NamedTextColor.RED)
                        .append(Component.text(args[0]).color(NamedTextColor.RED).decorate(TextDecoration.ITALIC))));
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("listscripts")) {
            listScripts(sender, prefix);
            return true;
        }

        return false;
    }

    private void listScripts(CommandSender sender, Component prefix) {
        File folder = new File(getServer().getWorldContainer(), "lua");
        if (!folder.exists() || !folder.isDirectory()) {
            sender.sendMessage(prefix.append(Component.text("Lua scripts directory does not exist.").color(NamedTextColor.RED)));
            return;
        }

        String[] luaFiles = folder.list((dir, name) -> name.endsWith(".lua"));
        if (luaFiles == null || luaFiles.length == 0) {
            sender.sendMessage(prefix.append(Component.text("No Lua scripts found.").color(NamedTextColor.RED)));
            return;
        }

        Component scriptsList = Component.text("Available scripts: ").color(NamedTextColor.GREEN);

        for (int i = 0; i < luaFiles.length; i++) {
            String scriptNameStr = luaFiles[i].substring(0, luaFiles[i].lastIndexOf('.'));
            Component scriptName = Component.text(scriptNameStr).color(NamedTextColor.AQUA).decorate(TextDecoration.ITALIC);

            scriptsList = scriptsList.append(scriptName);

            if (i < luaFiles.length - 1) {
                scriptsList = scriptsList.append(Component.text(", ").color(NamedTextColor.GREEN));
            }
        }

        sender.sendMessage(prefix.append(scriptsList));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("loadscript") && args.length == 1) {
            File folder = new File(getServer().getWorldContainer(), "lua");

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
