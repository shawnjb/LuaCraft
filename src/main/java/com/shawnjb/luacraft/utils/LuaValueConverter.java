package com.shawnjb.luacraft.utils;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.Varargs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LuaValueConverter {
    public static Object toJava(LuaValue value) {
        if (value.istable()) {
            LuaTable table = value.checktable();
            LuaValue key = LuaValue.NIL;
            boolean isList = true;
            int expectedIndex = 1;

            while (true) {
                Varargs next = table.next(key);
                key = next.arg1();
                if (key.isnil()) break;
                if (!key.isint() || key.toint() != expectedIndex) {
                    isList = false;
                    break;
                }
                expectedIndex++;
            }

            if (isList) {
                List<Object> list = new ArrayList<>();
                key = LuaValue.NIL;
                while (true) {
                    Varargs next = table.next(key);
                    key = next.arg1();
                    LuaValue val = next.arg(2);

                    if (key.isnil()) break;
                    list.add(toJava(val));
                }
                return list;
            } else {
                Map<String, Object> map = new HashMap<>();
                key = LuaValue.NIL;
                while (true) {
                    Varargs next = table.next(key);
                    key = next.arg1();
                    LuaValue val = next.arg(2);

                    if (key.isnil()) break;
                    map.put(key.tojstring(), toJava(val));
                }
                return map;
            }
        } else if (value.isboolean()) {
            return value.toboolean();
        } else if (value.isnumber()) {
            return value.todouble();
        } else if (value.isstring()) {
            return value.tojstring();
        } else {
            return null;
        }
    }

    public static LuaValue toLua(Object obj) {
        if (obj instanceof Map) {
            LuaValue luaTable = LuaValue.tableOf();
            Map<?, ?> map = (Map<?, ?>) obj;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                luaTable.set(LuaValue.valueOf(entry.getKey().toString()), toLua(entry.getValue()));
            }
            return luaTable;
        } else if (obj instanceof List) {
            LuaValue luaTable = LuaValue.tableOf();
            List<?> list = (List<?>) obj;
            for (int i = 0; i < list.size(); i++) {
                luaTable.set(i + 1, toLua(list.get(i)));
            }
            return luaTable;
        } else if (obj instanceof String) {
            return LuaValue.valueOf((String) obj);
        } else if (obj instanceof Boolean) {
            return LuaValue.valueOf((Boolean) obj);
        } else if (obj instanceof Number) {
            return LuaValue.valueOf(((Number) obj).doubleValue());
        } else {
            return LuaValue.NIL;
        }
    }
}
