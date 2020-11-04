package com.example.practicalparent.listener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * filter beforeTextChanged and onTextChanged
 */
public abstract class TextChangedListener implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
}
