package com.example.practicalparent.model;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * saves coin flip history,
 */
public class CoinFlipHistory {

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    private Child child;
    private int picked; // -1 not pick, 0 pick tail, 1 pick heads
    private boolean gotHead;
    private String date;

    public CoinFlipHistory(int picked, boolean gotHead) {
        // deal with no child case
        this(null, picked, gotHead);
    }

    public CoinFlipHistory(Child child, int picked, boolean gotHead) {
        this(child, picked, gotHead, new SimpleDateFormat(FORMAT).format(new Date()));
    }

    CoinFlipHistory(Child child, int picked, boolean gotHead, String date) {
        this.child = child;
        this.picked = picked;
        this.gotHead = gotHead;
        this.date = date;
    }


    public boolean isPicked() {
        return picked != PickedConstant.NOT_PICKED;
    }

    public Child getChild() {
        return child;
    }

    public boolean isPickedHead() {
        return picked == PickedConstant.PICKED_HEAD;
    }

    public boolean isGotHead() {
        return gotHead;
    }

    public int getPicked() {
        return picked;
    }

    public String getDate() {
        return date;
    }

    public boolean isWon() {
        return (picked == PickedConstant.PICKED_HEAD) == gotHead;
    }


    @NonNull
    @Override
    public String toString() {
        String sb = this.getDate() + " <br>";
        if (this.isPicked()) {
            sb += "<b>" +
                    this.getChild().getName() +
                    "</b> picked <b>" + parseBoolToHead(this.isPickedHead()) +
                    "</b>";
        }
        sb += " got <i>" + parseBoolToHead(this.isGotHead()) + "</i>";
        return sb;
    }

    private String parseBoolToHead(boolean isHead) {
        if (isHead) {
            return "Head";
        }
        return "Tail";
    }
}
