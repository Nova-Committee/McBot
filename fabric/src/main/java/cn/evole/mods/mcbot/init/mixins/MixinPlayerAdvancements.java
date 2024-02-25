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

//兼容1.20.1版本vanish
//#if MC == 12001
//$$ import cn.evole.mods.mcbot.init.compat.VanishAPI;
//#endif

/**
 * Author cnlimiter
 * CreateTime 2023/6/6 0:43
 * Name MixinPlayerAdvancements
 * Description
 */

@Mixin(value = PlayerAdvancements.class, priority = 1001)
public abstract class MixinPlayerAdvancements {
    @Shadow
    private ServerPlayer player;
    //#if MC < 11900
    @Inject(method = "award", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/ChatType;Ljava/util/UUID;)V", shift = At.Shift.AFTER))
    //#else
    //$$ @Inject(method = "award", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastSystemMessage(Lnet/minecraft/network/chat/Component;Z)V", shift = At.Shift.AFTER))
    //#endif
    public void PlayerAdvancements_award(Advancement advancement, String string, CallbackInfoReturnable<Boolean> cir) {

        ServerPlayer player = this.player;

        //#if MC == 12001
        //$$ if (VanishAPI.isVanished(player)) return;
        //#endif
        IEvents.PLAYER_ADVANCEMENT.invoker().onAdvancement(player, advancement);
    }
}
