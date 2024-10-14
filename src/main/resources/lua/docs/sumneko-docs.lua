--- @meta

--- @class LuaCraftScript
--- Represents the currently executing script. Use this class to obtain details about the running script.
script = {
	--- The script's name.
	--- @type string
	name = ''
}

--- @class Vec3
--- Represents a 3D vector with x, y, z coordinates, providing methods for common vector operations.
Vec3 = {
    --- @type number
    x = 0,
    --- @type number
    y = 0,
    --- @type number
    z = 0,

    --- Creates a new Vec3 object.
    --- @param x number The x-coordinate.
    --- @param y number The y-coordinate.
    --- @param z number The z-coordinate.
    --- @return Vec3 @A new vector object.
    --- ```lua
    --- local vec = Vec3:new(1, 2, 3)
    --- ```
    new = function(x, y, z) end,

	--- Adds another vector to this vector.
	--- @param vec3 Vec3 The other vector to add.
	--- @return Vec3 @A new vector resulting from the addition.
	--- ```lua
	--- local vec1 = Vec3.new(1, 2, 3)
	--- local vec2 = Vec3.new(4, 5, 6)
	--- local result = vec1:add(vec2)
	--- print(result.x, result.y, result.z)  -- 5, 7, 9
	--- ```
	add = function(self, vec3) end,

	--- Subtracts another vector from this vector.
	--- @param vec3 Vec3 The other vector to subtract.
	--- @return Vec3 @A new vector resulting from the subtraction.
	--- ```lua
	--- local result = vec1:subtract(vec2)
	--- print(result.x, result.y, result.z)  -- -3, -3, -3
	--- ```
	subtract = function(self, vec3) end,

	--- Multiplies this vector by a scalar value.
	--- @param scalar number The scalar to multiply the vector by.
	--- @return Vec3 @A new vector resulting from the multiplication.
	--- ```lua
	--- local result = vec1:multiply(2)
	--- print(result.x, result.y, result.z)  -- 2, 4, 6
	--- ```
	multiply = function(self, scalar) end,

	--- Calculates the dot product of this vector and another vector.
	--- @param vec3 Vec3 The other vector.
	--- @return number @The dot product of the two vectors.
	--- ```lua
	--- local dot = vec1:dot(vec2)
	--- print(dot)  -- 32
	--- ```
	dot = function(self, vec3) end,

	--- Calculates the cross product of this vector and another vector.
	--- @param vec3 Vec3 The other vector.
	--- @return Vec3 @A new vector resulting from the cross product.
	--- ```lua
	--- local result = vec1:cross(vec2)
	--- print(result.x, result.y, result.z)  -- -3, 6, -3
	--- ```
	cross = function(self, vec3) end,

	--- Normalizes this vector (returns a unit vector in the same direction).
	--- @return Vec3 @A new normalized vector.
	--- ```lua
	--- local normalized = vec1:normalize()
	--- print(normalized.x, normalized.y, normalized.z)
	--- ```
	normalize = function(self) end,

	--- Calculates the magnitude (length) of this vector.
	--- @return number @The magnitude of the vector.
	--- ```lua
	--- local mag = vec1:magnitude()
	--- print(mag)  -- 3.74...
	--- ```
	magnitude = function(self) end,

	--- Calculates the distance between this vector and another vector.
	--- @param vec3 Vec3 The other vector.
	--- @return number @The distance between the two vectors.
	--- ```lua
	--- local distance = vec1:distance(vec2)
	--- print(distance)  -- 5.19...
	--- ```
	distance = function(self, vec3) end,

	--- Performs linear interpolation between this vector and another vector.
	--- @param vec3 Vec3 The target vector.
	--- @param t number The interpolation factor (between 0 and 1).
	--- @return Vec3 @A new vector resulting from the interpolation.
	--- ```lua
	--- local result = vec1:lerp(vec2, 0.5)
	--- print(result.x, result.y, result.z)  -- 2.5, 3.5, 4.5
	--- ```
	lerp = function(self, vec3, t) end,
}

--- Creates a new vector.
--- @param x number The x-coordinate.
--- @param y number The y-coordinate.
--- @param z number The z-coordinate.
--- @return Vec3 @A new Vec3 instance.
--- ```lua
--- local vec = Vec3.new(1, 2, 3)
--- ```
function Vec3.new(x, y, z) end

