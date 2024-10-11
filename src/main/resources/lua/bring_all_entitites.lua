local playerPos = LuaCraft.getLocalPlayer().getPosition()
local allEntities = LuaCraft.getAllEntities()

for i, entity in ipairs(allEntities) do
	entity.setPosition(playerPos.x, playerPos.y, playerPos.z)
end
