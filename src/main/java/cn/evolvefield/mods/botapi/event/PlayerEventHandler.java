package cn.evolvefield.mods.botapi.event;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.message.SendMessage;
import net.minecraft.entity.player.EntityPlayer;
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
        if (BotApi.config.getCommon().isS_JOIN_ENABLE()){
            SendMessage.Group(BotApi.config.getCommon().getGroupId(), event.player.getDisplayNameString() + " 加入了服务器");
        }
    }

    @SubscribeEvent
    public static void playerLeft(PlayerEvent.PlayerLoggedOutEvent event) {
        if (BotApi.config.getCommon().isS_LEAVE_ENABLE()){
            SendMessage.Group(BotApi.config.getCommon().getGroupId(),event.player.getDisplayNameString() + " 离开了服务器");
        }
    }

    @SubscribeEvent
    public static void playerDeadEvent(LivingDeathEvent event) {
        if (event.getEntity() instanceof EntityPlayer && BotApi.config.getCommon().isS_DEATH_ENABLE()) {
            String message = event.getSource().getDeathMessage(event.getEntityLiving()).getUnformattedText();
            SendMessage.Group(BotApi.config.getCommon().getGroupId(),String.format(message, ScorePlayerTeam.formatPlayerName(event.getEntity().getTeam(), event.getEntity().getName())));
        }
    }

    @SubscribeEvent
    public static void playerAdvancementEvent(AdvancementEvent event) {
        if ( BotApi.config.getCommon().isS_ADVANCE_ENABLE() && event.getAdvancement().getDisplay() != null) {
            String message = new TextComponentTranslation("chat.botapi.type.advancement." + event.getAdvancement().getDisplay().getFrame().getName(), event.getEntityPlayer().getDisplayName(), event.getAdvancement().getDisplayText()).getUnformattedText();
            SendMessage.Group(BotApi.config.getCommon().getGroupId(),message);
        }
    }
}
