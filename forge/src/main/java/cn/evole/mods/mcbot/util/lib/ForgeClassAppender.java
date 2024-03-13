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
    public ForgeClassAppender(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public ForgeClassAppender(URL[] urls) {
        super(urls);
    }

    public ForgeClassAppender(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    public ForgeClassAppender(String name, URL[] urls, ClassLoader parent) {
        super(name, urls, parent);
    }

    public ForgeClassAppender(String name, URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(name, urls, parent, factory);
    }

    public void addURLs(URL... urls) {
        for (URL url : urls) {
            this.addURL(url);
        }
    }
    @Override
    public void appendFileToClasspath(@NotNull Path path) throws MalformedURLException {
        addURLs(path.toUri().toURL());
    }

}
