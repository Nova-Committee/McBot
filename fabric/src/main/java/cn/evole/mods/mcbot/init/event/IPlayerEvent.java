package cn.evole.mods.mcbot.init.event;

import cn.evole.mods.mcbot.McBot;
import cn.evole.mods.mcbot.init.config.ModConfig;
import cn.evole.mods.mcbot.util.locale.I18n;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 9:48
 * Version: 1.0
 */
public class IPlayerEvent {
    public static void loggedIn(Level world, Player player) {
        if (ModConfig.INSTANCE.getStatus().isSJoinEnable() && ModConfig.INSTANCE.getStatus().isSEnable()) {
            if (ModConfig.INSTANCE.getCommon().isGuildOn() && !ModConfig.INSTANCE.getCommon().getChannelIdList().isEmpty()) {
                for (String id : ModConfig.INSTANCE.getCommon().getChannelIdList())
                    McBot.bot.sendGuildMsg(ModConfig.INSTANCE.getCommon().getGuildId(), id, player.getDisplayName().getString() + " 加入了服务器");
            } else {
                for (long id : ModConfig.INSTANCE.getCommon().getGroupIdList())
                    McBot.bot.sendGroupMsg(id, player.getDisplayName().getString() + " 加入了服务器", false);
            }
        }
    }
    public static void loggedOut(Level world, Player player) {


            if (ModConfig.INSTANCE.getStatus().isSLeaveEnable() && ModConfig.INSTANCE.getStatus().isSEnable()) {
                if (ModConfig.INSTANCE.getCommon().isGuildOn() && !ModConfig.INSTANCE.getCommon().getChannelIdList().isEmpty()) {
                    for (String id : ModConfig.INSTANCE.getCommon().getChannelIdList())
                        McBot.bot.sendGuildMsg(ModConfig.INSTANCE.getCommon().getGuildId(), id, player.getDisplayName().getString() + " 离开了服务器");
                } else {
                    for (long id : ModConfig.INSTANCE.getCommon().getGroupIdList())
                        McBot.bot.sendGroupMsg(id, player.getDisplayName().getString() + " 离开了服务器", false);
                }
            }
    }
    public static void death(DamageSource source, ServerPlayer player) {
            if (player != null && ModConfig.INSTANCE.getStatus().isSDeathEnable() && ModConfig.INSTANCE.getStatus().isSEnable()) {
                LivingEntity livingEntity2 = player.getKillCredit();
                String msg = "";

                //#if MC >= 11904
                String string = "mcbot.death.attack." + source.type().msgId();
                //#else
                //$$ String string = "mcbot.death.attack." + source.getMsgId();
                //#endif

                if (source.getEntity() == null && source.getDirectEntity() == null) {
                    String string2 = string + ".player";
                    msg = livingEntity2 != null ? I18n.get(string2, player.getDisplayName().getString(), livingEntity2.getDisplayName().getString()) : I18n.get(string, player.getDisplayName().getString());
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
                    msg = !itemStack.isEmpty() && itemStack.hasCustomHoverName() ? I18n.get(string + ".item", player.getDisplayName().getString(), component.getString(), itemStack.getDisplayName().getString()) : I18n.get(string,player.getDisplayName().getString(), component.getString());
                }

                if (ModConfig.INSTANCE.getCommon().isGuildOn() && !ModConfig.INSTANCE.getCommon().getChannelIdList().isEmpty()) {
                    for (String id : ModConfig.INSTANCE.getCommon().getChannelIdList())
                        McBot.bot.sendGuildMsg(ModConfig.INSTANCE.getCommon().getGuildId(), id, String.format(msg, player.getDisplayName().getString()));
                } else {
                    for (long id : ModConfig.INSTANCE.getCommon().getGroupIdList())
                        McBot.bot.sendGroupMsg(id, String.format(msg, player.getDisplayName().getString()), false);
                }
            }
    }

    public static void advancement(Player player, Advancement advancement) {
        //#if MC <= 12001
        boolean displayExist = advancement.getDisplay() != null;
        //#else
        //$$ boolean displayExist = advancement.display().isPresent();
        //#endif

            if (ModConfig.INSTANCE.getStatus().isSAdvanceEnable() && displayExist && ModConfig.INSTANCE.getStatus().isSEnable()) {
                //#if MC <= 12001
                DisplayInfo display = advancement.getDisplay();
                //#else
                //$$ DisplayInfo display = advancement.display().get();
                //#endif

                String msg = I18n.get("mcbot.chat.type.advancement." + display.getFrame().getName(), player.getDisplayName().getString(), I18n.get(display.getTitle().getString()));

                if (ModConfig.INSTANCE.getCommon().isGuildOn() && !ModConfig.INSTANCE.getCommon().getChannelIdList().isEmpty()) {
                    for (String id : ModConfig.INSTANCE.getCommon().getChannelIdList())
                        McBot.bot.sendGuildMsg(ModConfig.INSTANCE.getCommon().getGuildId(), id, msg);
                } else {
                    for (long id : ModConfig.INSTANCE.getCommon().getGroupIdList())
                        McBot.bot.sendGroupMsg(id, msg, false);
                }
            }
    }

}
