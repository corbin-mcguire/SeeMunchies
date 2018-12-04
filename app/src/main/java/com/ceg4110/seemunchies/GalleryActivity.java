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

import com.ceg4110.seemunchies.q.backend.GalleryHandler;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    Button homeButton;
    Button previousButton;
    Button nextButton;
    ImageView imageView;
    TextView resultsTextView;
    ArrayList<Bitmap> galleryArray;
    ArrayList<String> imageResultsArray;
    int currentIndex = 0;
    GalleryHandler gh = new GalleryHandler();

    private String getBetterResults(String results) {
        String[] sa;

        String betterResults = "";
        sa = results.split(" ");
        System.out.println(sa[0] + sa[0]);

        Double yesFood = Double.parseDouble(sa[0]);
        System.out.println(yesFood);
        Double noFood = Double.parseDouble(sa[1]);
        System.out.println(noFood);

        Double diff = yesFood - noFood;
        System.out.println(diff);

        if (diff > .75) {
            betterResults = "I want to munch on this!";
        } else if (diff > .5) {
            betterResults = "I think I want to much on this.";
        } else if (diff > .25) {
            betterResults = "I'm not sure if I want to munch on this.";
        } else {
            betterResults = "I don't wanna munch on this!";
        }

        return betterResults;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        homeButton = findViewById(R.id.homeButton);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        imageView = findViewById(R.id.imageView);
        resultsTextView = findViewById(R.id.resultsTextView);

        try {
            gh.makeGalleryRequest();
            galleryArray = gh.getImages();
            imageResultsArray = gh.getScores();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        // Sets the imageView to the first image in the array from the server.
//        imageView.setImageBitmap(galleryArray[0]);
//        resultsTextView.setText(imageResultsArray[0]);
        System.out.println(gh.getImages());
        System.out.println(gh.getScores());

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
                    imageView.setImageBitmap(galleryArray.get(currentIndex));
                    resultsTextView.setText(imageResultsArray.get(currentIndex));
//                    resultsTextView.setText(getBetterResults(imageResultsArray.get(currentIndex)));
                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() { // Navigate forwards.
            @Override
            public void onClick(View v) {
                if (currentIndex > galleryArray.size() + 1) {
                    Toast.makeText(GalleryActivity.this, "No more images!", Toast.LENGTH_SHORT).show();
                } else {
                    currentIndex++;
                    imageView.setImageBitmap(galleryArray.get(currentIndex));
                    resultsTextView.setText(imageResultsArray.get(currentIndex));
//                    resultsTextView.setText(getBetterResults(imageResultsArray.get(currentIndex)));
                }
            }
        });


    }
}
