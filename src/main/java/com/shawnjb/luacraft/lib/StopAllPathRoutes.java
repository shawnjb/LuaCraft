package com.shawnjb.luacraft.lib;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class StopAllPathRoutes extends VarArgFunction {
    @Override
    public Varargs invoke(Varargs args) {
        SetDestinationPath.stopAllPathRoutes();
        return LuaValue.NIL;
    }
}
