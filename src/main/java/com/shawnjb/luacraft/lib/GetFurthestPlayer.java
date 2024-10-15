package com.shawnjb.luacraft.lib;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;
import com.shawnjb.luacraft.LuaCraftPlayer;

/**
 * The GetFurthestPlayer class allows Lua scripts to get the furthest player
 * from the provided player or the local player.
 */
public class GetFurthestPlayer extends VarArgFunction {
    private final LuaCraft plugin;

    public GetFurthestPlayer(LuaCraft plugin) {
        this.plugin = plugin;
    }

    /**
     * Gets the furthest player from the provided player or local player.
     * Lua Usage: getFurthestPlayer(player)
     *
     * @param player an optional LuaCraftPlayer table (if not provided, uses local player)
     * @return the furthest player's name, or "Unavailable" if not applicable
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
                plugin.getLogger().info("No player available.");
                return LuaValue.valueOf("Unavailable");
            }
        }
        Player furthestPlayer = null;
        double furthestDistance = 0;
        Location playerLocation = player.getLocation();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.equals(player)) {
                double distance = playerLocation.distance(onlinePlayer.getLocation());
                if (distance > furthestDistance) {
                    furthestDistance = distance;
                    furthestPlayer = onlinePlayer;
                }
            }
        }
        if (furthestPlayer != null) {
            return LuaValue.valueOf(furthestPlayer.getName());
        } else {
            return LuaValue.valueOf("No other players found.");
        }
    }
    private Player getLocalPlayer() {
        if (plugin.getLastSender() instanceof Player) {
            return (Player) plugin.getLastSender();
        }
        return null;
    }
}
