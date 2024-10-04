package com.dankfmemes.luacraft.lib;

import org.bukkit.inventory.ItemStack;
import org.luaj.vm2.LuaValue;

/**
 * Represents an item in the LuaCraft plugin, allowing Lua to interact with
 * Bukkit's ItemStack and modify it.
 */
public class LuaCraftItem {
    private final ItemStack itemStack;

    public LuaCraftItem(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public LuaValue toLuaValue() {
        LuaValue itemTable = LuaValue.tableOf();

        return itemTable;
    }
}
