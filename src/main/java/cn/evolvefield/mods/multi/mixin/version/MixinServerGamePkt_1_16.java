package cn.evolvefield.mods.multi.mixin.version;

import cn.evolvefield.mods.botapi.init.callbacks.ServerLevelEvents;
import cn.evolvefield.mods.multi.common.ComponentWrapper;
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
public class MixinServerGamePkt_1_16 {

    @Shadow
    public ServerPlayer player;

    @Inject(
            method = "handleChat(Ljava/lang/String;)V",
            remap = false,
            at =
            @At(
                    value = "INVOKE",
                    remap = false,
                    target = "Lnet/minecraft/server/players/class_3324;method_14616(Lnet/minecraft/network/chat/class_2561;Lnet/minecraft/network/chat/class_2556;Ljava/util/UUID;)V",
                    shift = At.Shift.BEFORE
            ))
    public void handleChat(String string, CallbackInfo ci) {
        Component component2 = ComponentWrapper.literal(String.format("<%s> %s", this.player.getDisplayName().getString(), string));
        ServerLevelEvents.Server_Chat.invoker().onChat(this.player, string, component2);
    }
}
