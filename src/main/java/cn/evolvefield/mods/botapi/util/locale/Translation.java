package cn.evolvefield.mods.botapi.util.locale;

import cn.evolvefield.mods.botapi.Const;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.StringDecomposer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

public abstract class Translation {
    private static final Gson GSON = new Gson();
    private static final Pattern UNSUPPORTED_FORMAT_PATTERN = Pattern.compile("%(\\d+\\$)?[\\d.]*[df]");
    public static final String DEFAULT_LANGUAGE = "en_us";
    private static volatile Translation instance = loadDefault(ConfigHandler.cached().getCommon().getLanguageSelect());

    public Translation() {
    }

    private static Translation loadDefault(String langId) {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        Objects.requireNonNull(builder);
        BiConsumer<String, String> biConsumer = builder::put;
        final String resourceFString = "/assets/botapi/lang/%s.json";
        final String resourceLocation = String.format(resourceFString, langId);
        try {
            InputStream inputStream = Translation.class.getResourceAsStream(resourceLocation);

            if (inputStream == null) {
                Const.LOGGER.info(String.format("No BotApi lang file for the language '%s' found. Make it to 'en_us' by default.", langId));
                inputStream = I18n.class.getResourceAsStream(String.format(resourceFString, DEFAULT_LANGUAGE));
            }

            try {
                loadFromJson(inputStream, biConsumer);
            } catch (Throwable var7) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                    }
                }

                throw var7;
            }

            if (inputStream != null) {
                inputStream.close();
            }
        } catch (JsonParseException | IOException var8) {
            Const.LOGGER.error("Couldn't read strings from {}", resourceLocation, var8);
        }

        final Map<String, String> inputStream = builder.build();
        return new Translation() {
            public String getOrDefault(String string) {
                return (String) inputStream.getOrDefault(string, string);
            }

            public boolean has(String string) {
                return inputStream.containsKey(string);
            }

            public boolean isDefaultRightToLeft() {
                return false;
            }

            public FormattedCharSequence getVisualOrder(FormattedText formattedText) {
                return (formattedCharSink) -> {
                    return formattedText.visit((style, string) -> {
                        return StringDecomposer.iterateFormatted(string, style, formattedCharSink) ? Optional.empty() : FormattedText.STOP_ITERATION;
                    }, Style.EMPTY).isPresent();
                };
            }
        };
    }

    public static void loadFromJson(InputStream inputStream, BiConsumer<String, String> biConsumer) {
        JsonObject jsonObject = (JsonObject) GSON.fromJson(new InputStreamReader(inputStream, StandardCharsets.UTF_8), JsonObject.class);

        for (Map.Entry<String, JsonElement> stringJsonElementEntry : jsonObject.entrySet()) {
            Map.Entry<String, JsonElement> entry = (Map.Entry) stringJsonElementEntry;
            String string = UNSUPPORTED_FORMAT_PATTERN.matcher(GsonHelper.convertToString((JsonElement) entry.getValue(), (String) entry.getKey())).replaceAll("%$1s");
            biConsumer.accept((String) entry.getKey(), string);
        }

    }

    public static Translation getInstance() {
        return instance;
    }

    public static void inject(Translation language) {
        instance = language;
    }

    public abstract String getOrDefault(String string);

    public abstract boolean has(String string);

    public abstract boolean isDefaultRightToLeft();

    public abstract FormattedCharSequence getVisualOrder(FormattedText formattedText);

    public List<FormattedCharSequence> getVisualOrder(List<FormattedText> list) {
        return (List) list.stream().map(this::getVisualOrder).collect(ImmutableList.toImmutableList());
    }
}
