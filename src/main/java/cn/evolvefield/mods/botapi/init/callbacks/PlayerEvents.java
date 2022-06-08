package cn.evolvefield.mods.botapi.init.callbacks;

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
public final class PlayerEvents {
    public PlayerEvents() {
    }

    public static final Event<Player_Tick> PLAYER_TICK = EventFactory.createArrayBacked(Player_Tick.class, callbacks -> (world, player) -> {
        for (Player_Tick callback : callbacks) {
            callback.onTick(world, player);
        }
    });

    public static final Event<Player_Death> PLAYER_DEATH = EventFactory.createArrayBacked(Player_Death.class, callbacks -> (source, player) -> {
        for (Player_Death callback : callbacks) {
            callback.onDeath(source, player);
        }
    });

    public static final Event<Player_Change_Dimension> PLAYER_CHANGE_DIMENSION = EventFactory.createArrayBacked(Player_Change_Dimension.class, callbacks -> (world, player) -> {
        for (Player_Change_Dimension callback : callbacks) {
            callback.onChangeDimension(world, player);
        }
    });

    public static final Event<Player_Dig_Speed_Calc> ON_PLAYER_DIG_SPEED_CALC = EventFactory.createArrayBacked(Player_Dig_Speed_Calc.class, callbacks -> (world, player, digSpeed, state) -> {
        for (Player_Dig_Speed_Calc callback : callbacks) {
            float newSpeed = callback.onDigSpeedCalc(world, player, digSpeed, state);
            if (newSpeed != digSpeed) {
                return newSpeed;
            }
        }

        return -1;
    });

    public static final Event<Player_Logged_In> PLAYER_LOGGED_IN = EventFactory.createArrayBacked(Player_Logged_In.class, callbacks -> (world, player) -> {
        for (Player_Logged_In callback : callbacks) {
            callback.onPlayerLoggedIn(world, player);
        }
    });

    public static final Event<Player_Logged_Out> PLAYER_LOGGED_OUT = EventFactory.createArrayBacked(Player_Logged_Out.class, callbacks -> (world, player) -> {
        for (Player_Logged_Out callback : callbacks) {
            callback.onPlayerLoggedOut(world, player);
        }
    });

    public static final Event<Player_Advancement> PLAYER_ADVANCEMENT = EventFactory.createArrayBacked(Player_Advancement.class, callbacks -> (player, advancement) -> {
        for (Player_Advancement callback : callbacks) {
            callback.onAdvancement(player, advancement);
        }
    });


    @FunctionalInterface
    public interface Player_Advancement {
        void onAdvancement(Player player, Advancement advancement);
    }

    @FunctionalInterface
    public interface Player_Tick {
        void onTick(ServerLevel world, ServerPlayer player);
    }

    @FunctionalInterface
    public interface Player_Death {
        void onDeath(DamageSource source, ServerPlayer player);
    }

    @FunctionalInterface
    public interface Player_Change_Dimension {
        void onChangeDimension(ServerLevel world, ServerPlayer player);
    }

    @FunctionalInterface
    public interface Player_Dig_Speed_Calc {
        float onDigSpeedCalc(Level world, Player player, float digSpeed, BlockState state);
    }

    @FunctionalInterface
    public interface Player_Logged_In {
        void onPlayerLoggedIn(Level world, Player player);
    }

    @FunctionalInterface
    public interface Player_Logged_Out {
        void onPlayerLoggedOut(Level world, Player player);
    }
}
