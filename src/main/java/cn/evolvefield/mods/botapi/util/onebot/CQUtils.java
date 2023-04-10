package cn.evolvefield.mods.botapi.util.onebot;

import cn.evolvefield.mods.botapi.Const;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import cn.evolvefield.onebot.sdk.util.BotUtils;
import cn.evolvefield.onebot.sdk.util.RegexUtils;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Project: Bot-Connect-fabric-1.18
 * Author: cnlimiter
 * Date: 2023/2/10 1:11
 * Description:
 */
public class CQUtils {

    private final static String CQ_CODE_SPLIT = "(?<=\\[CQ:[^]]{1,99999}])|(?=\\[CQ:[^]]{1,99999}])";

    private final static String CQ_CODE_REGEX = "\\[CQ:([^,\\[\\]]+)((?:,[^,=\\[\\]]+=[^,\\[\\]]*)*)]";


    public static boolean hasImg(String msg) {
        String regex = "\\[CQ:image,[(\\s\\S)]*\\]";
        var p = Pattern.compile(regex);
        var m = p.matcher(msg);
        return m.find();
    }

    public static String replace(String msg) {
        if (msg.indexOf('[') == -1)
            return BotUtils.unescape(msg);
        var message = new StringBuilder();
        var matcher = RegexUtils.regexMatcher(CQ_CODE_REGEX, msg);
        while (matcher.find()) {
            var type = matcher.group(1);
            var replacement = switch (type) {
                case "image" -> {
                    if (Const.ChatImageOn && ConfigHandler.cached().getCommon().isImageOn())
                    {
                        var url = Arrays.stream(matcher.group(2).split(","))//具体数据分割
                                .filter(it -> it.startsWith("url"))//非空判断
                                .map(it -> it.substring(it.indexOf('=')) + 1)
                                .findFirst();
                        if (url.isPresent())
                            yield String.format("[[CICode,url=%s,name=来自QQ的图片]]", url.get());
                        else
                            yield "[图片]";
                    }
                    else
                        yield "[图片]";
                }
                case "reply" -> "[回复]";
                case "at" -> "[@]";
                case "record" -> "[语音]";
                case "forward" -> "[合并转发]";
                case "video" -> "[视频]";
                case "music" -> "[音乐]";
                case "redbag" -> "[红包]";
                case "poke" -> "[戳一戳]";
                case "face" -> "[表情]";
                default -> "[?]";
            };
            matcher.appendReplacement(message, replacement);
        }
        matcher.appendTail(message);
        return BotUtils.unescape(message.toString());
    }
}
