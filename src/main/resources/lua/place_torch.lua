local surfaceType = LuaCraft.getPlayerBlockType()
local invalidSurfaces = { ['AIR'] = true, ['BARRIER'] = true }

local function isInvalidSurface()
    return invalidSurfaces[surfaceType] == true
end

if isInvalidSurface() then
    LuaCraft.print('&#4f4f4f[&#ff9900' .. script.name .. '&#4f4f4f]&#ffffff You cannot place a torch here.')
else
    LuaCraft.print('&#4f4f4f[&#ff9900' .. script.name .. '&#4f4f4f]&#ffffff A torch has been placed.')
    LuaCraft.runCommand('setblock ~ ~ ~ minecraft:torch')
end