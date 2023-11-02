package cn.evole.mods.mcbot;

//#if MC >= 11700
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//#else
//$$ import org.apache.logging.log4j.Logger;
//$$ import org.apache.logging.log4j.LogManager;
//#endif

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/1 16:58
 * Version: 1.0
 */
public class Const {
    public static final String MODID = "mcbot";
    //#if MC >= 11700
    public static final Logger LOGGER = LoggerFactory.getLogger("McBot");
    //#else
    //$$ public static final Logger LOGGER = LogManager.getLogger("McBot");
    //#endif
    public static boolean isShutdown = false;

    public static boolean isLoad(String modId){
        return ModList.get().isLoaded(modId);
    }

}
