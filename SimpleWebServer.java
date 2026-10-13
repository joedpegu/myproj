import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpleWebServer {
    public static void main(String[] args) throws Exception {
        // Create server listening on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        // Map the root URL ("/") to our handler
        server.createContext("/", new HtmlHandler());
        server.setExecutor(null); 
        server.start();
        
        System.out.println("Server started. Open http://localhost:8080 in your browser.");
    }

    static class HtmlHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            // Read the HTML file
            byte[] response = Files.readAllBytes(Paths.get("index.html"));
            
            // Send headers and response
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            os.write(response);
            os.close();
        }
    }
}