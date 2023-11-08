package cn.evole.mods.mcbot.init.config;

import lombok.Getter;
import lombok.Setter;
import cn.evole.config.toml.AutoLoadTomlConfig;
import cn.evole.config.toml.annotation.TableField;
import org.tomlj.TomlTable;

/**
 * Name: McBot-fabric / CmdConfig
 * Author: cnlimiter
 * CreateTime: 2023/11/7 20:19
 * Description:
 */

@Getter
@Setter
public class CmdConfig extends AutoLoadTomlConfig {
    @TableField(rightComment = "自定义q群加入事件消息")
    private String welcomeNotice = "欢迎加群~";//自定义q群加入事件消息
    @TableField(rightComment = "自定义q群离开消息")
    private String leaveNotice = "离开了我们qwq";//自定义q群离开消息
    @TableField(rightComment = "q群中使用命令的关键符号")
    private String cmdStart = "!";//q群中使用命令的关键符号

    @TableField(rightComment = "是否开启显示到游戏中的前缀")
    private boolean gamePrefixOn = true;//是否开启显示到游戏中的前缀
    @TableField(rightComment = "是否开启显示到游戏中的id前缀")
    private boolean idGamePrefixOn = true;//是否开启显示到游戏中的id前缀
    @TableField(rightComment = "来自q群显示到游戏中的前缀")
    private String qqGamePrefix = "群聊";//来自q群显示到游戏中的前缀
    @TableField(rightComment = "来自频道显示到游戏中的前缀")
    private String guildGamePrefix = "频道";//来自频道显示到游戏中的前缀
    @TableField(rightComment = "是否开启显示到游戏中的昵称为群昵称")
    private boolean groupNickOn = false;//是否开启显示到游戏中的昵称为群昵称


    @TableField(rightComment = "是否开启来自游戏的消息显示到群中的前缀")
    private boolean mcPrefixOn = true;//是否开启来自游戏的消息显示到群中的前缀
    @TableField(rightComment = "来自游戏的消息显示到群中的前缀")
    private String mcPrefix = "MC";//来自游戏的消息显示到群中的前缀



    @TableField(rightComment = "是否开启游戏中自定义关键词")
    private boolean mcChatPrefixOn = false;//是否开启游戏中自定义关键词
    @TableField(rightComment = "是否开启qq中自定义关键词")
    private boolean qqChatPrefixOn = false;//是否开启qq中自定义关键词
    @TableField(rightComment = "游戏中自定义的消息头文本")
    private String mcChatPrefix = "q";//游戏中自定义的消息头文本
    @TableField(rightComment = "qq中自定义的消息头文本")
    private String qqChatPrefix = "m";//qq中自定义的消息头文本

    public CmdConfig() {
        super(null);
    }
    public CmdConfig(TomlTable source) {
        super(source);
        this.load(CmdConfig.class);
    }
}
