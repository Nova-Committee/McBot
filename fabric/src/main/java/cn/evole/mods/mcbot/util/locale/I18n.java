package cn.evole.mods.mcbot.util.locale;

import cn.evole.mods.mcbot.Const;
import cn.evole.mods.mcbot.init.config.ModConfig;
import cn.evole.mods.mcbot.util.Reflection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.val;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.locale.Language;
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

    public static void init() {
        translations = new HashMap<>();

        val version = getMcVersion().replace(".", "_");

        Optional<Path> optional = FabricLoader.getInstance().getModContainer("mcbot_" + version).orElseThrow(null)
                .findPath("/lang/" + ModConfig.INSTANCE.getCommon().getLanguageSelect() + ".json");

        if (!optional.isPresent()) {
            Const.LOGGER.warn("-----------------------------------------");
            Const.LOGGER.warn("McBot cannot find translations for \"" + ModConfig.INSTANCE.getCommon().getLanguageSelect() + "\" and uses \"en_us\" by default!");
            Const.LOGGER.warn("");
            Const.LOGGER.warn("You are welcome to contribute translations!");
            Const.LOGGER.warn("Contributing: https://github.com/cnlimiter/McBot#Contributing");
            Const.LOGGER.warn("-----------------------------------------");

            optional = FabricLoader.getInstance().getModContainer("mcbot_" + version).orElseThrow(null)
                    .findPath("/lang/en_us.json");
        }

        if (optional.isPresent()) {
            try {
                String content = IOUtils.toString(Files.newInputStream(optional.get()), StandardCharsets.UTF_8);
                translations = new Gson().fromJson(content, new TypeToken<Map<String, String>>() {
                }.getType());
            } catch (Exception e) {
                Const.LOGGER.error(ExceptionUtils.getStackTrace(e));
            }
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

    private static String getMcVersion() {
        try {
            // Fabric
            return (String) Reflection.clazz("net.fabricmc.loader.impl.FabricLoaderImpl")
                    .get("INSTANCE")
                    .get("getGameProvider()")
                    .get("getNormalizedGameVersion()").get();
        } catch (Exception ignored) {

        }
        try {
            // Quilt
            return (String) Reflection.clazz("org.quiltmc.loader.impl.QuiltLoaderImpl")
                    .get("INSTANCE")
                    .get("getGameProvider()")
                    .get("getNormalizedGameVersion()").get();
        } catch (Exception ignored) {

        }
        return null;
    }
}
