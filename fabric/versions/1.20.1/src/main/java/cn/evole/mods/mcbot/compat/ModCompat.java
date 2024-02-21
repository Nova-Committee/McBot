package cn.evole.mods.mcbot.compat;

import cn.evole.mods.mcbot.init.event.IPlayerEvent;
import me.drex.vanish.api.VanishEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public class ModCompat {
    public static final boolean VANISH = FabricLoader.getInstance().isModLoaded("melius-vanish");

    public static void init() {
        if (VANISH) {
            initVanishEvents();
        }
    }

    // 初始化 Vanish 事件监听器
    private static void initVanishEvents() {
        // 注册事件监听器，当玩家解除隐身时调用 loggedIn 方法
        VanishEvents.UN_VANISH_MESSAGE_EVENT.register((serverPlayer) -> {
            // 获取玩家所在的世界信息
            Level world = serverPlayer.getCommandSenderWorld();
            // 调用 loggedIn 方法
            IPlayerEvent.loggedIn(world, serverPlayer);
            // 返回一个空的 Component
            return Component.empty();
        });

        // 注册事件监听器，当玩家隐身时调用 loggedOut 方法
        VanishEvents.VANISH_MESSAGE_EVENT.register((serverPlayer) -> {
            // 获取玩家所在的世界信息
            Level world = serverPlayer.getCommandSenderWorld();
            // 调用 loggedOut 方法
            IPlayerEvent.loggedOut(world, serverPlayer);
            // 返回一个空的 Component
            return Component.empty();
        });
    }
}



