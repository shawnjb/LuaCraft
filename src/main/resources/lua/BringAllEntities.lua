local luaCraftPlayer = LuaCraft.getLocalPlayer()
assert(luaCraftPlayer, 'You are not running in player mode.')
local localPosition = luaCraftPlayer.getPosition()

for _, luaCraftEntity in pairs(LuaCraft.getEntities()) do
	luaCraftEntity.setPosition(localPosition)
end