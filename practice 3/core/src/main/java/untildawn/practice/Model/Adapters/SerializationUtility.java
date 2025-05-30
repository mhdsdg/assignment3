package untildawn.practice.Model.Adapters;

import com.google.gson.*;
import untildawn.practice.Model.App;
import untildawn.practice.Model.User;

import java.io.*;

public class SerializationUtility {
    private static final Gson gson = new GsonBuilder()
        .registerTypeAdapter(User.class, new UserAdapter())
        .registerTypeAdapter(App.class, new AppAdapter())
        .setPrettyPrinting()
        .serializeNulls()
        .create();

    // Save static App state to a file
    public static void saveAppState(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(new App(), writer); // Pass dummy instance (serializer ignores it)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load static App state from a file
    public static void loadAppState(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            gson.fromJson(reader, App.class); // Deserializer updates static fields directly
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // (Keep existing User serialization methods)
    public static String serializeUser(User user) {
        return gson.toJson(user);
    }

    public static User deserializeUser(String json) {
        return gson.fromJson(json, User.class);
    }
}
