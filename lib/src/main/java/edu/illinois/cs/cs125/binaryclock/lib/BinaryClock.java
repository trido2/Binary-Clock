package edu.illinois.cs.cs125.binaryclock.lib;

import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

public final class BinaryClock {

    public static String getTime(final String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject result = parser.parse(jsonString).getAsJsonObject();
        String caption = result.get("formatted").getAsString();
        return caption.substring(11);
    }
}
