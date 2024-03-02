package cn.evole.mods.mcbot.util;

import org.jetbrains.annotations.NotNull;

/**
 * MCVersion
 *
 * @author cnlimiter
 * @version 1.0
 * @description 获取minecraft的版本
 * @date 2024/3/2 23:23
 */
public class MCVersion {

    @NotNull
    public static String getMcVersion() {
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
        return "1.16.5";
    }
}
