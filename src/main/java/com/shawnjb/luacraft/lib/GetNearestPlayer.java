package com.shawnjb.luacraft.lib;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;

/**
 * The GetNearestPlayer class allows Lua scripts to get the nearest player
 * to the given player or the local player.
 */
public class GetNearestPlayer extends VarArgFunction {
    private final LuaCraft plugin;

    public GetNearestPlayer(LuaCraft plugin) {
        this.plugin = plugin;
    }

    /**
     * Gets the nearest player to the provided player or local player.
     * Lua Usage: getNearestPlayer(player)
     *
     * @param player an optional LuaCraftPlayer table (if not provided, uses local player)
     * @return the nearest player's name, or "Unavailable" if not applicable
     */
    @Override
    public Varargs invoke(Varargs args) {
        LuaValue playerValue = args.optvalue(1, LuaValue.NIL);
        Player player = getPlayerFromLuaValue(playerValue);

        if (player == null) {
            player = getLocalPlayer();
            if (player == null) {
                plugin.getLogger().info("No player available.");
                return LuaValue.valueOf("Unavailable");
            }
        }

        Player nearestPlayer = null;
        double nearestDistance = Double.MAX_VALUE;
        Location playerLocation = player.getLocation();

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.equals(player)) {
                double distance = playerLocation.distance(onlinePlayer.getLocation());
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestPlayer = onlinePlayer;
                }
            }
        }

        if (nearestPlayer != null) {
            return LuaValue.valueOf(nearestPlayer.getName());
        } else {
            return LuaValue.valueOf("No other players nearby.");
        }
    }

    private Player getPlayerFromLuaValue(LuaValue value) {
        if (value != null && value.istable()) {
            LuaValue playerName = value.get("getName");
            if (!playerName.isnil()) {
                return plugin.getServer().getPlayer(playerName.tojstring());
            }
        }
        return null;
    }

    private Player getLocalPlayer() {
        if (plugin.getLastSender() instanceof Player) {
            return (Player) plugin.getLastSender();
        }
        return null;
    }
}
