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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ceg4110.seemunchies.q.backend.UploadHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private UploadHandler handler = new UploadHandler();
    private File file = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Button selectImage = (Button) findViewById(R.id.imagePicker);
//        selectImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                int PICK_IMAGE_REQUEST = 1;
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select an image"), PICK_IMAGE_REQUEST);
//            }
//        });

        Button submitPic = findViewById(R.id.takePhotoSubmitButton);
        submitPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (file != null) {
                    handler.getImages().add(file);
                    System.out.println("Button pressed and image was there!");
                    try {
                        handler.makeUploadRequest(handler.encodeFile());
                    } catch (FileNotFoundException e) {
                        e.getMessage();
                    } catch (IOException e) {
                        e.getMessage();
                    } catch (Exception e) {
                        e.getMessage();
                    }

                }
                else
                {
                    System.out.println("No file was found :(");
                }
            }
        });

        Button openCamera = (Button) findViewById(R.id.openCamera);
        openCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (file == null) { // Guarantees a new image will be created from the camera.
                    dispatchTakePictureIntent();
                }
                else {
                    file = null;
                    dispatchTakePictureIntent();
                }
            }
        });

//        Button openGallery = (Button) findViewById(R.id.openGallery);
//        openGallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Add code here to open the gallery.
//            }
//        });
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
//        File imageFile = null;
        try {
//            imageFile = createImageFile();
            file = createImageFile();
        } catch (IOException e) {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT);
        }
        if (file!= null) {
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.example.android.fileprovider",
                    file);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
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
