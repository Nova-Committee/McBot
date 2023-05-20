package cn.evole.mods.mcbot.init.mixins;

import cn.evole.mods.mcbot.init.callbacks.IEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Author cnlimiter
 * CreateTime 2023/5/21 0:17
 * Name MixinServerPlayer
 * Description
 */

@Mixin(value = ServerPlayer.class, priority = 1001)
public abstract class MixinServerPlayer {
    @Inject(method = "die(Lnet/minecraft/world/damagesource/DamageSource;)V", at = @At(value = "HEAD"))
    public void ServerPlayer_die(DamageSource damageSource, CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer) (Object) this;

        IEvents.PLAYER_DEATH.invoker().onDeath(damageSource, player);
    }
}
