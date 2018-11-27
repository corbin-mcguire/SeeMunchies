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
import android.widget.TextView;
import android.widget.Toast;

import com.ceg4110.seemunchies.q.backend.Results;
import com.ceg4110.seemunchies.q.backend.UploadHandler;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private UploadHandler handler = new UploadHandler();
    private File file = null;
    TextView resultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Results.getInstance();
        resultsTextView = (TextView) findViewById(R.id.resultsTextView);



//        TODO: Be able to upload a selected image from local storage.
        Button selectImage = (Button) findViewById(R.id.imagePicker);
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                int PICK_IMAGE_REQUEST = 1;
//                pickImage.setType("image/*");
//                pickImage.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(pickImage, "Select an image"), PICK_IMAGE_REQUEST);
                startActivityForResult(pickImage, 1);
            }
        });



        Button submitPic = findViewById(R.id.takePhotoSubmitButton);
        submitPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (file != null) {
                    handler.getImages().add(file);
                    System.out.println("Button pressed and image was there!");
                    try {
                        handler.makeUploadRequest(handler.encodeFile());
                        resultsTextView.setText(Results.getInstance().getAIDecision().get(0));
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
                if (file != null) { // Guarantees a new image will be created from the camera.
                    file = null;
                    dispatchTakePictureIntent();
                }
                else {
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

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    file = new File(selectedImage.getPath());
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    file = new File(selectedImage.getPath());
                }
                break;
        }
    }

    private String currentPhotoPath;
    /**
     * Creates an image file that can be uploaded to the EC2 for determination.
     * @return Created image file.
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", directory);

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }



}
