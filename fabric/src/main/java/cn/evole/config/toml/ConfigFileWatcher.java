package cn.evole.config.toml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ConfigFileWatcher {
    public static final ConfigFileWatcher CONFIG_FILE_WATCHER = new ConfigFileWatcher();
    private static final Map<String, AutoReloadToml> fieldMap = new HashMap<>();
    private final WatchService watchService;
    private final List<String> watchRegisterRecord = new ArrayList<>();

    private ConfigFileWatcher() {
        try {
            this.watchService = FileSystems.getDefault().newWatchService();
            start();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static ConfigFileWatcher getInstance() {
        return CONFIG_FILE_WATCHER;
    }

    public void register(String path, AutoReloadToml toml) {
        try {
            Path ph = Paths.get(path);
            Path dirPath = ph.getParent();
            String dirAbsolutePath = dirPath.toFile().getAbsolutePath();

            if (!watchRegisterRecord.contains(dirAbsolutePath)) {
                dirPath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            }
            watchRegisterRecord.add(dirAbsolutePath);

            fieldMap.putIfAbsent(ph.toFile().getAbsolutePath(), toml);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void start() {
        Thread thread = new Thread(() -> {
            long lastModifyTimestamp = 0;

            w: while (true) {
                try {
                    WatchKey key = watchService.take();
                    for (WatchEvent<?> event : key.pollEvents()) {

                        WatchEvent.Kind<?> kind = event.kind();
                        if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                            @SuppressWarnings("unchecked")
                            WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
                            File file = ((Path) key.watchable()).resolve(pathEvent.context()).toFile();

                            AutoReloadToml toml = fieldMap.get(file.getAbsolutePath());
                            if (toml != null) {

                                long currentModifyTimestamp = System.currentTimeMillis();
                                if (currentModifyTimestamp - lastModifyTimestamp < 1000) {
                                    key.reset();
                                    continue w;
                                }
                                lastModifyTimestamp = currentModifyTimestamp;

                                try {
                                    Thread.sleep(20);
                                } catch (InterruptedException ignored) {
                                }

                                toml.reload();
                                System.out.println("reload");
                            }
                        }
                    }
                    key.reset();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    record ReloadTarget(AutoReloadToml obj, Field field) {
    }
}
