package cn.evole.mods.mcbot;

import cn.evole.mods.mcbot.init.event.ICmdEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
    public void cmdRegister(@NotNull RegisterCommandsEvent event) {
        ICmdEvent.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void onServerAboutToStart(@NotNull ServerAboutToStartEvent event) {
        this.mcBot.onServerStarting(event.getServer());
    }

    @SubscribeEvent
    public void onServerStarted(@NotNull ServerStartedEvent event) {
        this.mcBot.onServerStarted(event.getServer());
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.LevelTickEvent event) {
        this.mcBot.onServerTick(event.level.getServer());
    }
    @SubscribeEvent
    public void onServerChat(@NotNull ServerChatEvent event){
        this.mcBot.onServerChat(event.getPlayer().level(), event.getPlayer(), event.getRawText());
    }

    @SubscribeEvent
    public void onServerStopping(@NotNull ServerStoppingEvent event){
        this.mcBot.onServerStopping(event.getServer());
    }
    @SubscribeEvent
    public void onServerStopped(@NotNull ServerStoppedEvent event){
        this.mcBot.onServerStopped(event.getServer());
    }


    @SubscribeEvent
    public void onPlayerIn(PlayerEvent.@NotNull PlayerLoggedInEvent  event){
        this.mcBot.onPlayerLogIn(event.getEntity().level(), event.getEntity());
    }
    @SubscribeEvent
    public void onPlayerOut(PlayerEvent.@NotNull PlayerLoggedOutEvent  event){
        this.mcBot.onPlayerLogOut(event.getEntity().level(), event.getEntity());
    }
    @SubscribeEvent
    public void onPlayerDeath(@NotNull LivingDeathEvent event){
        if (event.getEntity() instanceof ServerPlayer serverPlayer)
            this.mcBot.onPlayerDeath(
                    event.getEntity().level(),
                    event.getSource(),
                    serverPlayer);
    }
    @SubscribeEvent
    public void onPlayerAdvancement(@NotNull AdvancementEvent.AdvancementEarnEvent event){
        this.mcBot.onPlayerAdvancement(
                event.getEntity().level(),
                event.getEntity(),
                event.getAdvancement()
        );
    }
}
