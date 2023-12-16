package cn.evole.mods.mcbot.util.locale;

import cn.evole.mods.mcbot.Const;
import cn.evole.mods.mcbot.IMcBot;
import cn.evole.mods.mcbot.init.config.ModConfig;
import cn.evole.onebot.sdk.util.FileUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.locale.Language;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class I18n {
    private static Map<String, String> translations;
    public static Path LANG_FOLDER;
    public static String LANG_FILE;
    public static String DEFAULT_LANG_FILE;



    public static void init(Path folder) {
        translations = new HashMap<>();
        LANG_FOLDER = folder.resolve("lang");
        FileUtils.checkFolder(LANG_FOLDER);
        LANG_FILE = "assets/mcbot/lang/" + ModConfig.INSTANCE.getCommon().getLanguageSelect() + ".json";
        DEFAULT_LANG_FILE = "assets/mcbot/lang/en_us.json";

        Path optional = FMLLoader.getLoadingModList().getModFileById("mcbot").getFile().findResource(LANG_FILE);

        if (optional == null) {
            Const.LOGGER.warn("-----------------------------------------");
            Const.LOGGER.warn("McBot cannot find translations for \"" + ModConfig.INSTANCE.getCommon().getLanguageSelect() + "\" and uses \"en_us\" by default!");
            Const.LOGGER.warn("");
            Const.LOGGER.warn("You are welcome to contribute translations!");
            Const.LOGGER.warn("Contributing: https://github.com/cnlimiter/McBot#Contributing");
            Const.LOGGER.warn("-----------------------------------------");

            optional = FMLLoader.getLoadingModList().getModFileById("mcbot").getFile().findResource(DEFAULT_LANG_FILE);
        }

        try {
            String content = IOUtils.toString(Files.newInputStream(optional), StandardCharsets.UTF_8);
            translations = new Gson().fromJson(content, new TypeToken<Map<String, String>>() {
            }.getType());
        } catch (Exception e) {
            Const.LOGGER.error(ExceptionUtils.getStackTrace(e));
        }


    }

    public static String get(String key, Object... args) {
        try {
            String translation1 = translations.get(key);
            if (translation1 != null) {
                return String.format(translation1, args);
            } else {
                //#if MC >= 11600
                String translation2 = Language.getInstance().getOrDefault(key);
                //#else
                //$$ String translation2 = Language.getInstance().getElement(key);
                //#endif
                if (!translation2.equals(key)) {
                    return String.format(translation2, args);
                } else {
                    return "TranslateError{\"key\":\"" + key + "\",\"args\":" + Arrays.toString(args) + "}";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "TranslateError{\"key\":\"" + key + "\",\"args\":" + Arrays.toString(args) + "}";
        }
    }

    public static String get(String key) {
        String translation = translations.get(key);
        if (translation != null) {
            return translation;
        } else {
            return key;
        }
    }
}
