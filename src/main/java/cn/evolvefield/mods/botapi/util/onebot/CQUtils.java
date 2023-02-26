package cn.evolvefield.mods.botapi.util.onebot;

import cn.evolvefield.mods.botapi.Const;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import cn.evolvefield.onebot.sdk.util.BotUtils;
import cn.evolvefield.onebot.sdk.util.RegexUtils;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
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
        if (Const.ChatImageOn && ConfigHandler.cached().getCommon().isImageOn()) {
            var matcher = RegexUtils.regexMatcher(CQ_CODE_REGEX, msg);//匹配cq码格式
            AtomicReference<String> returnMsg = new AtomicReference<>("");//返回字符串
            if (matcher == null) {
                returnMsg.set(msg);//不包含任何cq码则返回原msg
            } else {
                //while (matcher.find()){//找到所有符合的matcher
                if (matcher.group(1).equals("image")) {//如果是图片格式
                    Arrays.stream(matcher.group(2).split(","))//具体数据分割
                            .filter(args -> !args.isEmpty())//非空判断
                            .forEach(args -> {
                                if (args.substring(0, args.indexOf("=")).equals("url")) {//url
                                    var v = BotUtils.unescape(args.substring(args.indexOf("=") + 1));//去除空格

                                    returnMsg.set(matcher.replaceAll(String.format("[[CICode,url=%s,name=来自QQ的图片]]", v)));//转换ci码
                                }
                            });
                } else returnMsg.set("暂不支持");
                //}
            }

            return returnMsg.get();

        } else return msg;
    }
}
