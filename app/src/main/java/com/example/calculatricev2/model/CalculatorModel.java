package com.example.calculatricev2.model;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class CalculatorModel {
    private Expression _expression;
    public double executeCalculation(String expressionString) {
        if(_expression == null){
            _expression = new ExpressionBuilder(expressionString).build();
        }
        return _expression.evaluate();
    }

    public boolean checkResult(String expressionString){
        try {
            _expression = new ExpressionBuilder(expressionString).build();
            return _expression.validate().isValid();
        } catch (IllegalArgumentException e){
            return false;
        }
    }
}
