package com.shawnjb.luacraft.lib;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

/**
 * The Sleep class allows Lua scripts to block and pause execution for a specified
 * amount of time in seconds.
 */
public class Sleep extends VarArgFunction {

    /**
     * Pauses the execution for a specified number of seconds.
     * Lua Usage: sleep(seconds)
     *
     * @param seconds the number of seconds to sleep
     * @return nil when the sleep is over
     */
    @Override
    public Varargs invoke(Varargs args) {
        double seconds = args.checkdouble(1);

        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return LuaValue.NIL;
    }
}
