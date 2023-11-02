package cn.evole.mods.mcbot.init.config;

import cn.evole.config.YmlConfig;
import cn.evole.config.api.ConfigComments;
import cn.evole.config.api.ConfigField;
import cn.evole.config.yaml.serialization.ConfigurationSerializable;
import cn.evole.onebot.client.config.BotConfig;
import lombok.Data;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/2 13:44
 * Version: 1.0
 */
@Getter
public class ModConfig extends YmlConfig {
    public ModConfig(File folder) {
        super(folder.getAbsolutePath() + File.separator + "config.yml");
    }

    @ConfigField("common")
    @ConfigComments("通用")
    private Common common = new Common();
    @ConfigField("status")
    @ConfigComments("状态")
    private Status status = new Status();
    @ConfigField("cmd")
    @ConfigComments("命令")
    private Cmd cmd = new Cmd();
    @ConfigField("bot_config")
    @ConfigComments("机器人")
    private Bot botConfig = new Bot();


    public static class Bot extends BotConfig implements ConfigurationSerializable{

        @Override
        public @NotNull Map<String, Object> serialize() {
            Map<String, Object> serialize = new HashMap<>();

            serialize.put("url", this.getUrl());
            serialize.put("token", this.getToken());
            serialize.put("botId", this.getBotId());
            serialize.put("isAccessToken", this.isAccessToken());
            serialize.put("miraiHttp", this.isMiraiHttp());
            serialize.put("reconnect", this.isReconnect());
            serialize.put("maxReconnectAttempts", this.getMaxReconnectAttempts());

            return serialize;
        }
    }

    @Data
    public static class Status implements ConfigurationSerializable {

        //接收来自q群的消息开关
        //接收来自q群的消息开关
        //////@SerializedName("RECEIVE_ENABLED")
        ////@Comment("全局接收")
        private boolean rEnable = true;
        //////@SerializedName("R_COMMAND_ENABLED")
        ////@Comment("命令接收")
        private boolean rCmdEnable = true;
        //////@SerializedName("R_CHAT_ENABLE")
        ////@Comment("消息接收")
        private boolean rChatEnable = true;

        //发往q群消息的开关
        //////@SerializedName("SEND_ENABLED")
        ////@Comment("发送消息")
        private boolean sEnable = true;
        //////@SerializedName("S_QQ_WELCOME_ENABLE")
        ////@Comment("发送欢迎玩家入群消息")
        private boolean sQqWelcomeEnable = true;
        //////@SerializedName("S_QQ_LEAVE_ENABLE")
        ////@Comment("发送玩家退群消息")
        private boolean sQqLeaveEnable = true;
        //////@SerializedName("S_JOIN_ENABLE")
        ////@Comment("发送加入服务器消息")
        private boolean sJoinEnable = true;
        //////@SerializedName("S_LEAVE_ENABLE")
        ////@Comment("发送离开服务器消息")
        private boolean sLeaveEnable = true;
        //////@SerializedName("S_DEATH_ENABLE")
        ////@Comment("发送玩家死亡消息")
        private boolean sDeathEnable = true;
        //////@SerializedName("S_CHAT_ENABLE")
        ////@Comment("发送服务器聊天")
        private boolean sChatEnable = true;
        //////@SerializedName("S_ADVANCE_ENABLE")
        ////@Comment("发送成就消息")
        private boolean sAdvanceEnable = true;

        @Override
        public @NotNull Map<String, Object> serialize() {
            Map<String, Object> serialize = new HashMap<>();

            serialize.put("rEnable", this.rEnable);
            serialize.put("rCmdEnable", this.rCmdEnable);
            serialize.put("sEnable", this.sEnable);
            serialize.put("sQqWelcomeEnable", this.sQqWelcomeEnable);
            serialize.put("sQqLeaveEnable", this.sQqLeaveEnable);
            serialize.put("sJoinEnable", this.sJoinEnable);
            serialize.put("sLeaveEnable", this.sLeaveEnable);
            serialize.put("sDeathEnable", this.sDeathEnable);
            serialize.put("sChatEnable", this.sChatEnable);
            serialize.put("sAdvanceEnable", this.sAdvanceEnable);

            return serialize;
        }
    }

    @Data
    public static class Cmd implements ConfigurationSerializable {
        ////@SerializedName("welcome_notice")
        //@Comment("自定义q群加入事件消息")
        private String welcomeNotice = "欢迎加群~";//自定义q群加入事件消息
        ////@SerializedName("leave_notice")
        //@Comment("自定义q群离开消息")
        private String leaveNotice = "离开了我们qwq";//自定义q群离开消息
        ////@SerializedName("command_start")
        //@Comment("q群中使用命令的关键符号")
        private String cmdStart = "!";//q群中使用命令的关键符号

