package cn.evole.mods.mcbot.util.lib;

import dev.vankka.dependencydownload.DependencyManager;
import dev.vankka.dependencydownload.classloader.IsolatedClassLoader;
import dev.vankka.dependencydownload.classpath.ClasspathAppender;
import dev.vankka.dependencydownload.repository.Repository;
import dev.vankka.dependencydownload.repository.StandardRepository;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        return new LibUtils(Executors.newSingleThreadExecutor(), new FabricClasspathAppender(), fromPaths(dataDirectory, paths));
    }

    public static LibUtils create(Path dataDirectory, ExecutorService executor, String... paths) {
        return new LibUtils(executor, new FabricClasspathAppender(), fromPaths(dataDirectory, paths));
    }

    public static LibUtils create(Path dataDirectory, ExecutorService executor, ClasspathAppender classpathAppender, String... paths) {
        return new LibUtils(executor, classpathAppender, fromPaths(dataDirectory, paths));
    }

    public static LibUtils create(DependencyManager dependencyManager) {
        return new LibUtils(Executors.newSingleThreadExecutor(), new FabricClasspathAppender(), dependencyManager);
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

}
