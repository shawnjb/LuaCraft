package com.shawnjb.luacraft.lib;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;

/**
 * The SocketServer class allows Lua scripts to create a TCP socket server
 * that listens for client connections.
 */
public class SocketServer extends VarArgFunction {
    private final LuaCraft plugin;

    public SocketServer(LuaCraft plugin) {
        this.plugin = plugin;
    }

    /**
     * Starts a TCP socket server on the specified port.
     * Lua Usage: socketServer(port)
     *
     * @param port the port number to listen on
     * @return a Lua table representing the server
     */
    @Override
    public Varargs invoke(Varargs args) {
        int port = args.checkint(1);

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            plugin.getLogger().info("Socket server started on port " + port);

            LuaValue serverTable = LuaValue.tableOf();

            serverTable.set("accept", new VarArgFunction() {
                @Override
                public Varargs invoke(Varargs innerArgs) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                        LuaValue clientTable = LuaValue.tableOf();

                        clientTable.set("receive", new VarArgFunction() {
                            @Override
                            public Varargs invoke(Varargs innerArgs) {
                                try {
                                    String request = in.readLine();
                                    return LuaValue.valueOf(request);
                                } catch (Exception e) {
                                    plugin.getLogger().severe("Error receiving data: " + e.getMessage());
                                    return LuaValue.NIL;
                                }
                            }
                        });

                        clientTable.set("send", new VarArgFunction() {
                            @Override
                            public Varargs invoke(Varargs innerArgs) {
                                String data = innerArgs.checkjstring(1);
                                out.println(data);
                                return LuaValue.TRUE;
                            }
                        });

                        clientTable.set("close", new VarArgFunction() {
                            @Override
                            public Varargs invoke(Varargs innerArgs) {
                                try {
                                    clientSocket.close();
                                    return LuaValue.TRUE;
                                } catch (Exception e) {
                                    plugin.getLogger().severe("Error closing client socket: " + e.getMessage());
                                    return LuaValue.NIL;
                                }
                            }
                        });

                        return clientTable;

                    } catch (Exception e) {
                        plugin.getLogger().severe("Error accepting client: " + e.getMessage());
                        return LuaValue.NIL;
                    }
                }
            });

            serverTable.set("close", new VarArgFunction() {
                @Override
                public Varargs invoke(Varargs innerArgs) {
                    try {
                        serverSocket.close();
                        return LuaValue.TRUE;
                    } catch (Exception e) {
                        plugin.getLogger().severe("Error closing server socket: " + e.getMessage());
                        return LuaValue.NIL;
                    }
                }
            });

            return serverTable;

        } catch (Exception e) {
            plugin.getLogger().severe("Failed to start socket server: " + e.getMessage());
            e.printStackTrace();
            return LuaValue.NIL;
        }
    }
}
