package cn.evolvefield.mods.botapi.core.tick;

import cn.evolvefield.mods.botapi.util.TickTimes;
import org.jetbrains.annotations.NotNull;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/27 12:15
 * Version: 1.0
 */
public interface MinecraftServerAccess {

    public double @NotNull [] recentTps();

    @NotNull
    TickTimes tickTimes5s();

    @NotNull TickTimes tickTimes10s();

    @NotNull TickTimes tickTimes60s();
}
