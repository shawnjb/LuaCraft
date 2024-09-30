--- @meta

--- @class LuaCraft
luacraft = {}

--- Prints messages to the last command sender's chat.
--- @vararg ... The messages to be printed.
--- ```lua
--- luacraft.print("Hello, World!")
--- luacraft.print("Your score is", score)
--- ```
function luacraft.print(...) end

--- Colorizes a given text using Minecraft's color codes.
--- @param color string The color code (e.g., "a" for green).
--- @param text string The text to be colorized.
--- @return string @The colorized text.
--- ```lua
--- local message = luacraft.colorize("a", "This is green text.")
--- ```
function luacraft.colorize(color, text) end

--- Pauses the execution for a specified number of seconds.
--- @param seconds number The duration to wait in seconds.
--- ```lua
--- luacraft.wait(5) -- Waits for 5 seconds.
--- ```
function luacraft.wait(seconds) end

--- Executes a command as the last command sender.
--- Only players can run commands.
--- @param command string The command to be executed.
--- ```lua
--- luacraft.runcommand("/give @p diamond 1")
--- ```
--- @deprecated
function luacraft.runcommand(command) end

--- Executes a command as the last command sender.
--- Only players can run commands.
--- @param command string The command to be executed.
--- ```lua
--- luacraft.runCommand("/give @p diamond 1")
--- ```
function luacraft.runCommand(command) end

--- Applies a new position to the executing player.
--- @param x integer
--- @param y integer
--- @param z integer
--- ```lua
--- luacraft.setPlayerPosition(0, 120, 0)
--- ```
function luacraft.setPlayerPosition(x, y, z) end

--- Retrieves the position of the executing player.
--- @return { x: integer, y: integer, z: integer }
--- ```lua
--- local vec3 = luacraft.getPlayerPosition()
--- print(vec3.x, vec3.y, vec3.z)
--- ```
function luacraft.getPlayerPosition() end