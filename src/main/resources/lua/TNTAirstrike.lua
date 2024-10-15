local me = LuaCraft.getLocalPlayer()

if me then
    local position = Vec3.new(0, 50, 0):add(me.getPosition())
    local grid_size = 3
    local spacing = 2
	
    for x = -grid_size, grid_size do
        for z = -grid_size, grid_size do
            local grid_offset = Vec3.new(x * spacing, 0, z * spacing)
            local spawn_position = Vec3.new(
                position.x + grid_offset.x,
                position.y + grid_offset.y,
                position.z + grid_offset.z
            )

            LuaCraft.newEntity('TNT', spawn_position)
        end
    end
	
    LuaCraft.broadcastMessage('TNT strike inbound!!!')
end
