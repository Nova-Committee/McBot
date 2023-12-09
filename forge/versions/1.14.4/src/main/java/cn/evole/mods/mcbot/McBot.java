package cn.evole.mods.mcbot;

import cn.evole.mods.mcbot.init.event.ICmdEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.*;
import net.minecraftforge.fml.loading.FMLPaths;
import org.jetbrains.annotations.NotNull;

/**
 * Author cnlimiter
 * CreateTime 2023/7/1 3:58
 * Name McBot
 * Description
 */

@Mod(Const.MODID)
public class McBot {
    public IMcBot mcBot;
    public McBot(){
        this.mcBot = new IMcBot(FMLPaths.CONFIGDIR.get());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void cmdRegister(@NotNull FMLServerStartingEvent event) {
        ICmdEvent.register(event.getCommandDispatcher());
    }

    @SubscribeEvent
    public void onServerAboutToStart(@NotNull FMLServerAboutToStartEvent event) {
        this.mcBot.onServerStarting(event.getServer());
    }

    @SubscribeEvent
    public void onServerStarted(@NotNull FMLServerStartedEvent event) {
        this.mcBot.onServerStarted(event.getServer());
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.WorldTickEvent event) {
        this.mcBot.onServerTick(event.world.getServer());
    }
    @SubscribeEvent
    public void onServerChat(@NotNull ServerChatEvent event){
        this.mcBot.onServerChat(event.getPlayer().level, event.getPlayer(), event.getMessage());
    }

    @SubscribeEvent
    public void onServerStopping(@NotNull FMLServerStoppingEvent event){
        this.mcBot.onServerStopping(event.getServer());
    }
    @SubscribeEvent
    public void onServerStopped(@NotNull FMLServerStoppedEvent event){
        this.mcBot.onServerStopped(event.getServer());
    }


    @SubscribeEvent
    public void onPlayerIn(PlayerEvent.@NotNull PlayerLoggedInEvent  event){
        this.mcBot.onPlayerLogIn(event.getPlayer().level, event.getPlayer());
    }
    @SubscribeEvent
    public void onPlayerOut(PlayerEvent.@NotNull PlayerLoggedOutEvent  event){
        this.mcBot.onPlayerLogOut(event.getPlayer().level, event.getPlayer());
    }
    @SubscribeEvent
    public void onPlayerDeath(@NotNull LivingDeathEvent event){
        if (event.getEntity() instanceof ServerPlayer)
            this.mcBot.onPlayerDeath(
                    event.getEntity().level,
                    event.getSource(),
                    (ServerPlayer) event.getEntity());
    }
    @SubscribeEvent
    public void onPlayerAdvancement(@NotNull AdvancementEvent event){
        this.mcBot.onPlayerAdvancement(
                event.getPlayer().level,
                event.getPlayer(),
                event.getAdvancement()
        );
    }
}
