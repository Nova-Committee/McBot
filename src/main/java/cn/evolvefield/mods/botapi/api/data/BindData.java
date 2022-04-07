package cn.evolvefield.mods.botapi.api.data;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.util.json.JSONArray;
import cn.evolvefield.mods.botapi.util.json.JSONFormat;
import cn.evolvefield.mods.botapi.util.json.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
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
    public static JSONArray allData;
    public static Map<String, Long> bindMap = new HashMap<>();
    static Path dataPath;
    static String content;

    public static void init() {
        dataPath = BotApi.CONFIG_FOLDER.resolve("data.json");
        if (dataPath.toFile().isFile()) {
            try {
                content = FileUtils.readFileToString(dataPath.toFile(), StandardCharsets.UTF_8);
                JSONObject data = new JSONObject(content);
                if (data.has("BindData")) {
                    allData = data.getJSONArray("BindData");
                    for (int i = 0; i < allData.length(); i++) {
                        JSONObject subObj = allData.getJSONObject(i);
                        bindMap.put(subObj.getString("name"), subObj.getLong("QQ"));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
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

        try {
            JSONObject data = new JSONObject();
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(dataPath.toFile()), StandardCharsets.UTF_8);
            for (Map.Entry<String, Long> entry : bindMap.entrySet()) {
                JSONObject subData = new JSONObject();
                subData.put("name", entry.getKey());
                subData.put("QQ", entry.getValue());
                data.accumulate("BindData", subData);
            }

            osw.write(JSONFormat.formatJson(data.toString()));
            osw.flush();//清空缓冲区，强制输出数据
            osw.close();//关闭输出流
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static boolean addBindData(String playerName, long qqId) {
        bindMap.put(playerName, qqId);
        save();
        return true;
    }

    public static boolean setBindData(String playerName, long qqId) {
        if (bindMap.containsKey(playerName)) {
            bindMap.replace(playerName, qqId);
            save();
            return true;
        }
        return false;
    }

    public static boolean delBindData(String playerName) {
        bindMap.remove(playerName);
        return true;
    }

    public static long getBindDataQQ(String playerName) {
        return bindMap.get(playerName);
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
