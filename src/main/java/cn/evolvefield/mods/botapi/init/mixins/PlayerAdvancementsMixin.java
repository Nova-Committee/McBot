package cn.evolvefield.mods.botapi.init.mixins;

import cn.evolvefield.mods.botapi.init.callbacks.PlayerEvents;
import net.minecraft.advancements.Advancement;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 10:13
 * Version: 1.0
 */
@Mixin(value = PlayerAdvancements.class, priority = 1001)
public class PlayerAdvancementsMixin {


    @Shadow
    private ServerPlayer player;

    @Inject(method = "award", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastSystemMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/resources/ResourceKey;)V", shift = At.Shift.AFTER))
    public void PlayerAdvancements_award(Advancement advancement, String string, CallbackInfoReturnable<Boolean> cir) {

        ServerPlayer player = this.player;

        PlayerEvents.PLAYER_ADVANCEMENT.invoker().onAdvancement(player, advancement);
    }


}
