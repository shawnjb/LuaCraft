package com.shawnjb.luacraft.lib;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

/**
 * The ConvertAmpersandToSection class allows Lua scripts to convert ampersand (&)
 * codes to Minecraft section (§) symbol codes for text formatting.
 */
public class ConvertAmpersandToSection extends VarArgFunction {

    /**
     * Converts ampersand (&) codes to Minecraft section (§) symbol codes.
     * Lua Usage: convertAmpersandToSection(text)
     *
     * @param text the string with & codes
     * @return the string with § codes
     */
    @Override
    public Varargs invoke(Varargs args) {
        String inputText = args.checkjstring(1);
        String convertedText = inputText.replace("&", "§");
        return LuaValue.valueOf(convertedText);
    }
}
