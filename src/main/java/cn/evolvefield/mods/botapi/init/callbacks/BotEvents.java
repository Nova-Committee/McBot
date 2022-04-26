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
    public static final Event<BotEvents.GroupMsg> GROUP_MSG_EVENT = EventFactory.createArrayBacked(BotEvents.GroupMsg.class, callbacks -> (event) -> {
        for (BotEvents.GroupMsg callback : callbacks) {
            callback.onGroupMsg(event);
        }
    });
    public static final Event<BotEvents.PrivateMsg> PRIVATE_MSG_EVENT = EventFactory.createArrayBacked(BotEvents.PrivateMsg.class, callbacks -> (event) -> {
        for (BotEvents.PrivateMsg callback : callbacks) {
            callback.onPrivateMsg(event);
        }
    });
    public static final Event<BotEvents.NoticeMsg> NOTICE_MSG_EVENT = EventFactory.createArrayBacked(BotEvents.NoticeMsg.class, callbacks -> (event) -> {
        for (BotEvents.NoticeMsg callback : callbacks) {
            callback.onNoticeMsg(event);
        }
    });

    public static final Event<BotEvents.ChannelGroupMsg> CHANNEL_GROUP_MSG_EVENT = EventFactory.createArrayBacked(BotEvents.ChannelGroupMsg.class, callbacks -> (event) -> {
        for (BotEvents.ChannelGroupMsg callback : callbacks) {
            callback.onChannelGroupMsg(event);
        }
    });

    public static final Event<BotEvents.RequestMsg> REQUEST_MSG_EVENT = EventFactory.createArrayBacked(BotEvents.RequestMsg.class, callbacks -> (event) -> {
        for (BotEvents.RequestMsg callback : callbacks) {
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
