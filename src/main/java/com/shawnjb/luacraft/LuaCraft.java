package com.shawnjb.luacraft;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import com.shawnjb.luacraft.utils.Undumper;
import com.shawnjb.luacraft.utils.Vec3Registrar;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import java.io.*;

public class LuaCraft extends JavaPlugin {
	private Globals globals;
	private CommandSender lastSender;

	public CommandSender getLastSender() {
		return lastSender;
	}

	@Override
	public void onEnable() {
		File luaCraftFolder = getDataFolder();
		if (!luaCraftFolder.exists()) {
			luaCraftFolder.mkdirs();
			getLogger().info("Created LuaCraft directory at: " + luaCraftFolder.getAbsolutePath());
		}

		// Initialize Lua globals
		globals = JsePlatform.standardGlobals();
		globals.undumper = new Undumper(globals);

		// Initialize LuaCraftLibrary and register autorun scripts
		LuaCraftLibrary luaCraftLibrary = new LuaCraftLibrary(this);
		registerAutorunScripts(new File(getServer().getWorldContainer(), "lua"));

		// Register LuaCraft functions and Vec3 utility
		luaCraftLibrary.registerLuaFunctions(globals);
		Vec3Registrar.registerVec3(globals);

		// Register command handlers via LifecycleEventManager
		LifecycleEventManager<Plugin> manager = this.getLifecycleManager();
		manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
			var commands = event.registrar();

			// Command: loadscript
			commands.register(
					Commands.literal("loadscript")
							.then(Commands.argument("filename", StringArgumentType.word())
									.suggests((context, builder) -> {
										File luaDir = new File(getServer().getWorldContainer(), "lua");
										if (luaDir.exists() && luaDir.isDirectory()) {
											for (File file : luaDir.listFiles((dir, name) -> name.endsWith(".lua"))) {
												String scriptName = file.getName().replace(".lua", "");
												builder.suggest(scriptName);
											}
										}
										return builder.buildFuture();
									})
									.executes(this::loadScript))
							.build());

			// Command: listscripts
			commands.register(
					Commands.literal("listscripts")
							.executes(this::listScripts)
							.build(),
					"List all available Lua scripts");

			// Command: loadstring
			commands.register(
						Commands.literal("loadstring")
							.then(Commands.argument("code", StringArgumentType.greedyString())
								.executes(this::loadString))
							.build()
					);
		});
	}

	private int loadScript(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
		CommandSourceStack sourceStack = context.getSource();
		CommandSender sender = sourceStack.getSender();
		lastSender = sender;
		String scriptName = StringArgumentType.getString(context, "filename");

		Component prefix = Component.text("[LuaCraft] ").color(NamedTextColor.LIGHT_PURPLE);
		File scriptFile = new File(getServer().getWorldContainer(), "lua/" + scriptName + ".lua");

		if (scriptFile.exists()) {
			try {
				globals.set("script", LuaValue.tableOf(new LuaValue[] {
						LuaValue.valueOf("name"), LuaValue.valueOf(scriptFile.getName())
				}));

				LuaValue chunk = globals.loadfile(scriptFile.getAbsolutePath());
				chunk.call();
				lastSender.sendMessage(
						prefix.append(Component.text("Script loaded successfully: ").color(NamedTextColor.GREEN)
								.append(Component.text(scriptName).color(NamedTextColor.AQUA)
										.decorate(TextDecoration.ITALIC))));
			} catch (LuaError e) {
				lastSender.sendMessage(prefix
						.append(Component.text("Error executing script: " + e.getMessage()).color(NamedTextColor.RED)));
			}
		} else {
			lastSender.sendMessage(prefix.append(Component.text("Script not found: ").color(NamedTextColor.RED)
					.append(Component.text(scriptName).color(NamedTextColor.RED).decorate(TextDecoration.ITALIC))));
		}
		return 1;
	}

	private int listScripts(CommandContext<CommandSourceStack> context) {
		CommandSourceStack sourceStack = context.getSource();
		CommandSender sender = sourceStack.getSender();

		Component prefix = Component.text("[LuaCraft] ").color(NamedTextColor.LIGHT_PURPLE);

		File folder = new File(getServer().getWorldContainer(), "lua");
		if (!folder.exists() || !folder.isDirectory()) {
			sender.sendMessage(prefix.append(Component.text("Lua scripts directory does not exist.").color(NamedTextColor.RED)));
			return 1;
		}

		String[] luaFiles = folder.list((dir, name) -> name.endsWith(".lua"));
		if (luaFiles == null || luaFiles.length == 0) {
			sender.sendMessage(prefix.append(Component.text("No Lua scripts found.").color(NamedTextColor.RED)));
			return 1;
		}

		Component scriptsList = Component.text("Available scripts: ").color(NamedTextColor.GREEN);
		for (int i = 0; i < luaFiles.length; i++) {
			String scriptNameStr = luaFiles[i].substring(0, luaFiles[i].lastIndexOf('.'));
			Component scriptName = Component.text(scriptNameStr).color(NamedTextColor.AQUA).decorate(TextDecoration.ITALIC);
			scriptsList = scriptsList.append(scriptName);
			if (i < luaFiles.length - 1) {
				scriptsList = scriptsList.append(Component.text(", ").color(NamedTextColor.GREEN));
			}
		}

		sender.sendMessage(prefix.append(scriptsList));
		return 1;
	}

	private int loadString(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
		CommandSourceStack sourceStack = context.getSource();
		CommandSender sender = sourceStack.getSender();
		lastSender = sender;
		String code = StringArgumentType.getString(context, "code");
	
		Component prefix = Component.text("[LuaCraft] ").color(NamedTextColor.LIGHT_PURPLE);
	
		try {
			LuaValue chunk = globals.load(code);
			chunk.call();
			lastSender.sendMessage(
					prefix.append(Component.text("Code executed successfully").color(NamedTextColor.GREEN)));
		} catch (LuaError e) {
			lastSender.sendMessage(
					prefix.append(Component.text("Error executing code: " + e.getMessage()).color(NamedTextColor.RED)));
		}
		
		return 1;
	}	

	private void registerAutorunScripts(File luaDir) {
		copyResourceDirectoryToServer("lua", luaDir);
		File autorunDir = new File(luaDir, "autorun");
		if (autorunDir.exists()) {
			for (File file : autorunDir.listFiles((dir, name) -> name.endsWith(".lua"))) {
				try {
					globals.set("script", LuaValue.tableOf(new LuaValue[] {LuaValue.valueOf("name"), LuaValue.valueOf(file.getName())}));
					LuaValue chunk = globals.loadfile(file.getAbsolutePath());
					chunk.call();
				} catch (LuaError e) {
					getLogger().severe("Error executing " + file.getName() + ": " + e.getMessage());
				}
			}
		} else {
			getLogger().info("No lua/autorun directory found, skipping autorun scripts.");
		}
	}

	private void copyResourceDirectoryToServer(String resourcePath, File targetDir) {
		if (targetDir.exists()) {
			getLogger().info("Lua directory already exists, skipping script regeneration.");
			return;
		}
		targetDir.mkdirs();
		try {
			File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
			if (jarFile.isFile()) {
				try (java.util.jar.JarFile jar = new java.util.jar.JarFile(jarFile)) {
					java.util.Enumeration<java.util.jar.JarEntry> entries = jar.entries();
					while (entries.hasMoreElements()) {
						java.util.jar.JarEntry entry = entries.nextElement();
						String entryName = entry.getName();

						if (entryName.startsWith(resourcePath) && !entry.isDirectory() && entryName.endsWith(".lua")) {
							String fileName = entryName.substring(resourcePath.length() + 1);
							File targetFile = new File(targetDir, fileName);

							targetFile.getParentFile().mkdirs();

							try (InputStream in = getResource(entryName);
									OutputStream out = new FileOutputStream(targetFile)) {
								byte[] buffer = new byte[1024];
								int length;
								while ((length = in.read(buffer)) > 0) {
									out.write(buffer, 0, length);
								}
								getLogger().info("Copied " + entryName + " to " + targetFile.getAbsolutePath());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			getLogger().severe("Failed to copy resource directory: " + e.getMessage());
		}
	}
}