--- @class LuaCraftItem
--- Represents an item in the game, allowing Lua scripts to interact with its metadata and modify its attributes.
local LuaCraftItem = {
	--- Retrieves the metadata of the item, including name, lore, enchantments, and type.
	--- @return table @A table containing metadata fields like `name`, `lore`, `enchantments`, `type`, and `amount`.
	--- ```lua
	--- local metadata = item.getItemMetadata()
	--- print(metadata.name, metadata.lore[1], metadata.enchantments["sharpness"], metadata.type)
	--- ```
	getItemMetadata = function() end,

	--- Sets the display name of the item.
	--- @param displayName string The custom name to set for the item.
	--- ```lua
	--- item.setDisplayName("Legendary Sword")
	--- ```
	setDisplayName = function(displayName) end,

	--- Sets the lore (description) of the item.
	--- @param lore table A table of strings representing the item's lore.
	--- ```lua
	--- item.setLore({ "Forged in the fires of the nether", "Unbreakable" })
	--- ```
	setLore = function(lore) end,

	--- Adds a safe enchantment to the item, ensuring valid enchantments and levels.
	--- @param enchantmentName string The name of the enchantment (e.g., "sharpness").
	--- @param level number The level of the enchantment (must be within valid bounds).
	--- ```lua
	--- item.addSafeEnchantment("sharpness", 5)
	--- ```
	addSafeEnchantment = function(enchantmentName, level) end,

	--- Removes an enchantment from the item if it exists.
	--- @param enchantmentName string The name of the enchantment to remove (e.g., "sharpness").
	--- ```lua
	--- item.removeSafeEnchantment("sharpness")
	--- ```
	removeSafeEnchantment = function(enchantmentName) end,

	--- Sets custom model data for the item.
	--- @param data number The custom model data value to set.
	--- ```lua
	--- item.setCustomModelData(123456)
	--- ```
	setCustomModelData = function(data) end,

	--- Sets a persistent string-based value on the item using a key.
	--- @param key string The key to store the data under.
	--- @param value string The string value to store.
	--- ```lua
	--- item.setPersistentData("owner", "Player123")
	--- ```
	setPersistentData = function(key, value) end,

	--- Retrieves a persistent string-based value stored on the item.
	--- @param key string The key to retrieve the data from.
	--- @return string|nil @The stored value, or nil if not set.
	--- ```lua
	--- local owner = item.getPersistentData("owner")
	--- if owner then
	---     print("This item belongs to:", owner)
	--- end
	--- ```
	getPersistentData = function(key) end,

	--- Sets whether the item is unbreakable.
	--- @param unbreakable boolean True to make the item unbreakable, false to make it breakable.
	--- ```lua
	--- item.setUnbreakable(true)
	--- ```
	setUnbreakable = function(unbreakable) end,
}

--- @class LuaCraftItemData
local LuaCraftItemData = {
	--- @type LuaCraftPlayer
	player = nil,
	--- @type string
	name = nil,
	--- @type { [number]: string }
	lore = {},
	--- @type { [string]: number }
	enchantments = {},
}

--- @class LuaCraftPlayer
--- Represents a player in the game, exposing methods to interact with the player.
local LuaCraftPlayer = {
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

	--- Retrieves the player's name.
	--- @return string @The name of the player.
	--- ```lua
	--- local name = player.getName()
	--- print(name)
	--- ```
	getName = function() end,

	--- Checks if the player is online.
	--- @return boolean @True if the player is online, false otherwise.
	--- ```lua
	--- local online = player.isOnline()
	--- print(online)
	--- ```
	isOnline = function() end
}

