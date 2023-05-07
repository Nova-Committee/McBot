package cn.evolvefield.mods.multi.mixin;

import cn.evolvefield.mods.multi.api.core.mapping.MappingHelper;
import cn.evolvefield.mods.multi.api.instance.IMutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Mixin(targets = "net/minecraft/network/chat/BaseComponent")
public abstract class MixinBaseComponent_1_14 implements IMutableComponent {

    @Unique
    private static final Method BaseText_append;

    @Unique
    private static final Method BaseText_setStyle;

    @Unique
    private static final Method BaseText_deepCopy;

    static {
        String CLASS_NAME_BaseText = "net.minecraft.class_2554"; // "net.minecraft.network.chat.BaseComponent";
        Class<?> BaseText_class = MappingHelper.mapAndLoadClass(CLASS_NAME_BaseText, MappingHelper.CLASS_MAPPER_FUNCTION);

        BaseText_append = MappingHelper.mapAndGetMethod(BaseText_class, "append", Component.class, Component.class);
        BaseText_setStyle = MappingHelper.mapAndGetMethod(BaseText_class, "setStyle", Component.class, Style.class);
        BaseText_deepCopy = MappingHelper.mapAndGetMethod(Component.class, "deepCopy", Component.class);
    }

    @Override
    public IMutableComponent ba$append(Component text) {
        try {
            BaseText_append.invoke(this, text);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new UnsupportedOperationException("Failed to invoke method \"BaseText::append\" with reflection", e);
        }
        return this;
    }

    @Override
    public IMutableComponent ba$setStyle(Style style) {
        try {
            BaseText_setStyle.invoke(this, style);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new UnsupportedOperationException("Failed to invoke method \"BaseText::setStyle\" with reflection", e);
        }
        return this;
    }

    @Override
    public IMutableComponent ba$copy() {
        try {
            // the upcast to IMutableComponent despite the return value being Text is a bit unsafe
            return (IMutableComponent) BaseText_deepCopy.invoke(this);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new UnsupportedOperationException("Failed to invoke method \"BaseText::deepCopy\" with reflection", e);
        }
    }
}
