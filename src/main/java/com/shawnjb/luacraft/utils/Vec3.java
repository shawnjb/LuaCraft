package com.shawnjb.luacraft.utils;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;
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
                Vec3 vecSelf = fromLua(self);
                Vec3 otherVec = fromLua(vec3Table);
                Vec3 resultVec = vecSelf.add(otherVec);
                return resultVec.toLua();
            }
        });

        table.set("subtract", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue vec3Table) {
                return subtract(fromLua(vec3Table)).toLua();
            }
        });

        table.set("multiply", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue scalar) {
                return multiply(scalar.todouble()).toLua();
            }
        });

        table.set("dot", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue vec3Table) {
                return LuaValue.valueOf(dot(fromLua(vec3Table)));
            }
        });

        table.set("cross", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue vec3Table) {
                return cross(fromLua(vec3Table)).toLua();
            }
        });

        table.set("magnitude", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue ignore) {
                return LuaValue.valueOf(magnitude());
            }
        });

        table.set("normalize", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue ignore) {
                return normalize().toLua();
            }
        });

        table.set("distance", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue vec3Table) {
                return LuaValue.valueOf(distance(fromLua(vec3Table)));
            }
        });

        table.set("lerp", new ThreeArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue vec3Table, LuaValue t) {
                return lerp(fromLua(vec3Table), t.todouble()).toLua();
            }
        });

        return table;
    }

    public static Vec3 fromLua(LuaValue table) {
        return new Vec3(table.get("x").todouble(), table.get("y").todouble(), table.get("z").todouble());
    }

    public static void registerVec3(LuaValue globals) {
        LuaValue vec3Table = LuaValue.tableOf();
        vec3Table.set("new", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue args) {
                return new Vec3(args.get(1).todouble(), args.get(2).todouble(), args.get(3).todouble()).toLua();
            }
        });
        globals.set("Vec3", vec3Table);
    }

    // Vector operations

    public Vec3 add(Vec3 other) {
        return new Vec3(x + other.x, y + other.y, z + other.z);
    }

    public Vec3 subtract(Vec3 other) {
        return new Vec3(x - other.x, y - other.y, z - other.z);
    }

    public Vec3 multiply(double scalar) {
        return new Vec3(x * scalar, y * scalar, z * scalar);
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double dot(Vec3 other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public Vec3 cross(Vec3 other) {
        return new Vec3(
                y * other.z - z * other.y,
                z * other.x - x * other.z,
                x * other.y - y * other.x);
    }

    public Vec3 normalize() {
        double mag = magnitude();
        return mag > 0 ? new Vec3(x / mag, y / mag, z / mag) : new Vec3(0, 0, 0);
    }

    public double distance(Vec3 other) {
        double dx = x - other.x;
        double dy = y - other.y;
        double dz = z - other.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public Vec3 lerp(Vec3 other, double t) {
        return new Vec3(
                x + t * (other.x - x),
                y + t * (other.y - y),
                z + t * (other.z - z));
    }
}
