package com.example.calculatricev2.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calculatricev2.MainActivity;
import com.example.calculatricev2.R;
import com.example.calculatricev2.presenter.RSSPresenter;

import java.net.URL;

public class RSSView extends AppCompatActivity {
    private RSSPresenter _RSSPresenter;
    private TextView _imageTitle;
    private TextView _imageDate;
    private ImageView _imageDisplay;
    private TextView _imageDescription;

    private Spinner _spinnerRSSFeed;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fluxrss);

        _RSSPresenter = new RSSPresenter(this);
        _RSSPresenter.setView(this);

        _imageTitle = findViewById(R.id.imageTitle);
        _imageDate = findViewById(R.id.imageDate);
        _imageDisplay = findViewById(R.id.imageDisplay);
        _imageDescription = findViewById(R.id.imageDescription);

        _spinnerRSSFeed = findViewById(R.id.spinnerRSSFeed);

        String[] feeds={"ENSICAEN", "Actualités", "International", "Société"};
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,feeds);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _spinnerRSSFeed.setAdapter(dataAdapterR);

        _spinnerRSSFeed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                System.out.println("...");
                String RSSFeedName = String.valueOf(_spinnerRSSFeed.getSelectedItem());
                _RSSPresenter.setRSSFeed(RSSFeedName);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.closeActivity) {
            finish();
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
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


