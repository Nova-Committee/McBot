package cn.evolvefield.mods.botapi.init.event;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.SendMessage;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AchievementEvent;

public class PlayerEventHandler {


    public void preInit() {
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (BotApi.config.getCommon().isS_JOIN_ENABLE() && BotApi.config.getCommon().isSEND_ENABLED()){
            SendMessage.Group(BotApi.config.getCommon().getGroupId(), event.player.getDisplayName() + " 加入了服务器");
        }
    }

    @SubscribeEvent
    public void playerLeft(PlayerEvent.PlayerLoggedOutEvent event) {
        if (BotApi.config.getCommon().isS_LEAVE_ENABLE() && BotApi.config.getCommon().isSEND_ENABLED()){
            SendMessage.Group(BotApi.config.getCommon().getGroupId(),event.player.getDisplayName() + " 离开了服务器");
        }
    }

    @SubscribeEvent
    public void playerDeadEvent(LivingDeathEvent event) {
        if (event.entityLiving instanceof EntityPlayerMP && BotApi.config.getCommon().isS_DEATH_ENABLE() && BotApi.config.getCommon().isSEND_ENABLED()) {

            EntityLivingBase living = event.entityLiving.func_94060_bK();
            String string = "botapi.death.attack." + event.source.damageType;
            String string2 = string + ".player";
            String msg = living != null ? I18n.format(string2, ((EntityPlayerMP) event.entityLiving).getDisplayName(), living.getCommandSenderName()) : I18n.format(string, ((EntityPlayerMP) event.entityLiving).getDisplayName());

            //String message = ((EntityTracker) event.entity).getCombatTracker().getDeathMessage().getUnformattedText();
            SendMessage.Group(BotApi.config.getCommon().getGroupId(),String.format(msg, ScorePlayerTeam.formatPlayerName(((EntityPlayerMP) event.entity).getTeam(), ((EntityPlayerMP) event.entity).getDisplayName())));
        }
    }

    @SubscribeEvent
    public void playerAdvancementEvent(AchievementEvent event) {
        if ( BotApi.config.getCommon().isS_ADVANCE_ENABLE() && event.achievement != null && BotApi.config.getCommon().isSEND_ENABLED()) {
            String message = new ChatComponentTranslation("botapi.chat.type.advancement." + event.achievement.getDescription(), event.entityPlayer.getDisplayName(), event.achievement.getDescription()).getUnformattedText();
            SendMessage.Group(BotApi.config.getCommon().getGroupId(),message);
        }
    }
}
