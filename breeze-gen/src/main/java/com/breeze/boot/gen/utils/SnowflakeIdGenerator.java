package com.breeze.boot.gen.utils;


public class SnowflakeIdGenerator {
    // 起始时间戳，这里以 2020-01-01 00:00:00 为例
    private final long startTimeStamp = 1577836800000L;

    // 数据中心 ID 所占位数
    private final long dataCenterIdBits = 5L;
    // 机器 ID 所占位数
    private final long workerIdBits = 5L;
    // 序列号所占位数
    private final long sequenceBits = 12L;

    // 数据中心 ID 最大值
    private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);
    // 机器 ID 最大值
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    // 序列号最大值
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    // 机器 ID 向左移位数
    private final long workerIdShift = sequenceBits;
    // 数据中心 ID 向左移位数
    private final long dataCenterIdShift = sequenceBits + workerIdBits;
    // 时间戳向左移位数
    private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

    private final long dataCenterId;
    private final long workerId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowflakeIdGenerator(long dataCenterId, long workerId) {
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException("Data center ID can't be greater than " + maxDataCenterId + " or less than 0");
        }
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("Worker ID can't be greater than " + maxWorkerId + " or less than 0");
        }
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
    }

    public synchronized long nextId() {
        long currentTimestamp = System.currentTimeMillis();

        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id for " + (lastTimestamp - currentTimestamp) + " milliseconds");
        }

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 序列号达到最大值，等待下一毫秒
                currentTimestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳改变，重置序列号
            sequence = 0L;
        }

        lastTimestamp = currentTimestamp;

        return ((currentTimestamp - startTimeStamp) << timestampLeftShift) |
                (dataCenterId << dataCenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    private long waitNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    public static void main(String[] args) {
        SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(1, 1);
        for (int i = 0; i < 10; i++) {
            System.out.println(idGenerator.nextId());
        }
    }
}