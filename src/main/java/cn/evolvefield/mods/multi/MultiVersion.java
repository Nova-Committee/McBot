package cn.evolvefield.mods.multi;

import cn.evolvefield.mods.multi.api.impl.mixinselect.config.VersionPredicateTypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.TypeAdapterFactory;
import de.klotzi111.util.GsonUtil.GsonUtil;

import java.util.ArrayList;
import java.util.List;

public class MultiVersion {
    public static final Gson GSON;

    static {
        List<TypeAdapterFactory> factories = new ArrayList<>();
        factories.add(new VersionPredicateTypeAdapterFactory());
        GSON = GsonUtil.getPreConfiguredGsonBuilder(factories)
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .create();
    }
}
