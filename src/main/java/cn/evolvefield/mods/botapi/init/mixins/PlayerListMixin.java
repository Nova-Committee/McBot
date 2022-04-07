package cn.evolvefield.mods.botapi.init.mixins;

import cn.evolvefield.mods.botapi.init.callbacks.PlayerEvents;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 9:38
 * Version: 1.0
 */
@Mixin(value = PlayerList.class, priority = 1001)
public class PlayerListMixin {
    @Inject(method = "placeNewPlayer", at = @At(value = "TAIL"))
    public void PlayerList_placeNewPlayer(Connection connection, ServerPlayer player, CallbackInfo ci) {
        PlayerEvents.PLAYER_LOGGED_IN.invoker().onPlayerLoggedIn(player.level, player);
    }

    @Inject(method = "remove", at = @At(value = "HEAD"))
    public void PlayerList_remove(ServerPlayer player, CallbackInfo ci) {
        PlayerEvents.PLAYER_LOGGED_OUT.invoker().onPlayerLoggedOut(player.level, player);
    }
}
