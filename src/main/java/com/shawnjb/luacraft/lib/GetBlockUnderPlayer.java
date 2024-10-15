package com.shawnjb.luacraft.lib;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.World;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;
import com.shawnjb.luacraft.LuaCraftPlayer;

/**
 * The GetBlockUnderPlayer class allows Lua scripts to get the block type
 * directly underneath the player or the local player.
 */
public class GetBlockUnderPlayer extends VarArgFunction {
    private final LuaCraft plugin;

    public GetBlockUnderPlayer(LuaCraft plugin) {
        this.plugin = plugin;
    }

    /**
     * Gets the block type directly under the player's feet.
     * Lua Usage: getBlockUnderPlayer(player)
     *
     * @param player an optional LuaCraftPlayer table (if not provided, uses local player)
     * @return the block type name under the player's feet or "Unavailable" if not applicable
     */
    @Override
    public Varargs invoke(Varargs args) {
        LuaValue playerValue = args.optvalue(1, LuaValue.NIL);
        Player player = LuaCraftPlayer.fromLuaValue(playerValue) != null
                ? LuaCraftPlayer.fromLuaValue(playerValue).getPlayer()
                : null;

        if (player == null) {
            player = getLocalPlayer();
            if (player == null) {
                plugin.getLogger().info("Surface information unavailable in the console.");
                return LuaValue.valueOf("Unavailable");
            }
        }

        World world = player.getWorld();
        Location location = player.getLocation();
        Location blockUnderPlayer = location.clone().subtract(0, 1, 0);
        Material blockMaterial = world.getBlockAt(blockUnderPlayer).getType();

        return LuaValue.valueOf(blockMaterial.name());
    }

    private Player getLocalPlayer() {
        if (plugin.getLastSender() instanceof Player) {
            return (Player) plugin.getLastSender();
        }
        return null;
    }
}
