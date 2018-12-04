package com.ceg4110.seemunchies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GalleryActivity extends AppCompatActivity {

    Button homeButton;
    Button previousButton;
    Button nextButton;
    ImageView imageView;
    TextView resultsTextView;
    Bitmap[] galleryArray;
    String[] imageResultsArray;
    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        homeButton = findViewById(R.id.homeButton);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        imageView = findViewById(R.id.imageView);
        resultsTextView = findViewById(R.id.resultsTextView);

        // Sets the imageView to the first image in the array from the server.
//        imageView.setImageBitmap(galleryArray[0]);
//        resultsTextView.setText(imageResultsArray[0]);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(GalleryActivity.this, MainActivity.class);
                startActivity(homeIntent);
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() { // Navigate backwards
            @Override
            public void onClick(View v) {
                if (currentIndex == 0) {
                    Toast.makeText(GalleryActivity.this, "You're at the beginning!", Toast.LENGTH_SHORT).show();
                } else {
                    currentIndex--;
                    imageView.setImageBitmap(galleryArray[currentIndex - 1]);
                    resultsTextView.setText(imageResultsArray[currentIndex - 1]);
                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() { // Navigate forwards.
            @Override
            public void onClick(View v) {
                if (currentIndex > galleryArray.length + 1) {
                    Toast.makeText(GalleryActivity.this, "No more images!", Toast.LENGTH_SHORT).show();
                } else {
                    currentIndex++;
                    imageView.setImageBitmap(galleryArray[currentIndex + 1]);
                    resultsTextView.setText(imageResultsArray[currentIndex + 1]);
                }
            }
        });


    }
}
