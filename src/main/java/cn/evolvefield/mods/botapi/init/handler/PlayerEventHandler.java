package cn.evolvefield.mods.botapi.init.handler;


import cn.evolvefield.mods.botapi.BotApi;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import lombok.val;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AchievementEvent;


public class PlayerEventHandler {
    public static PlayerEventHandler INSTANCE = new PlayerEventHandler();

    public void preInit() {
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        val player = event.player;
        if (ConfigHandler.cached().getStatus().isS_JOIN_ENABLE() && ConfigHandler.cached().getStatus().isSEND_ENABLED()) {
            if (ConfigHandler.cached().getCommon().isGuildOn() && !ConfigHandler.cached().getCommon().getChannelIdList().isEmpty()) {
                for (String id : ConfigHandler.cached().getCommon().getChannelIdList())
                    BotApi.bot.sendGuildMsg(ConfigHandler.cached().getCommon().getGuildId(), id, player.getDisplayName() + " 加入了服务器");
            } else {
                for (long id : ConfigHandler.cached().getCommon().getGroupIdList())
                    BotApi.bot.sendGroupMsg(id, player.getDisplayName() + " 加入了服务器", true);
            }
        }
    }

    @SubscribeEvent
    public void playerLeft(PlayerEvent.PlayerLoggedOutEvent event) {
        val player = event.player;
        if (ConfigHandler.cached().getStatus().isS_LEAVE_ENABLE() && ConfigHandler.cached().getStatus().isSEND_ENABLED()) {
            if (ConfigHandler.cached().getCommon().isGuildOn() && !ConfigHandler.cached().getCommon().getChannelIdList().isEmpty()) {
                for (String id : ConfigHandler.cached().getCommon().getChannelIdList())
                    BotApi.bot.sendGuildMsg(ConfigHandler.cached().getCommon().getGuildId(), id, player.getDisplayName() + " 离开了服务器");
            } else {
                for (long id : ConfigHandler.cached().getCommon().getGroupIdList())
                    BotApi.bot.sendGroupMsg(id, player.getDisplayName() + " 离开了服务器", true);

            }
        }
    }


    @SubscribeEvent
    public void playerDeadEvent(LivingDeathEvent event) {
        val entityLiving = event.entityLiving;
        val source = event.source;
        if (entityLiving instanceof EntityPlayer && ConfigHandler.cached().getStatus().isS_DEATH_ENABLE() && ConfigHandler.cached().getStatus().isSEND_ENABLED()) {
            val player = (EntityPlayer) entityLiving;
            val livingEntity2 = player.getLastAttacker();
            String string = "botapi.death.attack." + source.damageType;
            String msg = livingEntity2 != null ? I18n.format(string, player.getDisplayName(), livingEntity2.getCommandSenderName()) : I18n.format(string, player.getDisplayName());

            if (ConfigHandler.cached().getCommon().isGuildOn() && !ConfigHandler.cached().getCommon().getChannelIdList().isEmpty()) {
                for (String id : ConfigHandler.cached().getCommon().getChannelIdList())
                    BotApi.bot.sendGuildMsg(ConfigHandler.cached().getCommon().getGuildId(), id, String.format(msg, player.getDisplayName()));
            } else {
                for (long id : ConfigHandler.cached().getCommon().getGroupIdList())
                    BotApi.bot.sendGroupMsg(id, String.format(msg, player.getDisplayName()), true);
            }
        }
    }

    @SubscribeEvent
    public void playerAdvancementEvent(AchievementEvent event) {
        val player = event.entityPlayer;
        val advancement = event.achievement;
        if (ConfigHandler.cached().getStatus().isS_ADVANCE_ENABLE() && advancement.getDescription() != null && ConfigHandler.cached().getStatus().isSEND_ENABLED()) {
            String msg = I18n.format("botapi.chat.type.advancement", player.getDisplayName(), I18n.format(advancement.getDescription()));

            if (ConfigHandler.cached().getCommon().isGuildOn() && !ConfigHandler.cached().getCommon().getChannelIdList().isEmpty()) {
                for (String id : ConfigHandler.cached().getCommon().getChannelIdList())
                    BotApi.bot.sendGuildMsg(ConfigHandler.cached().getCommon().getGuildId(), id, msg);
            } else {
                for (long id : ConfigHandler.cached().getCommon().getGroupIdList())
                    BotApi.bot.sendGroupMsg(id, msg, true);
            }
        }
    }
}
