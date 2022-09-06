package cn.evolvefield.mods.botapi.init.mixins;

import cn.evolvefield.mods.botapi.core.tick.TickTimeService;
import cn.evolvefield.mods.botapi.util.tick.RollingAverage;
import cn.evolvefield.mods.botapi.util.tick.TickTimes;
import cn.evolvefield.mods.botapi.util.tick.TickUtil;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LongSummaryStatistics;
import java.util.function.BooleanSupplier;
import java.util.function.LongPredicate;
import java.util.stream.LongStream;

/**
 * Adds TPS and tick time rolling averages.
 */
@Unique
@Mixin(MinecraftServer.class)
abstract class LevelTickMixin implements TickTimeService {
    private final TickTimes tickTimes5s = new TickTimes(100);
    private final TickTimes tickTimes10s = new TickTimes(200);
    private final TickTimes tickTimes60s = new TickTimes(1200);

    private final RollingAverage tps5s = new RollingAverage(5);
    private final RollingAverage tps1m = new RollingAverage(60);
    private final RollingAverage tps5m = new RollingAverage(60 * 5);
    private final RollingAverage tps15m = new RollingAverage(60 * 15);

    private long previousTime;

    @Shadow
    private int tickCount;
    @Shadow
    @Final
    public long[] tickTimes;

    private static final LongPredicate NOT_ZERO = l -> l != 0;

    @Inject(method = "tickServer", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void injectTick(final BooleanSupplier var1, final CallbackInfo ci, final long tickStartTimeNanos, final long tickDurationNanos) {
        this.tickTimes5s.add(this.tickCount, tickDurationNanos);
        this.tickTimes10s.add(this.tickCount, tickDurationNanos);
        this.tickTimes60s.add(this.tickCount, tickDurationNanos);

        if (this.tickCount % RollingAverage.SAMPLE_INTERVAL == 0) {
            if (this.previousTime == 0) {
                this.previousTime = tickStartTimeNanos - RollingAverage.TICK_TIME;
            }
            final long diff = tickStartTimeNanos - this.previousTime;
            this.previousTime = tickStartTimeNanos;
            final BigDecimal currentTps = RollingAverage.TPS_BASE.divide(new BigDecimal(diff), 30, RoundingMode.HALF_UP);
            this.tps5s.add(currentTps, diff);
            this.tps1m.add(currentTps, diff);
            this.tps5m.add(currentTps, diff);
            this.tps15m.add(currentTps, diff);

        }
    }


    @Override
    public double averageMspt() {
        final LongSummaryStatistics statistics = LongStream.of(tickTimes5s.times()).filter(NOT_ZERO).summaryStatistics();
        //System.out.println(statistics.getAverage());
        double mspt = TickUtil.toMilliseconds(statistics.getAverage());
        if (mspt >= 5) {
            mspt = 5;
        }
        return mspt;
    }

    @Override
    public double @NotNull [] recentTps() {
        final double[] tps = new double[4];
        tps[0] = this.tps5s.average();
        tps[1] = this.tps1m.average();
        tps[2] = this.tps5m.average();
        tps[3] = this.tps15m.average();
        return tps;
    }

    @Override
    public @NotNull TickTimes tickTimes5s() {
        return this.tickTimes5s;
    }

    @Override
    public @NotNull TickTimes tickTimes10s() {
        return this.tickTimes10s;
    }

    @Override
    public @NotNull TickTimes tickTimes60s() {
        return this.tickTimes60s;
    }
}
