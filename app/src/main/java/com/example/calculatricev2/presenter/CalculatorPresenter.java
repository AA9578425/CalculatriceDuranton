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
        if(_calculatorModel.checkResult(expression) == false){
            _calculatorView.setInvalidResult();
        }else{
            _calculatorView.setValidResult();
        }
        _calculatorView.showValue(expression);
    }

    public void clear(){
        expression = "";
    }

    public void setView(CalculatorView calculatorView) {
        _calculatorView = calculatorView;
    }

    public void calcul() {
        String troncatedResult = String.format("%.3f", _calculatorModel.calcul(expression));
        _calculatorView.addToHistory(expression + " = " + troncatedResult);
        expression = troncatedResult;
        _calculatorView.showValue(troncatedResult);
    }

    public void removeCharacter() {
        if(expression != null && expression.length() != 0){
            expression = expression.substring(0, expression.length() - 1);
            _calculatorView.showValue(expression);
        }
    }

    public void doubleBracket() {
        if(expression == null || expression.length() == 0 || expression.substring(expression.length() - 1).compareTo("(") == 0
        || expression.chars().filter(ch -> ch == ')').count() >= expression.chars().filter(ch -> ch == '(').count()){
            nextCharacter("(");
        }else{
            nextCharacter(")");
        }
    }
}
