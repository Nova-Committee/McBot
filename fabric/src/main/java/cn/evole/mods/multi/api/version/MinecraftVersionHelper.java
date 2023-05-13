package cn.evole.mods.multi.api.version;

import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.loader.api.*;

import java.util.Optional;

public class MinecraftVersionHelper {

	public static Version MINECRAFT_VERSION = null;
	public static SemanticVersion SEMANTIC_MINECRAFT_VERSION = null;

	public static Object2ObjectOpenHashMap<String, SemanticVersion> SEMANTIC_VERSION_CACHE = new Object2ObjectOpenHashMap<>();
	public static Object2BooleanOpenHashMap<String> IS_AT_LEAST_SEMANTIC_VERSION_CACHE = new Object2BooleanOpenHashMap<>();

	static {
		getMinecraftVersion();
	}

	public static String getCurrentVersion(){
		return MINECRAFT_VERSION.getFriendlyString();
	}


	public static boolean isMCVersion(String version){
		return MINECRAFT_VERSION.getFriendlyString().equals(version);
	}

	public static boolean isMCVersionAtLeast(String versionToBeAtLeast) {
		if (IS_AT_LEAST_SEMANTIC_VERSION_CACHE.containsKey(versionToBeAtLeast)) {
			return IS_AT_LEAST_SEMANTIC_VERSION_CACHE.getBoolean(versionToBeAtLeast);
		}
		return isMCVersionAtLeast(parseSemanticVersion(versionToBeAtLeast));
	}

	// we need to use the deprecated compareTo method because older minecraft versions do not support the new/non deprecated way
	@SuppressWarnings("deprecation")
	public static boolean isMCVersionAtLeast(SemanticVersion versionToBeAtLeast) {
		boolean isAtLeast = SEMANTIC_MINECRAFT_VERSION.compareTo(versionToBeAtLeast) >= 0;
		IS_AT_LEAST_SEMANTIC_VERSION_CACHE.put(versionToBeAtLeast.getFriendlyString(), isAtLeast);
		return isAtLeast;
	}

	public static SemanticVersion parseSemanticVersion(String version) {
		SemanticVersion cached = SEMANTIC_VERSION_CACHE.get(version);
		if (cached != null) {
			return cached;
		}
		try {
			SemanticVersion semanticVersion = SemanticVersion.parse(version);
			SEMANTIC_VERSION_CACHE.put(version, semanticVersion);
			return semanticVersion;
		} catch (VersionParsingException e) {
			// this should really never happen, because we carefully craft the version strings statically (at compile time)
			throw new IllegalStateException("Could not parse semantic version for minecraft version: " + version, e);
		}
	}

	private static void getMinecraftVersion() {
		Optional<ModContainer> minecraftModContainer = FabricLoader.getInstance().getModContainer("minecraft");
		if (!minecraftModContainer.isPresent()) {
			throw new IllegalStateException("Minecraft not available?!?");
		}
		MINECRAFT_VERSION = minecraftModContainer.get().getMetadata().getVersion();
		String minecraftVersionString = MINECRAFT_VERSION.getFriendlyString();
		IS_AT_LEAST_SEMANTIC_VERSION_CACHE.put(minecraftVersionString, true);
		if (MINECRAFT_VERSION instanceof SemanticVersion) {
			SEMANTIC_MINECRAFT_VERSION = (SemanticVersion) MINECRAFT_VERSION;
			SEMANTIC_VERSION_CACHE.put(minecraftVersionString, SEMANTIC_MINECRAFT_VERSION);
		} else {
			throw new IllegalStateException("Minecraft version is no SemVer!");
		}
	}

}
