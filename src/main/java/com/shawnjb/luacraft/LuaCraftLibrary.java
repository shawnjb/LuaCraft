package com.shawnjb.luacraft;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;

import com.shawnjb.luacraft.lib.*;

/**
 * The LuaCraftLibrary class provides a collection of functions that can be
 * used in Lua scripts for interacting with Minecraft players, entities,
 * and the game world. It allows Lua scripts to execute commands, manipulate
 * player inventories, and access player and entity data.
 */
public class LuaCraftLibrary {
	private final LuaCraft plugin;

	/**
	 * Constructs a LuaCraftLibrary instance with the specified LuaCraft plugin.
	 *
	 * @param plugin the instance of the LuaCraft plugin used for interaction
	 *               with the Minecraft server and its functionalities.
	 */
	public LuaCraftLibrary(LuaCraft plugin) {
		this.plugin = plugin;
	}

	public void registerLuaFunctions(Globals globals) {
		LuaValue table = LuaValue.tableOf();

		// LuaCraftPlayer methods
		table.set("getPlayer", new GetPlayer());
		table.set("getPlayers", new GetPlayers());
		table.set("getPlayerFromUUID", new GetPlayerFromUUID(plugin));
		table.set("getLocalPlayer", new GetLocalPlayer(plugin));
		table.set("setPlayerWalkSpeed", new SetPlayerWalkSpeed(plugin));
		table.set("setPlayerFlySpeed", new SetPlayerFlySpeed(plugin));
		table.set("getCurrentDimension", new GetCurrentDimension(plugin));
		table.set("getBlockUnderPlayer", new GetBlockUnderPlayer(plugin));
		table.set("getNearestPlayer", new GetNearestPlayer(plugin));
		table.set("getFurthestPlayer", new GetNearestPlayer(plugin));

		// LuaCraftEntity methods
		table.set("newEntity", new NewEntity(plugin));
		table.set("getEntities", new GetEntities(plugin));
		table.set("getEntitiesByType", new GetEntitiesByType(plugin));
		table.set("getClosestEntity", new GetClosestEntity(plugin));
		table.set("getFurthestEntity", new GetFurthestEntity(plugin));

		// LuaCraftItem methods
		table.set("newItem", new NewItem(plugin));
		table.set("getInventory", new GetInventory(plugin));
		table.set("clearInventory", new ClearInventory(plugin));

		// Networking
		table.set("httpGet", new HttpGet(plugin));
		table.set("socketConnect", new SocketConnect(plugin));
		table.set("socketServer", new SocketServer(plugin));

		// Miscellaneous methods
		table.set("sleep", new Sleep());
		table.set("wait", new Wait(plugin));
		table.set("readFile", new ReadFile(plugin));
		table.set("writeFile", new WriteFile(plugin));
		table.set("setClipboard", new SetClipboard(plugin));
		table.set("convertAmpersandToSection", new ConvertAmpersandToSection());
		table.set("broadcastMessage", new BroadcastMessage(plugin));

		// Command execution
		table.set("executeCommand", new ExecuteCommand(plugin));
		table.set("executeCommandAs", new ExecuteCommandAs(plugin));

		// Traversal methods
		table.set("setDestinationPath", new SetDestinationPath());
		table.set("stopAllPathRoutes", new StopAllPathRoutes());
		table.set("getCurrentBiome", new StopAllPathRoutes());

		globals.set("LuaCraft", table);
		globals.set("json", LuaCraftJSON.create());
	}
}
