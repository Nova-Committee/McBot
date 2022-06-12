package cn.evolvefield.mods.botapi.common.config;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BotConfig {

    public String getConfigName() {
        return "botapi";
    }

    public Common getCommon() {
        return common;
    }

    public void setCommon(Common common) {
        this.common = common;
    }

    @SerializedName("mirai")
    private Mirai mirai = new Mirai();
    @SerializedName("status")
    private Status status = new Status();
    @SerializedName("cmd")
    private Cmd cmd = new Cmd();

    public Mirai getMirai() {
        return mirai;
    }

    public void setMirai(Mirai mirai) {
        this.mirai = mirai;
    }

    public Status getStatus() {
        return status;
    }


    @SerializedName("common")
    private Common common = new Common();

    public void setStatus(Status status) {
        this.status = status;
    }

    public Cmd getCmd() {
        return cmd;
    }

    public void setCmd(Cmd cmd) {
        this.cmd = cmd;
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

        public boolean isS_WELCOME_ENABLE() {
            return S_WELCOME_ENABLE;
        }

        public void setS_WELCOME_ENABLE(boolean s_WELCOME_ENABLE) {
            S_WELCOME_ENABLE = s_WELCOME_ENABLE;
        }

        public boolean isRECEIVE_ENABLED() {
            return RECEIVE_ENABLED;
        }

        public void setRECEIVE_ENABLED(boolean RECEIVE_ENABLED) {
            this.RECEIVE_ENABLED = RECEIVE_ENABLED;
        }

        public boolean isR_CHAT_ENABLE() {
            return R_CHAT_ENABLE;
        }

        public void setR_CHAT_ENABLE(boolean r_CHAT_ENABLE) {
            R_CHAT_ENABLE = r_CHAT_ENABLE;
        }

        public boolean isR_COMMAND_ENABLED() {
            return R_COMMAND_ENABLED;
        }

        public void setR_COMMAND_ENABLED(boolean r_COMMAND_ENABLED) {
            R_COMMAND_ENABLED = r_COMMAND_ENABLED;
        }

        public boolean isSEND_ENABLED() {
            return SEND_ENABLED;
        }

        public void setSEND_ENABLED(boolean SEND_ENABLED) {
            this.SEND_ENABLED = SEND_ENABLED;
        }

        public boolean isS_JOIN_ENABLE() {
            return S_JOIN_ENABLE;
        }

        public void setS_JOIN_ENABLE(boolean s_JOIN_ENABLE) {
            this.S_JOIN_ENABLE = s_JOIN_ENABLE;
        }

        public boolean isS_LEAVE_ENABLE() {
            return S_LEAVE_ENABLE;
        }

        public void setS_LEAVE_ENABLE(boolean s_LEAVE_ENABLE) {
            this.S_LEAVE_ENABLE = s_LEAVE_ENABLE;
        }

        public boolean isS_DEATH_ENABLE() {
            return S_DEATH_ENABLE;
        }

        public void setS_DEATH_ENABLE(boolean s_DEATH_ENABLE) {
            this.S_DEATH_ENABLE = s_DEATH_ENABLE;
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

    public static class Mirai {
        @SerializedName("verify_key")
        private String VerifyKey = "";
        @SerializedName("session_key")
        private String SessionKey = "";
        @SerializedName("ws_mirai")
        private String wsMirai = "ws://127.0.0.1:8080";

        public String getSessionKey() {
            return SessionKey;
        }

        public void setSessionKey(String sessionKey) {
            SessionKey = sessionKey;
        }

        public String getVerifyKey() {
            return VerifyKey;
        }

        public void setVerifyKey(String verifyKey) {
            VerifyKey = verifyKey;
        }

        public String getWsMirai() {
            return wsMirai;
        }

        public void setWsMirai(String wsMirai) {
            this.wsMirai = wsMirai;
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
        private String qqPrefix = "MC";


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

        public String getBindFail() {
            return bindFail;
        }

        public String getBindNotOnline() {
            return bindNotOnline;
        }

        public String getBindSuccess() {
            return bindSuccess;
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

        public String getQqPrefix() {
            return qqPrefix;
        }

        public void setQqPrefix(String qqPrefix) {
            this.qqPrefix = qqPrefix;
        }

        public String getCommandStart() {
            return commandStart;
        }

        public void setCommandStart(String commandStart) {
            this.commandStart = commandStart;
        }
    }

    public static class Common {
        @SerializedName("frame")
        private String frame = "cqhttp";//go-cqhttp///mirai
        @SerializedName("guild_on")
        private boolean guildOn = false;
        @SerializedName("guild_id")
        private String guildId = "";
        @SerializedName("channel_id_list")
        private List<String> channelIdList = new ArrayList<>();
        @SerializedName("group_id")
        private long groupId = 0;
        @SerializedName("bot_id")
        private long botId = 0;
        @SerializedName("master_id")
        private long masterId = 0;
        @SerializedName("ws_common")
        private String wsCommon = "ws://127.0.0.1:6700";
        @SerializedName("ws_Key")
        private String wsKey = "";
        @SerializedName("enable")
        private boolean Enable = true;
        @SerializedName("debuggable")
        private boolean Debuggable = false;
        @SerializedName("SQL_ENABLED")
        private boolean SQL_ENABLED = false;
        @SerializedName("whitelist_ENABLED")
        private boolean WHITELIST_ENABLED = false;
        @SerializedName("language_select")
        private String languageSelect = "zh_cn";
        @SerializedName("auto_open")
        private boolean autoOpen = true;

        public String getFrame() {
            return frame;
        }

        public void setFrame(String frame) {
            this.frame = frame;
        }

        public long getBotId() {
            return botId;
        }

        public void setBotId(long botId) {
            this.botId = botId;
        }

        public long getGroupId() {
            return groupId;
        }

        public void setGroupId(long groupId) {
            this.groupId = groupId;
        }

        public String getGuildId() {
            return guildId;
        }

        public void setGuildId(String guildId) {
            this.guildId = guildId;
        }

        public List<String> getChannelIdList() {
            return channelIdList;
        }

        public void setChannelIdList(List<String> channelIdList) {
            this.channelIdList = channelIdList;
        }

        public void removeChannelId(String channelId) {
            this.channelIdList.remove(channelId);
        }

        public void addChannelId(String channelId) {
            this.channelIdList.add(channelId);
        }

        public long getMasterId() {
            return masterId;
        }

        public void setMasterId(long masterId) {
            this.masterId = masterId;
        }

        public boolean isGuildOn() {
            return guildOn;
        }

        public void setGuildOn(boolean guildOn) {
            this.guildOn = guildOn;
        }

        public boolean isAutoOpen() {
            return autoOpen;
        }

        public void setAutoOpen(boolean autoOpen) {
            this.autoOpen = autoOpen;
        }

        public String getWsCommon() {
            return wsCommon;
        }

        public void setWsCommon(String wsCommon) {
            this.wsCommon = wsCommon;
        }

        public String getWsKey() {
            return wsKey;
        }

        public void setWsKey(String wsKey) {
            this.wsKey = wsKey;
        }

        public boolean isEnable() {
            return Enable;
        }

        public void setEnable(boolean enable) {
            this.Enable = enable;
        }

        public boolean isDebuggable() {
            return Debuggable;
        }

        public void setDebuggable(boolean debuggable) {
            this.Debuggable = debuggable;
        }


        public String getLanguageSelect() {
            return languageSelect;
        }

        public void setLanguageSelect(String languageSelect) {
            this.languageSelect = languageSelect;
        }

        public boolean isSQL_ENABLED() {
            return SQL_ENABLED;
        }

        public boolean isWHITELIST_ENABLED() {
            return WHITELIST_ENABLED;
        }


    }


}
