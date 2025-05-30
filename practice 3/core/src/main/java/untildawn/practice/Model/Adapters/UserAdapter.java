package untildawn.practice.Model.Adapters;

import com.badlogic.gdx.graphics.Texture;
import com.google.gson.*;
import untildawn.practice.Model.User;

import java.lang.reflect.Type;

public class UserAdapter implements JsonSerializer<User>, JsonDeserializer<User> {
    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", user.getId());
        jsonObject.addProperty("username", user.getUsername());
        jsonObject.addProperty("password", user.getPassword());

        // Handle nullable security fields
        jsonObject.add("securityQuestion",
            user.getSecurityQuestion() != null ?
                new JsonPrimitive(user.getSecurityQuestion()) : JsonNull.INSTANCE);

        jsonObject.add("securityAnswer",
            user.getSecurityAnswer() != null ?
                new JsonPrimitive(user.getSecurityAnswer()) : JsonNull.INSTANCE);

        jsonObject.addProperty("score", user.getScore());
        jsonObject.addProperty("kills", user.getKills());
        jsonObject.addProperty("survivalTime", user.getSurvivalTime());

        // Handle Texture (avatar) serialization
        if (user.getAvatar() != null) {
            jsonObject.addProperty("avatarPath", user.getAvatar().toString());
        } else {
            jsonObject.add("avatarPath", JsonNull.INSTANCE);
        }
        System.out.println("[SAVE] Kills: " + user.getKills() +
            ", Score: " + user.getScore() +
            ", Survival: " + user.getSurvivalTime());

        return jsonObject;
    }

    @Override
    public User deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        User user = new User(
            jsonObject.get("username").getAsString(),
            jsonObject.get("password").getAsString()
        );

        user.setId(jsonObject.get("id").getAsInt());

        // Handle nullable security fields
        if (!jsonObject.get("securityQuestion").isJsonNull()) {
            user.setSecurityQuestion(jsonObject.get("securityQuestion").getAsString());
        } else {
            user.setSecurityQuestion(null);
        }

        if (!jsonObject.get("securityAnswer").isJsonNull()) {
            user.setSecurityAnswer(jsonObject.get("securityAnswer").getAsString());
        } else {
            user.setSecurityAnswer(null);
        }

        user.setScore(jsonObject.get("score").getAsInt());
        user.setKills(jsonObject.get("kills").getAsInt());
        user.setSurvivalTime(jsonObject.get("survivalTime").getAsInt());

        // Handle Texture (avatar) deserialization
        if (!jsonObject.get("avatarPath").isJsonNull()) {
            String avatarPath = jsonObject.get("avatarPath").getAsString();
            user.setAvatar(new Texture(avatarPath));
        }
        System.out.println("[LOAD] Kills: " + user.getKills() +
            ", Score: " + user.getScore() +
            ", Survival: " + user.getSurvivalTime());
        return user;
    }
}
