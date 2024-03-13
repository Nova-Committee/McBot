package cn.evole.mods.mcbot.util;

import cn.evole.mods.mcbot.Const;
import cn.evole.onebot.sdk.util.GsonUtils;
import com.google.gson.JsonObject;
import cpw.mods.modlauncher.Launcher;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * MCVersion
 *
 * @author cnlimiter
 * @version 1.0
 * @description 获取minecraft的版本
 * @date 2024/3/2 23:23
 */
public class MCVerUtil {

    @NotNull
    public static String getMcVersion() {
        try {
            // Fabric
            return (String) ReflectionUtil.clazz("net.fabricmc.loader.impl.FabricLoaderImpl")
                    .get("INSTANCE")
                    .get("getGameProvider()")
                    .get("getNormalizedGameVersion()").get();
        } catch (Exception ignored) {

        }
        try {
            // Quilt
            return (String) ReflectionUtil.clazz("org.quiltmc.loader.impl.QuiltLoaderImpl")
                    .get("INSTANCE")
                    .get("getGameProvider()")
                    .get("getNormalizedGameVersion()").get();
        } catch (Exception ignored) {

        }

        // MinecraftForge 1.13~1.20.2
        // NeoForge 1.20.1~
        try {
            String[] args = (String[]) ReflectionUtil.clazz(Launcher.INSTANCE).get("argumentHandler").get("args").get();
            for (int i = 0; i < args.length - 1; ++i) {
                if (args[i].equalsIgnoreCase("--fml.mcversion")) {
                    return args[i + 1];
                }
            }
        } catch (Exception e) {
            Const.LOGGER.warn("Error getting minecraft version: %s", e);
        }

        // MinecraftForge 1.20.3~
        // 1.20.3: https://github.com/MinecraftForge/MinecraftForge/blob/1.20.x/fmlloader/src/main/java/net/minecraftforge/fml/loading/VersionInfo.java
        try {
            Class<?> clazz = Class.forName("net.minecraftforge.fml.loading.FMLLoader");
            try (InputStream is = clazz.getResourceAsStream("/forge_version.json")) {
                return GsonUtils.getGson().fromJson(new InputStreamReader(is), JsonObject.class).get("mc").getAsString();
            }
        } catch (Exception e) {
            Const.LOGGER.warn("Error getting minecraft version: %s", e);
        }
        return "1.16.5";
    }

}
