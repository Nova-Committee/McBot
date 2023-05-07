package cn.evolvefield.mods.multi.common;

import cn.evolvefield.mods.multi.api.core.mapping.MappingHelper;
import cn.evolvefield.mods.multi.api.core.version.MinecraftVersionHelper;
import cn.evolvefield.mods.multi.api.impl.error.ErrorHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class ComponentWrapper {

    private static final boolean IS_1_19 = MinecraftVersionHelper.isMCVersionAtLeast("1.19");

    private static final Constructor<?> LiteralComponentContent_constructor;
    private static final Constructor<?> TranslatableComponentContent_constructor;

    private static final Constructor<Style> Style_constructor;
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

        if (!MinecraftVersionHelper.isMCVersionAtLeast("1.16")) {
            Style_constructor = MappingHelper.getConstructor(Style.class);
        } else {
            Style_constructor = null;
        }
    }

    public static Style emptyStyle() {
        if (!MinecraftVersionHelper.isMCVersionAtLeast("1.16")) {
            try {
                Object[] instanceArgs = new Object[] {};
                return Style_constructor.newInstance(instanceArgs);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                ErrorHandler.handleReflectionException(e, "Failed to create new instance of \"%s\"", "Style");
            }
            return null;
        } else {
            return Style.EMPTY;
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

    public static Component translatable(String key) {
        if (IS_1_19) {
            return Component.translatable(key);
        } else {
            Component component = null;
            try {
                Object[] instanceArgs = new Object[] {key};
                component = (Component) TranslatableComponentContent_constructor.newInstance(instanceArgs);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                ErrorHandler.handleReflectionException(e, "Failed to create new instance of \"%s\"", "TranslatableContents");
            }
            return component;
        }
    }

    public static Component translatable(String key, Object... args) {
        if (IS_1_19) {
            return Component.translatable(key, args);
        } else {
            return (Component) (Object) new TranslatableContents(key, args);
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

    public static Component keybind(String string) {
        if (IS_1_19) {
            return Component.keybind(string);
        } else {
            return (Component) (Object) new KeybindContents(string);
        }
    }

    public static Component nbt(String rawPath, boolean interpret, Optional<Component> separator, DataSource dataSource) {
        if (IS_1_19) {
            return Component.nbt(rawPath, interpret, separator, dataSource);
        } else {
            return (Component) (Object) new NbtContents(rawPath, interpret, separator, dataSource);
        }
    }

    public static Component score(String name, String objective) {
        if (IS_1_19) {
            return Component.score(name, objective);
        } else {
            return (Component) (Object) new ScoreContents(name, objective);
        }
    }

    public static Component selector(String pattern, Optional<Component> separator) {
        if (IS_1_19) {
            return Component.selector(pattern, separator);
        } else {
            return (Component) (Object) new SelectorContents(pattern, separator);
        }
    }

    // this should return the same as IMutableComponent#fmvh$asString
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
