package cn.evolvefield.mods.botapi.config;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.util.json.JSONFormat;
import com.google.gson.Gson;
import net.minecraft.client.Minecraft;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static cn.evolvefield.mods.botapi.BotApi.CONFIG_FOLDER;

public class ConfigManger {
    //private static final Path CONFIG_FOLDER = Minecraft.getMinecraft().gameDir.toPath().resolve("config").resolve(BotApi.MODID);
    private static final Gson GSON = new Gson();

    public static void initBotConfig() {

        if (!CONFIG_FOLDER.toFile().isDirectory()) {
            try {
                Files.createDirectories(CONFIG_FOLDER);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Path configPath = CONFIG_FOLDER.resolve(BotApi.config.getConfigName() + ".json");
        if (configPath.toFile().isFile()) {
            try {
                BotApi.config = GSON.fromJson(FileUtils.readFileToString(configPath.toFile(), StandardCharsets.UTF_8),
                        BotConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FileUtils.write(configPath.toFile(), GSON.toJson(BotApi.config), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void saveBotConfig(BotConfig config) {
        if (!CONFIG_FOLDER.toFile().isDirectory()) {
            try {
                Files.createDirectories(CONFIG_FOLDER);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Path configPath = CONFIG_FOLDER.resolve(config.getConfigName() + ".json");
        try {
            FileUtils.write(configPath.toFile(), JSONFormat.formatJson(GSON.toJson(config)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
