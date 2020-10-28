package com.example.practicalparent.timer;

public enum TimerStatus {

    SET_TIMER("Set Timer"),
    PAUSE("Pause"),
    RESUME("Resume"),
    TIMEOUT("Time out");

    private String msg;
    TimerStatus(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
