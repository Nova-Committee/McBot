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

//#if MC >= 12002
//$$ import net.minecraft.advancements.AdvancementHolder;
//#endif

//兼容vanish
import cn.evole.mods.mcbot.init.compat.vanish.VanishAPI;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

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
    @Inject(method = "award", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)

    //#if MC >= 12002
    //$$ public void mcbot$award(AdvancementHolder advancement, String string, CallbackInfoReturnable<Boolean> cir) {
    //#else
    public void mcbot$award(Advancement advancement, String string, CallbackInfoReturnable<Boolean> cir) {
    //#endif



        ServerPlayer player = this.player;

        if (VanishAPI.isVanished(player)) return;
        //#if MC >= 12002
        //$$ IEvents.PLAYER_ADVANCEMENT.invoker().onAdvancement(player, advancement.value());
        //#else
        IEvents.PLAYER_ADVANCEMENT.invoker().onAdvancement(player, advancement);
        //#endif
    }
}
