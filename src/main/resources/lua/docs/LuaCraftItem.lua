--- @meta
--- @class LuaCraftItem
--- Represents an item in the game, allowing Lua scripts to interact with its metadata and modify its attributes.
local LuaCraftItem = {
	--- Retrieves the metadata of the item, including name, lore, enchantments, and type.
	--- @return table @A table containing metadata fields like `name`, `lore`, `enchantments`, `type`, and `amount`.
	--- ```lua
	--- local metadata = item.getItemMetadata()
	--- print(metadata.name, metadata.lore[1], metadata.enchantments["sharpness"], metadata.type)
	--- ```
	getItemMetadata = function() end,

	--- Sets the display name of the item.
	--- @param displayName string The custom name to set for the item.
	--- ```lua
	--- item.setDisplayName("Legendary Sword")
	--- ```
	setDisplayName = function(displayName) end,

	--- Sets the lore (description) of the item.
	--- @param lore table A table of strings representing the item's lore.
	--- ```lua
	--- item.setLore({ "Forged in the fires of the nether", "Unbreakable" })
	--- ```
	setLore = function(lore) end,

	--- Adds a safe enchantment to the item, ensuring valid enchantments and levels.
	--- @param enchantmentName string The name of the enchantment (e.g., "sharpness").
	--- @param level number The level of the enchantment (must be within valid bounds).
	--- ```lua
	--- item.addSafeEnchantment("sharpness", 5)
	--- ```
	addSafeEnchantment = function(enchantmentName, level) end,

	--- Removes an enchantment from the item if it exists.
	--- @param enchantmentName string The name of the enchantment to remove (e.g., "sharpness").
	--- ```lua
	--- item.removeSafeEnchantment("sharpness")
	--- ```
	removeSafeEnchantment = function(enchantmentName) end,

	--- Sets custom model data for the item.
	--- @param data number The custom model data value to set.
	--- ```lua
	--- item.setCustomModelData(123456)
	--- ```
	setCustomModelData = function(data) end,

	--- Sets a persistent string-based value on the item using a key.
	--- @param key string The key to store the data under.
	--- @param value string The string value to store.
	--- ```lua
	--- item.setPersistentData("owner", "Player123")
	--- ```
	setPersistentData = function(key, value) end,

	--- Retrieves a persistent string-based value stored on the item.
	--- @param key string The key to retrieve the data from.
	--- @return string|nil @The stored value, or nil if not set.
	--- ```lua
	--- local owner = item.getPersistentData("owner")
	--- if owner then
	---     print("This item belongs to:", owner)
	--- end
	--- ```
	getPersistentData = function(key) end,

	--- Sets whether the item is unbreakable.
	--- @param unbreakable boolean True to make the item unbreakable, false to make it breakable.
	--- ```lua
	--- item.setUnbreakable(true)
	--- ```
	setUnbreakable = function(unbreakable) end,
}