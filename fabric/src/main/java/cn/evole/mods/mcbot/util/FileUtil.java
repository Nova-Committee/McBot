package cn.evole.mods.mcbot.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * FileUtil
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/3/11 19:18
 */
public class FileUtil {
    public static void checkFolder(Path folder) {
        if (!folder.toFile().isDirectory()) {
            try {
                Files.createDirectories(folder);
            } catch (IOException ignored) {
            }
        }

    }
}
