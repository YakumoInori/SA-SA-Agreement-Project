package webcom;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WebServer {
    private static HttpServer httpServer;

    public static void main(String[] args) throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(8000), 0);

        httpServer.createContext("/Home.html", new FileHandler("src/webapp/Home.html"));
        httpServer.createContext("/Informed_consent.html", new FileHandler("src/webapp/Informed_consent.html"));
        httpServer.createContext("/Informed_consent2.html", new FileHandler("src/webapp/Informed_consent2.html"));
        httpServer.createContext("/New_Patient.html", new FileHandler("src/webapp/New_Patient.html"));
        httpServer.createContext("/shutdown", new ShutdownHandler());

        httpServer.setExecutor(null); // creates a default executor
        httpServer.start();
        System.out.println("HTTP Server started on port 8000");

        // Automatically open the home page
        openBrowser("http://localhost:8000/Home.html");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (httpServer != null) {
                httpServer.stop(0);
            }
            System.out.println("Server stopped");
        }));
    }

    static class FileHandler implements HttpHandler {
        private final String filePath;

        public FileHandler(String filePath) {
            this.filePath = filePath;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            byte[] response = Files.readAllBytes(Paths.get(filePath));
            exchange.sendResponseHeaders(200, response.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response);
            }
        }
    }

    static class ShutdownHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "Shutting down the server...";
            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
            System.out.println("Shutdown request received, stopping server...");
            httpServer.stop(0);
        }
    }

    public static void openBrowser(String url) {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", url});
            } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                Runtime.getRuntime().exec("open " + url);
            } else if (System.getProperty("os.name").toLowerCase().contains("nix") ||
                    System.getProperty("os.name").toLowerCase().contains("nux")) {
                Runtime.getRuntime().exec("xdg-open " + url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
