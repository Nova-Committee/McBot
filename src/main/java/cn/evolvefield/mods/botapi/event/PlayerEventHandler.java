package cn.evolvefield.mods.botapi.event;


import cn.evolvefield.mods.botapi.config.ModConfig;
import cn.evolvefield.mods.botapi.message.SendMessage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class PlayerEventHandler {


    @SubscribeEvent
    public static void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (ModConfig.SEND_ENABLED.get()){
            SendMessage.Group(ModConfig.GROUP_ID.get(), event.getPlayer().getDisplayName().getString() + " 加入了服务器");
        }
    }

    @SubscribeEvent
    public static void playerLeft(PlayerEvent.PlayerLoggedOutEvent event) {
        if (ModConfig.SEND_ENABLED.get()){
            SendMessage.Group(ModConfig.GROUP_ID.get(),event.getPlayer().getDisplayName().getString() + " 离开了服务器");
        }
    }

    @SubscribeEvent
    public static void playerDeadEvent(LivingDeathEvent event) {
        if (event.getEntity() instanceof PlayerEntity && ModConfig.SEND_ENABLED.get()) {
            String message = event.getSource().getLocalizedDeathMessage(event.getEntityLiving()).getString();
            SendMessage.Group(ModConfig.GROUP_ID.get(),String.format(message, event.getEntity().getDisplayName().getString()));
        }
    }
}
