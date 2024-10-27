package at.technikum.mtcg.utils;

import at.technikum.mtcg.models.User;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JsonUtil {
    private static final Gson gson = new Gson();

    public static User parseUserFromJson(String json) {
        try {
            return gson.fromJson(json, User.class);
        } catch (JsonSyntaxException e) {
            System.out.println("Error parsing user from json: " + e.getMessage());
            return null;
        }
    }
}
