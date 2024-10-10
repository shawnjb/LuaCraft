--- @meta

--- @class LuaCraft
LuaCraft = {}

--- @type LuaCraft
--- Removed. Use `LuaCraft`.
--- @deprecated
luacraft = {}

--- @class LuaCraftPlayer
--- Represents a player in the game.
--- Contains methods to interact with the player.
local LuaCraftPlayer = {
    --- Sends a message to the player.
    --- @param message string The message to send.
    --- ```lua
    --- player:sendMessage("Hello, Player!")
    --- ```
    sendMessage = function(message) end,

    --- Retrieves the player's position as a table.
    --- @return { x: number, y: number, z: number }
    --- ```lua
    --- local position = player:getPosition()
    --- print(position.x, position.y, position.z)
    --- ```
    getPosition = function() end,

    --- Teleports the player to the specified coordinates.
    --- @param x number The X coordinate.
    --- @param y number The Y coordinate.
    --- @param z number The Z coordinate.
    --- ```lua
    --- player:teleport(100, 64, 100)
    --- ```
    teleport = function(x, y, z) end,

    --- Retrieves the player's name.
    --- @return string @The name of the player.
    --- ```lua
    --- local name = player:getName()
    --- ```
    getName = function() end,

    --- Checks if the player is online.
    --- @return boolean @True if the player is online, false otherwise.
    --- ```lua
    --- local online = player:isOnline()
    --- ```
    isOnline = function() end,

    --- Gives an item to the player.
    --- @param itemName string The name of the item to give.
    --- @param amount number The quantity of the item.
    --- ```lua
    --- player:giveItem("DIAMOND", 5)
    --- ```
    giveItem = function(itemName, amount) end,

    --- Runs a command as the player.
    --- @param command string The command to execute.
    --- ```lua
    --- player:runCommand("/give @p diamond 1")
    --- ```
    runCommand = function(command) end
}

