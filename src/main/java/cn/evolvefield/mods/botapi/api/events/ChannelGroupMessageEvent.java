package cn.evolvefield.mods.botapi.api.events;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/23 20:38
 * Version: 1.0
 */
public class ChannelGroupMessageEvent {
    private String guild_id;//频道号
    private String channel_id;//子频道号
    private String tiny_id;//发送人id

    private String nickname;//发送人昵称
    private String json;//消息原始文本
    private String self_tiny_id;//机器人qq
    private String message_id;//收到消息ID
    private String message;//收到消息
    private String sub_type;//消息子类型

    public ChannelGroupMessageEvent(String json, String sub_type, String guild_id, String channel_id, String self_tiny_id, String tiny_id, String nickname, String message) {
        this.json = json;
        this.sub_type = sub_type;
        this.guild_id = guild_id;
        this.channel_id = channel_id;
        this.self_tiny_id = self_tiny_id;
        this.tiny_id = tiny_id;
        this.nickname = nickname;
        this.message = message;
    }


    public String getGuild_id() {
        return guild_id;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public String getTiny_id() {
        return tiny_id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getJson() {
        return json;
    }

    public String getSelf_tiny_id() {
        return self_tiny_id;
    }


    public String getMessage() {
        return message;
    }

    public String getSub_type() {
        return sub_type;
    }
}
