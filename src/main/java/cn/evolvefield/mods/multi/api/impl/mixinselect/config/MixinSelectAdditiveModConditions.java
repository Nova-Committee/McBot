package cn.evolvefield.mods.multi.api.impl.mixinselect.config;

import de.klotzi111.util.GsonUtil.interfaces.inlinefield.InlineField;
import de.klotzi111.util.GsonUtil.interfaces.inlinefield.InlineFieldJsonObject;
import net.fabricmc.loader.api.Version;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The conditions are logically AND-ed
 */
public class MixinSelectAdditiveModConditions implements InlineFieldJsonObject {
	@InlineField
	public final List<ModCondition> conditions;

	public MixinSelectAdditiveModConditions(List<ModCondition> conditions) {
		this.conditions = conditions;
	}

	public boolean matches(@NotNull HashMap<String, Version> modsWithVersion, MixinSelectAdditiveModConditions defaultConditions) {
		Set<String> checkedModIds = new HashSet<>();
		if (conditions != null) {
			for (ModCondition modCondition : conditions) {
				checkedModIds.add(modCondition.modId);
				Version currentModVersion = modsWithVersion.get(modCondition.modId);
				if (!modCondition.matches(currentModVersion)) {
					return false;
				}
			}
		}
		// check non overriden defaults
		if (defaultConditions != null) {
			for (ModCondition modCondition : defaultConditions.conditions) {
				if (!checkedModIds.contains(modCondition.modId)) {
					// if the modId was not already a explicitly specified condition
					Version currentModVersion = modsWithVersion.get(modCondition.modId);
					if (!modCondition.matches(currentModVersion)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public ModCondition getModConditionByModId(String modId) {
		if (conditions == null) {
			return null;
		}
		for (ModCondition modCondition : conditions) {
			if (modCondition.modId.equals(modId)) {
				return modCondition;
			}
		}
		return null;
	}

}
