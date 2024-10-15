--- @meta
--- @class LuaCraft
--- The base class for everything in LuaCraft. This is not safe from environment overwriting!
LuaCraft = {}

-- LuaCraftPlayer methods

--- Gets a player by name.
--- @param playerName string The name of the player to get.
--- @return LuaCraftPlayer? @The player object, or `nil` if the player is not found.
--- ```lua
--- local player = LuaCraft.getPlayer("Steve")
--- ```
function LuaCraft.getPlayer(playerName) end

--- Gets a player by their UUID.
--- @param uuid string The UUID of the player to get.
--- @return LuaCraftPlayer? @The player object, or `nil` if the player is not found.
--- ```lua
--- local player = LuaCraft.getPlayerFromUUID("123e4567-e89b-12d3-a456-426614174000")
--- ```
function LuaCraft.getPlayerFromUUID(uuid) end

--- Returns a table containing all online players
--- @return { [number]: LuaCraftPlayer }
--- ```lua
--- local playerList = LuaCraft.getPlayers()
--- ```
function LuaCraft.getPlayers(playerName) end

--- Gets the local player (the one executing the command).
--- @return LuaCraftPlayer? @The local player object, or `nil` if not available.
--- ```lua
--- local player = LuaCraft.getLocalPlayer()
--- ```
function LuaCraft.getLocalPlayer() end

--- Sets the player's walking speed.
--- @param player LuaCraftPlayer? The player whose walking speed to set.
--- @param speed number The speed value (1.0 to 10.0).
--- @return boolean @`true` if the speed was set successfully.
--- ```lua
--- LuaCraft.setPlayerWalkSpeed(player, 5)
--- ```
function LuaCraft.setPlayerWalkSpeed(player, speed) end

--- Sets the player's flying speed.
--- @param player LuaCraftPlayer? The player whose flying speed to set.
--- @param speed number The speed value (1.0 to 10.0).
--- @return boolean @`true` if the speed was set successfully.
--- ```lua
--- LuaCraft.setPlayerFlySpeed(player, 7)
--- ```
function LuaCraft.setPlayerFlySpeed(player, speed) end

--- Gets the current dimension of the player.
--- @param player LuaCraftPlayer? The player whose dimension to get.
--- @return string @The name of the player's current dimension.
--- ```lua
--- local dimension = LuaCraft.getCurrentDimension(player)
--- ```
function LuaCraft.getCurrentDimension(player) end

--- Gets the block directly under the player.
--- @param player LuaCraftPlayer? The player whose block under them to get.
--- @return string @The block type under the player.
--- ```lua
--- local block = LuaCraft.getBlockUnderPlayer(player)
--- ```
function LuaCraft.getBlockUnderPlayer(player) end

--- Gets the nearest player to the given player.
--- @param player LuaCraftPlayer? The player to find the nearest player to.
--- @return LuaCraftPlayer? @The nearest player object, or `nil` if no player is found.
--- ```lua
--- local nearestPlayer = LuaCraft.getNearestPlayer(player)
--- ```
function LuaCraft.getNearestPlayer(player) end

--- Gets the furthest player from the given player.
--- @param player LuaCraftPlayer? The player to find the furthest player from.
--- @return LuaCraftPlayer? @The furthest player object, or `nil` if no player is found.
--- ```lua
--- local furthestPlayer = LuaCraft.getFurthestPlayer(player)
--- ```
function LuaCraft.getFurthestPlayer(player) end

-- LuaCraftEntity methods

--- Spawns a new entity in the world.
--- @param entityType string The type of the entity to spawn (e.g., "ZOMBIE").
--- @param position table The position where the entity should be spawned (e.g., `{x=100, y=64, z=100}`).
--- @return LuaCraftEntity @The newly spawned entity.
--- ```lua
--- local entity = LuaCraft.newEntity("ZOMBIE", {x=100, y=64, z=100})
--- ```
function LuaCraft.newEntity(entityType, position) end

--- Gets all entities in the player's world.
--- @return { [number]: LuaCraftEntity } @A list of all entities.
--- ```lua
--- local entities = LuaCraft.getEntities()
--- ```
function LuaCraft.getEntities() end

--- Gets all entities of a specific type in the player's world.
--- @param entityType string The type of the entities to get.
--- @return { [number]: LuaCraftEntity } @A list of entities of the specified type.
--- ```lua
--- local zombies = LuaCraft.getEntitiesByType("ZOMBIE")
--- ```
function LuaCraft.getEntitiesByType(entityType) end

--- Gets the closest entity to the player.
--- @param player LuaCraftPlayer? The player to find the closest entity to.
--- @param entityType string? The type of entity to look for (optional).
--- @return LuaCraftEntity? @The closest entity, or `nil` if no entity is found.
--- ```lua
--- local closestEntity = LuaCraft.getClosestEntity(player, "CREEPER")
--- ```
function LuaCraft.getClosestEntity(player, entityType) end

--- Gets the furthest entity from the player.
--- @param player LuaCraftPlayer? The player to find the furthest entity from.
--- @param entityType string? The type of entity to look for (optional).
--- @return LuaCraftEntity? @The furthest entity, or `nil` if no entity is found.
--- ```lua
--- local furthestEntity = LuaCraft.getFurthestEntity(player, "CREEPER")
--- ```
function LuaCraft.getFurthestEntity(player, entityType) end

-- LuaCraftItem methods

