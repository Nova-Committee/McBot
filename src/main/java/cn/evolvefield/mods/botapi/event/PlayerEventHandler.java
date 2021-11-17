package cn.evolvefield.mods.botapi.event;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.message.SendMessage;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class PlayerEventHandler {


    @SubscribeEvent
    public static void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (BotApi.config.getCommon().isS_JOIN_ENABLE() && BotApi.config.getCommon().isENABLED()){
            SendMessage.Group(BotApi.config.getCommon().getGroupId(), event.getPlayer().getDisplayName().getString() + " 加入了服务器");
        }
    }

    @SubscribeEvent
    public static void playerLeft(PlayerEvent.PlayerLoggedOutEvent event) {
        if (BotApi.config.getCommon().isS_LEAVE_ENABLE() && BotApi.config.getCommon().isENABLED()){
            SendMessage.Group(BotApi.config.getCommon().getGroupId(),event.getPlayer().getDisplayName().getString() + " 离开了服务器");
        }
    }


    @SubscribeEvent
    public static void playerDeadEvent(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player && BotApi.config.getCommon().isS_DEATH_ENABLE() && BotApi.config.getCommon().isENABLED()) {
            String message = event.getSource().getLocalizedDeathMessage(event.getEntityLiving()).getString();
            SendMessage.Group(BotApi.config.getCommon().getGroupId(),String.format(message, event.getEntity().getDisplayName().getString()));
        }
    }

    @SubscribeEvent
    public static void playerAdvancementEvent(AdvancementEvent event) {
        if ( BotApi.config.getCommon().isS_ADVANCE_ENABLE() && event.getAdvancement().getDisplay() != null && BotApi.config.getCommon().isENABLED()) {
            String message = new TranslatableComponent("chat.botapi.type.advancement." + event.getAdvancement().getDisplay().getFrame().getName(), event.getPlayer().getDisplayName(), event.getAdvancement().getChatComponent()).getString();
            SendMessage.Group(BotApi.config.getCommon().getGroupId(),message);
        }
    }
}
