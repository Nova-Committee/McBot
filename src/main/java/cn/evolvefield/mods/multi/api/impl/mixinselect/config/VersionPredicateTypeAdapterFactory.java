package cn.evolvefield.mods.multi.api.impl.mixinselect.config;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import de.klotzi111.util.GsonUtil.typeadapter.AbstractEnhancedTypeAdapterFactory;
import de.klotzi111.util.GsonUtil.typeadapter.JsonDeSerializerBundle;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.version.VersionPredicate;

import java.lang.reflect.Type;

public class VersionPredicateTypeAdapterFactory extends AbstractEnhancedTypeAdapterFactory {

	private static class VersionPredicateAdapter implements JsonSerializer<VersionPredicate>, JsonDeserializer<VersionPredicate> {

		public VersionPredicateAdapter() {
		}

		@Override
		public VersionPredicate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			if (json.isJsonNull()) {
				return null;
			}

			String versionString = context.deserialize(json, String.class);
			try {
				return VersionPredicate.parse(versionString);
			} catch (VersionParsingException e) {
				throw new JsonParseException("Invalid VersionPredicate: " + versionString);
			}
		}

		@Override
		public JsonElement serialize(VersionPredicate src, Type typeOfSrc, JsonSerializationContext context) {
			if (src == null) {
				return JsonNull.INSTANCE;
			}

			return context.serialize(src.toString());
		}
	}

	private <T> boolean isApplicableForType(TypeToken<T> type) {
		return VersionPredicate.class.isAssignableFrom(type.getRawType());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T> JsonDeSerializerBundle<T> makeSerializers(Gson gson, TypeToken<T> type) {
		if (!isApplicableForType(type)) {
			return null;
		}
		VersionPredicateAdapter fd = new VersionPredicateAdapter();
		return (JsonDeSerializerBundle<T>) new JsonDeSerializerBundle<VersionPredicate>(fd, fd, null);
	}

}
