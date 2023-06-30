package cn.evole.mods.mcbot.init.event;

import cn.evole.mods.mcbot.IMcBot;
import cn.evole.mods.mcbot.init.handler.ConfigHandler;
import net.minecraft.advancements.Advancement;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
//#if MC >= 11800
import net.minecraftforge.common.ForgeI18n;
//#elseif MC < 11700
//$$ import net.minecraftforge.fml.ForgeI18n;
//#else
//$$ import net.minecraftforge.fmllegacy.ForgeI18n;
//#endif
/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 9:48
 * Version: 1.0
 */
public class IPlayerEvent {
    public static void loggedIn(Player player) {
        if (ConfigHandler.cached().getStatus().isS_JOIN_ENABLE() && ConfigHandler.cached().getStatus().isSEND_ENABLED()) {
            if (ConfigHandler.cached().getCommon().isGuildOn() && !ConfigHandler.cached().getCommon().getChannelIdList().isEmpty()) {
                for (String id : ConfigHandler.cached().getCommon().getChannelIdList())
                    IMcBot.bot.sendGuildMsg(ConfigHandler.cached().getCommon().getGuildId(), id, player.getDisplayName().getString() + " 加入了服务器");
            } else {
                for (long id : ConfigHandler.cached().getCommon().getGroupIdList())
                    IMcBot.bot.sendGroupMsg(id, player.getDisplayName().getString() + " 加入了服务器", true);
            }
        }
    }
    public static void loggedOut(Player player) {


            if (ConfigHandler.cached().getStatus().isS_LEAVE_ENABLE() && ConfigHandler.cached().getStatus().isSEND_ENABLED()) {
                if (ConfigHandler.cached().getCommon().isGuildOn() && !ConfigHandler.cached().getCommon().getChannelIdList().isEmpty()) {
                    for (String id : ConfigHandler.cached().getCommon().getChannelIdList())
                        IMcBot.bot.sendGuildMsg(ConfigHandler.cached().getCommon().getGuildId(), id, player.getDisplayName().getString() + " 离开了服务器");
                } else {
                    for (long id : ConfigHandler.cached().getCommon().getGroupIdList())
                        IMcBot.bot.sendGroupMsg(id, player.getDisplayName().getString() + " 离开了服务器", true);
                }
            }
    }
    public static void death(DamageSource source, ServerPlayer player) {
            if (player != null && ConfigHandler.cached().getStatus().isS_DEATH_ENABLE() && ConfigHandler.cached().getStatus().isSEND_ENABLED()) {
                LivingEntity livingEntity2 = player.getKillCredit();
                String msg = "";

                //#if MC >= 11904
                String string = "mcbot.death.attack." + source.type().msgId();
                //#else
                //$$ String string = "mcbot.death.attack." + source.getMsgId();
                //#endif

                if (source.getEntity() == null && source.getDirectEntity() == null) {
                    String string2 = string + ".player";
                    msg = livingEntity2 != null ? ForgeI18n.parseMessage(string2, player.getDisplayName().getString(), livingEntity2.getDisplayName().getString()) : ForgeI18n.parseMessage(string, player.getDisplayName().getString());
                } else {//支持物品造成的死亡信息
                    assert source.getDirectEntity() != null;
                    Component component = source.getEntity() == null ? source.getDirectEntity().getDisplayName() : source.getEntity().getDisplayName();
                    Entity sourceEntity = source.getEntity();
                    ItemStack itemStack;
                    if (sourceEntity instanceof LivingEntity livingEntity3) {
                        itemStack = livingEntity3.getMainHandItem();
                    } else {
                        itemStack = ItemStack.EMPTY;
                    }
                    msg = !itemStack.isEmpty() && itemStack.hasCustomHoverName() ? ForgeI18n.parseMessage(string + ".item", player.getDisplayName().getString(), component.getString(), itemStack.getDisplayName().getString()) : ForgeI18n.parseMessage(string,player.getDisplayName().getString(), component.getString());
                }

                if (ConfigHandler.cached().getCommon().isGuildOn() && !ConfigHandler.cached().getCommon().getChannelIdList().isEmpty()) {
                    for (String id : ConfigHandler.cached().getCommon().getChannelIdList())
                        IMcBot.bot.sendGuildMsg(ConfigHandler.cached().getCommon().getGuildId(), id, String.format(msg, player.getDisplayName().getString()));
                } else {
                    for (long id : ConfigHandler.cached().getCommon().getGroupIdList())
                        IMcBot.bot.sendGroupMsg(id, String.format(msg, player.getDisplayName().getString()), true);
                }
            }
    }

    public static void advancement(Player player, Advancement advancement) {

            if (ConfigHandler.cached().getStatus().isS_ADVANCE_ENABLE() && advancement.getDisplay() != null && ConfigHandler.cached().getStatus().isSEND_ENABLED()) {
                String msg = ForgeI18n.parseMessage("mcbot.chat.type.advancement." + advancement.getDisplay().getFrame().getName(), player.getDisplayName().getString(), ForgeI18n.parseMessage(advancement.getDisplay().getTitle().getString()));

                if (ConfigHandler.cached().getCommon().isGuildOn() && !ConfigHandler.cached().getCommon().getChannelIdList().isEmpty()) {
                    for (String id : ConfigHandler.cached().getCommon().getChannelIdList())
                        IMcBot.bot.sendGuildMsg(ConfigHandler.cached().getCommon().getGuildId(), id, msg);
                } else {
                    for (long id : ConfigHandler.cached().getCommon().getGroupIdList())
                        IMcBot.bot.sendGroupMsg(id, msg, true);
                }
            }
    }

}
