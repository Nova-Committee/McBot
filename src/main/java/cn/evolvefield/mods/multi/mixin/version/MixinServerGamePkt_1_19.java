package cn.evolvefield.mods.multi.mixin.version;

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

@Mixin(value = ServerGamePacketListenerImpl.class, priority = 1001)
public class MixinServerGamePkt_1_19 {

    @Shadow
    public ServerPlayer player;

    @Inject(method = "broadcastChatMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/network/chat/ChatType$Bound;)V", shift = At.Shift.BEFORE))
    public void handleChat(PlayerChatMessage filteredText, CallbackInfo ci) {
        String s1 = filteredText.decoratedContent().getString();
        Component component2 = Component.translatable("chat.type.text", this.player.getDisplayName(), s1);
        ServerLevelEvents.Server_Chat.invoker().onChat(this.player, s1, component2);
    }
}
