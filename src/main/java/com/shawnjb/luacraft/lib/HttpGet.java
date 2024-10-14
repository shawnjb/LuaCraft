package com.shawnjb.luacraft.lib;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import com.shawnjb.luacraft.LuaCraft;

/**
 * The HttpGet class allows Lua scripts to make HTTP GET requests
 * to fetch raw content from web URLs, such as Pastebin and GitHub Gist.
 */
public class HttpGet extends VarArgFunction {
    private final LuaCraft plugin;

    public HttpGet(LuaCraft plugin) {
        this.plugin = plugin;
    }

    /**
     * Makes an HTTP GET request to the provided URL and returns the raw content.
     * Lua Usage: httpGet(url)
     *
     * @param url the URL to fetch the content from
     * @return the raw content as a string, or nil if the request failed
     */
    @Override
    public Varargs invoke(Varargs args) {
        String urlString = args.checkjstring(1);

        try {
            URI uri = new URI(urlString);
            URL url = uri.toURL();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder content = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    content.append(line).append("\n");
                }

                in.close();
                return LuaValue.valueOf(content.toString().trim());
            } else {
                plugin.getLogger().warning("HttpGet request failed. Response code: " + responseCode);
                return LuaValue.NIL;
            }

        } catch (Exception e) {
            plugin.getLogger().severe("Failed to make HttpGet request: " + e.getMessage());
            e.printStackTrace();
            return LuaValue.NIL;
        }
    }
}
