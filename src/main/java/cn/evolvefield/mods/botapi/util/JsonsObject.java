package cn.evolvefield.mods.botapi.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/6/12 7:54
 * Version: 1.0
 */
public class JsonsObject {
    private final JsonObject jsonObject;

    public JsonsObject(String text) {
        this.jsonObject = parse(text);
    }

    public JsonsObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    private JsonObject parse(String text) {
        JsonElement elm = getJsonElement(text);
        if (elm == null) {
            return new JsonObject();
        }
        return elm.getAsJsonObject();
    }

    public JsonElement getFromString(String string, boolean log) {
        if (string == null) {
            return null;
        }
        try {
            return JsonParser.parseString(string);
        } catch (Exception e) {
            if (log) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public JsonElement getJsonElement(String string) {
        return getFromString(string, true);
    }

    public boolean has(String key) {
        return this.jsonObject.has(key);
    }

    public String optString(String key) {
        return this.optString(key, null);
    }

    public String optString(String key, String defaultValue) {
        return this.jsonObject.has(key) ? jsonObject.get(key).getAsString() : defaultValue;
    }

    public Number optNumber(String key) {
        return this.optNumber(key, null);
    }

    public Number optNumber(String key, Number defaultValue) {
        return this.jsonObject.has(key) ? jsonObject.get(key).getAsNumber() : defaultValue;
    }

    public long optLong(String key) {
        return this.optLong(key, 0);
    }

    public long optLong(String key, long defaultValue) {
        return this.jsonObject.has(key) ? jsonObject.get(key).getAsLong() : defaultValue;
    }

    public int optInt(String key) {
        return this.optInt(key, 0);
    }

    public int optInt(String key, int defaultValue) {
        final Number val = this.optNumber(key, null);
        if (val == null) {
            return defaultValue;
        }
        return val.intValue();
    }

    public JsonObject optJSONObject(String key) {
        return this.jsonObject.has(key) ? jsonObject.get(key).getAsJsonObject() : null;
    }

    public JsonArray optJSONArray(String key) {
        return this.jsonObject.has(key) ? jsonObject.get(key).getAsJsonArray() : null;
    }

}
