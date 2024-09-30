package org.dankfmemes.luacraft.utils;

import org.luaj.vm2.Varargs;

public class Vec3 {
    private final double x;
    private final double y;
    private final double z;

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vec3 fromArgs(Varargs args) {
        double x = 0;
        double y = 0;
        double z = 0;

        if (args.narg() >= 1) {
            x = args.checkdouble(1);
        }
        if (args.narg() >= 2) {
            y = args.checkdouble(2);
        }
        if (args.narg() >= 3) {
            z = args.checkdouble(3);
        }

        return new Vec3(x, y, z);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
