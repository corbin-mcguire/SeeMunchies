package com.ceg4110.seemunchies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

public class stagingActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staging);

        ImageView imagePreview = findViewById(R.id.imagePreview);
        Button retryButton = findViewById(R.id.retryButton);
        Button submitButton = findViewById(R.id.submitButton);
        TextView resultsTextView = findViewById(R.id.resultsTextView);

        resultsTextView.setText(getIntent().getStringExtra("testString"));

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retryIntent = new Intent(stagingActivity.this, MainActivity.class);
                startActivity(retryIntent);
            }
        });
    }
}
