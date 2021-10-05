package cn.evolvefield.mods.botapi.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class ModConfig {
    private static final ForgeConfigSpec.Builder CFG = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec.IntValue GROUP_ID;

    public static ForgeConfigSpec.ConfigValue<String> wsHOST;
    public static ForgeConfigSpec.IntValue wsPORT;
    public static ForgeConfigSpec.ConfigValue<String> sendHOST;
    public static ForgeConfigSpec.IntValue sendPORT;
    public static ForgeConfigSpec.BooleanValue IS_ENABLED;
    public static ForgeConfigSpec.ConfigValue<String> KEY;
    public static ForgeConfigSpec.BooleanValue RECEIVE_ENABLED;
    public static ForgeConfigSpec.BooleanValue SEND_ENABLED;
    static {
        CFG.comment("连接设置").push("connection");
        GROUP_ID = CFG.comment("群号").defineInRange("groupId", 337631140,0,1000000000);
        wsHOST = CFG.comment("WS 服务器地址").define("wsHost", "127.0.0.1");
        wsPORT = CFG.comment("WS 服务器端口").defineInRange("wsPort", 6700,0,65536);
        sendHOST = CFG.comment("发送地址").define("sendHost", "127.0.0.1");
        sendPORT = CFG.comment("发送端口").defineInRange("sendPort", 5700,0,65536);
        IS_ENABLED = CFG.comment("是否开启WS接收").define("enabled", true);
        KEY = CFG.comment("Access Key").define("key", "");
        RECEIVE_ENABLED = CFG.comment("是否发送QQ消息到游戏").define("receive_enabled", true);
        SEND_ENABLED = CFG.comment("是否发送游戏信息到QQ").define("send_enabled", true);
        CFG.pop();
        COMMON_CONFIG = CFG.build();
    }

    public static void setup(Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        configData.load();
        COMMON_CONFIG.setConfig(configData);
    }
}
