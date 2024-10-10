local playerPos = LuaCraft.getLocalPlayer().getPosition()
local allEntities = LuaCraft.getAllEntities()

for i, entity in ipairs(allEntities) do
    if entity.getType() == 'ZOMBIE' then
        entity.setPosition(playerPos.x, playerPos.y, playerPos.z)
        LuaCraft.modifyEntityData(entity.getUUID(), {
            isBaby = math.random(1, 2) == 1
        })
    end
end
