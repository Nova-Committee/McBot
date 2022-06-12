package cn.evolvefield.mods.botapi.init.handler;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.message.SendMessage;
import cn.evolvefield.mods.botapi.core.service.WebSocketService;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;


@Mod.EventBusSubscriber
public class PlayerEventHandler {


    @SubscribeEvent
    public static void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (BotApi.config.getStatus().isS_JOIN_ENABLE() && BotApi.config.getStatus().isSEND_ENABLED() && WebSocketService.client.isOpen()) {
            if (BotApi.config.getCommon().isGuildOn() && !BotApi.config.getCommon().getChannelIdList().isEmpty()) {
                for (String id : BotApi.config.getCommon().getChannelIdList())
                    SendMessage.ChannelGroup(BotApi.config.getCommon().getGuildId(), id, event.player.getDisplayNameString() + " 加入了服务器");
            } else {
                SendMessage.Group(BotApi.config.getCommon().getGroupId(), event.player.getDisplayNameString() + " 加入了服务器");
            }
        }
    }

    @SubscribeEvent
    public static void playerLeft(PlayerEvent.PlayerLoggedOutEvent event) {
        if (BotApi.config.getStatus().isS_LEAVE_ENABLE() && BotApi.config.getStatus().isSEND_ENABLED() && WebSocketService.client.isOpen()) {
            if (BotApi.config.getCommon().isGuildOn() && !BotApi.config.getCommon().getChannelIdList().isEmpty()) {
                for (String id : BotApi.config.getCommon().getChannelIdList())
                    SendMessage.ChannelGroup(BotApi.config.getCommon().getGuildId(), id, event.player.getDisplayNameString() + " 离开了服务器");
            } else {
                SendMessage.Group(BotApi.config.getCommon().getGroupId(), event.player.getDisplayNameString() + " 离开了服务器");

            }
        }
    }

    @SubscribeEvent
    public static void playerDeadEvent(LivingDeathEvent event) {
        if (event.getEntity() instanceof EntityPlayerMP && BotApi.config.getStatus().isS_DEATH_ENABLE() && BotApi.config.getStatus().isSEND_ENABLED() && WebSocketService.client.isOpen()) {
            EntityLivingBase livingEntity2 = event.getEntityLiving();

            String message = event.getEntityLiving().getCombatTracker().getDeathMessage().getUnformattedText();
            if (BotApi.config.getCommon().isGuildOn() && !BotApi.config.getCommon().getChannelIdList().isEmpty()) {
                for (String id : BotApi.config.getCommon().getChannelIdList())
                    SendMessage.ChannelGroup(BotApi.config.getCommon().getGuildId(), id, String.format(message, ScorePlayerTeam.formatPlayerName(event.getEntity().getTeam(), event.getEntity().getName())));
            } else {
                SendMessage.Group(BotApi.config.getCommon().getGroupId(), String.format(message, ScorePlayerTeam.formatPlayerName(event.getEntity().getTeam(), event.getEntity().getName())));
            }
        }
    }


    @SubscribeEvent
    public static void playerAdvancementEvent(AdvancementEvent event) {
        if (BotApi.config.getStatus().isS_ADVANCE_ENABLE() && event.getAdvancement().getDisplay() != null && BotApi.config.getStatus().isSEND_ENABLED() && WebSocketService.client.isOpen()) {
            String playerName = event.getEntityPlayer().getDisplayNameString();
            String advanceText = event.getAdvancement().getDisplay().getTitle().getUnformattedComponentText();
            String message = new TextComponentTranslation("chat.botapi.type.advancement." + event.getAdvancement().getDisplay().getFrame().getName(), playerName, advanceText).getUnformattedComponentText();

            if (BotApi.config.getCommon().isGuildOn() && !BotApi.config.getCommon().getChannelIdList().isEmpty()) {
                for (String id : BotApi.config.getCommon().getChannelIdList())
                    SendMessage.ChannelGroup(BotApi.config.getCommon().getGuildId(), id, message);
            } else {
                SendMessage.Group(BotApi.config.getCommon().getGroupId(), message);
            }
        }
    }

}
