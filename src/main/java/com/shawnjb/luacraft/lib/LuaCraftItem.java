package com.shawnjb.luacraft.lib;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.enchantments.Enchantment;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.Map;

/**
 * Represents an item in the LuaCraft plugin, allowing Lua to interact with
 * Bukkit's ItemStack and retrieve or modify its metadata.
 */
public class LuaCraftItem {
    private final ItemStack itemStack;

    public LuaCraftItem(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Retrieves the ItemStack metadata and converts it into a Lua table.
     * The table includes the item name, lore, enchantments, and more.
     * 
     * @return LuaValue representing the item metadata in a Lua-friendly format.
     */
    public LuaValue getItemMetadata() {
        LuaTable itemTable = LuaValue.tableOf();
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            if (meta.hasDisplayName()) {
                itemTable.set("name", LuaValue.valueOf(meta.displayName().toString()));
            }

            if (meta.hasLore()) {
                LuaTable loreTable = LuaValue.tableOf();
                List<Component> lore = meta.lore();
                for (int i = 0; i < lore.size(); i++) {
                    loreTable.set(i + 1, LuaValue.valueOf(lore.get(i).toString()));
                }
                itemTable.set("lore", loreTable);
            }

            if (meta.hasEnchants()) {
                LuaTable enchantTable = LuaValue.tableOf();
                Map<Enchantment, Integer> enchants = meta.getEnchants();
                for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
                    enchantTable.set(entry.getKey().getKey().getKey(), LuaValue.valueOf(entry.getValue()));
                }
                itemTable.set("enchantments", enchantTable);
            }
        }

        itemTable.set("type", LuaValue.valueOf(itemStack.getType().toString()));
        itemTable.set("amount", LuaValue.valueOf(itemStack.getAmount()));

        return itemTable;
    }

    /**
     * Converts the LuaCraftItem into a LuaValue that Lua can interact with.
     * This method registers the Lua methods such as getItemMetadata.
     * 
     * @return LuaTable that represents the LuaCraftItem in Lua.
     */
    public LuaValue toLuaValue() {
        LuaTable luaItem = LuaValue.tableOf();

        luaItem.set("getItemMetadata", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                return getItemMetadata();
            }
        });

        return luaItem;
    }
}
