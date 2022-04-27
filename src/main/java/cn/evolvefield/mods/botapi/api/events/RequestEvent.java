package cn.evolvefield.mods.botapi.api.events;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/18 18:46
 * Version: 1.0
 */
public class RequestEvent {
	private final String Json;//原始文本
	private final long self_id;//机器人qq
	private final long user_id;//请求人QQ
	private final String request_type;//请求类型
	private final String comment;//验证消息
	private final String flag;//请求flag
	private final long group_id;//请求群号
	private final String sub_type;//事件子类型

	public RequestEvent(String Json, long self_id, long user_id, String request_type, String comment, String flag, long group_id, String sub_type) {
		this.Json = Json;
		this.self_id = self_id;
		this.user_id = user_id;
		this.request_type = request_type;
		this.comment = comment;
		this.flag = flag;
		this.group_id = group_id;
		this.sub_type = sub_type;
	}

	public String getJson() {
		return this.Json;
	}

	public long getSelfId() {
		return this.self_id;
	}

	public long getUserId() {
		return this.user_id;
	}

	public String getRequestType() {
		return this.request_type;
	}

	public String getComment() {
		return this.comment;
	}

	public String getFlag() {
		return this.flag;
	}

	public long getGroupId() {
		return this.group_id;
	}

	public String getSubType() {
		return this.sub_type;
	}
}
