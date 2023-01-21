package cn.evolvefield.mods.botapi.init.config;

import cn.evolvefield.onebot.sdk.config.BotConfig;
import com.google.gson.annotations.SerializedName;

import java.util.HashSet;
import java.util.Set;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/2 13:44
 * Version: 1.0
 */
public class ModConfig {
    @SerializedName("common")
    private Common common = new Common();
    @SerializedName("status")
    private Status status = new Status();
    @SerializedName("cmd")
    private Cmd cmd = new Cmd();
    @SerializedName("bot_config")
    private BotConfig botConfig = new BotConfig();

    public Common getCommon() {
        return common;
    }

    public Status getStatus() {
        return status;
    }

    public Cmd getCmd() {
        return cmd;
    }

    public BotConfig getBotConfig() {
        return botConfig;
    }

    public String getConfigName() {
        return "botapi";
    }

    public static class Status {
        @SerializedName("RECEIVE_ENABLED")
        private boolean RECEIVE_ENABLED = true;
        @SerializedName("R_COMMAND_ENABLED")
        private boolean R_COMMAND_ENABLED = true;
        @SerializedName("R_CHAT_ENABLE")
        private boolean R_CHAT_ENABLE = true;
        @SerializedName("SEND_ENABLED")
        private boolean SEND_ENABLED = true;
        @SerializedName("WELCOME_ENABLE")
        private boolean S_WELCOME_ENABLE = true;
        @SerializedName("JOIN_ENABLE")
        private boolean S_JOIN_ENABLE = true;
        @SerializedName("LEAVE_ENABLE")
        private boolean S_LEAVE_ENABLE = true;
        @SerializedName("DEATH_ENABLE")
        private boolean S_DEATH_ENABLE = true;
        @SerializedName("S_CHAT_ENABLE")
        private boolean S_CHAT_ENABLE = true;
        @SerializedName("S_ADVANCE_ENABLE")
        private boolean S_ADVANCE_ENABLE = true;

        public boolean isRECEIVE_ENABLED() {
            return RECEIVE_ENABLED;
        }

        public void setRECEIVE_ENABLED(boolean RECEIVE_ENABLED) {
            this.RECEIVE_ENABLED = RECEIVE_ENABLED;
        }

        public boolean isR_COMMAND_ENABLED() {
            return R_COMMAND_ENABLED;
        }

        public void setR_COMMAND_ENABLED(boolean r_COMMAND_ENABLED) {
            R_COMMAND_ENABLED = r_COMMAND_ENABLED;
        }

        public boolean isR_CHAT_ENABLE() {
            return R_CHAT_ENABLE;
        }

        public void setR_CHAT_ENABLE(boolean r_CHAT_ENABLE) {
            R_CHAT_ENABLE = r_CHAT_ENABLE;
        }

        public boolean isSEND_ENABLED() {
            return SEND_ENABLED;
        }

        public void setSEND_ENABLED(boolean SEND_ENABLED) {
            this.SEND_ENABLED = SEND_ENABLED;
        }

        public boolean isS_WELCOME_ENABLE() {
            return S_WELCOME_ENABLE;
        }

        public void setS_WELCOME_ENABLE(boolean s_WELCOME_ENABLE) {
            S_WELCOME_ENABLE = s_WELCOME_ENABLE;
        }

        public boolean isS_JOIN_ENABLE() {
            return S_JOIN_ENABLE;
        }

        public void setS_JOIN_ENABLE(boolean s_JOIN_ENABLE) {
            S_JOIN_ENABLE = s_JOIN_ENABLE;
        }

        public boolean isS_LEAVE_ENABLE() {
            return S_LEAVE_ENABLE;
        }

        public void setS_LEAVE_ENABLE(boolean s_LEAVE_ENABLE) {
            S_LEAVE_ENABLE = s_LEAVE_ENABLE;
        }

        public boolean isS_DEATH_ENABLE() {
            return S_DEATH_ENABLE;
        }

