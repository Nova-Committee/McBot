package cn.evolvefield.mods.multi.mixin.version;

import cn.evolvefield.mods.botapi.init.callbacks.PlayerEvents;
import net.minecraft.advancements.Advancement;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PlayerAdvancements.class, priority = 1001)
public class MixinAdvancements_1_14 {
    @Shadow
    private ServerPlayer player;

    @Inject(
            method = "award",
            remap = false,
            at =
            @At(
                    value = "INVOKE",
                    remap = false,
                    target = "Lnet/minecraft/server/players/class_3324;method_14616(Lnet/minecraft/network/chat/class_2561;Z)V",
                    shift = At.Shift.AFTER
            )
    )
    public void PlayerAdvancements_award(Advancement advancement, String string, CallbackInfoReturnable<Boolean> cir) {

        ServerPlayer player = this.player;

        PlayerEvents.PLAYER_ADVANCEMENT.invoker().onAdvancement(player, advancement);
    }
}
