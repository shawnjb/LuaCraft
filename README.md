# LuaCraft

Run Lua scripts on your Minecraft server. This is very experimental. You'll have to build it yourself, but I'll provide a binary in the releases.

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