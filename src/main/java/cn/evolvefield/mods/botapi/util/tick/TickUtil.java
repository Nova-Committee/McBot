package cn.evolvefield.mods.botapi.util.tick;

import org.jetbrains.annotations.NotNull;

public class TickUtil {
    public static double toMilliseconds(final long time) {
        return time * 1.0E-6D;
    }

    public static double toMilliseconds(final double time) {
        return time * 1.0E-6D;
    }

    public static double average(final long @NotNull [] longs) {
        long i = 0L;
        for (final long l : longs) {
            i += l;
        }
        return i / (double) longs.length;
    }


}
