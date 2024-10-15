local me = LuaCraft.getLocalPlayer()

if me then
	local netheriteSword = LuaCraft.newItem('NETHERITE_SWORD', {
		player = me,
		name =
		'&#FF0000U&#FF0900n&#FF1100f&#FF1A00a&#FF2200i&#FF2B00r&#FF3300l&#FF3C00y &#FF4D00E&#FF5500n&#FF5E00c&#FF6600h&#FF6F00a&#FF7700n&#FF8000t&#FF8800e&#FF9100d &#FFA200S&#FFAA00w&#FFB300o&#FFBB00r&#FFC400d',
		enchantments = {
			{ 'SHARPNESS', 255 },
			{ 'KNOCKBACK', 255 }
		}
	})

	netheriteSword.setUnbreakable(true)
	LuaCraft.broadcastMessage(LuaCraft.convertAmpersandToSection('&ahey guys! ' .. me.name .. ' has a very powerful weapon so do not mess with them.'))
end
