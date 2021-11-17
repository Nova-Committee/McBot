package cn.evolvefield.mods.botapi.message;

import cn.evolvefield.mods.botapi.util.json.JSONObject;

public class MessageJson {
    private String message_type;
    private long self_id;
    private long message_id;
    private String raw_message;
    private long group_id;
    private String nickname;
    private long user_id;
    private String sub_type;
    private String Json;
    private String post_type;
    private String notice_type;
    private String operator_id;
    private String duration;
    private String file_name;
    private String file_size;
    private String target_id;
    private String request_type;
    private String comment;
    private String flag;
    private String temp_source;
    private String font;


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
        if (this.message_type != null) {
            this.self_id = json.getLong("self_id");
            this.message_id = json.getLong("message_id");
            this.raw_message = json.getString("raw_message");
            this.group_id = json.getLong("group_id");
            this.user_id = json.getLong("user_id");
            this.sub_type = json.getString("sub_type");
            this.nickname = json.getJSONObject("sender").getString("nickname");

        }

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

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
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
