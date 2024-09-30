# LuaCraft

Run Lua scripts on your Minecraft server. This is very experimental. You'll have to build it yourself, but I'll provide a binary in the releases.

---

## Usage

Place the server jar in your plugins folder. When the server runs it will create two directories in your server's root folder called `lua` and `lua-notlive`.

Scripts in `lua-notlive` can be ran through `/runscript [name]` and scripts in `lua` will run on server start.

You can use commands from other plugins in your Lua scripts. I created an abusive script called `hell_on_earth.lua` that uses WorldEdit to turn the surrounding 100 block area into just complete hell.

```lua
runcommand('/replacenear 50 dirt,grass_block,stone netherrack')
runcommand('/replacenear 50 air fire')
runcommand('/replacenear 50 andesite,diorite magma_block')
runcommand('/replacenear 50 water lava')
```

You can also summon a ton of mobs with this. I used Ghasts and Blaze for complete chaos.

```lua
for i = 1, 2 do
    for i = 1, 10, 1 do
        summon('GHAST')
        for i = 1, 2 do
            summon('BLAZE')
        end
    end
end
```

I've basically put most of my scripts in the "not live" folder because I'm more of a tamper with everything kind of user and don't have a server for any serious purpose whatsoever. This project was purely experimental and I was trying to do something fun with it.

You can also dump the environment. I did that with a colorize function which means you can also use colors while printing. The output goes to the player or the console, depending on where you ran it.

```lua
local function colorize(text, color)
    return "&" .. color .. text
end

local function printTable(t)
    local maxKeyLength = 0
    for key, _ in pairs(t) do
        maxKeyLength = math.max(maxKeyLength, #tostring(key))
    end

    for key, value in pairs(t) do
        local formattedKey = colorize(tostring(key), "a")
        local formattedValue = colorize(tostring(value), "e")
        print(string.format("%-" .. maxKeyLength .. "s : %s", formattedKey, formattedValue))
    end
end

print(colorize("=== Global Environment ===", "6"))
printTable(_G)
print("\n")
```
