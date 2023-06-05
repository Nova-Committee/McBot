package cn.evole.mods.mcbot.init.mixins;

import net.minecraft.server.PlayerAdvancements;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Author cnlimiter
 * CreateTime 2023/6/6 0:43
 * Name MixinPlayerAdvancements
 * Description
 */

@Mixin(value = PlayerAdvancements.class, priority = 1001)
public abstract class MixinPlayerAdvancements {
}
