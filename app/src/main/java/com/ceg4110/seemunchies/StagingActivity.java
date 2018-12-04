package com.ceg4110.seemunchies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceg4110.seemunchies.q.backend.Results;
import com.ceg4110.seemunchies.q.backend.UploadHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class StagingActivity extends AppCompatActivity {

    private UploadHandler handler = new UploadHandler();
    File imageFile = null;
    TextView submitConfirmTextView;
    ImageView imagePreview;
    Button retryButton;
    Button submitButton;
    Button galleryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staging);

        // Connect all elements.
        imagePreview = findViewById(R.id.imagePreview);
        retryButton = findViewById(R.id.retryButton);
        submitButton = findViewById(R.id.submitButton);
        submitConfirmTextView = findViewById(R.id.submitConfirmTextView);
        galleryButton = findViewById(R.id.galleryButton);

        imageFile = (File) getIntent().getExtras().get("imageFile"); // Get file from MainActivity
        String filePath = imageFile.getPath(); // get filepath
        Bitmap bitmap = BitmapFactory.decodeFile(filePath); // Convert file to bitmap
        imagePreview.setImageBitmap(bitmap); // preview image

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retryIntent = new Intent(StagingActivity.this, MainActivity.class);
                startActivity(retryIntent);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitConfirmTextView.setText("Submitting...");
                if (imageFile != null) {
                    handler.getImages().add(imageFile);
                    System.out.println("Button pressed and image was there!");
                    System.out.println("Printing absolute path from submitPic: " + imageFile.getAbsolutePath());
                    try {
                        handler.makeUploadRequest(handler.encodeFile());
                        Toast resultsToast = Toast.makeText(StagingActivity.this,
                                Results.getInstance().getAIDecision().get(0), Toast.LENGTH_LONG);
                        resultsToast.show();
                    } catch (FileNotFoundException e) {
                        e.getMessage();
                    } catch (IOException e) {
                        e.getMessage();
                    } catch (Exception e) {
                        e.getMessage();
                    } finally {
                        imageFile = null; // Always reset file.
                    }
                } else {
                    Toast toast = Toast.makeText(StagingActivity.this, "No image selected.", Toast.LENGTH_LONG);
                    toast.show();
                    System.out.println("No file was found :(");
                }
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(StagingActivity.this, GalleryActivity.class);
                startActivity(galleryIntent);
            }
        });
    }
}