        public void setS_DEATH_ENABLE(boolean s_DEATH_ENABLE) {
            S_DEATH_ENABLE = s_DEATH_ENABLE;
        }

        public boolean isS_CHAT_ENABLE() {
            return S_CHAT_ENABLE;
        }

        public void setS_CHAT_ENABLE(boolean s_CHAT_ENABLE) {
            S_CHAT_ENABLE = s_CHAT_ENABLE;
        }

        public boolean isS_ADVANCE_ENABLE() {
            return S_ADVANCE_ENABLE;
        }

        public void setS_ADVANCE_ENABLE(boolean s_ADVANCE_ENABLE) {
            S_ADVANCE_ENABLE = s_ADVANCE_ENABLE;
        }
    }

    public static class Cmd {
        @SerializedName("welcome_notice")
        private String welcomeNotice = "欢迎加群~";
        @SerializedName("leave_notice")
        private String leaveNotice = "有人离开了我们qwq";
        @SerializedName("command_start")
        private String commandStart = "!";
        @SerializedName("bind_command")
        private String bindCommand = "bind";
        @SerializedName("whitelist_command")
        private String whiteListCommand = "white";
        @SerializedName("bind_success")
        private String bindSuccess =
                "绑定成功 ┈━═☆\n" +
                        "成功绑定账号: %Player%\n" +
                        "你他妈绑定成功了呢~\"";
        @SerializedName("bindFail")
        private String bindFail =
                "绑定失败 ┈━═☆\n" +
                        "你的QQ已经绑定或 %Player% 已被绑定\n" +
                        "你他妈不能再绑定了呢~";
        @SerializedName("bindNotOnline")
        private String bindNotOnline =
                "玩家不在线 ┈━═☆\n" +
                        "%Player% 不在线或者不存在哦\n" +
                        "还他妈不上线搁这玩QQ呢~";

        @SerializedName("qqPrefix")
        private String qqPrefix = "群聊";
        @SerializedName("guildPrefix")
        private String guildPrefix = "频道";
        @SerializedName("mcPrefix")
        private String mcPrefix = "MC";
        @SerializedName("mcChatPrefixEnable")
        private boolean mcChatPrefixEnable = false;
        @SerializedName("qqChatPrefixEnable")
        private boolean qqChatPrefixEnable = false;
        @SerializedName("mcChatPrefix")
        private String mcChatPrefix = "q";
        @SerializedName("qqChatPrefix")
        private String qqChatPrefix = "m";

        public String getWelcomeNotice() {
            return welcomeNotice;
        }

        public void setWelcomeNotice(String welcomeNotice) {
            this.welcomeNotice = welcomeNotice;
        }

        public String getLeaveNotice() {
            return leaveNotice;
        }

        public void setLeaveNotice(String leaveNotice) {
            this.leaveNotice = leaveNotice;
        }

        public String getCommandStart() {
            return commandStart;
        }

        public void setCommandStart(String commandStart) {
            this.commandStart = commandStart;
        }

        public String getBindCommand() {
            return bindCommand;
        }

        public void setBindCommand(String bindCommand) {
            this.bindCommand = bindCommand;
        }

        public String getWhiteListCommand() {
            return whiteListCommand;
        }

        public void setWhiteListCommand(String whiteListCommand) {
            this.whiteListCommand = whiteListCommand;
        }

        public String getBindSuccess() {
            return bindSuccess;
        }

        public void setBindSuccess(String bindSuccess) {
            this.bindSuccess = bindSuccess;
        }

        public String getBindFail() {
            return bindFail;
        }

        public void setBindFail(String bindFail) {
            this.bindFail = bindFail;
        }

        public String getBindNotOnline() {
            return bindNotOnline;
        }

        public void setBindNotOnline(String bindNotOnline) {
            this.bindNotOnline = bindNotOnline;
        }

        public String getQqPrefix() {
            return qqPrefix;
        }

        public void setQqPrefix(String qqPrefix) {
            this.qqPrefix = qqPrefix;
        }

