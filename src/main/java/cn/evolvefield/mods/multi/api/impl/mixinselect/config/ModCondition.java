package cn.evolvefield.mods.multi.api.impl.mixinselect.config;

import de.klotzi111.util.GsonUtil.interfaces.map.MapJsonObject;
import de.klotzi111.util.GsonUtil.interfaces.map.MapKey;
import de.klotzi111.util.GsonUtil.interfaces.map.MapValue;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.version.VersionPredicate;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModCondition implements MapJsonObject {
	@MapKey
	@NotNull
	public final String modId;
	@MapValue
	public final Collection<VersionPredicate> versionAlternatives;

	public ModCondition(@NotNull String modId, Collection<VersionPredicate> versionAlternatives) {
		this.modId = modId;
		this.versionAlternatives = versionAlternatives;
	}

	public ModCondition(@NotNull String modId, List<String> versionAlternatives) throws VersionParsingException {
		this.modId = modId;
		this.versionAlternatives = parseVersionPredicatesNullAware(versionAlternatives);
	}

	public boolean matches(Version version) {
		if (versionAlternatives == null) {
			return false;
		}
		for (VersionPredicate predicate : versionAlternatives) {
			if (predicate == null ? version == null : (version != null && predicate.test(version))) {
				return true;
			}
		}
		return false;
	}

	private static Set<VersionPredicate> parseVersionPredicatesNullAware(Collection<String> predicates) throws VersionParsingException {
		Set<VersionPredicate> ret = new HashSet<>(predicates.size());

		for (String version : predicates) {
			if (version == null) {
				ret.add(null);
			} else {
				ret.add(VersionPredicate.parse(version));
			}
		}

		return ret;
	}

}
