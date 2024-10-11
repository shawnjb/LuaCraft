-- Spawn a grid of TNT around the player's position using Vec3

local player = LuaCraft.getLocalPlayer()
local pos = player:getPosition() -- Assuming pos is already a table with x, y, z fields
local playerPos = Vec3.new(pos.x, pos.y, pos.z)

local function tableToString(tbl)
	local result = "{"
	for k, v in pairs(tbl) do
		result = result .. k .. "=" .. tostring(v) .. ", "
	end
	return result .. "}"
end

LuaCraft.print('&#4f4f4f[&#ff9900' .. script.name .. '&#4f4f4f]&#ffffff Current position: ' .. tableToString(playerPos))

local gridSize = 1
local spacing = 5

for x = -gridSize, gridSize do
	for y = -gridSize, gridSize do
		for z = -gridSize, gridSize do
			local offset = Vec3.new(x * spacing, y * spacing, z * spacing)
			local tntPos = playerPos:add(offset)
			-- Summon TNT at the calculated position
			LuaCraft.summonAtPosition("TNT", tntPos.x, tntPos.y, tntPos.z)
		end
	end
end

LuaCraft.print("TNT grid spawned around your position!")
