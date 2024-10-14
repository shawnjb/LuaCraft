package com.shawnjb.luacraft.lib;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;

/**
 * The WriteFile class allows Lua scripts to write content to a file
 * in the LuaCraft plugin's data folder.
 */
public class WriteFile extends VarArgFunction {
    private final LuaCraft plugin;

    public WriteFile(LuaCraft plugin) {
        this.plugin = plugin;
    }

    /**
     * Writes the specified content to a file in the LuaCraft folder.
     * Lua Usage: writeFile(filePath, content)
     *
     * @param filePath the relative file path from the LuaCraft folder
     * @param content the content to write into the file
     * @return true if the file was written successfully, nil on failure
     */
    @Override
    public Varargs invoke(Varargs args) {
        String relativePath = args.checkjstring(1);
        String content = args.checkjstring(2);

        File luaCraftFolder = new File(plugin.getDataFolder(), relativePath);

        try (FileWriter writer = new FileWriter(luaCraftFolder)) {
            writer.write(content);
            return LuaValue.TRUE;
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to write file: " + luaCraftFolder.getPath());
            e.printStackTrace();
            return LuaValue.NIL;
        }
    }
}
