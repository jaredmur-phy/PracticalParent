package com.example.practicalparent.ui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.practicalparent.R;
import com.example.practicalparent.model.ChildManager;

// message which shows after flipping the coin
public class MessageFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.message_layout, null);

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_NEGATIVE:
                        launchMainMenu();
                        break;
                    case DialogInterface.BUTTON_POSITIVE:
                        if (!ChildManager.getInstance(getActivity()).isEmpty()) {
                            getActivity().finish();
                        }
                        break;
                }
            }
        };

        return new AlertDialog.Builder(getActivity()).setTitle("").
                setView(v).setNegativeButton("No", listener).setView(v).setPositiveButton("Yes", listener).create();
    }

    private void launchMainMenu() {
        Intent intent = MainActivity.makeLaunchIntent(getActivity());
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }

    public void setCanceledOnTouchOutside(boolean b) {
    }
}
