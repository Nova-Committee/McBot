package cn.evole.mods.multi.api.mapping;

import cn.evole.mods.multi.api.ErrorHandler;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Type;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Function;

public class MappingHelper {

	public static final MappingResolver MAPPING_RESOLVER = FabricLoader.getInstance().getMappingResolver();

	public static final String NAMESPACE_NAMED = "named";
	public static final String NAMESPACE_INTERMEDIARY = "intermediary";

	public static final Function<String, String> CLASS_MAPPER_FUNCTION = (className) -> MAPPING_RESOLVER.mapClassName(NAMESPACE_INTERMEDIARY, className);
	public static final Function<String, String> CLASS_UNMAPPER_FUNCTION = (className) -> MAPPING_RESOLVER.unmapClassName(NAMESPACE_INTERMEDIARY, className);

	public static Class<?> getClassFromType(Type type, Function<String, String> remapFunction) throws ClassNotFoundException {
		if (type == Type.INT_TYPE) {
			return Integer.TYPE;
		} else if (type == Type.VOID_TYPE) {
			return Void.TYPE;
		} else if (type == Type.BOOLEAN_TYPE) {
			return Boolean.TYPE;
		} else if (type == Type.BYTE_TYPE) {
			return Byte.TYPE;
		} else if (type == Type.CHAR_TYPE) {
			return Character.TYPE;
		} else if (type == Type.SHORT_TYPE) {
			return Short.TYPE;
		} else if (type == Type.DOUBLE_TYPE) {
			return Double.TYPE;
		} else if (type == Type.FLOAT_TYPE) {
			return Float.TYPE;
		} else if (type == Type.LONG_TYPE) {
			return Long.TYPE;
		} else if (type.getSort() >= Type.VOID && type.getSort() <= Type.DOUBLE) {
			// it is a primitive but it is non of the known instances?!?
			throw new AssertionError();
		} else if (type.getSort() == Type.ARRAY) {
			Class<?> elementClass = getClassFromType(type.getElementType(), remapFunction);
			for (int i = type.getDimensions(); i > 0; i--) {
				elementClass = Array.newInstance(elementClass, 0).getClass();
			}
			return elementClass;
		} else if (type.getSort() == Type.OBJECT) {
			String className = type.getClassName();
			if (remapFunction != null) {
				className = remapFunction.apply(className);
			}
			return Class.forName(className);
		} else {
			throw new AssertionError();
		}
	}

	public static String createSignature(String signatureFormat, Class<?>... formatFillClasses) {
		Object[] args = new Object[formatFillClasses.length];
		for (int i = 0; i < formatFillClasses.length; i++) {
			args[i] = Type.getType(formatFillClasses[i]);
		}
		return String.format(signatureFormat, args);
	}

	/**
	 * Creates the bytecode signature of the given class.
	 * <p>
	 * Example: java.lang.String -> Ljava/lang/String;
	 *
	 * @param clazz
	 * @return the signature of the given class
	 *
	 */
	public static String toTypeSignature(Class<?> clazz) {
		return Type.getType(clazz).toString();
	}

