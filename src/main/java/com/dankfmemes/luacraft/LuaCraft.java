package com.dankfmemes.luacraft;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.JsePlatform;

import com.dankfmemes.luacraft.lib.LuaCraftLibrary;
import com.dankfmemes.luacraft.utils.Undumper;
import com.dankfmemes.luacraft.utils.Vec3;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LuaCraft extends JavaPlugin {
    private static final String GITHUB_API_URL = "https://api.github.com/repos/dankfmemes/LuaCraft/tags";
    private String currentVersion;
    private FileConfiguration config;

    private CommandSender lastSender;
    private Globals globals;
    private LuaCraftLibrary luaCraftLibrary;

    public CommandSender getLastSender() {
        return lastSender;
    }

    @Override
    public void onEnable() {
        super.onEnable();

        currentVersion = getPluginMeta().getVersion();
        checkForUpdates();
        loadConfig();

        this.globals = JsePlatform.standardGlobals();
        this.globals.undumper = new Undumper(this.globals);
        luaCraftLibrary = new LuaCraftLibrary(this);

        File luaDir = new File(getServer().getWorldContainer(), "lua");
        if (!luaDir.exists()) {
            luaDir.mkdirs();
            getLogger().info("Created lua directory at " + luaDir.getAbsolutePath());
        }

        File luaNotLiveDir = new File(getServer().getWorldContainer(), "lua-notlive");
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

        luaCraftLibrary.registerFunctions(globals);
        Vec3.registerVec3(globals);
        globals.get("print").call(LuaValue.valueOf("Vec3 module registered."));
    }

    private void loadConfig() {
        saveDefaultConfig();
        config = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml"));
    }

    public FileConfiguration getConfig() {
        return config;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.globals = null;
    }

    private void checkForUpdates() {
        new Thread(() -> {
            try {
                URI uri = new URI(GITHUB_API_URL);
                URL url = uri.toURL();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                if (connection.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder jsonResponse = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        jsonResponse.append(line);
                    }
                    reader.close();

                    JSONParser parser = new JSONParser();
                    JSONArray tags = (JSONArray) parser.parse(jsonResponse.toString());

                    if (tags.size() > 0) {
                        JSONObject latestTag = (JSONObject) tags.get(0);
                        String latestVersion = (String) latestTag.get("name");

                        if (!currentVersion.equals(latestVersion)) {
                            getLogger().info("A new version is available: " + latestVersion);
                        } else {
                            getLogger().info("You are using the latest version: " + currentVersion);
                        }
                    } else {
                        getLogger().info("No tags found in the repository.");
                    }
                } else {
                    getLogger().severe("Failed to check for updates. Response code: " + connection.getResponseCode());
                }
            } catch (Exception e) {
                getLogger().severe("An error occurred while checking for updates: " + e.getMessage());
            }
        }).start();
    }

    public Component translateColorCodes(String message) {
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

        Component prefix = Component.text("[LuaCraft] ")
                .color(NamedTextColor.LIGHT_PURPLE);

        if (cmd.getName().equalsIgnoreCase("loadscript")) {
            if (args.length == 0) {
                sender.sendMessage(prefix.append(Component.text("Usage: /loadscript <filename>")
                        .color(NamedTextColor.RED)));
                return true;
            }

            File scriptFile = new File(getServer().getWorldContainer(), "lua-notlive/" + args[0] + ".lua");
            if (scriptFile.exists()) {
                try {
                    LuaValue chunk = globals.loadfile(scriptFile.getAbsolutePath());
                    chunk.call();
                    sender.sendMessage(prefix.append(Component.text("Script executed successfully.")
                            .color(NamedTextColor.GREEN)));
                } catch (LuaError e) {
                    sender.sendMessage(prefix.append(Component.text("Error executing script: " + e.getMessage())
                            .color(NamedTextColor.RED)));
                }
            } else {
                sender.sendMessage(prefix.append(Component.text("Script not found: ")
                        .color(NamedTextColor.RED)
                        .append(Component.text(args[0])
                                .color(NamedTextColor.RED)
                                .decorate(TextDecoration.ITALIC))));
            }

            Component scriptName = Component.text(args[0])
                    .color(NamedTextColor.YELLOW)
                    .decorate(TextDecoration.ITALIC);

            getServer().broadcast(prefix.append(Component.text(sender.getName() + " executed the script: ")
                    .color(NamedTextColor.YELLOW)
                    .append(scriptName)));
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("listscripts")) {
            listScripts(sender, prefix);
            return true;
        }

        return false;
    }

    private void listScripts(CommandSender sender, Component prefix) {
        File folder = new File(getServer().getWorldContainer(), "lua-notlive");
        if (!folder.exists() || !folder.isDirectory()) {
            sender.sendMessage(prefix.append(Component.text("Lua scripts directory does not exist.")
                    .color(NamedTextColor.RED)));
            return;
        }

        String[] luaFiles = folder.list((dir, name) -> name.endsWith(".lua"));
        if (luaFiles == null || luaFiles.length == 0) {
            sender.sendMessage(prefix.append(Component.text("No Lua scripts found.")
                    .color(NamedTextColor.RED)));
            return;
        }

        Component scriptsList = Component.text("Available scripts: ")
                .color(NamedTextColor.GREEN);

        for (int i = 0; i < luaFiles.length; i++) {
            String scriptNameStr = luaFiles[i].substring(0, luaFiles[i].lastIndexOf('.'));
            Component scriptName = Component.text(scriptNameStr)
                    .color(NamedTextColor.AQUA)
                    .decorate(TextDecoration.ITALIC);

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
