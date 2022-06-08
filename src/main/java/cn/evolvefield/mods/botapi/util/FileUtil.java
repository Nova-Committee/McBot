package cn.evolvefield.mods.botapi.util;


import cn.evolvefield.mods.botapi.BotApi;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/1 15:30
 * Version: 1.0
 */
public class FileUtil {

    public static void checkFolder(Path folder) {
        if (!folder.toFile().isDirectory()) {
            try {
                Files.createDirectories(folder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void streamResourceToDisk(URL inputUrl, File filePath) throws IOException {
        if (inputUrl == null) {
            BotApi.LOGGER.error("源文件夹是空的: " + filePath.toString());
        } else {
            FileUtils.copyURLToFile(inputUrl, filePath);
        }
    }


}
