local luaCraftPlayer = LuaCraft.getLocalPlayer()
assert(luaCraftPlayer, 'You are not running in player mode.')
local localPosition = luaCraftPlayer.getPosition()

for _, luaCraftEntity in pairs(LuaCraft.getEntities()) do
	luaCraftEntity.setPosition(localPosition)
end

LuaCraft.executeCommand('kill @e[type=!player]') for i = 1, 100, 1 do LuaCraft.executeCommandAs(LuaCraft.getLocalPlayer(), 'summon tnt_minecart ~ ~ ~') end