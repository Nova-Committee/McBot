package cn.evole.mods.mcbot.mixin;

import cn.evole.mods.multi.api.version.MinecraftVersionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModMixinConfig implements IMixinConfigPlugin {
    private static final String MIXIN_PACKAGE_ROOT = "cn.evole.test.multiversion.mixin.";
    private final Logger logger = LogManager.getLogger("AMixin");
    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    private boolean start = false;

    private boolean shouldApply(String mixinClassName){
        String mixin = mixinClassName.substring(MIXIN_PACKAGE_ROOT.length()).trim();//获取mixins所在的包名
        String version = MinecraftVersionHelper.getCurrentVersion();
        boolean r4 = MinecraftVersionHelper.isMCVersionAtLeast("1.14");
        boolean r6 = MinecraftVersionHelper.isMCVersionAtLeast("1.16");
        boolean r7 = MinecraftVersionHelper.isMCVersionAtLeast("1.17");
        boolean r8  = MinecraftVersionHelper.isMCVersionAtLeast("1.18");
        boolean r9  = MinecraftVersionHelper.isMCVersionAtLeast("1.19");


        if (Integer.parseInt(version.split("\\.")[1]) <= 12){
            return false;
        }

        if (mixin.contains("1_14")) {
            if (r4)
                logger.info("Applying mixin: " + mixin + "...");
            return r4;
        }
        if (mixin.contains("1_16")) {
            if (r6)
                logger.info("Applying mixin: " + mixin + "...");
            return r6;
        }
        if (mixin.contains("1_17")) {
            if (r7)
                logger.info("Applying mixin: " + mixin + "...");
            return r7;
        }
        if (mixin.contains("1_18")) {
            if (r8)
                logger.info("Applying mixin: " + mixin + "...");
            return r8;
        }
        if (mixin.contains("1_19")) {
            if (r9)
                logger.info("Applying mixin: " + mixin + "...");
            return r9;
        }


        logger.info("Applying mixin: " + mixin + "...");
        return true;

    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (!start) {
            logger.info("=======================================================");
            logger.info(" McBot - Test.");
            logger.info(" Copyright (c) 2019-2023 cnlimiter. Running on MC " + MinecraftVersionHelper.getCurrentVersion());
            logger.info("=======================================================");
        }
        start = true;

        return shouldApply(mixinClassName);
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        ArrayList<String> list = new ArrayList<>();


        return list;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
