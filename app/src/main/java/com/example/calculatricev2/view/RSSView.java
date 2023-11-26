package com.example.calculatricev2.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calculatricev2.R;
import com.example.calculatricev2.presenter.CalculatorPresenter;
import com.example.calculatricev2.presenter.RSSPresenter;

public class RSSView extends AppCompatActivity {
    RSSPresenter _RSSPresenter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fluxrss);
    }

    public RSSView(){
        _RSSPresenter = new RSSPresenter();
        _RSSPresenter.setView(this);
    }

    public void previousItem(View view) {
    }

    public void nextItem(View view) {
    }
}


