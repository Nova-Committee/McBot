package cn.evolvefield.mods.botapi.api.events;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/18 18:44
 * Version: 1.0
 */
public class NoticeEvent {
    private final String Json;//原始文本
    private long self_id;//机器人qq
    private String sub_type;//事件子类型
    private String notice_type;//通知类型
    private final long group_id;//事件触发群
    private final long user_id;//事件触发人QQ
    private long operator_id;//操作者QQ
    private String duration;//被禁言时长
    private long message_id;//消息ID、被撤回的消息ID
    private String file_name;//上传文件名字
    private long file_size;//上传文件大小
    private String target_id;//被戳、运气王QQ


    private String mirai_type;//mirai事件类型

    public NoticeEvent(String Json, long self_id, String sub_type, String notice_type, long group_id, long user_id, long operator_id) {
        this.Json = Json;
        this.self_id = self_id;
        this.sub_type = sub_type;
        this.notice_type = notice_type;
        this.group_id = group_id;
        this.user_id = user_id;
        this.operator_id = operator_id;
    }

    public NoticeEvent(String Json, long self_id, String sub_type, String notice_type, long group_id, long user_id, long operator_id, String duration, String message_id, String file_name, long file_size, String target_id) {
        this.Json = Json;
        this.self_id = self_id;
        this.sub_type = sub_type;
        this.notice_type = notice_type;
        this.group_id = group_id;
        this.user_id = user_id;
        this.operator_id = operator_id;
        this.duration = duration;
        this.file_name = file_name;
        this.file_size = file_size;
        this.target_id = target_id;
    }

    public long getGroup_id() {
        return group_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public String getJson() {
        return this.Json;
    }

    public long getSelfId() {
        return this.self_id;
    }

    public String getSubType() {
        return this.sub_type;
    }

    public String getNoticeType() {
        return this.notice_type;
    }

    public long getOperatorId() {
        return this.operator_id;
    }

    public String getDuration() {
        return this.duration;
    }

    public long getMessageId() {
        return this.message_id;
    }

    public String getFile_Name() {
        return this.file_name;
    }

    public long getFile_Size() {
        return this.file_size;
    }

    public String getTargetId() {
        return this.target_id;
    }
}
