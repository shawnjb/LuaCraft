--- A table containing entities to spawn and their respective quantities.
local entitiesToSpawn = {
	{ 'TNT_MINECART', 15 },
	{ 'TNT',          20 },
}

--- Generates a random 6-digit hex color code.
--- @return string @A random hex color in the form of "#RRGGBB".
local function generateRandomHex()
	local hex = "#"
	for i = 1, 6 do
		hex = hex .. string.format("%x", math.random(0, 15))
	end
	return hex
end

--- Converts a hex color code to RGB values.
--- @param hex string The hex color code in the form "RRGGBB".
--- @return number, number, number @The red, green, and blue values (0-255).
local function hexToRGB(hex)
	return tonumber(hex:sub(1, 2), 16), tonumber(hex:sub(3, 4), 16), tonumber(hex:sub(5, 6), 16)
end

--- Converts RGB values to a hex color code.
--- @param r number The red value (0-255).
--- @param g number The green value (0-255).
--- @param b number The blue value (0-255).
--- @return string @The hex color in the form "RRGGBB".
local function rgbToHex(r, g, b)
	return string.format("%02x%02x%02x", r, g, b)
end

--- Linearly interpolates between two values.
--- @param a number The starting value.
--- @param b number The ending value.
--- @param t number A factor (0-1) indicating the percentage of interpolation between a and b.
--- @return number @The interpolated value.
local function lerp(a, b, t)
	return a + (b - a) * t
end

--- Creates a gradient text using two hex color codes.
--- @param text string The text to display.
--- @param startHex string The starting hex color in the form `"#RRGGBB"`.
--- @param endHex string The ending hex color in the form `"#RRGGBB"`.
--- @return string @The gradient text with each character colored in a gradient between the start and end hex colors.
local function createGradientText(text, startHex, endHex)
	startHex = startHex:gsub("#", "")
	endHex = endHex:gsub("#", "")
	local startR, startG, startB = hexToRGB(startHex)
	local endR, endG, endB = hexToRGB(endHex)
	local result = ""

	for i = 1, #text do
		local t = (i - 1) / (#text - 1)
		local r = math.floor(lerp(startR, endR, t))
		local g = math.floor(lerp(startG, endG, t))
		local b = math.floor(lerp(startB, endB, t))
		local hex = rgbToHex(r, g, b)
		result = result .. "&#" .. hex:upper() .. text:sub(i, i)
	end

	return result
end

--- The player's current position.
local pos = LuaCraft.getLocalPlayer().getPosition()
--- The X coordinate of the player's current position.
local x = pos.x
--- The Y coordinate of the player's current position.
local y = pos.y
--- The Z coordinate of the player's current position.
local z = pos.z

--- A dataset containing random elements to generate phrases.
local phraseData = {
	--- @type table A list of subjects.
	subjects = { "The cat", "A wizard", "An astronaut", "The dragon", "A programmer" },
	--- @type table A list of verbs.
	verbs = { "jumps over", "casts a spell on", "flies to", "breathes fire at", "codes a solution for" },
	--- @type table A list of objects.
	objects = { "the moon", "a giant", "a spaceship", "a castle", "an AI program" },
	--- @type table A list of adverbs.
	adverbs = { "quickly", "mysteriously", "gracefully", "suddenly", "silently" }
}

--- Selects a random element from a table.
--- @param tbl table The table to select an element from.
--- @return any @A random element from the table.
local function getRandomElement(tbl)
	return tbl[math.random(#tbl)]
end

--- Generates a random phrase by selecting random elements from phraseData.
--- @return string A randomly generated phrase.
local function generateRandomPhrase()
	local subject = getRandomElement(phraseData.subjects)
	local verb = getRandomElement(phraseData.verbs)
	local object = getRandomElement(phraseData.objects)
	local adverb = getRandomElement(phraseData.adverbs)
	local phrase = subject .. " " .. verb .. " " .. object .. " " .. adverb .. "."
	return phrase
end

--- Calculates the grid size and spacing based on the number of entities.
--- @param count number The number of entities to be spawned.
--- @return number, number @The calculated grid size and spacing between entities.
local function calculateGrid(count)
	local gridSize = math.ceil(math.sqrt(count))
	local spacing = math.max(2, 10 - math.floor(count / 10)) -- Spacing reduces as count increases
	return gridSize, spacing
end

--- Spawns entities in a grid layout centered at the player's position.
for _, entitySpawnInfo in pairs(entitiesToSpawn) do
	--- @type string The type of entity to spawn.
	local entityType = entitySpawnInfo[1]
	--- @type number The number of entities to spawn.
	local count = entitySpawnInfo[2]

	local gridSize, spacing = calculateGrid(count)
	local offsetX = (gridSize - 1) * spacing / 2
	local offsetZ = (gridSize - 1) * spacing / 2

	local gridCount = 0
	for i = 0, gridSize - 1 do
		for j = 0, gridSize - 1 do
			if gridCount < count then
				local spawnX = x + (i * spacing) - offsetX
				local spawnZ = z + (j * spacing) - offsetZ
				LuaCraft.summonEntityAt(entityType, Vec3.new(spawnX, y, spawnZ))
				LuaCraft.broadcast(createGradientText(generateRandomPhrase(), generateRandomHex(), generateRandomHex()))
				LuaCraft.wait(0.1)
				gridCount = gridCount + 1
			end
		end
	end
end
