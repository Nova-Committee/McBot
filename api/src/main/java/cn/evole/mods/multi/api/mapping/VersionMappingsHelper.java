package cn.evole.mods.multi.api.mapping;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.loader.api.MappingResolver;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;
import net.fabricmc.loader.impl.util.version.VersionParser;
import net.fabricmc.mapping.tree.TinyMappingFactory;
import net.fabricmc.mapping.tree.TinyTree;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class VersionMappingsHelper {

	public static final Constructor<?> MappingResolverImpl_constructor;

	static {
		Constructor<?> local_MappingResolverImpl_constructor = null;
		try {
			Class<?> MappingResolverImpl_class = Class.forName("net.fabricmc.loader.impl.MappingResolverImpl");
			local_MappingResolverImpl_constructor = MappingResolverImpl_class.getDeclaredConstructor(Supplier.class, String.class);
			local_MappingResolverImpl_constructor.setAccessible(true);
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			throw new RuntimeException("Failed to load constructor from class \"MappingResolverImpl\" with reflection", e);
		}
		MappingResolverImpl_constructor = local_MappingResolverImpl_constructor;
	}

	public static MappingResolver createMappingResolver(TinyTree tinyTree, String namespace) {
		try {
			Supplier<TinyTree> supplier = () -> tinyTree;
			return (MappingResolver) MappingResolverImpl_constructor.newInstance(supplier, namespace);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException("Failed to instantiate class \"MappingResolverImpl\" with reflection", e);
		}
	}

	private static final Map<Path, TinyTree> MAPPINGS_CACHE = new Object2ObjectOpenHashMap<>();

	private static final String MAPPINGS_RESOURCE_PATH_FORMAT = "/mappings/mapping-%s.tiny";

	public static TinyTree getMappings(ModContainer mod, Version minecraftVersion) {
		return getMappings(mod, minecraftVersion.toString());
	}

	public static TinyTree getMappings(ModContainer mod, String minecraftVersion) {
		String resourcePath = String.format(MAPPINGS_RESOURCE_PATH_FORMAT, minecraftVersion);
		Optional<Path> path = mod.findPath(resourcePath);
		if (!path.isPresent()) {
			throw new RuntimeException("Could not find mapping resource for mod \"" + mod.getMetadata().getId() + "\": " + resourcePath);
		}
		return getMappingsFromPath(path.get());
	}

	public static TinyTree getMappingsFromPath(Path path) {
		TinyTree mappings = MAPPINGS_CACHE.get(path);
		if (mappings == null) {
			mappings = loadMappings(path);
			MAPPINGS_CACHE.put(path, mappings);
		}
		return mappings;
	}

	private static TinyTree loadMappings(Path path) {
		TinyTree mappings = null;
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			// long time = System.currentTimeMillis();
			mappings = TinyMappingFactory.loadWithDetection(reader);
			// Log.debug(LogCategory.MAPPINGS, "Loading extra mappings took %d ms", System.currentTimeMillis() - time);
		} catch (IOException e) {
			throw new RuntimeException("Could not parse tiny mapping", e);
		}

		// if (mappings == null) {
		// Log.info(LogCategory.MAPPINGS, "Could not load extra mappings: " + resourcePath);
		// }
		return mappings;
	}

	// Here it would be more precise if we would use the mapping name like '1.19+build.1' but at runtime fabric does not know the mapping version it has
	private static final String COMPILED_WITH_MINECRAFT_SOURCE_VERSION_NAME = "compiledWithMinecraftSourceVersion";
	private static final String COMPILED_WITH_MINECRAFT_SOURCE_VERSION_RESOURCE_PATH = String.format("/mappings/%s.txt", COMPILED_WITH_MINECRAFT_SOURCE_VERSION_NAME);

	public static Version getCompiledWithMinecraftSourceVersion(ModContainer mod) {
		Version version = null;
		Optional<Path> path = mod.findPath(COMPILED_WITH_MINECRAFT_SOURCE_VERSION_RESOURCE_PATH);
		if (!path.isPresent()) {
			throw new RuntimeException("Could not find resource: " + COMPILED_WITH_MINECRAFT_SOURCE_VERSION_RESOURCE_PATH);
		}

		String cwmsvString = null;
		try {
			cwmsvString = new String(Files.readAllBytes(path.get()), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException("Failed to read resource: " + COMPILED_WITH_MINECRAFT_SOURCE_VERSION_RESOURCE_PATH, e);
		}

		try {
			version = VersionParser.parse(cwmsvString, false);
		} catch (VersionParsingException e) {
			throw new RuntimeException("Could not parse version for \"" + COMPILED_WITH_MINECRAFT_SOURCE_VERSION_NAME + "\": " + cwmsvString, e);
		}
		return version;
	}

	/**
	 * for debugging
	 *
	 * @param dumpPath
	 */
	public static void dumpCurrentMappings(Path dumpPath) {
		Exception exception = null;
		InputStream is = FabricLauncherBase.class.getClassLoader().getResourceAsStream("mappings/mappings.tiny");
		if (is != null) {
			try {
				OutputStream os = Files.newOutputStream(dumpPath);
				IOUtils.copy(is, os);

				os.close();
				return;
			} catch (IOException e) {
				exception = e;
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					// ignore close failed
				}
			}
		}

		String message = is == null ? "No mapping found!" : exception.toString();
		try {
			Files.write(dumpPath, message.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
		}
	}

}
