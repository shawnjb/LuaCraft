--- @meta

--- @class LuaCraft
luacraft = {}

--- Prints messages to the last command sender's chat.
--- @vararg ... The messages to be printed.
--- ```lua
--- luacraft.print("Hello, World!")
--- luacraft.print("Your score is", score)
--- ```
function luacraft.print(...) end

--- Colorizes a given text using Minecraft's color codes.
--- @param color string The color code (e.g., "a" for green).
--- @param text string The text to be colorized.
--- @return string @The colorized text.
--- ```lua
--- local message = luacraft.colorize("a", "This is green text.")
--- ```
function luacraft.colorize(color, text) end

--- Pauses the execution for a specified number of seconds.
--- @param seconds number The duration to wait in seconds.
--- ```lua
--- luacraft.wait(5) -- Waits for 5 seconds without blocking other processes.
--- ```
function luacraft.wait(seconds) end

--- Blocks execution for a specified number of seconds.
--- @param seconds number The duration to sleep in seconds.
--- ```lua
--- luacraft.sleep(2.5) -- Blocks execution for 2.5 seconds.
--- ```
function luacraft.sleep(seconds) end

--- Executes a command as the last command sender.
--- Only players can run commands.
--- @param command string The command to be executed.
--- ```lua
--- luacraft.runcommand("/give @p diamond 1")
--- ```
--- @deprecated
function luacraft.runcommand(command) end

--- Executes a command as the last command sender.
--- Only players can run commands.
--- @param command string The command to be executed.
--- ```lua
--- luacraft.runCommand("/give @p diamond 1")
--- ```
function luacraft.runCommand(command) end

--- Applies a new position to the executing player.
--- @param x integer The new X coordinate.
--- @param y integer The new Y coordinate.
--- @param z integer The new Z coordinate.
--- ```lua
--- luacraft.setPlayerPosition(0, 120, 0)
--- ```
function luacraft.setPlayerPosition(x, y, z) end

--- Retrieves the position of the executing player.
--- @return { x: integer, y: integer, z: integer }
--- ```lua
--- local vec3 = luacraft.getPlayerPosition()
--- print(vec3.x, vec3.y, vec3.z)
--- ```
function luacraft.getPlayerPosition() end

--- Summons an entity at the specified coordinates.
--- The entity is identified by its Enum name.
--- @param entityName string The name of the entity (as a string) to summon (e.g., "CREEPER", "ZOMBIE").
--- @param x number The X coordinate where the entity should be summoned.
--- @param y number The Y coordinate where the entity should be summoned.
--- @param z number The Z coordinate where the entity should be summoned.
--- ```lua
--- -- Summons a creeper at (100, 64, 100)
--- luacraft.summonAtPosition("CREEPER", 100, 64, 100)
---
--- -- Summons a skeleton at the player's current position
--- local pos = luacraft.getPlayerPosition()
--- luacraft.summonAtPosition("SKELETON", pos.x, pos.y, pos.z)
--- ```
function luacraft.summonAtPosition(entityName, x, y, z) end

--- Retrieves all entities in the player's world as a JSON string.
--- @return string @A JSON array containing information about all entities, where each entry has:
---   - `id`: string (The UUID of the entity)
---   - `type`: string (The type of the entity)
---   - `position`: table (A table containing the x, y, z coordinates of the entity's location)
--- ```lua
--- local allEntities = luacraft.getAllEntities()
--- print(allEntities) -- Outputs a JSON string of all entities
--- ```
function luacraft.getAllEntities() end

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
--- luacraft.modifyEntityData("entity-uuid", mods)
--- ```
function luacraft.modifyEntityData(entityUUID, modifications) end

--- Logs a message to the server console.
--- @vararg string The message to log.
--- This function takes a single string argument and logs it to the 
--- Bukkit server console with the prefix "[LuaCraft]".
--- ```lua
--- luacraft.consoleMessage("This is a log message.")
--- ```
function luacraft.consoleMessage(...) end