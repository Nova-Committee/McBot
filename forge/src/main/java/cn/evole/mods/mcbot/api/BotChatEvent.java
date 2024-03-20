package cn.evole.mods.mcbot.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;

/**
 * BotChatEvent
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/3/20 19:39
 */
@Getter
@Setter
@AllArgsConstructor
public class BotChatEvent extends Event {
    private ServerPlayer player;
    private int message_id;
    private String message;
}
