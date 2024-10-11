package com.shawnjb.luacraft.utils;

import org.luaj.vm2.Globals;
import org.luaj.vm2.Prototype;
import java.io.IOException;
import java.io.InputStream;

public class Undumper implements Globals.Undumper {
    private final Globals globals;

    public Undumper(Globals globals) {
        this.globals = globals;
    }

    @Override
    public Prototype undump(InputStream inputStream, String name) throws IOException {
        if (globals.compiler == null)
            throw new IOException("No compiler available in Globals.");
        return globals.compiler.compile(inputStream, name);
    }
}
