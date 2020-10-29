package com.example.practicalparent.timer;

import android.os.SystemClock;


/**
 * Singleton class
 *
 * Help to calculate the timer information
 * also can pause, reset, resume the timer
 */
public class AlarmTimer {

    private static AlarmTimer timer = new AlarmTimer(0);

    private long startTime;
    private long endTime;
    private long duration;

    // it stores after how many seconds timeout
    // for example if startTime = 1000, endTime = 9000, pausedPoint = 2000
    // that means after 2s (2000 Mills), we will reach the endTime, so we are in 7000 right now
    private long pausedPoint;

    /**
     * @param duration ms
     */
    private AlarmTimer(long duration){
        if(duration < 0)
            throw new IllegalArgumentException("duration should not be negative");
        reset(duration);
    }

    public void pause(){
        pausedPoint = getRemainingTime();
    }

    public void resume(){
        if (isPaused()) {
            endTime = SystemClock.elapsedRealtime() + pausedPoint;
            pausedPoint = -1;
        }
    }

    // return after how many second times out
    public long getRemainingTime(){
        if(isPaused()){
            return pausedPoint;
        }
        long res = endTime - SystemClock.elapsedRealtime();
        return res > 0 ? res : 0;
    }

    public boolean isPaused(){
        return pausedPoint != -1;
    }

    public boolean isTimeout(){
        if(isPaused()) return false;
        return SystemClock.elapsedRealtime() >= endTime;
    }

    public void reset(long duration){
        this.duration = duration;
        startTime = SystemClock.elapsedRealtime();
        endTime = startTime + duration;
        pausedPoint = -1;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        validateNotPaused();
        return endTime;
    }

    public long getDuration() {
        return duration;
    }

    public long getPausedPoint() {
        return pausedPoint;
    }

    private void validateNotPaused(){
        if(isPaused()){
            throw new IllegalArgumentException("can't get end time when timer is paused.");
        }
    }

    public static AlarmTimer getInstance(){
        return timer;
    }
}
