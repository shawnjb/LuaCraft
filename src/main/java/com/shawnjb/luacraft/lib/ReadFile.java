package com.shawnjb.luacraft.lib;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;

/**
 * The ReadFile class allows Lua scripts to read the contents of a file
 * from the LuaCraft plugin's data folder.
 */
public class ReadFile extends VarArgFunction {
    private final LuaCraft plugin;

    public ReadFile(LuaCraft plugin) {
        this.plugin = plugin;
    }

    /**
     * Reads the contents of a file from the LuaCraft folder.
     * Lua Usage: readFile(filePath)
     *
     * @param filePath the relative file path from the LuaCraft folder
     * @return the file content as a string, or nil if the file doesn't exist
     */
    @Override
    public Varargs invoke(Varargs args) {
        String relativePath = args.checkjstring(1);

        File luaCraftFolder = new File(plugin.getDataFolder(), relativePath);

        if (!luaCraftFolder.exists()) {
            plugin.getLogger().warning("File not found: " + luaCraftFolder.getPath());
            return LuaValue.NIL;
        }

        try {
            String content = new String(Files.readAllBytes(luaCraftFolder.toPath()));
            return LuaValue.valueOf(content);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to read file: " + luaCraftFolder.getPath());
            e.printStackTrace();
            return LuaValue.NIL;
        }
    }
}
