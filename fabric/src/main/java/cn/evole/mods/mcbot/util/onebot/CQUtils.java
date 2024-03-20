package cn.evole.mods.mcbot.util.onebot;

import cn.evole.mods.mcbot.Const;
import cn.evole.mods.mcbot.config.ModConfig;
import cn.evole.onebot.sdk.event.message.GroupMessageEvent;
import cn.evole.onebot.sdk.event.message.MessageEvent;
import io.netty.util.concurrent.SingleThreadEventExecutor;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project: Bot-Connect-fabric-1.18
 * Author: cnlimiter
 * Date: 2023/2/10 1:11
 * Description:
 */
public class CQUtils {

    private final static String CQ_CODE_SPLIT = "(?<=\\[CQ:[^]]{1,99999}])|(?=\\[CQ:[^]]{1,99999}])";

    private final static String CQ_CODE_REGEX = "\\[CQ:(.*?),(.*?)]";

    private static final ExecutorService Executor = Executors.newSingleThreadExecutor();  // 创建CQ码处理线程池;

    public static boolean hasImg(String msg) {
        String regex = "\\[CQ:image,[(\\s\\S)]*]";
        val p = Pattern.compile(regex);
        val m = p.matcher(msg);
        return m.find();
    }

    /**
     * @param timeout 超时时间（毫秒），超时后返回空字符串。
     */
    public static @NotNull String replace(@NotNull MessageEvent event, long timeout) {
        String back = "";
        val task = new FutureTask<>(() -> replace(event));
        try {
            Executor.execute(task);
            back = task.get(timeout, TimeUnit.MILLISECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException | IllegalStateException e) {
            task.cancel(true);
            Const.LOGGER.error(e.getLocalizedMessage());
        }
        return back;
    }

    public static String replace(@NotNull MessageEvent event) {
        return replace(event.getRawMessage());
    }

    public static String replace(String msg) {
        String back;
        StringBuffer message = new StringBuffer();
        Pattern pattern = Pattern.compile(CQ_CODE_REGEX);
        Matcher matcher = pattern.matcher(msg);
        try {
            back = doReplace(matcher, message);
        } catch (Exception e) {
            back = msg;
            Const.LOGGER.error(e.getLocalizedMessage());
        }
        return back;
    }

    private static @NotNull String doReplace(@NotNull Matcher matcher, StringBuffer message) {
        while (matcher.find()) {//全局匹配
            val type = matcher.group(1);
            val data = matcher.group(2);
            switch (type) {
                case "image":
                    if (ModConfig.INSTANCE.getCommon().isImageOn() && Const.isLoad("chatimage")) {
                        val url = Arrays.stream(data.split(","))//具体数据分割
                                .filter(it -> it.startsWith("url"))//非空判断
                                .map(it -> it.substring(it.indexOf('=') + 1))
                                .findFirst();
                        if (url.isPresent()) {
                            matcher.appendReplacement(message, String.format("[[CICode,url=%s,name=来自QQ的图片]]", url.get()));
                        } else {
                            matcher.appendReplacement(message, "[图片]");
                        }
                    } else {
                        matcher.appendReplacement(message, "[图片]");
                    }
                    break;
                case "at":
                        val id = data.split("=");
                        if (id.length == 2) {
                            if (id[0].equals("qq"))
                                try {
                                    matcher.appendReplacement(message, String.format("[@%s]", BotUtils.getNickname(Long.parseLong(id[1]))));
                                    break;
                                } catch (NumberFormatException ignored) {}
                        }
                    matcher.appendReplacement(message, "[@]");
                    break;
                case "record":
                    matcher.appendReplacement(message, "[语音]");
                    break;
                case "forward":
                    matcher.appendReplacement(message, "[合并转发]");
                    break;
                case "video":
                    matcher.appendReplacement(message, "[视频]");
                    break;
                case "music":
                    matcher.appendReplacement(message, "[音乐]");
                    break;
                case "redbag":
                    matcher.appendReplacement(message, "[红包]");
                    break;
                case "face":
                    matcher.appendReplacement(message, "[表情]");
                    break;
                case "reply":
                    matcher.appendReplacement(message, "[回复]");
                    break;
                default:
                    matcher.appendReplacement(message, "[?]");
                    break;
            }
        }
        matcher.appendTail(message);
        return message.toString();

    }

    public static void shutdown() {
        Executor.shutdownNow();
    }
}
