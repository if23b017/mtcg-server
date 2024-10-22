package at.technikum.mtcg;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;

class UserHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            InputStream requestBody = exchange.getRequestBody();
            ObjectMapper objectMapper = new ObjectMapper();
            String response;
            try {
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                String username = jsonNode.get("Username").asText();
                String password = jsonNode.get("Password").asText();
                User user = new User(username, password);

                boolean registrationSuccessful = user.register();
                if (registrationSuccessful) {
                    response = "User registered successfully.";
                    exchange.sendResponseHeaders(201, response.getBytes(StandardCharsets.UTF_8).length);
                } else {
                    response = "User registration failed. Username already exists.";
                    exchange.sendResponseHeaders(409, response.getBytes(StandardCharsets.UTF_8).length); // Conflict
                }
            } catch (Exception e) {
                response = "Invalid request body.";
                exchange.sendResponseHeaders(400, response.getBytes(StandardCharsets.UTF_8).length); // Bad Request
            }

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes(StandardCharsets.UTF_8));
            os.close();
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }
}
