package com.example.practicalparent.timer;

import android.os.SystemClock;

public class AlarmTimer {

    private long startTime;
    private long endTime;
    private long duration;
    private long pausedPoint;   // it stores after how many seconds timeout

    /**
     * @param duration ms
     */
    public AlarmTimer(long duration){
        if(duration < 0)
            throw new IllegalArgumentException("duration should not be negative");
        this.duration = duration;
        reset();
    }

    public void pause(){
        pausedPoint = getRestTime();
    }

    public void resume(){
        if (isPaused()) {
            endTime = SystemClock.elapsedRealtime() + pausedPoint;
            pausedPoint = -1;
        }
    }

    // return after how many second times out
    public long getRestTime(){
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

    public void reset(){
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
}
