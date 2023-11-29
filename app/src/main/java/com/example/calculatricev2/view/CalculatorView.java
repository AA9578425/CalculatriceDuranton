package com.example.calculatricev2.view;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
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

    private TextView _result;
    private GradientDrawable _border;

    private Button _equalButton;

    private ScrollView _historyScrollView;
    private LinearLayout _historyLayout;

    private TableLayout _defaultOperations;
    private TableLayout _advancedOperations;

    private CalculatorPresenter _calculatorPresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _calculatorPresenter = new CalculatorPresenter();
        _calculatorPresenter.setView(this);


        setContentView(R.layout.activity_calculatrice);

        _historyScrollView = findViewById(R.id.historyScrollView);
        _historyLayout = findViewById(R.id.historyLayout);
        _defaultOperations = findViewById(R.id.defaultOperations);
        _advancedOperations = findViewById(R.id.advancedOperations);
        _equalButton = findViewById(R.id.buttonEqual);

        _result = findViewById(R.id.result);
        _result.setOnClickListener(event -> setContentView(R.layout.activity_calculatrice));

        _defaultOperations.setVisibility(View.VISIBLE);
        _advancedOperations.setVisibility(View.GONE);

        _border = new GradientDrawable();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            _border.setPadding(15,5,15,5);
        }

        _equalButton.setEnabled(false);
    }

    public void showValue(String value) {
        _result.setText(value);
    }

    public void nextCharacter(View view) {
        _calculatorPresenter.nextCharacter((String) view.getTag());
    }

    public void calcul(View view){
        _calculatorPresenter.calcul();
        _equalButton.setEnabled(false);
    }

    public void addToHistory(String text){
        TextView newTextView = new TextView(this);
        newTextView.setText(text);

        _historyLayout.addView(newTextView);

        _historyScrollView.fullScroll(View.FOCUS_DOWN);
    }

    public void clear(View view) {
        _calculatorPresenter.clear();
        showValue("");
        setValidResult();
        _equalButton.setEnabled(false);

    }

    public void switchPane(View view){
        if(_defaultOperations.getVisibility() == View.VISIBLE) {
            System.out.println("Advanced");
            _defaultOperations.setVisibility(View.GONE);
            _advancedOperations.setVisibility(View.VISIBLE);
        }else{
            System.out.println("Default");
            _defaultOperations.setVisibility(View.VISIBLE);
            _advancedOperations.setVisibility(View.GONE);
        }
    }

    public void removeCharacter(View view) {
        _calculatorPresenter.removeCharacter();
    }

    public void doubleBracket(View view) {
        _calculatorPresenter.doubleBracket();
    }

    public void setInvalidResult(){
        System.out.println("Invalid");
        _border.setStroke(7, Color.RED);
        _result.setBackground(_border);
        _equalButton.setEnabled(false);
    }

    public void setValidResult() {
        System.out.println("Valid");
        _border.setStroke(0, Color.RED);
        _result.setBackground(_border);
        _equalButton.setEnabled(true);
    }
}
