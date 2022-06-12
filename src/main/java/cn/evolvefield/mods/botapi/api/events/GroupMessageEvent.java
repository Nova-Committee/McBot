package cn.evolvefield.mods.botapi.api.events;

import cn.evolvefield.mods.botapi.api.message.MiraiMessage;

import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/18 18:40
 * Version: 1.0
 */
public class GroupMessageEvent {
    private final long group_id;//消息群号
    private final String nickname;//发送人昵称
    private final long user_id;//发送人qq
    private final String json;//消息原始文本
    private long self_id;//机器人qq
    private long message_id;//收到消息ID
    private String message;//收到消息
    private String role;//cqhttp角色权限
    private String sub_type;//消息子类型
    private String group_name;//群名称
    private List<MiraiMessage> raw_message;//Mirai消息链
    private String permission;//Mirai角色权限

    //cq-http框架触发事件
    public GroupMessageEvent(String json, long self_id, String message, long group_id, String nickname, String role, long user_id, String sub_type) {
        this.self_id = self_id;
        this.message = message;
        this.group_id = group_id;
        this.nickname = nickname;
        this.role = role;
        this.user_id = user_id;
        this.sub_type = sub_type;
        this.json = json;
    }

    //Mirai框架触发事件
    public GroupMessageEvent(String json, List<MiraiMessage> raw_message, long user_id, String permission, String memberName, long group_id, String group_name) {
        this.json = json;
        this.raw_message = raw_message;
        this.user_id = user_id;
        this.nickname = memberName;
        this.group_id = group_id;
        this.permission = permission;
        this.group_name = group_name;
    }


    //getter
    public String getGroupName() {
        return group_name;
    }

    public List<MiraiMessage> getMiraiMessage() {
        return raw_message;
    }

    public String getMessage() {
        return this.message;
    }

    public long getSelfId() {
        return this.self_id;
    }

    public long getGroupId() {
        return this.group_id;
    }

    public String getNickName() {
        return this.nickname;
    }

    public String getRole() {
        return role;
    }

    public long getUserId() {
        return this.user_id;
    }

    public String getPermission() {
        return permission;
    }

    public String getSubType() {
        return this.sub_type;
    }

    public String getJson() {
        return this.json;
    }
}
