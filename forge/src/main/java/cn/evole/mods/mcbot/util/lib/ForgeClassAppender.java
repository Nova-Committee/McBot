package cn.evole.mods.mcbot.util.lib;

import dev.vankka.dependencydownload.classpath.ClasspathAppender;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.nio.file.Path;

/**
 * ForgeClassAppender
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/3/13 13:48
 */
public class ForgeClassAppender extends URLClassLoader implements ClasspathAppender {
    static {
        ClassLoader.registerAsParallelCapable();
    }
    public ForgeClassAppender(ClassLoader parent) {
        super(new URL[0], parent);
    }

    @Override
    public void appendFileToClasspath(@NotNull Path path) throws MalformedURLException {
        addURL(path.toUri().toURL());
    }

}
