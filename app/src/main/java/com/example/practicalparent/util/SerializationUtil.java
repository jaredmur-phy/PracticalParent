package com.example.practicalparent.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * help to save Object
 * first use Gson Serialize Object to String
 * then use SharedPerformance save the String
 */
public class SerializationUtil {

    private final String filename;
    private final Context context;

    public SerializationUtil(Context c, String filename){
        this.context = c;
        this.filename = filename;
    }

    /**
     * example:  getObject(KEY, new TypeToken<List<Child>>(){}.getType(), new ArrayList<>());
     */
    public <T> T getObject(String key, Type type, T defaultVal) {
        String json = getString(key, null);
        if (TextUtils.isEmpty(json)) {
            return defaultVal;
        }
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, type);
        } catch (Exception e) {
            return defaultVal;
        }
    }

    public void putObject(String key, Object object) {
        String json = new Gson().toJson(object);
        putString(key, json);
    }

    public void putString(String key, String value){
        SharedPreferences sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(filename, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String getString(String key, String defaultVal){
        SharedPreferences sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(filename, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultVal);
    }


}
