package com.example.calculatricev2.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calculatricev2.R;
import com.example.calculatricev2.presenter.RSSPresenter;

public class RSSView extends AppCompatActivity {
    RSSPresenter _RSSPresenter;
    TextView _imageTitle;
    TextView _imageDate;
    ImageView _imageDisplay;
    TextView _imageDescription;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fluxrss);

        _RSSPresenter = new RSSPresenter(this);
        _RSSPresenter.setView(this);

        _imageTitle = findViewById(R.id.imageTitle);
        _imageDate = findViewById(R.id.imageDate);
        _imageDisplay = findViewById(R.id.imageDisplay);
        _imageDescription = findViewById(R.id.imageDescription);
    }

    public void previousItem(View view) {
        _RSSPresenter.previousItem();
    }

    public void nextItem(View view) {
        _RSSPresenter.nextItem();
    }

    public void showRSSContent(Bitmap bitmap, String date, String title, String description){
        _imageDisplay.setImageBitmap(bitmap);
        _imageDate.setText(date);
        _imageTitle.setText(title);
        _imageDescription.setText(description);
    }
}


