package cn.evolvefield.mods.multi.mixin;

import cn.evolvefield.mods.multi.api.instance.IMutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MutableComponent.class)
public abstract class MixinMutableComponent_1_19 implements IMutableComponent {
    @Override
    public IMutableComponent ba$append(Component text) {
        ((MutableComponent) (Object) this).append(text);
        return this;
    }

    @Override
    public IMutableComponent ba$setStyle(Style style) {
        ((MutableComponent) (Object) this).setStyle(style);
        return this;
    }

    @Override
    public IMutableComponent ba$copy() {
        return (IMutableComponent) ((Component) (Object) this).copy();
    }
}
