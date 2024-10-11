package com.shawnjb.luacraft.utils;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;

/**
 * The Vec3Registrar class handles the registration of the Vec3 class in Lua.
 */
public class Vec3Registrar {

	/**
	 * Registers the Vec3 class to the Lua environment.
	 * This allows Lua scripts to access Vec3 functionality.
	 *
	 * @param globals The global Lua environment where Vec3 will be registered.
	 */
	public static void registerVec3(Globals globals) {
		LuaValue vec3Table = LuaValue.tableOf();
		vec3Table.set("new", new VarArgFunction() {
			@Override
			public LuaValue call(LuaValue self, LuaValue x, LuaValue y, LuaValue z) {
				return new Vec3(x.todouble(), y.todouble(), z.todouble()).toLua();
			}
		});
		globals.set("Vec3", vec3Table);
	}
}
