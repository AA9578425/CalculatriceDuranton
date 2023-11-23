package com.example.calculatricev2.model;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class CalculatorModel {
    public double calcul(String expressionString) {
        Expression expression = new ExpressionBuilder(expressionString).build();
        return expression.evaluate();
    }
}
