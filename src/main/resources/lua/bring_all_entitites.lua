local playerPos = LuaCraft.getLocalPlayer().getPosition()
local allEntities = LuaCraft.getEntities()

for _, entity in ipairs(allEntities) do
	entity.setPosition(playerPos)
end
