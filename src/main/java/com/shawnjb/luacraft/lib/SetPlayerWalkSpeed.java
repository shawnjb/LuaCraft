package com.shawnjb.luacraft.lib;

import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;
import com.shawnjb.luacraft.LuaCraftPlayer;
import com.shawnjb.luacraft.utils.TextFormatter;

public class SetPlayerWalkSpeed extends VarArgFunction {
    private final LuaCraft plugin;

    public SetPlayerWalkSpeed(LuaCraft plugin) {
        this.plugin = plugin;
    }

    /**
     * Sets the walk speed of the specified player or local player.
     * Lua Usage: setPlayerWalkSpeed(player, speed)
     *
     * @param player an optional LuaCraftPlayer table (if not provided, uses local player)
     * @param speed the walk speed (between 1.0 and 10.0)
     * @return true if the speed was set successfully, nil otherwise
     */
    @Override
    public Varargs invoke(Varargs args) {
        LuaValue playerValue = args.optvalue(1, LuaValue.NIL);
        double speed = args.checkdouble(2);

        Player player = LuaCraftPlayer.fromLuaValue(playerValue) != null
                ? LuaCraftPlayer.fromLuaValue(playerValue).getPlayer()
                : null;

        if (player == null) {
            player = getLocalPlayer();
            if (player == null) {
                plugin.getLastSender().sendMessage(TextFormatter.toSections("&4No player applicable."));
                return LuaValue.NIL;
            }
        }

        if (speed < 1.0 || speed > 10.0) {
            plugin.getLastSender().sendMessage(TextFormatter.toSections("&4Speed must be between 1.0 and 10.0."));
            return LuaValue.NIL;
        }

        player.setWalkSpeed((float) (speed / 10.0));
        return LuaValue.TRUE;
    }

    private Player getLocalPlayer() {
        if (plugin.getLastSender() instanceof Player) {
            return (Player) plugin.getLastSender();
        }
        return null;
    }
}
