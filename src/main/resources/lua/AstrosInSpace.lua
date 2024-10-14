local function getPeopleInSpace()
    local response = LuaCraft.httpGet('http://api.open-notify.org/astros.json')
    if response ~= nil then
        local data = json.decode(response)
        if data and data.number then
            local message = 'There are currently ' .. data.number .. ' people in space.'
            LuaCraft.broadcastMessage(message)
        else
            LuaCraft.broadcastMessage('Failed to retrieve the number of people in space.')
        end
    else
        LuaCraft.broadcastMessage('Failed to fetch data from the space API.')
    end
end

getPeopleInSpace()
