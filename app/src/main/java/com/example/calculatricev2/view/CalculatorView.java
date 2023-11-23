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

public class CalculatorView extends AppCompatActivity {

    private TextView result;

    private ScrollView historyScrollView;
    private LinearLayout historyLayout;

    private TableLayout defaultOperations;
    private TableLayout advancedOperations;

    private final CalculatorPresenter _calculatorPresenter;

    public CalculatorView(){
        _calculatorPresenter = new CalculatorPresenter();
        _calculatorPresenter.setView(this);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calculatrice);

        historyScrollView = findViewById(R.id.historyScrollView);
        historyLayout = findViewById(R.id.historyLayout);
        defaultOperations = findViewById(R.id.defaultOperations);
        advancedOperations = findViewById(R.id.advancedOperations);

        result = findViewById(R.id.result);
        result.setOnClickListener(event -> setContentView(R.layout.activity_calculatrice));

        defaultOperations.setVisibility(View.VISIBLE);
        advancedOperations.setVisibility(View.GONE);
    }

    public void showValue(String value) {
        result.setText(value);
    }

    public void nextCharacter(View view) {
        _calculatorPresenter.nextCharacter((String) view.getTag());
    }

    public void calcul(View view){
        _calculatorPresenter.calcul();
    }

    public void addToHistory(String text){
        TextView newTextView = new TextView(this);
        newTextView.setText(text);

        historyLayout.addView(newTextView);

        historyScrollView.fullScroll(View.FOCUS_DOWN);
    }

    public void clear(View view) {
        _calculatorPresenter.clear();
        showValue("");
    }

    public void switchPane(View view){
        if(defaultOperations.getVisibility() == View.VISIBLE) {
            System.out.println("Advanced");
            defaultOperations.setVisibility(View.GONE);
            advancedOperations.setVisibility(View.VISIBLE);
        }else{
            System.out.println("Default");
            defaultOperations.setVisibility(View.VISIBLE);
            advancedOperations.setVisibility(View.GONE);
        }
    }
}
