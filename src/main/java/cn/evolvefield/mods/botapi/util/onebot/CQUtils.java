package cn.evolvefield.mods.botapi.util.onebot;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import cn.evolvefield.onebot.sdk.util.BotUtils;
import lombok.val;

import java.util.Arrays;
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


    public static boolean hasImg(String msg) {
        String regex = "\\[CQ:image,[(\\s\\S)]*\\]";
        val p = Pattern.compile(regex);
        val m = p.matcher(msg);
        return m.find();
    }

    public static String replace(String msg) {
        if (msg.indexOf('[') == -1)
            return BotUtils.unescape(msg);
        String message = "";
        Pattern pattern = Pattern.compile(CQ_CODE_REGEX);
        Matcher matcher = pattern.matcher(msg);
        while (matcher.find()) {//全局匹配
            val type = matcher.group(1);
            val data = matcher.group(2);
            switch (type) {
                case "image": {
                    if (ConfigHandler.cached().getCommon().isImageOn()) {
                        val url = Arrays.stream(data.split(","))//具体数据分割
                                .filter(it -> it.startsWith("url"))//非空判断
                                .map(it -> it.substring(it.indexOf('=') + 1))
                                .findFirst();
                        if (url.isPresent()) {
                            message = matcher.replaceFirst(String.format("[[CICode,url=%s,name=来自QQ的图片]]", url.get()));
                        } else {
                            message = matcher.replaceFirst("[图片]");
                        }
                    } else {
                        message = matcher.replaceFirst("[图片]");
                    }
                    break;
                }
                case "at":
                    val id = Arrays.stream(data.split(","))//具体数据分割
                            .filter(it -> it.startsWith("qq"))//非空判断
                            .map(it -> it.substring(it.indexOf('=') + 1))
                            .findFirst();
                    if (id.isPresent()) {
                        message = matcher.replaceFirst(String.format("@%s", id.get()));
                    } else {
                        message = matcher.replaceFirst("[@]");
                    }
                    break;
                case "record":
                    message = matcher.replaceFirst("[语音]");
                    break;
                case "forward":
                    message = matcher.replaceFirst("[合并转发]");
                    break;
                case "video":
                    message = matcher.replaceFirst("[视频]");
                    break;
                case "music":
                    message = matcher.replaceFirst("[音乐]");
                    break;
                case "redbag":
                    message = matcher.replaceFirst("[红包]");
                    break;
                case "face":
                    message = matcher.replaceFirst("[表情]");
                    break;
                default:
                    message = matcher.replaceFirst("[?]");
                    break;
            }
        }
        return message;
    }
}
