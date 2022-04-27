package cn.evolvefield.mods.botapi.util.tick;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/27 12:13
 * Version: 1.0
 */
public class RollingAverage {
	public static final int TPS = 20;
	public static final int SAMPLE_INTERVAL = 20;
	public static final long SEC_IN_NANO = 1000000000;
	public static final int TICK_TIME = (int) SEC_IN_NANO / SAMPLE_INTERVAL;
	public static final BigDecimal TPS_BASE = new BigDecimal("1E9").multiply(new BigDecimal(SAMPLE_INTERVAL));

	private final int size;
	private final BigDecimal[] samples;
	private final long[] times;
	private long time;
	private BigDecimal total;
	private int index = 0;

	public RollingAverage(final int size) {
		this.size = size;
		this.samples = new BigDecimal[size];
		this.times = new long[size];
		this.time = size * SEC_IN_NANO;
		this.total = dec(TPS).multiply(dec(SEC_IN_NANO)).multiply(dec(size));
		for (int i = 0; i < size; i++) {
			this.samples[i] = dec(TPS);
			this.times[i] = SEC_IN_NANO;
		}
	}

	private static @NotNull BigDecimal dec(final long t) {
		return new BigDecimal(t);
	}

	public void add(final @NotNull BigDecimal x, final long t) {
		this.time -= this.times[this.index];
		this.total = this.total.subtract(this.samples[this.index].multiply(dec(this.times[this.index])));
		this.samples[this.index] = x;
		this.times[this.index] = t;
		this.time += t;
		this.total = this.total.add(x.multiply(dec(t)));
		if (++this.index == this.size) {
			this.index = 0;
		}
	}

	public double average() {
		return this.total.divide(dec(this.time), 30, RoundingMode.HALF_UP).doubleValue();
	}
}
