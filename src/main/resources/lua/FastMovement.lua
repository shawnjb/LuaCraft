local me = LuaCraft.getLocalPlayer()

if me then
	LuaCraft.setPlayerWalkSpeed(me, 10)
	LuaCraft.setPlayerFlySpeed(me, 10)
	LuaCraft.broadcastMessage(LuaCraft.convertAmpersandToSection('&ahey guys! ' .. me.name .. ' is super fast, so don\'t try outrunning them.'))
end