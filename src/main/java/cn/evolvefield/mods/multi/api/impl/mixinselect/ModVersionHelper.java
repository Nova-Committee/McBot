package cn.evolvefield.mods.multi.api.impl.mixinselect;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.ModMetadata;

import java.util.Collection;
import java.util.HashMap;

public class ModVersionHelper {

	public static HashMap<String, Version> getAllModsWithVersion(FabricLoader loader, boolean addEnvironment) {
		HashMap<String, Version> modsWithVersion = new HashMap<>();

		Collection<ModContainer> modContainers = loader.getAllMods();
		for (ModContainer modContainer : modContainers) {
			ModMetadata metadata = modContainer.getMetadata();
			modsWithVersion.put(metadata.getId(), metadata.getVersion());
		}

		if (addEnvironment) {
			try {
				Version envTypeVersion = Version.parse(loader.getEnvironmentType().name());
				// upper case characters are invalid for modid but here we can use that in the config and we will never have a conflict with an other mod having this modid
				modsWithVersion.put("ENVIRONMENT", envTypeVersion);
			} catch (VersionParsingException e) {
			}
		}

		return modsWithVersion;
	}

}
