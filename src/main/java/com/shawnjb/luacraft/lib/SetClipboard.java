package com.shawnjb.luacraft.lib;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;

public class SetClipboard extends VarArgFunction {
    private final LuaCraft plugin;

    public SetClipboard(LuaCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public Varargs invoke(Varargs args) {
        String text = args.checkjstring(1);

        try {
            StringSelection stringSelection = new StringSelection(text);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            return LuaValue.TRUE;
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to set clipboard: " + e.getMessage());
            e.printStackTrace();
            return LuaValue.FALSE;
        }
    }

    public static void registerGlobal(Globals globals, LuaCraft plugin) {
        globals.set("setClipboard", new SetClipboard(plugin));
    }
}
