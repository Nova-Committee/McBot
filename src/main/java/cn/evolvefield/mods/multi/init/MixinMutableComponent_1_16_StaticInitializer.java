package cn.evolvefield.mods.multi.init;

import cn.evolvefield.mods.multi.api.core.mapping.MappingHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

import java.lang.reflect.Method;

public class MixinMutableComponent_1_16_StaticInitializer {
    public static final Method MutableComponent_append;

    public static final Method MutableComponent_setStyle;

    static {
        String CLASS_NAME_MutableComponent = "net.minecraft.class_5250"; // "net.minecraft.text.MutableComponent";
        Class<?> MutableComponent_class = MappingHelper.mapAndLoadClass(CLASS_NAME_MutableComponent, MappingHelper.CLASS_MAPPER_FUNCTION);

        MutableComponent_append = MappingHelper.mapAndGetMethod(MutableComponent_class, "method_10852", MutableComponent.class, Component.class);
        MutableComponent_setStyle = MappingHelper.mapAndGetMethod(MutableComponent_class, "method_10862", MutableComponent.class, Style.class);
    }
}
