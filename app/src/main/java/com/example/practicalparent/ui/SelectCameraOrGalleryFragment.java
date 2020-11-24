package com.example.practicalparent.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class SelectCameraOrGalleryFragment extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        String[] test = {"Open Camera", "Open Gallery"};
        dialog.setItems(test, new DialogInterface.OnClickListener() {
            final SaveChildActivity activity = (SaveChildActivity) getActivity();

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    activity.openCamera();
                } else {
                    activity.openGallery();
                }
            }
        });
        return dialog.create();
    }

}
