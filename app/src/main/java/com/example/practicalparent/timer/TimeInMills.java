package com.example.practicalparent.timer;

/**
 * conversion to microsecond
 */
public enum TimeInMills {

    HALF_SECOND(500),
    SECOND(1000),
    MINUTE(60_000);

    private long value;

    TimeInMills(int val) {
        value = val;
    }

    public long getValue() {
        return value;
    }
}
