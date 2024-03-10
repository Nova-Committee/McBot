package cn.evole.mods.mcbot.util.lib;

import dev.vankka.dependencydownload.classpath.ClasspathAppender;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

/**
 * FabricClasspathAppender
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/3/8 21:01
 */
public class FabricClasspathAppender implements ClasspathAppender {

    @Override
    public void appendFileToClasspath(@NotNull Path path) {
        FabricLauncherBase.getLauncher().addToClassPath(path);
    }
}
