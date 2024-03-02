package cn.evole.mods.mcbot.init.mixins;

import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import cn.evole.mods.mcbot.init.callbacks.IEvents;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.server.network.TextFilter;
//#if MC >= 11902
//$$ import net.minecraft.network.chat.PlayerChatMessage;
//#endif
/**
 * Author cnlimiter
 * CreateTime 2023/5/19 0:54
 * Name MixinServerGamePktImpl
 * Description
 */

@Mixin(value = ServerGamePacketListenerImpl.class, priority = 1001)
public abstract class MixinServerGamePktImpl {
    //#if MC < 11700
    @Shadow
    public ServerPlayer player;
    @Inject(method = "handleChat(Ljava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/ChatType;Ljava/util/UUID;)V", shift = At.Shift.BEFORE))
    public void mcbot$handleChat(String string, CallbackInfo ci) {
        IEvents.SERVER_CHAT.invoker().onChat(this.player, string);
    }
    //#elseif MC < 11900
    //$$ @Shadow
    //$$ public ServerPlayer player;
    //$$ @Inject(method = "handleChat(Lnet/minecraft/server/network/TextFilter$FilteredText;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastMessage(Lnet/minecraft/network/chat/Component;Ljava/util/function/Function;Lnet/minecraft/network/chat/ChatType;Ljava/util/UUID;)V", shift = At.Shift.BEFORE))
    //$$ public void mcbot$handleChat(TextFilter.FilteredText filteredText, CallbackInfo ci) {
    //$$     String s1 = filteredText.getRaw();
    //$$     IEvents.SERVER_CHAT.invoker().onChat(this.player, s1);
    //$$ }
    //#elseif MC < 11903
    //$$@Shadow
    //$$public ServerPlayer player;
    //$$@Inject(method = "broadcastChatMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/network/chat/ChatType$Bound;)V", shift = At.Shift.BEFORE))
    //$$public void mcbot$handleChat(PlayerChatMessage filteredText, CallbackInfo ci) {
    //$$    String s1 = filteredText.serverContent().getString();
    //$$    IEvents.SERVER_CHAT.invoker().onChat(this.player, s1);
    //$$}
    //#else
    //$$@Shadow
    //$$public ServerPlayer player;
    //$$@Inject(method = "broadcastChatMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/network/chat/ChatType$Bound;)V", shift = At.Shift.BEFORE))
    //$$public void mcbot$handleChat(PlayerChatMessage filteredText, CallbackInfo ci) {
    //$$    String s1 = filteredText.decoratedContent().getString();
    //$$    IEvents.SERVER_CHAT.invoker().onChat(this.player, s1);
    //$$}
    //#endif

}




