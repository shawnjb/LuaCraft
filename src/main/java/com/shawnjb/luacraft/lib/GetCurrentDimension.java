package com.shawnjb.luacraft.lib;

import org.bukkit.entity.Player;
import org.bukkit.World;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;
import com.shawnjb.luacraft.LuaCraftPlayer;

/**
 * The GetCurrentDimension class allows Lua scripts to get the current dimension
 * (world) of a player or the local player. If used in the console, it prints an
 * "Unavailable" message.
 */
public class GetCurrentDimension extends VarArgFunction {
    private final LuaCraft plugin;

    public GetCurrentDimension(LuaCraft plugin) {
        this.plugin = plugin;
    }

    /**
     * Gets the current dimension (world) of the player or the local player.
     * Lua Usage: getCurrentDimension(player)
     *
     * @param player an optional LuaCraftPlayer table (if not provided, uses local player)
     * @return the dimension name or "Unavailable" if not applicable
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
                plugin.getLogger().info("Dimension information unavailable in the console.");
                return LuaValue.valueOf("Unavailable");
            }
        }
        World world = player.getWorld();
        String dimensionName = world.getEnvironment().name();

        return LuaValue.valueOf(dimensionName);
    }

    private Player getLocalPlayer() {
        if (plugin.getLastSender() instanceof Player) {
            return (Player) plugin.getLastSender();
        }
        return null;
    }
}
