package cn.evolvefield.mods.multi.api.impl.mixinselect.config;

import de.klotzi111.util.GsonUtil.interfaces.inlinefield.InlineField;
import de.klotzi111.util.GsonUtil.interfaces.inlinefield.InlineFieldJsonObject;
import net.fabricmc.loader.api.Version;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

/**
 * The conditions are logically OR-ed
 */
public class MixinSelectAlternativeModConditions implements InlineFieldJsonObject {
	@InlineField
	public final List<MixinSelectAdditiveModConditions> alternatives;

	public MixinSelectAlternativeModConditions(List<MixinSelectAdditiveModConditions> alternatives) {
		this.alternatives = alternatives;
	}

	public boolean matches(@NotNull HashMap<String, Version> modsWithVersion, MixinSelectAdditiveModConditions defaultConditions) {
		if (alternatives == null) {
			return false;
		}
		for (MixinSelectAdditiveModConditions alternative : alternatives) {
			if (alternative.matches(modsWithVersion, defaultConditions)) {
				return true;
			}
		}
		return false;
	}

}
