package com.shawnjb.luacraft.lib;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;

/**
 * The SocketConnect class allows Lua scripts to create a TCP socket
 * client that connects to a remote server.
 */
public class SocketConnect extends VarArgFunction {
    private final LuaCraft plugin;

    public SocketConnect(LuaCraft plugin) {
        this.plugin = plugin;
    }

    /**
     * Connects to a remote server via TCP and returns a socket object.
     * Lua Usage: socketConnect(host, port)
     *
     * @param host the server's host address (e.g., "localhost")
     * @param port the server's port number
     * @return a Lua table representing the socket (can send and receive data)
     */
    @Override
    public Varargs invoke(Varargs args) {
        String host = args.checkjstring(1);
        int port = args.checkint(2);

        try {
            Socket socket = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            LuaValue socketTable = LuaValue.tableOf();

            socketTable.set("send", new VarArgFunction() {
                @Override
                public Varargs invoke(Varargs innerArgs) {
                    String data = innerArgs.checkjstring(1);
                    out.println(data);
                    return LuaValue.TRUE;
                }
            });

            socketTable.set("receive", new VarArgFunction() {
                @Override
                public Varargs invoke(Varargs innerArgs) {
                    try {
                        String response = in.readLine();
                        return LuaValue.valueOf(response);
                    } catch (Exception e) {
                        plugin.getLogger().severe("Error receiving data: " + e.getMessage());
                        return LuaValue.NIL;
                    }
                }
            });

            socketTable.set("close", new VarArgFunction() {
                @Override
                public Varargs invoke(Varargs innerArgs) {
                    try {
                        socket.close();
                        return LuaValue.TRUE;
                    } catch (Exception e) {
                        plugin.getLogger().severe("Error closing socket: " + e.getMessage());
                        return LuaValue.NIL;
                    }
                }
            });

            return socketTable;

        } catch (Exception e) {
            plugin.getLogger().severe("Failed to connect to socket: " + e.getMessage());
            e.printStackTrace();
            return LuaValue.NIL;
        }
    }
}
