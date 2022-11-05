package cn.evolvefield.mods.botapi.init.handler;


import cn.evolvefield.mods.botapi.BotApi;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;


@Mod.EventBusSubscriber()
public class PlayerEventHandler {


    @SubscribeEvent
    public static void playerJoin(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
        if (BotApi.config.getStatus().isS_JOIN_ENABLE() && BotApi.config.getStatus().isSEND_ENABLED()) {
            if (BotApi.config.getCommon().isGuildOn() && !BotApi.config.getCommon().getChannelIdList().isEmpty()) {
                for (String id : BotApi.config.getCommon().getChannelIdList())
                    BotApi.bot.sendGuildMsg(BotApi.config.getCommon().getGuildId(), id, event.player.getDisplayNameString() + " 加入了服务器");
            } else {
                for (long id : BotApi.config.getCommon().getGroupIdList())
                    BotApi.bot.sendGroupMsg(id, event.player.getDisplayNameString() + " 加入了服务器", true);
            }
        }
    }

    @SubscribeEvent
    public static void playerLeft(PlayerEvent.PlayerLoggedOutEvent event) {
        if (BotApi.config.getStatus().isS_LEAVE_ENABLE() && BotApi.config.getStatus().isSEND_ENABLED()) {
            if (BotApi.config.getCommon().isGuildOn() && !BotApi.config.getCommon().getChannelIdList().isEmpty()) {
                for (String id : BotApi.config.getCommon().getChannelIdList())
                    BotApi.bot.sendGuildMsg(BotApi.config.getCommon().getGuildId(), id, event.player.getDisplayNameString() + " 离开了服务器");
            } else {
                for (long id : BotApi.config.getCommon().getGroupIdList())
                    BotApi.bot.sendGroupMsg(id, event.player.getDisplayNameString() + " 离开了服务器", true);

            }
        }
    }


    @SubscribeEvent
    public static void playerDeadEvent(LivingDeathEvent event) {
        if (event.getEntity() instanceof EntityPlayer && BotApi.config.getStatus().isS_DEATH_ENABLE() && BotApi.config.getStatus().isSEND_ENABLED()) {
            String message = event.getSource().getDeathMessage(event.getEntityLiving()).getFormattedText();
            if (BotApi.config.getCommon().isGuildOn() && !BotApi.config.getCommon().getChannelIdList().isEmpty()) {
                for (String id : BotApi.config.getCommon().getChannelIdList())
                    BotApi.bot.sendGuildMsg(BotApi.config.getCommon().getGuildId(), id, String.format(message, ((EntityPlayer) event.getEntity()).getDisplayNameString()));
            } else {
                for (long id : BotApi.config.getCommon().getGroupIdList())
                    BotApi.bot.sendGroupMsg(id, String.format(message, ((EntityPlayer) event.getEntity()).getDisplayNameString()), true);
            }
        }
    }

    @SubscribeEvent
    public static void playerAdvancementEvent(AdvancementEvent event) {
        if (BotApi.config.getStatus().isS_ADVANCE_ENABLE() && event.getAdvancement().getDisplay() != null && BotApi.config.getStatus().isSEND_ENABLED()) {
            String msg = I18n.format("botapi.chat.type.advancement." + event.getAdvancement().getDisplay().getFrame().getName(), event.getEntity().getDisplayName().getFormattedText(), I18n.format(event.getAdvancement().getDisplay().getTitle().getFormattedText()));

            if (BotApi.config.getCommon().isGuildOn() && !BotApi.config.getCommon().getChannelIdList().isEmpty()) {
                for (String id : BotApi.config.getCommon().getChannelIdList())
                    BotApi.bot.sendGuildMsg(BotApi.config.getCommon().getGuildId(), id, msg);
            } else {
                for (long id : BotApi.config.getCommon().getGroupIdList())
                    BotApi.bot.sendGroupMsg(id, msg, true);
            }

        }
    }
}
