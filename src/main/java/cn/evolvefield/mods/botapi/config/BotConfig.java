package cn.evolvefield.mods.botapi.config;

import com.google.gson.annotations.SerializedName;

public class BotConfig implements IConfig{
    @Override
    public String getConfigName() {
        return "botapi";
    }



    public Common getCommon() {
        return common;
    }

    public void setCommon(Common common) {
        this.common = common;
    }

    @SerializedName("common")
    private Common common = new Common();


    public static class Common {
        @SerializedName("groupId")
        private long groupId = 0;
        @SerializedName("wsHOST")
        private String wsHOST = "127.0.0.1";
        @SerializedName("wsPORT")
        private int wsPORT = 6700;
        @SerializedName("KEY")
        private String KEY = "";
        @SerializedName("ENABLED")
        private boolean ENABLED = true;
        @SerializedName("sendHOST")
        private String sendHOST = "127.0.0.1";
        @SerializedName("sendPORT")
        private int sendPORT = 5700;
        @SerializedName("debuggable")
        private boolean debuggable = false;

        @SerializedName("RECEIVE_ENABLED")
        private boolean RECEIVE_ENABLED = true;
        @SerializedName("R_COMMAND_ENABLED")
        private boolean R_COMMAND_ENABLED = true;
        @SerializedName("R_CHAT_ENABLE")
        private boolean R_CHAT_ENABLE = true;
        @SerializedName("SEND_ENABLED")
        private boolean SEND_ENABLED = true;
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

        public long getGroupId() {
            return groupId;
        }

        public void setGroupId(long groupId) {
            this.groupId = groupId;
        }

        public int getWsPORT() {
            return wsPORT;
        }

        public void setWsPORT(int wsPORT) {
            this.wsPORT = wsPORT;
        }

        public String getWsHOST() {
            return wsHOST;
        }

        public void setWsHOST(String wsHOST) {
            this.wsHOST = wsHOST;
        }

        public String getKEY() {
            return KEY;
        }

        public void setKEY(String KEY) {
            this.KEY = KEY;
        }

        public boolean isENABLED() {
            return ENABLED;
        }

        public void setENABLED(boolean ENABLED) {
            this.ENABLED = ENABLED;
        }

        public String getSendHOST() {
            return sendHOST;
        }

        public void setSendHOST(String sendHOST) {
            this.sendHOST = sendHOST;
        }

        public int getSendPORT() {
            return sendPORT;
        }

        public void setSendPORT(int sendPORT) {
            this.sendPORT = sendPORT;
        }

        public boolean isDebuggable() {
            return debuggable;
        }

        public void setDebuggable(boolean debuggable) {
            this.debuggable = debuggable;
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


}
