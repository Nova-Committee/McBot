package cn.evolvefield.mods.multi.mixin;

import cn.evolvefield.mods.multi.api.core.mixinselect.MixinSelectConfig;
import cn.evolvefield.mods.multi.api.impl.mixinselect.ModVersionHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ModMixinConfig implements IMixinConfigPlugin {
    private static final ModContainer MOD_CONTAINER = FabricLoader.getInstance().getModContainer("botapi").get();

    private List<String> mixinClasses = null;
    @Override
    public void onLoad(String mixinPackage) {
        MixinSelectConfig selectConfig = MixinSelectConfig.loadMixinSelectConfig(MOD_CONTAINER);
        HashMap<String, Version> modsWithVersion = ModVersionHelper.getAllModsWithVersion(FabricLoader.getInstance(), true);
        mixinClasses = selectConfig.getAllowedMixins(mixinPackage, this.getClass().getClassLoader(), modsWithVersion);

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return mixinClasses == null ? null : (mixinClasses.isEmpty() ? null : mixinClasses);
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
