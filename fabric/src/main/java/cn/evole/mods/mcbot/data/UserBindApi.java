package cn.evole.mods.mcbot.data;

import com.xykj.easycsv.EasyCsv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * @Project: McBot-fabric
 * @Author: cnlimiter
 * @CreateTime: 2024/2/20 15:35
 * @Description:
 */

public class UserBindApi {
    public static String BIND_NAME = "bind.csv";
    private static final EasyCsv easyCsv = new EasyCsv();
    public static Map<String, UserBind> users;

    public static void load(Path folder){
        Path bindFile = folder.resolve(BIND_NAME);
        List<UserBind> userBinds = new ArrayList<>();
        Map<String, UserBind> userBindMap = new HashMap<>();
        try {
            if (!bindFile.toFile().isFile()) Files.createFile(bindFile);
            userBinds = easyCsv.readAll(bindFile.toFile().getAbsolutePath()
                    , UserBind.class);
        } catch (IOException ignored) {
        }
        for (UserBind userBind : userBinds){
            userBindMap.put(userBind.getQqId(), userBind);
        }
        users = userBindMap;
    }

    public static void save(Path folder){
        Path bindFile = folder.resolve(BIND_NAME);
        try {
            Files.deleteIfExists(bindFile);
            easyCsv.write(bindFile.toFile().getAbsolutePath()
                    , Arrays.asList(users.values().toArray()));
        } catch (IOException ignored) {
        }
    }

    public static boolean exist(String qq_id){
        return users.containsKey(qq_id);
    }

    public static void add(String group_name, String qq_id, String game_name){
        if (!exist(qq_id)) users.put(qq_id, new UserBind(System.currentTimeMillis(), qq_id, group_name, game_name));
    }

    public static void delete(String qq_id){
        if (exist(qq_id)) users.remove(qq_id);
    }
}
