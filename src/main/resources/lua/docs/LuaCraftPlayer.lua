--- @meta
--- @class LuaCraftPlayer
--- Represents a player in the game, exposing methods to interact with the player.
local LuaCraftPlayer = {
	--- The name of the player.
	--- @type string
	name = '',

	--- Sends a message to the player.
	--- @param message string The message to send.
	--- ```lua
	--- player.sendMessage("Hello, Player!")
	--- ```
	sendMessage = function(message) end,

	--- Retrieves the player's position as a Lua table with `x`, `y`, and `z` coordinates.
	--- @return table @A table with `x`, `y`, and `z` fields representing the player's current position.
	--- ```lua
	--- local position = player.getPosition()
	--- print(position.x, position.y, position.z)
	--- ```
	getPosition = function() end,

	--- Teleports the player to the specified coordinates.
	--- @param position Vec3
	--- ```lua
	--- player.setPosition(Vec3.new(100, 64, 100))
	--- ```
	setPosition = function(position) end,

	--- Gives an item to the player.
	--- @param itemName string The name of the item to give.
	--- @param amount number The quantity of the item.
	--- ```lua
	--- player.giveItem("DIAMOND", 5)
	--- ```
	giveItem = function(itemName, amount) end,

	--- Checks if the player is online.
	--- @return boolean @True if the player is online, false otherwise.
	--- ```lua
	--- local online = player.isOnline()
	--- print(online)
	--- ```
	isOnline = function() end
}