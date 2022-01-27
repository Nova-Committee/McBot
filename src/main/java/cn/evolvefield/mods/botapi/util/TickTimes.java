package cn.evolvefield.mods.botapi.util;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/27 12:12
 * Version: 1.0
 */
public class TickTimes {
    private final long[] times;

    public TickTimes(final int length) {
        this.times = new long[length];
    }

    public void add(final int index, final long time) {
        this.times[index % this.times.length] = time;
    }

    public long[] times() {
        return this.times.clone();
    }
}
