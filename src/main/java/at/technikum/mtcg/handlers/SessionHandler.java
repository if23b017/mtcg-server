package at.technikum.mtcg.handlers;

import at.technikum.mtcg.models.User;
import at.technikum.mtcg.utils.JsonUtil;
import at.technikum.mtcg.utils.ResponseUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SessionHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            User user = JsonUtil.parseUserFromJson(requestBody);
            String token = user != null ? user.login() : null;
            String response;

            if (token != null) {
                response = "HTTP/1.1 200 OK: User logged in successfully. Token: " + token + "\n";
                exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);

            } else {
                response = "HTTP/1.1 401 Unauthorized: Invalid credentials.\n";
                exchange.sendResponseHeaders(401, response.getBytes(StandardCharsets.UTF_8).length);
            }
            ResponseUtil.writeResponse(exchange, response);
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }
}