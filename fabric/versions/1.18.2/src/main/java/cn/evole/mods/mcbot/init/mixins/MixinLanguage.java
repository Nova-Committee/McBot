//#if MC >= 11600
package cn.evole.mods.mcbot.init.mixins;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.locale.Language;
import net.minecraft.util.GsonHelper;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;


/**
 * @author Xujiayao
 */
@Mixin(Language.class)
public abstract class MixinLanguage {

	@Final
	@Shadow
	private static Gson GSON;

	@Final
	@Shadow
	private static Pattern UNSUPPORTED_FORMAT_PATTERN;

	@Shadow @Final private static Logger LOGGER;


	@ModifyVariable(method = "loadDefault", at = @At("STORE"), ordinal = 0)
	private static ImmutableMap.Builder<String, String> mapInjected(ImmutableMap.Builder<String, String> originalMap) {
		LinkedHashMap<String, String> map = new LinkedHashMap<>(originalMap.build());

		FabricLoader.getInstance().getAllMods().forEach(modContainer -> {
			Optional<Path> optional = modContainer.findPath("/assets/" + modContainer.getMetadata().getId() + "/lang/en_us.json");

			if (optional.isPresent()) {
				try (InputStream inputStream = Files.newInputStream(optional.get())) {
					JsonObject json = GSON.fromJson(new InputStreamReader(inputStream, StandardCharsets.UTF_8), JsonObject.class);
					for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
						String string = UNSUPPORTED_FORMAT_PATTERN.matcher(GsonHelper.convertToString(entry.getValue(), entry.getKey())).replaceAll("%$1s");
						map.put(entry.getKey(), string);
					}
				} catch (Exception e) {
					LOGGER.error("Couldn't read strings from /assets/{}", modContainer.getMetadata().getId() + "/lang/en_us.json", e);
				}
			}
		});
		ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
		builder.putAll(map);
		return builder;
	}
}
//#endif