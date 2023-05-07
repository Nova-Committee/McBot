package cn.evolvefield.mods.multi.api.core.mixinselect;

import cn.evolvefield.mods.multi.MultiVersion;
import cn.evolvefield.mods.multi.api.impl.error.ErrorHandler;
import cn.evolvefield.mods.multi.api.impl.mixinselect.config.MixinSelectAdditiveModConditions;
import cn.evolvefield.mods.multi.api.impl.mixinselect.config.MixinSelectAlternativeModConditions;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

public class MixinSelectConfig {

	private final MixinSelectAdditiveModConditions defaultConditions;
	/**
	 * mixinPath -> alternative mod conditions
	 */
	private final HashMap<String, MixinSelectAlternativeModConditions> mixinConditions;

	public MixinSelectConfig(HashMap<String, MixinSelectAlternativeModConditions> mixinConditions, MixinSelectAdditiveModConditions defaultConditions) {
		this.mixinConditions = mixinConditions;
		this.defaultConditions = defaultConditions;
	}

	public boolean isMixinAllowed(String mixinClassPath, HashMap<String, Version> modsWithVersion) {
		MixinSelectAlternativeModConditions conditions = mixinConditions.get(mixinClassPath);
		if (conditions == null) {
			// we allow unknown mixins
			return true;
		}
		return conditions.matches(modsWithVersion, defaultConditions);
	}

	private static String getClassFileName(String className) {
		return className.replace('.', '/').concat(".class");
	}

	private static boolean isMixinClassPresent(String mixinClassName, ClassLoader classLoader) {
		// can not use Class.forName here even when initialize is false because that triggers the mixin processor in applying the mixins and that loads the target class. But that is too early
		String fileName = getClassFileName(mixinClassName);
		URL url = classLoader.getResource(fileName);
		return url != null;
	}

	public List<String> getAllowedMixins(String mixinPackage, ClassLoader classLoader, HashMap<String, Version> modsWithVersion) {
		List<String> mixins = new ArrayList<>();
		for (Entry<String, MixinSelectAlternativeModConditions> entry : mixinConditions.entrySet()) {
			String mixinPath = entry.getKey();
			String mixinClassName = mixinPackage + "." + mixinPath;
			if (isMixinClassPresent(mixinClassName, classLoader)) {
				if (entry.getValue().matches(modsWithVersion, defaultConditions)) {
					mixins.add(mixinPath);
				}
			} else {
				ErrorHandler.logf(Level.WARN, "mixinSelect config contains mixin path that does not exist: %s", mixinPath);
			}
		}
		return mixins;
	}

	public static final String MIXIN_SELECT_CONFIG_RESOURCE_PATH = "mixin/mixinSelect.config.json";

	public static MixinSelectConfig loadMixinSelectConfig(ModContainer mod) {
		Optional<Path> path = mod.findPath(MIXIN_SELECT_CONFIG_RESOURCE_PATH);
		if (!path.isPresent()) {
			throw new RuntimeException("Could not find mixinSelect config for mod \"" + mod.getMetadata().getId() + "\": " + MIXIN_SELECT_CONFIG_RESOURCE_PATH);
		}
		return loadMixinSelectConfig(path.get());
	}

	public static MixinSelectConfig loadMixinSelectConfig(Path path) {
		try {
			String mixinSelectConfigJson = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
			return loadMixinSelectConfig(mixinSelectConfigJson);
		} catch (IOException e) {
			throw new RuntimeException("Could not load mixinSelect config: " + path.toString(), e);
		}
	}

	public static MixinSelectConfig loadMixinSelectConfig(String mixinSelectConfigJson) {
		return MultiVersion.GSON.fromJson(mixinSelectConfigJson, MixinSelectConfig.class);
	}

}
