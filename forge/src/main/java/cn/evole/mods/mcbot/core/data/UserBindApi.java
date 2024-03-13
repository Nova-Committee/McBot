package cn.evole.mods.mcbot.core.data;

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
    private static final String BIND_NAME = "bind.csv";
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
            for (UserBind userBind : userBinds){
                userBindMap.put(userBind.getQqId(), userBind);
            }
        } catch (IOException ignored) {
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

    public static boolean has(String qq_id){
        return users.containsKey(qq_id);
    }

    public static boolean isIn(String user_name){
        for (UserBind userBind : users.values()){
            return userBind.getGameName().equals(user_name);
        }
        return false;
    }

    public static void add(String group_name, String qq_id, String game_name){
        if (!has(qq_id)) users.put(qq_id, new UserBind(System.currentTimeMillis(), qq_id, group_name, game_name));
    }

    public static void del(String qq_id){
        if (has(qq_id)) users.remove(qq_id);
    }
}
