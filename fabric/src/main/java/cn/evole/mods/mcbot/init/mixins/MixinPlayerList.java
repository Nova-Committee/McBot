package cn.evole.mods.mcbot.init.mixins;


import cn.evole.mods.mcbot.init.callbacks.IEvents;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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
        IEvents.PLAYER_LOGGED_IN.invoker().onPlayerLoggedIn(player.getCommandSenderWorld(), player);
    }
    @Inject(method = "remove", at = @At(value = "HEAD"))
    public void PlayerList_remove(ServerPlayer player, CallbackInfo ci) {
        IEvents.PLAYER_LOGGED_OUT.invoker().onPlayerLoggedOut(player.getCommandSenderWorld(), player);
    }
    //#endif
}