--- Creates a new item with custom attributes and gives it to the specified player.
--- 
--- @param materialName string The name of the material (e.g., "DIAMOND_SWORD").
--- @param itemData LuaCraftItemData A table containing the custom attributes for the item.
--- @return LuaCraftItem The newly created item if successful, or nil if creation failed.
--- 
--- Example usage:
--- ```lua
--- local player = LuaCraft.getLocalPlayer()
--- LuaCraftWorld.NewItem("DIAMOND_SWORD", {
---     player = player,
---     name = "&6Legendary Sword",
---     lore = { "Forged in the fires of the nether", "Unbreakable" },
---     enchantments = { { "sharpness", 5 }, { "unbreaking", 3 } }
--- })
--- ```
function LuaCraft.newItem(materialName, itemData) end

--- Gets the player's inventory.
--- @param player LuaCraftPlayer? The player whose inventory to get.
--- @return table @A list of items in the player's inventory.
--- ```lua
--- local inventory = LuaCraft.getInventory(player)
--- ```
function LuaCraft.getInventory(player) end

--- Clears the inventory of the player.
--- @param player LuaCraftPlayer? The player whose inventory to clear.
--- @return boolean @`true` if the inventory was cleared successfully.
--- ```lua
--- LuaCraft.clearInventory(player)
--- ```
function LuaCraft.clearInventory(player) end

-- Networking methods

--- Makes an HTTP GET request to the specified URL and returns the response.
--- @param url string The URL to fetch.
--- @return string|nil @The response content, or `nil` if the request failed.
--- ```lua
--- local response = LuaCraft.httpGet("https://example.com")
--- print(response)
--- ```
function LuaCraft.httpGet(url) end

--- Creates a TCP socket connection to the specified host and port.
--- @param host string The host to connect to.
--- @param port number The port to connect to.
--- @return table @A socket object with methods for sending and receiving data.
--- ```lua
--- local socket = LuaCraft.socketConnect("localhost", 8080)
--- socket.send("Hello!")
--- local response = socket.receive()
--- print(response)
--- socket.close()
--- ```
function LuaCraft.socketConnect(host, port) end

--- Creates a TCP socket server listening on the specified port.
--- @param port number The port to listen on.
--- @return table @A server object with methods for accepting connections.
--- ```lua
--- local server = LuaCraft.socketServer(8080)
--- local client = server.accept()
--- local request = client.receive()
--- print(request)
--- client.send("Hello, client!")
--- client.close()
--- server.close()
--- ```
function LuaCraft.socketServer(port) end

-- Miscellaneous methods

--- Pauses execution for a specified number of seconds.
--- @param seconds number The number of seconds to pause.
--- @return nil
--- ```lua
--- LuaCraft.sleep(5)
--- ```
function LuaCraft.sleep(seconds) end

--- Pauses execution asynchronously for a specified number of seconds.
--- @param seconds number The number of seconds to wait.
--- @return nil
--- ```lua
--- LuaCraft.wait(5)
--- ```
function LuaCraft.wait(seconds) end

--- Reads the content of a file from the LuaCraft data folder.
--- @param filePath string The relative path to the file.
--- @return string|nil @The content of the file, or `nil` if the file could not be read.
--- ```lua
--- local content = LuaCraft.readFile("example.txt")
--- print(content)
--- ```
function LuaCraft.readFile(filePath) end

--- Writes content to a file in the LuaCraft data folder.
--- @param filePath string The relative path to the file.
--- @param content string The content to write to the file.
--- @return boolean @`true` if the write was successful, `false` otherwise.
--- ```lua
--- local success = LuaCraft.writeFile("example.txt", "Hello, LuaCraft!")
--- print(success)
--- ```
function LuaCraft.writeFile(filePath, content) end

--- Sets the system clipboard content.
--- @param content string The content to copy to the clipboard.
--- @return boolean @`true` if the clipboard was set successfully.
--- ```lua
--- LuaCraft.setClipboard("Copied text!")
--- ```
function LuaCraft.setClipboard(content) end

--- Executes a Minecraft command as the specified player or as the console if no player is provided.
--- @param player LuaCraftPlayer? The player to execute the command as (optional). If not provided, the command is executed as the console.
--- @param command string The command to execute.
--- @return boolean @true if the command was successfully executed, false otherwise.
--- ```lua
--- -- Execute a command as a specific player
--- local success = LuaCraft.executeCommandAs(player, "say Hello, World!")
--- print(success)
---
--- -- Execute a command as the console
--- local success = LuaCraft.executeCommandAs(nil, "say Hello from the console!")
--- print(success)
--- ```
function LuaCraft.executeCommandAs(player, command) end

--- Executes a Minecraft command from the console.
--- @param command string The command to execute.
--- @return boolean @true if the command was successfully executed, false otherwise.
--- ```lua
--- -- Execute a command as the console
--- local success = LuaCraft.executeCommand("say Hello from the console!")
--- print(success)
--- ```
function LuaCraft.executeCommand(command) end

--- Converts ampersand (&) codes to Minecraft section (§) symbol codes for formatting text.
--- This is useful for color and formatting codes in Minecraft chat, signs, and other text areas.
--- @param text string The input string with ampersand (&) formatting codes.
--- @return string @The string with converted section (§) symbol codes.
--- ```lua
--- local formattedText = LuaCraft.convertAmpersandToSection("&aHello, &bWorld!")
--- print(formattedText)  -- Outputs: §aHello, §bWorld!
--- ```
function LuaCraft.convertAmpersandToSection(text) end

--- Broadcasts a message to both the server console and all online players.
--- @param message string The message to broadcast to the console and players.
--- @return nil
--- ```lua
--- -- Broadcast a message to everyone and the console
--- LuaCraft.broadcastMessage("Hello, everyone! This message is from the server!")
--- ```
function LuaCraft.broadcastMessage(message) end
