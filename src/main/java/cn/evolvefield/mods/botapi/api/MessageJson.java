package cn.evolvefield.mods.botapi.api;

import cn.evolvefield.mods.botapi.util.json.JSONObject;

public class MessageJson {
    private String post_type;//上报类型

    private String message_type;//消息类型

    /**
     * 消息子类型,
     * message类型--normal、anonymous、notice--正常消息是 normal, 匿名消息是 anonymous, 系统提示 ( 如「管理员已禁止群内匿名聊天」 ) 是 notice
     * notice类型--group_increase--approve、invite 分别表示管理员已同意入群、管理员邀请入群
     * notice类型--group_ban--ban、lift_ban 分别表示禁言、解除禁言
     */
    private String sub_type;

    private String notice_type;//通知类型

    private String request_type;

    private long self_id;//机器人qq id
    private long group_id;//消息来源的群id
    private String nickname;//发送者的昵称
    private long user_id;//发送者的qq id

    private long message_id;//消息id
    private String raw_message;//原始消息内容
    private String message;//消息内容

    private String font;

    private String target_id;//被戳者 QQ 号
    private long operator_id;//操作者 QQ 号


    private String Json;
    private String duration;
    private String file_name;
    private String file_size;
    private String comment;
    private String flag;
    private String temp_source;


    public MessageJson(String msg){
        JSONObject json = new JSONObject(msg);
        this.Json = msg;
        if (this.Json.contains("post_type")) {
            if (json.getString("post_type").equalsIgnoreCase("meta_event")) {
                return;
            }
            this.post_type = json.getString("post_type");
        } else {
            this.post_type = null;
        }

        if (this.Json.contains("message_type")) {
            this.message_type = json.getString("message_type");
        } else {
            this.message_type = null;
        }
        if (this.Json.contains("notice_type")) {
            this.notice_type = json.getString("notice_type");
        } else {
            this.notice_type = null;
        }

        if (this.post_type != null)
            switch (this.post_type){
                case "message":
                    if (this.message_type != null) {
                        switch (this.message_type){
                            case  "group":
                                this.post_type = json.getString("post_type");
                                this.message_type = json.getString("message_type");
                                this.sub_type = json.getString("sub_type");
                                this.self_id = json.getLong("self_id");
                                this.message_id = json.getInt("message_id");
                                this.group_id = json.getLong("group_id");
                                this.user_id = json.getLong("user_id");
                                this.message = json.getString("message");
                                this.raw_message = json.getString("raw_message");
                                this.nickname = json.getJSONObject("sender").getString("nickname");
                                break;
                            case  "private":
                                this.post_type = json.getString("post_type");
                                this.message_type = json.getString("message_type");
                                this.self_id = json.getLong("self_id");
                                this.message_id = json.getInt("message_id");
                                this.user_id = json.getLong("user_id");
                                this.message = json.getString("message");
                                this.raw_message = json.getString("raw_message");
                                this.nickname = json.getJSONObject("sender").getString("nickname");
                                break;
                        }
                    }
                    break;
                case "notice":
                    if (this.notice_type != null) {
                        switch (this.notice_type){
                            case  "group_increase":
                            case  "group_decrease":
                                this.post_type = json.getString("post_type");
                                this.notice_type = json.getString("notice_type");
                                this.sub_type = json.getString("sub_type");
                                this.self_id = json.getLong("self_id");
                                this.group_id = json.getLong("group_id");
                                this.user_id = json.getLong("user_id");
                                this.operator_id = json.getLong("operator_id");
                                break;
                            case  "group_admin":
                                this.post_type = json.getString("post_type");
                                this.notice_type = json.getString("notice_type");
                                this.sub_type = json.getString("sub_type");
                                this.self_id = json.getLong("self_id");
                                this.group_id = json.getLong("group_id");
                                this.user_id = json.getLong("user_id");
                                break;
                        }
                    }
                    break;
                case "request":
                    break;
            }


    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public long getSelf_id() {
        return self_id;
    }

    public void setSelf_id(long self_id) {
        this.self_id = self_id;
    }

    public long getMessage_id() {
        return message_id;
    }

    public void setMessage_id(long message_id) {
        this.message_id = message_id;
    }

    public String getRaw_message() {
        return raw_message;
    }

    public void setRaw_message(String raw_message) {
        this.raw_message = raw_message;
    }

    public long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getSub_type() {
        return sub_type;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }

    public String getJson() {
        return Json;
    }

    public void setJson(String json) {
        Json = json;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public String getNotice_type() {
        return notice_type;
    }

    public void setNotice_type(String notice_type) {
        this.notice_type = notice_type;
    }

    public long getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(long operator_id) {
        this.operator_id = operator_id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFile_name() {
        return file_name;
    }

    public String getFile_size() {
        return file_size;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    public String getTarget_id() {
        return target_id;
    }

    public void setTarget_id(String target_id) {
        this.target_id = target_id;
    }

    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getTemp_source() {
        return temp_source;
    }

    public void setTemp_source(String temp_source) {
        this.temp_source = temp_source;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }
}
