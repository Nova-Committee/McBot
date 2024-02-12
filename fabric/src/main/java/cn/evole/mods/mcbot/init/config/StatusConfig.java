package cn.evole.mods.mcbot.init.config;

import cn.evole.config.toml.AutoLoadTomlConfig;
import cn.evole.config.toml.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import org.tomlj.TomlTable;

/**
 * Name: McBot-fabric / StatusConfig
 * Author: cnlimiter
 * CreateTime: 2023/11/7 18:49
 * Description:
 */

@Getter
@Setter
public class StatusConfig extends AutoLoadTomlConfig {
    //接收来自q群的消息开关
    @TableField(rightComment = "全局接收")
    private boolean rEnable = true;
    @TableField(rightComment = "命令接收")
    private boolean rCmdEnable = true;
    @TableField(rightComment = "消息接收")
    private boolean rChatEnable = true;

    //发往q群消息的开关
    @TableField(rightComment = "发送消息")
    private boolean sEnable = true;
    @TableField(rightComment = "发送欢迎玩家入群消息")
    private boolean sQqWelcomeEnable = true;
    @TableField(rightComment = "发送玩家退群消息")
    private boolean sQqLeaveEnable = true;
    @TableField(rightComment = "发送加入服务器消息")
    private boolean sJoinEnable = true;
    @TableField(rightComment = "发送离开服务器消息")
    private boolean sLeaveEnable = true;
    @TableField(rightComment = "发送玩家死亡消息")
    private boolean sDeathEnable = true;
    @TableField(rightComment = "发送服务器聊天")
    private boolean sChatEnable = true;
    @TableField(rightComment = "发送成就消息")
    private boolean sAdvanceEnable = true;

    public StatusConfig(){
        super(null);
    }

    public StatusConfig(TomlTable source) {
        super(source);
        this.load(StatusConfig.class);
    }
}
