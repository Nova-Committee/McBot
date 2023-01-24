package cn.evolvefield.mods.botapi.init.config;

import cn.evolvefield.onebot.sdk.config.BotConfig;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/2 13:44
 * Version: 1.0
 */
@Data
public class ModConfig {
    @SerializedName("common")
    private Common common = new Common();
    @SerializedName("status")
    private Status status = new Status();
    @SerializedName("cmd")
    private Cmd cmd = new Cmd();
    @SerializedName("bot_config")
    private BotConfig botConfig = new BotConfig();

    public String getConfigName() {
        return "botapi";
    }

    @Data
    public static class Status {

        //接收来自q群的消息开关
        @SerializedName("RECEIVE_ENABLED")
        private boolean RECEIVE_ENABLED = true;
        @SerializedName("R_COMMAND_ENABLED")
        private boolean R_COMMAND_ENABLED = true;
        @SerializedName("R_CHAT_ENABLE")
        private boolean R_CHAT_ENABLE = true;

        //发往q群消息的开关
        @SerializedName("SEND_ENABLED")
        private boolean SEND_ENABLED = true;
        @SerializedName("S_WELCOME_ENABLE")
        private boolean S_WELCOME_ENABLE = true;
        @SerializedName("S_JOIN_ENABLE")
        private boolean S_JOIN_ENABLE = true;
        @SerializedName("S_LEAVE_ENABLE")
        private boolean S_LEAVE_ENABLE = true;
        @SerializedName("S_DEATH_ENABLE")
        private boolean S_DEATH_ENABLE = true;
        @SerializedName("S_CHAT_ENABLE")
        private boolean S_CHAT_ENABLE = true;
        @SerializedName("S_ADVANCE_ENABLE")
        private boolean S_ADVANCE_ENABLE = true;

    }

    @Data
    public static class Cmd {
        @SerializedName("welcome_notice")
        private String welcomeNotice = "欢迎加群~";//自定义q群加入事件消息
        @SerializedName("leave_notice")
        private String leaveNotice = "有人离开了我们qwq";//自定义q群离开消息
        @SerializedName("command_start")
        private String commandStart = "!";//q群中使用命令的关键符号
        @SerializedName("bind_command")
        private String bindCommand = "bind";//暂时没用
        @SerializedName("whitelist_command")
        private String whiteListCommand = "white";//暂时没用
        @SerializedName("bind_success")
        private String bindSuccess =
                "绑定成功 ┈━═☆\n" +
                        "成功绑定账号: %Player%\n" +
                        "你他妈绑定成功了呢~\"";//暂时没用
        @SerializedName("bindFail")
        private String bindFail =
                "绑定失败 ┈━═☆\n" +
                        "你的QQ已经绑定或 %Player% 已被绑定\n" +
                        "你他妈不能再绑定了呢~";//暂时没用
        @SerializedName("bindNotOnline")
        private String bindNotOnline =
                "玩家不在线 ┈━═☆\n" +
                        "%Player% 不在线或者不存在哦\n" +
                        "还他妈不上线搁这玩QQ呢~";//暂时没用

        @SerializedName("qqPrefix")
        private String qqPrefix = "群聊";//来自q群显示到游戏中的前缀
        @SerializedName("guildPrefix")
        private String guildPrefix = "频道";//来自频道显示到游戏中的前缀
        @SerializedName("mcPrefix")
        private String mcPrefix = "MC";//来自游戏的消息前缀
        @SerializedName("mcChatPrefixEnable")
        private boolean mcChatPrefixEnable = false;//游戏中系统命令发送的消息头
        @SerializedName("mcSystemPrefixEnable")
        private boolean mcSystemPrefixEnable = true;//游戏中自定义消息头
        @SerializedName("qqChatPrefixEnable")
        private boolean qqChatPrefixEnable = false;//qq中自定义消息头
        @SerializedName("mcChatPrefix")
        private String mcChatPrefix = "q";//游戏中自定义的消息头文本
        @SerializedName("qqChatPrefix")
        private String qqChatPrefix = "m";//qq中自定义的消息头文本
        @SerializedName("mcSystemPrefix")
        private String mcSystemPrefix = "SERVER";//游戏中系统命令发送的消息头文本

    }

    @Data
    public static class Common {
        @SerializedName("group_on")
        private boolean groupOn = true;
        @SerializedName("group_id_list")
        private Set<Long> groupIdList = new HashSet<>();//支持多个q群
        @SerializedName("guild_on")
        private boolean guildOn = false;//是否开启频道
        @SerializedName("guild_id")
        private String guildId = "";//频道id
        @SerializedName("channel_id_list")
        private Set<String> channelIdList = new HashSet<>();//子频道列表
        @SerializedName("bot_id")
        private long botId = 0;//机器人qq
        @SerializedName("master_id")
        private long masterId = 0;//主人qq
        @SerializedName("enable")
        private boolean enable = true;//是否启用
        @SerializedName("debuggable")
        private boolean debuggable = false;//是否开发模式，将显示事件信息操作
        @SerializedName("whitelist_enable")
        private boolean WHITELIST_ENABLED = false;//白名单，无用
        @SerializedName("language_select")
        private String languageSelect = "zh_cn";//选择语言系统
        @SerializedName("auto_open")
        private boolean autoOpen = true;//自动重连

        public void addChannelId(String id) {
            this.channelIdList.add(id);
        }

        public void removeChannelId(String id) {
            this.channelIdList.remove(id);
        }

        public void removeGroupId(long id) {
            this.groupIdList.remove(id);
        }

        public void addGroupId(long id) {
            this.groupIdList.add(id);
        }


    }

}
