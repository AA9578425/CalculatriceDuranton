package com.example.calculatricev2.presenter;

import com.example.calculatricev2.model.CalculatorModel;
import com.example.calculatricev2.view.CalculatorView;

public class CalculatorPresenter {
    CalculatorModel _calculatorModel;
    CalculatorView _calculatorView;

    String expression = "";

    public CalculatorPresenter(){
        _calculatorModel = new CalculatorModel();
    }


    public void nextCharacter(String character){
        expression += character;
        _calculatorView.showValue(expression);
    }

    public void clear(){
        expression = "";
    }

    public void setView(CalculatorView calculatorView) {
        _calculatorView = calculatorView;
    }

    public void calcul() {
        Double result = _calculatorModel.calcul(expression);
        _calculatorView.addToHistory(expression + " = " + result);
        expression = String.valueOf(result);
        _calculatorView.showValue(String.valueOf(result));
    }
}
