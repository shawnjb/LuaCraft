package com.shawnjb.luacraft;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.plugin.Plugin;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.Map;

public class LuaCraftItem {
    private final ItemStack itemStack;
    private final Plugin plugin;

    public LuaCraftItem(ItemStack itemStack, Plugin plugin) {
        this.itemStack = itemStack;
        this.plugin = plugin;
    }

    public LuaValue getItemMetadata() {
        LuaTable itemTable = LuaValue.tableOf();
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            if (meta.hasDisplayName())
                itemTable.set("name", LuaValue.valueOf(meta.displayName().toString()));

            if (meta.hasLore()) {
                LuaTable loreTable = LuaValue.tableOf();
                List<Component> lore = meta.lore();
                for (int i = 0; i < lore.size(); i++)
                    loreTable.set(i + 1, LuaValue.valueOf(lore.get(i).toString()));
                itemTable.set("lore", loreTable);
            }

            if (meta.hasEnchants()) {
                LuaTable enchantTable = LuaValue.tableOf();
                for (Map.Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet())
                    enchantTable.set(entry.getKey().getKey().getKey(), LuaValue.valueOf(entry.getValue()));
                itemTable.set("enchantments", enchantTable);
            }
        }

        itemTable.set("type", LuaValue.valueOf(itemStack.getType().toString()));
        itemTable.set("amount", LuaValue.valueOf(itemStack.getAmount()));

        return itemTable;
    }

    public void setDisplayName(String displayName) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.displayName(Component.text(displayName));
            itemStack.setItemMeta(meta);
        }
    }

    public void setLore(LuaTable loreTable) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            List<Component> lore = new java.util.ArrayList<>();
            for (int i = 1; i <= loreTable.length(); i++) {
                lore.add(Component.text(loreTable.get(i).tojstring()));
            }
            meta.lore(lore);
            itemStack.setItemMeta(meta);
        }
    }

    public void addSafeEnchantment(String enchantmentName, int level) {
        try {
            Registry<Enchantment> enchantmentRegistry = RegistryAccess.registryAccess()
                    .getRegistry(RegistryKey.ENCHANTMENT);

            Enchantment enchantment = enchantmentRegistry.getOrThrow(
                    TypedKey.create(RegistryKey.ENCHANTMENT, Key.key("minecraft", enchantmentName.toLowerCase())));

            if (level > 0 && level <= enchantment.getMaxLevel()) {
                if (enchantment.canEnchantItem(itemStack)) {
                    itemStack.addEnchantment(enchantment, level);
                } else {
                    System.out.println("This enchantment cannot be applied to this item.");
                }
            } else {
                System.out.println("Invalid enchantment level.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid enchantment key: " + enchantmentName);
        }
    }

    public void removeSafeEnchantment(String enchantmentName) {
        try {
            Registry<Enchantment> enchantmentRegistry = RegistryAccess.registryAccess()
                    .getRegistry(RegistryKey.ENCHANTMENT);

            Enchantment enchantment = enchantmentRegistry.getOrThrow(
                    TypedKey.create(RegistryKey.ENCHANTMENT, Key.key("minecraft", enchantmentName.toLowerCase())));

            if (itemStack.containsEnchantment(enchantment)) {
                itemStack.removeEnchantment(enchantment);
            } else {
                System.out.println("Enchantment not present on the item.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid enchantment key: " + enchantmentName);
        }
    }

    public void setCustomModelData(int data) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setCustomModelData(data);
            itemStack.setItemMeta(meta);
        }
    }

    public void setPersistentData(String key, String value) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
            meta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, value);
            itemStack.setItemMeta(meta);
        }
    }

    public String getPersistentData(String key) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
            return meta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING);
        }
        return null;
    }

    public void setUnbreakable(boolean unbreakable) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setUnbreakable(unbreakable);
            itemStack.setItemMeta(meta);
        }
    }

    public LuaValue toLuaValue() {
        LuaTable luaItem = LuaValue.tableOf();

        luaItem.set("getItemMetadata", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                return getItemMetadata();
            }
        });

        luaItem.set("setDisplayName", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                setDisplayName(args.checkjstring(1));
                return LuaValue.NIL;
            }
        });

        luaItem.set("setLore", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                setLore(args.checktable(1));
                return LuaValue.NIL;
            }
        });

        luaItem.set("addSafeEnchantment", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                addSafeEnchantment(args.checkjstring(1), args.checkint(2));
                return LuaValue.NIL;
            }
        });

        luaItem.set("removeSafeEnchantment", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                removeSafeEnchantment(args.checkjstring(1));
                return LuaValue.NIL;
            }
        });

        luaItem.set("setCustomModelData", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                setCustomModelData(args.checkint(1));
                return LuaValue.NIL;
            }
        });

        luaItem.set("setPersistentData", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                setPersistentData(args.checkjstring(1), args.checkjstring(2));
                return LuaValue.NIL;
            }
        });

        luaItem.set("getPersistentData", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                return LuaValue.valueOf(getPersistentData(args.checkjstring(1)));
            }
        });

        luaItem.set("setUnbreakable", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                setUnbreakable(args.checkboolean(1));
                return LuaValue.NIL;
            }
        });

        return luaItem;
    }
}
