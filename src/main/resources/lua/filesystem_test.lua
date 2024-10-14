local filePath = "data/playerData.txt"

local function savePlayerData(player)
    local playerName = player.getName
    local playerPosition = player.getPosition()
	local content = string.format("Player: %s\nPosition: X: %.2f, Y: %.2f, Z: %.2f\n", playerName, playerPosition.x, playerPosition.y, playerPosition.z)
    local success = writeFile(filePath, content)
    if success then
        player.sendMessage("Your data has been saved!")
    else
        player.sendMessage("Failed to save data.")
    end
end

local function loadPlayerData(player)
    local content = readFile(filePath)
    if content then
        player.sendMessage("Loaded data: \n" .. content)
    else
        player.sendMessage("No data found.")
    end
end

LuaCraftWorld.bindEvent("PlayerJoin", function(player)
    player.sendMessage("Welcome, " .. player.getName .. "!")
    loadPlayerData(player)
end)

LuaCraftWorld.bindEvent("PlayerQuit", function(player)
    savePlayerData(player)
end)