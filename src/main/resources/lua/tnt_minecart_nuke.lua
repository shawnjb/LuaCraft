local player = LuaCraft.getLocalPlayer()
local playerPos = player:getPosition()

for _ = 1, 20 do
	LuaCraft.summonEntityAt("TNT_MINECART", playerPos)
end