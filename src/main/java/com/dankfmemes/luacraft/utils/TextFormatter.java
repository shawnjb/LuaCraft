package com.dankfmemes.luacraft.utils;

public class TextFormatter {
    /**
     * Replaces all ampersands (&) in the input string with the section symbol (§).
     * This is typically used for Minecraft text formatting codes.
     * 
     * @param inputString The input string containing formatting codes with '&'.
     * @return A new string where '&' is replaced with '§'.
     */
    public static String toSections(String inputString) {
        if (inputString == null) {
            return null;
        }
        return inputString.replace("&", "§");
    }
}
