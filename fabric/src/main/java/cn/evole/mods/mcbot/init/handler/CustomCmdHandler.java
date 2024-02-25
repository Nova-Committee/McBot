package cn.evole.mods.mcbot.init.handler;

import cn.evole.mods.mcbot.Const;
import cn.evole.mods.mcbot.cmds.CustomCmd;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.gson.*;
import lombok.val;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/2 13:25
 * Version: 1.0
 */
public class CustomCmdHandler {

    public static final CustomCmdHandler INSTANCE = new CustomCmdHandler();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

    private static final File dir = FabricLoader.getInstance().getConfigDir().resolve("mcbot/cmds/").toFile();

    private final Map<String, CustomCmd> customCmdMap = new LinkedHashMap<>();

    public List<CustomCmd> getCustomCmds() {
        return Lists.newArrayList(this.customCmdMap.values());
    }

    public CustomCmd getCmdByAlies(String alies) {
        return this.customCmdMap.get(alies);
    }

    public Map<String, CustomCmd> getCustomCmdMap() {
        return customCmdMap;
    }

    public void load() {
        val stopwatch = Stopwatch.createStarted();

        this.writeDefault();

        clear();

        if (!dir.mkdirs() && dir.isDirectory()) {
            this.loadFiles();
        }

        stopwatch.stop();

        Const.LOGGER.info("加载 {} 个自定义命令，耗时 {} 毫秒", this.customCmdMap.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    public void writeDefault() {
        if (!dir.exists() && dir.mkdirs()) {
            JsonObject json1 = GSON.fromJson("{'alies': 'list', 'content': 'list', 'role': 0, 'OPEscape': false, 'enable': true}", JsonObject.class);
            JsonObject json2 = GSON.fromJson("{'alies': 'say', 'content': 'say %', 'role': 1, 'OPEscape': true, 'enable': true}", JsonObject.class);
            JsonObject json3 = GSON.fromJson("{'alies': 'bind', 'content': 'mcbot addBind %', 'role': 0, 'OPEscape': false, 'enable': true}", JsonObject.class);

            FileWriter writer1 = null;
            FileWriter writer2 = null;
            FileWriter writer3 = null;

            try {
                val file1 = new File(dir, "list.json");
                val file2 = new File(dir, "say.json");
                val file3 = new File(dir, "bind.json");
                writer1 = new FileWriter(file1);
                writer2 = new FileWriter(file2);
                writer3 = new FileWriter(file3);

                GSON.toJson(json1, writer1);
                GSON.toJson(json2, writer2);
                GSON.toJson(json3, writer3);

                writer1.close();
                writer2.close();
                writer3.close();
            } catch (Exception e) {
                Const.LOGGER.error("An error occurred while generating default custom cmd", e);
            } finally {
                IOUtils.closeQuietly(writer1, writer2, writer3);
            }

        }
    }

    private void loadFiles() {
        val files = CustomCmdHandler.dir.listFiles((FileFilter) FileFilterUtils.suffixFileFilter(".json"));
        if (files == null)
            return;

        for (val file : files) {
            JsonObject json;
            InputStreamReader reader = null;
            CustomCmd customCmd = null;

            try {
                reader = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
                json = new JsonParser().parse(reader).getAsJsonObject();

                customCmd = CustomCmd.loadFromJson(json);

                reader.close();
            } catch (Exception e) {
                Const.LOGGER.error("加载自定义命令出错，请检查文件", e);
            } finally {
                IOUtils.closeQuietly(reader);
            }

            if (customCmd != null && customCmd.isEnabled()) {
                String alies = customCmd.getCmdAlies();
                Const.LOGGER.debug(alies);
                this.customCmdMap.put(alies, customCmd);
            }
        }
    }

    public void clear() {
        this.customCmdMap.clear();
    }
}
