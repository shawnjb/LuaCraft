package com.shawnjb.luacraft.lib;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;

public class ReadFile extends VarArgFunction {
    private final LuaCraft plugin;

    public ReadFile(LuaCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public Varargs invoke(Varargs args) {
        String relativePath = args.checkjstring(1); // The file path relative to LuaCraft folder

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

    public static void registerGlobal(Globals globals, LuaCraft plugin) {
        globals.set("readFile", new ReadFile(plugin));
    }
}
