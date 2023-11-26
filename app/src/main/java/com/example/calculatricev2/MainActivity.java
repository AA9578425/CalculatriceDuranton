package com.example.calculatricev2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.calculatricev2.view.CalculatorView;
import com.example.calculatricev2.view.RSSView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button startCalculator = findViewById(R.id.calculatrice);

        startCalculator.setOnClickListener(event -> {
            Intent intent = new Intent(this, CalculatorView.class);
            startActivity(intent);
        });

        Button startRSS = findViewById(R.id.fluxRSS);

        startRSS.setOnClickListener(event -> {
            Intent intent = new Intent(this, RSSView.class);
            startActivity(intent);
        });

    }

    public void resetDisplay(String title, String date, Bitmap image, String description) {

    }
}