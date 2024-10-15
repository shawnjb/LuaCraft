package com.shawnjb.luacraft.lib;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;
import com.shawnjb.luacraft.LuaCraftItem;
import com.shawnjb.luacraft.LuaCraftPlayer;
import com.shawnjb.luacraft.utils.TextFormatter;

public class GetInventory extends VarArgFunction {
    private final LuaCraft plugin;

    public GetInventory(LuaCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public Varargs invoke(Varargs args) {
        LuaValue playerValue = args.optvalue(1, LuaValue.NIL);

        Player player = LuaCraftPlayer.fromLuaValue(playerValue) != null
                ? LuaCraftPlayer.fromLuaValue(playerValue).getPlayer()
                : null;

        if (player == null) {
            player = getLocalPlayer();
            if (player == null) {
                plugin.getLastSender().sendMessage(TextFormatter.toComponent("No player applicable."));
                return LuaValue.NIL;
            }
        }

        LuaValue inventoryTable = LuaValue.tableOf();
        ItemStack[] items = player.getInventory().getContents();

        int index = 1;
        for (ItemStack item : items) {
            if (item != null) {
                LuaCraftItem luaCraftItem = new LuaCraftItem(item, plugin);
                inventoryTable.set(index++, luaCraftItem.toLuaValue());
            }
        }

        return inventoryTable;
    }

    private Player getLocalPlayer() {
        if (plugin.getLastSender() instanceof Player) {
            return (Player) plugin.getLastSender();
        }
        return null;
    }
}
