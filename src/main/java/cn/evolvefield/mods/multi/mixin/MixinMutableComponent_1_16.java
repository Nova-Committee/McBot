package cn.evolvefield.mods.multi.mixin;

import cn.evolvefield.mods.multi.api.instance.IMutableComponent;
import cn.evolvefield.mods.multi.init.MixinMutableComponent_1_16_StaticInitializer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;

import java.lang.reflect.InvocationTargetException;

@Mixin(MutableComponent.class)
public interface MixinMutableComponent_1_16 extends IMutableComponent {
    @Override
    public default IMutableComponent ba$append(Component text) {
        try {
            MixinMutableComponent_1_16_StaticInitializer.MutableComponent_append.invoke(this, text);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new UnsupportedOperationException("Failed to invoke method \"MutableComponent::append\" with reflection", e);
        }
        return this;
    }

    @Override
    public default IMutableComponent ba$setStyle(Style style) {
        try {
            MixinMutableComponent_1_16_StaticInitializer.MutableComponent_setStyle.invoke(this, style);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new UnsupportedOperationException("Failed to invoke method \"MutableComponent::setStyle\" with reflection", e);
        }
        return this;
    }

    @Override
    public default IMutableComponent ba$copy() {
        return (IMutableComponent) ((Component) (Object) this).copy();
    }
}