        public String getGuildPrefix() {
            return guildPrefix;
        }

        public void setGuildPrefix(String guildPrefix) {
            this.guildPrefix = guildPrefix;
        }

        public String getMcPrefix() {
            return mcPrefix;
        }

        public void setMcPrefix(String mcPrefix) {
            this.mcPrefix = mcPrefix;
        }

        public boolean isMcChatPrefixEnable() {
            return mcChatPrefixEnable;
        }

        public void setMcChatPrefixEnable(boolean mcChatPrefixEnable) {
            this.mcChatPrefixEnable = mcChatPrefixEnable;
        }

        public boolean isQqChatPrefixEnable() {
            return qqChatPrefixEnable;
        }

        public void setQqChatPrefixEnable(boolean qqChatPrefixEnable) {
            this.qqChatPrefixEnable = qqChatPrefixEnable;
        }

        public String getMcChatPrefix() {
            return mcChatPrefix;
        }

        public void setMcChatPrefix(String mcChatPrefix) {
            this.mcChatPrefix = mcChatPrefix;
        }

        public String getQqChatPrefix() {
            return qqChatPrefix;
        }

        public void setQqChatPrefix(String qqChatPrefix) {
            this.qqChatPrefix = qqChatPrefix;
        }
    }

    public static class Common {
        @SerializedName("group_on")
        private boolean groupOn = true;
        @SerializedName("group_id_list")
        private Set<Long> groupIdList = new HashSet<>();
        @SerializedName("guild_on")
        private boolean guildOn = false;
        @SerializedName("guild_id")
        private String guildId = "";
        @SerializedName("channel_id_list")
        private Set<String> channelIdList = new HashSet<>();
        @SerializedName("bot_id")
        private long botId = 0;
        @SerializedName("master_id")
        private long masterId = 0;
        @SerializedName("enable")
        private boolean enable = true;
        @SerializedName("debuggable")
        private boolean debuggable = false;
        @SerializedName("whitelist_enable")
        private boolean WHITELIST_ENABLED = false;
        @SerializedName("language_select")
        private String languageSelect = "zh_cn";
        @SerializedName("auto_open")
        private boolean autoOpen = true;

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

        public boolean isGroupOn() {
            return groupOn;
        }

        public void setGroupOn(boolean groupOn) {
            this.groupOn = groupOn;
        }

        public Set<Long> getGroupIdList() {
            return groupIdList;
        }

        public void setGroupIdList(Set<Long> groupIdList) {
            this.groupIdList = groupIdList;
        }

        public boolean isGuildOn() {
            return guildOn;
        }

        public void setGuildOn(boolean guildOn) {
            this.guildOn = guildOn;
        }

        public String getGuildId() {
            return guildId;
        }

        public void setGuildId(String guildId) {
            this.guildId = guildId;
        }

        public Set<String> getChannelIdList() {
            return channelIdList;
        }

        public void setChannelIdList(Set<String> channelIdList) {
            this.channelIdList = channelIdList;
        }

        public long getBotId() {
            return botId;
        }

        public void setBotId(long botId) {
            this.botId = botId;
        }

        public long getMasterId() {
            return masterId;
        }

        public void setMasterId(long masterId) {
            this.masterId = masterId;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public boolean isDebuggable() {
            return debuggable;
        }

        public void setDebuggable(boolean debuggable) {
            this.debuggable = debuggable;
        }

        public boolean isWHITELIST_ENABLED() {
            return WHITELIST_ENABLED;
        }

        public void setWHITELIST_ENABLED(boolean WHITELIST_ENABLED) {
            this.WHITELIST_ENABLED = WHITELIST_ENABLED;
        }

        public String getLanguageSelect() {
            return languageSelect;
        }

        public void setLanguageSelect(String languageSelect) {
            this.languageSelect = languageSelect;
        }

        public boolean isAutoOpen() {
            return autoOpen;
        }

        public void setAutoOpen(boolean autoOpen) {
            this.autoOpen = autoOpen;
        }
    }

}
