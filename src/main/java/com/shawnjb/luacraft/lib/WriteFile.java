package com.shawnjb.luacraft.lib;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;

public class WriteFile extends VarArgFunction {
    private final LuaCraft plugin;

    public WriteFile(LuaCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public Varargs invoke(Varargs args) {
        String relativePath = args.checkjstring(1);
        String content = args.checkjstring(2);

        File luaCraftFolder = new File(plugin.getDataFolder(), relativePath);
        if (!luaCraftFolder.getParentFile().exists()) {
            luaCraftFolder.getParentFile().mkdirs();
        }

        try (FileWriter writer = new FileWriter(luaCraftFolder)) {
            writer.write(content);
            writer.flush();
            return LuaValue.TRUE;
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to write to file: " + luaCraftFolder.getPath());
            e.printStackTrace();
            return LuaValue.FALSE;
        }
    }

    public static void registerGlobal(Globals globals, LuaCraft plugin) {
        globals.set("writeFile", new WriteFile(plugin));
    }
}
