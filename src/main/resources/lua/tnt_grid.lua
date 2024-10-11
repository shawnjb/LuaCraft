local player = LuaCraft.getLocalPlayer()
local pos = player:getPosition()

LuaCraft.print('&#4f4f4f[&#ff9900' .. script.name .. '&#4f4f4f]&#ffffff TNT strike inbound...')

local gridSize = 1
local spacing = 5

for x = -gridSize, gridSize do
    for y = -gridSize, gridSize do
        for z = -gridSize, gridSize do
            local offset = Vec3.new(x * spacing, y * spacing, z * spacing)
            local tntPos = pos:add(offset)
            LuaCraft.summonEntityAt('TNT', Vec3.new(tntPos.x, tntPos.y, tntPos.z))
        end
    end
end

LuaCraft.print("TNT grid spawned around your position!")
