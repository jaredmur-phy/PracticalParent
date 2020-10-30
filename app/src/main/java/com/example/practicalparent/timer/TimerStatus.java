package com.example.practicalparent.timer;


import android.content.Context;

import com.example.practicalparent.R;

/**
 * Save the the different status that Timer might have
 */
public enum TimerStatus {

    SET_TIMER(R.string.set_timer),
    PAUSE(R.string.pause_timer),
    RESUME(R.string.resume_timer),
    TIMEOUT(R.string.time_out);

    private int msgId;
    TimerStatus(int msgId){
        this.msgId = msgId;
    }

    public String getMsg(Context c) {
        return c.getString(msgId);
    }

}
