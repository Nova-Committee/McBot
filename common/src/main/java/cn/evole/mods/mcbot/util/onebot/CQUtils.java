package cn.evole.mods.mcbot.util.onebot;

import cn.evole.mods.mcbot.PlatformHelper;
import cn.evole.mods.mcbot.common.config.ModConfig;
import cn.evole.onebot.sdk.entity.ArrayMsg;
import cn.evole.onebot.sdk.event.message.GroupMessageEvent;
import cn.evole.onebot.sdk.event.message.MessageEvent;
import cn.evole.onebot.sdk.util.BotUtils;
import cn.evole.onebot.sdk.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static cn.evole.mods.mcbot.Constants.LOGGER;
import static cn.evole.mods.mcbot.Constants.cqExecutor;

/**
 * @Project: McBot
 * @Author: cnlimiter
 * @CreateTime: 2024/10/27 19:57
 * @Description:
 */
public class CQUtils {

    /**
     * @param timeout 超时时间（毫秒），超时后返回空字符串。
     */
    public static @NotNull String replace(@NotNull MessageEvent event, long timeout) {
        String back = "";
        val task = new FutureTask<>(() -> doReplace(event));
        try {
            cqExecutor.execute(task);
            back = task.get(timeout, TimeUnit.MILLISECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException | IllegalStateException e) {
            task.cancel(true);
            LOGGER.error(e.getLocalizedMessage());
        }
        return back;
    }

    private static @NotNull String doReplace(MessageEvent event) {
        val stringMsg = event.getMessage();
        val message = new StringBuilder();
        List<ArrayMsg> msg = GsonUtils.fromJson(stringMsg, new TypeToken<List<ArrayMsg>>() {}.getType());
        for (ArrayMsg arrayMsg : msg){
            //try {
                switch (arrayMsg.getType()){
                    case text -> {
                        message.append(arrayMsg.getData().get("text"));
                    }
                    case image -> {
                        if (ModConfig.get().getCommon().getImageOn().getValue()
                                && PlatformHelper.isModLoaded("chatimage")
                        ) {
                            message.append(String.format("[[CICode,url=%s,name=来自QQ的图片]]",
                                    arrayMsg.getData().get("url").replaceAll("&amp;", "&")//转义字符转义
                            ));
                        } else {
                            message.append("[图片]");
                        }
                    }
                    case at -> {
                        val qq = arrayMsg.getData().get("qq");
                        if (!qq.equalsIgnoreCase("all")) {
                            if (event instanceof GroupMessageEvent groupMessageEvent)
                                message.append(String.format("[@%s]", arrayMsg.getData().get("name")));
                            else message.append("[@]");
                        } else {
                            message.append("[@全体]");
                        }
                    }
                    case reply -> {

                    }
                    default -> message.append("[?]");
                }
//            } catch (Exception e) {
//                LOGGER.error(e.getLocalizedMessage());
//                return stringMsg;
//            }
        }
        return message.toString();
    }
}
