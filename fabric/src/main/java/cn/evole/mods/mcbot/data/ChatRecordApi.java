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

public class ChatRecordApi {
    private static final String FILE_NAME = "chat.csv";
    private static final EasyCsv easyCsv = new EasyCsv();
    public static Map<String, ChatRecord> users;

    public static void load(Path folder){
        Path bindFile = folder.resolve(FILE_NAME);
        List<ChatRecord> chatRecords = new ArrayList<>();
        Map<String, ChatRecord> chatRecordMap = new HashMap<>();
        try {
            if (!bindFile.toFile().isFile()) Files.createFile(bindFile);
            chatRecords = easyCsv.readAll(bindFile.toFile().getAbsolutePath()
                    , ChatRecord.class);
            for (ChatRecord chatRecord : chatRecords){
                chatRecordMap.put(chatRecord.getMessageId(), chatRecord);
            }
        } catch (IOException ignored) {
        }

        users = chatRecordMap;
    }

    public static void save(Path folder){
        Path bindFile = folder.resolve(FILE_NAME);
        try {
            Files.deleteIfExists(bindFile);
            easyCsv.write(bindFile.toFile().getAbsolutePath()
                    , Arrays.asList(users.values().toArray()));
        } catch (IOException ignored) {
        }
    }

    public static boolean has(String message_id){
        return users.containsKey(message_id);
    }

    public static void add(String message_id, String group_name, String qq_id, String message){
        if (!has(qq_id)) users.put(qq_id, new ChatRecord(message_id, System.currentTimeMillis(), qq_id, group_name, message));
    }

    public static void del(String message_id){
        if (has(message_id)) users.remove(message_id);
    }
}
