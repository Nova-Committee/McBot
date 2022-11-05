package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.Static;
import cn.evolvefield.mods.botapi.api.cmd.CustomCmd;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.InputStreamReader;
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

    private static final CustomCmdHandler INSTANCE = new CustomCmdHandler();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

    private static final File dir = BotApi.CONFIGS.resolve("botapi/custom_cmd/").toFile();


    private final Map<String, CustomCmd> customCmdMap = new LinkedHashMap<>();

    public static CustomCmdHandler getInstance() {
        return INSTANCE;
    }

    public List<CustomCmd> getCustomCmds() {
        return Lists.newArrayList(this.customCmdMap.values());
    }

    public Map<String, CustomCmd> getCustomCmdMap() {
        return customCmdMap;
    }

    public CustomCmd getCustomCmdByAlies(String alies) {
        return this.customCmdMap.get(alies);
    }

    public void load() {
        Stopwatch stopwatch = Stopwatch.createStarted();

        this.writeDefault();

        clear();

        if (!dir.mkdirs() && dir.isDirectory()) {
            this.loadFiles(dir);
        }

        stopwatch.stop();

        Static.LOGGER.info("Loaded {} custom cmd(s) in {} ms", this.customCmdMap.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    public void writeDefault() {
        if (!dir.exists() && dir.mkdirs()) {
            JsonObject json = new JsonObject();
            json.addProperty("alies", "list");
            json.addProperty("content", "list");
            json.addProperty("role", 0);
            json.addProperty("enable", true);

            FileWriter writer = null;

            try {
                File file = new File(dir, "list.json");
                writer = new FileWriter(file);

                GSON.toJson(json, writer);
                writer.close();
            } catch (Exception e) {
                Static.LOGGER.error("An error occurred while generating default custom cmd", e);
            } finally {
                IOUtils.closeQuietly(writer);
            }

        }
    }

    private void loadFiles(File dir) {
        File[] files = dir.listFiles((FileFilter) FileFilterUtils.suffixFileFilter(".json"));
        if (files == null)
            return;

        for (File file : files) {
            JsonObject json;
            InputStreamReader reader = null;
            CustomCmd customCmd = null;

            try {
                JsonParser parser = new JsonParser();
                reader = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
                String name = file.getName().replace(".json", "");
                json = parser.parse(reader).getAsJsonObject();

                customCmd = CustomCmd.loadFromJson(new ResourceLocation(Static.MODID, name), json);

                reader.close();
            } catch (Exception e) {
                Static.LOGGER.error("An error occurred while loading custom cmd", e);
            } finally {
                IOUtils.closeQuietly(reader);
            }

            if (customCmd != null && customCmd.isEnabled()) {
                String alies = customCmd.getCmdAlies();

                this.customCmdMap.put(alies, customCmd);
            }
        }
    }

    public void clear() {
        this.customCmdMap.clear();
    }
}
