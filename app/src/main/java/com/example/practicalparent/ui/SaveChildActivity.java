package com.example.practicalparent.ui;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.practicalparent.R;
import com.example.practicalparent.model.Child;
import com.example.practicalparent.model.ChildManager;


public class SaveChildActivity extends AppCompatActivity {

    private final static String GET_INDEX = "GET_INDEX";
    private final static int OPEN_GALLERY_REQUEST_CODE = 13;
    private ChildManager childManager;
    private int editChildIndex = -1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_child);

        requestWritePermission();

        // TODO: delete child and tack picture
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
        if (editChildIndex == -1) {
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
        imgBtn.setOnClickListener(v -> openGallery());
    }

    private void setupSaveBtn(){
        findViewById(R.id.id_save_child).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.id_child_name_text);
                String childName = editText.getText().toString();
                ImageView imageView = findViewById(R.id.id_child_img);
                Child child = new Child(childName, imageView.getDrawable());
                if (editChildIndex == -1) {
                    childManager.addChild(child);
                } else {
                    childManager.updateChild(editChildIndex, child);
                }
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == OPEN_GALLERY_REQUEST_CODE) {
            ImageView imageView = findViewById(R.id.id_child_img);
            Uri uri = data.getData();
            imageView.setImageURI(uri);
        }
    }

    // open gallery to select image
    private void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, OPEN_GALLERY_REQUEST_CODE);
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

    private void requestWritePermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

}