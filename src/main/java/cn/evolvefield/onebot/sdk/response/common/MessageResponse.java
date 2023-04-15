package cn.evolvefield.onebot.sdk.response.common;

import cn.evolvefield.onebot.sdk.action.ActionData;
import cn.evolvefield.onebot.sdk.entity.MsgId;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/10 0:04
 * Version: 1.0
 */
public class MessageResponse extends ActionData<MsgId> {
    public MessageResponse(int messageId){
        this.setStatus("ok");
        this.setRetCode(0);
        this.setData(new MsgId(messageId));
    }
}
