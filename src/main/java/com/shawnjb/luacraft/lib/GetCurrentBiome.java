package com.shawnjb.luacraft.lib;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;
import com.shawnjb.luacraft.LuaCraftPlayer;

public class GetCurrentBiome extends VarArgFunction {
    private final LuaCraft plugin;

    public GetCurrentBiome(LuaCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public Varargs invoke(Varargs args) {
        LuaValue entityValue = args.optvalue(1, LuaValue.NIL);
        Entity entity = null;
        if (!entityValue.isnil()) {
            LuaCraftPlayer luaCraftPlayer = LuaCraftPlayer.fromLuaValue(entityValue);
            if (luaCraftPlayer != null) {
                entity = luaCraftPlayer.getPlayer();
            } else {
                String entityName = entityValue.tojstring();
                Player player = Bukkit.getPlayer(entityName);
                if (player != null) {
                    entity = player;
                } else {
                    try {
                        UUID uuid = UUID.fromString(entityName);
                        entity = Bukkit.getEntity(uuid);
                    } catch (IllegalArgumentException e) {}
                }
            }
        }
        else if (plugin.getLastSender() instanceof Player) {
            entity = (Player) plugin.getLastSender();
        }
        if (entity == null) {
            return LuaValue.NIL;
        }
        Location location = entity.getLocation();
        Biome biome = location.getWorld().getBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        return LuaValue.valueOf(biome.name());
    }
}
