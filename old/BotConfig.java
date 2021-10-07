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
        @SerializedName("RECEIVE_ENABLED")
        private boolean RECEIVE_ENABLED = true;
        @SerializedName("SEND_ENABLED")
        private boolean SEND_ENABLED = true;


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

        public boolean isRECEIVE_ENABLED() {
            return RECEIVE_ENABLED;
        }

        public void setRECEIVE_ENABLED(boolean RECEIVE_ENABLED) {
            this.RECEIVE_ENABLED = RECEIVE_ENABLED;
        }

        public boolean isSEND_ENABLED() {
            return SEND_ENABLED;
        }

        public void setSEND_ENABLED(boolean SEND_ENABLED) {
            this.SEND_ENABLED = SEND_ENABLED;
        }
    }


}
