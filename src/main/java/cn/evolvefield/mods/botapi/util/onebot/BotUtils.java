package cn.evolvefield.mods.botapi.util.onebot;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.CustomCmdHandler;
import cn.evolvefield.onebot.sdk.model.event.message.GroupMessageEvent;
import cn.evolvefield.onebot.sdk.model.event.message.GuildMessageEvent;
import cn.evolvefield.onebot.sdk.model.event.message.MessageEvent;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Project: Bot-Connect-fabric-1.18
 * Author: cnlimiter
 * Date: 2023/1/21 19:04
 * Description:
 */
public class BotUtils {

    public static boolean varParse(MessageEvent event) {
        AtomicBoolean match = new AtomicBoolean(false);
        CustomCmdHandler.INSTANCE.getCustomCmdMap().keySet().forEach(
                s -> {
                    if (CustomCmdHandler.INSTANCE.getCmdByAlies(s).getCmdContent().contains("%")) {
                        if (event.getMessage().substring(1).contains(s))
                            match.set(true);
                    }

                }
        );
        return match.get();
    }

    public static boolean guildAdminParse(GuildMessageEvent event) {
        AtomicBoolean isAdmin = new AtomicBoolean(false);
        for (var roleInfo : BotApi.bot.getGuildMemberProfile(event.getGuildId(), event.getSender().getTinyId())
                .getData()
                .getRoles()) {
            if (Integer.parseInt(roleInfo.getRoleId()) >= 2 || Arrays.stream(GuildRole.values()).anyMatch(s -> s.role.equals(roleInfo.getRoleName()))) {
                isAdmin.set(true);
                break;
            }
        }
        return isAdmin.get();
    }

    public static boolean groupAdminParse(GroupMessageEvent event) {
        return !event.getSender().getRole().equals("MEMBER") || !event.getSender().getRole().equals("member");
    }

}