--- @class LuaCraftEntity
--- Represents an entity in the Minecraft world. This class provides methods to interact with and manipulate the entity's attributes.
local LuaCraftEntity = {
	--- Retrieves the entity's position as a Lua table with `x`, `y`, and `z` coordinates.
	--- @return table @A table with `x`, `y`, and `z` fields representing the entity's current position.
	--- ```lua
	--- local position = entity.getPosition()
	--- print(position.x, position.y, position.z)
	--- ```
	getPosition = function() end,

	--- Teleports the entity to the specified coordinates.
	--- @param position Vec3
	--- ```lua
	--- entity.setPosition(Vec3.new(100, 64, 100))
	--- ```
	setPosition = function(position) end,

	--- Sets a custom name for the entity.
	--- @param name string The custom name to set.
	--- ```lua
	--- entity.setCustomName("Boss Monster")
	--- ```
	setCustomName = function(name) end,

	--- @type string @The type of the entity *(e.g., "CREEPER", "ZOMBIE")*.
	--- ```lua
	--- print("Entity type:", entity.type)
	--- ```
	type = nil,

	--- Retrieves the entity's UUID as a string.
	--- @return string @The UUID of the entity.
	--- ```lua
	--- local uuid = entity.getUUID()
	--- print("Entity UUID:", uuid)
	--- ```
	getUUID = function() end,

	--- Sets the health of the entity if it is a LivingEntity.
	--- @param health number The health value to set.
	--- ```lua
	--- entity.setHealth(20)
	--- ```
	setHealth = function(health) end,

	--- Sets whether the entity is a baby (for ageable entities).
	--- @param isBaby boolean True to set the entity as a baby, false to set it as an adult.
	--- ```lua
	--- entity.setBaby(true) -- Set entity to baby
	--- entity.setBaby(false) -- Set entity to adult
	--- ```
	setBaby = function(isBaby) end,

	--- Sets whether the entity (if it is a Creeper) is charged.
	--- @param charged boolean True to charge the Creeper, false to discharge it.
	--- ```lua
	--- entity.setCharged(true) -- Charge the Creeper
	--- entity.setCharged(false) -- Remove the charge from the Creeper
	--- ```
	setCharged = function(charged) end,

	--- Retrieves the custom name of the entity, or nil if not set.
	--- @return string|nil @The custom name of the entity, or nil if not set.
	--- ```lua
	--- local customName = entity.getCustomName()
	--- if customName then
	---     print("Entity custom name:", customName)
	--- else
	---     print("Entity has no custom name.")
	--- end
	--- ```
	getCustomName = function() end,

	--- Enables or disables the AI for the entity if it is a LivingEntity.
	--- @param enabled boolean True to enable AI, false to disable it.
	--- ```lua
	--- entity.setAI(false) -- Disable AI
	--- entity.setAI(true) -- Enable AI
	--- ```
	setAI = function(enabled) end,
}

--- @class LuaCraft
LuaCraft = {}

--- Retrieves a player by name.
--- @param playerName string The name of the player.
--- @return LuaCraftPlayer @The player object.
--- ```lua
--- local player = LuaCraft.getPlayer("Steve")
--- ```
function LuaCraft.getPlayer(playerName) end

--- Retrieves the local player who initiated the current action.
--- @return LuaCraftPlayer @The local player.
--- ```lua
--- local player = LuaCraft.getLocalPlayer()
--- ```
function LuaCraft.getLocalPlayer() end

--- Retrieves all players on the server.
--- @return { [number]: LuaCraftPlayer } @A table containing all online players.
--- ```lua
--- local players = LuaCraft.getPlayers()
--- for i, player in ipairs(players) do
---     print(player:getName())
--- end
--- ```
function LuaCraft.getPlayers() end

--- Prints a message to all players' chat.
--- @vararg ... The messages to print.
--- ```lua
--- LuaCraft.print("Hello, World!")
--- ```
function LuaCraft.print(...) end

--- Broadcasts a message to the server console and all players.
--- @param message string The message to broadcast.
--- ```lua
--- LuaCraft.broadcast("Server restarting soon!")
--- ```
function LuaCraft.broadcast(message) end

--- Converts ampersand `&` to section `§` in strings for Minecraft formatting.
--- @param inputString string The string to convert.
--- @return string @The converted string.
--- ```lua
--- local formatted = LuaCraft.toSections("&aGreen &bText")
--- ```
function LuaCraft.toSections(inputString) end

--- Blocks execution for a given number of seconds.
--- @param seconds number The number of seconds to sleep.
--- ```lua
--- LuaCraft.sleep(2)
--- ```
function LuaCraft.sleep(seconds) end

--- Waits (non-blocking) for a given number of seconds.
--- @param seconds number The number of seconds to wait.
--- ```lua
--- LuaCraft.wait(5)
--- ```
function LuaCraft.wait(seconds) end

--- Executes a command as the local player.
--- @param command string The command to run.
--- ```lua
--- LuaCraft.runCommand("/give @p diamond 1")
--- ```
function LuaCraft.runCommand(command) end

--- Summons an entity at the specified position.
--- @param entityName string The entity to summon (e.g., "CREEPER").
--- @param position Vec3 The position to summon the entity at.
--- ```lua
--- LuaCraft.summonAtPosition("CREEPER", Vec3.new(100, 64, 100))
--- ```
function LuaCraft.summonEntityAt(entityName, position) end

--- Retrieves all entities in the world.
--- @return { [number]: LuaCraftEntity } @A table of all entities.
--- ```lua
--- local entities = LuaCraft.getAllEntities()
--- for i, entity in ipairs(entities) do
---     print(entity:getUUID())
--- end
--- ```
function LuaCraft.getEntities() end

