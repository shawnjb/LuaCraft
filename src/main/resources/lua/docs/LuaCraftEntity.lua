--- @meta
--- @class LuaCraftEntity
--- Represents an entity in the Minecraft world. This class provides methods to interact with and manipulate the entity's attributes.
local LuaCraftEntity = {
	--- Retrieves the entity's position as a Lua table with `x`, `y`, and `z` coordinates.
	--- @return table @A table with `x`, `y`, and `z` fields representing the entity's current position.
	--- ```lua
	--- local position = entity.getPosition()
	--- print(position.x, position.y, position.z)
	--- ```
	getPosition = function() end,

	--- Teleports the entity to the specified coordinates.
	--- @param position Vec3
	--- ```lua
	--- entity.setPosition(Vec3.new(100, 64, 100))
	--- ```
	setPosition = function(position) end,

	--- Sets a custom name for the entity.
	--- @param name string The custom name to set.
	--- ```lua
	--- entity.setCustomName("Boss Monster")
	--- ```
	setCustomName = function(name) end,

	--- @type string @The type of the entity *(e.g., "CREEPER", "ZOMBIE")*.
	--- ```lua
	--- print("Entity type:", entity.type)
	--- ```
	type = nil,

	--- Retrieves the entity's UUID as a string.
	--- @return string @The UUID of the entity.
	--- ```lua
	--- local uuid = entity.getUUID()
	--- print("Entity UUID:", uuid)
	--- ```
	getUUID = function() end,

	--- Sets the health of the entity if it is a LivingEntity.
	--- @param health number The health value to set.
	--- ```lua
	--- entity.setHealth(20)
	--- ```
	setHealth = function(health) end,

	--- Sets whether the entity is a baby (for ageable entities).
	--- @param isBaby boolean True to set the entity as a baby, false to set it as an adult.
	--- ```lua
	--- entity.setBaby(true) -- Set entity to baby
	--- entity.setBaby(false) -- Set entity to adult
	--- ```
	setBaby = function(isBaby) end,

	--- Sets whether the entity (if it is a Creeper) is charged.
	--- @param charged boolean True to charge the Creeper, false to discharge it.
	--- ```lua
	--- entity.setCharged(true) -- Charge the Creeper
	--- entity.setCharged(false) -- Remove the charge from the Creeper
	--- ```
	setCharged = function(charged) end,

	--- Retrieves the custom name of the entity, or nil if not set.
	--- @return string|nil @The custom name of the entity, or nil if not set.
	--- ```lua
	--- local customName = entity.getCustomName()
	--- if customName then
	---     print("Entity custom name:", customName)
	--- else
	---     print("Entity has no custom name.")
	--- end
	--- ```
	getCustomName = function() end,

	--- Enables or disables the AI for the entity if it is a LivingEntity.
	--- @param enabled boolean True to enable AI, false to disable it.
	--- ```lua
	--- entity.setAI(false) -- Disable AI
	--- entity.setAI(true) -- Enable AI
	--- ```
	setAI = function(enabled) end,
}