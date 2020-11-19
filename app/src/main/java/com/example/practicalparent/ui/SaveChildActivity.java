package com.example.practicalparent.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.practicalparent.R;
import com.example.practicalparent.model.Child;
import com.example.practicalparent.model.ChildManager;


public class SaveChildActivity extends AppCompatActivity {

    private final static String GET_INDEX = "GET_INDEX";
    private final static int OPEN_GALLERY_REQUEST_CODE = 13;
    private final static int OPEN_CAMERA_REQUEST_CODE = 14;
    private final static int IMG_WIDTH = 100;
    private final static int IMG_HEIGHT = 100;
    private ChildManager childManager;
    private int editChildIndex = -1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_child);

        // TODO: check duplicate name
        getChildIndex();
        getChildManager();

        fillInfo();

        setupImgBtn();
        setupSaveBtn();
    }

    private void getChildIndex(){
        editChildIndex = getIntent().getExtras().getInt(GET_INDEX);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void fillInfo() {
        ImageView childImg = findViewById(R.id.id_child_img);
        if (editChildIndex == -1) { // use default image
            childImg.setImageDrawable(getDrawable(R.drawable.head));
        } else {

            Child curChild = childManager.get(editChildIndex);

            EditText editText = findViewById(R.id.id_child_name_text);
            editText.setText(curChild.getName());

            childImg.setImageDrawable(curChild.getDrawable(this));
        }
    }

    private void getChildManager(){
        childManager = ChildManager.getInstance(this);
    }

    private void setupImgBtn(){
        ImageView imgBtn = findViewById(R.id.id_child_img);
        imgBtn.setOnClickListener(v -> {
            showDialog();
        });
    }

    private void showDialog(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        SelectCameraOrGalleryFragment dialog = new SelectCameraOrGalleryFragment();
        dialog.show(fragmentManager, "test");
    }

    private void setupSaveBtn(){
        findViewById(R.id.id_save_child).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.id_child_name_text);
                String childName = editText.getText().toString();
                ImageView imageView = findViewById(R.id.id_child_img);

                // Compress bitmap
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = Bitmap.createScaledBitmap(drawable.getBitmap(), IMG_WIDTH, IMG_HEIGHT, true);

                Child child = new Child(childName, new BitmapDrawable(getResources(), bitmap));
                if (editChildIndex == -1) {
                    childManager.addChild(child);
                } else {
                    childManager.updateChild(editChildIndex, child);
                }
                finish();
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK || data == null){
            return;
        }
        ImageView imageView = findViewById(R.id.id_child_img);
        if (requestCode == OPEN_GALLERY_REQUEST_CODE) {
            // select an image from gallery
            Uri uri = data.getData();
            imageView.setImageURI(uri);
        }else if(requestCode == OPEN_CAMERA_REQUEST_CODE){
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(bitmap);
        }
    }

    // open gallery to select image
    public void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, OPEN_GALLERY_REQUEST_CODE);
    }

    private void checkPermissionForCamera() {
        if (ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    OPEN_CAMERA_REQUEST_CODE);
        }
    }

    public void openCamera() {
        checkPermissionForCamera();
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePhotoIntent, OPEN_CAMERA_REQUEST_CODE);
        } else {
            Toast.makeText(this, getString(R.string.no_camera_message), Toast.LENGTH_SHORT).show();
        }
    }

    public static Intent getIntent(Context c, int index){
        Intent i = new Intent(c, SaveChildActivity.class);
        i.putExtra(GET_INDEX, index);
        return i;
    }

    public static Intent makeLaunchIntent(Context c) {
        return getIntent(c, -1);
    }

    public static Intent makeLaunchIntent(Context c, int index) {
        return getIntent(c, index);
    }


}