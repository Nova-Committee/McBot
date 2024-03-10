package cn.evole.mods.mcbot.config;

import cn.evole.config.toml.AutoLoadTomlConfig;
import cn.evole.config.toml.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import org.tomlj.TomlTable;

/**
 * Name: McBot-fabric / BotConfig
 * Author: cnlimiter
 * CreateTime: 2023/11/7 20:28
 * Description:
 */

@Getter
@Setter
public class BotConfig extends AutoLoadTomlConfig {

    @TableField(rightComment = "地址（支持域名和ipv6）")
    private String url = "ws://127.0.0.1:8080";
    @TableField(rightComment = "鉴权")
    private String token = "";
    @TableField(rightComment = "mirai鉴权方式不一样")
    private boolean mirai = false;
    @TableField(rightComment = "机器人qq")
    private long botId = 0L;//机器人qq
    @TableField(rightComment = "重连（未实现）")
    private boolean reconnect = true;
    @TableField(rightComment = "重连次数（未实现）")
    private int maxReconnectAttempts = 20;

    public BotConfig() {
        super(null);
    }

    public BotConfig(TomlTable source) {
        super(source);
        this.load(BotConfig.class);
    }

    public cn.evole.onebot.client.core.BotConfig build(){
        return new cn.evole.onebot.client.core.BotConfig(url, token, botId, true, mirai, reconnect, maxReconnectAttempts, "string");
    }

}
