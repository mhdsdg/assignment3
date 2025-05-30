package untildawn.practice.Model.Adapters;

import com.google.gson.*;
import untildawn.practice.Model.App;
import untildawn.practice.Model.User;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AppAdapter implements JsonSerializer<App>, JsonDeserializer<App> {
    @Override
    public JsonElement serialize(App app, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        // Serialize static users list
        JsonArray usersArray = new JsonArray();
        for (User user : App.getUsers()) {
            usersArray.add(context.serialize(user));
        }
        jsonObject.add("users", usersArray);

        // Serialize other static App properties
        jsonObject.addProperty("language", App.getLanguage());
        jsonObject.addProperty("musicVolume", App.getMusicVolume());
        jsonObject.addProperty("autoReloadEnabled", App.isAutoReloadEnabled());
        jsonObject.addProperty("soundEffectsEnabled", App.isSoundEffectsEnabled());
        jsonObject.addProperty("grayscaleEnabled", App.isGrayscaleEnabled());

        return jsonObject;
    }

    @Override
    public App deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Deserialize users list (static)
        JsonArray usersArray = jsonObject.getAsJsonArray("users");
        ArrayList<User> users = new ArrayList<>();
        for (JsonElement userElement : usersArray) {
            users.add(context.deserialize(userElement, User.class));
        }
        App.setUsers(users); // Update static users list

        // Deserialize other static properties
        App.setLanguage(jsonObject.get("language").getAsString());
        App.setMusicVolume(jsonObject.get("musicVolume").getAsFloat());
        App.setAutoReloadEnabled(jsonObject.get("autoReloadEnabled").getAsBoolean());
        App.setSoundEffectsEnabled(jsonObject.get("soundEffectsEnabled").getAsBoolean());
        App.setGrayscaleEnabled(jsonObject.get("grayscaleEnabled").getAsBoolean());

        // Since App is static, we return null (or a dummy instance if needed)
        return null;
    }
}
