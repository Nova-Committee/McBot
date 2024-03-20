package cn.evole.mods.mcbot.util.lib;


import cn.evole.mods.mcbot.McBot;
import dev.vankka.dependencydownload.DependencyManager;
import net.minecraftforge.fml.loading.FMLLoader;
import dev.vankka.dependencydownload.classpath.ClasspathAppender;
import dev.vankka.dependencydownload.repository.Repository;
import dev.vankka.dependencydownload.repository.StandardRepository;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//#if MC > 11800
//$$ import cpw.mods.modlauncher.Launcher;
//$$ import cpw.mods.modlauncher.api.IModuleLayerManager;
//#endif
/**
 * @Project: Test-Lib
 * @Author: cnlimiter
 * @CreateTime: 2024/2/18 18:29
 * @Description:
 */

public class LibUtils {


    private static final List<Repository> REPOSITORIES = Arrays.asList(
            new StandardRepository("https://repo1.maven.org/maven2"),
            new StandardRepository("https://oss.sonatype.org/content/repositories/snapshots"),
            new StandardRepository("https://s01.oss.sonatype.org/content/repositories/snapshots"),
            new StandardRepository("https://maven.neoforged.net/releases"),
            new StandardRepository("https://maven.nova-committee.cn/releases")
    );

    private final DependencyManager dependencyManager;
    private final ExecutorService executor;
    private final ClasspathAppender classpathAppender;

    public static LibUtils create(Path dataDirectory, String... paths) {
        return new LibUtils(Executors.newSingleThreadExecutor(), new ForgeClassAppender(getClassLoader()), fromPaths(dataDirectory, paths));
    }

    public static LibUtils create(Path dataDirectory, ExecutorService executor, String... paths) {
        return new LibUtils(executor, new ForgeClassAppender(getClassLoader()), fromPaths(dataDirectory, paths));
    }

    public static LibUtils create(Path dataDirectory, ClasspathAppender classpathAppender, String... paths) {
        return new LibUtils(Executors.newSingleThreadExecutor(), classpathAppender, fromPaths(dataDirectory, paths));
    }

    public static LibUtils create(Path dataDirectory, ExecutorService executor, ClasspathAppender classpathAppender, String... paths) {
        return new LibUtils(executor, classpathAppender, fromPaths(dataDirectory, paths));
    }

    public static LibUtils create(Path dataDirectory, DependencyManager dependencyManager) {
        return new LibUtils(Executors.newSingleThreadExecutor(), new ForgeClassAppender(getClassLoader()), dependencyManager);
    }


    public LibUtils(ExecutorService executor, ClasspathAppender classpathAppender, DependencyManager dependencyManager) {
        this.dependencyManager = dependencyManager;
        this.executor = executor;
        this.classpathAppender = classpathAppender;
    }


    public static DependencyManager fromPaths(Path dataDirectory, String... resources){
        if (!dataDirectory.toFile().isDirectory()) dataDirectory.toFile().mkdirs();
        DependencyManager dependencyManager = new DependencyManager(dataDirectory);
        for (String dependencyResource : resources) {
            try {
                URL resource = LibUtils.class.getClassLoader().getResource(dependencyResource);
                if (resource != null) dependencyManager.loadFromResource(resource);
            } catch (IOException | IllegalArgumentException e) {
                e.fillInStackTrace();
            }
        }
        return dependencyManager;
    }

    public DependencyManager getDependencyManager() {
        return dependencyManager;
    }

    public LibUtils download() {
        return download(classpathAppender);
    }

    private LibUtils download(ClasspathAppender appender) {
        CompletableFuture<Void>[] downloadFutures = dependencyManager.download(executor, REPOSITORIES);
        for (CompletableFuture<Void> future : downloadFutures) {
            executor.submit(() -> {
                ProgressBar downloadBar = ProgressBar.build(dependencyManager.getDependencies().size());
                future.join();
                downloadBar.process();
            });
        }

        //dependencyManager.relocateAll(executor).join();
        dependencyManager.loadAll(executor, appender).join();
        return this;
    }


    private static URL[] getJars(Path cache) {
        File folder = cache.toFile();
        File[] files = folder.listFiles((file, name) -> name.endsWith(".jar"));
        if (files == null) {
            return new URL[0];
        }
        URL[] urls = new URL[files.length];
        for (int i = 0; i < files.length; i++) {
            try {
                urls[i] = files[i].toURI().toURL();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        return urls;
    }

    private static ClassLoader getClassLoader() {
        //#if MC <11700
        ClassLoader loader = FMLLoader.getLaunchClassLoader();
        //#else
        //$$ ClassLoader loader = Thread.currentThread().getContextClassLoader();
        //$$ if (loader == null)
        //$$     loader = Launcher.INSTANCE.findLayerManager().flatMap(lm -> lm.getLayer(IModuleLayerManager.Layer.GAME)).orElseThrow().modules().stream().findFirst().map(Module::getClassLoader).orElse(null);
        //#endif
        if (loader == null)
            loader = McBot.class.getClassLoader();
        return loader;
    }

}