--- Modifies an entity's data.
--- @param entityUUID string The UUID of the entity.
--- @param modifications table A table with keys like `customName`, `health`, `position`.
--- ```lua
--- LuaCraft.modifyEntityData("entity-uuid", { customName = "Boss", health = 20 })
--- ```
function LuaCraft.modifyEntityData(entityUUID, modifications) end

--- Logs a message to the server console.
--- @vararg ... The messages to log.
--- ```lua
--- LuaCraft.consoleMessage("This is a log message.")
--- ```
function LuaCraft.consoleMessage(...) end

--- Creates a custom item with the specified properties and gives it to a player.
--- @param materialName string The material name (e.g., "DIAMOND_SWORD").
--- @param itemData table The item data, including `player`, `name`, `lore`, `enchantments`.
--- @return LuaCraftItem @The created item.
--- ```lua
--- LuaCraft.createItem("NETHERITE_SWORD", {
---     player = LuaCraft.getLocalPlayer(),
---     name = "&6Legendary Sword",
---     lore = { "A sword forged in the nether." },
---     enchantments = { { "sharpness", 5 }, { "unbreaking", 3 } }
--- })
--- ```
function LuaCraft.createItem(materialName, itemData) end

--- Retrieves the block type the player is currently standing on.
--- @return string @The block type (e.g., "STONE", "GRASS_BLOCK") that the player is standing on.
--- ```lua
--- local blockType = LuaCraft.getPlayerBlockType()
--- print("You are standing on: " .. blockType)
--- ```
function LuaCraft.getPlayerBlockType() end

--- Sets the player's movement speed.
--- @param speed number The desired speed, between -10 and 10. A value of 1 is the maximum forward speed, and -1 is the maximum reverse speed.
--- ```lua
--- LuaCraft.setPlayerSpeed(5) -- Set speed to 50% of the maximum forward speed
--- ```
function LuaCraft.setPlayerSpeed(speed) end

--- Sets the player's flight speed.
--- @param speed number The desired flight speed, between 1 and 10. A value of 1 corresponds to the slowest flight speed (0.1), and 10 corresponds to the maximum flight speed (1.0).
--- ```lua
--- LuaCraft.setPlayerFlightSpeed(5) -- Set flight speed to 50% of the maximum flight speed
--- LuaCraft.setPlayerFlightSpeed(10) -- Set flight speed to the maximum
--- ```
function LuaCraft.setPlayerFlightSpeed(speed) end

--- @meta

--- @class LuaCraftWorld
--- Provides a system to bind Lua functions to various server events, allowing interaction with players and the world.
LuaCraftWorld = {}

--- Binds a Lua function to a specific server event.
--- @param eventName string The name of the event to bind (e.g., "PlayerJoin", "PlayerQuit").
--- @param callback function The Lua function to call when the event is triggered.
--- @return LuaCraftWorldEvent @An event object that allows disconnecting the event later.
--- ```lua
--- LuaCraftWorld.bindEvent("PlayerJoin", function(player)
---     print(player.getName .. " has joined the server!")
--- end)
--- ```
function LuaCraftWorld.bindEvent(eventName, callback) end

--- @class LuaCraftWorldEvent
--- Represents an event that was previously bound, allowing it to be disconnected later.
LuaCraftWorldEvent = {}

--- Unbinds the previously bound event, stopping the Lua function from being called when the event triggers.
--- ```lua
--- local event = LuaCraftWorld.bindEvent("PlayerJoin", function(player)
---     print(player.getName .. " has joined the server!")
--- end)
--- event.unbindEvent()  -- Disconnect the event listener
--- ```
function LuaCraftWorldEvent.unbindEvent() end

--- Writes content to a file within the LuaCraft plugin's folder.
--- @param filePath string The path to the file relative to the LuaCraft folder.
--- @param content string The content to write to the file.
--- @return boolean @Returns `true` if the file was successfully written, `false` otherwise.
--- ```lua
--- local success = writeFile("data/myfile.txt", "Hello, LuaCraft!")
--- if success then
---     print("File written successfully!")
--- else
---     print("Failed to write the file.")
--- end
--- ```
function writeFile(filePath, content) end

--- Reads content from a file within the LuaCraft plugin's folder.
--- @param filePath string The path to the file relative to the LuaCraft folder.
--- @return string? @Returns the content of the file, or `nil` if the file could not be read.
--- ```lua
--- local content = readFile("data/myfile.txt")
--- if content then
---     print("File content: " .. content)
--- else
---     print("Failed to read the file.")
--- end
--- ```
function readFile(filePath) end
