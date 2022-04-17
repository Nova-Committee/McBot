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
    public static JsonArray allData;
    public static Map<Long, String> bindMap = new HashMap<>();
    static Path dataPath;

    public static void init() {
        dataPath = BotApi.CONFIG_FOLDER.resolve("data.json");
        if (dataPath.toFile().isFile()) {
            allData = JsonUtil.getArray(dataPath.toFile(), "BindData");
            if (!allData.isJsonNull()) {
                for (int i = 0; i < allData.size(); i++) {
                    JsonElement sub = allData.get(i);
                    if (sub instanceof JsonObject) {
                        JsonObject subObj = (JsonObject) sub;
                        if (subObj.has("QQ") && subObj.has("name"))
                            bindMap.put(subObj.get("QQ").getAsLong(), subObj.get("name").getAsString());
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

        JsonArray bindData = new JsonArray();
        JsonObject sub = new JsonObject();

        for (Map.Entry<Long, String> entry : bindMap.entrySet()) {
            sub.addProperty("name", entry.getValue());
            sub.addProperty("QQ", entry.getKey());
        }
        bindData.add(sub);
        JsonUtil.update(dataPath.toFile(), "BindData", bindData);

    }

    public static boolean addBindData(String playerName, long qqId) {
        if (bindMap.containsKey(playerName)) {
            return false;
        }
        bindMap.put(qqId, playerName);
        save();
        return true;
    }

    public static boolean setBindData(String playerName, long qqId) {
        if (bindMap.containsKey(playerName)) {
            bindMap.replace(qqId, playerName);
            save();
            return true;
        }
        return false;
    }

    public static boolean delBindData(long qqId) {
        bindMap.remove(qqId);
        return true;
    }

    public static String getBindDataQQ(long qqId) {
        return bindMap.get(qqId);
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
