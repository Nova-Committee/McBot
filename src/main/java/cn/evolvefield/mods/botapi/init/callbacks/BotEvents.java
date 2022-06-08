package cn.evolvefield.mods.botapi.init.callbacks;

import cn.evolvefield.mods.botapi.api.events.*;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/20 8:00
 * Version: 1.0
 */
public class BotEvents {
    public static final Event<GroupMsg> GROUP_MSG_EVENT = EventFactory.createArrayBacked(GroupMsg.class, callbacks -> (event) -> {
        for (GroupMsg callback : callbacks) {
            callback.onGroupMsg(event);
        }
    });
    public static final Event<PrivateMsg> PRIVATE_MSG_EVENT = EventFactory.createArrayBacked(PrivateMsg.class, callbacks -> (event) -> {
        for (PrivateMsg callback : callbacks) {
            callback.onPrivateMsg(event);
        }
    });
    public static final Event<NoticeMsg> NOTICE_MSG_EVENT = EventFactory.createArrayBacked(NoticeMsg.class, callbacks -> (event) -> {
        for (NoticeMsg callback : callbacks) {
            callback.onNoticeMsg(event);
        }
    });

    public static final Event<ChannelGroupMsg> CHANNEL_GROUP_MSG_EVENT = EventFactory.createArrayBacked(ChannelGroupMsg.class, callbacks -> (event) -> {
        for (ChannelGroupMsg callback : callbacks) {
            callback.onChannelGroupMsg(event);
        }
    });

    public static final Event<RequestMsg> REQUEST_MSG_EVENT = EventFactory.createArrayBacked(RequestMsg.class, callbacks -> (event) -> {
        for (RequestMsg callback : callbacks) {
            callback.onRequestMsg(event);
        }
    });

    public BotEvents() {
    }

    @FunctionalInterface
    public interface GroupMsg {
        void onGroupMsg(GroupMessageEvent event);
    }

    @FunctionalInterface
    public interface PrivateMsg {
        void onPrivateMsg(PrivateMessageEvent event);
    }

    @FunctionalInterface
    public interface NoticeMsg {
        void onNoticeMsg(NoticeEvent event);
    }

    @FunctionalInterface
    public interface ChannelGroupMsg {
        void onChannelGroupMsg(ChannelGroupMessageEvent event);
    }

    @FunctionalInterface
    public interface RequestMsg {
        void onRequestMsg(RequestEvent event);
    }
}
