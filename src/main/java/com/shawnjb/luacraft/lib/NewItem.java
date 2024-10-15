package com.shawnjb.luacraft.lib;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;
import com.shawnjb.luacraft.LuaCraftItem;
import com.shawnjb.luacraft.LuaCraftPlayer;
import com.shawnjb.luacraft.utils.TextFormatter;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;

public class NewItem extends VarArgFunction {
    private final LuaCraft plugin;

    public NewItem(LuaCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public Varargs invoke(Varargs args) {
        String materialName = args.checkjstring(1);
        LuaValue itemData = args.checktable(2);
        LuaValue playerLua = itemData.get("player");

        Player player = LuaCraftPlayer.fromLuaValue(playerLua) != null
                ? LuaCraftPlayer.fromLuaValue(playerLua).getPlayer()
                : null;

        if (player != null) {
            Material material = Material.getMaterial(materialName.toUpperCase());
            if (material != null) {
                ItemStack itemStack = new ItemStack(material, 1);
                ItemMeta meta = itemStack.getItemMeta();

                LuaValue customName = itemData.get("name");
                if (customName.isstring()) {
                    Component coloredName = TextFormatter.toHexColors(customName.tojstring());
                    meta.displayName(coloredName);
                }

                LuaValue loreTable = itemData.get("lore");
                if (loreTable.istable()) {
                    List<Component> loreList = new ArrayList<>();
                    LuaValue loreKey = LuaValue.NIL;
                    while ((loreKey = loreTable.next(loreKey).arg1()).isnil() == false) {
                        String loreLine = loreTable.get(loreKey).tojstring();
                        Component coloredLoreLine = TextFormatter.toHexColors(loreLine);
                        loreList.add(coloredLoreLine);
                    }
                    meta.lore(loreList);
                }

                LuaValue enchantmentsTable = itemData.get("enchantments");
                if (enchantmentsTable.istable()) {
                    LuaValue enchantKey = LuaValue.NIL;
                    while ((enchantKey = enchantmentsTable.next(enchantKey).arg1()).isnil() == false) {
                        LuaValue enchantmentData = enchantmentsTable.get(enchantKey);
                        String enchantmentName = enchantmentData.get(1).tojstring();
                        int level = enchantmentData.get(2).toint();

                        Registry<Enchantment> enchantmentRegistry = RegistryAccess.registryAccess()
                                .getRegistry(RegistryKey.ENCHANTMENT);

                        Enchantment enchantment = enchantmentRegistry
                                .getOrThrow(NamespacedKey.minecraft(enchantmentName.toLowerCase()));

                        if (enchantment != null) {
                            meta.addEnchant(enchantment, level, true);
                        } else {
                            plugin.getLastSender().sendMessage("Invalid enchantment: " + enchantmentName);
                        }
                    }
                } else {
                    plugin.getLogger().info("No enchantments provided.");
                }

                itemStack.setItemMeta(meta);
                player.getInventory().addItem(itemStack);
                plugin.getLogger().info("Item added to player: " + player.getName());
                plugin.getLogger().info("Item Meta: " + itemStack.getItemMeta().toString());

                return new LuaCraftItem(itemStack, plugin).toLuaValue();
            } else {
                plugin.getLastSender()
                        .sendMessage(TextFormatter.toComponent("Invalid material: " + materialName));
                plugin.getLogger().warning("Invalid material: " + materialName);
            }
        } else {
            plugin.getLastSender().sendMessage("Invalid player.");
            plugin.getLogger().warning("Invalid player");
        }

        return LuaValue.NIL;
    }
}
