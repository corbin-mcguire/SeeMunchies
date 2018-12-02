package com.ceg4110.seemunchies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GalleryActivity extends AppCompatActivity {

//    Button homeButton = findViewById(R.id.homeButton);
//    Button previousButton = findViewById(R.id.previousButton);
//    Button nextButton = findViewById(R.id.nextButton);
//
//    ImageView imageView1 = findViewById(R.id.imageView1);
//    TextView textView1 = findViewById(R.id.textView1);
//
//    ImageView imageView2 = findViewById(R.id.imageView3);
//    TextView textView2 = findViewById(R.id.textView2);
//
//    ImageView imageView3 = findViewById(R.id.imageView3);
//    TextView textView3 = findViewById(R.id.textView3);
//
//    ImageView imageView4 = findViewById(R.id.imageView4);
//    TextView textView4 = findViewById(R.id.textView4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Button homeButton = findViewById(R.id.homeButton);
        Button previousButton = findViewById(R.id.previousButton);
        Button nextButton = findViewById(R.id.nextButton);

        ImageView imageView1 = findViewById(R.id.imageView1);
        TextView textView1 = findViewById(R.id.textView1);

        ImageView imageView2 = findViewById(R.id.imageView3);
        TextView textView2 = findViewById(R.id.textView2);

        ImageView imageView3 = findViewById(R.id.imageView3);
        TextView textView3 = findViewById(R.id.textView3);

        ImageView imageView4 = findViewById(R.id.imageView4);
        TextView textView4 = findViewById(R.id.textView4);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(GalleryActivity.this, MainActivity.class);
                startActivity(homeIntent);
            }
        });


    }
}
