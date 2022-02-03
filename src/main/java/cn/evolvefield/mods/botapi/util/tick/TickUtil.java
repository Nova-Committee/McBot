package cn.evolvefield.mods.botapi.util.tick;

import org.jetbrains.annotations.NotNull;

public class TickUtil {
    public static double toMilliSeconds(final double time) {
        return time * 10E-6d;
    }

    public static double average(final long @NotNull [] longs) {
        long i = 0L;
        for (final long l : longs) {
            i += l;
        }

        return i / (double) longs.length;
    }
}
