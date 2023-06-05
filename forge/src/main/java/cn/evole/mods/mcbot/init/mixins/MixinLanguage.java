//#if MC >= 11900
package cn.evole.mods.mcbot.init.mixins;

import net.minecraft.locale.Language;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Author cnlimiter
 * CreateTime 2023/5/19 0:53
 * Name MixinLanguage
 * Description
 */
@Mixin(Language.class)
public abstract class MixinLanguage {
}
//#endif