local data = { 
    name = "Alex", 
    health = 80, 
    items = { "pickaxe", "torch" } 
}

local jsonString = json.encode(data)
LuaCraft.broadcastMessage("Encoded JSON: " .. jsonString)

local decodedData = json.decode(jsonString)
LuaCraft.broadcastMessage("Decoded Name: " .. decodedData.name)
LuaCraft.broadcastMessage("Decoded Health: " .. decodedData.health)

for i, item in ipairs(decodedData.items) do
    LuaCraft.broadcastMessage("Item " .. i .. ": " .. item)
end
