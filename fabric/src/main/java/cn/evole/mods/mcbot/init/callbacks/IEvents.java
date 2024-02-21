package cn.evole.mods.mcbot.init.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.advancements.Advancement;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 9:36
 * Version: 1.0
 */
public final class IEvents {
    public static final Event<PlayerTick> PLAYER_TICK = EventFactory.createArrayBacked(PlayerTick.class, callbacks -> (world, player) -> {
        for (PlayerTick callback : callbacks) {
            callback.onTick(world, player);
        }
    });

    public static final Event<PlayerDeath> PLAYER_DEATH = EventFactory.createArrayBacked(PlayerDeath.class, callbacks -> (source, player) -> {
        for (PlayerDeath callback : callbacks) {
            callback.onDeath(source, player);
        }
    });

    public static final Event<PlayerChangeDimension> PLAYER_CHANGE_DIMENSION = EventFactory.createArrayBacked(PlayerChangeDimension.class, callbacks -> (world, player) -> {
        for (PlayerChangeDimension callback : callbacks) {
            callback.onChangeDimension(world, player);
        }
    });

    public static final Event<PlayerDigSpeedCalc> ON_PLAYER_DIG_SPEED_CALC = EventFactory.createArrayBacked(PlayerDigSpeedCalc.class, callbacks -> (world, player, digSpeed, state) -> {
        for (PlayerDigSpeedCalc callback : callbacks) {
            float newSpeed = callback.onDigSpeedCalc(world, player, digSpeed, state);
            if (newSpeed != digSpeed) {
                return newSpeed;
            }
        }

        return -1;
    });

    public static final Event<PlayerLoggedIn> PLAYER_LOGGED_IN = EventFactory.createArrayBacked(PlayerLoggedIn.class, callbacks -> (world, player) -> {
        for (PlayerLoggedIn callback : callbacks) {
            callback.onPlayerLoggedIn(world, player);
        }
    });

    public static final Event<PlayerLoggedOut> PLAYER_LOGGED_OUT = EventFactory.createArrayBacked(PlayerLoggedOut.class, callbacks -> (world, player) -> {
        for (PlayerLoggedOut callback : callbacks) {
            callback.onPlayerLoggedOut(world, player);
        }
    });

    public static final Event<PlayerAdvancement> PLAYER_ADVANCEMENT = EventFactory.createArrayBacked(PlayerAdvancement.class, callbacks -> (player, advancement) -> {
        for (PlayerAdvancement callback : callbacks) {
            callback.onAdvancement(player, advancement);
        }
    });


    public static final Event<ServerChat> SERVER_CHAT = EventFactory.createArrayBacked(ServerChat.class, callbacks -> (player, message) -> {
        for (ServerChat callback : callbacks) {
            callback.onChat(player, message);
        }
    });

    @FunctionalInterface
    public interface PlayerAdvancement {
        void onAdvancement(Player player, Advancement advancement);
    }

    @FunctionalInterface
    public interface PlayerTick {
        void onTick(ServerLevel world, ServerPlayer player);
    }

    @FunctionalInterface
    public interface PlayerDeath {
        void onDeath(DamageSource source, ServerPlayer player);
    }

    @FunctionalInterface
    public interface PlayerChangeDimension {
        void onChangeDimension(ServerLevel world, ServerPlayer player);
    }

    @FunctionalInterface
    public interface PlayerDigSpeedCalc {
        float onDigSpeedCalc(Level world, Player player, float digSpeed, BlockState state);
    }

    @FunctionalInterface
    public interface PlayerLoggedIn {
        void onPlayerLoggedIn(Level world, ServerPlayer player);
    }

    @FunctionalInterface
    public interface PlayerLoggedOut {
        void onPlayerLoggedOut(Level world, ServerPlayer player);
    }

    @FunctionalInterface
    public interface ServerChat {
        void onChat(ServerPlayer player, String message);
    }
}
