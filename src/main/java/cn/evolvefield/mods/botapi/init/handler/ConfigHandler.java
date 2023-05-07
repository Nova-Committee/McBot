package cn.evolvefield.mods.botapi.init.handler;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.Const;
import cn.evolvefield.mods.botapi.init.config.ModConfig;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;


public class ConfigHandler {
    private static final AtomicReference<ModConfig> data = new AtomicReference<>();
    private static final Jankson gson = Jankson.builder().build();
    public static AtomicReference<WatchService> watcher = new AtomicReference<>();//看门狗
    private static File lastFile;
    private static boolean loaded;

    public static void load(File file) {
        lastFile = file;
        try {
            JsonObject jObject = gson.load(file);
            ModConfig newData = gson.fromJson(jObject, ModConfig.class);
            if (newData != data.get()) {
                data.set(newData);
                if (!isLoaded()) {
                    //立即保存，确保将任何缺少的配置值及其默认值添加到配置文件中，并恢复注释
                    FileWriter tileWriter = new FileWriter(file);
                    tileWriter.write(ConfigHandler.saveConfig());
                    tileWriter.close();
                }
                loaded = true;
            }
        } catch (Exception ignored) {
            data.set(new ModConfig());
        }
    }

    public static boolean isLoaded() {
        return loaded;
    }

    public static void saveConfigToFile(File file) {
        try (FileOutputStream configOut = new FileOutputStream(file)) {
            IOUtils.write(ConfigHandler.saveConfig(), configOut, StandardCharsets.UTF_8);
        } catch (Throwable ignored) {
        }
    }

    public static void save() {
        saveConfigToFile(BotApi.CONFIG_FILE);
    }

    public static ModConfig cached() {
        return data.get();
    }

    public static synchronized ModConfig update(ModConfig _data) {
        data.set(_data);
        return data.get();
    }

    public static synchronized boolean reload() {
        if (lastFile != null) {
            load(lastFile);
            return true;
        }
        return false;
    }

    private static String saveConfig() {
        ModConfig conf = data.get();
        JsonElement elem = gson.toJson(conf);
        return elem.toJson(true, true);
    }

    public static void init(File file) {
        if (lastFile == null) lastFile = file;
        try {
            try {//监听配置文件变化
                Runnable configWatcher = () ->
                {
                    try {
                        if (watcher.get() == null) {
                            watcher.set(FileSystems.getDefault().newWatchService());
                            lastFile.toPath().getParent().register(watcher.get(), StandardWatchEventKinds.ENTRY_MODIFY);
                        }
                        WatchKey checker = watcher.get().take();
                        for (WatchEvent<?> event : checker.pollEvents()) {
                            if (Const.isShutdown) return;
                            Path changed = (Path) event.context();
                            if (changed.endsWith(lastFile.getName()) && isLoaded()) {
                                if (reload())
                                    Const.LOGGER.info("已经重新加载位于" + lastFile.getCanonicalPath() + "的配置文件!");
                            }
                        }
                        checker.reset();
                    } catch (Exception ignored) {
                    }
                };
                if (BotApi.configWatcherExecutorService.isShutdown()) {
                    BotApi.configWatcherExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("BotApi Config Watcher %d").setDaemon(true).build());
                }
                BotApi.configWatcherExecutorService.scheduleAtFixedRate(configWatcher, 0, 10, TimeUnit.SECONDS);

            } catch (Exception ignored) {
            }

            if (!file.exists()) {
                ModConfig configData = new ModConfig();//重新加载配置
                data.set(configData);
                FileWriter tileWriter = new FileWriter(file);
                tileWriter.write(ConfigHandler.saveConfig());
                tileWriter.close();
            } else {
                ConfigHandler.load(file);//没有文件则加载
            }
        } catch (Exception ignored) {
        }
    }

}
