package cn.evolvefield.mods.botapi.event;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.message.SendMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;


@Mod.EventBusSubscriber
public class PlayerEventHandler {


    @SubscribeEvent
    public static void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (BotApi.config.getCommon().isSEND_ENABLED()){
            SendMessage.Group(BotApi.config.getCommon().getGroupId(), event.player.getDisplayName() + " 加入了服务器");
        }
    }

    @SubscribeEvent
    public static void playerLeft(PlayerEvent.PlayerLoggedOutEvent event) {
        if (BotApi.config.getCommon().isSEND_ENABLED()){
            SendMessage.Group(BotApi.config.getCommon().getGroupId(),event.player.getDisplayName() + " 离开了服务器");
        }
    }

    @SubscribeEvent
    public static void playerDeadEvent(LivingDeathEvent event) {
        if (event.getEntity() instanceof EntityPlayer && BotApi.config.getCommon().isSEND_ENABLED()) {
            String message = event.getSource().getDeathMessage(event.getEntityLiving()).getFormattedText();
            SendMessage.Group(BotApi.config.getCommon().getGroupId(),String.format(message, event.getEntity().getDisplayName()));
        }
    }
}
