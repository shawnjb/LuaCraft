--- @meta

--- @class LuaCraftScript
--- Represents the currently executing script. Use this class to obtain details about the running script.
script = {
    --- The script's name.
    --- @type string
    name = ''
}

--- @class LuaCraftItem
--- Represents an item in the game.
--- Currently, this class has no methods or fields defined.
local LuaCraftItem = {}

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
    --- @param x number The X coordinate.
    --- @param y number The Y coordinate.
    --- @param z number The Z coordinate.
    --- ```lua
    --- player.setPosition(100, 64, 100)
    --- ```
    setPosition = function(x, y, z) end,

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
--- Represents an entity in the Minecraft world with limited methods exposed via toLuaValue.
local LuaCraftEntity = {
    --- Retrieves the entity's position as a Lua table with `x`, `y`, and `z` coordinates.
    --- @return table @A table with `x`, `y`, `z` fields representing the entity's current position.
    --- ```lua
    --- local position = entity.getPosition()
    --- print(position.x, position.y, position.z)
    --- ```
    getPosition = function() end,

    --- Teleports the entity to the specified coordinates.
    --- @param x number The X coordinate.
    --- @param y number The Y coordinate.
    --- @param z number The Z coordinate.
    --- ```lua
    --- entity.setPosition(100, 64, 100)
    --- ```
    setPosition = function(x, y, z) end,

    --- Sets a custom name for the entity.
    --- @param name string The custom name to set.
    --- ```lua
    --- entity.setCustomName("Boss Monster")
    --- ```
    setCustomName = function(name) end,

    --- Retrieves the entity's type as a string.
    --- @return string @The type of the entity (e.g., "CREEPER", "ZOMBIE").
    --- ```lua
    --- local entityType = entity.getType()
    --- print("Entity type:", entityType)
    --- ```
    getType = function() end,

    --- Retrieves the entity's UUID as a string.
    --- @return string @The UUID of the entity.
    --- ```lua
    --- local uuid = entity.getUUID()
    --- print("Entity UUID:", uuid)
    --- ```
    getUUID = function() end
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
--- @param x number The X coordinate.
--- @param y number The Y coordinate.
--- @param z number The Z coordinate.
--- ```lua
--- LuaCraft.summonAtPosition("CREEPER", 100, 64, 100)
--- ```
function LuaCraft.summonAtPosition(entityName, x, y, z) end

--- Retrieves all entities in the world.
--- @return { [number]: LuaCraftEntity } @A table of all entities.
--- ```lua
--- local entities = LuaCraft.getAllEntities()
--- for i, entity in ipairs(entities) do
---     print(entity:getUUID())
--- end
--- ```
function LuaCraft.getAllEntities() end

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