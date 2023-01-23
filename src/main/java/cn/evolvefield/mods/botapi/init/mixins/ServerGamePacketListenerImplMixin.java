package cn.evolvefield.mods.botapi.init.mixins;

import cn.evolvefield.mods.botapi.init.callbacks.ServerLevelEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 10:30
 * Version: 1.0
 */
@Mixin(value = ServerGamePacketListenerImpl.class, priority = 1001)
public class ServerGamePacketListenerImplMixin {

    @Shadow
    public ServerPlayer player;

    @Inject(method = "broadcastChatMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/network/chat/ChatType$Bound;)V"))
    public void handleChat(PlayerChatMessage filteredText, CallbackInfo ci) {
        String s1 = filteredText.decoratedContent().getString();
        Component component2 = Component.translatable("chat.type.text", this.player.getDisplayName(), s1);
        ServerLevelEvents.Server_Chat.invoker().onChat(this.player, s1, component2);
    }
}
