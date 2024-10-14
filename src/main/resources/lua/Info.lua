local function broadcastLuaInfo()
    local packagePath = package.path
    local luaVersion = _VERSION
    local osInfo = "Unknown OS"
    if type(package.config) == 'string' then
        if package.config:sub(1, 1) == "\\" then
            osInfo = "Windows"
        else
            osInfo = "Unix-like"
        end
    end
    LuaCraft.broadcastMessage("Current package.path: " .. packagePath)
    LuaCraft.broadcastMessage("Lua version: " .. luaVersion)
    LuaCraft.broadcastMessage("Operating System: " .. osInfo)
end

broadcastLuaInfo()
