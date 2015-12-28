package de.trion.sample.util;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * Provides a minimal http server for demonstration
 * This class is a collection of dirty hacks - do not use in production!
 */

public class SampleWebserver
{
    public static void serve()
    {
        try
        {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(8081), 0);
            httpServer.createContext("/", (HttpExchange he) ->
            {
                final URI requestURI = he.getRequestURI();
                
                try (final OutputStream out = he.getResponseBody())
                {
                    final String path = requestURI.getPath();
                    
                    if(path.length() < 2)
                    {
                        he.sendResponseHeaders(404, 0);
                        return;
                    }
                    
                    final URL resource = SampleWebserver.class.getResource(path);
                    if(resource == null)
                    {
                        he.sendResponseHeaders(404, 0);
                        return;
                    }
                    
                    if(path.endsWith("html"))
                    {
                        he.getResponseHeaders().add("content-type", "text/html");
                    }
                    if(path.endsWith("js"))
                    {
                        he.getResponseHeaders().add("content-type", "text/javascript");
                    }
                    
                    final String content = new BufferedReader(new InputStreamReader(resource.openStream(), StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
                    
                    he.sendResponseHeaders(200, content.getBytes().length);
                    out.write(content.getBytes());
                    out.flush();
                }
                he.close();
            });
            httpServer.createContext("/api/contracts", (HttpExchange he) ->
            {
                final String path = "/contracts/contracts.json";
                final URL resource = SampleWebserver.class.getResource(path);
                
                try (final OutputStream out = he.getResponseBody())
                {
                    final String content = new BufferedReader(new InputStreamReader(resource.openStream(), StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
                    
                    he.getResponseHeaders().add("content-type", "application/json; charset=utf-8");
                    he.sendResponseHeaders(200, content.getBytes().length);
                    out.write(content.getBytes());
                    out.flush();
                }
                he.close();
            });
            httpServer.setExecutor(null);
            httpServer.start();
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }

}
