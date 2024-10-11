# LuaCraft

LuaCraft allows players to run Lua scripts in Minecraft.

## Commands

- `/loadscript <filename>`: Loads and executes a specified Lua script from the `lua` directory.
- `/listscripts`: Lists all available Lua scripts in the `lua` directory.

### Lua API

LuaCraft provides a set of APIs to interact with the Minecraft world using Lua scripts. Here's a brief overview:

- **LuaCraftScript**: Represents the currently executing script, allowing access to the script's metadata (e.g., `name`).
  
- **Vec3**: A 3D vector class with common vector operations like `add`, `subtract`, `dot`, `cross`, `magnitude`, and more.

- **LuaCraftItem**: Represents items, allowing scripts to manipulate attributes such as metadata, enchantments, lore, and custom names.

- **LuaCraftPlayer**: Represents a player, providing methods to send messages, modify the player's position, give items, retrieve player data, and more.

- **LuaCraftEntity**: Provides methods to interact with entities in the world, including position, health, type, custom names, and AI management.

- **LuaCraft**: The global interface to interact with the game world, including players, items, entities, blocks, and server-side functions like broadcasting messages and summoning entities.

This API allows detailed customization and control of in-game actions via Lua scripting.

## License

This project is licensed under the Apache License 2.0.