        ////@SerializedName("gamePrefixOn")
        //@Comment("####################\n" +
        //"是否开启显示到游戏中的前缀")
        private boolean gamePrefixOn = true;//是否开启显示到游戏中的前缀
        ////@SerializedName("idGamePrefixOn")
        //@Comment("是否开启显示到游戏中的id前缀")
        private boolean idGamePrefixOn = true;//是否开启显示到游戏中的id前缀
        ////@SerializedName("qqGamePrefix")
        //@Comment("来自q群显示到游戏中的前缀")
        private String qqGamePrefix = "群聊";//来自q群显示到游戏中的前缀
        ////@SerializedName("guildGamePrefix")
        //@Comment("来自频道显示到游戏中的前缀")
        private String guildGamePrefix = "频道";//来自频道显示到游戏中的前缀
        ////@SerializedName("groupNickOn")
        //@Comment("是否开启显示到游戏中的昵称为群昵称")
        private boolean groupNickOn = false;//是否开启显示到游戏中的昵称为群昵称


        ////@SerializedName(
        // "mcPrefixOn")
        //@Comment("####################\n" +
        // "是否开启来自游戏的消息显示到群中的前缀")
        private boolean mcPrefixOn = true;//是否开启来自游戏的消息显示到群中的前缀
        ////@SerializedName("mcPrefix")
        //@Comment("来自游戏的消息显示到群中的前缀")
        private String mcPrefix = "MC";//来自游戏的消息显示到群中的前缀


        ////@SerializedName(
        //"mcChatPrefixOn")
        //@Comment("####################\n" +
        //"是否开启游戏中自定义关键词")
        private boolean mcChatPrefixOn = false;//是否开启游戏中自定义关键词
        ////@SerializedName("qqChatPrefixOn")
        //@Comment("是否开启qq中自定义关键词")
        private boolean qqChatPrefixOn = false;//是否开启qq中自定义关键词
        ////@SerializedName("mcChatPrefix")
        //@Comment("游戏中自定义的消息头文本")
        private String mcChatPrefix = "q";//游戏中自定义的消息头文本
        ////@SerializedName("qqChatPrefix")
        //@Comment("qq中自定义的消息头文本")
        private String qqChatPrefix = "m";//qq中自定义的消息头文本

        @Override
        public @NotNull Map<String, Object> serialize() {
            Map<String, Object> serialize = new HashMap<>();

            serialize.put("welcomeNotice", this.welcomeNotice);
            serialize.put("leaveNotice", this.leaveNotice);
            serialize.put("cmdStart", this.cmdStart);

            serialize.put("gamePrefixOn", this.gamePrefixOn);
            serialize.put("idGamePrefixOn", this.idGamePrefixOn);
            serialize.put("qqGamePrefix", this.qqGamePrefix);
            serialize.put("guildGamePrefix", this.guildGamePrefix);
            serialize.put("groupNickOn", this.groupNickOn);

            serialize.put("mcPrefixOn", this.mcPrefixOn);
            serialize.put("mcPrefix", this.mcPrefix);

            serialize.put("mcChatPrefixOn", this.mcChatPrefixOn);
            serialize.put("qqChatPrefixOn", this.qqChatPrefixOn);
            serialize.put("mcChatPrefix", this.mcChatPrefix);
            serialize.put("qqChatPrefix", this.qqChatPrefix);

            return serialize;
        }
    }

    @Data
    public static class Common implements ConfigurationSerializable {
        //@SerializedName("group_on")
        //@Comment("开启q群功能")
        private boolean groupOn = true;
        //@SerializedName("group_id_list")
        //@Comment("支持多个q群")
        private Set<Long> groupIdList = new HashSet<>();//支持多个q群
        //@SerializedName("guild_on")
        //@Comment("是否开启频道")
        private boolean guildOn = false;//是否开启频道
        //@SerializedName("guild_id")
        //@Comment("频道id")
        private String guildId = "";//频道id
        //@SerializedName("channel_id_list")
        //@Comment("子频道列表")
        private Set<String> channelIdList = new HashSet<>();//子频道列表
        //@SerializedName("bot_id")
        //@Comment("机器人qq")
        private long botId = 0;//机器人qq
        //@SerializedName("enable")
        //@Comment("是否启用")
        private boolean enable = true;//是否启用
        //@SerializedName("debuggable")
        //@Comment("是否开发模式，将显示事件信息操作")
        private boolean debug = false;//是否开发模式，将显示事件信息操作
         //@SerializedName("language_select")
        //@Comment("选择语言系统")
        private String languageSelect = "zh_cn";//选择语言系统
        //@SerializedName("auto_open")
        //@Comment("自动连接")
        private boolean autoOpen = true;//自动连接
        //@SerializedName("image_on")
        //@Comment("是否开启聊天栏图片功能")
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

        @Override
        public @NotNull Map<String, Object> serialize() {
            Map<String, Object> serialize = new HashMap<>();

            serialize.put("groupOn", this.groupOn);
            serialize.put("groupIdList", this.groupIdList);
            serialize.put("guildOn", this.guildOn);
            serialize.put("guildId", this.guildId);
            serialize.put("channelIdList", this.channelIdList);
            serialize.put("botId", this.botId);
            serialize.put("enable", this.enable);
            serialize.put("debuggable", this.debug);
            serialize.put("languageSelect", this.languageSelect);
            serialize.put("autoOpen", this.autoOpen);
            serialize.put("imageOn", this.imageOn);

            return serialize;
        }
    }

}
