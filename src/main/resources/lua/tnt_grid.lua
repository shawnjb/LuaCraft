-- Spawn a grid of TNT around the player's position
local player = LuaCraft.getLocalPlayer()
local playerPos = player:getPosition()

local gridSize = 1
local spacing = 5

for x = -gridSize, gridSize do
    for y = -gridSize, gridSize do
        for z = -gridSize, gridSize do
            local tntX = playerPos.x + (x * spacing)
            local tntY = playerPos.y + (y * spacing)
            local tntZ = playerPos.z + (z * spacing)
            LuaCraft.summonAtPosition("TNT", tntX, tntY, tntZ)
        end
    end
end

LuaCraft.print("TNT grid spawned around your position!")
