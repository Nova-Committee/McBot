package cn.evolvefield.mods.botapi.api.data;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.util.JsonUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/20 10:19
 * Version: 1.0
 */
public class BindData {
    public static JsonArray groupData;
    public static JsonArray guildData;

    public static Map<Long, String> groupBindMap = new HashMap<>();

    public static Map<String, String> guildBindMap = new HashMap<>();

    static Path dataPath;

    public static void init() {
        dataPath = BotApi.CONFIG_FOLDER.resolve("data.json");
        if (dataPath.toFile().isFile()) {
            groupData = JsonUtil.getArray(dataPath.toFile(), "groupData");
            if (!groupData.isJsonNull()) {
                for (int i = 0; i < groupData.size(); i++) {
                    JsonElement sub = groupData.get(i);
                    if (sub instanceof JsonObject) {
                        JsonObject subObj = (JsonObject) sub;
                        if (subObj.has("QQ") && subObj.has("name"))
                            groupBindMap.put(subObj.get("QQ").getAsLong(), subObj.get("name").getAsString());
                    }
                }
            }
            guildData = JsonUtil.getArray(dataPath.toFile(), "guildData");
            if (!guildData.isJsonNull()) {
                for (int i = 0; i < guildData.size(); i++) {
                    JsonElement sub = guildData.get(i);
                    if (sub instanceof JsonObject) {
                        JsonObject subObj = (JsonObject) sub;
                        if (subObj.has("tinyId") && subObj.has("name"))
                            guildBindMap.put(subObj.get("tinyId").getAsString(), subObj.get("name").getAsString());
                    }
                }
            }

        } else {
            try {
                Files.createFile(dataPath);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static void save() {
        saveGroup();
        saveGuild();
    }

    private static void saveGroup() {
        JsonArray groupData = new JsonArray();
        JsonObject sub = new JsonObject();

        for (Map.Entry<Long, String> entry : groupBindMap.entrySet()) {
            sub.addProperty("name", entry.getValue());
            sub.addProperty("QQ", entry.getKey());
        }
        groupData.add(sub);
        JsonUtil.update(dataPath.toFile(), "groupData", groupData);

    }

    private static void saveGuild() {
        JsonArray guildData = new JsonArray();
        JsonObject sub1 = new JsonObject();

        for (Map.Entry<String, String> entry : guildBindMap.entrySet()) {
            sub1.addProperty("name", entry.getValue());
            sub1.addProperty("tinyId", entry.getKey());
        }

        guildData.add(sub1);
        JsonUtil.update(dataPath.toFile(), "guildData", guildData);

    }

    public static boolean addGroupBindData(String playerName, long qqId) {
        if (groupBindMap.containsKey(qqId)) {
            return false;
        }
        groupBindMap.put(qqId, playerName);
        saveGroup();
        return true;
    }

    public static boolean addGuildBindData(String playerName, String tinyId) {
        if (guildBindMap.containsKey(tinyId)) {
            return false;
        }
        guildBindMap.put(tinyId, playerName);
        saveGuild();
        return true;
    }

    public static boolean setGroupBindData(String playerName, long qqId) {
        if (groupBindMap.containsKey(qqId)) {
            groupBindMap.replace(qqId, playerName);
            saveGroup();
            return true;
        }
        return false;
    }

    public static boolean setGuildBindData(String playerName, String tinyId) {
        if (guildBindMap.containsKey(tinyId)) {
            guildBindMap.replace(tinyId, playerName);
            saveGuild();
            return true;
        }
        return false;
    }

    public static boolean delGroupBindData(long qqId) {
        groupBindMap.remove(qqId);
        return true;
    }

    public static boolean delGuildBindData(String tinyId) {
        groupBindMap.remove(tinyId);
        return true;
    }

    public static String getGroupBindDataQQ(long qqId) {
        return groupBindMap.get(qqId);
    }

    public static String getGuildBindDataId(String tinyId) {
        return guildBindMap.get(tinyId);
    }

    public static Object getKey(Map map, Object value) {
        Set set = map.entrySet(); //通过entrySet()方法把map中的每个键值对变成对应成Set集合中的一个对象
        Iterator<Map.Entry<Object, Object>> iterator = set.iterator();
        ArrayList<Object> arrayList = new ArrayList();
        while (iterator.hasNext()) {
            //Map.Entry是一种类型，指向map中的一个键值对组成的对象
            Map.Entry<Object, Object> entry = iterator.next();
            if (entry.getValue().equals(value)) {
                arrayList.add(entry.getKey());
            }
        }
        return arrayList;
    }
}
