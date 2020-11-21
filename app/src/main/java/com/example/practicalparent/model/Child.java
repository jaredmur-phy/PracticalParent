package com.example.practicalparent.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * One child object represent a child
 */
public class Child {
    private String firstName;
    private byte[] imgInByte;

    //Constructor
    public Child(String name) {
        this(name, null);
    }

    public Child(String name, Drawable d){
        if (name == null) {
            throw new IllegalArgumentException("Null child name");
        }
        this.firstName = name;
        if(d == null){
            imgInByte = null;
        }else {
            setDrawable(d);
        }
    }

    public boolean hasDrawable(){
        return imgInByte != null;
    }

    public void setDrawable(Drawable d) {
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        this.imgInByte = stream.toByteArray();
    }

    public BitmapDrawable getDrawable(Context c) {
        return new BitmapDrawable(c.getResources(), BitmapFactory.decodeByteArray(this.imgInByte, 0, this.imgInByte.length));
    }

    public void setName(String name) {
        this.firstName = name;
    }

    public String getName() {
        return this.firstName;
    }


    public String toString() {
        return "Name: " + firstName;
    }


}
