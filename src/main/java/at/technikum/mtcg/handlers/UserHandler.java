package at.technikum.mtcg.handlers;

import at.technikum.mtcg.models.User;
import at.technikum.mtcg.utils.JsonUtil;
import at.technikum.mtcg.utils.ResponseUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UserHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            User user = JsonUtil.parseUserFromJson(requestBody);
            if (user != null && user.getUsername() != null && user.getPassword() != null) {
                try {
                    if (user.register()) {
                        String response = "HTTP/1.1 201 Created: User registered successfully.\n";
                        exchange.sendResponseHeaders(201, response.getBytes(StandardCharsets.UTF_8).length);
                        ResponseUtil.writeResponse(exchange, response);
                    } else {
                        String response = "HTTP/1.1 409 Conflict: User already exists.\n";
                        exchange.sendResponseHeaders(409, response.getBytes(StandardCharsets.UTF_8).length);
                        ResponseUtil.writeResponse(exchange, response);
                    }
                } catch(Exception e) {
                    String response = "HTTP/1.1 500 Internal Server Error:" + e.getMessage() + "\n";
                    exchange.sendResponseHeaders(400, response.getBytes(StandardCharsets.UTF_8).length);
                    ResponseUtil.writeResponse(exchange, response);
                }
                    } else {
                    String response = "HTTP/1.1 400 Bad Request: Invalid request body.\n";
                    exchange.sendResponseHeaders(400, response.getBytes(StandardCharsets.UTF_8).length);
                    ResponseUtil.writeResponse(exchange, response);
                }
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }