local playerPos = LuaCraft.getLocalPlayer().getPosition()
local allEntities = LuaCraft.getAllEntities()

for i, entity in ipairs(allEntities) do
    if entity.getType == 'CREEPER' then
        entity.setPosition(playerPos.x, playerPos.y, playerPos.z)
        LuaCraft.modifyEntityData(entity.getUUID(), {
            charged = math.random(1, 2) == 1
        })
    end
end