//package cn.evolvefield.mods.botapi.init.event;
//
//import cn.evolvefield.mods.botapi.BotApi;
//import cpw.mods.fml.client.event.ConfigChangedEvent;
//import cpw.mods.fml.common.FMLCommonHandler;
//import cpw.mods.fml.common.event.FMLPreInitializationEvent;
//import cpw.mods.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.common.config.Config;
//import net.minecraftforge.common.config.ConfigCategory;
//import net.minecraftforge.common.config.ConfigManager;
//import net.minecraftforge.common.config.Configuration;
//import net.minecraftforge.fml.client.event.ConfigChangedEvent;
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//
//
//public class WorldEventHandler {
//
//    public void preInit(FMLPreInitializationEvent event) {
//        FMLCommonHandler.instance().bus().register(this);
//    }
//
//    @SubscribeEvent
//    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
//        if (event.modID.equals(BotApi.MODID)) {
//            ConfigCategory.(BotApi.MODID, Config.Type.INSTANCE);
//        }
//    }
//
//
//}
