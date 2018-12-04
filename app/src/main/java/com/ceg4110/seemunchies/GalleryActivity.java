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

        String score = results;

        score = score.replace("[", "");
        score = score.replace("]", "");
        score = score.replace("}", "");
        score = score.replace("{", "");

        char[] temp = score.toCharArray();
        int index = -1;
        for(int i = 0; i < temp.length; i++)
        {
            if(temp[i] == '.')
            {
                index = i;
            }
        }

        index -= 1;
        if(temp[index - 1] == '-')
        {
            index -= 1;
        }

        String upper = score.substring(0, index);
        String lower = score.substring(index);

        System.out.println(upper);
        System.out.println(lower);

        double[] parsedScores = { Double.parseDouble(upper), Double.parseDouble(lower)};

        System.out.println(parsedScores[0]);
        System.out.println(parsedScores[1]);

        String betterResults = "";
//
        Double diff = parsedScores[0] - parsedScores[1];
        System.out.println(diff);
//
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
        imageView.setImageBitmap(galleryArray.get(0));
        System.out.println(gh.getImages());
        resultsTextView.setText(imageResultsArray.get(0));
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
                    resultsTextView.setText(getBetterResults(imageResultsArray.get(currentIndex)));
                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() { // Navigate forwards.
            @Override
            public void onClick(View v) {
                System.out.println(currentIndex);
                if (currentIndex >= galleryArray.size()-1) {
                    Toast.makeText(GalleryActivity.this, "No more images!", Toast.LENGTH_SHORT).show();
                } else {
                    currentIndex++;
                    imageView.setImageBitmap(galleryArray.get(currentIndex));
                    resultsTextView.setText(getBetterResults(imageResultsArray.get(currentIndex)));
                }
            }
        });


    }
}
