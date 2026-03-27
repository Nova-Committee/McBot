package cn.evole.mods.mcbot.common.config;

import com.iafenvoy.jupiter.config.container.AutoInitConfigContainer.AutoInitConfigCategoryBase;
import com.iafenvoy.jupiter.config.entry.BooleanEntry;
import com.iafenvoy.jupiter.config.entry.IntegerEntry;
import com.iafenvoy.jupiter.config.entry.StringEntry;
import lombok.Getter;
import lombok.Setter;

/**
 * Name: McBot-fabric / BotConfig
 * Author: cnlimiter
 * CreateTime: 2023/11/7 20:28
 * Description:
 */

@Getter
@Setter
public class BotConfig extends AutoInitConfigCategoryBase {
    public StringEntry tag = new StringEntry("config.mcbot.bot.tag", "main");//, "跨服支持,权限支持"
    public StringEntry url = new StringEntry("config.mcbot.bot.url", "127.0.0.1:18082");//, "地址（支持域名和ipv6）"
    public StringEntry token = new StringEntry("config.mcbot.bot.token", "");//, "鉴权"
    public StringEntry botId = new StringEntry("config.mcbot.bot.botId", "0");//, "机器人qq"
    public BooleanEntry reconnect = new BooleanEntry("config.mcbot.bot.reconnect", true);//, "自动重连"
    public IntegerEntry reconnectMaxTimes = new IntegerEntry("config.mcbot.bot.reconnectMaxTimes", 3, 0, 10);//, "自动重连次数"
    public IntegerEntry reconnectInterval = new IntegerEntry("config.mcbot.bot.reconnectInterval", 5, 0, 10000);//, "超时宽容度（秒）"

    public BotConfig() {
        super("bot", "config.mcbot.category.bot");
    }

    public cn.evole.onebot.client.core.BotConfig build() {
        return new cn.evole.onebot.client.core.BotConfig(
                makeValid(url.getValue())
                , token.getValue(), Long.parseLong(botId.getValue()), token.getValue().startsWith("mirai_"),
                reconnect.getValue(), reconnectInterval.getValue(), reconnectMaxTimes.getValue());
    }

    private static String makeValid(String url) {
        if (url.startsWith("wss://")) return url;
        return url.startsWith("ws://") ? url : "ws://" + url;
    }
}
