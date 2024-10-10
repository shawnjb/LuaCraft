-- Spawn 20 TNT Minecarts at the player's position
local player = LuaCraft.getLocalPlayer()
local playerPos = player:getPosition()

for _ = 1, 20 do
    LuaCraft.summonAtPosition("TNT_MINECART", playerPos.x, playerPos.y, playerPos.z)
end

LuaCraft.print("20 TNT Minecarts spawned at your position!")
