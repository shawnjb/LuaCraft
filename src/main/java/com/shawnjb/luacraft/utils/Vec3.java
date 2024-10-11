package com.shawnjb.luacraft.utils;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Vec3 {
    public final double x, y, z;

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public LuaValue toLua() {
        LuaValue table = LuaValue.tableOf();
        table.set("x", LuaValue.valueOf(x));
        table.set("y", LuaValue.valueOf(y));
        table.set("z", LuaValue.valueOf(z));

        table.set("add", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue vec3Table) {
                Vec3 other = fromLua(vec3Table);
                LuaValue otherLuaValue = other.toLua();
                LuaValue result = self.add(otherLuaValue);
                return result;
            }
        });

        table.set("subtract", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue vec3Table) {
                Vec3 other = fromLua(vec3Table);
                Vec3 result = subtract(other);
                return result.toLua();
            }
        });

        table.set("multiply", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue scalar) {
                Vec3 result = multiply(scalar.todouble());
                return result.toLua();
            }
        });

        table.set("magnitude", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue ignore) {
                return LuaValue.valueOf(magnitude());
            }
        });

        return table;
    }

    public static Vec3 fromLua(LuaValue table) {
        double x = table.get("x").todouble();
        double y = table.get("y").todouble();
        double z = table.get("z").todouble();
        return new Vec3(x, y, z);
    }

    public static void registerVec3(LuaValue globals) {
        LuaValue vec3Table = LuaValue.tableOf();

        vec3Table.set("new", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue args) {
                LuaValue x = args.get(1);
                LuaValue y = args.get(2);
                LuaValue z = args.get(3);

                return new Vec3(x.todouble(), y.todouble(), z.todouble()).toLua();
            }
        });

        globals.set("Vec3", vec3Table);
    }

    public Vec3 add(Vec3 other) {
        return new Vec3(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public Vec3 subtract(Vec3 other) {
        return new Vec3(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public Vec3 multiply(double scalar) {
        return new Vec3(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y + z * z);
    }
}
