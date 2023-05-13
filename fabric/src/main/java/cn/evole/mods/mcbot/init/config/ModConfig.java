package cn.evole.mods.mcbot.init.config;

import blue.endless.jankson.Comment;
import blue.endless.jankson.annotation.SerializedName;
import cn.evole.onebot.client.config.BotConfig;
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
    @Comment("通用")
    private Common common = new Common();
    @SerializedName("status")
    @Comment("状态")
    private Status status = new Status();
    @SerializedName("cmd")
    @Comment("命令")
    private Cmd cmd = new Cmd();
    @SerializedName("bot_config")
    @Comment("机器人")
    private BotConfig botConfig = new BotConfig();

    @Data
    public static class Status {

        //接收来自q群的消息开关
        @SerializedName("RECEIVE_ENABLED")
        @Comment("全局接收")
        private boolean RECEIVE_ENABLED = true;
        @SerializedName("R_COMMAND_ENABLED")
        @Comment("命令接收")
        private boolean R_COMMAND_ENABLED = true;
        @SerializedName("R_CHAT_ENABLE")
        @Comment("消息接收")
        private boolean R_CHAT_ENABLE = true;

        //发往q群消息的开关
        @SerializedName("SEND_ENABLED")
        @Comment("发送消息")
        private boolean SEND_ENABLED = true;
        @SerializedName("S_QQ_WELCOME_ENABLE")
        @Comment("发送欢迎玩家入群消息")
        private boolean S_QQ_WELCOME_ENABLE = true;
        @SerializedName("S_QQ_LEAVE_ENABLE")
        @Comment("发送玩家退群消息")
        private boolean S_QQ_LEAVE_ENABLE = true;
        @SerializedName("S_JOIN_ENABLE")
        @Comment("发送加入服务器消息")
        private boolean S_JOIN_ENABLE = true;
        @SerializedName("S_LEAVE_ENABLE")
        @Comment("发送离开服务器消息")
        private boolean S_LEAVE_ENABLE = true;
        @SerializedName("S_DEATH_ENABLE")
        @Comment("发送玩家死亡消息")
        private boolean S_DEATH_ENABLE = true;
        @SerializedName("S_CHAT_ENABLE")
        @Comment("发送服务器聊天")
        private boolean S_CHAT_ENABLE = true;
        @SerializedName("S_ADVANCE_ENABLE")
        @Comment("发送成就消息")
        private boolean S_ADVANCE_ENABLE = true;

    }

    @Data
    public static class Cmd {
        @SerializedName("welcome_notice")
        @Comment("自定义q群加入事件消息")
        private String welcomeNotice = "欢迎加群~";//自定义q群加入事件消息
        @SerializedName("leave_notice")
        @Comment("自定义q群离开消息")
        private String leaveNotice = "有人离开了我们qwq";//自定义q群离开消息
        @SerializedName("command_start")
        @Comment("q群中使用命令的关键符号")
        private String commandStart = "!";//q群中使用命令的关键符号
        @SerializedName("bind_command")
        @Comment("暂时没用")
        private String bindCommand = "bind";//暂时没用
        @SerializedName("whitelist_command")
        @Comment("暂时没用")
        private String whiteListCommand = "white";//暂时没用
        @SerializedName("bind_success")
        @Comment("暂时没用")
        private String bindSuccess =
                "绑定成功 ┈━═☆\n" +
                        "成功绑定账号: %Player%\n" +
                        "你他妈绑定成功了呢~\"";//暂时没用
        @SerializedName("bindFail")
        @Comment("暂时没用")
        private String bindFail =
                "绑定失败 ┈━═☆\n" +
                        "你的QQ已经绑定或 %Player% 已被绑定\n" +
                        "你他妈不能再绑定了呢~";//暂时没用
        @SerializedName("bindNotOnline")
        @Comment("暂时没用")
        private String bindNotOnline =
                "玩家不在线 ┈━═☆\n" +
                        "%Player% 不在线或者不存在哦\n" +
                        "还他妈不上线搁这玩QQ呢~";//暂时没用

        @SerializedName("gamePrefixOn")
        @Comment("####################" +
                "是否开启显示到游戏中的前缀")
        private boolean gamePrefixOn = true;//是否开启显示到游戏中的前缀
        @SerializedName("idGamePrefixOn")
        @Comment("是否开启显示到游戏中的id前缀")
        private boolean idGamePrefixOn = true;//是否开启显示到游戏中的id前缀
        @SerializedName("qqGamePrefix")
        @Comment("来自q群显示到游戏中的前缀")
        private String qqGamePrefix = "群聊";//来自q群显示到游戏中的前缀
        @SerializedName("guildGamePrefix")
        @Comment("来自频道显示到游戏中的前缀")
        private String guildGamePrefix = "频道";//来自频道显示到游戏中的前缀
        @SerializedName("groupNickOn")
        @Comment("是否开启显示到游戏中的昵称为群昵称")
        private boolean groupNickOn = false;//是否开启显示到游戏中的昵称为群昵称


        @SerializedName("####################" +
                "mcPrefixOn")
        @Comment("是否开启来自游戏的消息显示到群中的前缀")
        private boolean mcPrefixOn = true;//是否开启来自游戏的消息显示到群中的前缀
        @SerializedName("mcPrefix")
        @Comment("来自游戏的消息显示到群中的前缀")
        private String mcPrefix = "MC";//来自游戏的消息显示到群中的前缀


        @SerializedName("####################" +
                "mcChatPrefixOn")
        @Comment("是否开启游戏中自定义关键词")
        private boolean mcChatPrefixOn = false;//是否开启游戏中自定义关键词
        @SerializedName("qqChatPrefixOn")
        @Comment("是否开启qq中自定义关键词")
        private boolean qqChatPrefixOn = false;//是否开启qq中自定义关键词
        @SerializedName("mcChatPrefix")
        @Comment("游戏中自定义的消息头文本")
        private String mcChatPrefix = "q";//游戏中自定义的消息头文本
        @SerializedName("qqChatPrefix")
        @Comment("qq中自定义的消息头文本")
        private String qqChatPrefix = "m";//qq中自定义的消息头文本

        @SerializedName("####################" +
                "mcSystemPrefixOn")
        @Comment("是否开启游戏中机器人执行命令发送的消息前缀")
        private boolean mcSystemPrefixOn = true;//是否开启游戏中机器人执行命令发送的消息前缀
        @SerializedName("mcSystemPrefix")
        @Comment("游戏中机器人执行命令发送的消息前缀")
        private String mcSystemPrefix = "SERVER";//游戏中机器人执行命令发送的消息前缀

    }

    @Data
    public static class Common {
        @SerializedName("group_on")
        @Comment("开启q群功能")
        private boolean groupOn = true;
        @SerializedName("group_id_list")
        @Comment("支持多个q群")
        private Set<Long> groupIdList = new HashSet<>();//支持多个q群
        @SerializedName("guild_on")
        @Comment("是否开启频道")
        private boolean guildOn = false;//是否开启频道
        @SerializedName("guild_id")
        @Comment("频道id")
        private String guildId = "";//频道id
        @SerializedName("channel_id_list")
        @Comment("子频道列表")
        private Set<String> channelIdList = new HashSet<>();//子频道列表
        @SerializedName("bot_id")
        @Comment("机器人qq")
        private long botId = 0;//机器人qq
        @SerializedName("master_id")
        @Comment("主人qq")
        private long masterId = 0;//主人qq
        @SerializedName("enable")
        @Comment("是否启用")
        private boolean enable = true;//是否启用
        @SerializedName("debuggable")
        @Comment("是否开发模式，将显示事件信息操作")
        private boolean debuggable = false;//是否开发模式，将显示事件信息操作
        @SerializedName("whitelist_enable")
        @Comment("白名单，无用")
        private boolean WHITELIST_ENABLED = false;//白名单，无用
        @SerializedName("language_select")
        @Comment("选择语言系统")
        private String languageSelect = "zh_cn";//选择语言系统
        @SerializedName("auto_open")
        @Comment("自动连接")
        private boolean autoOpen = true;//自动连接
        @SerializedName("image_on")
        @Comment("是否开启聊天栏图片功能")
        private boolean imageOn = true;//是否开启聊天栏图片功能

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
