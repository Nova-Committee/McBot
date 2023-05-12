package cn.evole.mods.multi.common;

import cn.evole.mods.multi.api.ErrorHandler;
import cn.evole.mods.multi.api.mapping.MappingHelper;
import net.minecraft.network.chat.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static cn.evole.mods.multi.Const.IS_1_19;


public class ComponentWrapper {


    private static final Constructor<?> LiteralComponentContent_constructor;
    private static final Constructor<?> TranslatableComponentContent_constructor;
    private static final Method Component_asString;

    static {
        if (!IS_1_19) {
            String CLASS_NAME_LiteralContents = "net.minecraft.class_2585"; // "net.minecraft.chat.LiteralContents";
            Class<?> LiteralContents_class = MappingHelper.mapAndLoadClass(CLASS_NAME_LiteralContents, MappingHelper.CLASS_MAPPER_FUNCTION);
            LiteralComponentContent_constructor = MappingHelper.getConstructor(LiteralContents_class, String.class);

            String CLASS_NAME_TranslatableContents = "net.minecraft.class_2588"; // "net.minecraft.chat.TranslatableContents";
            Class<?> TranslatableContents_class = MappingHelper.mapAndLoadClass(CLASS_NAME_TranslatableContents, MappingHelper.CLASS_MAPPER_FUNCTION);
            TranslatableComponentContent_constructor = MappingHelper.getConstructor(TranslatableContents_class, String.class);

            Component_asString = MappingHelper.mapAndGetMethod(Component.class, "method_10851", String.class);
        } else {
            LiteralComponentContent_constructor = null;
            TranslatableComponentContent_constructor = null;
            Component_asString = null;
        }

    }


    public static Component literal(String string) {
        if (IS_1_19) {
            return Component.literal(string);
        } else {
            try {
                Object[] instanceArgs = new Object[] {string};
                return (Component) LiteralComponentContent_constructor.newInstance(instanceArgs);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                ErrorHandler.handleReflectionException(e, "Failed to create new instance of \"%s\"", "LiteralContents");
            }
            return empty();
        }
    }


    private static final Component EMPTY_LITERAL = literal("");

    public static Component empty() {
        if (IS_1_19) {
            return Component.empty();
        } else {
            return EMPTY_LITERAL;
        }
    }
    public static String getAsString(Component text) {
        if (IS_1_19) {
            return text.getString();
        } else {
            try {
                return (String) Component_asString.invoke(text);
            } catch (IllegalAccessException | InvocationTargetException e) {
                ErrorHandler.handleReflectionException(e, "Failed to invoke \"%s\"", "Component::asString");
            }
            return "";
        }
    }
}