	/**
	 * This method can handle primitives, arrays and other objects.
	 * It maps all object based classes using the {@code remapFunction}.
	 *
	 * @param clazz
	 * @param remapFunction
	 * @return the signature of the class with all object classes mapped
	 *
	 */
	public static String mapToTypeSignature(Class<?> clazz, Function<String, String> remapFunction) {
		if (clazz.isPrimitive()) {
			return toTypeSignature(clazz);
		} else if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder();
			// not so clean that this conversion that is implemented in Type is here again. But better than convert class -> Type -> getElementType -> class -> map -> ...
			do {
				sb.append("[");
				clazz = clazz.getComponentType();
			} while (clazz.isArray());
			sb.append(mapToTypeSignature(clazz, remapFunction));
			return sb.toString();
		} else {
			// object
			String className = clazz.getName();
			// not so clean that this conversion that should take place in Type takes place here. But it is not exposed and better than map -> class (that does not even exist) -> Type -> String
			String remappedClass = remapFunction.apply(className);
			return "L" + remappedClass.replace('.', '/') + ";";
		}
	}

	public static String createSignatureMapTypes(String signatureFormat, Function<String, String> remapFunction, Class<?>... formatFillClasses) {
		Object[] args = new Object[formatFillClasses.length];
		for (int i = 0; i < formatFillClasses.length; i++) {
			Class<?> clazz = formatFillClasses[i];
			args[i] = mapToTypeSignature(clazz, remapFunction);
		}
		return String.format(signatureFormat, args);
	}

	/**
	 * Can be used for field signatures
	 *
	 * @param signature
	 * @return the type class
	 * @throws ClassNotFoundException
	 */
	public static Class<?> getTypeFromSignature(String signature) throws ClassNotFoundException {
		Type type = Type.getType(signature);
		return getClassFromType(type, null);
	}

	/**
	 * Can be used for method and constructor signatures
	 *
	 * @param signature
	 * @return the return type class
	 * @throws ClassNotFoundException
	 */
	public static Class<?> getReturnTypeFromSignature(String signature) throws ClassNotFoundException {
		Type type = Type.getReturnType(signature);
		return getClassFromType(type, null);
	}

	/**
	 * Can be used for method and constructor signatures
	 *
	 * @param signature
	 * @return the argument type classes
	 * @throws ClassNotFoundException
	 */
	public static Class<?>[] getArgumentTypesFromSignature(String signature) throws ClassNotFoundException {
		Type[] methodArgumentTypes = Type.getArgumentTypes(signature);
		Class<?>[] ret = new Class<?>[methodArgumentTypes.length];
		for (int i = 0; i < methodArgumentTypes.length; i++) {
			ret[i] = getClassFromType(methodArgumentTypes[i], null);
		}
		return ret;
	}

	/**
	 * This method only works when the given {@code className} is a fully qualified class name. Package path with '.'.
	 *
	 * @param className
	 * @param remapFunction
	 * @return the {@link Class} instance of the given {@code className}
	 */
	public static Class<?> mapAndLoadClass(String className, Function<String, String> remapFunction) {
		String mappedClass = remapFunction.apply(className);
		return loadClass(mappedClass);
	}

	public static Class<?> loadClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			ErrorHandler.handleReflectionException(e, "Failed to load class \"%s\" with reflection", className);
		}
		return null;
	}

	public static String mapField(String className, String fieldIntermediary, String signature) {
		String unmappedClass = MAPPING_RESOLVER.unmapClassName(NAMESPACE_INTERMEDIARY, className);
		return MAPPING_RESOLVER.mapFieldName(NAMESPACE_INTERMEDIARY, unmappedClass, fieldIntermediary, signature);
	}

	public static String mapMethod(String className, String methodIntermediary, String signature) {
		String unmappedClass = MAPPING_RESOLVER.unmapClassName(NAMESPACE_INTERMEDIARY, className);
		return MAPPING_RESOLVER.mapMethodName(NAMESPACE_INTERMEDIARY, unmappedClass, methodIntermediary, signature);
	}

	public static Field getField(Class<?> clazz, String remappedFieldName, @Nullable Class<?> valueType) {
		try {
			Field field = clazz.getDeclaredField(remappedFieldName);
			if (valueType != null) {
				if (!field.getType().equals(valueType)) {
					ErrorHandler.handleReflectionException(null, "Failed to load field \"%s\" from class \"%s\" with reflection: There was a field found with that name but has wrong type", remappedFieldName,
						clazz.getSimpleName());
					return null;
				}
			}
			field.setAccessible(true);
			return field;
		} catch (NoSuchFieldException | SecurityException e) {
			ErrorHandler.handleReflectionException(e, "Failed to load field \"%s\" from class \"%s\" with reflection", remappedFieldName, clazz.getSimpleName());
			return null;
		}
	}

	public static Field getField(Class<?> clazz, String remappedFieldName, @Nullable String fieldSignature) {
		try {
			return getField(clazz, remappedFieldName, fieldSignature == null ? null : getTypeFromSignature(fieldSignature));
		} catch (ClassNotFoundException e) {
			ErrorHandler.handleReflectionException(e, "Failed to load field \"%s\" from class \"%s\" with reflection: Invalid field signature \"%s\"", remappedFieldName, clazz.getSimpleName(), fieldSignature);
			return null;
		}
	}

	public static Field mapAndGetField(Class<?> clazz, String fieldIntermediary, Class<?> valueType) {
		String lookupSignature = mapToTypeSignature(valueType, CLASS_UNMAPPER_FUNCTION);
		String remappedFieldName = mapField(clazz.getName(), fieldIntermediary, lookupSignature);
		return getField(clazz, remappedFieldName, valueType);
	}

	public static Method getMethod(Class<?> clazz, String remappedMethodName, @Nullable Class<?> returnType, Class<?>... argumentTypes) {
		try {
			Method method = clazz.getDeclaredMethod(remappedMethodName, argumentTypes);
			if (returnType != null) {
				if (!method.getReturnType().equals(returnType)) {
					ErrorHandler.handleReflectionException(null, "Failed to load method \"%s\" from class \"%s\" with reflection: There was a method found with that name but has wrong return type",
						remappedMethodName,
						clazz.getSimpleName());
					return null;
				}
			}
			method.setAccessible(true);
			return method;
		} catch (NoSuchMethodException | SecurityException e) {
			ErrorHandler.handleReflectionException(e, "Failed to load method \"%s\" from class \"%s\" with reflection", remappedMethodName, clazz.getSimpleName());
			return null;
		}
	}

	public static Method getMethod(Class<?> clazz, String remappedMethodName, String methodSignature) {
		try {
			Class<?>[] argumentTypes = getArgumentTypesFromSignature(methodSignature);
			Class<?> returnType = getReturnTypeFromSignature(methodSignature);
			return getMethod(clazz, remappedMethodName, returnType, argumentTypes);
		} catch (ClassNotFoundException e) {
			ErrorHandler.handleReflectionException(e, "Failed to load method \"%s\" from class \"%s\" with reflection: Invalid method signature \"%s\"", remappedMethodName, clazz.getSimpleName(),
				methodSignature);
			return null;
		}
	}

	public static Method mapAndGetMethod(Class<?> clazz, String methodIntermediary, Class<?> returnType, Class<?>... argumentTypes) {
		StringBuilder sb = new StringBuilder("(");
		for (int i = 0, max = argumentTypes.length; i < max; i++) {
			sb.append(mapToTypeSignature(argumentTypes[i], CLASS_UNMAPPER_FUNCTION));
		}
		sb.append(")");
		sb.append(mapToTypeSignature(returnType, CLASS_UNMAPPER_FUNCTION));

		String lookupSignature = sb.toString();
		String remappedMethodName = mapMethod(clazz.getName(), methodIntermediary, lookupSignature);
		return getMethod(clazz, remappedMethodName, returnType, argumentTypes);
	}

	public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... argumentTypes) {
		try {
			Constructor<T> contructor = clazz.getDeclaredConstructor(argumentTypes);
			contructor.setAccessible(true);
			return contructor;
		} catch (NoSuchMethodException | SecurityException e) {
			ErrorHandler.handleReflectionException(e, "Failed to load constructor from class \"%s\" with reflection", clazz.getSimpleName());
			return null;
		}
	}

	public static <T> Constructor<T> getConstructor(Class<T> clazz, String constructorSignature, boolean checkReturnType) {
		try {
			if (checkReturnType) {
				Class<?> returnType = getReturnTypeFromSignature(constructorSignature);
				if (!clazz.equals(returnType)) {
					ErrorHandler.handleReflectionException(null, "Failed to load constructor for class \"%s\" with reflection: The class does not match the return type of the signature \"%s\"",
						clazz.getSimpleName(), constructorSignature);
					return null;
				}
			}

			Class<?>[] constructorArgumentTypes = getArgumentTypesFromSignature(constructorSignature);
			return getConstructor(clazz, constructorArgumentTypes);
		} catch (ClassNotFoundException e) {
			ErrorHandler.handleReflectionException(e, "Failed to load constructor from class \"%s\" with reflection: Invalid constructor signature \"%s\"", clazz.getSimpleName(), constructorSignature);
			return null;
		}
	}

	public static <T> Constructor<T> getConstructor(Class<T> clazz, String constructorSignature) {
		return getConstructor(clazz, constructorSignature, false);
	}

}