--- @class LuaCraftEntity
--- Represents an entity in the game.
--- Contains methods to interact with and manipulate the entity.
local LuaCraftEntity = {
    --- Retrieves the entity's UUID.
    --- @return string @The UUID of the entity.
    --- ```lua
    --- local uuid = entity:getUUID()
    --- print("Entity UUID:", uuid)
    --- ```
    getUUID = function() end,

    --- Retrieves the entity's type.
    --- @return string @The type of the entity (e.g., "CREEPER", "ZOMBIE").
    --- ```lua
    --- local entityType = entity:getType()
    --- print("Entity type:", entityType)
    --- ```
    getType = function() end,

    --- Retrieves the entity's position as a table.
    --- @return { x: number, y: number, z: number }
    --- ```lua
    --- local position = entity:getPosition()
    --- print(position.x, position.y, position.z)
    --- ```
    getPosition = function() end,

    --- Teleports the entity to the specified coordinates.
    --- @param x number The X coordinate.
    --- @param y number The Y coordinate.
    --- @param z number The Z coordinate.
    --- ```lua
    --- entity:teleport(100, 64, 100)
    --- ```
    teleport = function(x, y, z) end,

    --- Sets a custom name for the entity.
    --- @param name string The custom name to set.
    --- ```lua
    --- entity:setCustomName("Boss Monster")
    --- ```
    setCustomName = function(name) end,

    --- Retrieves the custom name of the entity.
    --- @return string|nil @The custom name of the entity or nil if no custom name is set.
    --- ```lua
    --- local name = entity:getCustomName()
    --- print("Entity name:", name)
    --- ```
    getCustomName = function() end,

    --- Sets the health of the entity (if it's a living entity).
    --- @param health number The health value to set.
    --- ```lua
    --- entity:setHealth(20)
    --- ```
    setHealth = function(health) end,

    --- Enables or disables the AI of the entity (if it's a living entity).
    --- @param enabled boolean True to enable AI, false to disable it.
    --- ```lua
    --- entity:setAI(false) -- Disable AI
    --- ```
    setAI = function(enabled) end,

    --- Sets whether the entity is a baby (if it's an ageable entity).
    --- @param isBaby boolean True to make the entity a baby, false to make it an adult.
    --- ```lua
    --- entity:setBaby(true)
    --- ```
    setBaby = function(isBaby) end,

    --- Sets whether a Creeper is charged.
    --- @param charged boolean True to charge the Creeper, false to discharge it.
    --- ```lua
    --- entity:setCharged(true)
    --- ```
    setCharged = function(charged) end
}

--- Prints messages to the last command sender's chat.
--- @vararg ... The messages to be printed.
--- ```lua
--- LuaCraft.print("Hello, World!")
--- LuaCraft.print("Your score is", score)
--- ```
function LuaCraft.print(...) end

--- Broadcasts a message to all players on the server.
--- @param message string The message to broadcast.
--- ```lua
--- LuaCraft.broadcast("Server is restarting soon!")
--- ```
function LuaCraft.broadcast(message) end

--- Logs a message to the server console with a "[LuaCraft]" prefix.
--- @param message string The message to log.
--- ```lua
--- LuaCraft.consoleMessage("Script execution started.")
--- ```
function LuaCraft.consoleMessage(message) end

--- Pauses the execution for a specified number of seconds.
--- @param seconds number The duration to wait in seconds.
--- ```lua
--- LuaCraft.wait(5) -- Waits for 5 seconds without blocking other processes.
--- ```
function LuaCraft.wait(seconds) end

--- Blocks execution for a specified number of seconds.
--- @param seconds number The duration to sleep in seconds.
--- ```lua
--- LuaCraft.sleep(2.5) -- Blocks execution for 2.5 seconds.
--- ```
function LuaCraft.sleep(seconds) end

--- Executes a command as the last command sender.
--- Only players can run commands.
--- @param command string The command to be executed.
--- ```lua
--- LuaCraft.runcommand("/give @p diamond 1")
--- ```
--- @deprecated
function LuaCraft.runcommand(command) end

--- Executes a command as the last command sender.
--- Only players can run commands.
--- @param command string The command to be executed.
--- ```lua
--- LuaCraft.runCommand("/give @p diamond 1")
--- ```
function LuaCraft.runCommand(command) end

--- Applies a new position to the executing player.
--- @param x integer The new X coordinate.
--- @param y integer The new Y coordinate.
--- @param z integer The new Z coordinate.
--- ```lua
--- LuaCraft.setPlayerPosition(0, 120, 0)
--- ```
function LuaCraft.setPlayerPosition(x, y, z) end

--- Retrieves the position of the executing player.
--- @return { x: integer, y: integer, z: integer }
--- ```lua
--- local vec3 = LuaCraft.getPlayerPosition()
--- print(vec3.x, vec3.y, vec3.z)
--- ```
function LuaCraft.getPlayerPosition() end

--- Retrieves the executing player as a LuaCraftPlayer object.
--- @return LuaCraftPlayer @The player object.
--- ```lua
--- local player = LuaCraft.getLocalPlayer()
--- ```
function LuaCraft.getLocalPlayer() end

--- Summons an entity at the specified coordinates.
--- The entity is identified by its Enum name.
--- @param entityName string The name of the entity (as a string) to summon (e.g., "CREEPER", "ZOMBIE").
--- @param x number The X coordinate where the entity should be summoned.
--- @param y number The Y coordinate where the entity should be summoned.
--- @param z number The Z coordinate where the entity should be summoned.
--- ```lua
--- -- Summons a creeper at (100, 64, 100)
--- LuaCraft.summonAtPosition("CREEPER", 100, 64, 100)
---
--- -- Summons a skeleton at the player's current position
--- local pos = LuaCraft.getPlayerPosition()
--- LuaCraft.summonAtPosition("SKELETON", pos.x, pos.y, pos.z)
--- ```
function LuaCraft.summonAtPosition(entityName, x, y, z) end

--- Retrieves all entities in the player's world as a Lua table.
--- @return { [number]: LuaCraftEntity } @A table where each entry is a LuaCraftEntity object.
--- ```lua
--- local allEntities = LuaCraft.getAllEntities()
--- for i, entity in ipairs(allEntities) do
---     print(entity.id, entity.type)
--- end
--- ```
function LuaCraft.getAllEntities() end

--- Modifies an entity's properties based on a table of attributes.
--- @param entityUUID string The UUID of the entity to be modified.
--- @param modifications table A table containing the properties to modify. Possible keys:
---   - `customName`: string (The new name for the entity).
---   - `health`: number (New health value for the entity, only for living entities).
---   - `position`: table (A table containing x, y, z coordinates for the entity's new location).
---   - `charged`: boolean (Sets whether a creeper should be charged or not).
---   - `isBaby`: boolean (Sets whether an ageable entity should be a baby or an adult).
--- ```lua
--- local mods = {
---     customName = "Boss Monster",
---     health = 100,
---     position = {x = 200, y = 80, z = -300},
---     charged = true,
---     isBaby = false
--- }
--- LuaCraft.modifyEntityData("entity-uuid", mods)
--- ```
function LuaCraft.modifyEntityData(entityUUID, modifications) end

--- Logs a message to the server console.
--- @vararg string The message to log.
--- This function takes a single string argument and logs it to the
--- Bukkit server console with the prefix "[LuaCraft]".
--- ```lua
--- LuaCraft.consoleMessage("This is a log message.")
--- ```
function LuaCraft.consoleMessage(...) end

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

--- Creates an item with custom properties such as a name, lore, and enchantments, 
--- and adds it to the player's inventory.
--- 
--- @param materialName string The name of the material (e.g., "DIAMOND_SWORD", "NETHERITE_SWORD").
--- @param itemData LuaCraftItemData A table containing the properties for the item:
---   - `player` LuaCraftPlayer: The player who will receive the item.
---   - `name`? string: Optional custom name for the item, with color codes.
---   - `lore`? table: Optional table of lore lines for the item.
---   - `enchantments`? table: Optional table of enchantments, each entry containing:
---     - `[1]` string: The enchantment name (e.g., "sharpness", "unbreaking").
---     - `[2]` number: The enchantment level.
---
--- @return LuaCraftItem @The created item, as a LuaCraftItem object.
---
--- ```lua
--- local itemData = {
---     player = LuaCraft.getLocalPlayer(),
---     name = "&r&6Legendary Sword",
---     lore = {
---         "&r&7This sword was forged in the depths of the nether.",
---         "&r&fUnbreakable and powerful."
---     },
---     enchantments = {
---         { "sharpness", 5 },
---         { "unbreaking", 3 }
---     }
--- }
--- local item = LuaCraft.createItem("NETHERITE_SWORD", itemData)
--- ```
function LuaCraft.createItem(materialName, itemData) end

--- Converts all ampersands (&) in a string to the section (§) symbol.
--- This is typically used for formatting codes in text where the section symbol is a control character.
--- @param inputString string The string in which ampersands will be replaced with the section symbol.
--- @return string @The modified string with all ampersands replaced by section symbols.
--- ```lua
--- local formattedText = LuaCraft.toSections("&cHello, &aWorld!")
--- print(formattedText) -- Output: §cHello, §aWorld!
--- ```
function LuaCraft.toSections(inputString) end
