package com.shawnjb.luacraft.lib;

import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;
import com.shawnjb.luacraft.utils.TextFormatter;

public class SetPlayerFlySpeed extends VarArgFunction {
    private final LuaCraft plugin;

    public SetPlayerFlySpeed(LuaCraft plugin) {
        this.plugin = plugin;
    }

    /**
     * Sets the fly speed of the specified player or local player.
     * Lua Usage: setPlayerFlySpeed(player, speed)
     *
     * @param player an optional LuaCraftPlayer table (if not provided, uses local player)
     * @param speed the fly speed (between 1.0 and 10.0)
     * @return true if the speed was set successfully, nil otherwise
     */
    @Override
    public Varargs invoke(Varargs args) {
        LuaValue playerValue = args.optvalue(1, LuaValue.NIL);
        double speed = args.checkdouble(2);
        Player player = getPlayerFromLuaValue(playerValue);

        if (player == null) {
            player = getLocalPlayer();
            if (player == null) {
                plugin.getLastSender().sendMessage(TextFormatter.toSections("&4No player applicable."));
                return LuaValue.NIL;
            }
        }

        if (speed < 1.0 || speed > 10.0) {
            plugin.getLastSender().sendMessage(TextFormatter.toSections("&4Fly speed must be between 1.0 and 10.0."));
            return LuaValue.NIL;
        }

        player.setFlySpeed((float) (speed / 10.0));
        return LuaValue.TRUE;
    }

    private Player getPlayerFromLuaValue(LuaValue value) {
        if (value != null && value.istable()) {
            LuaValue playerName = value.get("name");
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
