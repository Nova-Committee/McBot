package cn.evole.mods.mcbot.init.mixins;


import cn.evole.mods.mcbot.init.callbacks.IEvents;
import com.mojang.authlib.GameProfile;
import me.drex.vanish.util.VanishManager;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;
//#if MC >= 12002
//$$ import net.minecraft.server.network.CommonListenerCookie;
//#endif

/**
 * Author cnlimiter
 * CreateTime 2023/5/19 0:51
 * Name MixinPlayerList
 * Description
 */

@Mixin(value = PlayerList.class, priority = 1001)
public abstract class MixinPlayerList {
    //#if MC >= 12002
    //$$ @Inject(method = "placeNewPlayer", at = @At(value = "TAIL"))
    //$$ public void PlayerList_placeNewPlayer(Connection connection, ServerPlayer player, CommonListenerCookie commonListenerCookie, CallbackInfo ci) {
    //$$     IEvents.PLAYER_LOGGED_IN.invoker().onPlayerLoggedIn(player.getCommandSenderWorld(), player);
    //$$ }
    //$$ @Inject(method = "remove", at = @At(value = "HEAD"))
    //$$ public void PlayerList_remove(ServerPlayer player, CallbackInfo ci) {
    //$$     IEvents.PLAYER_LOGGED_OUT.invoker().onPlayerLoggedOut(player.getCommandSenderWorld(), player);
    //$$ }
    //#else
    @Inject(method = "placeNewPlayer", at = @At(value = "TAIL"))
    public void PlayerList_placeNewPlayer(Connection connection, ServerPlayer player, CallbackInfo ci) {
        if (!isPlayerVanished(player)) {
            // 如果玩家不是隐身状态，则执行逻辑
            IEvents.PLAYER_LOGGED_IN.invoker().onPlayerLoggedIn(player.getCommandSenderWorld(), player);
        } else {
            // 如果玩家是隐身状态，直接返回不执行任何操作
        }
    }
    @Inject(method = "remove", at = @At(value = "HEAD"))
    public void PlayerList_remove(ServerPlayer player, CallbackInfo ci) {
        if (!isPlayerVanished(player)) {
            // 如果玩家不是隐身状态，则执行逻辑
            IEvents.PLAYER_LOGGED_OUT.invoker().onPlayerLoggedOut(player.getCommandSenderWorld(), player);
        } else {
            // 如果玩家是隐身状态，直接返回不执行任何操作
        }
    }
    //#endif
    private boolean isPlayerVanished(ServerPlayer player) {
        // 获取玩家的 GameProfile
        GameProfile gameProfile = player.getGameProfile();
        // 从 GameProfile 中获取 UUID
        UUID uuid = gameProfile.getId();
        // 使用 VanishManager.isVanished 方法检查玩家是否处于隐身状态
        return VanishManager.isVanished(player.getServer(), uuid);
    }
}