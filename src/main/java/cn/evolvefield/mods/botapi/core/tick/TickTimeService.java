package cn.evolvefield.mods.botapi.core.tick;

import cn.evolvefield.mods.botapi.util.tick.TickTimes;
import org.jetbrains.annotations.NotNull;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/27 12:24
 * Version: 1.0
 */
public interface TickTimeService {

    public double @NotNull [] recentTps();

    double averageMspt();

    @NotNull TickTimes tickTimes5s();

    @NotNull TickTimes tickTimes10s();

    @NotNull TickTimes tickTimes60s();
}
