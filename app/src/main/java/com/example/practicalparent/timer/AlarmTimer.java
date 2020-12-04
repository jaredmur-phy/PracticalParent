package com.example.practicalparent.timer;

import android.content.Context;
import android.os.SystemClock;

import com.example.practicalparent.R;


/**
 * Singleton class
 * <p>
 * Help to calculate the timer information
 * also can pause, reset, resume the timer
 */
public class AlarmTimer {

    private static AlarmTimer timer = new AlarmTimer(0);

    private TimerStatus status = TimerStatus.SET_TIMER;

    private long startTime;
    private long endTime;
    private long duration;

    private long spedEndTime;
    private double speed;

    // it stores after how many seconds timeout
    // for example if startTime = 1000, endTime = 9000, pausedPoint = 2000
    // that means after 2s (2000 Mills), we will reach the endTime, so we are in 7000 right now
    private long pausedPoint;

    /**
     * @param duration ms
     */
    private AlarmTimer(long duration) {
        if (duration < 0) {
            throw new IllegalArgumentException("duration should not be negative");
        }
        this.duration = duration;
        startTime = SystemClock.elapsedRealtime();
        endTime = startTime + duration;
        spedEndTime = endTime;
        speed = 1;
        pausedPoint = -1;
    }

    public String getSpeedText() {
        return "speed " + (int) (this.speed * 100) + "%";
    }

    public void pause() {
        pausedPoint = getRemainingTime();
    }

    public void resume() {
        if (isPaused()) {
            endTime = SystemClock.elapsedRealtime() + pausedPoint;
            spedEndTime = (long) (SystemClock.elapsedRealtime() + pausedPoint / speed);
            pausedPoint = -1;
        }
    }

    public void setSpeed(double speed) {
        pause();    // use current speed calculate paused point
        this.speed = speed; // change speed
        resume();   // use new speed calculate end point
    }

    // return a value in [0, 1]
    // return 0.6 means there are 60% time left
    public float getRemainingPercentage() {
        float percentage = (float) getRemainingTime() / this.duration;
        if (Float.isNaN(percentage)) {
            percentage = 1;
        }
        return Math.min(Math.max(0, percentage), 1);
    }

    // return after how many second times out
    public long getRemainingTime() {
        if (isPaused()) {
            return pausedPoint;
        }
        long res = (long) ((spedEndTime - SystemClock.elapsedRealtime()) * speed);
        return res > 0 ? res : 0;
    }

    public boolean isPaused() {
        return pausedPoint != -1;
    }

    public boolean isTimeout() {
        if (isPaused() || status == TimerStatus.SET_TIMER) {
            return false;
        }
        return SystemClock.elapsedRealtime() >= spedEndTime;
    }

    public void reset(long duration) {
        this.duration = duration;
        startTime = SystemClock.elapsedRealtime();
        endTime = startTime + duration;
        spedEndTime = (long) (startTime + duration / speed);
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

    private void validateNotPaused() {
        if (isPaused()) {
            throw new IllegalArgumentException("can't get end time when timer is paused.");
        }
    }

    public static AlarmTimer getInstance() {
        return timer;
    }

    /**
     * Save the the different status that Timer might have
     */
    public enum TimerStatus {

        SET_TIMER(R.string.set_timer),
        PAUSE(R.string.pause_timer),
        RESUME(R.string.resume_timer),
        TIMEOUT(R.string.time_out);

        private int msgId;

        TimerStatus(int msgId) {
            this.msgId = msgId;
        }

        public String getMsg(Context c) {
            return c.getString(msgId);
        }
    }

    public TimerStatus getStatus() {
        return status;
    }

    public String getStatusDesc(Context c) {
        return status.getMsg(c);
    }

    public void setTimeoutStatus() {
        status = TimerStatus.TIMEOUT;
    }

    public void setSetTimerStatus() {
        status = TimerStatus.SET_TIMER;
    }

    public void setPauseStatus() {
        status = TimerStatus.PAUSE;
    }

    public void changeStatus() {
        switch (status) {
            case SET_TIMER:
            case RESUME:
                status = TimerStatus.PAUSE;
                break;
            case PAUSE:
                status = TimerStatus.RESUME;
                break;
            case TIMEOUT:
                status = TimerStatus.SET_TIMER;
                break;
        }
    }

}
