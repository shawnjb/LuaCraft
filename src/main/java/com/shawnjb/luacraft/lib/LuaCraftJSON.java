package com.shawnjb.luacraft.lib;

import com.google.gson.Gson;
import com.shawnjb.luacraft.utils.LuaValueConverter;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

/**
 * The LuaCraftJSON class provides JSON encode and decode functionality using Gson.
 * It exposes the `json.encode` and `json.decode` functions globally in Lua.
 */
public class LuaCraftJSON {

    private static final Gson gson = new Gson();

    /**
     * Creates and returns a Lua table containing the encode and decode methods.
     * This can be set globally as the `json` table in Lua.
     */
    public static LuaValue create() {
        LuaValue jsonTable = LuaValue.tableOf();

        jsonTable.set("encode", new JSONEncode());
        jsonTable.set("decode", new JSONDecode());

        return jsonTable;
    }

    /**
     * Encodes a Lua table or value into a JSON string.
     */
    static class JSONEncode extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs args) {
            LuaValue table = args.checktable(1);
            String jsonString = gson.toJson(LuaValueConverter.toJava(table));
            return LuaValue.valueOf(jsonString);
        }
    }

    /**
     * Decodes a JSON string into a Lua table.
     */
    static class JSONDecode extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs args) {
            String jsonString = args.checkjstring(1);
            Object javaObj = gson.fromJson(jsonString, Object.class);
            return LuaValueConverter.toLua(javaObj);
        }
    }
}
