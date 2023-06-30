//#if MC >= 11900
package cn.evole.mods.mcbot.init.mixins;

import cn.evole.mods.mcbot.init.callbacks.IEvents;
import net.minecraft.advancements.Advancement;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Author cnlimiter
 * CreateTime 2023/5/19 0:53
 * Name MixinPlayerAdvancements
 * Description
 */

@Mixin(value = PlayerAdvancements.class, priority = 1001)
public abstract class MixinPlayerAdvancements {

    @Shadow
    private ServerPlayer player;

    @Inject(method = "award", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastSystemMessage(Lnet/minecraft/network/chat/Component;Z)V", shift = At.Shift.AFTER))
    public void PlayerAdvancements_award(Advancement advancement, String string, CallbackInfoReturnable<Boolean> cir) {

        ServerPlayer player = this.player;

        IEvents.PLAYER_ADVANCEMENT.invoker().onAdvancement(player, advancement);
    }

}
//#endif