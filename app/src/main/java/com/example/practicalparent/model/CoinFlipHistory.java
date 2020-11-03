package com.example.practicalparent.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CoinFlipHistory {

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    private Child child;
    private boolean pickedHead;
    private boolean gotHead;
    private String date;

    public CoinFlipHistory(boolean pickedHead, boolean gotHead){
        // deal with no child case
        this(new Child(""), pickedHead, gotHead);
    }

    public CoinFlipHistory(Child child, boolean pickedHead, boolean gotHead){
        this(child, pickedHead, gotHead, new SimpleDateFormat(FORMAT).format(new Date()));
    }

    CoinFlipHistory(Child child, boolean pickedHead, boolean gotHead, String date){
        this.child = child;
        this.pickedHead = pickedHead;
        this.gotHead = gotHead;
        this.date = date;
    }

    public Child getChild() {
        return child;
    }

    public boolean isPickedHead() {
        return pickedHead;
    }

    public boolean isGotHead() {
        return gotHead;
    }

    public String getDate() {
        return date;
    }

    public boolean isWon(){
        return pickedHead == gotHead;
    }
}
