package cn.evole.mods.mcbot.init.event;

import cn.evole.mods.mcbot.Const;
import cn.evole.mods.mcbot.init.config.ModConfig;
import cn.evole.mods.mcbot.util.locale.I18n;
import lombok.val;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
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
            val msg = player.getDisplayName().getString() + " 加入了服务器";
            send(msg);
        }
    }
    public static void loggedOut(Level world, Player player) {
        if (ModConfig.INSTANCE.getStatus().isSLeaveEnable() && ModConfig.INSTANCE.getStatus().isSEnable()) {
            val msg = player.getDisplayName().getString() + " 离开了服务器";
            send(msg);
        }
    }
    public static void death(DamageSource source, ServerPlayer player) {
        if (player != null && ModConfig.INSTANCE.getStatus().isSDeathEnable() && ModConfig.INSTANCE.getStatus().isSEnable()) {
            LivingEntity livingEntity2 = player.getKillCredit();
            String message = "";

            //#if MC >= 11904
            //$$ String string = "mcbot.death.attack." + source.type().msgId();
            //#else
            String string = "mcbot.death.attack." + source.getMsgId();
            //#endif

            if (source.getEntity() == null && source.getDirectEntity() == null) {
                String string2 = string + ".player";
                message = livingEntity2 != null ? I18n.get(string2, player.getDisplayName().getString(), livingEntity2.getDisplayName().getString()) : I18n.get(string, player.getDisplayName().getString());
            } else {//支持物品造成的死亡信息
                assert source.getDirectEntity() != null;
                Component component = source.getEntity() == null ? source.getDirectEntity().getDisplayName() : source.getEntity().getDisplayName();
                Entity sourceEntity = source.getEntity();
                ItemStack itemStack;
                if (sourceEntity instanceof LivingEntity) {
                    itemStack = ((LivingEntity)sourceEntity).getMainHandItem();
                } else {
                    itemStack = ItemStack.EMPTY;
                }
                message = !itemStack.isEmpty() && itemStack.hasCustomHoverName() ? I18n.get(string + ".item", player.getDisplayName().getString(), component.getString(), itemStack.getDisplayName().getString()) : I18n.get(string,player.getDisplayName().getString(), component.getString());
            }
            val msg = String.format(message, player.getDisplayName().getString());
            send(msg);
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

            String message = I18n.get("mcbot.chat.type.advancement." + display.getFrame().getName(), player.getDisplayName().getString(), I18n.get(display.getTitle().getString()));
            val msg = String.format(message, player.getDisplayName().getString());
            send(msg);
        }
    }

    private static void send(String msg){
        if (ModConfig.INSTANCE.getCommon().isGuildOn() && !ModConfig.INSTANCE.getCommon().getChannelIdList().isEmpty()) {
            Const.sendGuildMsg(msg);
        } else {
            Const.sendGroupMsg(msg);
        }
    }

}
