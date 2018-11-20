package com.ceg4110.seemunchies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button selectImage = (Button) findViewById(R.id.imagePicker);
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                int PICK_IMAGE_REQUEST = 1;
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select an image"), PICK_IMAGE_REQUEST);
            }
        });

        Button openCamera = (Button) findViewById(R.id.openCamera);
        openCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dispatchTakePictureIntent();
//                Intent takePictureIntent = new Intent("android.media.action.IMAGE_CAPTURE");
//                Uri uriSavedImage = Uri.fromFile(new File("/sdcard/flashCropped.png"));
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
//                startActivityForResult(takePictureIntent, 1);
//                startActivity(takePictureIntent);
            }
        });

        Button openGallery = (Button) findViewById(R.id.openGallery);
        openGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add code here to open the gallery.
            }
        });
    }

    private final int REQUEST_TAKE_PHOTO = 1;

    /**
     * This function enables the user to take a picture.
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
        File imageFile = null;
        try {
            imageFile = createImageFile();
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
        }
        if (imageFile!= null) {
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.example.android.fileprovider",
                    imageFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            Log.i("Success", "Success!");
        }
    }

    String currentPhotoPath;
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", directory);

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

}
