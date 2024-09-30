package com.dankfmemes.luacraft;

import net.kyori.adventure.text.Component;
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
        luaCraftLibrary = new LuaCraftLibrary(this); // Initialize the LuaCraftLibrary

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

        luaCraftLibrary.registerFunctions(globals); // Register LuaCraftLibrary functions
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
                URI uri = new URI(GITHUB_API_URL); // Create a URI instance
                URL url = uri.toURL(); // Convert URI to URL
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

                    // Parse JSON response using org.json.simple
                    JSONParser parser = new JSONParser();
                    JSONArray tags = (JSONArray) parser.parse(jsonResponse.toString());

                    if (tags.size() > 0) {
                        JSONObject latestTag = (JSONObject) tags.get(0); // Get the first tag
                        String latestVersion = (String) latestTag.get("name"); // Get the name of the latest tag

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
