--- @meta
--- @class json
--- Provides functions to encode Lua tables into JSON strings and decode JSON strings into Lua tables.
json = {}

--- Encodes a Lua table or value into a JSON string.
--- @param table table The Lua table or value to encode into JSON format.
--- @return string @A JSON string representing the input table.
--- ```lua
--- local data = {
---     name = "Steve",
---     score = 100,
---     alive = true,
---     items = {"sword", "shield"}
--- }
--- local jsonString = json.encode(data)
--- print(jsonString)  -- Outputs: {"name":"Steve","score":100,"alive":true,"items":["sword","shield"]}
--- ```
function json.encode(table) end

--- Decodes a JSON string into a Lua table.
--- @param jsonString string The JSON string to decode into a Lua table.
--- @return table @A Lua table representing the data in the JSON string.
--- ```lua
--- local jsonString = '{"name":"Steve","score":100,"alive":true,"items":["sword","shield"]}'
--- local data = json.decode(jsonString)
--- print(data.name)  -- Outputs: Steve
--- print(data.score) -- Outputs: 100
--- ```
function json.decode(jsonString) end
