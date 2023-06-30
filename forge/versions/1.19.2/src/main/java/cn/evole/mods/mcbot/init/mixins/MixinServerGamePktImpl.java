//#if MC >= 11900
package cn.evole.mods.mcbot.init.mixins;
import cn.evole.mods.mcbot.init.callbacks.IEvents;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Author cnlimiter
 * CreateTime 2023/5/19 0:54
 * Name MixinServerGamePktImpl
 * Description
 */

@Mixin(value = ServerGamePacketListenerImpl.class, priority = 1001)
public abstract class MixinServerGamePktImpl {
    @Shadow
    public ServerPlayer player;

    @Inject(method = "broadcastChatMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/network/chat/ChatType$Bound;)V", shift = At.Shift.BEFORE))
    public void handleChat(PlayerChatMessage filteredText, CallbackInfo ci) {
        String s1 = filteredText.serverContent().getString();
        IEvents.SERVER_CHAT.invoker().onChat(this.player, s1);
    }
}
//#endif
