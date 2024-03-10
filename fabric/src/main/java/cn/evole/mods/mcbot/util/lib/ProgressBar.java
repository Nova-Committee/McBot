package cn.evole.mods.mcbot.util.lib;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Objects;

/**
 * @Project: Test-Lib
 * @Author: cnlimiter
 * @CreateTime: 2024/2/27 19:49
 * @Description:
 * 进度条工具
 * <p>
 * 这个进度条打印期间，其他控制台输出会影响最终结果
 * 所以做成单线程，阻塞的打印
 */

public class ProgressBar {
    /**
     * 进度条长度
     */
    private int BAR_LENGTH = 35;

    /**
     * 进度条缩放比例
     */
    private BigDecimal resizeRate;

    /**
     * 总数据长度
     */
    private long dataCount;

    /**
     * 当前进度
     */
    private volatile long completeCount;

    /**
     * 完成进度在进度条中的数量
     */
    private volatile int completeBarCount;

    /**
     * 完成进度百分比
     */
    private volatile String completePercent;

    /**
     * 是否初始化
     */
    private boolean hasInited = false;
    /**
     * 是否已经结束
     */
    private boolean hasFinished = false;
    /**
     * 进度条title
     */
    private String title;

    private static final char processChar = '█';
    private static final char waitChar = '─';

    public static final long MILLIS_PER_SECOND = 1000;
    public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
    public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;

    public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;

    private long startTime;
    private long endTime;
    private String content;


    private ProgressBar() {
        resizeRate = new BigDecimal(BAR_LENGTH).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
    }

    private static ProgressBar build() {
        return new ProgressBar();
    }



    public static ProgressBar build(long dataCount) {
        return build(dataCount, 0, "Progress");
    }

    public static ProgressBar build(long dataCount, String title) {
        return build(dataCount, 0, title);
    }

    public static ProgressBar build(long dataCount, long completeCount, String title) {
        ProgressBar progressBar = build();
        progressBar.dataCount = dataCount;
        progressBar.completeCount = completeCount;
        progressBar.title = title;
        return progressBar;
    }



    private String generate(int num, char ch) {
        if (num == 0) return "";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < num; i++) {
            builder.append(ch);
        }
        return builder.toString();
    }

    private String genProcess(int num) {
        return generate(num, processChar);
    }

    private String genWaitProcess(int num) {
        return generate(num, waitChar);
    }

    /**
     * 清空进度条
     */
    private void cleanProcessBar() {
        if(Objects.nonNull(content)) {
//            System.out.print(generate(content.length(), '\b'));
        }
        System.out.print('\r');
    }

    /**
     * 进度+1
     */
    public void process() {
        checkStatus();
        checkInit();
        cleanProcessBar();
        doProcess();
        drawProgressBar();
        checkFinish();
    }

    private void doProcess() {
        completeCount++;
        BigDecimal completeBarRate = getCompleteRate();
        completeBarCount = genCompleteBarCount(completeBarRate);
        completePercent = genCompletePercent(completeBarRate);
    }

    private int genCompleteBarCount(BigDecimal completeBarRate) {
        BigDecimal completeBarCount = BigDecimal.ZERO;
        if(completeCount > 0 && dataCount > 0) {
            completeBarCount = completeBarRate
                    .multiply(new BigDecimal("100"))
                    .multiply(resizeRate);
        }
        return completeBarCount.intValue();

    }

    private String genCompletePercent(BigDecimal completeBarRate) {
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        percentInstance.setMaximumFractionDigits(2);
        return percentInstance.format(completeBarRate);
    }

    private BigDecimal getCompleteRate() {
        BigDecimal completeBarRate = BigDecimal.ZERO;
        if(completeCount > 0 && dataCount > 0) {
            completeBarRate = new BigDecimal(completeCount).divide(new BigDecimal(dataCount), 4, RoundingMode.HALF_UP);
        }
        return completeBarRate;
    }




    private String genDurationStr() {
        long days         = 0;
        long hours        = 0;
        long minutes      = 0;
        long seconds      = 0;
        long milliseconds = System.currentTimeMillis() - startTime;

        days = milliseconds / MILLIS_PER_DAY;
        milliseconds = milliseconds - (days * MILLIS_PER_DAY);
        hours = milliseconds / MILLIS_PER_HOUR;
        milliseconds = milliseconds - (hours * MILLIS_PER_HOUR);
        minutes = milliseconds / MILLIS_PER_MINUTE;
        milliseconds = milliseconds - (minutes * MILLIS_PER_MINUTE);
        seconds = milliseconds / MILLIS_PER_SECOND;
        milliseconds = milliseconds - (seconds * MILLIS_PER_SECOND);
        StringBuilder sb = new StringBuilder();
        if(days > 0) {
            sb.append(days).append('天');
        }
        if(hours > 0) {
            sb.append(hours).append("小时");
        }
        if(minutes > 0) {
            sb.append(minutes).append("分钟");
        }
        if(seconds > 0) {
            sb.append(seconds).append('秒');
        }
        if(milliseconds > 0) {
            sb.append(milliseconds).append("毫秒");
        }
        return sb.toString();
    }

    /**
     * 绘制进度条
     */
    private void drawProgressBar() {
        checkStatus();
        content = String.format(
                "%s: %7s├%s%s┤%s",
                title,
                completePercent,
                genProcess(completeBarCount),
                genWaitProcess(BAR_LENGTH - completeBarCount),
                genDurationStr()
        );
        System.out.print(content);
    }


    /**
     * 检查进度条状态
     * 已完成的进度条不可以继续执行
     */
    private void checkStatus() {
        if (hasFinished) throw new RuntimeException("进度条已经完成");
    }

    /**
     * 检查是否已经初始化
     */
    private void checkInit() {
        if (!hasInited) init();
    }


    /**
     * 检查是否已经完成
     */
    private void checkFinish() {
        if (hasFinished() && !hasFinished) finish();
    }

    /**
     * 是否已经完成进度条
     *
     * @return
     */
    private boolean hasFinished() {
        return completeCount >= dataCount;
    }

    /**
     * 初始化进度条
     */
    private void init() {
        hasInited = true;
        startTime = System.currentTimeMillis();
    }

    /**
     * 结束进度条，由 checkFinish()调用
     */
    private void finish() {
        System.out.println();
        endTime = System.currentTimeMillis();
        hasFinished = true;
    }


}
