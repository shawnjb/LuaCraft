package com.shawnjb.luacraft.lib;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;
import com.shawnjb.luacraft.LuaCraftPlayer;

/**
 * The GetPlayerFromUUID class allows Lua scripts to find a player by their UUID
 * and return a LuaCraftPlayer object.
 */
public class GetPlayerFromUUID extends VarArgFunction {
    private final LuaCraft plugin;

    public GetPlayerFromUUID(LuaCraft plugin) {
        this.plugin = plugin;
    }

    /**
     * Gets a player by their UUID.
     * Lua Usage: getPlayerFromUUID(uuid)
     *
     * @param uuid the UUID of the player
     * @return LuaCraftPlayer or "Player not found" if the player is not online
     */
    @Override
    public Varargs invoke(Varargs args) {
        String uuidStr = args.checkjstring(1);

        try {
            UUID uuid = UUID.fromString(uuidStr);
            Player player = Bukkit.getPlayer(uuid);

            if (player != null) {
                LuaCraftPlayer luaCraftPlayer = new LuaCraftPlayer(player);
                return luaCraftPlayer.toLuaValue();
            } else {
                return LuaValue.valueOf("Player not found");
            }

        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Invalid UUID format: " + uuidStr);
            return LuaValue.valueOf("Invalid UUID");
        }
    }
}
